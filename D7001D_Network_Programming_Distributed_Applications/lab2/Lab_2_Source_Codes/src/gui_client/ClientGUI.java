package gui_client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.*;

public class ClientGUI {

    static JTextField text_in;
    static JFrame frame;
    static JButton button;
    static JTextArea text_out;

    public static void main(String args[]) {
        // create a new frame to store text field and button
        frame = new JFrame("Client_Console");

        // create a textarea to display text
        text_out = new JTextArea(20, 40);
        text_out.setEditable(false);
        JScrollPane scroll = new JScrollPane(text_out);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // create a new button
        button = new JButton("execute");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input, output;
                input = text_in.getText();
                text_in.setText("");
                text_out.setText("");
                try {
                    Process process = Runtime.getRuntime().exec("cmd /c" + input);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    BufferedReader readerError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                    while ((output = reader.readLine()) != null) {
                        System.out.println("The inout stream is " + output);
                        text_out.append(output);
                        text_out.append("\n");
                    }
                    while ((output = readerError.readLine()) != null) {
                        System.out.println(output);
                        text_out.append(output);
                    }

                } catch (IOException error) {
                    error.printStackTrace();
                }

            }
        });

        // create a object of JTextField with 16 columns
        text_in = new JTextField(30);

        // create a panel to add buttons and textfield
        JPanel panel = new JPanel();

        // add buttons and textfield to panel
        panel.add(text_in);
        panel.add(button);
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
