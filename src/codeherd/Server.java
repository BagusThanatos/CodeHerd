/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codeherd;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BagusThanatos
 */
public class Server {
    public static int serverPort= 8001;
    public static int serverPortData = 8002;
    public static String DISCONNECT="COMMAND: DISCONNECT";
    
 
    String sName;
    DatagramSocket socket;
    ServerSocket s2;
    Socket s3;
    //PrintWriter p;
    DataOutputStream p;
    ListenClass lC;
    private ArrayList<Socket> clients ;
    public Server () {
        try {
            socket= new DatagramSocket(8001,InetAddress.getByName("0.0.0.0"));
            s2= new ServerSocket(serverPortData);
            clients = new ArrayList<>();
            System.out.println(socket.getLocalAddress()+""+InetAddress.getLocalHost());
            
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public Server (String sname){
        this();
        this.sName=sname;
    }
    public void listenToReqs(){
        lC= new ListenClass();
        Thread a = new Thread (() -> {
                try {
                    while (true) {
                        if (!lC.isAlive()) break;
                        clients.add(s2.accept());
                        System.out.println("a client added");
                    }
                } catch (IOException ex) {
                    //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        a.start();
        lC.start();
    }
    public void stopListening(){
        try {
            lC.interrupt();
            socket.close();
            s2.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void sendData(String s){
        for (Socket i : clients) {
            try {
                if (i.isClosed() || !i.isConnected()) {
                    clients.remove(i);
                    continue;
                }
                DataOutputStream p = new DataOutputStream(i.getOutputStream());
                p.writeUTF(s);
                System.out.println("sending text");
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    protected class ListenConnect extends Thread{
       
    }
    protected class ListenClass extends Thread{
        @Override
        public void run(){
            try {
                DatagramPacket p = new DatagramPacket(new byte[65535], 65535);
                
                System.out.println("Server ip:"+ socket.getInetAddress());
                while (!Thread.interrupted()){
                    System.out.println("Server receiving");
                    socket.receive(p);
                    String s = new String(p.getData());
                    s=s.trim();
                    System.out.println(s);
                  
                    socket.send(new DatagramPacket(sName.getBytes(),sName.getBytes().length,p.getAddress(), p.getPort()));
                    System.out.println("sending reply to"+p.getAddress());
                    
                }
            } catch (InterruptedIOException ex){
                Thread.currentThread().interrupt();
            } catch (SocketException ex) {
                //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
    public String getName(){
        return this.sName;
    }
}