package networks;

import java.io.*;
import java.util.*;
import java.net.*;
public class Client implements Runnable {
    Socket SOCK;
    Scanner Input;
    Scanner SEND = new Scanner(System.in);
    PrintWriter Out;
    
    public Client(Socket s){
        this.SOCK = s;
    }
    public void run(){
        try{
            try{
                Input = new Scanner(SOCK.getInputStream());
                Out = new PrintWriter(SOCK.getOutputStream());
                Out.flush();
                CheckStream();
            }
            finally{
                SOCK.close();
            }
        }
        catch(Exception E){
            System.out.println(E);
        }
    }
    public void CheckStream(){
        while(true){
            RECIEVE();
        }
    }
    public void RECIEVE(){
        if(Input.hasNext()){
            String msg = Input.nextLine();
            if(msg.contains("!@#")){
                String temp = msg.substring(3);
                temp = temp.replace("[", "");
                temp = temp.replace("]", "");
                String[] Users = temp.split(", ");
                
                ClientGUI.L_Online.setListData(Users);
            }
            else{
                ClientGUI.F_Conversation.append(msg+"\n");
            }
        }
        
    }
    public void SEND(String s){
        Out.println(ClientGUI.Name+": "+s);
        Out.flush();
        ClientGUI.F_Message.setText("");
    }
    public void DISCONNECT()throws IOException{
        Out.println(ClientGUI.Name+" has disconnected");
        Out.flush();
        SOCK.close();
        System.exit(0);
    }
}
