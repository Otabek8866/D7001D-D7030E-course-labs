package gui_client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.*;

public class GUILoop {

    static JTextField text_in;
    static JFrame frame;
    static JButton button;
    static JTextArea text_out;
    public static Boolean check;
    public static int number;

    public static void main(String args[]) {
        // create a new frame to store text field and button
        frame = new JFrame("Client_Console");
        check = false;
        number = 0;

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread started");
                execute_loop();
                System.out.println("thread finished");
            }
        }).start();

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
                try {
                    String input, output;
                    input = text_in.getText();
                    number = Integer.parseInt(text_in.getText());
                    text_in.setText("");
                    text_out.setText(input);
                    GUILoop.check = true;
//                            Loop l = new Loop(num);
//                            l.run();
                    System.out.println("Button stopped");
                } catch (Exception ex) {
                    System.out.println("Error: " + ex.toString());
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
        frame.getContentPane()
                .add(panel, BorderLayout.CENTER);

        // set the attributes of frame
        frame.setSize(
                500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(
                false);
        frame.setVisible(
                true);
        frame.setLocation(
                400, 200);
        frame.show();
    }

    public static void execute_loop() {
        for (;;) {
            //System.out.println("I am in the function");
            while (GUILoop.check) {
                Loop l = new Loop(number);
                l.run();
                check = false;
                number = 0;
                System.out.println("I run");
            }
        }
    }
}
