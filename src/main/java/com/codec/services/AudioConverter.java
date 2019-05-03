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
    try{
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
            if(s.contains("(eng)"))
            {
                //parse output selectively to extract mapping values for english.
                int startIndex = s.indexOf("(eng)") - 3 ;  
                int endIndex = startIndex+3;
                String replacement = "";
                replacement= s.substring(startIndex,endIndex);
                output = output+" -map "+replacement;
            }
        }

        String newFilename = getOutPutFilename(fileName);
        
        //create 2nd command to convert files.
        String command2  = "ffmpeg -i "+ fileName + " -c copy "+ output + " "+newFilename;
        System.out.println(command2);
        Process proc2=Runtime.getRuntime().exec(command2);
        int exitVal = proc2.waitFor();
        if(exitVal !=0 )
        {
            System.out.println(fileName+ " could not be processed. Moving to next file.");
        }
        else
        {
            System.out.println(fileName+ " is processed Successfully. Converted File is "+newFilename);
        }

        //save the hash of new file so that it doesnt get processed again.
        String hash1 = AudioConverter.getHash(newFilename);
        FileHashService.writeHash(hash1);
        String hash2 = AudioConverter.getHash(fileName);
        FileHashService.writeHash(hash2);
        
    }
    catch(java.io.IOException e){
         System.out.println("error happened");
    }
    catch(java.lang.InterruptedException e){
         System.out.println("error happened");
    }
} 

public static void convertFiles(List<String> fileNames)  
{
    for(String file: fileNames){
        //get hash of file to check
        String hash = AudioConverter.getHash(file);
        //System.out.println(hash);
        if(FileHashService.checkHash(hash)){
            AudioConverter.convertFile(file);
        }
        else
        {
            System.out.println(file+" has already been processed. Skipping this file.");
        }
    }
}

public static String getOutPutFilename(String origFilename){

    StringBuffer newFilename = new StringBuffer(origFilename); 
    int index = origFilename.lastIndexOf("/");
    newFilename.insert(index + 1, "output/stripped_"); 

    createOutputFolder(origFilename);
    return newFilename.toString();
}


public static String getHash(String file)

{
    String s,hash = null;
    try{
        String command1 = "ffhash md5 " +file;
        Process proc=Runtime.getRuntime().exec(command1);
        
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        while ((s = stdInput.readLine()) != null) {
            hash =s;
        }
        }
        catch(java.io.IOException e){
         System.out.println("error happened");
    }
    return hash;
}

public static void createOutputFolder(String path)
{
    int index = path.lastIndexOf("/");
    String toBeReplaced = path.substring(0, index);
    toBeReplaced=toBeReplaced+"/output";
    System.out.println("creating: "+ toBeReplaced); 
    File dir = new File(toBeReplaced);
    dir.mkdir();
}
}