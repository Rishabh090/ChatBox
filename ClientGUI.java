
package networks;

import java.awt.event.ActionEvent;
import java.io.PrintWriter;
import javax.swing.*;
import java.util.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class ClientGUI {
    private static Client ChatClient;
    public static String Name = "~";
    
    public static JFrame MainWindow = new JFrame();
    private static JButton ABOUT_B = new JButton();
    private static JButton CONNECT_B = new JButton();
    private static JButton DISCONNECT_B = new JButton();
    private static JButton SEND_B = new JButton(); 
    private static JLabel Message = new JLabel("Message: ");
    public static JTextField F_Message = new JTextField(20);
    private static JLabel Conversation = new JLabel();
    public static JTextArea F_Conversation = new JTextArea();
    private static JScrollPane S_Conversation = new JScrollPane();
    private static JLabel Online = new JLabel();
    public static JList L_Online = new JList();
    
    public static JFrame LogInWindow = new JFrame();
    public static JTextField UserNameBox = new JTextField(20);
    private static JButton Enter = new JButton("Enter");
    private static JLabel EnterUserName = new JLabel();
    private static JPanel LogIn = new JPanel();
    
    public static void main(String args[]){
        BuildWindow();
        initialize();
    }
    public static void Connect()throws Exception{
        try{

            LogInWindow.setEnabled(false);
            final int PORT = 444;
            final String Host = "localhost";
            Socket Sock = new Socket(Host,PORT);
            System.out.println("Connected to: "+ Host);
            
            ChatClient = new Client(Sock);
            PrintWriter Out = new PrintWriter(Sock.getOutputStream());
            Out.println(Name);
            Out.flush();
            
            Thread t = new Thread(ChatClient);
            t.start();
        }
        catch(Exception E){
            System.out.println(E);
            System.exit(0);
        }
    }
    public static void BuildWindow(){
        MainWindow.setTitle(Name+"'s Chat Box");
        MainWindow.setSize(500, 500);
        MainWindow.setLocation(0, 0);
        MainWindow.setResizable(false);
        ConfigureWindow();
        MainWindow_Action();
        MainWindow.setVisible(true);
    }
    public static void ConfigureWindow(){
        MainWindow.getContentPane().setLayout(null);
        
        SEND_B.setText("Send");
        MainWindow.getContentPane().add(SEND_B);
        SEND_B.setBounds(250, 40, 80, 20);
        
        DISCONNECT_B.setText("Disconnect");
        MainWindow.getContentPane().add(DISCONNECT_B);
        DISCONNECT_B.setBounds(10, 40, 110, 20);
        
        CONNECT_B.setText("Connect");
        MainWindow.getContentPane().add(CONNECT_B);
        CONNECT_B.setBounds(130, 40, 110, 20);
        
        ABOUT_B.setText("About");
        MainWindow.getContentPane().add(ABOUT_B);
        ABOUT_B.setBounds(340, 40, 70, 20);
        
        Message.setText("Message:");
        MainWindow.getContentPane().add(Message);
        Message.setBounds(10, 10, 60, 20);
        
        F_Message.requestFocus();
        MainWindow.getContentPane().add(F_Message);
        F_Message.setBounds(100, 70, 140, 16);
        
        Conversation.setHorizontalAlignment(SwingConstants.CENTER);
        Conversation.setText("Conversation");
        MainWindow.getContentPane().add(Conversation);
        Conversation.setBounds(100, 70, 140, 16);
        
        F_Conversation.setColumns(20);
        F_Conversation.setLineWrap(true);
        F_Conversation.setRows(5);
        F_Conversation.setEditable(false);
        
        S_Conversation.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        S_Conversation.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        S_Conversation.setViewportView(F_Conversation);
        MainWindow.getContentPane().add(S_Conversation);
        S_Conversation.setBounds(10, 90, 330, 190);
        
        Online.setHorizontalAlignment(SwingConstants.CENTER);
        Online.setText("Online");
        MainWindow.getContentPane().add(Online);
        Online.setBounds(350, 70, 130, 16);
        
    }
    public static void BuildLogInWindow(){
        LogInWindow.setTitle("Name:");
        LogInWindow.setSize(400,100);
        LogInWindow.setLocation(250, 200);
        LogInWindow.setResizable(false);
        LogIn = new JPanel();
        LogIn.add(EnterUserName);
        LogIn.add(UserNameBox);
        LogIn.add(Enter);
        LogInWindow.add(LogIn);
        LoginAction();
        LogInWindow.setVisible(true);
    }
    public static void LoginAction(){
        Enter.addActionListener(
                new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ACTION_ENTER_B();
            }
            
        }
        );
    }
    public static void ACTION_ENTER_B(){
        if(!UserNameBox.getText().equals("")){
            try {
                Name = UserNameBox.getText().trim();
                Server.UserList.add(Name);
                MainWindow.setTitle(Name+"'s chat box");
                LogInWindow.setEnabled(false);
                SEND_B.setEnabled(true);
                DISCONNECT_B.setEnabled(true);
                CONNECT_B.setEnabled(false);
                Connect();
            } catch (Exception ex) {
                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static void initialize(){
        SEND_B.setEnabled(false);
        DISCONNECT_B.setEnabled(false);
        CONNECT_B.setEnabled(true);
    }
    public static void MainWindow_Action(){
         SEND_B.addActionListener(
            new java.awt.event.ActionListener(){
                

             @Override
             public void actionPerformed(ActionEvent e) {
                 ACTION_SEND_B(); 
             }

            }
         );
         
         DISCONNECT_B.addActionListener(
            new java.awt.event.ActionListener(){
                

             @Override
             public void actionPerformed(ActionEvent e) {
                  ACTION_DISCONNECT_B();
             }

            }
         );
         
         CONNECT_B.addActionListener(
            new java.awt.event.ActionListener(){
                

             @Override
             public void actionPerformed(ActionEvent e) {
                 BuildLogInWindow();
             }

            }
         );
         
         ABOUT_B.addActionListener(
            new java.awt.event.ActionListener(){
                

             @Override
             public void actionPerformed(ActionEvent e) {
                 BuildLogInWindow();
             }

            }
         );
    }
    public static void ACTION_SEND_B(){
        if(!F_Message.getText().equals("")){
            ChatClient.SEND(F_Message.getText());
            F_Message.requestFocus();
        }
    }
    public static void ACTION_DISCONNECT_B(){
        try{
            ChatClient.DISCONNECT();
        }
        catch(Exception E){
            E.printStackTrace();
        }
    }
}
