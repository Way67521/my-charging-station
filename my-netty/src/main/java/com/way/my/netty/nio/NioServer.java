package com.way.my.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NioServer {
    public static void main(String[] args) throws IOException {
        accept();
    }

    static List<SocketChannel> socketChannelList = new ArrayList<>();
    public static void accept() throws IOException {
        /** 创建NIO ServerSocketChnnel*/
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(52168));
        serverSocketChannel.configureBlocking(false);
        System.out.println("服务器启动成功");
        while (true){
            /* 非阻塞模式下accept方法不会被阻塞
            * NIO的非阻塞是由操作系统内部实现的，底层调用了linux内核的accept函数*/
            SocketChannel accept = serverSocketChannel.accept();
            if (accept != null){
                System.out.println("连接成功");
                /** 设置SocketChannel为非阻塞*/
                accept.configureBlocking(false);
                socketChannelList.add(accept);
            }

            /** 遍历数组处理连接数据*/
            Iterator<SocketChannel> iterator = socketChannelList.iterator();
            while (iterator.hasNext()) {
                SocketChannel socketChannel = iterator.next();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                /** 非阻塞模式下read方法不会被阻塞*/
                int length = socketChannel.read(byteBuffer);
                if (length > 0) {
                    System.out.println("received data: "+new String(byteBuffer.array()));
                }else if (length == -1){
                    iterator.remove();
                    System.out.println("客户端断开连接");
                }
            }
//            System.out.println("服务器监控");
        }
    }
}
