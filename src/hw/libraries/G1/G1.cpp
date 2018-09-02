/* Proxecto Puding (proxecto-puding) - Punteiro Dixital Integramente Galego
 * Advanced wireless digital Galician bagpipes
 * http://proxecto-puding.org, info@proxecto-puding.org
 * Copyright (C) 2012  Alejo Pacín Jul
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
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

/** @file G1.cpp
 * @brief 4D SYSTEMS uDRIVE-uSD-G1 storage device control interface impl.
 * 
 * Implementation of the 4D SYSTEMS uDRIVE-uSD-G1 storage device control interface.
 */

#if (ARDUINO >= 100)
#include <Arduino.h>
#else
#include <WProgram.h>
#endif

#include "G1.h"

G1::G1() {
  
}

boolean G1::copyFile(char *fromFile, char *toFile) {
  String data;
  boolean copied;
  
  data = readFile(fromFile);
  if(data == NULL) {
    return false;
  }
  copied = writeFile(toFile, false, data);
  
  return copied;
}

unsigned long G1::getFileSize(char *name) {
  unsigned long size = 0;
  
  Serial.write(byte(CC_EC));
  Serial.write(byte(RDF_CH));
  Serial.write(byte(RDF_HS));
  Serial.write(name);
  // TODO Test what happens if no file available.
  while (Serial.available() < 4) {
  }
  for (int i = 0; i < 4; i++) {
    size = (size << 8) | (unsigned long) Serial.read();
  }
  Serial.write(byte(CC_NAK));
  while (Serial.available() < 1) {
  }
  // TODO Test if after NAK retuns ACK.
  Serial.read();
  
  return size;
}

boolean G1::initDiskDriveMemoryCard() {
  byte acknowledge;
  
  Serial.write(byte(CC_EC));
  Serial.write(byte(IMD_CH));
  while (Serial.available() < 1) {
  }
  acknowledge = Serial.read();
  
  return (acknowledge == byte(CC_ACK));
}

boolean G1::listDirectory(char *name) {
  byte b;
  String list = String("");
  
  Serial.write(byte(CC_EC));
  Serial.write(byte(LD_CH));
  Serial.write(name);
  while (true) {
    if (Serial.available() > 0) {
      b = Serial.read();
      if (b == byte(CC_ACK)) {
        Serial.println(list);
        return true;
      }
      else if (b == byte(CC_NAK)) {
        return false;
      }
      else {
        list += char(b);
      }
    }
  }
}

boolean G1::moveFile(char *fromFile, char *toFile) {
  boolean copied;
  boolean removed;
  
  copied = copyFile(fromFile, toFile);
  if (!copied) {
    return copied;
  }
  removed = removeFile(fromFile);
  
  return removed;
}

void G1::printDeviceVersionInfo() {
  byte data [5];
  byte siliconRev;
  byte pmmcRev;
  
  Serial.write(byte(DVI_CMD));
  while (Serial.available() < 5) {
  }
  for (int i = 0; i < 5; i++) {
    data[i] = Serial.read();
  }
  siliconRev = data[1];
  pmmcRev = data[2];
  Serial.print("GOLDELOX silicon revision: ");
  Serial.println(siliconRev);
  Serial.print("PmmC firmware revision: ");
  Serial.println(pmmcRev);
}

String G1::readFile(char *name) {
  unsigned long size = 0;
  String data = String("");
  byte acknowledge;
  
  Serial.write(byte(CC_EC));
  Serial.write(byte(RDF_CH));
  Serial.write(byte(RDF_HS));
  Serial.write(name); // Terminator included.
  // TODO Test what happens if no file available.
  while (Serial.available() < 4) {
  }
  for (int i = 0; i < 4; i++) {
    size = (size << 8) | (unsigned long) Serial.read();
  }
  if (size == 0) {
    Serial.write(byte(CC_NAK));
  }
  for (unsigned long i = 0; i < size; i++) {
    Serial.write(byte(CC_ACK));
    while (Serial.available() < int(byte(RDF_HS))) {
    }
    data += char(Serial.read());
  }
  // TODO Test if after NAK returns ACK.
  while (Serial.available() < 1) {
  }
  acknowledge = Serial.read();
  
  if (acknowledge == byte(CC_NAK)) {
    data = "";
  }
  
  return data;
}

boolean G1::removeFile(char *name) {
  byte acknowledge;
  
  Serial.write(byte(CC_EC));
  Serial.write(byte(RMF_CH));
  Serial.write(name);
  while (Serial.available() < 1) {
  }
  acknowledge = Serial.read();
  
  return (acknowledge == byte(CC_ACK));
}

void resetDevice() {
  pinMode(RESET_PIN, OUTPUT);
  digitalWrite(RESET_PIN, LOW);
  // Active Low pulse greater than 2 micro-seconds will reset the module.
  delay(10);
  digitalWrite(RESET_PIN, HIGH);
}

void G1::setDevice() {
  setHostSerial();
  while (!setSerialAutoBaud()) {
  }
  while (!initDiskDriveMemoryCard()) {
  }
}

void G1::setFatProtection(boolean enable) {
  Serial.write(byte(SFP_CMD));
  Serial.write(byte(SFP_MOD));
  if (enable) {
    Serial.write(byte(SFP_VPO));
  }
  else {
    Serial.write(byte(SFP_VPF));
  }
}

void G1::setHostSerial() {
  resetDevice();
  // Allow up to 500ms delay after power-up or reser for the uDRIVE to settle.
  // Within 100ms of powering up, the host should make sure it has its Tx line pulled HIGH.
  delay(1000);
  Serial.begin(SERIAL_VEL);
  while (Serial.available() > 0) {
    Serial.read(); // Flush RX buffer
  }
}

boolean G1::setSerialAutoBaud() {
  byte acknowledge;
  
  Serial.write(byte(SAB_CMD));
  while (Serial.available() < 1) {
  }
  acknowledge = Serial.read();
  
  return (acknowledge == byte(CC_ACK));
}

boolean G1::writeFile(char *name, boolean append, String data) {
  byte options;
  unsigned long size;
  unsigned long s;
  byte acknowledge;
  
  Serial.write(byte(CC_EC));
  Serial.write(byte(WF_CH));
  options = byte(WF_HS) | byte(WF_PM);
  if(append) {
    options |= byte(WF_AMA);
  }
  else {
    options |= byte(WF_AMN);
  }
  Serial.write(byte(options));
  Serial.write(name);
  size = data.length();
  for (int i = 1; i <= 4; i++) {
    Serial.write((byte((size >> 8*(4-i)))));
  }
  while (Serial.available() < 1) {
  }
  acknowledge = Serial.read();
  if (acknowledge == byte(CC_NAK)) {
    return false;
  }
  s = 0;
  while (s < size) {
    Serial.write(data.charAt(s));
    while (Serial.available() < 1) {
    }
    acknowledge = Serial.read();
    if (acknowledge == byte(CC_ACK))
      s++;
  }
  while (Serial.available() < 1) {
  }
  acknowledge = Serial.read();
  
  return (acknowledge == byte(CC_ACK));
}
