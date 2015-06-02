/* Proxecto Puding (proxecto-puding) - Punteiro Dixital Integramente Galego
 * Fully Galician Digital Bagpipes
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/** @file Bmp085.ino
 * @brief Bmp085 using example.
 * 
 * Example showing how to use the Bmp085 library.
 */

#include <Wire.h>

#include <Bmp085.h>

/** @brief Serial port connection velocity.
 * 
 */
#define SERIAL_VEL 9600

Bmp085 bmp085;

void setup()
{
  bmp085.setDevice();
  Serial.begin(SERIAL_VEL);
  
}

void loop()
{
  long temperature = bmp085.getTemperature();
  Serial.print(temperature, DEC);
  Serial.print(" ");
  long pressure = bmp085.calPressure();
  Serial.println(pressure, DEC);
  delay(1000);
}
