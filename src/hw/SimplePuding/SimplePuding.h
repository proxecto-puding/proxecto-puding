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

/** @file SimplePuding.h
 * @brief Main file interface.
 * 
 * Interface of the main project file.
 */

#if (ARDUINO >= 100)
#include <Arduino.h>
#else
#include <WProgram.h>
#endif

#include <Wire.h>

#include <Mpr121.h>

#include <MIDI.h>

#ifndef Puding_h
#define Puding_h

/** @brief Discovery beacon.
 * 
 */
#define D_BEACON        "DBEACON"

/** @brief Product serial number.
 * 
 */
#define PUDING_SN       "A00000001"

/** @brief Serial port connection velocity.
 * 
 */
#define SERIAL_VEL 9600

/** @brief Startup delay.
 * 
 */
#define STARTUP_DELAY 5000

/** @addtogroup PudingConfigurationActions
 * 
 * Puding configuration actions.
 * 
 * Different configuration actions allowed by the bagpipes.
 * 
 * ACTION_CURRENT refers to the current configuration.
 * ACTION_NEW refers to a new configuration.
 * ACTION_REVERT refers to undo the last changes and restore the previous
 * configuration.
 * ACTION_DEFAULT refers to undo the last changer and restore the default
 * configuration.
 * @{
 */
#define ACTION_CURRENT  "current"
#define ACTION_NEW      "new"
#define ACTION_REVERT   "revert"
#define ACTION_DEFAULT  "default"
/** @}
 */

/** @addtogroup PudingConfigurationTypes
 * 
 * Puding configuration types.
 * 
 * Different bagpipes configuration types.
 * 
 * TYPE_START refers to the configuration application initial screen.
 * TYPE_SELECT refers to the configuration application selection screen.
 * TYPE_TUNING refers to the tuning configuration.
 * TYPE_SENSIT refers to the sensitivity configuration.
 * TYPE_FINGER refers to the fingerings configuration.
 * @{
 */
#define TYPE_START      "start"
#define TYPE_SELECT     "select"
#define TYPE_TUNING     "tuning"
#define TYPE_SENSIT     "sensit"
#define TYPE_FINGER     "finger"
/** @}
 */

/** @addtogroup PudingConfigurationFileExtensions
 * 
 * Puding configuration file extensions.
 * 
 * CONF_FILE_EXT refers to the actual configuration.
 * BAK_FILE_EXT refers to previous configuration.
 * DEF_FILE_EXT refers to the default configuration.
 * @{
 */
#define CONF_FILE_EXT   ".json"
#define BAK_FILE_EXT   ".bak"
#define DEF_FILE_EXT    ".def"
/** @}
 */

/** @brief Default tone.
 * 
 */
#define DEF_TONE        60

/** @brief Default velocity.
 * 
 */
#define DEF_VEL         63

/** @brief Default chanter MIDI channel.
 * 
 */
#define DEF_CC          1

/** @brief Default drones MIDI channel.
 * 
 */
#define DEF_DC          2

/** @brief Default number of drones.
 * 
 */
#define DEF_DN          3

/** @brief Default pitch bend variation value.
 * 
 * In the MIDI specification, the pitch wheel may send 16,384 possible
 * variation values. The General MIDI specification recommends that the total
 * range be ± 2 semitones.
 * 
 * The amount of bend to send (in a signed integer format), between -8192
 * (maximum downwards bend) and 8191 (max upwards bend), center value is 0.
 * 
 * Examples:
 * 
 * 1 tone = 8192
 * 1/2 tone = 4096
 * 1/4 tone = 2048
 * 1/8 tone = 1024
 */
#define DEF_PB          -1024

/** @brief Default bag pressure.
 * 
 */
// TODO Calculate a default value.
#define DEF_MBP         100000

/** @addtogroup PudingInputMasks
 * 
 * Puding input masks.
 * 
 * Masks to differenciate and isolate touch inputs from proximity inputs and
 * vice versa.
 * @{
 */
#define TI_MASK         (B00001111 * 256) + B11111111
#define PI_MASK         (B00010000 * 256) + B00000000
/** @}
 */

/** @brief Default amount of fingering types.
 * 
 */
#define DEF_FT          3

/** @brief Maximum amount of fingerings.
 * 
 * Maximum quantity of fingering definitions supported by the chanter.
 */
#define MAX_FING        128

/** @brief Default offset.
 * 
 * Represents a non-allowed offset. It is used to differentiate free offset
 * positions.
 */
#define DEF_OFFSET      -999

/** @brief Default types of samples.
 * 
 */
#define DEF_SN          3

#endif
