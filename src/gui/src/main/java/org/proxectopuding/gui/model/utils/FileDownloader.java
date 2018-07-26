package org.proxectopuding.gui.model.utils;

import com.google.inject.Inject;

public class FileDownloader {
	
	private FileDownload fileDownload;
	
	@Inject
	public FileDownloader(FileDownload fileDownload) {
		
		this.fileDownload = fileDownload;
	}
	
	/**
	 * Download an URL content to a file into a separated thread.
	 * @param srcUrl Source URL.
	 * @param destFile Destination file.
	 * @return The file download so its status can be checked at any time.
	 */
	public FileDownload downloadFile(String srcUrl, String destFile) {
		
		fileDownload.setSource(srcUrl);
		fileDownload.setDestination(destFile);
		
		Thread t = new Thread(fileDownload);
		t.start();
		
		return fileDownload;
	}

}
