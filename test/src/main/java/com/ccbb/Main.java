package com.ccbb;

import java.io.*;

/**
 * Created by CCA3441 on 11/05/2016.
 */
public class Main {


    public static void main(String[] args) throws IOException {
        File f = new File("/home/fedora/test.txt");
        OutputStream inputStream = new FileOutputStream(f);
        inputStream.write(("Salut c'est cool").getBytes());
    }
}
