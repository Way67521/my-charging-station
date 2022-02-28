package com.way.my.netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    public static void main(String[] args) throws IOException {
        SocketServer.listen();
    }
    public static void listen() throws IOException {
        ServerSocket serverSocket = new ServerSocket(52167);
        while (true){
            System.out.println("等待客户端连接");
            Socket accept = serverSocket.accept();
            System.out.println("客户端连接进来了");
//            handler(accept);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        handler(accept);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private static void handler(Socket accept) throws IOException {
        while (true){
            byte[] bytes = new byte[1024];
            System.out.println("reading"+Thread.currentThread().getName());
            /* 准备接受客户端的数据，阻塞，没有数据读时就阻塞住 */
            int read = accept.getInputStream().read(bytes);
            System.out.println("read accomplished!");
            if (read != -1){
                System.out.println("data:"+new String(bytes, 0 , read));
            }
            accept.getOutputStream().write("hello client".getBytes());
            accept.getOutputStream().flush();
        }
    }
}
