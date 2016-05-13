#!/bin/bash
rjava -jar /home/fedora/projet/cloudAdapterCCBB/repartiteur/repartiteur-jar-with-dependencies.jar 2003
java -jar /home/fedora/projet/cloudAdapterCCBB/server/server-jar-with-dependencies.jar 8081
java -jar /home/fedora/projet/cloudAdapterCCBB/server/server-jar-with-dependencies.jar 8082
java -jar /home/fedora/projet/cloudAdapterCCBB/server/server-jar-with-dependencies.jar 8083
java -jar /home/fedora/projet/cloudAdapterCCBB/server/server-jar-with-dependencies.jar 8084
java -jar /home/fedora/projet/cloudAdapterCCBB/updateRepartieur/server-jar-with-dependencies.jar localhost 2003 add localhost 8081
java -jar /home/fedora/projet/cloudAdapterCCBB/updateRepartieur/server-jar-with-dependencies.jar localhost 2003 add localhost 8082
java -jar /home/fedora/projet/cloudAdapterCCBB/updateRepartieur/server-jar-with-dependencies.jar localhost 2003 add localhost 8083
java -jar /home/fedora/projet/cloudAdapterCCBB/updateRepartieur/server-jar-with-dependencies.jar localhost 2003 add localhost 8084