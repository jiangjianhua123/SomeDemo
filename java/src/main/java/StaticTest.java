import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

/**
 * @author jianghong
 * @Description
 * @create 2018-06-07 17:58
 **/
public class StaticTest {

    public static void main(String[] args) throws Exception
    {
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
        LocalTime localTime = LocalTime.now();
        LocalTime endLocalTime = LocalTime.of(23,59,59);

        System.err.println(localTime.getHour()*60*60+localTime.getMinute()*60+localTime.getSecond());

        System.err.println(24*60*60-LocalTime.now().getLong(ChronoField.SECOND_OF_DAY));


        System.out.println(localTime.until(endLocalTime,ChronoUnit.HOURS));



    }

}
