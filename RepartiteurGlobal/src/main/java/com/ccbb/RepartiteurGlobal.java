package com.ccbb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by CCA3441 on 09/05/2016.
 */
public class RepartiteurGlobal {
    private static Process process;

    public static void main(String[] args) throws Exception {
        RepartiteurUpdate repartiteurUpdate = new RepartiteurUpdate();
        repartiteurUpdate.addVM();
    }

}
