/*
 * Proxecto Puding
 * Copyright (C) 2011 Alejo Pacín <info@proxecto-puding.org>
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

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.proxectopuding.gui.model.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.inject.Inject;

public class FileDownload implements Runnable {
	
	private static final Logger LOGGER = Logger.getLogger(FileDownload.class.getName());
	
	private final FileUtils fileUtils;
	private String srcUrl;
	private String destFile;
	private boolean isDownloading;
	private boolean isDownloaded;
	
	@Inject
	public FileDownload(FileUtils fileUtils) {
		
		this.fileUtils = fileUtils;
		this.isDownloading = false;
		this.isDownloaded = false;
	}
	
	/**
	 * Set the source URL.
	 * @param srcUrl Source URL.
	 */
	public void setSource(String srcUrl) {
		
		this.srcUrl = srcUrl;
	}
	
	/**
	 * Set the destination file.
	 * @param destFile Destination file.
	 */
	public void setDestination(String destFile) {
		
		this.destFile = destFile;
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
			fileUtils.copyUrlToFile(srcUrl, destFile);
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
