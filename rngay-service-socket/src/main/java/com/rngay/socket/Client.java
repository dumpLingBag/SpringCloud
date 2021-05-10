package com.rngay.socket;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 52333);
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter pw=new PrintWriter(outputStream);
        //输入流
        InputStream is=socket.getInputStream();
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        //3.利用流按照一定的操作，对socket进行读写操作
        String info="用户名：Tom,用户密码：123456";
        pw.write(info);
        pw.flush();
        socket.shutdownOutput();
    }

}
