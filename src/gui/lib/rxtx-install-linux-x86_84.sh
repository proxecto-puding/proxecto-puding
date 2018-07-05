#!/bin/bash

ZIP_NAME=rxtx-2.1-7-bins-r2
unzip -a $ZIP_NAME.zip
cd ZIP_NAME

mvn install:install-file -DgroupId=gnu.io -DartifactId=rxtx -Dversion=2.1.7 -Dpackaging=jar -Dfile=RXTXcomm.jar


JAVA_HOME=$(find /usr/lib/jvm -name "java*" -type d | head -n 1)
sudo cp RXTXcomm.jar $JAVA_HOME/jre/lib/ext
sudo cp Linux/x86_64-unknown-linux-gnu/librxtxSerial.so $JAVA_HOME/jre/lib/amd64

sudo usermod -a -G uucp $USER
sudo usermod -a -G dialout $USER

cd ..
rm -rf ZIP_NAME