/**
 * @author jianghong
 * @Description
 * @create 2018-07-10 17:30
 **/
public class SynchronizedTest {

    public static String condition = "1";


    public synchronized String a(){
        return "";
    }

    public synchronized static String c(){
        return "";
    }


    public int b(){
        int a = 1;
        synchronized (this){
            a++;
        }
        return a;
    }


    public  int e(){
        int a = 1;
        synchronized (condition){
            a++;
        }
        return a;
    }

    public static int cal(int a){
        if(a <=0){
            return 0;
        }
        if(a==1){
            return 1;
        }
        return cal(a-1)+cal(a-2);
    }

    public static int calcy(int a){
        int i = 0,j = 1,temp=1;
        for(;a > 1;a--){
           temp = i+j;
           i = j;
           j=temp;
        }
        return temp;
    }


    public static void main(String[] args) {
        System.err.println(calcy(1));
        System.err.println(calcy(2));
        System.err.println(calcy(3));
        System.err.println(calcy(4));
        System.err.println(calcy(5));
        System.err.println(calcy(7));
    }


}
