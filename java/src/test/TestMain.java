import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSONArray;
import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class TestMain {

    @Test
    public void test1() throws IOException {
        String rootFilePath = "F:\\jh\\zt-ztk";
        String dbName = "db_20w";


        File f = new File(rootFilePath);
        if (!f.isDirectory()) {
            return;
        }
        File[] fs = f.listFiles();
        List<String> errorFile = new ArrayList<>();
        MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, "--------------------HV2ymHFg03ehbqgZCaKO6jyH", Charset.defaultCharset());
        int num = fs.length;
        int temnum = num -1;
        for (int a = 0; a < num; a++) {
            if ( a!= temnum && (a + 1) % 36!= 0) {
                multipartEntity.addPart("imageDatas", new FileBody(fs[a]));
                continue;
            }
            multipartEntity.addPart("imageDatas", new FileBody(fs[a]));
            HttpPost request = new HttpPost("http://192.168.3.240:9001/verify/face/synAdd?dbName="+dbName+"&getFeature=0");
            request.setEntity(multipartEntity);
            request.addHeader("Content-Type", "multipart/form-data; boundary=--------------------HV2ymHFg03ehbqgZCaKO6jyH");
            RequestConfig requestConfig = RequestConfig.custom().build();
            request.setConfig(requestConfig);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(request);
            InputStream is = response.getEntity().getContent();
            JSONObject jsonObject = JSONObject.parseObject(IOUtils.toString(is));
            JSONArray jsonArray = jsonObject.getJSONArray("fail");
            List<Message> list = JSONArray.parseArray(jsonObject.getString("fail"), Message.class);
            multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, "--------------------HV2ymHFg03ehbqgZCaKO6jyH", Charset.defaultCharset());
            list.forEach(m -> errorFile.add(m.getName()));
            System.err.println(a);
        }
        errorFile.forEach(s -> System.err.print(s + ","));
    }


}


