package com.ccbb.repartiteurVm;

/**
 * Created by clement on 06/05/2016.
 */
public class Tache {
    private Object[] params;
    private String result;
    private Thread thread;

    public Thread getThread(Runnable runnable){
        if(thread == null){
            thread = new Thread(runnable);
        }
        return thread;
    }

    public synchronized void waitResult() throws InterruptedException {
        while(result == null){
            this.wait();
        }
    }

    public synchronized void notifyResult(){
        this.notify();
    }
    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
