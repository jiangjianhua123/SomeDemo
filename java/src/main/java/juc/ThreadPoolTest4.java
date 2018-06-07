package juc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author jianghong
 * @Description
 * @create 2018-05-28 14:52
 **/
public class ThreadPoolTest4 {

    /**
     * 具有返回值的测试线程
      */
    class MyThread implements Callable<String> {
        private String name;
        public MyThread(String name) {
            this.name = name;
        }

        @Override
        public String call() {
            int sleepTime = new Random().nextInt(10000);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 返回给调用者的值
            String str = name + " sleep time:" + sleepTime;
            System.out.println(name + " finished...");

            return str;
        }
    }

    private final int POOL_SIZE = 5;
    private final int TOTAL_TASK = 50;

    /** 方法一，自己写集合来实现获取线程池中任务的返回结果*/
    public void testByQueue() throws Exception {
        // 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
//        BlockingQueue<Future<String>> queue = new LinkedBlockingQueue<>();
        List<Future<String>> queue = new ArrayList<>();

        // 向里面扔任务
        for (int i = 0; i < TOTAL_TASK; i++) {
            Future<String> future = pool.submit(new MyThread("Thread" + i));
            queue.add(future);
        }

        // 检查线程池任务执行结果
        for (int i = 0; i < TOTAL_TASK; i++) {
            System.out.println("method1:" + queue.get(i).get());
        }

        // 关闭线程池
        pool.shutdown();
    }

    /**  方法二，通过CompletionService来实现获取线程池中任务的返回结果*/
    public void testByCompetion() throws Exception {
        // 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        CompletionService<String> cService = new ExecutorCompletionService<String>(pool);

        // 向里面扔任务
        for (int i = 0; i < TOTAL_TASK; i++) {
            cService.submit(new MyThread("Thread" + i));
        }

        // 检查线程池任务执行结果
        for (int i = 0; i < TOTAL_TASK; i++) {
            Future<String> future = cService.take();
            System.err.println("method2:" + future.get());
        }

        // 关闭线程池
        pool.shutdown();
    }

    public static void main(String[] args) throws Exception {
        ThreadPoolTest4 t = new ThreadPoolTest4();
//        t.testByQueue();
        t.testByCompetion();
    }

}
