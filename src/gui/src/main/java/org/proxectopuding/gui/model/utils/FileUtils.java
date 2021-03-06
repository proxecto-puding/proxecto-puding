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

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUtils {
	
	private static final Logger LOGGER = Logger.getLogger(FileUtils.class.getName());
	
	/**
	 * Copy a file to a directory.
	 * @param srcFile Source file.
	 * @param destDir Destination directory.
	 * @return The path to the copied file in the new destination.
	 * @throws IOException If a problem comes up when copying the file.
	 */
	public String copyFileToDirectory(String srcFile, String destDir)
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
			LOGGER.log(Level.SEVERE, "Unable to copy file: {0} to directory: {1}", new String[]{srcFile, destDir});
			LOGGER.log(Level.SEVERE, "Unable to copy file to directory", e);
			throw e;
		}
		
		return destFilePath;
	}
	
	/**
	 * Copy an URL content to a file.
	 * @param srcUrl Source URL.
	 * @param destFile Destination file.
	 * @throws IOException If a problem comes up when copying.
	 * @throws MalformedURLException If the URL is malformed.
	 */
	public void copyUrlToFile(String srcUrl, String destFile)
			throws IOException {
		
		try {
			URL source = new URL(srcUrl);
			File destination = new File(destFile);
			org.apache.commons.io.FileUtils.copyURLToFile(source, destination);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Unable to copy URL: {0} to file: {1}", new String[]{srcUrl, destFile});
			LOGGER.log(Level.SEVERE, "Unable to copy URL to file", e);
			throw e;
		}
	}
	
	/**
	 * Read all lines from from the provided file.
	 * @param file File to read from.
	 * @return All the lines content in the file.
	 * @throws IOException If a problem comes up when reading.
	 */
	public List<String> readLines(File file) throws IOException {
		
		return org.apache.commons.io.FileUtils.readLines(file, "UTF-8");
	}
	
	/**
	 * Write the provided lines to the provided file.
	 * @param file File to write to.
	 * @param lines Lines to write.
	 * @param append Indicate if the file is going to be overwritten or not.
	 * @throws IOException If a problem comes up when writing.
	 */
	public void writeLines(File file, Collection<?> lines,
			boolean append) throws IOException {
		
		org.apache.commons.io.FileUtils.writeLines(file, lines, append);
	}

}
