package juc.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jianghong
 * @Description
 * @create 2018-05-25 14:29
 **/
public class OtherUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(OtherUtil.class);

    public static void main(String[] args) {
        byte[] a = float2byte(1.09f);
        byte[] b = float2byte(1f);

        byte[] c = new byte[4];

        LOGGER.info(c.toString());
    }

    public static void getOtherLog(){
        LOGGER.info("OhterUitl");
    }

    /**
     * 浮点转换为字节
     *
     * @param f
     * @return
     */
    public static byte[] float2byte(float f) {

        // 把float转换为byte[]
        int fbit = Float.floatToIntBits(f);

        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (fbit >> (24 - i * 8));
        }

        // 翻转数组
        int len = b.length;
        // 建立一个与源数组元素类型相同的数组
        byte[] dest = new byte[len];
        // 为了防止修改源数组，将源数组拷贝一份副本
        System.arraycopy(b, 0, dest, 0, len);
        byte temp;
        // 将顺位第i个与倒数第i个交换
        for (int i = 0; i < len / 2; ++i) {
            temp = dest[i];
            dest[i] = dest[len - i - 1];
            dest[len - i - 1] = temp;
        }

        return dest;

    }
}
