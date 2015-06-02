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

/** @file Mpr121.h
 * @brief Freescale MPR121 capacitive sensor control interface.
 * 
 * Complete interface to manage the Freescale MPR121 capacitive sensor.
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

#ifndef Mpr121_h
#define Mpr121_h

/** @brief MPR121 module I2C address.
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
 * MPR121 I2C write address: 0xB4
 * MPR121 I2C read address: 0xB5
 * 
 * 0xB4 >> 1 == 0xB5 >> 1 == 0x5A
 * 
 * [Wire]: http://arduino.cc/en/Reference/Wire "Wire"
 */
#define MPR121_AD       0X5A

/** @brief IRQ pin.
 * 
 * The Arduino board pin where you have to connect the MPR121 IRQ pin.
 */
#define IRQ_PIN 2

/** @brief Electrodes touch status LSB register address.
 *
 * See the [Freescale MPR121] datasheet (Table 2) for more info.
 * 
 * [Freescale MPR121]: http://cache.freescale.com/files/sensors/doc/data_sheet/
 * MPR121.pdf "Freescale MPR121"
 * 
 * Note: MSB = LSB + 1.
 */
#define EN_TS   0x00

/** @addtogroup MPR121ConfigurationRegisterAddresses
 * 
 * MPR121 sensor configuration register addresses.
 * 
 * See the [Freescale MPR121] datasheet (Table 2) for more info.
 * 
 * [Freescale MPR121]: http://cache.freescale.com/files/sensors/doc/data_sheet/
 * MPR121.pdf "Freescale MPR121"
 * @{
 */
#define MHD_R   0x2B
#define NHD_R   0x2C
#define NCL_R   0x2D
#define FDL_R   0x2E

#define MHD_F   0x2F
#define NHD_F   0x30
#define NCL_F   0x31
#define FDL_F   0x32

#define MHDPROX_R       0x36
#define	NHDPROX_R       0x37
#define NCLPROX_R       0x38
#define FDLPROX_R       0x39

#define MHDPROX_F       0x3A
#define	NHDPROX_F       0x3B
#define NCLPROX_F       0x3C
#define FDLPROX_F       0x3D

#define NHDPROX_T       0x3E
#define NCLPROX_T       0x3F
#define FDLPROX_T       0x40

#define E0_TTH  0x41
#define E0_RTH  0x42
#define E1_TTH  0x43
#define E1_RTH  0x44
#define E2_TTH  0x45
#define E2_RTH  0x46
#define E3_TTH  0x47
#define E3_RTH  0x48
#define E4_TTH  0x49
#define E4_RTH  0x4A
#define E5_TTH  0x4B
#define E5_RTH  0x4C
#define E6_TTH  0x4D
#define E6_RTH  0x4E
#define E7_TTH  0x4F
#define E7_RTH  0x50
#define E8_TTH  0x51
#define E8_RTH  0x52
#define E9_TTH  0x53
#define E9_RTH  0x54

#define E12_TTH 0x59
#define E12_RTH 0x5A

#define FCR     0x5D
#define ECR     0x5E

#define AC_CR0  0x7B
#define AC_USL  0x7D
#define AC_LSL  0x7E
#define AC_TL   0x7F
/** @}
 */

/** @addtogroup MPR121ConfigurationRegisterValues
 * 
 * MPR121 sensor configuration register values.
 * 
 * See the [Freescale MPR121] datasheet (Table 2) for more info.
 * 
 * Also, see the following application notes for even more info:
 * [AN3994]: Sections A, B, C, D, E and F.
 * [AN3893]: Sections 4.0 and 5.0.
 * [AN3889]: Section Auto-configuration.
 * 
 * [Freescale MPR121]: http://cache.freescale.com/files/sensors/doc/data_sheet/
 * MPR121.pdf "Freescale MPR121"
 * [AN3994]: http://cache.freescale.com/files/sensors/doc/app_note/AN3944.pdf
 * "AN3994"
 * [AN3893]: http://cache.freescale.com/files/sensors/doc/app_note/AN3893.pdf
 * "AN3893"
 * [AN3889]: http://cache.freescale.com/files/sensors/doc/app_note/AN3889.pdf
 * "AN3889"
 * @{
 */
// AN3944 - Section A
#define MHD_RV  0x01
#define NHD_RV  0x01
#define NCL_RV  0x00
#define FDL_RV  0x00

// AN3944 - Section B
#define MHD_FV  0x01
#define NHD_FV  0x01
#define NCL_FV  0xFF
#define FDL_FV  0x02

// AN3893 - 5.0
#define MHDPROX_RV      0xFF
#define	NHDPROX_RV      0xFF
#define NCLPROX_RV      0x00
#define FDLPROX_RV      0x00

#define MHDPROX_FV      0x01
#define	NHDPROX_FV      0x01
#define NCLPROX_FV      0xFF
#define FDLPROX_FV      0xFF

#define NHDPROX_TV      0x00
#define NCLPROX_TV      0x00
#define FDLPROX_TV      0x00

// AN3944 - Section C
#define EN_TTHV 0x0F
#define EN_RTHV 0x0A

// AN3893 - 4.0
#define E12_TTHV        0x08
#define E12_RTHV        0x05

// AN3944 - Section D
#define FCRV    0x04
// AN3944 - Section E
// AN3893 - 5.0
// Proximity detection enabled for 12 electrodes.
// 12 electrodes needed.
#define ECRV    0x3C

// AN3944 - Section F
#define AC_CR0V 0x0B
// AN3889 - AUTO-CONFIGURATION
#define AC_USLV 0xC9 // Vdd = 3.3V USL = (Vdd - 0.7) / Vdd * 256 = 0xC9
#define AC_LSLV 0x83 // LSL = USL * 0.65 = 0x83
#define AC_TLV  0xB5 // TL = USL * 0.9 = 0xB5
/** @}
 */

class Mpr121
{
  private:
    
    /** @brief Old touch and proximity input values.
     * 
     * To preserve values between consecutive calls to
     * getTouchAndProximityInputs() function.
     */
    static unsigned int oldInputs;
    
    /** @brief Check if IRQ is enable.
     * 
     * @return A boolean indicating if IRQ is enable.
     */
    boolean isIrqEnabled();
    
    /** @brief Read 'quantity' bytes from the MPR121 registers.
     * 
     * @param address LSB register address.
     * @param quantity Number of bytes to read.
     * @param data Variable to store the content of the first 'quantity'.
     */
    void getRegisters(byte address, int quantity, byte *data);

    /** @brief Configure the calibration data.
     * 
     * See the [Freescale MPR121] datasheet (Table 2) for more info.
     * 
     * Also, see the following application notes for even more info:
     * [AN3994]: Sections A, B, C, D, E and F.
     * [AN3893]: Sections 4.0 and 5.0.
     * [AN3889]: Section Auto-configuration.
     * 
     * [Freescale MPR121]: http://cache.freescale.com/files/sensors/doc/
     * data_sheet/MPR121.pdf "Freescale MPR121"
     * [AN3994]: http://cache.freescale.com/files/sensors/doc/app_note/
     * AN3944.pdf "AN3994"
     * [AN3893]: http://cache.freescale.com/files/sensors/doc/app_note/
     * AN3893.pdf "AN3893"
     * [AN3889]: http://cache.freescale.com/files/sensors/doc/app_note/
     * AN3889.pdf "AN3889"
     */
    void setCalParam();

    /** @brief Setup the I2C communication.
     * 
     * Inicialize the I2C communication via Wire library and assign an Arduino
     * pin to the MPR121 IRQ pin initializing it.
     */
    void setI2C();

    /** @brief Write a 1 byte value into the MPR121 write address.
     * 
     * @param address Register address.
     * @param value Content to write into the register.
     */
    void setRegister(byte address, byte value);
    
  public:
    
    /** @brief Constructor.
     * 
     */
    Mpr121();
    
    /** @brief Read MPR121 touch and proximity input values.
     * 
     * Format: -|-|-|E12|E11|...|E0 (16 bits)
     * E12: proximity input.
     * E11-0: respective touch values.
     * 1 = touched/proximate.
     * 0 = untouched/not proximate.
     * 
     * @return All inputs in an unique 16 bits value. If there is a new value
     * available (IRQ enabled) return this, else return the old value.
     */
    unsigned int getTouchAndProximityInputs();

    /** @brief Setup the MPR121 pressure sensor.
     * 
     * Inicialize the comunication ports and the configuration parameters.
     */
    void setDevice();
};

#endif
