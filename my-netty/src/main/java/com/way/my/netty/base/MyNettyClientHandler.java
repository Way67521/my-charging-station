package com.way.my.netty.base;

import com.way.my.netty.entity.TestMsg;
import com.way.my.netty.subcontract.MyMessageProtocol;
import com.way.my.netty.util.ProtostuffUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;

public class MyNettyClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        String msg = "Hello server!";
//        ByteBuf byteBuf = Unpooled.copiedBuffer(msg.getBytes(CharsetUtil.UTF_8));
//        ctx.writeAndFlush(byteBuf);

        for (int i = 0; i < 10; i++) {
            MyMessageProtocol myMessageProtocol = new MyMessageProtocol();
            String content = "哈喽，我是诸葛";
            myMessageProtocol.setContent(content.getBytes(CharsetUtil.UTF_8));
            myMessageProtocol.setLength(content.getBytes(CharsetUtil.UTF_8).length);
            ctx.writeAndFlush(myMessageProtocol);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("读取服务端消息的线程："+Thread.currentThread().getName());
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        System.out.println("收到服务端发来的消息："+ ProtostuffUtil.deserializer(bytes, TestMsg.class));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    }
}
