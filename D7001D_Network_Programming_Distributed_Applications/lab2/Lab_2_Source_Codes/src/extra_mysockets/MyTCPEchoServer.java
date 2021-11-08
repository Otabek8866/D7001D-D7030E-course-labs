package extra_mysockets;

import java.net.*;	// need this for InetAddress, Socket, ServerSocket 
import java.io.*;	// need this for I/O stuff

public class MyTCPEchoServer {

    public static void main(String[] args) throws IOException {
        ServerSocket sever_socekt = new ServerSocket(8888);

        Socket s = sever_socekt.accept();
        System.out.println("client connected");

        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        String str = bf.readLine();
        System.out.println("Client: " + str);
        
        PrintWriter writer = new PrintWriter(s.getOutputStream());
        writer.println("hello server");
        writer.flush();
    }

}
