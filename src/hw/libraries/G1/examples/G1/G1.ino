/*
 * Proxecto Puding
 * Copyright (C) 2011 Alejo Pacín <info@proxecto-puding.org>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

/** @file G1.ino
 * @brief G1 using example.
 * 
 * Example showing how to use the G1 library.
 */

#include <Wire.h>

#include <G1.h>

/** @brief Serial port connection velocity.
 * 
 */
#define SERIAL_VEL 9600

G1 g1;

void setup()
{
  g1.setDevice();
}

void loop()
{
  char *directoryName = ".";
  char *fileName = "test.txt";
  boolean append = false;
  String data = "test";
  char *newFileName = "moved.txt";
  
  g1.printDeviceVersionInfo();
  g1.listDirectory(directoryName);
  g1.writeFile(fileName, append, data);
  g1.moveFile(fileName, newFileName);
  g1.removeFile(newFileName);
  delay(10000);
}
