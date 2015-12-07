package main.model.utils;

import java.io.File;
import java.io.IOException;

public class SoundFontManager {
	
	private static final String SOUNDFONT_URL = "https://goo.gl/uNtY5u";
	private static final String SOUNDFONT_FILE_PATH = "sounds/FluidR3_GM.sf2";
	private static final String SOUNDFONT_FILE_NAME = "FluidR3_GM.sf2";
	
	private static FileDownload fileDownload;
	
	/**
	 * Check if the SoundFont file is downloaded.
	 * @return A boolean indicating if the file is downloaded.
	 * @throws IOException If the SoundFont file is still being downloaded.
	 */
	public static boolean isSoundFontFileDownloaded() throws IOException {
		
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
	public static void downloadSoundFontFile() {
		
		fileDownload = FileDownloader.downloadFile(
				SOUNDFONT_URL, SOUNDFONT_FILE_PATH);
		
	}
	
	/**
	 * Check if the SoundFont file is placed into the provided directory.
	 * @param destDir Destination directory where the SoundFont file should be
	 * placed in.
	 * @return A boolean indicating if the SoundFont is placed into the
	 * destination directory. 
	 */
	public static boolean isSoundFontFileCopied(String destDir) {
		
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
	public static String copySoundFontFile(String destDir) throws IOException {
		
		String soundFontFilePath = getSoundFontFileDestinationPath(destDir); 
		
		if (soundFontFilePath != null) {
			try {
				// TODO Test this line carefully. SF2 file could not be found this way.
				FileUtils.copyFileToDirectory(SOUNDFONT_FILE_PATH, destDir);
			} catch (IOException e) {
				soundFontFilePath = null;
				System.err.println("Error while copying the SoundFont file to directory '" +
						destDir + "'." +
						" Message: " + e.getMessage());
				e.printStackTrace();
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
	private static String getSoundFontFileDestinationPath(String destDir) {
		
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
			System.err.println(
					"Error while getting the SoundFont file destination path." +
					" Message: " + e.getMessage());
			e.printStackTrace();
		}
		
		return soundFontFilePath;
	}

}
