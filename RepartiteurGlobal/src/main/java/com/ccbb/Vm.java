package com.ccbb;

/**
 * Created by CCA3441 on 11/05/2016.
 */
public class Vm {

    public Vm(){
        id = 0;
        ip = "0";
        port = 2000;
        name = "";
    }
    private int id;
    private String ip;
    private int port;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
