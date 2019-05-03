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
       // String command = "ffmpeg -i "+fileName+ " "+fileName+"_new" ;
       String command = "ffprobe " +fileName;
        //String[] command = {"cat", "/Users/pradeep.sadashiv/Documents/test/assignment_files/abcd.txt", ">>", "/Users/pradeep.sadashiv/Documents/test/assignment_files/hello.txt"};
//String command = "cat /Users/pradeep.sadashiv/Documents/test/assignment_files/abcd.txt";
        System.out.println(command);
        Process proc=Runtime.getRuntime().exec(command);
        //proc.waitFor();
        BufferedReader stdInput = new BufferedReader(new 
     InputStreamReader(proc.getInputStream()));

BufferedReader stdError = new BufferedReader(new 
     InputStreamReader(proc.getErrorStream()));

// read the output from the command
//System.out.println("Here is the standard output of the command:\n");
String s = null;
while ((s = stdInput.readLine()) != null) {
   // System.out.println(s);
}
String output = new String();
//System.out.println("Here is the standard error of the command (if any):\n");

//why the hell is valid output coming in stderror!! 
while ((s = stdError.readLine()) != null) {
    if(s.contains("(eng)"))
    {
        int startIndex = s.indexOf("(eng)") - 3 ;
        int endIndex = startIndex+3;
        String replacement = "";
        replacement= s.substring(startIndex,endIndex);
        output = output+" -map "+replacement;
    }
}

String command2  = "ffmpeg -i "+ fileName + " -c copy "+ output + " /Users/pradeep.sadashiv/Documents/test/temp/output.mov";
System.out.println("printing output");
System.out.println(command2);


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
}
