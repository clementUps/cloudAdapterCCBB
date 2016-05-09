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
        String s;
        executeProcess("nova boot --flavor m1.small --image FC23"
                + " --nic net-id=c1445469-4640-4c5a-ad86-9c0cb6650cca --security-group default"
                + " --key-name myKeyCCBB privateCCBB");
    }

    public static String executeProcess(String cmd) {
        ProcessBuilder process = new ProcessBuilder("/bin/sh", "-c", cmd);
        System.out.println("début ");
        Process p;
        StringBuilder sb = new StringBuilder();
        try {
            System.out.println("start ");
            p = process.start();
            System.out.println("lancé ");
            try {
                System.out.println("début wait ");
                p.waitFor();
                System.out.println("fin wait ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String ligne;

            while (( ligne = br.readLine()) != null) {
                sb.append(ligne).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

}
