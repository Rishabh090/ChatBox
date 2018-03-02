package networks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.*;
import java.net.*;
public class Server_Return implements Runnable {
    Socket Sock;
    Scanner Input;
    PrintWriter Out;
    String Message = "";
    public Server_Return(Socket s){
        this.Sock = s;
    }
    public void run(){
        try{
            try{
                Input = new Scanner(Sock.getInputStream());
                Out = new PrintWriter(Sock.getOutputStream());
                while(true){
                    CheckConnection();
                    if(!Input.hasNext()){
                        return;
                    }
                    Message = Input.nextLine();
                    System.out.println("Client :"+Message);
                    for(int i=1;i<Server.ConnectionList.size();i++){
                        Socket s = Server.ConnectionList.get(i-1);
                        PrintWriter Temp_out = new PrintWriter(s.getOutputStream());
                        Temp_out.println(Message);
                        Temp_out.flush();
                        System.out.println(Message);
                    }
                }
            }
            finally{
                Sock.close();
            }
        }
        catch(Exception E){
            System.out.println(E);
        }
        
    }
    void CheckConnection()throws Exception{
        if(!Sock.isConnected()){
            for(int i=1;i<Server.ConnectionList.size();i++){
                if(Server.ConnectionList.get(i)==Sock){
                   Server.ConnectionList.remove(i);
                }
            }
            for(int i=1;i<Server.ConnectionList.size();i++){
                Socket s = Server.ConnectionList.get(i-1);
                PrintWriter Temp_out = new PrintWriter(s.getOutputStream());
                Temp_out.println(s.getLocalAddress().getHostName()+" disconnected");
                Temp_out.flush();
                System.out.println(s.getLocalAddress().getHostName()+" disconnected");
            }
        }
        
    }
}
