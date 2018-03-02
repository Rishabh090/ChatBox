/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networks;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.*;
import java.net.*;
public class Server {

    public static ArrayList<Socket> ConnectionList = new ArrayList<Socket>();
    public static ArrayList<String> UserList = new ArrayList<String>();
    
    public static void main(String[] args)throws Exception{
        try{
            final int PORT = 444;
            ServerSocket ServSocket = new ServerSocket(PORT);
            System.out.println("waiting for clients!");
            while(true){
                Socket socket = ServSocket.accept();
                ConnectionList.add(socket);
                System.out.println("Client conected from: "+ socket.getLocalAddress().getHostName());
                AddUser(socket);
                Server_Return server = new Server_Return(socket);
                Thread t = new Thread(server);
                t.start();
            }
        }
        catch(Exception E){
            System.out.println(E);
        }
    }
    public static void AddUser(Socket sock) throws Exception{
        Scanner sc = new Scanner(sock.getInputStream());
        String UserName = sc.nextLine();
        UserList.add(UserName);
        for(int i=1;i<ConnectionList.size();i++){
            Socket Temp = ConnectionList.get(i-1);
            PrintWriter OUT = new PrintWriter(Temp.getOutputStream());
            OUT.println("!@#"+UserList);
            OUT.flush();
        }
    }
    
}
