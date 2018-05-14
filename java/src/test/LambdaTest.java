
import org.junit.Test;

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
        GreetingService greetingService = (String message)-> System.out.printf("send "+message);
        greetingService.sayMessage("jianghong");
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
