package sockets;

import java.net.*;	// need this for InetAddress, Socket, ServerSocket 
import java.io.*;	// need this for I/O stuff
import java.util.*;

public class UDPClient {

    static final int BUFSIZE = 1024;

    static public void main(String args[]) throws SocketException {

        Scanner scan = new Scanner(System.in);
        int port = 7777;
        byte[] buff = new byte[BUFSIZE];
        DatagramSocket s_client = new DatagramSocket();

        try {
            InetAddress ipadd = InetAddress.getLocalHost();
            DatagramPacket dp_client;

            System.out.print("Enter a text: ");
            String request = scan.nextLine();

            while (!s_client.isClosed()) {
                dp_client = new DatagramPacket(request.getBytes(), request.getBytes().length, ipadd, port);
                s_client.send(dp_client);

                if (request.equals("bye")) {
                    s_client.close();
                    break;
                }

                dp_client = new DatagramPacket(buff, BUFSIZE);
                s_client.receive(dp_client);

                System.out.println("Response from Server:" + new String(buff, "UTF-8"));
                buff = new byte[BUFSIZE];

                System.out.print("Enter a text: ");
                request = scan.nextLine();
            }
        } catch (IOException e) {
            System.out.println("Fatal I/O Error !");
            System.exit(0);
        }
    }

}
