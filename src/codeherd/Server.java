/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codeherd;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BagusThanatos
 */
public class Server {
    DatagramSocket socket;
    BufferedReader in;
    ListenClass lC;
    public Server () throws SocketException{
        try {
            socket= new DatagramSocket(8001,InetAddress.getByName("0.0.0.0"));
            System.out.println(socket.getLocalAddress()+""+InetAddress.getLocalHost());
        } catch (UnknownHostException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void listenToReqs(){
        lC= new ListenClass();
        lC.start();
    }
    public void stopListening(){
        lC.stop();
        socket.close();
    }
    protected class ListenClass extends Thread{
        @Override
        public void run(){
            try {
                DatagramPacket p = new DatagramPacket(new byte[256], 256);
                
                System.out.println("Server ip:"+ socket.getInetAddress());
                while (true){
                    System.out.println("Server receiving");
                    socket.receive(p);
                    String s=socket.getInetAddress()+"";
                    socket.send(new DatagramPacket(s.getBytes(),s.getBytes().length,p.getAddress(), p.getPort()));
                    System.out.println("sending reply to"+p.getAddress());
                }
            } catch (SocketException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static void main(String[] a){
        try {
            Server s = new Server();
            s.listenToReqs();
            Thread t = new Thread(() -> {
                Scanner sc = new Scanner(System.in);
                sc.nextLine();
                s.stopListening();
            });
        } catch (SocketException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
