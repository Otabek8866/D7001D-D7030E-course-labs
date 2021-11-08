package multi_thread_tcp;

import java.net.*;	// need this for InetAddress, Socket, ServerSocket 
import java.io.*;	// need this for I/O stuff
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.FileHandler;

public class ServerTCP extends Thread {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    static final int BUFSIZE = 1024;
    private Socket s;
    static FileHandler fileHandler = null;

    public ServerTCP(Socket s) {
        this.s = s;
    }

    public static void main(String[] args) {
        try {
            LOGGER.setLevel(Level.INFO);
            fileHandler = new FileHandler("./tcp_server_logs.log");
            LOGGER.addHandler(fileHandler);

            //initialize the server
            ServerSocket sever = new ServerSocket(8888);
            System.out.println("Server is listening on port " + "8888");

            for (;;) {
                Socket client = sever.accept();
                System.out.println("Connection from " + client.getInetAddress().getHostAddress());
                Thread server = new ServerTCP(client);
                server.start();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Exception occur", e);
        }
    }

    public void run() {
        try {
            handleClient(s);
            System.out.println("waiting for another client");
            LOGGER.setLevel(Level.INFO);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Exception occur", e);
        }
    }

    static void handleClient(Socket s) throws IOException {

        byte[] buff = new byte[BUFSIZE];

        //Set up streams 
        InputStream in = s.getInputStream();
        // writer to client
        PrintWriter writer = new PrintWriter(s.getOutputStream(), true);

        //read/write loop 
        while (in.read(buff) != -1) {
            String request = new String(buff).trim();
            String result;
            System.out.println("Request from Client: " + request);
            // Recording the command requested by a user (with its IP address)
            LOGGER.log(Level.INFO, "Recording Client Command: ".format(request, s.getInetAddress()));

            //executing console commands
            Process process = Runtime.getRuntime().exec("cmd /c" + request);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            //BufferedReader readerError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            //responding to client
            while ((result = reader.readLine()) != null) {
                System.out.println(result);
                writer.println(result);
                writer.flush();
            }
            System.out.println("\n");
            s.close();
            buff = new byte[BUFSIZE];
            Thread t1 = currentThread();
            try {
                t1.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
