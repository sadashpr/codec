package com.codec.codec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.codec.services.FileService;
import com.codec.services.*;

@SpringBootApplication
public class CodecApplication {

	public static void main(String[] args) {
		FileService fs = new FileService(args[0]);
		fs.listFilesForFolder(fs.folderPath);
		//fs.printListOfFiles();
		AudioConverter.convertFiles(fs.getListOfFiles());
		//FileHashService.writeHash("abcd");
		//FileHashService.writeHash("abcd2");
		//FileHashService.writeHash("abcd33");

		//System.out.println(FileHashService.checkHash("abcd"));
		SpringApplication.run(CodecApplication.class, args);
	}
}
