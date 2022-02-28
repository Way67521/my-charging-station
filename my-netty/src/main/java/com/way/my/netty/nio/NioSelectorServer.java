package com.way.my.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class NioSelectorServer {
    public static void main(String[] args) throws IOException {
        accept();
    }
    public static void accept() throws IOException {
        /** 创建NIO ServerSocketChnnel*/
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(52167));
        serverSocketChannel.configureBlocking(false);
        // 创建Selector处理Channel，即创建epoll
        Selector selector = Selector.open();

        // 把ServerSocketChannel注册到Selector上，并且监听客户端对服务端的accept连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器启动成功");
        while (true){
            /* 非阻塞模式下accept方法不会被阻塞
             * NIO的非阻塞是由操作系统内部实现的，底层调用了linux内核的accept函数*/
//            SocketChannel accept = serverSocketChannel.accept();
//            if (accept != null){
//                System.out.println("连接成功");
//                /** 设置SocketChannel为非阻塞*/
//                accept.configureBlocking(false);
//                socketChannelList.add(accept);
//            }
            // 阻塞等待事件的发生
            selector.select();

            // 获取selector中注册的全部事件的SelectionKey实例
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> selectionKeysIterator = selectionKeys.iterator();

            /** 遍历数组 处理连接数据*/
//            Iterator<SocketChannel> iterator = socketChannelList.iterator();
            while (selectionKeysIterator.hasNext()) {
//                SocketChannel socketChannel = iterator.next();
//                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//                /** 非阻塞模式下read方法不会被阻塞*/
//                int length = socketChannel.read(byteBuffer);
//                if (length > 0) {
//                    System.out.println("received data: "+new String(byteBuffer.array()));
//                }else if (length == -1){
//                    iterator.remove();
//                    System.out.println("客户端断开连接");
//                }
                SelectionKey selectionKey = selectionKeysIterator.next();
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel accept = channel.accept();
                    accept.configureBlocking(false);
                    accept.register(selector, SelectionKey.OP_READ);
                    System.out.println("客户端连接成功！");
                }
                if (selectionKey.isReadable()){
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//                  /** 非阻塞模式下read方法不会被阻塞*/
                    int length = socketChannel.read(byteBuffer);
                    if (length > 0) {
                        System.out.println("received data: "+new String(byteBuffer.array()));
                    }else if (length == -1){
                        System.out.println("客户端断开连接");
                        socketChannel.close();
                    }
                }
                selectionKeysIterator.remove();
            }
//            System.out.println("服务器监控");
        }
    }
}
