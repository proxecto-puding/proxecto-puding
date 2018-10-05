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

/** @file Mpr121.ino
 * @brief Mpr121 using example.
 * 
 * Example showing how to use the Mpr121 library.
 */

#include <Wire.h>

#include <Mpr121.h>

/** @brief Serial port connection velocity.
 * 
 */
#define SERIAL_VEL 9600

Mpr121 mpr121;

void setup()
{
  mpr121.setDevice();
  Serial.begin(SERIAL_VEL);
}

void loop()
{
  unsigned int inputs = mpr121.getTouchAndProximityInputs();
  Serial.println(inputs, BIN);
  delay(1000);
}
