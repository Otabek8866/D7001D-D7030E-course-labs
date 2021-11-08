package sockets;

import java.net.*;// need this for InetAddress, Socket, ServerSocket 
import java.io.*;// need this for I/O stuff


public class UDPEchoServer {

    static final int BUFSIZE = 1024;

    static public void main(String args[]) throws SocketException {

//        if (args.length != 1) {
//            throw new IllegalArgumentException("Must specify a port!");
//        }
//        int port = Integer.parseInt(args[0]);

        byte[] buff = new byte[BUFSIZE];
        int port = Integer.parseInt("7777");
        DatagramSocket s_server = new DatagramSocket(port);
        DatagramPacket dp_server;

        try {
            while (!s_server.isClosed()) {
                dp_server = new DatagramPacket(buff, BUFSIZE);
                s_server.receive(dp_server);
                
                // print out client's address 
                System.out.println("Message from " + dp_server.getAddress().getHostAddress());

                String data = new String(dp_server.getData(), "UTF-8").trim();
                if(data.equals("bye")){
                s_server.close();
                break;
                }
                System.out.println("Client: " + data);
                data = data + " Otabek";
                dp_server = new DatagramPacket(data.getBytes(), data.getBytes().length, dp_server.getAddress(), dp_server.getPort());
                s_server.send(dp_server);
                
                buff = new byte[BUFSIZE];
                //dp_server.setLength(BUFSIZE);// avoid shrinking the packet buffer
            }
        } catch (IOException e) {
            System.out.println("Fatal I/O Error !");
            System.exit(0);

        }

    }
}
