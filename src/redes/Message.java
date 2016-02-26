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
   private String msg;
   private Integer pid;
   private int parameter = 0;

    public Message(Integer time, String msg, Integer pid) {
        this.time = time;
        this.msg = msg;
        this.pid = pid;
    }

    public int getParameter() {
        return parameter;
    }

    public void setParameter(int parameter) {
        this.parameter = parameter;
    }
    
    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    

    @Override
    public String toString() {
        return time.toString()+"-"+msg+"-"+pid.toString()+"-"+parameter+"-";
    }
   
}
