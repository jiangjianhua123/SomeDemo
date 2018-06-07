package juc.lock;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author jianghong
 * @Description
 * @create 2018-05-24 20:13
 **/
public class DelayQueueTest {

    public static void main(String[] args) {

        DelayQueue<MyDelayElement> myDelayElements = new DelayQueue<>();

        LocalDateTime startDate = LocalDateTime.now();

        myDelayElements.offer(new MyDelayElement(startDate.plusSeconds(20)));

        myDelayElements.offer(new MyDelayElement(startDate.plusSeconds(40)));

        myDelayElements.offer(new MyDelayElement(startDate.plusSeconds(60)));

        System.err.println(myDelayElements);

        new Thread(()->{
            try {
                while (!myDelayElements.isEmpty()){

                    System.err.println(LocalDateTime.now()+" "+myDelayElements.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


    }



}

class MyDelayElement implements Delayed {

    private LocalDateTime localDateTime;



    public MyDelayElement() {
        super();
    }

    public MyDelayElement(LocalDateTime localDateTime) {
        super();
        this.localDateTime = localDateTime;
    }

    /**
     * Returns the remaining delay associated with this object, in the
     * given time unit.
     *
     * @param unit the time unit
     * @return the remaining delay; zero or negative values indicate
     * that the delay has already elapsed
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return Duration.between(LocalDateTime.now(),localDateTime).toNanos();
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Delayed o) {
        return this.getLocalDateTime().compareTo(((MyDelayElement)o).getLocalDateTime());
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "MyDelayElement{" +
                "localDateTime=" + localDateTime +
                '}';
    }
}
