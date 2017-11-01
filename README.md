# LabviewDatabaseSystem
Labview adatabase system using Java and MySQL

Following are the steps to run the application
1.	Download and install MySQL Workbench 
2.	At the installation time put secured username and password for MySQL workbench and use same in application.
3.	Create folders with following path
C:/Labview/CSV Files
C:/Labview/Properties
4.	Under folder named “Properties” copy the files given in the “Copy Files” folder of LabviewDataSystemApp folder.
5. Create Windows Batch file of source code.
 
•	The “Labview Data System Application” windows will popup, which prompts the user to select the dates. The user needs to choose date of last modified file from "Date Modified" column of source location folder(from you want to copy data of files into database). Then user needs to select date of the current end up file i.e. up to which file user want to process. Hit the submit button, the system will start to run. You can see the output of process in console.
•	Example: Last Modified Date 2016-12-02 
  Current End up Date 2016-12-05 
•	Then system will work on those files generated from 2016-12-03 to 2016-12-05.

