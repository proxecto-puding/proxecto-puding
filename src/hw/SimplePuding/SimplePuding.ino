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

#include "Puding.h"

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

/** @brief Arduino MIDI library.
 * 
 */
MIDI midi;

/** @brief Chanter base tone.
 * 
 */
byte baseTone = DEF_TONE;

/** @brief Offset related to the base tone.
 * 
 */
unsigned int chanterOffset = DEF_OFFSET;

/** @brief Chanter actual note.
 * 
 */
byte chanterNote = baseTone;

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
const unsigned int aberto[][2] = {(B1111111110000000, -1),
                                  (B1111111100000000, 0),
                                  (B1111011110000000, 1),
                                  (B1111111000000000, 2),
                                  (B1111110100000000, 3),
                                  (B1111110000000000, 4),
                                  (B1111100000000000, 5),
                                  (B1111100010000000, 5),
                                  (B1111100100000000, 5),
                                  (B1111100110000000, 5),
                                  (B1110110000000000, 6),
                                  (B1110100010000000, 6),
                                  (B1110100100000000, 6),
                                  (B1110110110000000, 6),
                                  (B1110111000000000, 6),
                                  (B1110111010000000, 6),
                                  (B1110100000000000, 7),
                                  (B1110100010000000, 7),
                                  (B1110100100000000, 7),
                                  (B1110100110000000, 7),
                                  (B1101100000000000, 8),
                                  (B1101100010000000, 8),
                                  (B1101100100000000, 8),
                                  (B1101100110000000, 8),
                                  (B0110100000000000, 8),
                                  (B0110100010000000, 8),
                                  (B0110100100000000, 8),
                                  (B0110100110000000, 8),
                                  (B1100100000000000, 9),
                                  (B1100100010000000, 9),
                                  (B1100100100000000, 9),
                                  (B1100100110000000, 9),
                                  (B1010100000000000, 10),
                                  (B1010100010000000, 10),
                                  (B1010100100000000, 10),
                                  (B1010100110000000, 10),
                                  (B0100100000000000, 10),
                                  (B0100100010000000, 10),
                                  (B0100100100000000, 10),
                                  (B0100100110000000, 10),
                                  (B1000100000000000, 11),
                                  (B1000100010000000, 11),
                                  (B1000100100000000, 11),
                                  (B1000100110000000, 11),
                                  (B0111111110000000, 11),
                                  (B0000000000000000, 12),
                                  (B0000000010000000, 12),
                                  (B0000000100000000, 12),
                                  (B0000000110000000, 12),
                                  (B1011111100000000, 12),
                                  (B0111111100000000, 12),
                                  (B0111011100000000, 13),
                                  (B0111111000000000, 14),
                                  (B0111110100000000, 15),
                                  (B0111110000000000, 16),
                                  (B0111100000000000, 17)};

/** @brief Matrix containing all the "pechado" offsets.
 * 
 * Matrix containing the correspondent offset for all the possible "pechado" inputs.
 */
const unsigned int pechado[][2] = {(B1111111110000000, -1),
                                   (B1111111100000000, 0),
                                   (B1111011100000000, 1),
                                   (B1111111000000000, 2),
                                   (B1111110100000000, 3),
                                   (B1111110110000000, 4),
                                   (B1111101100000000, 5),
                                   (B1110110100000000, 6),
                                   (B1110111100000000, 7),
                                   (B1101101100000000, 8),
                                   (B0110111100000000, 8),
                                   (B1100111100000000, 9),
                                   (B1010111100000000, 10),
                                   (B0100111100000000, 10),
                                   (B1000111100000000, 11),
                                   (B0111111110000000, 11),
                                   (B1011111100000000, 12),
                                   (B0111111100000000, 12),
                                   (B1011011100000000, 13),
                                   (B0111011100000000, 13),
                                   (B0111111000000000, 14),
                                   (B0111110100000000, 15),
                                   (B0111110000000000, 16),
                                   (B0111100000000000, 17)};

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
  if (isBagEnabled && !isBagPressureEnough()) { // Stop.
    midi.sendNoteOff(chanterNote, velocity, chanterChannel);
    if (isVibratoEnabled()) {
      midi.sendPitchBend(0, chanterChannel);
    }
    if (isBassDroneEnabled) {
      midi.sedNoteOff(bassDroneNote, velocity, dronesChannel);
    }
    if (isTenorDroneEnabled) {
      midi.sendNoteOff(tenorDroneNote, velocity, dronesChannel);
    }
    if (isHighDroneEnabled) {
      midi.sendNoteOff(highDroneNote, velocity, dronesChannel);
    }
  }
  else { // Play.
    chanterOffset = getChanterOffset();
    if (chanterOffset != DEF_OFFSET) {
      chanterNote = baseTone + chanterOffset;
      midi.sendNoteOn(chanterNote, velocity, chanterChannel);
      if (isVibratoEnabled()) {
        midi.sendPitchBend(DEF_PB, chanterChannel);
      }
    } else { // Stop if the new fingering is not recognized.
      midi.sendNoteOff(chanterNote, velocity, chanterChannel);
      if (isVibratoEnabled()) {
        midi.sendPitchBend(0, chanterChannel);
      }
    }
    if (isBassDroneEnabled) {
      midi.sedNoteOn(bassDroneNote, velocity, dronesChannel);
    }
    if (isTenorDroneEnabled) {
      midi.sendNoteOn(tenorDroneNote, velocity, dronesChannel);
    }
    if (isHighDroneEnabled) {
      midi.sendNoteOn(highDroneNote, velocity, dronesChannel);
    }
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
  midi.begin();
}

/** @brief Configure the bagpipe allowed fingerings.
 * 
 */
void setFingerings() {
  initializeOffsets();
}
