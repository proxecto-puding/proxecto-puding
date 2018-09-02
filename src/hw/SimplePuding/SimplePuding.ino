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

/** @file SimplePuding.ino
 * @brief Main file.
 * 
 * Main project file.
 */

#include "SimplePuding.h"

#include <Wire.h>

#include <Mpr121.h>

#include <MIDI.h>

/** @addtogroup PudingSensors
 * 
 * Bagpipes sensors and components.
 * @{
 */
Mpr121 mpr121;
/** @}
 */

/** @brief Chanter base tone.
 * 
 */
byte baseTone = byte(DEF_TONE);

/** @brief Offset related to the base tone.
 * 
 */
short chanterOffset = short(DEF_OFFSET);

/** @brief Chanter current note.
 * 
 */
byte chanterNote = baseTone;
byte newChanterNote = chanterNote - 1;

/** @addtogroup DroneNotes
 * 
 * Drone current notes.
 * @{
 */
byte bassDroneNote = chanterNote;
byte tenorDroneNote = chanterNote;
byte highDroneNote = chanterNote;
/** @}
 */

/** @brief MIDI note velocity.
 * 
 */
byte velocity = byte(DEF_VEL);

/** @brief Chanter MIDI channel.
 * 
 */
byte chanterChannel = byte(DEF_CC);

/** @brief Bagpipes drones channel.
 * 
 */
byte dronesChannel = byte(DEF_DC);

/** @brief Indicate if the pressure in the bag is enough.
 * 
 */
boolean isBagEnabled = true;

/** @brief Minimum bag pressure to make the chanter sound.
 * 
 */
long minBagPressure = long(DEF_MBP);

/** @addtogroup DronesEnabled
 * 
 * Indicate if some drone is enabled.
 * @{
 */
boolean isBassDroneEnabled = true;
boolean isTenorDroneEnabled = true;
boolean isHighDroneEnabled = true;
/** @}
 */

/** @brief Actual fingering inputs.
 * 
 */
short inputs = 0;

/** @brief Matrix containing all the offsets.
 * 
 * Matrix containing the correspondent offset for all the possible inputs.
 */
const short offsets[MAX_FING][2] =
  // Aberto.
  {((B00000001 * 256) + B11111111, -1),
  ((B00000001 * 256) + B11111110, 0),
  ((B00000001 * 256) + B11101110, 1),
  ((B00000001 * 256) + B01111110, 2),
  ((B00000001 * 256) + B11111010, 3),
  ((B00000001 * 256) + B11111000, 4),
  ((B00000001 * 256) + B11110000, 5),
  ((B00000001 * 256) + B11110001, 5),
  ((B00000001 * 256) + B11110010, 5),
  ((B00000001 * 256) + B11110011, 5),
  ((B00000001 * 256) + B11011000, 6),
  ((B00000001 * 256) + B11010001, 6),
  ((B00000001 * 256) + B11010010, 6),
  ((B00000001 * 256) + B11011011, 6),
  ((B00000001 * 256) + B11011100, 6),
  ((B00000001 * 256) + B11011101, 6),
  ((B00000001 * 256) + B11010000, 7),
  ((B00000001 * 256) + B11010001, 7),
  ((B00000001 * 256) + B11010010, 7),
  ((B00000001 * 256) + B11010011, 7),
  ((B00000001 * 256) + B10110000, 8),
  ((B00000001 * 256) + B10110001, 8),
  ((B00000001 * 256) + B10110010, 8),
  ((B00000001 * 256) + B10110011, 8),
  ((B00000000 * 256) + B11010000, 8),
  ((B00000000 * 256) + B11010001, 8),
  ((B00000000 * 256) + B11010010, 8),
  ((B00000000 * 256) + B11010011, 8),
  ((B00000001 * 256) + B10010000, 9),
  ((B00000001 * 256) + B10010001, 9),
  ((B00000001 * 256) + B10010010, 9),
  ((B00000001 * 256) + B10010011, 9),
  ((B00000001 * 256) + B01010000, 10),
  ((B00000001 * 256) + B01010001, 10),
  ((B00000001 * 256) + B01010010, 10),
  ((B00000001 * 256) + B01010011, 10),
  ((B00000000 * 256) + B10010000, 10),
  ((B00000000 * 256) + B10010001, 10),
  ((B00000000 * 256) + B10010010, 10),
  ((B00000000 * 256) + B10010011, 10),
  ((B00000001 * 256) + B00010000, 11),
  ((B00000001 * 256) + B00010001, 11),
  ((B00000001 * 256) + B00010010, 11),
  ((B00000001 * 256) + B00010011, 11),
  ((B00000000 * 256) + B11111111, 11),
  ((B00000000 * 256) + B00000000, 12),
  ((B00000000 * 256) + B00000001, 12),
  ((B00000000 * 256) + B00000010, 12),
  ((B00000000 * 256) + B00000011, 12),
  ((B00000001 * 256) + B01111110, 12),
  ((B00000000 * 256) + B11111110, 12),
  ((B00000000 * 256) + B11101110, 13),
  ((B00000000 * 256) + B11111100, 14),
  ((B00000000 * 256) + B11111010, 15),
  ((B00000000 * 256) + B11111000, 16),
  ((B00000000 * 256) + B11110000, 17),
  // Pechado
  ((B00000001 * 256) + B11111111, -1),
  ((B00000001 * 256) + B11111110, 0),
  ((B00000001 * 256) + B11101110, 1),
  ((B00000001 * 256) + B11111100, 2),
  ((B00000001 * 256) + B11111010, 3),
  ((B00000001 * 256) + B11111011, 4),
  ((B00000001 * 256) + B11110110, 5),
  ((B00000001 * 256) + B11011010, 6),
  ((B00000001 * 256) + B11011110, 7),
  ((B00000001 * 256) + B10110110, 8),
  ((B00000000 * 256) + B11011110, 8),
  ((B00000001 * 256) + B10011110, 9),
  ((B00000001 * 256) + B01011110, 10),
  ((B00000000 * 256) + B10011110, 10),
  ((B00000001 * 256) + B00011110, 11),
  ((B00000000 * 256) + B11111111, 11),
  ((B00000001 * 256) + B01111110, 12),
  ((B00000000 * 256) + B11111110, 12),
  ((B00000001 * 256) + B01101110, 13),
  ((B00000000 * 256) + B11101110, 13),
  ((B00000000 * 256) + B11111100, 14),
  ((B00000000 * 256) + B11111010, 15),
  ((B00000000 * 256) + B11111000, 16),
  ((B00000000 * 256) + B11110000, 17)};

/** @brief Indicate fingering types to use.
 * 
 * 0 - "Aberto".
 * 1 - "Pechado".
 * 2 - Custom.
 */
boolean fingeringTypes[DEF_FT] = {true};

/** @brief Arduino setup function.
 * 
 */
void setup() {
  setDevice();
  delay(STARTUP_DELAY);
}

/** @brief Arduino loop function.
 * 
 */
void loop() {
  play();
}

/** @brief Calculate the offset to apply to the base tone.
 * 
 * The chanter has a base tone defined and each fingering has an
 * offset defined. The correspondent note is obtained by adding the offset to
 * the base tone. Note that the offset can be negative.
 * 
 * @return An offset.
 */
short getChanterOffset() {
  inputs = mpr121.getTouchAndProximityInputs();
  short touchInputs = inputs & short(TI_MASK);
  int i;
  for (i = 0; i < MAX_FING; i++) {
    if (offsets[i][0] == touchInputs) {
      return offsets[i][1];
    }
  }
  return short(DEF_OFFSET);
}

/** @brief Get chanter note.
 * 
 */
byte getChanterNote() {
  return baseTone + chanterOffset;
}

/** @brief Get bass drone note.
 * 
 */
byte getBassDroneNote() {
  return baseTone - 24; // Two octaves below.
}

/** @brief Get bass drone note.
 * 
 */
byte getTenorDroneNote() {
  return baseTone - 12; // One octave below.
}

/** @brief Get bass drone note.
 * 
 */
byte getHighDroneNote() {
   return baseTone + 7; // Same octave dominant.
}

/** @brief Check if the vibrato is enabled.
 * 
 * The vibrato is simulated with the proximity sensor.
 * 
 * @return A boolean indicating if the vibrato is enabled.
 */
boolean isVibratoEnabled() {
  short vibrato = inputs & short(PI_MASK);
  return (vibrato != 0);
}

/** @brief Make the bagpipe sound.
 * 
 * Function called during the playing phase which reads the data from the
 * sensors and makes the bagpipe sound in accordance.
 */
void play() {
  playChanter();  
//  playDrones();
}

/** @brief Make the chanter sound.
 * 
 */
void playChanter() {
  
  chanterOffset = getChanterOffset();
  newChanterNote = getChanterNote();
  
  // Stop if the note changes or the new fingering is not recognized.
  if (chanterOffset == short(DEF_OFFSET) || newChanterNote != chanterNote) {
    MIDI.sendNoteOff(chanterNote, velocity, chanterChannel);
  }
  
  // Play if the note changes and the new fingering is recognized.
  if (chanterOffset != short(DEF_OFFSET) && newChanterNote != chanterNote) {
    chanterNote = newChanterNote;
    MIDI.sendNoteOn(chanterNote, velocity, chanterChannel);
  }
}

/** @brief Make the drones sound.
 * 
 */
void playDrones() {
  if (isBassDroneEnabled) {
    if (bassDroneNote != getBassDroneNote()) {
      MIDI.sendNoteOff(bassDroneNote, velocity, dronesChannel);
      bassDroneNote = getBassDroneNote();
      MIDI.sendNoteOn(bassDroneNote, velocity, dronesChannel);
    }
  } else {
    MIDI.sendNoteOff(bassDroneNote, velocity, dronesChannel);
  }
  
  if (isTenorDroneEnabled) {
    if (tenorDroneNote != getTenorDroneNote()) {
      MIDI.sendNoteOff(tenorDroneNote, velocity, dronesChannel);
    }
    tenorDroneNote = getTenorDroneNote();
    MIDI.sendNoteOn(tenorDroneNote, velocity, dronesChannel);
  } else {
    MIDI.sendNoteOff(tenorDroneNote, velocity, dronesChannel);
  }
  
  if (isHighDroneEnabled) {
    if (highDroneNote != getHighDroneNote()) {
      MIDI.sendNoteOff(highDroneNote, velocity, dronesChannel);
    }
    highDroneNote = getHighDroneNote();
    MIDI.sendNoteOn(highDroneNote, velocity, dronesChannel);
  } else {
    MIDI.sendNoteOff(highDroneNote, velocity, dronesChannel);
  }
}

/** @brief Setup the bagpipe.
 * 
 * Initialize the comunication ports and the configuration parameters.
 */
void setDevice() {
  Serial.begin(SERIAL_VEL);
  delay(STARTUP_DELAY);
  mpr121.setDevice();
  delay(STARTUP_DELAY);
  MIDI.begin();
  delay(STARTUP_DELAY);
}
