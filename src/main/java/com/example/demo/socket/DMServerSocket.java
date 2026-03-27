package com.example.demo.socket;

import com.example.demo.Util.Constant;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DMServerSocket {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(Constant.SOCKET_PORT)) {
            System.out.println("Server is listening on port: " + Constant.SOCKET_PORT);

            // Chờ client kết nối (lệnh này sẽ dừng chương trình cho đến khi có kết nối)
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected from: " + clientSocket.getInetAddress());

            // Đọc dữ liệu từ client gửi lên
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message = in.readLine();
            System.out.println("Message from Client: " + message);

            // Gửi dữ liệu phản hồi lại cho client
            // autoFlush = true để đảm bảo dữ liệu được gửi đi ngay lập tức
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("Server response hello client! ");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}