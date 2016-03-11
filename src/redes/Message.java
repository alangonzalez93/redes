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
   private Integer time;
   private int state;
   private Integer pid;
   

    public Message(Integer time, Integer pid) {
        this.time = time;
        this.pid = pid;
    }

    public Message(Integer time, Integer pid, int state) {
        this.time = time;
        this.pid = pid;
        this.state = state;
    }
    
    public int getState() {
        return state;
    }

    public void setState(Integer parameter) {
        this.state = parameter;
    }
    
    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    

    @Override
    public String toString() {
        return time.toString()+"-"+state+"-"+pid.toString()+"-";
    }
   
}
