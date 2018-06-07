package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author jianghong
 * @Description
 * @create 2018-05-25 10:48
 **/
public class SteamTest {

    public static void main(String[] args) {

        Stream.generate(Math::random).forEach(System.out::println);

        //数组
        String[] strArr = new String[]{"aa", "bb", "cc"};
        Stream<String> streamArr = Stream.of(strArr);
        Stream<String> streamArr2 = Arrays.stream(strArr);
        //集合
        List<String> list = new ArrayList<>();
        Stream<String> streamList = list.stream();
        Stream<String> streamList2 = list.parallelStream();//并行执行

        /********** Intermediate **********/
        //filter 过滤操作
        streamArr.filter(str -> str.startsWith("a"));
        //map 遍历和转换操作
        streamArr2.map(String::toLowerCase);
        //flatMap 将流展开
        List<String> list1 = new ArrayList<>();
        list1.add("aa");
        list1.add("bb");
        List<String> list2 = new ArrayList<>();
        list2.add("cc");
        list2.add("dd");
        Stream.of(list1, list2).flatMap(str -> str.stream()).collect(Collectors.toList());
        //limit 提取子流
        streamArr.limit(1);
        //skip 跳过
        streamArr.skip(1);
        //peek 产生相同的流，支持每个元素调用一个函数
        streamArr.peek(s -> System.out.println("item:" + s));
        //distinct 去重
        Stream.of("aa", "bb", "aa").distinct();
        //sorted 排序
        Stream.of("aaa", "bb", "c").sorted(Comparator.comparing(String::length).reversed());
        //parallel 转为并行流,谨慎使用
        streamArr.parallel();


        /********** Terminal **********/
        //forEach
        streamArr.forEach(System.out::println);
        //forEachOrdered 如果希望顺序执行并行流，请使用该方法
        streamArr.parallel().forEachOrdered(System.out::println);
        //toArray 收集到数组中
        streamArr.filter(str -> str.startsWith("a")).toArray(String[]::new);
        //reduce 聚合操作
        streamArr.reduce((str1, str2) -> str1 + str2);
        //collect 收集到List中
        streamArr.collect(Collectors.toList());
        //collect 收集到Set中
        streamArr.collect(Collectors.toSet());
        //min 取最小值？
        IntStream.of(1, 2, 3, 4).min();

        Stream.of(1, 2, 3, 4).min(Integer::compareTo);
        //max 取最大值？
        IntStream.of(1, 2, 3, 4).max();
        Stream.of(1, 2, 3, 4).max(Integer::compareTo);
        //count 计算总量？
        streamArr.count();
        //anyMatch 判断流中是否含有匹配元素
        boolean hasMatch = streamArr.anyMatch(str -> str.startsWith("a"));
        //allMatch 判断流中是否全部匹配
        boolean hasMatch1 = streamArr.allMatch(str -> str.startsWith("a"));
        //noneMatch 判断流中是否全部不匹配
        boolean hasMatch2 = streamArr.noneMatch(str -> str.startsWith("a"));
        //findFirst 找到第一个就返回
        streamArr.filter(str -> str.startsWith("a")).findFirst();
        //findAny 找到任意一个就返回
        streamArr.filter(str -> str.startsWith("a")).findAny();




    }
}
