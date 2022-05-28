import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.io.PrintWriter;

import java.net.*;

public class Server {
    // This is constructor
    ServerSocket server;

    Socket socket;

    BufferedReader br;

    PrintWriter out;

    public Server() {
        try {

            server = new ServerSocket(8080);

            System.out.println("Server is ready to ecept connection: ");

            System.out.println("Waiting: ");

            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());

            startReading();

            startWriting();
        } catch (Exception e) {

            System.out.println("Connection Closed: ");

        }
    }

    public void startReading() {
        System.out.println("Reading Started: ");
        // This thread is use for read data

        Runnable r1 = () -> {
            try {
                while (true) {

                    String msg = br.readLine();

                    if (msg.equals("exit")) {
                        System.out.println("Client terminated the chat: ");
                        socket.close();
                        System.out.println("Connection Closed :");
                        break;
                    }
                    System.out.println("Client: " + msg);

                }
            } catch (Exception e) {

                System.out.println("Connection Closed ");
            }

        };
        new Thread(r1).start();
    }

    public void startWriting() {
        System.out.println("Writer Started : ");
        // Thread data user lega and than send karega
        Runnable r2 = () -> {
            try {
                while (true && !socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                    if (content.equals("exit")) {
                        socket.close();
                        break;
                    }
                }

            } catch (Exception e) {

                System.out.println("Connection Closed: ");

            }
            System.out.println("Connection Closed :");

        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {

        System.out.println("This is Server Going to start : ");
        new Server();
    }

}