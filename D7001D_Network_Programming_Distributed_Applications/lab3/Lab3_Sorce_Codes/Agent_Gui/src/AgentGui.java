import jade.gui.GuiEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgentGui {
    JComboBox messageBox;
    JTextField receiverBox;
    JTextField contentBox;
    private JButton sendButton;
    private JButton cancelButton;
    JTextArea sendMessagesArea;
    JTextArea receivedMessagesArea;
    private JPanel mainPanel;
    private JLabel messageLabel;
    private JLabel receiverLabel;
    private JLabel contentLabel;
    private JLabel sentMessagesLabel;
    private JLabel receivedMessagesLabel;
    private JButton agentListButton;
    JList agentList;
    private JLabel agentListLabel;
    private SenderAgent myAgent;
    JFrame frame;


    public AgentGui(SenderAgent agent) {

        myAgent = agent;

        frame = new JFrame("AgentGui");
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiEvent ge = new GuiEvent(this, 1);
                myAgent.postGuiEvent(ge);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                receiverBox.setText("");
                contentBox.setText("");
                sendMessagesArea.setText("");
                receivedMessagesArea.setText("");

                GuiEvent ge = new GuiEvent(this, 3);
                myAgent.postGuiEvent(ge);
            }
        });
        agentListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiEvent ge = new GuiEvent(this, 2);
                myAgent.postGuiEvent(ge);

            }
        });
    }

    public void setAgent(SenderAgent a) {
        myAgent = a;
    }

    public void errorDialog(){
        JOptionPane.showMessageDialog(new JFrame(), "Agent Not Found!", "Error!",
                JOptionPane.ERROR_MESSAGE);
    }


    private void createUIComponents() {
        messageBox = new JComboBox();
        messageBox.addItem("REQUEST");
        messageBox.addItem("INFORM");
    }

}
