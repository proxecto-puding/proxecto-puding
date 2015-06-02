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

/** @file Mpr121.cpp
 * @brief Freescale MPR121 capacitive sensor control interface implementation.
 * 
 * Implementation of the Freescale MPR121 capacitive sensor interface.
 */

#if (ARDUINO >= 100)
#include <Arduino.h>
#else
#include <WProgram.h>
#endif

#include "Mpr121.h"

unsigned int Mpr121::oldInputs = 0;

Mpr121::Mpr121() {
  
}

void Mpr121::getRegisters(byte address, int quantity, byte *data) { 
  Wire.beginTransmission(byte(MPR121_AD));
  Wire.write(address);
  Wire.endTransmission();
  
  Wire.requestFrom(byte(MPR121_AD), byte(quantity));
  while(Wire.available() < quantity) {
  }
  for (byte i = 0; i < quantity; i++) {
    data[i] = Wire.read();
  }
}

unsigned int Mpr121::getTouchAndProximityInputs() {
  String s;
  byte data [2];
  unsigned int inputs;
  
  if (isIrqEnabled()) {
    getRegisters(byte(EN_TS), 2, data);
    inputs = ((unsigned int) data[1] << 8) | ((unsigned int) data[0]);
  }
  else {
    inputs = oldInputs;
  }
  
  return inputs;
}

boolean Mpr121::isIrqEnabled() {
  boolean enabled;
  
  enabled = !digitalRead(IRQ_PIN);
  
  return enabled;
}

void Mpr121::setCalParam() {  
  // AN3944 - Section A
  setRegister(byte(MHD_R), byte(MHD_RV));
  setRegister(byte(NHD_R), byte(NHD_RV));
  setRegister(byte(NCL_R), byte(NCL_RV));
  setRegister(FDL_R, FDL_RV);

  // AN3944 - Section B
  setRegister(byte(MHD_F), byte(MHD_FV));
  setRegister(byte(NHD_F), byte(NHD_FV));
  setRegister(byte(NCL_F), byte(NCL_FV));
  setRegister(byte(FDL_F), byte(FDL_FV));
  
  // AN3893 - 5.0
  setRegister(byte(MHDPROX_R), byte(MHDPROX_RV));
  setRegister(byte(NHDPROX_R), byte(NHDPROX_RV));
  setRegister(byte(NCLPROX_R), byte(NCLPROX_RV));
  setRegister(byte(FDLPROX_R), byte(FDLPROX_RV));
  
  setRegister(byte(MHDPROX_F), byte(MHDPROX_FV));
  setRegister(byte(NHDPROX_F), byte(NHDPROX_FV));
  setRegister(byte(NCLPROX_F), byte(NCLPROX_FV));
  setRegister(byte(FDLPROX_F), byte(FDLPROX_FV));
  
  setRegister(byte(NHDPROX_T), byte(NHDPROX_TV));
  setRegister(byte(NCLPROX_T), byte(NCLPROX_TV));
  setRegister(byte(FDLPROX_T), byte(FDLPROX_TV));
  
  // AN3944 - Section C
  setRegister(byte(E0_TTH), byte(EN_TTHV));
  setRegister(byte(E0_RTH), byte(EN_RTHV));
  setRegister(byte(E1_TTH), byte(EN_TTHV));
  setRegister(byte(E1_RTH), byte(EN_RTHV));
  setRegister(byte(E2_TTH), byte(EN_TTHV));
  setRegister(byte(E2_RTH), byte(EN_RTHV));
  setRegister(byte(E3_TTH), byte(EN_TTHV));
  setRegister(byte(E3_RTH), byte(EN_RTHV));
  setRegister(byte(E4_TTH), byte(EN_TTHV));
  setRegister(byte(E4_RTH), byte(EN_RTHV));
  setRegister(byte(E5_TTH), byte(EN_TTHV));
  setRegister(byte(E5_RTH), byte(EN_RTHV));
  setRegister(byte(E6_TTH), byte(EN_TTHV));
  setRegister(byte(E6_RTH), byte(EN_RTHV));
  setRegister(byte(E7_TTH), byte(EN_TTHV));
  setRegister(byte(E7_RTH), byte(EN_RTHV));
  setRegister(byte(E8_TTH), byte(EN_TTHV));
  setRegister(byte(E8_RTH), byte(EN_RTHV));
  
  // AN3893 - 4.0
  setRegister(byte(E12_TTHV), byte(E12_TTHV));
  setRegister(byte(E12_RTHV), byte(E12_RTHV));
  
  // AN3944 - Section D
  setRegister(byte(FCR), byte(FCRV));
  
  // AN3944 - Section F
  setRegister(byte(AC_CR0), byte(AC_CR0V));
  // AN3889 - AUTO-CONFIGURATION
  setRegister(byte(AC_USL), byte(AC_USLV));
  setRegister(byte(AC_LSL), byte(AC_LSLV));
  setRegister(byte(AC_TL), byte(AC_TLV));
  
  // AN3944 - Section E
  setRegister(byte(ECR), byte(ECRV)); // This register should always be written last.
}

void Mpr121::setDevice() {
  setI2C();
  setCalParam();
}

void Mpr121::setI2C() {
  pinMode(IRQ_PIN, INPUT);
  digitalWrite(IRQ_PIN, HIGH); // IRQ is low level active.
  Wire.begin();
}

void Mpr121::setRegister(byte address, byte value) {
  Wire.beginTransmission(byte(MPR121_AD));
  Wire.write(address);
  Wire.write(value);
  Wire.endTransmission();
}
