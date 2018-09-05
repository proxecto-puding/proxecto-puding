#!/bin/bash

rm -rf ~/proxecto-puding
mkdir ~/proxecto-puding
cp gui-0.0.1-SNAPSHOT-jar-with-dependencies.jar ~/proxecto-puding
cp run.sh ~/proxecto-puding
cd ~/proxecto-puding
jar -xf gui-0.0.1-SNAPSHOT-jar-with-dependencies.jar
