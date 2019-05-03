package com.codec.services;

import java.io.File;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService{
    List<String> listOfFiles;
    public File folderPath;

    public List<String>getListOfFiles()
    {
        return listOfFiles;
    }
    public FileService(String path)
    {
        folderPath= new File(path);
        listOfFiles = new ArrayList<String>();
    }

    public void listFilesForFolder(final File folder) 
    {
        for (final File fileEntry : folder.listFiles()) {
            if(fileEntry.getName().startsWith(".")== false ){
                {
                    if (fileEntry.isDirectory() ) 
                    {
                        listFilesForFolder(fileEntry);
                    } 
                    else 
                    {
                        listOfFiles.add(folder+"/"+fileEntry.getName());
                    }
                }
            }
        }
    }
    public void printListOfFiles()
    {
        for(String s : listOfFiles)
            System.out.println(s);
    }
}
