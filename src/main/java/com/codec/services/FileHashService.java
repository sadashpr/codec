package com.codec.services;

import java.io.File;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;


@Service
public class FileHashService{

    public static void writeHash(String hashValue) 
    {
        try{
            FileWriter fileWriter = new FileWriter("samplefile2.txt",true);
            fileWriter.write(hashValue);
            fileWriter.write("\n"); 
            fileWriter.close();
        }
        catch (IOException e)
        {
            System.out.println("file create failed. time to bail out.");
        }
    }
    public static boolean checkHash(String hashValue) 
    {
        String line;
        try {

        BufferedReader bufferreader = new BufferedReader(new FileReader("samplefile2.txt"));

        while ((line = bufferreader.readLine()) != null) {     
            if(line.equalsIgnoreCase(hashValue))
                return false;  // hash found . file not to be processed
            else 
                continue;    
        }
        } catch (FileNotFoundException ex) {
             
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return true;// hash not found . file to be processed 
    }

}
