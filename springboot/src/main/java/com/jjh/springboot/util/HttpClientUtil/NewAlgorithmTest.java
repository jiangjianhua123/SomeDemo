package com.jjh.springboot.util.HttpClientUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jjh.springboot.util.Base64Util;
import okhttp3.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author jianghong
 * @Description
 * @create 2018-05-30 10:57
 **/
public class NewAlgorithmTest {


    private static String AT_SEARCH_URL = "http://192.168.3.240:9001/verify/face/search";


    private static String YT_SEARCH_URL = "http://192.168.3.245:11180/website/face/v2/retrieval/proxy";

    private static String YT_SEARCH_PAM = "{\"fields\":[\"face_image_id\",\"repository_id\",\"timestamp\",\"person_id\",\"name\",\"gender\",\"nation\",\"camera_id\",\"is_hit\",\"born_year\",\"picture_uri\",\"face_image_uri\",\"face_rect\",\"rec_glasses\",\"rec_fringe\",\"rec_uygur\",\"rec_gender\",\"rec_age\",\"rec_age_range\",\"multi_frame_face_image_uris\",\"multi_frame_picture_uris\",\"multi_frame_face_rects\",\"similarity\",\"annotation\",\"case_number\",\"remark\"],\"condition\":{},\"query_meta\":{\"searchReason\":{\"reason\":\"\",\"info\":\"\"}},\"order\":{\"similarity\":-1},\"start\":0,\"limit\":5,\"retrieval\":{\"face_image_id\":\"%s\",\"threshold\":0,\"using_ann\":true,\"repository_ids\":[\"2@DEFAULT\"]}}";

    private static String YT_UPIMAGE_URL = "http://192.168.3.245:11180/face/v1/framework/face_image/repository/picture/synchronized/global";

    private static String YT_UPIMAGE_PAM = "{\"options\":{\"max_faces_allowed\":-1},\"picture_image_content_base64\":\"%s\"}";

    /**
     *
     */
//    public static void getATTopN(File searchFile) throws IOException {
//        String result = "";
//        CloseableHttpClient client = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost(AT_SEARCH_URL);
//        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//        builder.addBinaryBody("imageData",searchFile);
//        builder.addTextBody("dbName", "test_5w");
//        builder.addTextBody("topNum", "5");
//        builder.addTextBody("score", "0.5");
//        HttpEntity entity = builder.build();
//        httpPost.setEntity(entity);
//        HttpResponse response = client.execute(httpPost);// 执行提交
//        HttpEntity responseEntity = response.getEntity();
//        if (responseEntity != null) {
//            // 将响应内容转换为字符串
//            result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
//        }
//        System.err.println(result);
//    }


    /**
     * 取得tes_5W 库中数据
     *
     * @param searchFile
     * @return
     * @throws Exception
     */
    public static String getSTTopN(File searchFile) throws Exception {
        OkHttpClient.Builder builder = HttpClientFactory.getInstance().getOkClientPool().getHttpClient()
                .newBuilder();
        OkHttpClient okClient = builder.build();
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), searchFile);
        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("dbName", "test_5w")
                .addFormDataPart("topNum", "5")
                .addFormDataPart("score", "0.5")
                .addFormDataPart("imageData", searchFile.getName(), fileBody)
                .build();
        Request request = new Request.Builder()
                .url(AT_SEARCH_URL)
                .post(requestBody)
                .build();
        try (Response response = okClient.newCall(request).execute()) {
            return response.body().string();
        }
    }

    /**
     * 取得01库 库中数据
     *
     * @param searchFile
     * @return
     * @throws IOException
     */
    public static String getYTTopN(File searchFile) throws IOException {
        JSONObject jsonObject = JSON.parseObject(HttpClientUtil.OkSyncPost(YT_UPIMAGE_URL, String.format(YT_UPIMAGE_PAM, Base64Util.imageToString(searchFile))));
        if ("OK".equalsIgnoreCase(jsonObject.getString("message"))) {
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            String face_image_id = jsonArray.getJSONObject(0).getString("face_image_id");
            return HttpClientUtil.OkSyncPost(YT_SEARCH_URL, String.format(YT_SEARCH_PAM, face_image_id));
        }
        return "";
    }



    public static void main(String[] args) throws Exception {
        HttpClientUtil.SSION_ID = "1099098391@DEFAULT";
        Path fpath=Paths.get("G:\\Desktop\\WORK\\新算法测试\\TestAll");
        //Path fpath = Paths.get("G:\\Desktop\\WORK\\新算法测试\\test");

        System.err.println("==========begin===============");
        Path countCSV = Paths.get("D:\\YT_result_99.csv");
        //创建文件
        if (!Files.exists(countCSV)) {
            try {
                Files.createFile(countCSV);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedWriter writer = Files.newBufferedWriter(countCSV, Charset.forName("gb2312"))) {
            try {
                Files.list(fpath).flatMap(f -> subStream(f)).collect(Collectors.toList()).forEach(f -> {
                    //处理
                    try {
                        writeYTResultToFile(writer, f.toFile());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.err.println("==========end===============");
    }

    private static void writeATResultToFile(BufferedWriter writer, File file) throws Exception {
        JSONObject jsonObject = JSON.parseObject(getSTTopN(file));
        writer.write(file.getAbsolutePath());
        writer.write("\n");
        if ("success".equalsIgnoreCase(jsonObject.getString("result"))) {
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (JSONObject o : jsonArray.toArray(new JSONObject[jsonArray.size()])) {
                writer.write(o.getJSONObject("payload").getString("filename") + "," + o.getDouble("score"));
                writer.write("\n");
            }
        }
        writer.write("\n\n");
    }


    private static void writeYTResultToFile(BufferedWriter writer, File file) throws IOException {
        JSONObject jsonObject = JSON.parseObject(getYTTopN(file));
        writer.write(file.getAbsolutePath());
        writer.write("\n");
        try {
            if ("ok".equalsIgnoreCase(jsonObject.getString("message"))) {
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (JSONObject o : jsonArray.toArray(new JSONObject[jsonArray.size()])) {
                    writer.write(o.getString("name") + "," + o.getDouble("similarity"));
                    writer.write("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer.write("\n");
    }

}
