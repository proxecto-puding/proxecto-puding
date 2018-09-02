#!/bin/bash

apt-get install openjdk-11-jdk
apt-get install timidity
apt-get install fluid-soundfont-gm

echo "Edit /etc/timidity/timidity.cfg, change source from freepaths to fluid-soundfont-gm"