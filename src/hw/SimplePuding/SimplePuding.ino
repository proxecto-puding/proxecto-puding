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
byte baseTone = DEF_TONE;

/** @brief Offset related to the base tone.
 * 
 */
unsigned int chanterOffset = DEF_OFFSET;

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
byte velocity = DEF_VEL;

/** @brief Chanter MIDI channel.
 * 
 */
byte chanterChannel = DEF_CC;

/** @brief Bagpipes drones channel.
 * 
 */
byte dronesChannel = DEF_DC;

/** @brief Indicate if the pressure in the bag is enough.
 * 
 */
boolean isBagEnabled = true;

/** @brief Minimum bag pressure to make the chanter sound.
 * 
 */
long minBagPressure = DEF_MBP;

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
unsigned int inputs = 0;

/** @brief Matrix containing all the offsets.
 * 
 * Matrix containing the correspondent offset for all the possible inputs.
 */
unsigned int offsets[MAX_FING][2];

/** @brief Indicate fingering types to use.
 * 
 * 0 - "Aberto".
 * 1 - "Pechado".
 * 2 - Custom.
 */
boolean fingeringTypes[DEF_FT];

/** @brief Matrix containing all the "aberto" offsets.
 * 
 * Matrix containing the correspondent offset for all the possible "aberto" inputs.
 */
const unsigned int aberto[][2] = {((B11111111 * 256) + B10000000, -1),
                                  ((B11111111 * 256) + B00000000, 0),
                                  ((B11110111 * 256) + B10000000, 1),
                                  ((B11111110 * 256) + B00000000, 2),
                                  ((B11111101 * 256) + B00000000, 3),
                                  ((B11111100 * 256) + B00000000, 4),
                                  ((B11111000 * 256) + B00000000, 5),
                                  ((B11111000 * 256) + B10000000, 5),
                                  ((B11111001 * 256) + B00000000, 5),
                                  ((B11111001 * 256) + B10000000, 5),
                                  ((B11101100 * 256) + B00000000, 6),
                                  ((B11101000 * 256) + B10000000, 6),
                                  ((B11101001 * 256) + B00000000, 6),
                                  ((B11101101 * 256) + B10000000, 6),
                                  ((B11101110 * 256) + B00000000, 6),
                                  ((B11101110 * 256) + B10000000, 6),
                                  ((B11101000 * 256) + B00000000, 7),
                                  ((B11101000 * 256) + B10000000, 7),
                                  ((B11101001 * 256) + B00000000, 7),
                                  ((B11101001 * 256) + B10000000, 7),
                                  ((B11011000 * 256) + B00000000, 8),
                                  ((B11011000 * 256) + B10000000, 8),
                                  ((B11011001 * 256) + B00000000, 8),
                                  ((B11011001 * 256) + B10000000, 8),
                                  ((B01101000 * 256) + B00000000, 8),
                                  ((B01101000 * 256) + B10000000, 8),
                                  ((B01101001 * 256) + B00000000, 8),
                                  ((B01101001 * 256) + B10000000, 8),
                                  ((B11001000 * 256) + B00000000, 9),
                                  ((B11001000 * 256) + B10000000, 9),
                                  ((B11001001 * 256) + B00000000, 9),
                                  ((B11001001 * 256) + B10000000, 9),
                                  ((B10101000 * 256) + B00000000, 10),
                                  ((B10101000 * 256) + B10000000, 10),
                                  ((B10101001 * 256) + B00000000, 10),
                                  ((B10101001 * 256) + B10000000, 10),
                                  ((B01001000 * 256) + B00000000, 10),
                                  ((B01001000 * 256) + B10000000, 10),
                                  ((B01001001 * 256) + B00000000, 10),
                                  ((B01001001 * 256) + B10000000, 10),
                                  ((B10001000 * 256) + B00000000, 11),
                                  ((B10001000 * 256) + B10000000, 11),
                                  ((B10001001 * 256) + B00000000, 11),
                                  ((B10001001 * 256) + B10000000, 11),
                                  ((B01111111 * 256) + B10000000, 11),
                                  ((B00000000 * 256) + B00000000, 12),
                                  ((B00000000 * 256) + B10000000, 12),
                                  ((B00000001 * 256) + B00000000, 12),
                                  ((B00000001 * 256) + B10000000, 12),
                                  ((B10111111 * 256) + B00000000, 12),
                                  ((B01111111 * 256) + B00000000, 12),
                                  ((B01110111 * 256) + B00000000, 13),
                                  ((B01111110 * 256) + B00000000, 14),
                                  ((B01111101 * 256) + B00000000, 15),
                                  ((B01111100 * 256) + B00000000, 16),
                                  ((B01111000 * 256) + B00000000, 17)};

/** @brief Matrix containing all the "pechado" offsets.
 * 
 * Matrix containing the correspondent offset for all the possible "pechado" inputs.
 */
const unsigned int pechado[][2] = {((B11111111 * 256) + B10000000, -1),
                                   ((B11111111 * 256) + B00000000, 0),
                                   ((B11110111 * 256) + B00000000, 1),
                                   ((B11111110 * 256) + B00000000, 2),
                                   ((B11111101 * 256) + B00000000, 3),
                                   ((B11111101 * 256) + B10000000, 4),
                                   ((B11111011 * 256) + B00000000, 5),
                                   ((B11101101 * 256) + B00000000, 6),
                                   ((B11101111 * 256) + B00000000, 7),
                                   ((B11011011 * 256) + B00000000, 8),
                                   ((B01101111 * 256) + B00000000, 8),
                                   ((B11001111 * 256) + B00000000, 9),
                                   ((B10101111 * 256) + B00000000, 10),
                                   ((B01001111 * 256) + B00000000, 10),
                                   ((B10001111 * 256) + B00000000, 11),
                                   ((B01111111 * 256) + B10000000, 11),
                                   ((B10111111 * 256) + B00000000, 12),
                                   ((B01111111 * 256) + B00000000, 12),
                                   ((B10110111 * 256) + B00000000, 13),
                                   ((B01110111 * 256) + B00000000, 13),
                                   ((B01111110 * 256) + B00000000, 14),
                                   ((B01111101 * 256) + B00000000, 15),
                                   ((B01111100 * 256) + B00000000, 16),
                                   ((B01111000 * 256) + B00000000, 17)};

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
unsigned int getChanterOffset() {
  inputs = mpr121.getTouchAndProximityInputs();
  unsigned int touchInputs = inputs | TI_MASK;
  int i;
  for (i = 0; i < MAX_FING; i++) {
    // When a default offset value is reached there are not more valid
    // fingerings after this one.
    if (offsets[i][0] == DEF_OFFSET) {
      return offsets[i][0];
    }
    if (offsets[i][0] == touchInputs) {
      return offsets[i][1];
    }
  }
  return DEF_OFFSET;
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

/** @brief Initialize the offsets.
 * 
 * No custom offsets allowed at this point.
 */
void initializeOffsets() {
  // All to default.
  for (int i = 0; i < MAX_FING; i++) {
    offsets[i][1] = DEF_OFFSET;
  }
  int size;
  // Aberto.
  if (fingeringTypes[0]) {
    size = sizeof(aberto)/(2*sizeof(unsigned int));
    for (int i = 0; i < size; i++) {
      offsets[i][0] = aberto[i][0];
      offsets[i][1] = aberto[i][1];
    }
  }
  // Pechado.
  if (fingeringTypes[1]) {
    size = sizeof(pechado)/(2*sizeof(unsigned int));
    for (int i = 0; i < size; i++) {
      for (int j = 0; i < MAX_FING; i++) {
        // Replace.
        if (offsets[j][0] == pechado[i][0]) {
          offsets[j][1] = pechado[i][1];
          break;
        }
        // Add.
        if (offsets[j][1] == DEF_OFFSET) {
          offsets[j][0] = pechado[i][0];
          offsets[j][1] = pechado[i][1];
          break;
        }
      }
    }
  }
}

/** @brief Check if the vibrato is enabled.
 * 
 * The vibrato is simulated with the proximity sensor.
 * 
 * @return A boolean indicating if the vibrato is enabled.
 */
boolean isVibratoEnabled() {
  unsigned int vibrato = inputs | PI_MASK;
  return (vibrato != 0);
}

/** @brief Make the bagpipe sound.
 * 
 * Function called during the playing phase which reads the data from the
 * sensors and makes the bagpipe sound in accordance.
 */
void play() {
  playChanter();  
  playDrones();
}

/** @brief Make the chanter sound.
 * 
 */
void playChanter() {
  
  chanterOffset = getChanterOffset();
  newChanterNote = getChanterNote();
  
  // Stop if the note changes or the new fingering is not recognized.
  if (chanterOffset == DEF_OFFSET || newChanterNote != chanterNote) {
    MIDI.sendNoteOff(chanterNote, velocity, chanterChannel);
    if (isVibratoEnabled()) {
      MIDI.sendPitchBend(0, chanterChannel);
    }
  }
  
  // Play if the note changes and the new fingering is recognized.
  if (chanterOffset != DEF_OFFSET && newChanterNote != chanterNote) {
    chanterNote = newChanterNote;
    MIDI.sendNoteOn(chanterNote, velocity, chanterChannel);
    if (isVibratoEnabled()) {
      MIDI.sendPitchBend(DEF_PB, chanterChannel);
    }
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
}

/** @brief Configure the bagpipe allowed fingerings.
 * 
 */
void setFingerings() {
  initializeOffsets();
}
