package com.codec.services;

import java.io.File;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.InterruptedException;

@Service
public class AudioConverter{

public static void convertFile(String fileName)
{
    String newFilename="";
    try{
        //ffprobe the file to get details of the file.
        String command1 = "ffprobe " +fileName;
        Process proc=Runtime.getRuntime().exec(command1);
        //proc.waitFor();  // not required as we are executing in another shell.
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        // read the output from the command1
        String s = null;
        while ((s = stdInput.readLine()) != null) {
        // System.out.println(s);
        }
        String output = new String();
        //why the hell is valid output coming in stderror!! 
        while ((s = stdError.readLine()) != null) {
            //parse output selectively to extract mapping values for english.
            if(s.contains("(eng)"))
            {
                int startIndex = s.indexOf("(eng)") - 3 ;  
                int endIndex = startIndex+3;
                String replacement = "";
                //prepare command using mapping values from ffprobe output.
                replacement= s.substring(startIndex,endIndex);
                output = output+" -map "+replacement;
            }
        }

        //create output filename 
        newFilename = getOutPutFilename(fileName);
        
        //create 2nd command to convert files.
        //use -c copy to make sure all codecs are intact.
        String command2  = "ffmpeg -i "+ fileName + " -c copy "+ output + " "+newFilename;
        Process proc2=Runtime.getRuntime().exec(command2);
        // need to wait for processing to complete
        int exitVal = proc2.waitFor();
        if(exitVal !=0 )
        {
            System.out.println(fileName+ " could not be processed. Moving to next file.");
        }
        else
        {
            System.out.println(fileName+ " is processed Successfully. Converted File is "+newFilename);
        }
    }
    catch(java.io.IOException e){
         System.out.println("error happened during processing.");
    }
    catch(java.lang.InterruptedException e){
         System.out.println("error happened");
    }
    
    //save the hash of input file so that it doesnt get processed again
    String hash1 = AudioConverter.getHash(fileName);
    FileHashService.writeHash(hash1);
    //save the hash of output file so that it doesnt get processed again
    String hash2 = AudioConverter.getHash(newFilename);
    FileHashService.writeHash(hash2);
} 

public static void convertFiles(List<String> fileNames)  
{
    for(String file: fileNames){
        //get hash of file to check if already processed.
        String hash = AudioConverter.getHash(file);
        if(FileHashService.checkHash(hash))
        {
            AudioConverter.convertFile(file);
        }
        else
        {
            System.out.println(file+" has already been processed. Skipping this file.");
        }
    }
}

public static String getOutPutFilename(String origFilename)
{
    StringBuffer newFilename = new StringBuffer(origFilename); 
    int index = origFilename.lastIndexOf("/");
    newFilename.insert(index + 1, "output/stripped_"); 
    //create output folder if it doesnt exist.
    createOutputFolder(origFilename);
    return newFilename.toString();
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

public static void createOutputFolder(String path)
{
    int index = path.lastIndexOf("/");
    String toBeCreated= path.substring(0, index);
    toBeCreated=toBeCreated+"/output";
    File dir = new File(toBeCreated);
    dir.mkdir();
}
}