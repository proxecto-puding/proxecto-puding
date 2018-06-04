package org.proxectopuding.gui.model.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileDownload implements Runnable {
	
	private static final Logger LOGGER = Logger.getLogger(FileDownload.class.getName());
	
	private String srcUrl;
	private String destFile;
	private boolean isDownloading;
	private boolean isDownloaded;
	
	public FileDownload(String srcUrl, String destFile) {
		this.srcUrl = srcUrl;
		this.destFile = destFile;
		this.isDownloading = false;
		this.isDownloaded = false;
	}

	@Override
	public void run() {
		try {
			isDownloading = true;
			download(srcUrl, destFile);
			isDownloaded = true;
		} catch (Exception e) {
			isDownloaded = false;
			LOGGER.log(Level.SEVERE, "Unable to download URL: {0} to file: {1}", new String[]{srcUrl, destFile});
			LOGGER.log(Level.SEVERE, "Unable to download", e);
		} finally {
			isDownloading = false;
		}
	}
		
	/**
	 * Download an URL content to a file.
	 * @param srcUrl Source URL.
	 * @param destFile Destination file.
	 * @throws IOException If a problem comes up when copying or the URL is
	 * malformed.
	 */
	private void download(String srcUrl, String destFile)
			throws IOException {
		
		try {
			FileUtils.copyUrlToFile(srcUrl, destFile);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Unable to download URL: {0} to file: {1}", new String[]{srcUrl, destFile});
			LOGGER.log(Level.SEVERE, "Unable to download", e);
			throw e;
		}
	}
	
	/**
	 * Check if a download is still ongoing.
	 * @return A boolean indicating if the download is still ongoing.
	 */
	public boolean isDownloading() {
		return isDownloading;
	}
	
	/**
	 * Check if a download has properly finished.
	 * @return A boolean indicating if the download is properly finished.
	 */
	public boolean isDownloaded() {
		return isDownloaded;
	}

}