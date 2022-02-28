package com.way.my.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartBeatNettyServerHandler extends SimpleChannelInboundHandler {
    int readIdleTimes = 0;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        String msg = "HeartBeat packet";
        String msg0 = (String) o;
        if (msg.equals(msg0)) {
            channelHandlerContext.writeAndFlush("ok!");
        }else {
            System.out.println("Heartbeat packet failed!");
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
        IdleState state = idleStateEvent.state();
        String idleStateStr = null;
        switch (state){
            case READER_IDLE:
                idleStateStr = "读空闲！";
                readIdleTimes++;
                break;
            case WRITER_IDLE:
                idleStateStr = "写空闲";
                readIdleTimes++;
                break;
            case ALL_IDLE:
                readIdleTimes++;
                idleStateStr = "读写空闲";
                break;
        }

        System.out.println("超时事件："+idleStateStr);
        if (readIdleTimes > 3) {
            System.out.println("[server] 空闲3次，关闭连接释放更多资源");
            ctx.writeAndFlush("idle close!");
            ctx.close();
        }
    }
}
