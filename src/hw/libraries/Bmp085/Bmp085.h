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

/** @file Bmp085.h
 * @brief BOSH BMP085 pressure sensor control interface.
 * 
 * Complete interface to manage the BOSCH BMP085 pressure sensor.
 */

#if (ARDUINO >= 100)
#include <Arduino.h>
#else
#include <WProgram.h>
#endif

/** @brief Allow to communicate with I2C / TWI devices.
 * 
 */
#include <Wire.h>

#ifndef Bmp085_h
#define Bmp085_h

/** @brief BMP085 module I2C address.
 * 
 * From the [Wire] library:
 * 
 * There are both 7- and 8-bit versions of I2C addresses.
 * 7 bits identify the device, and the eighth bit determines if it's being
 * written to or read from. The Wire library uses 7 bit addresses throughout.
 * If you have a datasheet or sample code that uses 8 bit address, you'll want
 * to drop the low bit (i.e. shift the value one bit to the right), yielding an
 * address between 0 and 127.
 * 
 * BMP085 I2C write address: 0xEE
 * BMP085 I2C read address: 0xEF
 * 
 * 0xEE >> 1 == 0xEF >> 1 == 0x77
 * 
 * [Wire]: http://arduino.cc/en/Reference/Wire "Wire"
 */
#define BMP085_AD       0X77

/** @brief Serial port connection velocity.
 * 
 */

/** @addtogroup BMP085RegistersAddresses
 * 
 * BMP085 sensor MSB registers addresses.
 * 
 * Note: LSB = MSB + 1
 * @{
 */
#define AC1_AD  0xAA
#define AC2_AD  0xAC
#define AC3_AD  0xAE
#define AC4_AD  0xB0
#define AC5_AD  0xB2
#define AC6_AD  0xB4
#define B1_AD   0xB6
#define B2_AD   0xB8
#define MB_AD   0xBA
#define MC_AD   0xBC
#define MD_AD   0xBE
/** @}
 */

/** @addtogroup BMP085RWAddresses
 * 
 * BMP085 read and write data registers addresses.
 * @{
 */
#define READ_AD         0xF6
#define WRITE_AD        0xF4
/** @}
 */

/** @brief Oversampling setting.
 * 
 * Resolution mode control parameter.
 * 
 * Mode:
 * 0 = ultra low power
 * 1 = standard
 * 2 = high resolution
 * 3 = ultra high resolution
 */
#define OSS     0

class Bmp085
{
  private:

    /** @brief Temperature conversion time.
     * 
     * BMP085 temperature conversion time.
     * 
     * Mode / Max time (ms):
     * Standar (4.5)
     * 
     */
    static const byte TCT = 5;
    
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
    static const short PCT[4];
    
    /** @brief Temperature reading request value.
     * 
     */
    static const byte TEMP_REQ = 0x2E;

    /** @brief Pressure reading request value.
     * 
     * Oversampling setting included.
     */
    static const byte PRES_REQ = 0x34 + (OSS << 6);

    /** @addtogroup BMP085SensorRegisters
     * 
     * BPM085 sensor registers.
     * 
     * @{
     */
    short ac1;
    short ac2;
    short ac3;
    unsigned int ac4; // Arduino has not unsigned short data type.
    unsigned int ac5;
    unsigned int ac6;
    short b1;
    short b2;
    short mb;
    short mc;
    short md;
    /**
     * @}
     */
    
    /** @brief Value kept between getting temperature and pressure.
     * 
     */
    long b5;
    
    /** @brief Read calibration data.
     * 
     * From the [BOSH BMP085] datasheet:
     * 
     * For calculating temperature and pressure, the calibration data has to be
     * used. These constants can be read out from the BMP085 E2PROM via I2C
     * interface at software initialization.
     * 
     * [BOSH BMP085]: http://www.bosch-sensortec.com/content/language1/
     * downloads/BST-BMP085-DS000-06.pdf "BOSH BMP085"
     */
    void getCalParam();

    /** @brief Read 'quantity' bytes from the BMP085 registers.
     * 
     * @param address MSB register address.
     * @param quantity Number of bytes to read.
     * @param data Variable to store the content of the first 'quantity'.
     * registers.
     */
    void getRegisters(byte address, int quantity, byte *data);

    /** @brief Read uncompensated pressure value.
     * 
     * @return Uncompensated pressure value.
     */
    long getUP();

    /** @brief Read uncompensated temperature value.
     * 
     * @return Uncompensated temperature value.
     */
    long getUT();
    
    /** @brief Setup the I2C communication.
     * 
     * Inicialize the I2C communication via Wire library.
     */
    void setI2C();

    /** @brief Write a 1 byte value into the BMP085 write address.
     * 
     * @param value Content to write into the register.
     */
    void setRegister(byte value);

    /** @brief Setup the Arduino serial port.
     * 
     * Inicialize the Arduino serial port setting the communication velocity.
     */    

  public:
    
    /** @brief Constructor.
     * 
     */
    Bmp085();
  
    /** @brief Calculate true pressure.
     * 
     * @return Pressure in Pa.
     */
    long calPressure();

    /** @brief Calculate true temperature.
     * 
     * Function getUT() must be called first.
     * 
     * @return Temperature in 0.1 ºC.
     */
    long getTemperature();
    
    /** @brief Setup the BMP085 pressure sensor.
     * 
     * Inicialize the communication ports and the configuration parameters.
     */
    void setDevice();
};

#endif
