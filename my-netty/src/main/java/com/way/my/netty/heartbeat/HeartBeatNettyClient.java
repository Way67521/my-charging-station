package com.way.my.netty.heartbeat;

import com.way.my.netty.chat.ChatNettyClientHandler;
import com.way.my.netty.subcontract.MyMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Random;
import java.util.Scanner;

public class HeartBeatNettyClient {
    public static void main(String[] args) {
        clientListen();
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
                                    .addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    .addLast(new ChatNettyClientHandler());
                        }
                    });
            System.out.println("netty client start ...");
            ChannelFuture channelFuture = clientBootStrap.connect("127.0.0.1", 52167).sync();
            Channel channel = channelFuture.channel();
            String msg = "HeartBeat packet";
            Random random = new Random();
            while (true){
                int num = random.nextInt(10);
                Thread.sleep(1 * 1000);
                channel.writeAndFlush(msg);
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            clientEventLoopGroup.shutdownGracefully();
        }
    }
}
