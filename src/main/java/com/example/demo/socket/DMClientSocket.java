package com.example.demo.socket;

import com.example.demo.Util.Constant;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class DMClientSocket {

    public static void main(String[] args) {
        // Lấy host từ biến môi trường (cho Docker), nếu null thì dùng mặc định (Constant)
        String serverHost = System.getenv("SERVER_HOST");
        if (serverHost == null || serverHost.isEmpty()) {
            serverHost = Constant.SOCKET_SERVER;
        }

        System.out.println("Connecting to server at: " + serverHost + ":" + Constant.SOCKET_PORT);

        try (Socket socket = new Socket(serverHost, Constant.SOCKET_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connected to server.");
            String message = "Hello Server!";
            out.println(message);
            System.out.println("Sent: " + message);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = in.readLine();
            System.out.println("Client received message from server : " + response);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
