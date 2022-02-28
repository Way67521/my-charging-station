package com.way.my.netty.heartbeat;

import com.way.my.netty.chat.ChatNettyServerHandler;
import com.way.my.netty.subcontract.MyMessageDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class HeartBeatNettyServer {

    public static void main(String[] args) {
        serverListen();
    }

    public static void serverListen(){
        // 创建处理连接请求的 boosGroup 和 处理客户业务的 workGroup  ，真正处理客户业务的任务交给 workGroup 来完成
        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup(8);

        // 创建客户端启动对象
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boosGroup , workGroup) // 设置两个线程组
                    .channel(NioServerSocketChannel.class) // 设置服务器通道
                    /**
                     * 初始化服务器队列连接大小，服务段处理客户端请求是顺序处理的，所以同一时刻服务端只能处理一个客户端连接请求
                     * 多个客户端同时请求时，服务端会将不能处理的请求放入队列中等待处理
                     */
                    .option(ChannelOption.SO_BACKLOG , 1024)
                    // 创建通道初始化对象，设置初始化参数
                    .childHandler(new ChannelInitializer<SocketChannel>(){


                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 处理管道中客户端发送的请求
                            socketChannel.pipeline()
                                    .addLast("decoder", new StringDecoder())
                                    .addLast("encoder", new StringEncoder())
                                    .addLast(new IdleStateHandler(3, 0, 0))
                                    .addLast(new HeartBeatNettyServerHandler());
                        }
                    });
            System.out.println("netty server start...");
            ChannelFuture bind = serverBootstrap.bind(52167).sync();
            System.out.println("---");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
