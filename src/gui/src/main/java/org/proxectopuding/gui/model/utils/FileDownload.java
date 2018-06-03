package org.proxectopuding.gui.model.utils;

import java.io.IOException;
import java.net.MalformedURLException;

public class FileDownload implements Runnable {
	
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
			System.err.println("Error while downloading URL '" + srcUrl +
					"' to file '" + destFile + "'." +
					" Message: " + e.getMessage());
			e.printStackTrace();
		} finally {
			isDownloading = false;
		}
	}
		
	/**
	 * Download an URL content to a file.
	 * @param srcUrl Source URL.
	 * @param destFile Destination file.
	 * @throws IOException If a problem comes up when copying.
	 * @throws MalformedURLException If the URL is malformed.
	 */
	private void download(String srcUrl, String destFile)
			throws IOException, MalformedURLException {
		
		try {
			FileUtils.copyUrlToFile(srcUrl, destFile);
		} catch (MalformedURLException e) {
			System.err.println("Error while downloading URL '" + srcUrl +
					"' to file '" + destFile + "'." +
					" Message: " + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			System.err.println("Error while downloading URL '" + srcUrl +
					"' to file '" + destFile + "'." +
					" Message: " + e.getMessage());
			e.printStackTrace();
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
