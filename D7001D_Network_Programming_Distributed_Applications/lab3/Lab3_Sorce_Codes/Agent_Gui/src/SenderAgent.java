import jade.core.*;
import jade.core.behaviours.*;
import jade.domain.AMSService;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

import javax.swing.*;

public class SenderAgent extends GuiAgent {

    // ToDo
    // 1. Agent List
    // 2. Status

    private AgentGui gui;
    private SendMessage sm;
    AMSAgentDescription[] agents;
    DefaultListModel listModel;


    protected void setup() {

        gui = new AgentGui(this);

        System.out.println("Hello World! My name is " + getLocalName());

        /** Registration with the DF */
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("SenderAgent");
        sd.setName(getName());
        sd.setOwnership("ExampleReceiversOfJADE");
        sd.addOntologies("SenderAgent");
        dfd.setName(getAID());
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            System.err.println(getLocalName() + " registration with DF unsucceeded. Reason: " + e.getMessage());
            doDelete();
        }

        sm = new SendMessage();
        ReceiveMessage rm = new ReceiveMessage();
        addBehaviour(rm);

    }

    protected void onGuiEvent(GuiEvent ev) {

        if (ev.getType() == 1){
            addBehaviour(sm);
        } else if (ev.getType() == 2){
            getAgentList();
        } else if (ev.getType() == 3){
            listModel.clear();
        }

    }

    protected void getAgentList() {
        System.out.println("Getting Agent List");
        // Getting agents list
        agents = null;
        try {
            SearchConstraints c = new SearchConstraints();// object to search

            // the container exist on the System
            //define infinity result to C
            c.setMaxResults(new Long(-1));

            //putt all agent found on the system to the agents list
            agents = AMSService.search(this, new AMSAgentDescription(), c);


        } catch (Exception e) {
            System.out.println("Problem searching AMS: " + e);
            e.printStackTrace();
        }


        listModel = new DefaultListModel();

        for (int i = 0; i < agents.length; i++) {
            AID agentID = agents[i].getName();
            listModel.addElement(agentID.getName());
            System.out.println(agentID.getName());
        }

         gui.agentList.setModel(listModel);

    }


    public class SendMessage extends OneShotBehaviour {

        public void action() {

            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);;
            String receiverName = gui.receiverBox.getText();
            String messageContent = gui.contentBox.getText();

            if (gui.messageBox.getSelectedItem().toString() == "REQUEST") {
                msg = new ACLMessage(ACLMessage.REQUEST);
            }

            else if (gui.messageBox.getSelectedItem().toString() == "INFORM") {
                msg = new ACLMessage(ACLMessage.INFORM);
            }


            msg.addReceiver(new AID(receiverName, AID.ISLOCALNAME));
            msg.setLanguage("English");
            msg.setContent(messageContent);
            send(msg);

            gui.sendMessagesArea.append(messageContent + " -> " + receiverName + "\n");
            System.out.println("****I Sent Message to::> " + receiverName + "*****" + "\n" +
                    "The Content of My Message is::>" + msg.getContent());
        }
    }

    public class ReceiveMessage extends CyclicBehaviour {

        // Variable to Hold the content of the received Message
        private String Message_Performative;
        private String Message_Content;
        private String SenderName;


        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {

                Message_Performative = msg.getPerformative(msg.getPerformative());
                Message_Content = msg.getContent();
                SenderName = msg.getSender().getLocalName();

                if (Message_Performative == "FAILURE") {
                    System.out.println("Fail......");
                    gui.errorDialog();

                } else {

                    gui.receivedMessagesArea.append(Message_Content + " :From: " + SenderName + " :Performative: " + Message_Performative + "\n");

                    System.out.println(" ****I Received a Message***" + "\n" +
                            "The Sender Name is::>" + SenderName + "\n" +
                            "The Content of the Message is::> " + Message_Content + "\n" +
                            "::: And Performative is::> " + Message_Performative + "\n");
                    System.out.println("ooooooooooooooooooooooooooooooooooooooo");

                }
            }
        }

    }
}


