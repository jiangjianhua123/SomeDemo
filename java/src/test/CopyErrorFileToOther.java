import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author jianghong
 * @Description
 * @create 2018-06-12 17:56
 **/
public class CopyErrorFileToOther {

    @Test
    public void copyfile(){
        String fileNameStr = "";


        String[] fileNameArray = fileNameStr.split(",");
        String newFilePath = "F:\\jh\\errfile\\";
        String oldFilePath = "F:\\jh\\zt-ztk\\";
        for(String fn:fileNameArray){
            try {
                Files.copy(Paths.get(oldFilePath+fn),Paths.get(newFilePath+fn));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
