/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redes;

/**
 *
 * @author luciano
 */
class Message {
   private int time;
   private int state;
   private int pid;
   

    public Message(int time, int pid) {
        this.time = time;
        this.pid = pid;
    }

    public Message(int time, int pid, int state) {
        this.time = time;
        this.pid = pid;
        this.state = state;
    }
    
    public int getState() {
        return state;
    }

    public void setState(int parameter) {
        this.state = parameter;
    }
    
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    

    @Override
    public String toString() {
        return time+"-"+state+"-"+pid+"-";
    }
   
}
