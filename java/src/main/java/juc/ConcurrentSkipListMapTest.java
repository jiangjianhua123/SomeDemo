package juc;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author jianghong
 * @Description
 * @create 2018-05-25 15:42
 **/
public class ConcurrentSkipListMapTest {

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer,Object> skipListMap = new ConcurrentSkipListMap<>();
        Set<Integer> tempSet = new HashSet<>();
        int temp ;
        for(int i=0;i<200;i++){
            temp = (int)(Math.random()*1000);
            tempSet.add(temp);
            tempSet.stream().sorted().forEach(s->System.out.print(s+" "));
            skipListMap.put(temp, new Object());
            System.out.println();
        }

        System.err.println(skipListMap);

    }
}
