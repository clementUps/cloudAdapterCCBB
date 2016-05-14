package com.ccbb.repartiteurVm;

import org.apache.xmlrpc.client.XmlRpcClient;

/**
 * Created by clement on 04/05/2016.
 */
public class Server {
    private XmlRpcClient client;
    private Boolean pret;
    private Integer port;
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public XmlRpcClient getClient() {
        return client;
    }

    public void setClient(XmlRpcClient client) {
        this.client = client;
    }

    public Boolean getPret() {
        return pret;
    }

    public void setPret(Boolean pret) {
        this.pret = pret;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Server) {
            return (this.port == ((Server) o).port && this.ip == ((Server) o).ip);
        }
        return false;
    }


}
