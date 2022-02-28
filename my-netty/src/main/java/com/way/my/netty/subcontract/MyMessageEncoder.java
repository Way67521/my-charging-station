package com.way.my.netty.subcontract;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyMessageEncoder extends MessageToByteEncoder<MyMessageProtocol> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MyMessageProtocol myMessageProtocol, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(myMessageProtocol.getLength());
        byteBuf.writeBytes(myMessageProtocol.getContent());
    }
}
