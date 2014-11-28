/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codeherd;

import java.io.IOException;
import java.net.MulticastSocket;

/**
 *
 * @author BagusThanatos
 */
public class Client {
    MulticastSocket in;
    
    public Client() throws IOException{
        this.in= new MulticastSocket(4446);
    }
    
    public class GetServers extends Thread{
        @Override
        public void run(){
            
        }
    }
}
