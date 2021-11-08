package sockets;

import java.net.*;	// need this for InetAddress, Socket, ServerSocket 
import java.io.*;	// need this for I/O stuff

public class TCPEchoServer {

    // define a constant used as size of buffer 
    static final int BUFSIZE = 1024;

    static public void main(String args[]) {

//        if (args.length != 1) {
//            throw new IllegalArgumentException("Must specify a port!");
//        }
//        int port = Integer.parseInt(args[0]);
        int port = Integer.parseInt("8888");

        try {
            // Create Server Socket (passive socket) 
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket s = ss.accept();
                System.out.println("New client connected");
                handleClient(s);
            }
        } catch (IOException e) {
            System.out.println("Fatal I/O Error !");
            System.exit(0);
        }

    }

    //this method handles one client
    // declared as throwing IOException - this means it throws 
    // up to the calling method (who must handle it!)
    //try taking out the "throws IOException" and compiling, 
    // the compiler will tell us we need to deal with this!
    static void handleClient(Socket s) throws IOException {

        byte[] buff = new byte[BUFSIZE];

        //print out client's address
        System.out.println("Connection from " + s.getInetAddress().getHostAddress());

        //Set up streams 
        InputStream in = s.getInputStream();

        // writer to client
        PrintWriter writer = new PrintWriter(s.getOutputStream(), true);

        //read/write loop 
        //Modify your code here so that it sends back your name in addition to the echoed symbols
        while (in.read(buff) != -1) {
            String str = new String(buff);
            str = str + " Otabek";
            writer.println(str);
            writer.flush();
            buff = new byte[BUFSIZE];
        }
        System.out.println("Client has left");
        s.close();
    }

}
