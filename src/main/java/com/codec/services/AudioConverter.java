package com.codec.services;

import java.io.File;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

@Service
public class AudioConverter{

public static void convertFile(String fileName)
{
    try{
       String command1 = "ffprobe " +fileName;
        Process proc=Runtime.getRuntime().exec(command1);
        //proc.waitFor();
        BufferedReader stdInput = new BufferedReader(new 
        InputStreamReader(proc.getInputStream()));

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
            System.out.println("printing output");
            System.out.println(command2);
            Process proc2=Runtime.getRuntime().exec(command2);
    }
    catch(java.io.IOException e){
         System.out.println("error happened");
    }
} 

public static void convertFiles(List<String> fileNames)  
{
    for(String file: fileNames)
       AudioConverter.convertFile(file);
}

public static String getOutPutFilename(String origFilename){

    StringBuffer newFilename = new StringBuffer(origFilename); 
    int index = origFilename.lastIndexOf("/");
    newFilename.insert(index + 1, "stripped_"); 
    return newFilename.toString();
}
}
