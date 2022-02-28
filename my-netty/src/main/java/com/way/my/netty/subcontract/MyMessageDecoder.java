package com.way.my.netty.subcontract;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyMessageDecoder extends ByteToMessageDecoder {

    protected Integer length = 0;
    // Netty 的学习记录。

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println(byteBuf);
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        length = byteBuf.readInt();
        if (byteBuf.readableBytes() < length){
            return;
        }
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        // 组装消息对象，并传递到下一个handler
        MyMessageProtocol myMessageProtocol = new MyMessageProtocol();
        myMessageProtocol.setLength(length);
        myMessageProtocol.setContent(bytes);
        list.add(myMessageProtocol);
        length = 0;
    }
}
