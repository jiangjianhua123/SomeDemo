import java.time.Clock;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author jianghong
 * @Description
 * @create 2018-06-07 17:58
 **/
public class StaticTest {

    public static int queue_capacity =20000;

   public static CopyOnWriteArrayList<Features> features = new CopyOnWriteArrayList<Features>();

    //public static ConcurrentLinkedQueue<Features> features = new ConcurrentLinkedQueue<Features>();

    public static void main(String[] args) throws Exception
    {
        TimeUnit.SECONDS.sleep(10);



//        List<Integer> listOfIntegers = IntStream.range(1,200000000).boxed().collect(Collectors.toList());
//        List<Integer> parallelStorage = new ArrayList<>();//Collections.synchronizedList(new ArrayList<>());
//        long starttimes = System.nanoTime();
//        parallelStorage = listOfIntegers
//                .parallelStream()
//                .filter(integer -> integer>0).collect(Collectors.toList());
//        System.out.println("parallelStream: "+(System.nanoTime()-starttimes)/1000);
//        System.out.println(parallelStorage.size());
//
//        starttimes = System.nanoTime();
//        parallelStorage = listOfIntegers.stream().filter(integer -> integer>0).collect(Collectors.toList());
//        System.out.println("stream: "+(System.nanoTime()-starttimes)/1000);
//        System.out.println(parallelStorage.size());

//        System.out.println();
//        System.out.println("Sleep 5 sec");
//        TimeUnit.SECONDS.sleep(5);
//        parallelStorage
//                .stream()
//                .forEachOrdered(e -> System.out.print(e + " "));


        //LocalDateTime localDateTime = LocalDateTime.of()
//        LocalTime localTime = LocalTime.now();
//        LocalTime endLocalTime = LocalTime.of(23,59,59);
//
//        System.err.println(localTime.getHour()*60*60+localTime.getMinute()*60+localTime.getSecond());
//
//        System.err.println(24*60*60-LocalTime.now().getLong(ChronoField.SECOND_OF_DAY));
//
//
//        System.out.println("aaa:"+Clock.systemUTC().millis());
//
//
//        System.err.println("_____________________________________");
//        //System.err.println(Clock.systemUTC().instant().getEpochSecond());
//
//        System.err.println("1.01.01.0001".matches("(\\d+\\.){3}\\d+"));
//        System.err.println("1.01.0190001".matches("(\\d+\\.){3}\\d+"));
//        System.err.println("1.01。01.0001".matches("(\\d+\\.){3}\\d+"));
//        System.err.println("1.01.01.0001".matches("(\\d+\\.){3}\\d+"));
        Features f ;
        System.err.println(Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(10);
        long temp = queue_capacity*2;
        for(int i=0;i<temp;i++){
            f = new Features();
            f.setId(i);
            f.setCreateTime(Clock.systemUTC().millis());
            if(features.size()>queue_capacity){
                features.remove(0);
            }
            features.add(f);
        }
        System.err.println("add is finish");
        System.err.println(features.get(0).getId());
        //System.err.println(features.get(features.size()).getId());
        System.err.println("over");

       int[] data = {1,3,5,10,5,2,345,8,12,99,4};
       //basket(data);
        int a=0,b=1;
        boolean bl = a!=(a=b);
        System.err.println(bl);



    }

    public static void basket(int data[])//data为待排序数组
    {
        int n=data.length;
        int bask[][]=new int[10][n];
        int index[]=new int[10];
        int max=Integer.MIN_VALUE;
        for(int i=0;i<n;i++)
        {
            max=max>(Integer.toString(data[i]).length())?max:(Integer.toString(data[i]).length());
        }
        String str;
        for(int i=max-1;i>=0;i--)
        {
            for(int j=0;j<n;j++)
            {
                str="";
                if(Integer.toString(data[j]).length()<max)
                {
                    for(int k=0;k<max-Integer.toString(data[j]).length();k++) {
                        str += "0";
                    }
                }
                str+=Integer.toString(data[j]);
                bask[str.charAt(i)-'0'][index[str.charAt(i)-'0']++]=data[j];
            }
            int pos=0;
            for(int j=0;j<10;j++)
            {
                for(int k=0;k<index[j];k++)
                {
                    data[pos++]=bask[j][k];
                }
            }
            for(int x=0;x<10;x++){
                index[x]=0;
            }
        }
    }

}
