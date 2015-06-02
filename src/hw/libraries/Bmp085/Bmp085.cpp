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

/** @file Bmp085.cpp
 * @brief BOSH BMP085 pressure sensor control interface implementation.
 * 
 * Implementation of the BOSCH BMP085 pressure sensor interface.
 */

#include "Bmp085.h"

/** @brief Pressure conversion times.
  * 
  * BMP085 pressure conversion times.
  * 
  * Mode / Max time (ms):
  * 0 = ultra low power (4.5)
  * 1 = standard (7.5)
  * 2 = high resolution (13.5)
  * 3 = ultra high resolution (25.5)
  */
// C++ doesn't support array initialization into the class definition.
const short Bmp085::PCT[4] = {5, 8, 14, 26};

Bmp085::Bmp085() {
  
}

long Bmp085::calPressure() {
  long x1, x2, x3, b3, b6;
  unsigned long b4, b7;
  long up;
  long p;
  
  b6 = b5 - 4000;
  
  x1 = (b2 * ((b6 * b6) >> 12)) >> 11;
  x2 = (ac2 * b6) >> 11;
  x3 = x1 + x2;
  if (OSS == 3) {
    b3 = ((int32_t) ac1 * 4 + x3 + 2) << 1;
  } else {
    b3 = ((int32_t) ac1 * 4 + x3 + 2) >> (2 - OSS);
  }
  
  x1 = (ac3 * b6) >> 13;
  x2 = (b1 * ((b6 * b6) >> 12)) >> 16;
  x3 = ((x1 + x2) + 2) >> 2;
  b4 = (ac4 * (uint32_t) (x3 + 32768)) >> 15;
  
  up = getUP();
  b7 = ((uint32_t) up - b3) * (50000 >> OSS);
  if (b7 < 0x80000000) {
    p = (b7 * 2) / b4;
  } else {
    p = (b7 / b4) * 2;
  }
    
  x1 = (p >> 8) * (p >> 8);
  x1 = (x1 * 3038) >> 16;
  x2 = (-7357 * p) >> 16;
  p += ((x1 + x2 + 3791) >> 4);
  
  return p;
}

void Bmp085::getCalParam() {
  byte data [2];
  
  getRegisters(byte(AC1_AD), 2, data);
  ac1 = ((short) data[0] << 8) | ((short) data[1]);
  getRegisters(byte(AC2_AD), 2, data);
  ac2 = ((short) data[0] << 8) | ((short) data[1]);
  getRegisters(byte(AC3_AD), 2, data);
  ac3 = ((short) data[0] << 8) | ((short) data[1]);
  getRegisters(byte(AC4_AD), 2, data);
  ac4 = ((unsigned int) data[0] << 8) | ((unsigned int) data[1]);
  getRegisters(byte(AC5_AD), 2, data);
  ac5 = ((unsigned int) data[0] << 8) | ((unsigned int) data[1]);
  getRegisters(byte(AC6_AD), 2, data);
  ac6 = ((unsigned int) data[0] << 8) | ((unsigned int) data[1]);
  getRegisters(byte(B1_AD), 2, data);
  b1 = ((short) data[0] << 8) | ((short) data[1]);
  getRegisters(byte(B2_AD), 2, data);
  b2 = ((short) data[0] << 8) | ((short) data[1]);
  getRegisters(byte(MB_AD), 2, data);
  mb = ((short) data[0] << 8) | ((short) data[1]);
  getRegisters(byte(MC_AD), 2, data);
  mc = ((short) data[0] << 8) | ((short) data[1]);
  getRegisters(byte(MD_AD), 2, data);
  md = ((short) data[0] << 8) | ((short) data[1]);
}

long Bmp085::getTemperature() {
  long ut;
  long x1, x2;
  long t;
  
  ut = getUT();
  x1 = (((long) ut - ac6) * ac5) >> 15;
  x2 = ((long) mc >> 11) / (x1 + md);
  b5 = x1 + x2;
  t = (b5 + 8) >> 4;

  return t;
}

void Bmp085::getRegisters(byte address, int quantity, byte *data) {
  Wire.beginTransmission(byte(BMP085_AD));
  Wire.write(address);
  Wire.endTransmission();
  
  Wire.requestFrom(byte(BMP085_AD), byte(quantity));
  while(Wire.available() < quantity) {
  }
  for (int i = 0; i < quantity; i++) {
    data[i] = Wire.read();
  }
}

long Bmp085::getUP() {
  byte data [3];
  byte msb, lsb, xlsb;
  long up = 0;
  
  setRegister(byte(PRES_REQ));
  delay(PCT[OSS]);
  getRegisters(byte(READ_AD), 3, data);
  msb = data[0];
  lsb = data[1];
  xlsb = data[2];
  up = (((long) msb << 16) 
	| ((long) lsb << 8) 
	| (long) xlsb)
	>> (8 - OSS);
  
  return up;
}

long Bmp085::getUT() {
  byte data [2];
  byte msb, lsb;
  long ut;
  
  setRegister(TEMP_REQ);
  delay(TCT);
  getRegisters(byte(READ_AD), 2, data);
  msb = data[0];
  lsb = data[1];
  ut = ((long) msb << 8) | ((long) lsb);
  
  return ut;
}

void Bmp085::setDevice() {  
  setI2C();
  getCalParam();
}

void Bmp085::setI2C() {
  Wire.begin();
}

void Bmp085::setRegister(byte value) {
  Wire.beginTransmission(byte(BMP085_AD));
  Wire.write(byte(WRITE_AD));
  Wire.write(value);
  Wire.endTransmission();
}
