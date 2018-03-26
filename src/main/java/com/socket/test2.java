package com.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class test2 {

    public static void main(String[] args) throws IOException{

        // 1.创建ServerSocket对象
        ServerSocket serverSocket = new ServerSocket(12345);

        while(true){

            // 2.等待客户端连接，阻塞的方法
            final Socket socket = serverSocket.accept();

            Runnable runnable = new Runnable(){
                @Override
                public void run(){
                    try{
                        // 3.使用输入流接受客户端发送的请求
                        DataInputStream dis = new DataInputStream(socket.getInputStream());
                        String cityName = dis.readUTF();
                        System.out.println("接收到客户端发送的请求: " + cityName);

                        Thread.sleep(1000);

                        // 4.根据城市名查询天气
                        String result = "今天天气很热";
                        System.out.println("返回天气信息: " + result);

                        // 5.返回查询结果，使用输出流。
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        dos.writeUTF(result);

                        // 6.关闭流
                        dis.close();
                        dos.close();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            };

            //启动线程
            new Thread(runnable).start();

        }
    }
}
