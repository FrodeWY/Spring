package com.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class SocketThread extends Thread{
    private ServerSocket serverSocket = new ServerSocket(8080);
    private AtomicInteger integer = new AtomicInteger(0);

    public SocketThread() throws IOException {

    }

    @Override
    public void run() {
        super.run();
        InputStream inputStream = null;
        OutputStream outputStream=null;
        Socket socket=null;
        BufferedReader bufferedReader=null;
        BufferedWriter bufferedWriter=null;
        while (true) {
            try {
                socket = serverSocket.accept();
                System.out.println("第" + integer.incrementAndGet() + "次收到请求");
                inputStream = socket.getInputStream();
                bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"),1024);
                outputStream = socket.getOutputStream();
                bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"),1024);
                StringBuilder request=new StringBuilder();
                String s=null;
                while (!(s=bufferedReader.readLine()).equals("")){
                    request.append(s);
                }
                System.out.println("request:" + request+"/");
                if(request.toString().equals("exit")){
                    System.out.println("停止");
                    bufferedWriter.write("Stop\r\n\r\n");
                    bufferedWriter.flush();
                    socket.close();
                    break;
                }
                bufferedWriter.write("首都\r\n\r\n");
                bufferedWriter.flush();
            }  catch ( IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("服务退出");
    }
}
