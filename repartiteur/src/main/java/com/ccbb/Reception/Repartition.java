package com.ccbb.Reception;

/**
 * Created by clement on 04/05/2016.
 */
public class Repartition {

    public String repartir(String a, int b) throws Exception {
        // make the a regular call
        Object[] params = new Object[]
                {new String(a), new Integer(b)};
        Tache tache = new Tache();
        tache.setParams(params);
        tache.setResult(null);
        Algorithme.addTache(tache);
        tache.waitResult();
        return tache.getResult();
    }


}
