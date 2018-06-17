package juc;

/**
 * @author jianghong
 * @Description
 * @create 2018-06-14 15:22
 **/
public class FiboSeries {

    public static void main(String[] args) {
        System.err.println(calculateFiboSeriesByCycle(4));

        exchangePositionNum(4,19);


    }

    static void exchangePositionNum(int i,int j){
        System.err.println("origial i = "+i+" , j = "+j);
        i = i^j;
        j = i^j;
        i = i^j;
        System.err.println("exchage result i = "+i+" , j = "+j);
    }

    static int calculateFiboSeriesByCycle(int n){
        int i = 0;
        int j = 1;
        int temp;
        for(;n>1;n--){
           temp = i + j;
           i = j;
           j = temp;
        }
        return j;
    }
}
