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
import java.io.InputStreamReader;


@Service
public class FileHashService{

    public static void writeHash(String hashValue) 
    {
        try{
            //hard coded file name. Ideally can pick from ENV variables.
            FileWriter fileWriter = new FileWriter("processedlogfile.txt",true);  
            fileWriter.write(hashValue);
            fileWriter.write("\n"); 
            fileWriter.close();
        }
        catch (IOException e)
        {
            System.out.println("processed log file create/write failed.");
        }
    }
    public static boolean checkHash(String hashValue) 
    {
        String line;
        try 
        {
            //hard coded file name. Ideally can pick from ENV variables.
            BufferedReader bufferreader = new BufferedReader(new FileReader("processedlogfile.txt"));
            while ((line = bufferreader.readLine()) != null) 
            {     
                if(line.equalsIgnoreCase(hashValue))
                    return false;  // hash found in processed logfile. File not to be processed
                else 
                    continue;    
            }
        } catch (FileNotFoundException ex) {
            System.out.println("unable to read processed log file. File may get reprocessed.");
            return true;
        } catch (IOException ex) {
            System.out.println("unable to read processed log file. File may get reprocessed.");
            return true;
        }
        return true;// hash not found . file has to be processed 
    }

    public static String getHash(String file)
    {
    String s,hash = "";
    try{
            String command1 = "ffhash md5 " +file;
            Process proc=Runtime.getRuntime().exec(command1);
            
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            while ((s = stdInput.readLine()) != null) {
                hash =s;
            }
        }
        catch(java.io.IOException e){
            System.out.println("Could not get hash of the file.returning empty string.");
    }
    return hash;
    }
}
