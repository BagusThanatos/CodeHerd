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
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BagusThanatos
 */
public class ServerThread {
    DatagramSocket socket;
    BufferedReader in;
    public ServerThread () throws SocketException{
        socket= new DatagramSocket(4445);
        
    }
    
    protected class ListenClass extends Thread{
        @Override
        public void run(){
            DatagramPacket p = new DatagramPacket(new byte[256], 256);
            while (true){
                try {
                    socket.receive(p);
                    String s=socket.getInetAddress()+","+socket.getPort();
                    socket.send(new DatagramPacket(s.getBytes(),s.getBytes().length));
                } catch (IOException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
