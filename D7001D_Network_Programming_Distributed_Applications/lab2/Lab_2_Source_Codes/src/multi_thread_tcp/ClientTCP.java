package multi_thread_tcp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.net.*;	// need this for InetAddress, Socket, ServerSocket 
import java.io.*;	// need this for I/O stuff
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientTCP {

    static JTextField text_url;
    static JTextField text_in;
    static JFrame frame;
    static JButton button_execute;
    static JTextArea text_out;

    static final int BUFSIZE = 1024;

    public static void main(String args[]) {
        // create a new frame to store text field and button
        frame = new JFrame("Client_1_Console");

        // create a textarea to display text
        text_out = new JTextArea(17, 40);
        text_out.setEditable(true);
        JScrollPane scroll = new JScrollPane(text_out);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // create a new button
        button_execute = new JButton("execute");

        // Button to perform console commands
        button_execute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String url = text_url.getText().trim();
                    //url = "localhost";
                    Socket client_socket = new Socket(url, 8888);

                    InputStreamReader inreader = new InputStreamReader(client_socket.getInputStream());
                    BufferedReader bf = new BufferedReader(inreader);
                    PrintWriter out = new PrintWriter(client_socket.getOutputStream(), true);

                    // Setting up communication streams
                    String input, output;
                    input = text_in.getText();
                    text_out.setText("");
                    //input = "dir";
                    
                    // sending commands to the server
                    out.write(input);
                    out.flush();

                    //printing the server's response
                    while ((output = bf.readLine()) != null){
                        System.out.println(output);
                        text_out.append(output+"\n");
                    }
                    
                    client_socket.close();
                    System.out.println("Client socket is closed");
                    
                } catch (IOException ex) {
                    Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );

        // create a object of JTextField with 16 columns
        text_in = new JTextField(30);
        text_url = new JTextField(35);

        // create a panel to add buttons and textfield
        JPanel panel = new JPanel();

        // add buttons and textfield to panel
        panel.add(new JLabel("URL Address"));
        panel.add(text_url);
        panel.add(new JLabel("Command:"));
        panel.add(text_in);
        panel.add(button_execute);
        panel.add(scroll);
        // add panel to frame
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        // set the attributes of frame
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocation(400, 200);
        frame.show();
    }
}
