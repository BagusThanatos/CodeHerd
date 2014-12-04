/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codeherd;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author BagusThanatos
 */
public class Client {
    public static int clientPort= 4447;
    public static int clientPort2=4448;
    InetAddress serverIP= null;
    DatagramSocket out;
    DatagramSocket in;
    ArrayList<InetAddress> serverIPs;
    ArrayList<String> serverNames;
    Enumeration<NetworkInterface> netInterfaces;
    ArrayList<InetAddress> broadcasts;
    ListenToServer l;
    public Client(){
        try {
            netInterfaces= NetworkInterface.getNetworkInterfaces();
            broadcasts= new ArrayList<>();
            while (netInterfaces.hasMoreElements()){
                NetworkInterface n = netInterfaces.nextElement();
                if (n.isLoopback() || !n.isUp()) continue;
                for (InterfaceAddress iA : n.getInterfaceAddresses()){
                    InetAddress b = iA.getBroadcast();
                    if (b==null) continue;
                    broadcasts.add(b);
                }
            }
            
            this.out= new DatagramSocket(clientPort);
            this.in= new DatagramSocket(clientPort2);
            out.setBroadcast(true);
            serverIPs= new ArrayList();
            serverNames= new ArrayList<>();
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void getServers(){
        serverIPs.clear();
        new GetServers(new Timer()).start();    
    }
    public ArrayList<InetAddress> getServerIPs(){
        return serverIPs;
    }
    public ArrayList<String> getServerNames(){
        return this.serverNames;
    }
    public void setServerIP(InetAddress i){
        this.serverIP=i;
    }
    public void connect(JTextArea j){
        if (serverIP != null) {
            try {
                String s = "CONNECT";
                out.send(new DatagramPacket(s.getBytes(), s.getBytes().length, serverIP, Server.serverPort));
                ListenToServer l = new ListenToServer(j);
                l.start();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void disconnect(){
        if (serverIP != null) {
            try {
                String s = "DISCONNECT";
                out.send(new DatagramPacket(s.getBytes(), s.getBytes().length, serverIP, Server.serverPort));
                if (!l.isInterrupted() && l.isAlive()) l.interrupt();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public class GetServers extends Thread{
        Timer t;
        
        public GetServers(Timer t){
            super();
            this.t=t;
            t.setThread(this);
        }
        @Override
        public void run(){
            t.start();
            DatagramPacket p;
            try {
                for (InetAddress i : broadcasts) {
                    if (i.equals(InetAddress.getByName("0.0.0.0"))) continue;
                    p= new DatagramPacket(new byte[256], 256,i , Server.serverPort); System.out.println("sending to "+i);
                    out.send(p);
                }
                
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            p= new DatagramPacket(new byte[1500], 1500);
            while(true){
                try { System.out.println("waiting for replies..");
                    out.receive(p); System.out.println("receiving reply from servers");
                    serverIPs.add(p.getAddress());
                    serverNames.add(new String (p.getData()));
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("this");
                }
            }
        }
    }
    public class ListenToServer extends Thread{
        JTextArea j;
        
        public ListenToServer(JTextArea j){
            super();
            this.j = j;
        }
        
        @Override
        public void run(){
            
            while (true){
                try {
                    DatagramPacket p= new DatagramPacket(new byte[65535], 65535);
                    in.receive(p);
                    String s= new String(p.getData()).trim();
                    j.setText(s);
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    public static void main(String[] args){
        Client C= new Client();
        C.getServers();
        
    }
}
