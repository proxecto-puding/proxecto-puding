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

/** @file Puding.ino
 * @brief Main file.
 * 
 * Main project file.
 */

#include "Puding.h"

#include <Wire.h>

#include <Bmp085.h>
#include <G1.h>
#include <Mpr121.h>

#include <aJSON.h>
#include <MIDI.h>

/** @addtogroup PudingSensors
 * 
 * Bagpipes sensors and components.
 * @{
 */
Bmp085 bmp085;
G1 g1;
Mpr121 mpr121;
/** @}
 */

/** @brief Indicate if the chanter has been identified by the configuration
 * application.
 * 
 */
boolean isChanterIdentified = false;

/** @brief Chanter base tone.
 * 
 */
byte baseTone = DEF_TONE;

/** @brief Offset related to the base tone.
 * 
 */
short chanterOffset = DEF_OFFSET;

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
unsigned short inputs = 0;

/** @brief Matrix containing all the offsets.
 * 
 * Matrix containing the correspondent offset for all the possible inputs.
 */
short offsets[MAX_FING][2];

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
const short aberto[][2] = {((B00000001 * 256) + B11111111, -1),
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
                            ((B00000000 * 256) + B11110000, 17)};

/** @brief Matrix containing all the "pechado" offsets.
 * 
 * Matrix containing the correspondent offset for all the possible "pechado" inputs.
 */
const short pechado[][2] = {((B00000001 * 256) + B11111111, -1),
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
  if (!isChanterIdentified) {
    isChanterIdentified = identifyChanter();
  } else {
    configure();
  } 
  play();
}

/** @brief Backup the configuration file related with the provided type.
 * 
 * @param type A configuration type.
 */
void backupFile(String type) {
  String configFile = type + CONF_FILE_EXT;
  unsigned int cfLength = configFile.length();
  char fromFile [cfLength + 1];
  configFile.toCharArray(fromFile, cfLength + 1);
  String backupFile = type + BAK_FILE_EXT;
  unsigned int bfLength = backupFile.length();
  char toFile [bfLength + 1];
  backupFile.toCharArray(toFile, bfLength + 1);
  g1.copyFile(fromFile, toFile);
}

/** @brief Configure the chanter.
 * 
 * Function called during the configuration phase which reads the new
 * configuration data sent by the configuration application and changes the
 * bagpipe configuration in accordance.
 */
void configure() {
  if (!Serial.available()) {
    return;
  }
  aJsonObject* root = getAJsonObject();
  if (!isMeTheTarget(root)) {
    aJson.deleteItem(root);
    return;
  }
  if (!isConfiguration(root, String(ACTION_CURRENT))) {
    setConfiguration(root);
  }
  if (!isConfiguration(root, String(ACTION_NEW))) {
    sendConfiguration(root);
  }
  aJson.deleteItem(root);
}

/** @brief Look for the discovery beacon sent by the configuration application.
 * 
 * @return A boolean indicating if the discovery beacon has been found.
 */
boolean findDiscoveryBeacon() {
  if (!Serial.available()) {
    return false;
  }
  char *data = getSerialData();
  return String(D_BEACON).equals(String(data));
}

/** @brief Get serial data in JSON format.
 * 
 * @return A JSON object.
 */
aJsonObject *getAJsonObject() {
  aJsonObject *root;
  char *data = getSerialData();
  root = aJson.parse(data);
  free(data);
  return root;
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
  unsigned short touchInputs = inputs | TI_MASK;
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

/** @brief Get the configuration specified by the type.
 * 
 * @param type A configuration type.
 * @return A configuration.
 */
String getConfiguration(String type) {
  String data;
  String configFile = type + CONF_FILE_EXT;
  unsigned int length = type.length();
  char name [length + 1];
  configFile.toCharArray(name, length + 1);
  data = g1.readFile(name);
  return data;
}

/** @brief Read the serial port.
 * 
 * @return The serial port data.
 */
char *getSerialData() {
  char *data;
  String sdata = "";
  while(Serial.available()) {
    sdata += Serial.read();
  }
  unsigned int length = sdata.length();
  data = (char *) malloc(sizeof(char) * (length + 1));
  sdata.toCharArray(data, length + 1);
  return data;
}

/** @brief Identify the chanter to the configuration application when required.
 * 
 * @return A boolean indicating if the chanter has been identified.
 */
boolean identifyChanter() {
  boolean received = findDiscoveryBeacon();
  if (received) {
    sendDeviceSerialNumber();
  }
  return received;
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
    size = sizeof(aberto)/(2*sizeof(short));
    for (int i = 0; i < size; i++) {
      offsets[i][0] = aberto[i][0];
      offsets[i][1] = aberto[i][1];
    }
  }
  // Pechado.
  if (fingeringTypes[1]) {
    size = sizeof(pechado)/(2*sizeof(short));
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

/** @brief Check if the pressure is enough to make the chanter sound.
 * 
 * @return A boolean indicating if the pressure is enough.
 */
boolean isBagPressureEnough() {
  return !isBagEnabled || (bmp085.calPressure() >= minBagPressure);
}

/** @brief Check if it is asking for the expected configuration.
 * 
 * @param root A device configuration.
 * @param expectedAction A configuration action.
 * @return A boolean indicating if it is the expected configuration.
 */
boolean isConfiguration(aJsonObject* root, String expectedAction) {
  aJsonObject* aJsonAction = aJson.getObjectItem(root, "action");
  String action = String(aJsonAction->valuestring);
  aJson.deleteItem(aJsonAction);
  return expectedAction.equals(action);
}

/** @brief Check if the destination of the configuration data is this bagpipe.
 * 
 * Each Puding bagpipe has an unique serial product identificator number.
 * This id is used to identify the bagpipe due to is possible to have more
 * than one bagpipe connected to the same receiver.
 * 
 * @param root A JSON object.
 * @return A boolean indicating if this bagpipe is the receiver.
 */
boolean isMeTheTarget(aJsonObject *root) {
    boolean isMeTheTarget;
    aJsonObject* aJsonProductId = aJson.getObjectItem(root, "productId");
    String productId = String(aJsonProductId->valuestring);
    aJson.deleteItem(aJsonProductId);
    isMeTheTarget = String(PUDING_SN).equals(productId);
    return isMeTheTarget;
}

/** @brief Check if the vibrato is enabled.
 * 
 * The vibrato is simulated with the proximity sensor.
 * 
 * @return A boolean indicating if the vibrato is enabled.
 */
boolean isVibratoEnabled() {
  unsigned short vibrato = inputs | PI_MASK;
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
  
  if (!isBagPressureEnough()) { // Stop.
    MIDI.sendNoteOff(chanterNote, velocity, chanterChannel);
    if (isVibratoEnabled()) {
      MIDI.sendPitchBend(0, chanterChannel);
    }
    if (isBassDroneEnabled) {
      MIDI.sendNoteOff(bassDroneNote, velocity, dronesChannel);
    }
    if (isTenorDroneEnabled) {
      MIDI.sendNoteOff(tenorDroneNote, velocity, dronesChannel);
    }
    if (isHighDroneEnabled) {
      MIDI.sendNoteOff(highDroneNote, velocity, dronesChannel);
    }
  }
}

/** @brief Make the chanter sound.
 * 
 */
void playChanter() {
  chanterOffset = getChanterOffset();
  newChanterNote = getChanterNote();
  
  // Stop if there is not enough bag pressure, or the note changes or the new fingering is not recognized.
  if (!isBagPressureEnough() || chanterOffset == DEF_OFFSET || newChanterNote != chanterNote) {
    MIDI.sendNoteOff(chanterNote, velocity, chanterChannel);
    if (isVibratoEnabled()) {
      MIDI.sendPitchBend(0, chanterChannel);
    }
  }
  
  // Play if there is enough bag pressure, the note changes and the new fingering is recognized.
  if (isBagPressureEnough() && chanterOffset != DEF_OFFSET && newChanterNote != chanterNote) {
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
  if (isBagPressureEnough() && isBassDroneEnabled) {
    if (bassDroneNote != getBassDroneNote()) {
      MIDI.sendNoteOff(bassDroneNote, velocity, dronesChannel);
      bassDroneNote = getBassDroneNote();
      MIDI.sendNoteOn(bassDroneNote, velocity, dronesChannel);
    }
  } else {
    MIDI.sendNoteOff(bassDroneNote, velocity, dronesChannel);
  }
  
  if (isBagPressureEnough() && isTenorDroneEnabled) {
    if (tenorDroneNote != getTenorDroneNote()) {
      MIDI.sendNoteOff(tenorDroneNote, velocity, dronesChannel);
    }
    tenorDroneNote = getTenorDroneNote();
    MIDI.sendNoteOn(tenorDroneNote, velocity, dronesChannel);
  } else {
    MIDI.sendNoteOff(tenorDroneNote, velocity, dronesChannel);
  }
  
  if (isBagPressureEnough() && isHighDroneEnabled) {
    if (highDroneNote != getHighDroneNote()) {
      MIDI.sendNoteOff(highDroneNote, velocity, dronesChannel);
    }
    highDroneNote = getHighDroneNote();
    MIDI.sendNoteOn(highDroneNote, velocity, dronesChannel);
  } else {
    MIDI.sendNoteOff(highDroneNote, velocity, dronesChannel);
  }
}

/** @brief Read the bagpipe configuration from file.
 * 
 * @param type A configuration type.
 * @return A JSON object containing the configuration.
 */
aJsonObject *readFile(String type) {
  aJsonObject *root;
  type += CONF_FILE_EXT;
  unsigned int length = type.length();
  char name [length + 1];
  type.toCharArray(name, length + 1);
  String fdata = g1.readFile(name);
  length = fdata.length();
  char *cdata = (char *) malloc(sizeof(char) * (length + 1));
  root = aJson.parse(cdata);
  free(cdata);
  return root;
}

/** @brief Restore the configuration file related with the provided type.
 * 
 * Depending of the action it can be restored to the previous or the default
 * configuration file.
 * 
 * @param action An action to do.
 * @param type A configuration type.
 */
void restoreFile(String action, String type) {
  String backupFile = type;
  if (String(ACTION_REVERT).equals(action)) {
    backupFile += BAK_FILE_EXT;
  } else if (String(ACTION_DEFAULT).equals(action)) {
    backupFile += DEF_FILE_EXT;
  }
  unsigned int bfLength = backupFile.length();
  char fromFile [bfLength + 1];
  backupFile.toCharArray(fromFile, bfLength + 1);
  String configFile = type + CONF_FILE_EXT;
  unsigned int cfLength = configFile.length();
  char toFile [cfLength + 1];
  configFile.toCharArray(toFile, cfLength + 1);
  g1.copyFile(fromFile, toFile);
}

/** @brief Save the configuration file related with the provided type.
 * 
 * @param type A configuration type.
 * @param root A JSON object containing the file data.
 */
void saveFile(String type, aJsonObject *root) {
  backupFile(type);
  String configFile = type + CONF_FILE_EXT;
  unsigned int cfLength = configFile.length();
  char fileName [cfLength + 1];
  configFile.toCharArray(fileName, cfLength + 1);
  char *cdata = aJson.print(root);
  String sdata = String(cdata);
  free(cdata);
  g1.writeFile(fileName, false, sdata);
}

/** @brief Send the current configuration to the configuration application.
 * 
 */
void sendConfiguration(aJsonObject* root) {
  
  aJsonObject* aJsonType = aJson.getObjectItem(root, "type");
  String type = String(aJsonType->valuestring);
  aJson.deleteItem(aJsonType);
  String configuration = getConfiguration(type);
  Serial.print(configuration);
}

/** @brief Send the device serial number to the configuration application.
 * 
 */
void sendDeviceSerialNumber() {
  aJsonObject *root = aJson.createObject();
  aJson.addStringToObject(root, "productId", PUDING_SN);
  char *croot = aJson.print(root);
  Serial.write(croot);
}

/** @brief Configure the minimum bag pressure.
 * 
 * @param bagPressure Value between 1 and 100 where 50 is the default minimum
 * bag pressure.
 */
void setMinBagPressure(byte bagPressure) {
  // TODO Define a step based on the measurements inside the bag.
  byte avg = 50;
  unsigned int step = 100;
  minBagPressure = DEF_MBP + (bagPressure - avg) * step;
}

/** @brief Configure all the bagpipe params based on the received JSON object.
 * 
 * @param root A JSON object containing the configuration data.
 */
void setConfiguration(aJsonObject *root) {
  aJsonObject *aJsonAction = aJson.getObjectItem(root, "action");
  String action = String(aJsonAction->valuestring);
  aJson.deleteItem(aJsonAction);
  
  aJsonObject *aJsonSubtype = aJson.getObjectItem(root, "type");
  String type = String(aJsonSubtype->valuestring);
  aJson.deleteItem(aJsonSubtype);
  
  setConfiguration(action, type, root);
}

/** @brief Configure all the bagpipe params based on the received JSON object.
 * 
 * @param action An action to do.
 * @param type A configuration type.
 * @param root A JSON object containing the configuration data.
 */
void setConfiguration(String action, String type, aJsonObject *root) {
  if (String(ACTION_NEW).equals(action)) {
    saveFile(type, root);
  } else if (String(ACTION_REVERT).equals(action) ||
      String(ACTION_DEFAULT).equals(action)) {
    restoreFile(action, type);
  }
  
  setConfiguration(type);
}

/** @brief Configure all the bagpipe params based on the received type.
 * 
 * @param type A configuration type.
 */
void setConfiguration(String type) {
  aJsonObject *root = readFile(type);
  setConfigurationData(type, root);
  aJson.deleteItem(root);
}

/** @brief Configure all the bagpipe params based on the received JSON object.
 * 
 * @param type A configuration type.
 * @param root A JSON object containing the configuration data.
 */
void setConfigurationData(String type, aJsonObject *root) {
  if (String(TYPE_START).equals(type)) {
    setConfigurationDataTypeStart(root);
  } else if (String(TYPE_SELECT).equals(type)) {
    setConfigurationDataTypeSelect(root);
  } else if (String(TYPE_TUNING).equals(type)) {
    setConfigurationDataTypeTuning(root);
  } else if (String(TYPE_SENSIT).equals(type)) {
    setConfigurationDataTypeSensit(root);
  } else if (String(TYPE_FINGER).equals(type)) {
    setConfigurationDataTypeFinger(root);
  }
}

/** @brief Configure the bagpipe params related with the fingerings.
 * 
 * @param root A JSON object containing the configuration data.
 */
void setConfigurationDataTypeFinger(aJsonObject *root) {
  // Reset offsets.
  initializeOffsets();
  aJsonObject *fingeringsArray = aJson.getObjectItem(root, "fingerings");
  unsigned char size = aJson.getArraySize(fingeringsArray);
  aJsonObject *itemObject = NULL;
  aJsonObject *fingeringObject = NULL;
  aJsonObject *offsetObject = NULL;
  short fingering;
  short offset;
  for (int i = 0; i < (int) size; i++) {
    itemObject = aJson.getArrayItem(fingeringsArray, i);
    fingeringObject = aJson.getObjectItem(itemObject, "fingering");
    offsetObject = aJson.getObjectItem(itemObject, "offset");
    fingering = (short) fingeringObject->valueint;
    offset = (short) offsetObject->valueint;
    for (int j = 0; j < MAX_FING; j++) {
      // Replace.
      if (offsets[j][0] == fingering) {
        offsets[j][1] = offset;
        break;
      }
      // Add.
      if (offsets[j][1] == DEF_OFFSET) {
        offsets[j][0] = fingering;
        offsets[j][1] = offset;
        break;
      }
    }
  }
}

/** @brief Configure the bagpipe params related with the selection screen.
 * 
 * @param root A JSON object containing the configuration data.
 */
void setConfigurationDataTypeSelect(aJsonObject *root) {
  aJsonObject *volumeObject = aJson.getObjectItem(root, "volume");
  aJsonObject *fingeringTypesArray = aJson.getObjectItem(root, "fingeringTypes");
  aJsonObject *bagEnabledObject = aJson.getObjectItem(root, "bagEnabled");
  aJsonObject *dronesEnabledArray = aJson.getObjectItem(root, "dronesEnabled");
  
  byte volume = byte(volumeObject->valueint);
  aJsonObject *fingeringTypeObject = NULL;
  for (int i = 0; i < DEF_FT; i++) {
    fingeringTypeObject = aJson.getArrayItem(fingeringTypesArray, i);
    fingeringTypes[i] = (fingeringTypeObject->valuebool == 1);
  }
  boolean bagEnabled = (bagEnabledObject->valuebool == 1);
  boolean dronesEnabled[DEF_DN];
  aJsonObject *droneEnabledObject = NULL;
  for (int i = 0; i < DEF_DN; i++) {
    droneEnabledObject = aJson.getArrayItem(dronesEnabledArray, i);
    dronesEnabled[i] = (droneEnabledObject->valuebool == 1);
  }
  
  velocity = volume;
  setFingerings();
  isBassDroneEnabled = dronesEnabled[0];
  isTenorDroneEnabled = dronesEnabled[1];
  isHighDroneEnabled = dronesEnabled[2];
}

/** @brief Configure the bagpipe params related with the sensitivity screen.
 * 
 * @param root A JSON object containing the configuration data.
 */
void setConfigurationDataTypeSensit(aJsonObject *root) {
  aJsonObject *bagPressureObject = aJson.getObjectItem(root, "bagPressure");
  byte bagPressure = byte(bagPressureObject->valueint);
  setMinBagPressure(bagPressure);
}

/** @brief Configure the bagpipe params related with the start screen.
 * 
 * @param root A JSON object containing the configuration data.
 */
void setConfigurationDataTypeStart(aJsonObject *root) {
  // Skip.
}

/** @brief Configure the bagpipe params related with the tuning screen.
 * 
 * @param root A JSON object containing the configuration data.
 */
void setConfigurationDataTypeTuning(aJsonObject *root) {
  aJsonObject *toneObject = aJson.getObjectItem(root, "tone");
  aJsonObject *octaveObject = aJson.getObjectItem(root, "octave");
  
  byte tone = byte(toneObject->valueint);
  short octave = octaveObject->valueint;
  
  // Tone is between 0-11.
  // It is the number of the semitone into a semitones scale.
  baseTone = DEF_TONE + tone * octave;
}

/** @brief Setup the bagpipe.
 * 
 * Initialize the comunication ports and the configuration parameters.
 */
void setDevice() {
  initializeOffsets();
  Serial.begin(SERIAL_VEL);
  delay(STARTUP_DELAY);
  g1.setDevice();
  mpr121.setDevice();
  bmp085.setDevice();
  delay(STARTUP_DELAY);
  MIDI.begin();
}

/** @brief Configure the bagpipe allowed fingerings.
 * 
 */
void setFingerings() {
  initializeOffsets();
  setConfiguration(String(TYPE_FINGER));
}

/** @brief Wait for ACK from the configuration application.
 * 
 */
void waitForAck() {
  while(!Serial.available()) {
  }
  while(true) {
    aJsonObject* root = getAJsonObject();
    if (isMeTheTarget(root)) {
      aJson.deleteItem(root);
      return;
    }
    aJson.deleteItem(root);
  }
}
