package com.ccbb;

import java.io.*;

/**
 * Created by CCA3441 on 11/05/2016.
 */
public class Main {


    public static void main(String[] args) throws IOException {
        File f = new File("/home/fedora/text.txt");
        int cpt = 0;
        while(f.exists()) {
            File file;
            if(cpt < 10){
                file = new File("/home/fedora/tempo"+cpt+".txt");
                cpt ++;
            } else {
                cpt --;
                file = new File("/home/fedora/tempo"+cpt+".txt");
                file.delete();
            }
            f = new File("/home/fedora/text.txt");
        }
    }
}
