package juc;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author jianghong
 * @Description
 * @create 2018-05-24 17:16
 **/
public class SemaphoreTest {

    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(5);
        // 创建许可数为3和非公平的公平设置的 Semaphore。
        final Semaphore semp = new Semaphore(3);
        Random random = new Random();
        // 模拟10个客户端访问
        for (int index = 0; index < 10; index++) {
            Runnable run = ()->{
                    try {
                        // 从此信号量获取一个许可
                        semp.acquire();
                        System.out.println(Thread.currentThread().getName());
                        TimeUnit.SECONDS.sleep(random.nextInt(5));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        // 释放信号量。如果没有这条语句，则在控制台只能打印5条记录，之后线程一直阻塞
                        semp.release();
                    }
            };
            exec.execute(run);
        }
        exec.shutdown();
    }
}
