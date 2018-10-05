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

/** @file G1.h
 * @brief 4D SYSTEMS uDRIVE-uSD-G1 storage device control interface.
 * 
 * Complete interface to manage the 4D SYSTEMS uDRIVE-uSD-G1 embedded 
 * "DOS micro-Drive" device.
 * 
 * See 4D SYSTEMS uDRIVE-uSD-G1 [datasheet] and [command set] for more 
 * information.
 * 
 * [datasheet]: http://www.4dsystems.com.au/downloads/micro-DRIVE/
 * uDRIVE-uSD-G1/Docs/uDRIVE-uSD-G1-DS-rev4.pdf "datasheet"
 * [command set]: http://www.4dsystems.com.au/downloads/Semiconductors/
 * GOLDELOX-DOS/Docs/GOLDELOX-DOS-COMMANDS-SIS-rev3.pdf "command set"
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

#ifndef G1_h
#define G1_h

/** @brief Serial port connection velocity.
 * 
 */
#define SERIAL_VEL 9600

/** @brief Reset pin.
 * 
 */
#define RESET_PIN 2

/** @addtogroup CommonCommandDefinitions
 * 
 * Common command definitions.
 * @{
 */
#define CC_EC   0x40
#define CC_ACK  0x06
#define CC_NAK  0x15
#define CC_DEL  0x0A
#define CC_TER  0x00
/** @}
 */

/** @addtogroup InitDiskDriveMemoryCardDefinitions
 * 
 * Init disk drive memory card command definitions.
 * @{
 */
#define IMD_CH  0x69
/** @}
 */

/** @addtogroup ListDirectoryDefinitions
 * 
 * List directory command definitions.
 * @{
 */
#define LD_CH   0x64
/** @}
 */

/** @addtogroup PrintDeviceVersionInfoDefinitions
 * 
 * Print Device Version Info command definitions.
 * @{
 */
#define DVI_CMD 0x56
/** @}
 */

/** @addtogroup ReadFileDefinitions
 * 
 * Read file command definitions.
 * @{
 */
#define RDF_CH  0x61
/* Read handshaking mode.
 * 
 * Number of bytes by ACK between two host data requests:
 * 
 * 0x00 - No handshaking (up to 512 bytes).
 * 0x01 - 1 byte/ACK.
 * 0x02 - 2 bytes/ACK.
 * ...
 * 0x32 - 50 bytes/ACK (max.).
 * 
 * Arduino [serial] receive buffer can hold up to 64 bytes.
 * 
 * [serial]: http://arduino.cc/en/Serial/Available "serial"
 */
#define RDF_NH  0x00
#define RDF_HS  0x01
/** @}
 */

/** @addtogroup RemoveFileDefinitions
 * 
 * Remove file command definitions.
 * @{
 */
#define RMF_CH  0x65
/** @}
 */

/** @addtogroup SetFatProtectionDefinitions
 * 
 * Set FAT protection command definitions.
 * @{
 */
#define SFP_CMD 0x59
#define SFP_MOD 0x08
// FAT protection value
#define SFP_VPF 0x00 // Protection OFF.
#define SFP_VPO 0x01 // Protection ON.
/** @}
 */

/** @addtogroup SetSerialAutobaudDefinitions
 * 
 * Set serial auto-baud command definitions.
 * @{
 */
#define SAB_CMD 0x55
/** @}
 */

/** @addtogroup WriteFileDefinitions
 * 
 * Write file command definitions.
 * @{
 */
#define WF_CH   0x74
/* Write handshaking mode.
 * 
 * Number of bytes by ACK between two device data requests:
 * 
 * 0x00 - No handshaking (up to 100 bytes).
 * 0x01 - 1 byte/ACK.
 * 0x02 - 2 bytes/ACK.
 * ...
 * 0x32 - 50 bytes/ACK (max.).
 */
#define WF_HS   0x01
// Write append mode.
#define WF_AMN  0x00 // No append (create or overwrite).
#define WF_AMA  0X80 // Append (or create).
/* Write performance mode.
 * 
 * 0x00 – High performance. 2 ACKs will be sent after the file size.
 * 0x40 – Low performance. Only 1 ACK is sent at a time (for un-buffered serial
 * port controllers).
 */
#define WF_PM   0x40
/** @}
 */

class G1
{
  private:
    
    /** @brief Initialize the memory card, after power up or reset.
     * 
     * This function automatically calls to setFatProtection() turning FAT
     * protection ON.
     */
    boolean initDiskDriveMemoryCard();
    
    /** @brief Setup the Arduino serial port.
     * 
     * Inicialize the Arduino serial port setting the communication velocity.
     */
    void setHostSerial();

    /** @brief Determine and lock on to the host serial port baud rate.
     * 
     * This must be the first command sent to the device after power up
     * or reset.
     * 
     * @return A boolean indicating if the operation was successful.
     */
    boolean setSerialAutoBaud();
  
  public:
    
    /** @brief Constructor.
     * 
     */
    G1();
    
    /** @brief Create a copy of the first file into the second.
     * 
     * @param fromFile String up to 12 chars long. If it is not specified, a
     * '.' will be assumed between chars 8 and 9.
     * @param toFile String up to 12 chars long. If it is not specified, a '.'
     * will be assumed between chars 8 and 9.
     * @return A boolean indicating if it was copied.
     */
    boolean copyFile(char *fromFile, char *toFile);

    /** @brief Get the size of the specified file.
     * 
     * @param name String up to 12 chars long. If it is not specified, a '.'
     * will be assumed between chars 8 and 9.
     * @return Size of the file. If blank or error, return zero.
     */
    unsigned long getFileSize(char *name);

    /** @brief Print files into the directory matching the specified name.
    * 
    * @param name String up to 12 chars long. If it is not specified, a '.'
    * will be assumed between chars 8 and 9. Wildcars ('*', '?') allowed.
    * @return A boolean indicating if the operation was successful.
    */
    boolean listDirectory(char *name);

    /** @brief Change the name of the fisrt file to the second one.
    * 
    * @param fromFile String up to 12 chars long. If it is not specified, a 
    * '.' will be assumed between chars 8 and 9.
    * @param toFile String up to 12 chars long. If it is not specified, a '.'
    * will be assumed between chars 8 and 9.
    * @return A boolean indicating if it was moved.
    */
    boolean moveFile(char *fromFile, char *toFile);
    
    /** @brief Obtain and print the device version information.
     * 
     * Print all the device information (characteristics, capability, etc.).
     */
    void printDeviceVersionInfo();

    /** @brief Read the specified file.
    * 
    * @param name String up to 12 chars long. If it is not specified, a '.' 
    * will be assumed between chars 8 and 9.
    * @return The file data contained into a String.
    */
    String readFile(char *name);

    /** @brief Delete the specified file.
    * 
    * @return A boolean indicating if the file was removed.
    */
    boolean removeFile(char *name);
    
    /** @brief Setup the uDRIVE-uSD-G1 device.
     * 
     * Inicialize the communication ports and the configuration parameters.
     */
    void setDevice();

    /** @brief Enable/Disable card FAT protection.
     * 
     * @param enable Boolean indicating to turn the FAT protection ON/OFF.
     */
    void setFatProtection(boolean enable);

    /** @brief Write a data block into the specified file.
     * 
     * @param name String up to 12 chars long. If it is not specified, a '.' will
     * be assumed between chars 8 and 9.
     * @param append Opening mode: true for append, false for overwrite.
     * @param data Data to write into the specified file.
     * @return A boolean indicating if the operation was successful.
     */
    boolean writeFile(char *name, boolean append, String data);
};

#endif
