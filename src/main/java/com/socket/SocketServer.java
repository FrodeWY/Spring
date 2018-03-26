package com.socket;

import java.io.*;


public class SocketServer {
    public static void main(String[] args) throws IOException {
        accept();
    }
    public static void accept() throws IOException {

        SocketThread socketThread=new SocketThread();
        socketThread.start();

    }


}
