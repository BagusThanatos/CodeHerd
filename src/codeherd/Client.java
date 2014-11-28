/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codeherd;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BagusThanatos
 */
public class Client {
    MulticastSocket in;
    DatagramSocket out;
    ArrayList<String> serverIPs;
    public Client() throws IOException{
        this.in= new MulticastSocket(4446);
        this.out= new DatagramSocket(4445);
        out.setBroadcast(true);
        serverIPs= new ArrayList();
    }
    public ArrayList<String> getServers(){
        serverIPs.clear();
        
        return serverIPs;
    }
    public class GetServers extends Thread{
        Timer t;
        
        public GetServers(Timer t){
            super();
            this.t=t;
            t.setGetServers(this);
        }
        @Override
        public void run(){
            t.start();
            DatagramPacket p = new DatagramPacket(new byte[256], 256);
            try {
                out.send(p);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            while(true){
                try {
                    in.receive(p);
                    serverIPs.add(new String(p.getData(),0,p.getLength()));
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    public class Timer extends Thread{
        GetServers g;
        public void setGetServers(GetServers g){
            this.g=g;
        }
        @Override
        public void run(){
            int i=0;
            while (i<=4){
                try {
                    sleep(1000);
                    i++;
                } catch (InterruptedException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        g.interrupt();
        }
    }
    public static void main(){
        try {
            Client C= new Client();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
