package com.way.foundation.today20220330;

/**
 * 判断一个数是不是2的n次方
 */
public class PowerOfTwo {
    public static void main(String[] args) {
        int num = 1;
        long l01 = System.currentTimeMillis();
        boolean powerOfTwo01 = isPowerOfTwo01(num);
        System.out.println(powerOfTwo01);
        long l02 = System.currentTimeMillis();
        System.out.println("cost02:"+(l02 -l01));
        boolean powerOfTwo02 = isPowerOfTwo02(num);
        System.out.println(powerOfTwo02);
        long l03 = System.currentTimeMillis();
        System.out.println("cost03:"+(l03 -l02));
    }

    private static boolean isPowerOfTwo01(int num) {
        int tem = 0;
        if (num == 0){
            return false;
        }
        if (num == 1){
            return true;
        }
        while (num > 1){
            tem = num % 2;
            if (tem == 0){
                num = num / 2;
            }else {
                return false;
            }
        }
        return true;
    }

    private static boolean isPowerOfTwo02(int num) {
        return (num & num-1) == 0 ;
    }
}
