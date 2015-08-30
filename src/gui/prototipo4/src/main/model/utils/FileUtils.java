package main.model.utils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class FileUtils {
	
	/**
	 * Copy a file to a directory.
	 * @param srcFile Source file.
	 * @param destDir Destination directory.
	 * @return The path to the copied file in the new destination.
	 * @throws IOException If a problem comes up when copying the file.
	 */
	public static String copyFileToDirectory(String srcFile, String destDir)
			throws IOException {
		
		String destFilePath = null;
		
		File src = new File(srcFile);
		File dest = new File(destDir);
		try {
			org.apache.commons.io.FileUtils.copyFileToDirectory(src, dest);
			destFilePath = dest.getCanonicalPath();
			if (!destFilePath.endsWith(File.separator)) {
				destFilePath += File.separator;
			}
			destFilePath += src.getName();
		} catch (IOException e) {
			System.err.println("Error while copying file '" + srcFile +
					"' to directory '" + destDir + "'." +
					" Message: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		
		return destFilePath;
	}
	
	/**
	 * Read all lines from from the provided file.
	 * @param file File to read from.
	 * @return All the lines content in the file.
	 * @throws IOException If a problem comes up when reading.
	 */
	public static List<String> readLines(File file) throws IOException {
		return org.apache.commons.io.FileUtils.readLines(file, "UTF-8");
	}
	
	/**
	 * Write the provided lines to the provided file.
	 * @param file File to write to.
	 * @param lines Lines to write.
	 * @param append Indicate if the file is going to be overwritten or not.
	 * @throws IOException If a problem comes up when writing.
	 */
	public static void writeLines(File file, Collection<?> lines,
			boolean append) throws IOException {
		org.apache.commons.io.FileUtils.writeLines(file, lines, append);
	}

}
