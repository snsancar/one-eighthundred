package com.aconex.oneeighthundred.input;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FileInputReader {

	private final static Logger LOGGER = Logger.getLogger(FileInputReader.class.getName());

	public static Stream<String> getFileContent(String filePath) {
		try {
			return Files.lines(Paths.get(filePath));
		} catch (IOException e) {
			LOGGER.warning("File " + filePath + " could not be read, skipping.");
			return Stream.empty();
		}
	}
}
