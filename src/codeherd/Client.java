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

/**
 *
 * @author BagusThanatos
 */
public class Client {
    DatagramSocket out;
    ArrayList<InetAddress> serverIPs;
    Enumeration<NetworkInterface> netInterfaces;
    ArrayList<InetAddress> broadcasts;
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
            
            this.out= new DatagramSocket(4447);
            out.setBroadcast(true);
            serverIPs= new ArrayList();
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void getServers(){
        serverIPs.clear();
        new GetServers(new Timer()).start();    
    }
    public ArrayList<InetAddress> getServerList(){
        return serverIPs;
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
                    p= new DatagramPacket(new byte[256], 256,i , 8001); System.out.println("sending to "+i);
                    out.send(p);
                }
                
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            p= new DatagramPacket(new byte[256], 256);
            while(true){
                try { System.out.println("waiting for replies..");
                    out.receive(p); System.out.println("receiving reply from servers");
                    serverIPs.add(p.getAddress());
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("this");
                }
            }
        }
    }
    public class Timer extends Thread{
        Thread g;
        public void setThread(Thread g){
            this.g=g;
        }
        @Override
        public void run(){
            int i=1;
            while (i<=10){
                try { System.out.println(getServerList());
                    sleep(1000);
                    i++;
                } catch (InterruptedException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
            g.stop();
            out.close();
            
        }
    }
    public static void main(String[] args){
        Client C= new Client();
        C.getServers();
        
    }
}
