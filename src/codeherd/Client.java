/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codeherd;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
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
    public static int clientPort= 0; //choose a random port
    public static int clientPort2=0; //choose a random port
    InetAddress serverIP= null;
    DatagramSocket out;
    Socket in;
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
                this.in= new Socket(serverIP, Server.serverPortData);
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
                in.close();
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
            //BufferedReader r = null;
            DataInputStream r = null;
            try {
                String s;
                r = new DataInputStream((in.getInputStream()));
                //r = new BufferedReader(new InputStreamReader(in.getInputStream())); System.out.println("buffered reader created");
                while ((s = r.readUTF())!=null && in.isConnected()){ System.out.println(s);
                    j.setText(s);
                    System.out.println("showing data from server");
                }
                /*
                        while (true){
                            try {
                                
                                System.out.println("Getting data from server");
                                String a = r.readLine().trim();
                                j.setText(a);
                            } catch (IOException ex) {
                                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                */
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (r!=null) r.close();
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
