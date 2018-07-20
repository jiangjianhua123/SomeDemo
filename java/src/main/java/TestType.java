import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author jianghong
 * @Description
 * @create 2018-07-19 10:34
 **/
public class TestType {

    static class OOMObject{

        double temp;
    }

    public static void main(String[] args) throws InterruptedException {
        List<OOMObject> list = new ArrayList<>();
        TimeUnit.SECONDS.sleep(10);
        while (true){
            list.add(new OOMObject());
        }

    }
}
