package juc.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @author jianghong
 * @Description
 * @create 2018-05-24 16:54
 **/
public class CyclicBarrierTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CyclicBarrierTest.class);

    /**
     * 参与者数量
     */
    private static int parties = 3;
    /**
     * 创建一个新的 CyclicBarrier，它将在给定数量的参与者（线程）处于等待状态时启动
     */
    private static CyclicBarrier barrier = new CyclicBarrier(parties);

    public static void main(String[] args) throws InterruptedException {
        LOGGER.info(""+TimeUnit.SECONDS.toMillis(1));
        for (int i = 0; i < parties; i++){
            new Thread(new Task()).start();
        }
        Thread.sleep(5000);
        LOGGER.info("getNumberWaiting()：" + barrier.getNumberWaiting());


    }

    static class Task implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " await");
                // 在所有参与者都已经在此 barrier 上调用 await 方法之前，将一直等待。
                barrier.await();
                LOGGER.info(Thread.currentThread().getName() + " continued");
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
