#!/bin/bash

# Proxecto Puding
# Copyright (C) 2011 Alejo Pacín <info@proxecto-puding.org>
# 
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.

# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <https://www.gnu.org/licenses/>.

apt-get install openjdk-11-jdk
apt-get install timidity
apt-get install fluid-soundfont-gm

echo "Edit /etc/timidity/timidity.cfg, change source from freepaths to fluid-soundfont-gm"