/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redes;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alangonzalez
 */
public class CheckAndExecute extends Thread {

    @Override
    public void run() {
        Message  m;
        try {
            while (!Main.q.isEmpty() && Main.q.peek().getPid() == Main.pid) {
                synchronized (this) {m= Main.q.remove();} 
                UDPServer.exec(m);
                UDPServer.release();
            }
        } catch (IOException ex) {
            Logger.getLogger(CheckAndExecute.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
