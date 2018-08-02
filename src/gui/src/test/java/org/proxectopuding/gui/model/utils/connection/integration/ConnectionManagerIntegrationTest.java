package org.proxectopuding.gui.model.utils.connection.integration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;
import org.proxectopuding.gui.model.entities.midiServer.MidiServer;
import org.proxectopuding.gui.model.entities.midiServer.MidiServerGeneral;
import org.proxectopuding.gui.model.entities.midiServer.MidiServerUnix;
import org.proxectopuding.gui.model.services.MidiService;
import org.proxectopuding.gui.model.services.impl.MidiServiceImpl;
import org.proxectopuding.gui.model.utils.FileDownload;
import org.proxectopuding.gui.model.utils.FileDownloader;
import org.proxectopuding.gui.model.utils.FileUtils;
import org.proxectopuding.gui.model.utils.MidiUtils;
import org.proxectopuding.gui.model.utils.OperativeSystemManager;
import org.proxectopuding.gui.model.utils.SoundFontManager;
import org.proxectopuding.gui.model.utils.connection.ConnectionManager;
import org.proxectopuding.gui.model.utils.connection.ConnectionManagerJsscImpl;

public class ConnectionManagerIntegrationTest {
	
	private static final Logger LOGGER = Logger.getLogger(ConnectionManagerIntegrationTest.class.getName());
	
	private FileUtils fileUtils = new FileUtils();
	private FileDownload fileDownload = new FileDownload(fileUtils);
	private FileDownloader fileDownloader = new FileDownloader(fileDownload);
	private SoundFontManager soundFontManager =
			new SoundFontManager(fileDownloader, fileUtils);
	private OperativeSystemManager operativeSystemManager =
			new OperativeSystemManager();
	private MidiUtils midiUtils = new MidiUtils();
	private MidiServerGeneral midiServerGeneral =
			new MidiServerGeneral(soundFontManager, operativeSystemManager,
					fileUtils, midiUtils);
	private MidiServer midiServer = new MidiServerUnix(midiServerGeneral);
	private MidiService midiService = new MidiServiceImpl(midiServer);
	private ConnectionManager connectionManager =
			new ConnectionManagerJsscImpl(operativeSystemManager, midiService);

	/* Ping.ino
	
	 	void setup() {
		  
		  Serial.begin(9600);
		}
		
		void loop() {
		  
		  Serial.println("ping");
		  delay(1000);
		}
	 */
	@Test
	public void readFromDevice() {
		
		try {
			connectionManager.connect();
			String data = null;
			for (int i = 0; i < 10; i++) {
				LOGGER.log(Level.INFO, "Attempt: {0}", i + 1);
				data = connectionManager.readData(false);
				if (data != null) {
					break;
				}
			}
			assertNotNull(data);
		} catch (IOException e) {
			fail(e.getMessage());
		} finally {
			connectionManager.disconnect();
		}
	}
	
	/* PingPong.ino
	 
		void setup() {
		  
		  Serial.begin(9600);
		}
		
		void loop() {
		  
		  String data = Serial.readString();
		  delay(1000);
		  Serial.print(data);
		}
	 */
	@Test
	public void writeToAndReadFromDevice() {
		
		try {
			connectionManager.connect();
			String data = null;
			for (int i = 0; i < 10; i++) {
				LOGGER.log(Level.INFO, "Attempt: {0}", i + 1);
				connectionManager.writeData("ping", false);
				Thread.sleep(2000);
				data = connectionManager.readData(false);
				if (data != null) {
					break;
				}
			}
			assertNotNull(data);
		} catch (Exception e) {
			fail(e.getMessage());
		} finally {
			connectionManager.disconnect();
		}
	}
}
