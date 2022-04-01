package com.way.foundation.today20220402;

public class ArrayDemo {
    private int size;
    private int index;
    private int data[];

    public ArrayDemo(int size){
        this.size = size;
        this.index = 0;
        this.data = new int[size];
    }

    public void add(int loc, int val){
        if (index++ < size){
            for (int i = size -1; i > loc; i--) {
                data[i] = data[i-1];
            }
            data[loc] = val;
        }
        /* 数据扩容 */
    }

    public void add(int val){
        if (index < size){
            data[index] = val;
            index++;
        }
    }

    public void delete(int loc){
        for (int i = loc; i < size; i++) {
            if (i != size -1){
                data[i] = data[i+1];
            }else {
                data[index] = 0;
            }
        }
        index--;
    }

    public void print(){
        for (int i = 0; i < data.length; i++) {
            System.out.println("数组下标为：" + i + "数据为：" + data[i]);
        }
    }
    public static void main(String[] args) {
        ArrayDemo array = new ArrayDemo(10);
        array.add(1);
        array.add(2);
        array.add(4);
        array.add(0, 10);
        array.print();
    }
}
