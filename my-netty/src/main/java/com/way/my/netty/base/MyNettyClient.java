package com.way.my.netty.base;

import com.way.my.netty.chat.ChatNettyClientHandler;
import com.way.my.netty.entity.TestMsg;
import com.way.my.netty.subcontract.MyMessageDecoder;
import com.way.my.netty.subcontract.MyMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MyNettyClient {
    public static void main(String[] args) {

        int max = Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        System.out.println("netty: " + max);
//        clientListen();
    }
    public static void clientListen(){
        // 创建客户端事件循环组
        EventLoopGroup clientEventLoopGroup = new NioEventLoopGroup();

        try {
            // 创建客户端启动对象
            Bootstrap clientBootStrap = new Bootstrap();
            // 设置相关参数
            clientBootStrap.group(clientEventLoopGroup) // 设置线程组
                    .channel(NioSocketChannel.class) // 设置客户端通道
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
//                                    .addLast(new MyMessageEncoder())
//                                    .addLast(new DelimiterBasedFrameDecoder(102400, Unpooled.copiedBuffer("_".getBytes())))
                                    .addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    .addLast(new ChatNettyClientHandler());
                        }
                    });
            System.out.println("netty client start ...");
            ChannelFuture channelFuture = clientBootStrap.connect("127.0.0.1", 52167).sync();
            Channel channel = channelFuture.channel();
            Scanner scanner = new Scanner(System.in);
//            while (scanner.hasNext()){
//                String msg = scanner.next();
//                channel.writeAndFlush(msg);
//            }
//            for (int i = 0; i < 1000; i++) {
//                channel.writeAndFlush("Hello everyone!_");
//            }
            channelFuture.channel().closeFuture().sync();
        }catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            clientEventLoopGroup.shutdownGracefully();
        }
    }
}
