package sockets;

import java.net.*;	// need this for InetAddress, Socket, ServerSocket 
import java.io.*;	// need this for I/O stuff
import java.util.*;

public class TCPClient {

    static final int BUFSIZE = 1024;

    public static void main(String[] args) throws IOException {

        //byte[] buff = new byte[BUFSIZE];
        Scanner scan = new Scanner(System.in);

        Socket client_socket = new Socket("localhost", 8888);

        InputStreamReader inreader = new InputStreamReader(client_socket.getInputStream());        
        BufferedReader bf = new BufferedReader(inreader);
        PrintWriter out = new PrintWriter(client_socket.getOutputStream(), true);

        String input;
        String response;
        System.out.print("Enter a text: ");
        input = scan.nextLine();

        while (!input.equals("bye")) {
            out.write(input);
            out.flush();
            response = bf.readLine();
            System.out.println("Response from Sever: " + response);
           
            //Try to send another text to Server
            System.out.print("Enter a text: ");
            input = scan.nextLine();
        }
        client_socket.close();
    }
}
