package com.socket;

import java.io.*;
import java.net.Socket;

public class SocketClient {

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 10; i++) {
            send("北京\r\n\r\n");
        }
        /*在发送字符串时之所以在Hello World后加上 “\r\n\r\n”，这是因为HTTP协议头是以“\r\n\r\n”作为结束标志（HTTP协议的详细内容将在以后讲解），
        因此，通过在发送字符串后加入“\r\n\r\n”，可以使服务端程序认为HTTP头已经结束，可以处理了。如果不加“\r\n\r\n”，那么服务端程序将一直等待HTTP头的结束，
        也就是“\r\n\r\n”。如果是这样，服务端程序就不会向客户端发送响应信息，而br.readLine()将因无法读以响应信息面被阻塞，直到连接超时。*/
        send("exit\r\n\r\n");

    }

    public static void send(String message) throws IOException {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        BufferedWriter bufferedWriter=null;
        BufferedReader bufferedReader=null;
        Socket socket =null;
        try {
            socket = new Socket("192.168.0.104", 8080);
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
            bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"),1024);
            bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"),1024);
            bufferedWriter.write(message);
            /*为了提高数据传输的效率，Socket类并没有在每次调用write方法后都进行数据传输，而是将这些要传输的数据写到一个缓冲区里（默认是8192个字节），
            然后通过flush方法将这个缓冲区里的数据一起发送出去，因此，bw.flush();是必须的。*/
            bufferedWriter.flush();
            String s=null;
            StringBuilder response=new StringBuilder();
            while (!(s=bufferedReader.readLine()).equals("")){
                response.append(s);
            }
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }

        }


    }
}
