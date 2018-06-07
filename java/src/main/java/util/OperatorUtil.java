package util;

import java.util.LinkedList;
import java.util.Random;

public class OperatorUtil {


    public static float getSimilarScore(final Float[] sourceFeature, final Float[] targetFeature){
        float score = 0.0F;
        for (int i = 0; i < 512; i += 4) {
            score += sourceFeature[i] * targetFeature[i] + sourceFeature[i + 1] * targetFeature[i + 1]
                    + sourceFeature[i + 2] * targetFeature[i + 2] + sourceFeature[i + 3] * targetFeature[i + 3];
        }
        return score;
    }

    /**
     * 字节转换为浮点
     *
     * @param b     字节（至少4个字节）
     * @param index 开始位置
     * @return
     */
    public static float byte2float(byte[] b, int index) {
        int l;
        l = b[index + 0];
        l &= 0xff;
        l |= ((long) b[index + 1] << 8);
        l &= 0xffff;
        l |= ((long) b[index + 2] << 16);
        l &= 0xffffff;
        l |= ((long) b[index + 3] << 24);
        return Float.intBitsToFloat(l);
    }


    public static void main(String[] arg){
//        Scanner sc=new Scanner(System.in);
//        int cycle = Integer.parseInt(sc.next());
//        int count = Integer.parseInt(sc.next());
//        System.err.println("计算总数："+cycle*count);
//        Float[] sourceFeature = IntStream.rangeClosed(1, 512).mapToObj(e->(float)e).toArray(Float[]::new);
//        Float[]  targetFeature = IntStream.rangeClosed(1, 512).mapToObj(e->(float)e).toArray(Float[]::new);
//
//
//
//        // 创建倒计时闩并指定倒计时次数为2
//        CountDownLatch latch = new CountDownLatch(count);
//        ExecutorService service = new ThreadPoolExecutor(50,100,0,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>(1024),Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
//
//        long start = System.nanoTime();
//        for(int m=0;m<count;m++) {
//            service.submit(() -> {
//                for (int i = 0; i < cycle; i++) {
//                    OperatorUtil.getSimilarScore(sourceFeature, targetFeature);
//                }
//                latch.countDown();
//            });
//        }
//        try {
//            latch.await();  // 等待倒计时闩减到0
//            System.err.println(System.nanoTime()-start);
//            System.exit(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        LinkedList<Float[]> tempList = new LinkedList<>();
//        for(int i=0;i<10000000;i++){
//            tempList.add(IntStream.rangeClosed(1, 512).mapToObj(e->(float)e).toArray(Float[]::new));
//        }
        Random ra =new Random();
        for(int i=0;i<512;i++){
            if(ra.nextBoolean()){
                System.err.println(ra.nextFloat());
            }else{
                System.err.println(-ra.nextFloat());
            }

        }


//        tempList.parallelStream().map().collect(Collectors.toList())



    }




}

