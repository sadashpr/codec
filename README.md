# codec

# prerequisites

Java - to run the application

ffmpeg - tool to convert files

maven - to compile the code


######################

This application assumes ffmpeg is installed on the system.
Use the below commands to install ffmpeg.

MacOS
	
	brew install ffmpeg

Linux 

	sudo apt update
	sudo apt install ffmpeg

CentOS

	sudo rpm --import http://li.nux.ro/download/nux/RPM-GPG-KEY-nux.ro
	sudo rpm -Uvh http://li.nux.ro/download/nux/dextop/el7/x86_64/nux-dextop-release-0-5.el7.nux.noarch.rpm	

###########################

# Optional Steps to install java and maven

We can use brew to install Java. Homebrew is a package manager designed for installing UNIX tools and other open-source applications on Mac OS X. Use the below commands to install Homebrew ,then install Java 8 and Apache Maven 3.5.3 

1. xcode-select --install   //install xcode command line utilities 
2. ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"   //install homebrew
3. brew cask install java8     
4. brew install maven@3.5 

You can check the installations are successfull using the the below commands

1. java -version
2. mvn -v

###########################
# Steps to run the codec java app

1. Clone the repo using "git clone https://github.com/sadashpr/codec"
2. Enter the codec folder 
3. Build the application from codec folder using "mvn clean install"
4. Copy the Jar file which will be in target folder as "target/codec-0.0.1-SNAPSHOT.jar" to any new directory 
5. Run the java app as "java -jar codec-0.0.1-SNAPSHOT.jar <path_to_folder_to_be_processed> 
	    Example:  java -jar codec-0.0.1-SNAPSHOT.jar /Users/pradeep.sadashiv/Documents/test/assignment_files/
6. The processed files will be output directory of the destination folderss.

#############################

# Limitations

This application creates a local file called processedlogfile.txt. This file contains details about all the previously processed files. 
If this file is lost/deleted, previously processed files will be processed again. 
In a practical solution, this file would be replaced by a Database. 

