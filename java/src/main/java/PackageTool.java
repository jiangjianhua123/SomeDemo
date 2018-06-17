import java.util.stream.IntStream;

/**
 * @author jianghong
 * @Description
 * @create 2018-06-10 15:44
 **/
public class PackageTool {

    public static void main(String[] args) {
        System.err.println(0%40);

        IntStream.range(1,50).forEach(value -> System.err.println(value & 0x5));
//        Path path = Paths.get("D:\\2018_06_08\\08");
////        Path path = Paths.get("F:\\08\\08");
//        File file = path.toFile();
//        File[] allPicFile = file.listFiles();
//        int i = 0;
//        int j = 1;
//        String root = "D:\\jh2\\part_";
//        String pathnew = "";
//        File   dirFile;
//        File   tempFile;
//        for(File source:allPicFile ){
//            if(i==0){
//                pathnew = root+j;
//                dirFile = new File(pathnew);
//                dirFile.mkdirs();
//            }
//            source.renameTo(new File(pathnew+"\\"+source.getName()));
//            if(++i==20000){
//                i = 0;
//                j++;
//            }
//        }

    }





}
