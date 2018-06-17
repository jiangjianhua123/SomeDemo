import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jianghong
 * @Description
 * @create 2018-06-08 0:59
 **/
public class AnalyseTool {

    public static void main(String[] args) throws Exception{
        Path path = Paths.get("G:\\Desktop\\fx.csv");
        List<Entity> entityList = new ArrayList<>();
        try(BufferedReader reader = Files.newBufferedReader(path)){
            String str = null;
            String[] temp;
            while ((str =reader.readLine()) !=null) {
                temp = str.split("\t");
                entityList.add(new Entity(Double.valueOf(temp[0]),Double.valueOf(temp[1]),Double.valueOf(temp[2])));
            }
        }

        for(double ourTempScore = 95;ourTempScore>87;ourTempScore=ourTempScore-0.5){
            double tt = ourTempScore;
            for(long sttemp = 90;sttemp>80;sttemp--){
                long s = sttemp;
                System.err.print("our:"+ourTempScore+"  st:"+s+"  ");
                System.err.print(entityList.stream().filter(entity -> entity.getOurScore()>tt).count()+"  ");
                System.err.println(entityList.stream().filter(entity -> entity.getOurScore()>tt&&entity.getStScore()>s).count());
            }
        }



    }



}
class Entity{

    private double ourScore;

    private double stScore;

    private double ytScore;

    public Entity(double ourScore, double stScore, double ytScore) {
        this.ourScore = ourScore;
        this.stScore = stScore;
        this.ytScore = ytScore;
    }

    public double getOurScore() {
        return ourScore;
    }

    public void setOurScore(double ourScore) {
        this.ourScore = ourScore;
    }

    public double getStScore() {
        return stScore;
    }

    public void setStScore(double stScore) {
        this.stScore = stScore;
    }

    public double getYtScore() {
        return ytScore;
    }

    public void setYtScore(double ytScore) {
        this.ytScore = ytScore;
    }
}
