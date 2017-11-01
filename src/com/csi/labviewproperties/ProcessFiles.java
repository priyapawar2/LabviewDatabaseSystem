package com.csi.labviewproperties;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.GZIPInputStream;
import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;


/**
 * @author ppawar
 *
 */
public class ProcessFiles implements FilenameFilter {
	String dateStart;
	String dateEnd;
	SimpleDateFormat sdf;

	//Make a constructor
	public ProcessFiles(String dateStart, String dateEnd){
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		//JFormattedTextField text1 = new JFormattedTextField(sdf);
	}
	public boolean accept(File dir, String name) {
		Date d = new Date(new File(dir, name).lastModified());
		String current = sdf.format(d);
		return ((dateStart.compareTo(current) < 0 && (dateEnd.compareTo(current) >= 0)));
	}


	public static void populateFiles(File file, ArrayList<File> files, FileFilter filter) {
		File[] all = file.listFiles(filter);
		{ 
			try{
				for(int j = 0; j<all.length; j++) {

					File file1 = all[j];

					if (file1.isDirectory()) {
						populateFiles(file1,files,filter);
					} else {
						files.add(file1);
					}
				}
			}
			catch(NullPointerException ex) {
				System.out.println("Null pointer");
			}

		}
	}

	public static void DSP_IIcopy(File file, File file2) {
		try {
			System.out.println("\tCopying [" + file + "] to folder [" + file2 + "]...");
			InputStream input = new FileInputStream(file);
			OutputStream out = new FileOutputStream(new File(file2 + File.separator + file.getName()));
			byte data[] = new byte[input.available()];
			input.read(data);
			out.write(data);
			out.flush();
			out.close();
			input.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void DSP_IIuntar(File file, File file2) throws IOException {
		//assuming the file you pass in is not a dir
		file2.mkdir();
		try{
			//create tar input stream from a .tar.gz file
			TarInputStream tin = new TarInputStream( new GZIPInputStream( new FileInputStream(file)));

			//get the first entry in the archive
			TarEntry tarEntry = tin.getNextEntry();

			//create a file with the same name as the tarEntry
			while (tarEntry != null){

				File destPath = new File(file2.toString() + File.separatorChar + tarEntry.getName());
				for(int i=0; i<destPath.length(); i++) {
					destPath.renameTo(new File (file2.toString() + File.separatorChar + i + tarEntry.getName()));
				}
				if(tarEntry.isDirectory()){

					destPath.mkdir();
				}
				else {
					FileOutputStream fout = new FileOutputStream(destPath);
					tin.copyEntryContents(fout);
					fout.close();
				}
				tarEntry = tin.getNextEntry();
			}
			System.out.println("Successfully .tgz file is untared into file named 'sysinfo'");
			tin.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}

	//Create method to take user entered dates as input, and process on the files between date range
	public void run(String dateStart, String dateEnd) throws IOException{
		
		System.out.println("The dates are submitted! Please wait...The system is in process now... ");
		System.out.println("Scanning files...");
		
		try {
						
			String[] types = {"sysinfo"};
			FileFilter filter = new FileTypesFilter(types);

			File root1 = new File("O:/Operations/LabVIEW Test Data/");
			File[] folders = root1.listFiles();

			for(File folder1 : folders) {

				if(folder1.isDirectory()){
					File file1 = folder1;
					
					//Five folders of "O:/Operations/LabVIEW Test Data/" folder
					String DSP_II = "DSP II";
					String George = "George";
					String Paul = "Paul";
					String PilotBeacon = "Pilot Beacon";
					String Ringo = "Ringo";

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					ProcessFiles filter1 = new ProcessFiles(dateStart, dateEnd);
					//search for properties files from config_sys folder

					//'if loop' to process on each folder of LabVIEW Test Data
					if(file1.getName().equals(DSP_II))
					{

						File folder =  new File("O:/Operations/LabVIEW Test Data/DSP II/");
						File filess[] = folder.listFiles(filter1);
						for (File f : filess) {
							System.out.println(f.getName() + " " + sdf.format(new Date(f.lastModified())));
							ArrayList<File> files = new ArrayList<File>();
							populateFiles(f, files, filter);

							//Copy the .tgz files between date range from O:/ Drive to local machine
							for (File file : files) {
								File file2 = new File("C:/Labview/tgz");
								if(!file2.exists()) {
									file2.mkdir();
								}
								ProcessFiles.DSP_IIcopy(file, file2);

								//Untar the .tgz files
								File file3 = new File("C:/Labview/sysinfo");
								if(!file3.exists()) {
									file3.mkdir();
								}
								ProcessFiles.DSP_IIuntar(file, file3);

								//Read each property files
								File root = new File("C:/Labview/sysinfo/");
								File[] sysfiles = root.listFiles();
								for(File f1: sysfiles) {
									O_DSP_II.DSP_IIproperty1(f1);	
								}
								O_DSP_II.DSP_IIdeleteSysinfoDir(new File("C:/Labview/sysinfo"));
								O_DSP_II.DSP_IIdeleteTgzDir(new File("C:/Labview/tgz"));
								O_DSP_II.DSP_IIupdateTables();
								O_DSP_II.DSP_IIMySQLToCSV();

							} 
						}
						
					}
				
					if(file1.getName().equals(George))
					{

						File folder =  new File("O:/Operations/LabVIEW Test Data/George/");
						File filess[] = folder.listFiles(filter1);
						for (File f : filess) {
							System.out.println(f.getName() + " " + sdf.format(new Date(f.lastModified())));
							ArrayList<File> files = new ArrayList<File>();
							populateFiles(f, files, filter);

							for (File file : files) {
								File file2 = new File("C:/Labview/tgz");
								if(!file2.exists()) {
									file2.mkdir();
								}
								ProcessFiles.DSP_IIcopy(file, file2);

								File file3 = new File("C:/Labview/sysinfo");
								if(!file3.exists()) {
									file3.mkdir();
								}
								ProcessFiles.DSP_IIuntar(file, file3);

								File root = new File("C:/Labview/sysinfo/");
								File[] sysfiles = root.listFiles();
								for(File f1: sysfiles) {
									O_George.George_property1(f1);	
								}
								O_DSP_II.DSP_IIdeleteSysinfoDir(new File("C:/Labview/sysinfo"));
								O_DSP_II.DSP_IIdeleteTgzDir(new File("C:/Labview/tgz"));
								O_George.George_updateTables();
								O_George.George_MySQLToCSV();

							} 
						}
					}

					if(file1.getName().equals(Paul))
					{

						File folder =  new File("O:/Operations/LabVIEW Test Data/Paul/");
						File filess[] = folder.listFiles(filter1);
						for (File f : filess) {
							System.out.println(f.getName() + " " + sdf.format(new Date(f.lastModified())));
							ArrayList<File> files = new ArrayList<File>();
							populateFiles(f, files, filter);

							for (File file : files) {
								File file2 = new File("C:/Labview/tgz");
								if(!file2.exists()) {
									file2.mkdir();
								}
								ProcessFiles.DSP_IIcopy(file, file2);

								File file3 = new File("C:/Labview/sysinfo");
								if(!file3.exists()) {
									file3.mkdir();
								}
								ProcessFiles.DSP_IIuntar(file, file3);

								File root = new File("C:/Labview/sysinfo/");
								File[] sysfiles = root.listFiles();
								for(File f1: sysfiles) {
									O_Paul.Paul_property1(f1);	
								}
								O_DSP_II.DSP_IIdeleteSysinfoDir(new File("C:/Labview/sysinfo"));
								O_DSP_II.DSP_IIdeleteTgzDir(new File("C:/Labview/tgz"));
								O_Paul.Paul_updateTables();
								O_Paul.Paul_MySQLToCSV();

							} 
						}
					}

					if(file1.getName().equals(PilotBeacon))
					{

						File folder =  new File("O:/Operations/LabVIEW Test Data/Pilot Beacon/");
						File filess[] = folder.listFiles(filter1);
						for (File f : filess) {
							System.out.println(f.getName() + " " + sdf.format(new Date(f.lastModified())));
							ArrayList<File> files = new ArrayList<File>();
							populateFiles(f, files, filter);

							for (File file : files) {
								File file2 = new File("C:/Labview/tgz");
								if(!file2.exists()) {
									file2.mkdir();
								}
								ProcessFiles.DSP_IIcopy(file, file2);

								File file3 = new File("C:/Labview/sysinfo");
								if(!file3.exists()) {
									file3.mkdir();
								}
								ProcessFiles.DSP_IIuntar(file, file3);

								File root = new File("C:/Labview/sysinfo/");
								File[] sysfiles = root.listFiles();
								for(File f1: sysfiles) {
									O_Pilot_Beacon.PilotBeacon_property1(f1);	
								}
								O_DSP_II.DSP_IIdeleteSysinfoDir(new File("C:/Labview/sysinfo"));
								O_DSP_II.DSP_IIdeleteTgzDir(new File("C:/Labview/tgz"));
								O_Pilot_Beacon.PilotBeacon_updateTables();
								O_Pilot_Beacon.PilotBeacon_MySQLToCSV();

							} 
						}
					}

					if(file1.getName().equals(Ringo))
					{

						File folder =  new File("O:/Operations/LabVIEW Test Data/Ringo/");
						File filess[] = folder.listFiles(filter1);
						for (File f : filess) {
							System.out.println(f.getName() + " " + sdf.format(new Date(f.lastModified())));
							ArrayList<File> files = new ArrayList<File>();
							populateFiles(f, files, filter);

							for (File file : files) {
								File file2 = new File("C:/Labview/tgz");
								if(!file2.exists()) {
									file2.mkdir();
								}
								ProcessFiles.DSP_IIcopy(file, file2);

								File file3 = new File("C:/Labview/sysinfo");
								if(!file3.exists()) {
									file3.mkdir();
								}
								ProcessFiles.DSP_IIuntar(file, file3);

								File root = new File("C:/Labview/sysinfo/");
								File[] sysfiles = root.listFiles();
								for(File f1: sysfiles) {
									O_Ringo.Ringo_property1(f1);	
								}
								O_DSP_II.DSP_IIdeleteSysinfoDir(new File("C:/Labview/sysinfo"));
								O_DSP_II.DSP_IIdeleteTgzDir(new File("C:/Labview/tgz"));
								O_Ringo.Ringo_updateTables();
								O_Ringo.Ringo_MySQLToCSV();

							} 
						}
					}

				}
			}
			System.out.println("The process is completed!");
		}

		catch (Exception e){
			e.printStackTrace();
		}
	}
	
}

//Create FileTypesFilter class using FileFilter to tgz files from each item-serial number folder
class FileTypesFilter implements FileFilter {

	String[] types;

	FileTypesFilter(String[] types) {
		this.types = types;
	}

	public boolean accept(File f) {
		if (f.isDirectory()) return true;
		for (String type : types) {
			if (f.getName().startsWith(type)) return true;
		}
		return false;
	}
}
