package com.way.my.netty.subcontract;

/**
 * 自定义消息协议
 */
public class MyMessageProtocol {
    // 一次发送消息长度
    private Integer length;
    // 一次发送消息的内容
    private byte[] content;

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
