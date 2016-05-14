package com.ccbb.repartiteurVm;

/**
 * Created by clement on 06/05/2016.
 */
public class Lock {
    private boolean isLocked = false;

    public synchronized void lock()
            throws InterruptedException{
        while(isLocked){
            wait();
        }
        isLocked = true;
    }

    public synchronized void unlock(){
        isLocked = false;
        notify();
    }
}
