package com.way.my.netty.base;

import com.way.my.netty.entity.TestMsg;
import com.way.my.netty.util.ProtostuffUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 自定义 handler 处理客户端的请求
 */
public class MyNettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        TestMsg testMsg = new TestMsg();
//        testMsg.setId(System.currentTimeMillis());
//        testMsg.setTitle("title:" + "test");
//        testMsg.setContent("content:" + "test protostuff");
//        byte[] bytes = ProtostuffUtil.serializer(testMsg);
//        ByteBuf byteBuf = Unpooled.copiedBuffer(bytes);
//        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务端读取线程："+Thread.currentThread().getName());
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("收到客户端消息："+byteBuf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        String msg = "Hello client!";
        ByteBuf byteBuf = Unpooled.copiedBuffer(msg.getBytes(CharsetUtil.UTF_8));
        ctx.writeAndFlush(byteBuf);
    }
}

