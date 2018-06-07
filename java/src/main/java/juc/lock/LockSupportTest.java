package juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author jianghong
 * @Description
 * @create 2018-05-24 16:57
 **/
public class LockSupportTest {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() ->{
            System.out.println(Thread.currentThread().getName() + "阻塞了");
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(5));
            if (Thread.interrupted()) {
                System.out.println(Thread.currentThread().getName() + "中断了");

            }
            System.out.println(Thread.currentThread().getName() + "运行了");

        });
        t.start();
//        TimeUnit.SECONDS.sleep(2);
//        LockSupport.unpark(t);
    }
}
