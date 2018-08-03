package org.proxectopuding.gui.model.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.inject.Inject;

public class SoundFontManager {
	
	private static final Logger LOGGER = Logger.getLogger(SoundFontManager.class.getName());
	
	private static final String SOUNDFONT_URL =
			"https://drive.google.com/file/d/0BxNAQKTkohGMOC1jUmFGU1ZZZGc";
	private static final String SOUNDFONT_FILE_NAME = "FluidR3_GM.sf2";
	private static final String SOUNDFONT_FILE_PATH =
			"sounds/" + SOUNDFONT_FILE_NAME;
	
	private final FileDownloader fileDownloader;
	private final FileUtils fileUtils;
	private FileDownload fileDownload;
	
	@Inject
	public SoundFontManager(FileDownloader fileDownloader,
			FileUtils fileUtils) {
		
		this.fileDownloader = fileDownloader;
		this.fileUtils = fileUtils;
	}
	
	/**
	 * Check if the SoundFont file is downloaded.
	 * @return A boolean indicating if the file is downloaded.
	 * @throws IOException If the SoundFont file is still being downloaded.
	 */
	public boolean isSoundFontFileDownloaded() throws IOException {
		
		boolean isDownloaded = false;
		
		if (fileDownload != null) {
		
			// Avoid using real samples when the SoundFont file is unavailable.
			if (fileDownload.isDownloading()) {
				throw new IOException("The SoundFont file is still being downloaded.");
			}
			
			if (fileDownload.isDownloaded()) {
				File file = new File(SOUNDFONT_FILE_PATH);
				isDownloaded = file.exists();
			}
		}
		
		return isDownloaded;
	}
	
	/**
	 * Download the SoundFont file to use from the Internet.
	 */
	public void downloadSoundFontFile() {
		
		fileDownload = fileDownloader.downloadFile(
				SOUNDFONT_URL, SOUNDFONT_FILE_PATH);
	}
	
	/**
	 * Check if the SoundFont file is placed into the provided directory.
	 * @param destDir Destination directory where the SoundFont file should be
	 * placed in.
	 * @return A boolean indicating if the SoundFont is placed into the
	 * destination directory. 
	 */
	public boolean isSoundFontFileCopied(String destDir) {
		
		boolean isPlaced = false;
		
		String soundFontFilePath = getSoundFontFileDestinationPath(destDir); 
		
		if (soundFontFilePath != null) {
			File destFile = new File(soundFontFilePath);
			isPlaced = destFile.exists();
		}
		
		return isPlaced;
	}
	
	/**
	 * Copy the SoundFont file into the destination directory.
	 * @param destDir Destination directory.
	 * @return The path where the SoundFont file is placed in. Null otherwise.
	 * @throws IOException If a problem comes up when copying the file.
	 */
	public String copySoundFontFile(String destDir) throws IOException {
		
		String soundFontFilePath = getSoundFontFileDestinationPath(destDir); 
		
		if (soundFontFilePath != null) {
			try {
				fileUtils.copyFileToDirectory(SOUNDFONT_FILE_PATH, destDir);
			} catch (IOException e) {
				soundFontFilePath = null;
				LOGGER.log(Level.SEVERE, "Unable to copy tthe SoundFont file to directory: {0}", destDir);
				LOGGER.log(Level.SEVERE, "Unable to copy tthe SoundFont file to directory", e);
				throw e;
			}
		}
		
		return soundFontFilePath;
	}
	
	/**
	 * Get the path to the copy of the SoundFont file placed into the
	 * destination directory.
	 * @param destDir Destination directory.
	 * @return The path to the copy of the SoundFont file placed into the
	 * destination directory. Null otherwise.
	 */
	private String getSoundFontFileDestinationPath(String destDir) {
		
		String soundFontFilePath = null;
		
		try {
			File destDirectory = new File(destDir);
			soundFontFilePath = destDirectory.getCanonicalPath();
			if (!soundFontFilePath.endsWith(File.separator)) {
				soundFontFilePath += File.separator;
			}
			soundFontFilePath += SOUNDFONT_FILE_NAME;
		} catch (IOException e) {
			soundFontFilePath = null;
			LOGGER.log(Level.SEVERE, "Unable to get the SoundFont file destination path", e);
		}
		
		return soundFontFilePath;
	}

}
