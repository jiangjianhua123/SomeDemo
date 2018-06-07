import org.junit.Test;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author jianghong
 * @Description
 * @create 2018-05-14 17:48
 **/
public class LambdaTest {

    @Test
    public void test01(){
        LambdaTest tester = new LambdaTest();
                // 类型声明
        MathOperation addition = (int a, int b) -> a + b;

        // 不用类型声明
        MathOperation subtraction = (a, b) -> a - b;

        // 大括号中的返回语句
        MathOperation multiplication = (int a, int b) -> {
            return a * b;
        };

        // 没有大括号及返回语句
        MathOperation division = (int a, int b) -> a / b;

        System.out.println("10 + 5 = " + tester.operate(10, 5, addition));
        System.out.println("10 - 5 = " + tester.operate(10, 5, subtraction));
        System.out.println("10 x 5 = " + tester.operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + tester.operate(10, 5, division));
        System.out.println("11 + 2 = " + addition.operation(11,2));


        GreetingService greetingService = (String message)-> System.out.println("send "+message);
        greetingService.sayMessage("jianghong");


        System.out.println("------------------------");
        Set<Integer> integers = IntStream.rangeClosed(1,5).boxed().collect(Collectors.toSet());

        integers.forEach(System.out::print);
        System.out.println("------------------------");
        integers.stream().forEach(System.out::print);

        System.out.println("------------------------");
        List<String> wordList = new ArrayList<>();
        wordList.add("kiekck");
        wordList.add("ew");
        wordList.add("kiekdfggck");
        wordList.add("kiekddwwck");
        wordList.add("ererwxgg");
        wordList.add("hbrtere");
        wordList.add("kiekck");
        wordList.add("ghdfr");
        wordList.add("dfgdfg");
        wordList.add("ghfht");
        wordList.add("dfgert");
        wordList.add("wer234fgr");

        wordList.stream().collect(Collectors.toList());
        ArrayList<String> strings = wordList.stream().collect(() -> new ArrayList<>(),
                (c, e) -> c.add(e),
                (r1, r2) -> r1.addAll(r2));
        strings.forEach(System.out::println);

        wordList.stream().
                map(String::toUpperCase).
                collect(Collectors.toList()).forEach(System.out::println);


        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture(original).thenApply(String::toUpperCase).thenAcceptBoth(
                CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
                (s1, s2) -> result.append(s1 + s2));
        System.out.println("original:"+result);

        Map<Integer, String> map = new HashMap<>();
        map.put(2,"j");
        map.put(5,"b");
        map.put(44,"jc");
        map.put(22,"jd");
        map.put(35,"jx");
        int maxKey = map.entrySet().stream().collect(Collectors.maxBy(Comparator.comparing(Map.Entry::getKey))).get().getKey();

        System.out.println("Maxkey:"+maxKey);

        IntStream.generate(() -> 2).limit(15).forEach(System.out::print);

        Stream<double[]> sqrt = IntStream.rangeClosed(1, 100).boxed() .flatMap(a -> IntStream.rangeClosed(a, 100) .mapToObj(b -> new double[]{a,b,Math.sqrt(a*a + b*b)}) .filter(t -> t[2] % 1 == 0));
        sqrt.limit(10).forEach(t -> System.out.println((int)t[0] + "," + (int)t[1] + "," + (int)t[2]));
    }

    @Test
    public void intermediate(){

        //数组
        String[] strArr = new String[]{"aa","bb","cc"};
        Stream<String> streamArr = Stream.of(strArr);
        Stream<String> streamArr2 = Arrays.stream(strArr);
        //集合
        List<String> list = new ArrayList<>();
        Stream<String> streamList = list.stream();
        Stream<String> streamList2 = list.parallelStream();//并行执行

        //generator 生成无限长度的stream
        Stream.generate(Math::random);
        // iterate 也是生成无限长度的Stream，其元素的生成是重复对给定的种子值调用函数来生成的
        Stream.iterate(1, item -> item + 1);


    }


    interface MathOperation {
        int operation(int a, int b);
        default int addition(int a,int b){
            return a+b;
        }
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation){
        return mathOperation.operation(a, b);
    }

}
