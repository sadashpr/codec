package com.codec.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.File;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class FileServiceTest
{
    @Autowired
    private FileService fileservice;

    @Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		fileservice= new FileService("/");

	}

    @Test
    public void testGetListOfFiles()
    {
        List<String> files = fileservice.getListOfFiles();
        assertEquals(files.size(),0);

    }

    //@Test   disable comment to run this test. But needs a valid path varaible like shown below.
    public void testGetListOfFiles2()
    {
        FileService fs = new FileService("/Users/pradeep.sadashiv/Documents/test/assignment_files");
		fs.listFilesForFolder(fs.folderPath);
        List<String> files = fs.getListOfFiles();
        assertThat(files.size(),not(0));
    }
}