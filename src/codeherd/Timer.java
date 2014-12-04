/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codeherd;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BagusThanatos
 */
public class Timer extends Thread{
        Thread g;
        public void setThread(Thread g){
            this.g=g;
        }
        @Override
        public void run(){
            int i=1;
            while (i<=10){
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