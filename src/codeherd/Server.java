/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codeherd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BagusThanatos
 */
public class Server {
    public static int serverPort= 8001;
    public static int serverPortData = 8002;
    public static byte[] DISCONNECT="DISCONNECT".getBytes();
    public static byte[] CONNECT="CONNECT".getBytes();
 
    String sName;
    DatagramSocket socket;
    Socket s2;
    BufferedReader in;
    
    ListenClass lC;
    private ArrayList<InetAddress> clients ;
    public Server () {
        try {
            socket= new DatagramSocket(8001,InetAddress.getByName("0.0.0.0"));
            
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
        lC.start();
    }
    public void stopListening(){
        lC.interrupt();
        socket.close();
    }
    public void sendData(String s){
        clients.stream().forEach((i) -> {
            try {
                s2= new Socket(i, serverPortData);
                PrintWriter p = new PrintWriter(s2.getOutputStream(),true);
                p.println(s);
                s2.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
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
                    if (s.equals("CONNECT")) {
                        clients.add(p.getAddress()); System.out.println("a client is connected");
                    }
                    else if (s.equals("DISCONNECT")) {
                        clients.remove(p.getAddress());
                    }
                    else 
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
    public static void main(String[] a){
        Server s = new Server();
        s.listenToReqs();
        Thread t = new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            sc.nextLine();
            s.stopListening();
        });
        t.start();
    }
}
