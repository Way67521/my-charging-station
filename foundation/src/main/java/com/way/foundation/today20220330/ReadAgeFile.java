package com.way.foundation.today20220330;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 给你一个文件里面包含了全国人民的年龄数据，现在要你统计每一个年龄有多少人？(每一个年龄指的是每一个时间标度比如92、93、94)
 * 给定机器为每台+2CPU+2G内存，不得使用现成的容器，比如map
 * 在以上情况下你该如何以最高效的方式来解决这个问题
 * 文件大小有5.7GB
 */
public class ReadAgeFile {
    public static void main(String[] args) {
//        writeAge2File(1400000000);
        String fileName = "C:\\Users\\chinasoft\\Documents\\allAgeFile\\age.txt";
        List<String> test = new ArrayList<>();
        test.add("1");
        test.add("2");
        test.add("3");
        test.add(2, "5");
        System.out.println(test.get(2));
//        int[] ages = readFileAndCal(fileName);
//        for (int i = 0; i < ages.length; i++) {
//            System.out.println("年龄为：" + i + "的个数为：" + ages[i]);
//        }
    }

    /**
     * 将文件读出，统计每个年龄的人数
     * @param fileName
     * @return
     */
    private static int[] readFileAndCal(String fileName) {
        int[] ageArray = new int[180];
        int total = 0;
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(fileName), "utf-8");
            BufferedReader br = new BufferedReader(inputStreamReader);
            String str = "";
            long startTime = System.currentTimeMillis();
            /* 按行读取，读到具体的值后将数组对应下标所处的值加一*/
            while ((str = br.readLine()) != null) {
                int read = Integer.valueOf(str);
                ageArray[read]++; // 将年龄为 i 对应数组中的值加 1
                total++;
            }
            long endTime = System.currentTimeMillis();
            System.out.println("总共耗时："+(endTime - startTime));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("全国总人口："+total);
        return ageArray;
    }

    /**
     * 生成一个包含年龄的文件
     * @param index
     */
    public static void writeAge2File(int index) {
        String fileName = "C:\\Users\\chinasoft\\Documents\\allAgeFile\\age.txt";
        FileWriter fw = null;
        try {
            fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            Random random = new Random();
            for (int i = 0; i < index; i++) {
                int age = random.nextInt(180);
                bw.write(String.valueOf(age)+"\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
