import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.io.PrintWriter;

import java.net.ServerSocket;

import java.net.Socket;

public class Client {

    Socket socket;

    ServerSocket server;

    BufferedReader br;

    PrintWriter out;

    public Client() {
        try {
            System.out.println("Sending Request to Server: ");

            socket = new Socket("127.0.0.1", 8080);

            System.out.println("Connection Done : ");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        } catch (Exception e) {
            System.out.println("Connection Closed: ");

        }
    }

    public void startReading() {
        // This thread is use for read data
        Runnable r1 = () -> {
            System.out.println("Start Reading: ");
            try {
                while (true) {

                    String msg = br.readLine();

                    if (msg.equals("exit")) {

                        System.out.println("Client terminated the chat: ");
                        socket.close();

                        break;
                    }
                    System.out.println("Server: " + msg);

                }
            } catch (Exception e) {

                System.out.println("Connection Closed : ");
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

        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {

        System.out.println("This is Client side : ");
        new Client();
    }
}
