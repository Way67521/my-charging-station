package com.way.foundation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoundationApplication {

    public static void main(String[] args) {
//        SpringApplication.run(FoundationApplication.class, args);
        int num = 1;
        boolean result = test01(num);
        String s = test02("20");
        System.out.println(s);
    }

    private static String test02(String projectTasktype) {
        String userId = "";
        switch (projectTasktype) {
            case "10":
                userId = "21";
                break;
            case "20":
                userId = "23";
                break;
            default:
                userId = "24";
        }
        return userId;
    }

    private static boolean test(int num) {
        int sub = 0;
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

    private static boolean test01(int num) {
        return (num & num-1) == 0 ;
    }

}
