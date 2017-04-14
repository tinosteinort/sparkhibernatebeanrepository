#!/bin/bash
# Prozes im Hintergrund starten, ohne das die Shell blockiert wird
nohup java -Dport= -Dkeystore= -DkeystorePassword= -jar sparkhibernatebeanrepository-1.0-SNAPSHOT-assembly/server-1.0-SNAPSHOT-launcher.jar > log.txt 2>&1 &
