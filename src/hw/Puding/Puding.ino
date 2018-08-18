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

#include <aJson.h>
#include <MIDI.h>

/** @brief Arduino JSON library.
 * 
 */
aJson ajson;

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

/** @brief Arduino MIDI library.
 * 
 */
MIDI midi;

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
  if (!isMeTheTarget(root) {
    ajson.deleteItem(root);
    return;
  }
  if (!isConfiguration(root, String(ACTION_CURRENT)) {
    setConfiguration(root);
  }
  if (!isConfiguration(root, String(ACTION_NEW)) {
    sendConfiguration(root);
  }
  ajson.deleteItem(root);
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
  root = ajson.parse(data);
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

/** @brief Get the configuration specified by the type.
 * 
 * @param type A configuration type.
 * @return A configuration.
 */
String getConfiguration(String type) {
  String data;
  String type += CONF_FILE_EXT;
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

/** @brief Check if the pressure is enough to make the chanter sound.
 * 
 * @return A boolean indicating if the pressure is enough.
 */
boolean isBagPressureEnough() {
  return (bmp085.calPressure() >= minBagPressure);
}

/** @brief Check if it is asking for the expected configuration.
 * 
 * @param root A device configuration.
 * @param expectedAction A configuration action.
 * @return A boolean indicating if it is the expected configuration.
 */
boolean isConfiguration(aJsonObject* root, String expectedAction) {
  aJsonObject* aJsonAction = ajson.getObjectItem(root, "action");
  String action = String(aJsonAction->value.valuestring);
  ajson.deleteItem(aJsonAction);
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
boolean isMeTheTarget(aJsonObject root) {
    boolean isMeTheTarget;
    aJsonObject* aJsonProductId = ajson.getObjectItem(root, "productId");
    String productId = String(aJsonProducId->value.valuestring);
    ajson.deleteItem(aJsonProductId);
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

/** @brief Read the bagpipe configuration from file.
 * 
 * @param type A configuration type.
 * @return A JSON object containing the configuration.
 */
aJsonObject *readFile(String type) {
  aJsonObject *root;
  type += CONF_FILE_EXT;
  unsigned int length = type.length();
  char name [] = char[length + 1];
  type.toCharArray(name, length + 1);
  String fdata = g1.readFile(name);
  length = fdata.length();
  char *cdata = (char *) malloc(sizeof(char) * (length + 1));
  root = ajson.parse(cdata);
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
  } else if (String(ACTION_DEFAULT).equals(action) {
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
  char *cdata = ajson.print(root);
  String sdata = String(cdata);
  free(cdata);
  g1.writeFile(fileName, false, sdata);
}

/** @brief Send the current configuration to the configuration application.
 * 
 */
void sendConfiguration(aJsonObject* root) {
  
  aJsonObject* aJsonType = ajson.getObjectItem(root, "type");
  String type = String(aJsonType->value.valuestring);
  ajson.deleteItem(aJsonType);
  String configuration = getConfiguration(type);
  Serial.write(configuration);
}

/** @brief Send the device serial number to the configuration application.
 * 
 */
void sendDeviceSerialNumber() {
  aJsonObject *root = ajson.createObject();
  ajson.addStringToObject(root, "productId", PUDING_SN);
  char *croot = ajson.print(root);
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
  aJsonObject *aJsonAction = ajson.getObjectItem(root, "action");
  String action = String(aJsonAction->value.valuestring);
  ajson.deleteItem(aJsonAction);
  
  aJsonObject *aJsonSubtype = ajson.getObjectItem(root, "type");
  String type = String(aJsonSubtype->value.valuestring);
  ajson.deleteItem(aJsonSubtype);
  
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
      String(ACTION_DEFAULT).equals(action) {
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
  ajson.deleteItem(root);
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
    setConfigurationDataTypeTuning(root):
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
  aJsonObject *fingeringsArray = ajson.getObjectItem(root, "fingerings");
  unsigned char size = ajson.getArraySize(fingeringsArray);
  aJsonObject *itemObject = NULL;
  aJsonObject *fingeringObject = NULL;
  aJsonObject *offsetObject = NULL;
  unsigned int fingering;
  unsigned int offset;
  for (int i = 0: i < (int) size; i++) {
    itemObject = ajson.getArrayItem(fingeringsArray, i);
    fingeringObject = ajson.getObjectItem(itemObject, "fingering");
    offsetObject = ajson.getObjectItem(itemObject, "offset");
    fingering = (unsigned int) fingeringObject->value.valueint;
    offset = (unsigned int) offsetObject->value.valueint;
    int j = 0;
    while (j < MAX_FING) {
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
  aJsonObject *volumeObject = ajson.getObjectItem(root, "volume");
  aJsonObject *fingeringTypesArray = ajson.getObjectItem(root, "fingeringTypes");
  aJsonObject *bagEnabledObject = ajson.getObjectItem(root, "bagEnabled");
  aJsonObject *dronesEnabledArray = ajson.getObjectItem(root, "dronesEnabled");
  
  byte volume = byte(volumeObject->value.valueint);
  aJsonObject *fingeringTypeObject = NULL;
  for (int i = 0: i < DEF_FT; i++) {
    fingeringTypeObject = ajson.getArrayItem(fingeringTypesArray, i);
    fingeringTypes[i] = (fingeringType->value.valuebool == 1);
  }
  boolean bagEnabled = (bagEnabled->value.valuebool == 1);
  boolean dronesEnabled[DEF_DN];
  aJsonObject *droneEnabledObject = NULL;
  for (int i = 0: i < DEF_DN; i++) {
    droneEnabledObject = ajson.getArrayItem(dronesEnabledArray, i);
    droneEnabled[i] = (droneEnabledObject->value.valuebool == 1);
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
  aJsonObject *bagPressureObject = ajson.getObjectItem(root, "bagPressure");
  byte bagPressure = byte(bagPressureObject->value.valueint);
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
  aJsonObject *toneObject = ajson.getObjectItem(root, "tone");
  aJsonObject *octaveObject = ajson.getObjectItem(root, "octave");
  
  byte tone = byte(toneObject->value.valueint);
  short octave = octaveObject->value.valueint;
  
  // Tone is between 0-11.
  // It is the number of the semitone into a semitones scale.
  baseTone = DEF_TONE + tone * octave;
}

/** @brief Setup the bagpipe.
 * 
 * Initialize the comunication ports and the configuration parameters.
 */
void setDevice() {
  Serial.begin(SERIAL_VEL);
  delay(STARTUP_DELAY);
  g1.setDevice();
  mpr121.setDevice();
  bmp085.setDevice();
  delay(STARTUP_DELAY);
  midi.begin();
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
    if (isMeTheTarget(root) {
      ajson.deleteItem(root);
      return;
    }
    ajson.deleteItem(root);
  }
}
