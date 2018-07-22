import java.util.concurrent.TimeUnit;

/**
 * @author jianghong
 * @Description
 * @create 2018-07-12 16:30
 **/
public class FalseSharing   implements Runnable
{
    public final static int NUM_THREADS = 4;
    public final static long ITERATIONS = 500L * 1000L * 1000L;
    private final int arrayIndex;

    private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];
    static
    {
        for (int i = 0; i < longs.length; i++)
        {
            longs[i] = new VolatileLong();
        }
    }

    public FalseSharing(final int arrayIndex)
    {
        this.arrayIndex = arrayIndex;
    }

    public static void main(final String[] args) throws Exception
    {

        TimeUnit.SECONDS.sleep(20);
        final long start = System.nanoTime();
        runTest();
        System.out.println("duration = " + (System.nanoTime() - start));
    }

    private static void runTest() throws InterruptedException
    {
        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < threads.length; i++)
        {
            threads[i] = new Thread(new FalseSharing(i));
        }

        for (Thread t : threads)
        {
            t.start();
        }

        for (Thread t : threads)
        {
            t.join();
        }
    }

    @Override
    public void run()
    {
        long i = ITERATIONS + 1;
        while (0 != --i)
        {
            longs[arrayIndex].value = i;
        }
    }

    public static long sumPaddingToPreventOptimisation(final int index)
    {
        VolatileLong v = longs[index];
        return v.p1 + v.p2 + v.p3 + v.p4 + v.p5;
    }

    //jdk7以上使用此方法(jdk7的某个版本oracle对伪共享做了优化)
    public final static class VolatileLong
    {
        public volatile long value = 0L;
        public long p1, p2, p3, p4, p5;
    }

    // jdk7以下使用此方法
//    public final static class VolatileLong
//    {
//        public long p1, p2, p3, p4, p5, p6, p7; // cache line padding
//        public volatile long value = 0L;
//        public long p8, p9, p10, p11, p12, p13, p14; // cache line padding
//
//    }
}