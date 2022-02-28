package com.way.my.netty.chat;

import com.way.my.netty.entity.TestMsg;
import com.way.my.netty.subcontract.MyMessageProtocol;
import com.way.my.netty.util.ProtostuffUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatNettyServerHandler extends SimpleChannelInboundHandler<MyMessageProtocol> {

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println(channels);
//    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String globalMessages = "[客户端]"+channel.remoteAddress()+"上线了！"+simpleDateFormat.format(new Date()) + "\n" + "_";
        channels.writeAndFlush(globalMessages);
        channels.add(channel);
        System.out.println(globalMessages);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyMessageProtocol msg) throws Exception {
//        Channel channel = channelHandlerContext.channel();
//        for (Channel channel0 : channels) {
//            String msg = "客户端" + channel.remoteAddress()+"发送了消息：" + s + "\n_";
//            if (channel != channel0){
//                channel0.writeAndFlush(msg);
//            }else {
//                channel.writeAndFlush(s);
//            }
//        }

        System.out.println("服务端收到的消息如下");
        System.out.println("长度：" + msg.getLength());
        System.out.println("内容：" + new String(msg.getContent(), CharsetUtil.UTF_8));
    }


}
