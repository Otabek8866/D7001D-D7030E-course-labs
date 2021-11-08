package extra_mysockets;

import java.net.*;	// need this for InetAddress, Socket, ServerSocket 
import java.io.*;	// need this for I/O stuff

public class MyTCPClient {

    public static void main(String[] args) throws IOException {
        Socket client_socket = new Socket("localhost", 8888);

        PrintWriter writer = new PrintWriter(client_socket.getOutputStream());
        writer.println("hello client");
        writer.flush();
        InputStreamReader in = new InputStreamReader(client_socket.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        String str = bf.readLine();
        System.out.println("Sever: " + str);

    }

}
