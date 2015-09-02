package main.model.utils;

public class FileDownloader {
	
	/**
	 * Download an URL content to a file into a separated thread.
	 * @param srcUrl Source URL.
	 * @param destFile Destination file.
	 * @return The file download so its status can be checked at any time.
	 */
	public static FileDownload downloadFile(String srcUrl, String destFile) {
		
		FileDownload fileDownload = new FileDownload(srcUrl, destFile);
		
		Thread t = new Thread(fileDownload);
		t.start();
		
		return fileDownload;
	}

}
