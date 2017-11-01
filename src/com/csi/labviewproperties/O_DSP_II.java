package com.csi.labviewproperties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.apache.commons.io.output.TeeOutputStream;

/**
 * @author ppawar
 *
 */
public class O_DSP_II {
	//DSP II Database	
	public static void DSP_IIproperty1(File f1) {

		for(File sysinfo : f1.listFiles()) {
			//System.out.println(sysinfo);
			if(sysinfo.isDirectory()){
				//file1 = config_1 folder which contain 4 properties files
				File file1 = sysinfo;
				String folderName = "config_sys";
				String folderName1 = "config_1";
				String folderName2 = "config_2";
				//	System.out.println("File: " + file1.getName()  + " " + "Path: " + file1.getAbsolutePath());

				//search for properties files from config_sys folder
				if(file1.getName().equals(folderName))
				{
					File[] proper = file1.listFiles();
					{
						try {
							for (int i = 0; i< proper.length; i++) {
								File fileFirst = proper[i];
								if (fileFirst.isFile()) {
									String fileName1 = "identity.properties";
									System.out.println();
									if (fileFirst.getName().equals(fileName1))
									{
										@SuppressWarnings("unused")
										TreeMap<String, String> map1 = getPropertiesIdentity(fileFirst);
									}
									//	fileFirst.delete();
								}

								if (fileFirst.isFile()) {
									String fileName2 = "engr.properties";
									System.out.println();
									if (fileFirst.getName().equals(fileName2))
									{
										@SuppressWarnings("unused")
										TreeMap<String, String> map2 = getPropertiesEngr(fileFirst);
									}
									//		fileFirst.delete();
								}
								fileFirst.delete();
							}
						}
						catch (Exception e){
							e.printStackTrace();
						}

					}

				}


				//search for properties files from config_1 folder i.e. Band 2
				if(file1.getName().equals(folderName1))
				{
					File[] proper = file1.listFiles();
					{
						try {
							for (int i = 0; i< proper.length; i++) {
								File fileSecond = proper[i];
								if (fileSecond.isFile()) {
									String fileName3 = "caldata.properties";
									System.out.println();
									if (fileSecond.getName().equals(fileName3))
									{
										@SuppressWarnings("unused")
										TreeMap<String, String> map3 = getPropertiesCaldata1(fileSecond);
									}
									//	fileSecond.delete();
								}

								if (fileSecond.isFile()) {
									String fileName4 = "link.properties";
									System.out.println();
									if (fileSecond.getName().equals(fileName4))
									{
										@SuppressWarnings("unused")
										TreeMap<String, String> map4 = getPropertiesLink1(fileSecond);
									}
									//	fileSecond.delete();
								}

								fileSecond.delete();
							}
						}
						catch (Exception e){
							e.printStackTrace();
						}

					}
				}

				//search for properties files from config_2 folder i.e. Band 2
				if(file1.getName().equals(folderName2))
				{
					File[] proper = file1.listFiles();
					{
						try {
							for (int i = 0; i< proper.length; i++) {
								File fileThird = proper[i];
								if (fileThird.isFile()) {
									String fileName6 = "caldata.properties";
									System.out.println();
									if (fileThird.getName().equals(fileName6))
									{
										@SuppressWarnings("unused")
										TreeMap<String, String> map6 = getPropertiesCaldata2(fileThird);
									}
									//	fileThird.delete();
								}

								if (fileThird.isFile()) {
									String fileName7 = "link.properties";
									System.out.println();
									if (fileThird.getName().equals(fileName7))
									{
										@SuppressWarnings("unused")
										TreeMap<String, String> map7 = getPropertiesLink2(fileThird);
									}
									//	fileThird.delete();
								}

								fileThird.delete();
							}
						}
						catch (Exception e){
							e.printStackTrace();
						}

					}
				}

				file1.delete();

			}

			sysinfo.delete();
		}

	}


	//Identity.properties: read file, map key-values in TreeMap 
	public static TreeMap<String, String> getPropertiesIdentity(File file) throws IOException {
		final int lhs = 0;
		final int rhs = 1;

		TreeMap<String, String> map1 = new TreeMap<String, String>();
		BufferedReader  bfr = new BufferedReader(new FileReader(file));

		String line;
		String [] pair = null;
		while ((line = bfr.readLine()) != null) {
			try {
				if (!line.startsWith("#") && !line.isEmpty()) {
					pair = line.trim().split("=");
					if (pair.length == 2 ) {
						map1.put(pair[lhs].trim(), pair[rhs].trim());
					} 
					else {
						map1.put(pair[lhs].trim(), " ");
					}

				}
			}  catch (Exception e){
				e.printStackTrace();
			}
		}

		//get the specific properties and their values and write them into text file, close text file.

		String value = map1.get("identity.serial.number");
		String value1 = map1.get("identity.model.number");
		String value2 = map1.get("identity.item.number");
		String value3 = map1.get("identity.software.version");
		String value4 = map1.get("identity.date.built");

		bfr.close();

		File f = new File("C:\\Labview\\Properties\\identityproperty.txt");
		if(!f.exists())
		{
			try {
				f.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(f);
			//I want to print in standard "System.out" and in "file"
			TeeOutputStream myOut=new TeeOutputStream(System.out, fos);
			PrintStream ps = new PrintStream(myOut);
			System.setOut(ps);

			System.out.print(value + "\t");
			System.out.print(value1 + "\t");
			System.out.print(value2 + "\t");
			System.out.print(value3 + "\t");
			System.out.print(value4 + "\t");

			fos.close();

			//config.properties file: contains the properties(url+database_name, username, password) to make JDBC connection with MySQL
			//get the properties and make the connection using Driver Manager
			Properties prop1 = new Properties();
			InputStream is;
			try {
				is = new FileInputStream("C:/Labview/Properties/DSP_II.properties");

				prop1.load(is);
				String url= prop1.getProperty("url");
				String un = prop1.getProperty("username");
				String pass = prop1.getProperty("password");
				//System.out.println("\nURL: "+url+" UN: "+un+" PASS: "+pass);
				is.close();
				Class.forName("com.mysql.jdbc.Driver");

				System.out.println("\nMySQL JDBC Driver Registered!");
				Connection connection = null;

				Statement stmt = null;
				String query = null;
				PreparedStatement pst = null;
				PreparedStatement pst1 = null;

				//Create the database
				try {
					connection = DriverManager.getConnection(url, un, pass);  
					pst1 = connection.prepareStatement("create database if not exists dsp_ii_new;");
					pst1.executeUpdate();
					System.out.println("The database is created!");
				}


				catch (SQLException ex) {
					Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
					lgr.log(Level.SEVERE, ex.getMessage(), ex);
				}
				finally {
					try {
						if (connection != null) {
							connection.close();
						}
						if (pst1 != null) {
							pst1.close();
						}

					} catch (SQLException ex) {
						Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
						lgr.log(Level.WARNING, ex.getMessage(), ex);
					}
				}

				//Create the table 
				try
				{
					connection = DriverManager.getConnection(url, un, pass);  
					pst = connection.prepareStatement("create table if not exists tbl_identity(ID int not null primary key auto_increment, identity_serial_number varchar(30), identity_model_number varchar(30), identity_item_number varchar(30), identity_software_version varchar(30), identity_date_built varchar(30)) engine=InnoDB default charset=utf8;");
					pst.executeUpdate();
					System.out.println("The table is created");
				}


				catch (SQLException ex) {
					Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
					lgr.log(Level.SEVERE, ex.getMessage(), ex);
				}
				finally {
					try {
						if (connection != null) {
							connection.close();
						}
						if (pst != null) {
							pst.close();
						}

					} catch (SQLException ex) {
						Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
						lgr.log(Level.WARNING, ex.getMessage(), ex);
					}
				}
				//Insert the text file data (values) into table
				String fileName11= "identity.properties";
				if (file.getName().equals(fileName11)){
					try {
						connection = DriverManager.getConnection(url, un, pass);                 	   
						System.out.println("Inserting data into the identity table");
						stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

						query = "LOAD DATA INFILE 'C:/Labview/Properties/identityproperty.txt' INTO TABLE tbl_identity(identity_serial_number, identity_model_number, identity_item_number, identity_software_version, identity_date_built)";

						stmt.executeUpdate(query);
						System.out.println("The data is inserted into the identity table");
					}        

					catch(Exception e)
					{
						e.printStackTrace();
						stmt = null;
					}

					finally {
						try {
							if (connection != null) {
								connection.close();
							}
							if (stmt != null) {
								stmt.close();
							}

						} catch (SQLException ex) {
							Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
							lgr.log(Level.WARNING, ex.getMessage(), ex);
						}
					}
				}
			} 
			catch (ClassNotFoundException e) {
				System.out.println("Where is your MySQL JDBC Driver?");
				e.printStackTrace();
				//	return;
			}

		}
		catch (IOException e) {  
			e.printStackTrace();  
		}
		//Return the map.
		return(map1);
	}

	//Engr.properties: read file, map key-values in TreeMap
	public static TreeMap<String, String> getPropertiesEngr(File file) throws IOException {
		final int lhs = 0;
		final int rhs = 1;

		TreeMap<String, String> map2 = new TreeMap<String, String>();
		BufferedReader  bfr = new BufferedReader(new FileReader(file));

		String line;
		String [] pair = null;
		while ((line = bfr.readLine()) != null) {
			try {
				if (!line.startsWith("#") && !line.isEmpty()) {
					pair = line.trim().split("=");
					if (pair.length == 2 ) {
						map2.put(pair[lhs].trim(), pair[rhs].trim());
					} 
					else {
						map2.put(pair[lhs].trim(), " ");
					}

				}
			}  catch (Exception e){
				e.printStackTrace();
			}
		}

		//get the specific properties and their values and write them into text file, close text file.
		String value = " ";
		String value0 = " ";
		String value1 = map2.get("engr.acela.cell.out.revb.dn.k");	
		String value2 = map2.get("engr.acela.cell.out.revb.dn.n");	
		String value3 = map2.get("engr.acela.cell.out.revb.up.k");	
		String value4 = map2.get("engr.acela.cell.out.revb.up.n");	
		String value5 = map2.get("engr.acela.lte.out.revb.dn.k");	
		String value6 = map2.get("engr.acela.lte.out.revb.dn.n");	
		String value7 = map2.get("engr.acela.lte.out.revb.up.k");	
		String value8 = map2.get("engr.acela.lte.out.revb.up.n");	
		String value9 = map2.get("engr.acela.pcs.out.revb.dn.k");	
		String value10 = map2.get("engr.acela.pcs.out.revb.dn.n");	
		String value11 = map2.get("engr.acela.pcs.out.revb.up.k");	
		String value12 = map2.get("engr.acela.pcs.out.revb.up.n");		
		String value13 = map2.get("engr.aws.in.revb.dn.k");	
		String value14 = map2.get("engr.aws.in.revb.dn.n");	
		String value15 = map2.get("engr.aws.in.revb.up.k");	
		String value16 = map2.get("engr.aws.in.revb.up.n");	
		String value17 = map2.get("engr.aws.out.revb.dn.k");	
		String value18 = map2.get("engr.aws.out.revb.dn.n");	
		String value19 = map2.get("engr.aws.out.revb.up.k");	
		String value20 = map2.get("engr.aws.out.revb.up.n");	
		String value21 = map2.get("engr.cell.in.revb.dn.k");	
		String value22 = map2.get("engr.cell.in.revb.dn.n");	
		String value23 = map2.get("engr.cell.in.revb.up.k");	
		String value24 = map2.get("engr.cell.in.revb.up.n");	
		String value25 = map2.get("engr.cell.out.revb.dn.k");	
		String value26 = map2.get("engr.cell.out.revb.dn.n");	
		String value27 = map2.get("engr.cell.out.revb.up.k");	
		String value28 = map2.get("engr.cell.out.revb.up.n");	
		String value29 = map2.get("engr.das.24v.dnlink.coeff.acela");	
		String value30 = map2.get("engr.das.24v.dnlink.coeff.acela.cell");	
		String value31 = map2.get("engr.das.24v.dnlink.coeff.acela.lte");	
		String value32 = map2.get("engr.das.24v.uplink.coeff.acela");	
		String value33 = map2.get("engr.das.24v.uplink.coeff.acela.cell");	
		String value34 = map2.get("engr.das.24v.uplink.coeff.acela.lte");	
		String value35 = map2.get("engr.das.24v.dnlink.coeff.acela.cell");
		String value36 = map2.get("engr.das.24v.dnlink.coeff.acela.lte");
		String value37 = map2.get("engr.das.24v.dnlink.coeff.acela.pcs");
		String value38 = map2.get("engr.das.24v.dnlink.coeff.acela.aws");
		String value39 = map2.get("engr.das.24v.uplink.coeff.acela.cell");
		String value40 = map2.get("engr.das.24v.uplink.coeff.acela.lte");
		String value41 = map2.get("engr.das.24v.uplink.coeff.acela.pcs");
		String value42 = map2.get("engr.das.24v.uplink.coeff.acela.aws");
		String value43 = map2.get("engr.lte.in.revb.dn.k");	
		String value44 = map2.get("engr.lte.in.revb.dn.n");	
		String value45 = map2.get("engr.lte.in.revb.up.k");	
		String value46 = map2.get("engr.lte.in.revb.up.n");	
		String value47 = map2.get("engr.lte.out.revb.dn.k");	
		String value48 = map2.get("engr.lte.out.revb.dn.n");	
		String value49 = map2.get("engr.lte.out.revb.up.k");	
		String value50 = map2.get("engr.lte.out.revb.up.n");	
		String value51 = map2.get("engr.lte2.in.revb.dn.k");	
		String value52 = map2.get("engr.lte2.in.revb.dn.n");	
		String value53 = map2.get("engr.lte2.in.revb.up.k");	
		String value54 = map2.get("engr.lte2.in.revb.up.n");	
		String value55 = map2.get("engr.lte2.out.revb.dn.k");	
		String value56 = map2.get("engr.lte2.out.revb.dn.n");	
		String value57 = map2.get("engr.lte2.out.revb.up.k");	
		String value58 = map2.get("engr.lte2.out.revb.up.n");	
		String value59 = map2.get("engr.pcs.in.revb.dn.k");	
		String value60 = map2.get("engr.pcs.in.revb.dn.n");	
		String value61 = map2.get("engr.pcs.in.revb.up.k");	
		String value62 = map2.get("engr.pcs.in.revb.up.n");	
		String value63 = map2.get("engr.pcs.out.revb.dn.k");	
		String value64 = map2.get("engr.pcs.out.revb.dn.n");	
		String value65 = map2.get("engr.pcs.out.revb.up.k");	
		String value66 = map2.get("engr.pcs.out.revb.up.n");	
		String value67 = map2.get("engr.pilot.aws.out.revb.dn.k");	
		String value68 = map2.get("engr.pilot.aws.out.revb.dn.n");	
		String value69 = map2.get("engr.pilot.aws.out.revb.up.k");	
		String value70 = map2.get("engr.pilot.aws.out.revb.up.n");	
		String value71 = map2.get("engr.pilot.cell.out.revb.dn.k");
		String value72 = map2.get("engr.pilot.cell.out.revb.dn.n");	
		String value73 = map2.get("engr.pilot.cell.out.revb.up.k");	
		String value74 = map2.get("engr.pilot.cell.out.revb.up.n");	
		String value75 = map2.get("engr.pilot.pcs.out.revb.dn.k");	
		String value76 = map2.get("engr.pilot.pcs.out.revb.dn.n");	
		String value77 = map2.get("engr.pilot.pcs.out.revb.up.k");	
		String value78 = map2.get("engr.pilot.pcs.out.revb.up.n");	
		String value79 = map2.get("engr.scan.rcvr.cal.1");	
		String value80 = map2.get("engr.scan.rcvr.cal.2");	
		String value81 = map2.get("engr.scan.rcvr.cal2.0");	
		String value82 = map2.get("engr.scan.rcvr.cal2.1");	
		String value83 = map2.get("engr.scan.rcvr.cal2.2");	
		String value84 = map2.get("engr.scan.rcvr.cell.cal.0");	
		String value85 = map2.get("engr.scan.rcvr.cell.cal.1");	
		String value86 = map2.get("engr.scan.rcvr.cell.cal.2");	
		String value87 = map2.get("engr.scan.rcvr.lte.cal.0");	
		String value88 = map2.get("engr.scan.rcvr.lte.cal.1");	
		String value89 = map2.get("engr.scan.rcvr.lte.cal.2");	
		String value90 = map2.get("engr.smr.in.revb.dn.k");	
		String value91 = map2.get("engr.smr.in.revb.dn.n");	
		String value92 = map2.get("engr.smr.in.revb.up.k");	
		String value93 = map2.get("engr.smr.in.revb.up.n");	
		String value94 = map2.get("engr.smr.out.revb.dn.k");	
		String value95 = map2.get("engr.smr.out.revb.dn.n");	
		String value96 = map2.get("engr.smr.out.revb.up.k");	
		String value97 = map2.get("engr.smr.out.revb.up.n");	
		String value98 = map2.get("engr.temp.gain.dn.k");	
		String value99 = map2.get("engr.temp.gain.dn.n");		
		String value100 = map2.get("engr.temp.gain.up.k");	
		String value101 = map2.get("engr.temp.gain.up.n");	
		String value102 = map2.get("engr.temp.gain1.dn.k");	
		String value103 = map2.get("engr.temp.gain1.dn.n");	
		String value104 = map2.get("engr.temp.gain1.up.k");	
		String value105 = map2.get("engr.temp.gain1.up.n");

		bfr.close();


		File f = new File("C:\\Labview\\Properties\\engrproperty.txt");
		if(!f.exists())
		{
			try {
				f.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(f);
			//I want to print in standard "System.out" and in "file"
			TeeOutputStream myOut=new TeeOutputStream(System.out, fos);
			PrintStream ps = new PrintStream(myOut);
			System.setOut(ps);

			System.out.print(value + "\t");
			System.out.print(value0 + "\t");
			System.out.print(value1 + "\t");  
			System.out.print(value2 + "\t");
			System.out.print(value3 + "\t");
			System.out.print(value4 + "\t"); 	
			System.out.print(value5 + "\t"); 	
			System.out.print(value6 + "\t"); 	
			System.out.print(value7 + "\t"); 	
			System.out.print(value8 + "\t"); 	
			System.out.print(value9 + "\t"); 	
			System.out.print(value10 + "\t"); 	
			System.out.print(value11 + "\t"); 	
			System.out.print(value12 + "\t"); 		
			System.out.print(value13 + "\t"); 	
			System.out.print(value14 + "\t"); 	
			System.out.print(value15 + "\t"); 	
			System.out.print(value16 + "\t"); 	
			System.out.print(value17 + "\t"); 	
			System.out.print(value18 + "\t"); 	
			System.out.print(value19 + "\t"); 	
			System.out.print(value20 + "\t"); 	
			System.out.print(value21 + "\t"); 	
			System.out.print(value22 + "\t"); 	
			System.out.print(value23 + "\t"); 	
			System.out.print(value24 + "\t"); 	
			System.out.print(value25 + "\t"); 	
			System.out.print(value26 + "\t"); 	
			System.out.print(value27 + "\t"); 	
			System.out.print(value28 + "\t"); 	
			System.out.print(value29 + "\t"); 	
			System.out.print(value30 + "\t"); 	
			System.out.print(value31 + "\t"); 	
			System.out.print(value32 + "\t"); 	
			System.out.print(value33 + "\t"); 	
			System.out.print(value34 + "\t"); 
			System.out.print(value35 + "\t"); 
			System.out.print(value36 + "\t"); 
			System.out.print(value37 + "\t"); 
			System.out.print(value38 + "\t"); 
			System.out.print(value39 + "\t"); 
			System.out.print(value40 + "\t"); 
			System.out.print(value41 + "\t"); 
			System.out.print(value42 + "\t"); 
			System.out.print(value43 + "\t"); 	
			System.out.print(value44 + "\t"); 	
			System.out.print(value45 + "\t"); 	
			System.out.print(value46 + "\t"); 	
			System.out.print(value47 + "\t"); 	
			System.out.print(value48 + "\t"); 	
			System.out.print(value49 + "\t"); 	
			System.out.print(value50 + "\t"); 	
			System.out.print(value51 + "\t"); 	
			System.out.print(value52 + "\t"); 	
			System.out.print(value53 + "\t"); 	
			System.out.print(value54 + "\t"); 	
			System.out.print(value55 + "\t"); 	
			System.out.print(value56 + "\t"); 	
			System.out.print(value57 + "\t"); 	
			System.out.print(value58 + "\t"); 	
			System.out.print(value59 + "\t"); 	
			System.out.print(value60 + "\t"); 	
			System.out.print(value61 + "\t"); 	
			System.out.print(value62 + "\t"); 	
			System.out.print(value63 + "\t"); 	
			System.out.print(value64 + "\t"); 	
			System.out.print(value65 + "\t"); 	
			System.out.print(value66 + "\t"); 	
			System.out.print(value67 + "\t"); 	
			System.out.print(value68 + "\t"); 	
			System.out.print(value69 + "\t"); 	
			System.out.print(value70 + "\t"); 	
			System.out.print(value71 + "\t"); 
			System.out.print(value72 + "\t"); 	
			System.out.print(value73 + "\t"); 	
			System.out.print(value74 + "\t"); 	
			System.out.print(value75 + "\t"); 	
			System.out.print(value76 + "\t"); 	
			System.out.print(value77 + "\t"); 	
			System.out.print(value78 + "\t"); 	
			System.out.print(value79 + "\t"); 	
			System.out.print(value80 + "\t"); 
			System.out.print(value81 + "\t"); 	
			System.out.print(value82 + "\t"); 	
			System.out.print(value83 + "\t"); 	
			System.out.print(value84 + "\t"); 	
			System.out.print(value85 + "\t"); 	
			System.out.print(value86 + "\t"); 	
			System.out.print(value87 + "\t"); 	
			System.out.print(value88 + "\t"); 	
			System.out.print(value89 + "\t"); 	
			System.out.print(value90 + "\t"); 	
			System.out.print(value91 + "\t"); 	
			System.out.print(value92 + "\t"); 	
			System.out.print(value93 + "\t"); 	
			System.out.print(value94 + "\t"); 	
			System.out.print(value95 + "\t"); 	
			System.out.print(value96 + "\t"); 	
			System.out.print(value97 + "\t"); 	
			System.out.print(value98 + "\t"); 	
			System.out.print(value99 + "\t"); 		
			System.out.print(value100 + "\t"); 	
			System.out.print(value101 + "\t"); 
			System.out.print(value102 + "\t"); 	
			System.out.print(value103 + "\t"); 	
			System.out.print(value104 + "\t"); 	
			System.out.print(value105 + "\t"); 


			fos.close();

			//config.properties file: contains the properties(url+database_name, username, password) to make JDBC connection with MySQL
			//get the properties and make the connection using Driver Manager
			Properties prop1 = new Properties();
			InputStream is;
			try {
				is = new FileInputStream("C:/Labview/Properties/DSP_II.properties");

				prop1.load(is);
				String url= prop1.getProperty("url");
				String un = prop1.getProperty("username");
				String pass = prop1.getProperty("password");
				//System.out.println("URL: "+url+" UN: "+un+" PASS: "+pass);
				is.close();
				Class.forName("com.mysql.jdbc.Driver");


				System.out.println("\nMySQL JDBC Driver Registered!");
				Connection connection = null;

				Statement stmt = null;
				String query = null;
				PreparedStatement pst = null;

				//Create the table
				try {
					connection = DriverManager.getConnection(url, un, pass);  
					pst = connection.prepareStatement("create table if not exists tbl_engr(ID int not null primary key auto_increment, Serial_no varchar(100), Model_no varchar(100), engr_acela_cell_out_revb_dn_k varchar(30), engr_acela_cell_out_revb_dn_n varchar(30), engr_acela_cell_out_revb_up_k varchar(30), engr_acela_cell_out_revb_up_n varchar(30), engr_acela_lte_out_revb_dn_k varchar(30), engr_acela_lte_out_revb_dn_n varchar(30), engr_acela_lte_out_revb_up_k varchar(30), engr_acela_lte_out_revb_up_n varchar(30), engr_acela_pcs_out_revb_dn_k varchar(30), engr_acela_pcs_out_revb_dn_n varchar(30), engr_acela_pcs_out_revb_up_k varchar(30), engr_acela_pcs_out_revb_up_n varchar(30), engr_aws_in_revb_dn_k varchar(30), engr_aws_in_revb_dn_n varchar(30), engr_aws_in_revb_up_k varchar(30), engr_aws_in_revb_up_n varchar(30), engr_aws_out_revb_dn_k varchar(30), engr_aws_out_revb_dn_n varchar(30), engr_aws_out_revb_up_k varchar(30), engr_aws_out_revb_up_n varchar(30), engr_cell_in_revb_dn_k varchar(30), engr_cell_in_revb_dn_n varchar(30), engr_cell_in_revb_up_k varchar(30), engr_cell_in_revb_up_n varchar(30), engr_cell_out_revb_dn_k varchar(30), engr_cell_out_revb_dn_n varchar(30), engr_cell_out_revb_up_k varchar(30), engr_cell_out_revb_up_n varchar(30), engr_das_24v_dnlink_coeff_acela varchar(30), engr_das_24v_dnlink_coeff_acela_cell varchar(30), engr_das_24v_dnlink_coeff_acela_lte varchar(30), engr_das_24v_uplink_coeff_acela varchar(30), engr_das_24v_uplink_coeff_acela_cell varchar(30), engr_das_24v_uplink_coeff_acela_lte varchar(30), engr_das_24v_dnlink_coeff_acela_cell1 varchar(30), engr_das_24v_dnlink_coeff_acela_lte1 varchar(30), engr_das_24v_dnlink_coeff_acela_pcs varchar(30), engr_das_24v_dnlink_coeff_acela_aws varchar(30), engr_das_24v_uplink_coeff_acela_cell1 varchar(30), engr_das_24v_uplink_coeff_acela_lte1 varchar(30), engr_das_24v_uplink_coeff_acela_pcs varchar(30), engr_das_24v_uplink_coeff_acela_aws varchar(30), engr_lte_in_revb_dn_k varchar(30), engr_lte_in_revb_dn_n varchar(30), engr_lte_in_revb_up_ varchar(30), engr_lte_in_revb_up_n varchar(30), engr_lte_out_revb_dn_k varchar(30), engr_lte_out_revb_dn_n varchar(30), engr_lte_out_revb_up_k varchar(30), engr_lte_out_revb_up_n varchar(30), engr_lte2_in_revb_dn_k varchar(30), engr_lte2_in_revb_dn_n varchar(30), engr_lte2_in_revb_up_k varchar(30), engr_lte2_in_revb_up_n varchar(30), engr_lte2_out_revb_dn_k varchar(30), engr_lte2_out_revb_dn_n varchar(30), engr_lte2_out_revb_up_k varchar(30), engr_lte2_out_revb_up_n varchar(30), engr_pcs_in_revb_dn_k varchar(30), engr_pcs_in_revb_dn_n varchar(30), engr_pcs_in_revb_up_k varchar(30), engr_pcs_in_revb_up_n varchar(30), engr_pcs_out_revb_dn_k varchar(30), engr_pcs_out_revb_dn_n varchar(30), engr_pcs_out_revb_up_k varchar(30), engr_pcs_out_revb_up_n varchar(30), engr_pilot_aws_out_revb_dn_k varchar(30), engr_pilot_aws_out_revb_dn_n varchar(30), engr_pilot_aws_out_revb_up_k varchar(30), engr_pilot_aws_out_revb_up_n varchar(30), engr_pilot_cell_out_revb_dn_ varchar(30), engr_pilot_cell_out_revb_dn_n varchar(30), engr_pilot_cell_out_revb_up_k varchar(30), engr_pilot_cell_out_revb_up_n varchar(30), engr_pilot_pcs_out_revb_dn_k varchar(30), engr_pilot_pcs_out_revb_dn_n varchar(30), engr_pilot_pcs_out_revb_up_k varchar(30), engr_pilot_pcs_out_revb_up_n varchar(30), engr_scan_rcvr_cal_1 varchar(30), engr_scan_rcvr_cal_2 varchar(30), engr_scan_rcvr_cal2_0 varchar(30), engr_scan_rcvr_cal2_1 varchar(30), engr_scan_rcvr_cal2_2 varchar(30), engr_scan_rcvr_cell_cal_0 varchar(30), engr_scan_rcvr_cell_cal_1 varchar(30), engr_scan_rcvr_cell_cal_2 varchar(30), engr_scan_rcvr_lte_cal_0 varchar(30), engr_scan_rcvr_lte_cal_1 varchar(30), engr_scan_rcvr_lte_cal_2 varchar(30), engr_smr_in_revb_dn_k varchar(30), engr_smr_in_revb_dn_n varchar(30), engr_smr_in_revb_up_k varchar(30), engr_smr_in_revb_up_n varchar(30), engr_smr_out_revb_dn_k varchar(30), engr_smr_out_revb_dn_n varchar(30), engr_smr_out_revb_up_k varchar(30), engr_smr_out_revb_up_n varchar(30), engr_temp_gain_dn_k varchar(30), engr_temp_gain_dn_n varchar(30), engr_temp_gain_up_k varchar(30), engr_temp_gain_up_n varchar(30), engr_temp_gain1_dn_k varchar(30), engr_temp_gain1_dn_n varchar(30), engr_temp_gain1_up_k varchar(30), engr_temp_gain1_up_n varchar(30)) engine=InnoDB default charset=utf8;");
					pst.executeUpdate();
					System.out.println("The table is created");
				}


				catch (SQLException ex) {
					Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
					lgr.log(Level.SEVERE, ex.getMessage(), ex);
				}

				finally {
					try {
						if (connection != null) {
							connection.close();
						}
						if (pst != null) {
							pst.close();
						}

					} catch (SQLException ex) {
						Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
						lgr.log(Level.WARNING, ex.getMessage(), ex);
					}
				}

				//Insert the text file data (values) into table
				String fileName11= "engr.properties";
				if (file.getName().equals(fileName11)){
					try {
						connection = DriverManager.getConnection(url, un, pass);                 	   
						System.out.println("Inserting data into the engr table");
						stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

						query = "LOAD DATA INFILE 'C:/Labview/Properties/engrproperty.txt' INTO TABLE tbl_engr(Serial_no, Model_no, engr_acela_cell_out_revb_dn_k, engr_acela_cell_out_revb_dn_n, engr_acela_cell_out_revb_up_k, engr_acela_cell_out_revb_up_n, engr_acela_lte_out_revb_dn_k, engr_acela_lte_out_revb_dn_n, engr_acela_lte_out_revb_up_k, engr_acela_lte_out_revb_up_n, engr_acela_pcs_out_revb_dn_k, engr_acela_pcs_out_revb_dn_n, engr_acela_pcs_out_revb_up_k, engr_acela_pcs_out_revb_up_n, engr_aws_in_revb_dn_k, engr_aws_in_revb_dn_n, engr_aws_in_revb_up_k, engr_aws_in_revb_up_n, engr_aws_out_revb_dn_k, engr_aws_out_revb_dn_n, engr_aws_out_revb_up_k, engr_aws_out_revb_up_n, engr_cell_in_revb_dn_k, engr_cell_in_revb_dn_n, engr_cell_in_revb_up_k, engr_cell_in_revb_up_n, engr_cell_out_revb_dn_k, engr_cell_out_revb_dn_n, engr_cell_out_revb_up_k, engr_cell_out_revb_up_n, engr_das_24v_dnlink_coeff_acela, engr_das_24v_dnlink_coeff_acela_cell, engr_das_24v_dnlink_coeff_acela_lte, engr_das_24v_uplink_coeff_acela, engr_das_24v_uplink_coeff_acela_cell, engr_das_24v_uplink_coeff_acela_lte, engr_das_24v_dnlink_coeff_acela_cell1, engr_das_24v_dnlink_coeff_acela_lte1, engr_das_24v_dnlink_coeff_acela_pcs, engr_das_24v_dnlink_coeff_acela_aws, engr_das_24v_uplink_coeff_acela_cell1, engr_das_24v_uplink_coeff_acela_lte1, engr_das_24v_uplink_coeff_acela_pcs, engr_das_24v_uplink_coeff_acela_aws, engr_lte_in_revb_dn_k, engr_lte_in_revb_dn_n, engr_lte_in_revb_up_, engr_lte_in_revb_up_n, engr_lte_out_revb_dn_k, engr_lte_out_revb_dn_n, engr_lte_out_revb_up_k, engr_lte_out_revb_up_n, engr_lte2_in_revb_dn_k, engr_lte2_in_revb_dn_n, engr_lte2_in_revb_up_k, engr_lte2_in_revb_up_n, engr_lte2_out_revb_dn_k, engr_lte2_out_revb_dn_n, engr_lte2_out_revb_up_k, engr_lte2_out_revb_up_n, engr_pcs_in_revb_dn_k, engr_pcs_in_revb_dn_n, engr_pcs_in_revb_up_k, engr_pcs_in_revb_up_n, engr_pcs_out_revb_dn_k, engr_pcs_out_revb_dn_n, engr_pcs_out_revb_up_k, engr_pcs_out_revb_up_n, engr_pilot_aws_out_revb_dn_k, engr_pilot_aws_out_revb_dn_n, engr_pilot_aws_out_revb_up_k, engr_pilot_aws_out_revb_up_n, engr_pilot_cell_out_revb_dn_, engr_pilot_cell_out_revb_dn_n, engr_pilot_cell_out_revb_up_k, engr_pilot_cell_out_revb_up_n, engr_pilot_pcs_out_revb_dn_k, engr_pilot_pcs_out_revb_dn_n, engr_pilot_pcs_out_revb_up_k, engr_pilot_pcs_out_revb_up_n, engr_scan_rcvr_cal_1, engr_scan_rcvr_cal_2, engr_scan_rcvr_cal2_0, engr_scan_rcvr_cal2_1, engr_scan_rcvr_cal2_2, engr_scan_rcvr_cell_cal_0, engr_scan_rcvr_cell_cal_1, engr_scan_rcvr_cell_cal_2, engr_scan_rcvr_lte_cal_0, engr_scan_rcvr_lte_cal_1, engr_scan_rcvr_lte_cal_2, engr_smr_in_revb_dn_k, engr_smr_in_revb_dn_n, engr_smr_in_revb_up_k, engr_smr_in_revb_up_n, engr_smr_out_revb_dn_k, engr_smr_out_revb_dn_n, engr_smr_out_revb_up_k, engr_smr_out_revb_up_n, engr_temp_gain_dn_k, engr_temp_gain_dn_n, engr_temp_gain_up_k, engr_temp_gain_up_n, engr_temp_gain1_dn_k, engr_temp_gain1_dn_n, engr_temp_gain1_up_k, engr_temp_gain1_up_n)";

						stmt.executeUpdate(query);
						System.out.println("The data is inserted into the engr table");
					}        

					catch(Exception e)
					{
						e.printStackTrace();
						stmt = null;
					}

					finally {
						try {
							if (connection != null) {
								connection.close();
							}
							if (stmt != null) {
								stmt.close();
							}
							// if (query != null) {
							//     query.close();
							//  }

						} catch (SQLException ex) {
							Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
							lgr.log(Level.WARNING, ex.getMessage(), ex);
						}
					}
				}
			} 
			catch (ClassNotFoundException e) {
				System.out.println("Where is your MySQL JDBC Driver?");
				e.printStackTrace();
				//	return;
			}


		}
		catch (IOException e) {  
			e.printStackTrace();  
		}

		return(map2);
	}


	//Caldata1.properties: read file, map key-values in TreeMap
	public static TreeMap<String, String> getPropertiesCaldata1(File file) throws IOException {
		final int lhs = 0;
		final int rhs = 1;

		TreeMap<String, String> map3 = new TreeMap<String, String>();
		BufferedReader  bfr = new BufferedReader(new FileReader(file));

		String line;
		String [] pair = null;
		while ((line = bfr.readLine()) != null) {
			try {
				if (!line.startsWith("#") && !line.isEmpty()) {
					pair = line.trim().split("=");
					if (pair.length == 2 ) {
						map3.put(pair[lhs].trim(), pair[rhs].trim());
					} 
					else {
						map3.put(pair[lhs].trim(), " ");
					}

				}
			}  catch (Exception e){
				e.printStackTrace();
			}
		}

		//get the specific properties and their values and write them into text file, close text file.
		String value = " ";
		String value0 = " ";
		String value1 = map3.get("calibration.dnlink.output.adc.goal");
		String value2 = map3.get("calibration.dnlink.output.power.goal");
		String value3 = map3.get("calibration.dnlink.output.adc.maximum");
		String value4 = map3.get("calibration.dnlink.output.power.maximum");
		String value5 = map3.get("calibration.uplink.output.adc.goal");
		String value6 = map3.get("calibration.uplink.output.power.goal");
		String value7 = map3.get("calibration.uplink.output.adc.maximum");
		String value8 = map3.get("calibration.uplink.output.power.maximum");

		bfr.close();

		File f = new File("C:\\Labview\\Properties\\caldata1property.txt");
		if(!f.exists())
		{
			try {
				f.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(f);
			//I want to print in standard "System.out" and in "file"
			TeeOutputStream myOut=new TeeOutputStream(System.out, fos);
			PrintStream ps = new PrintStream(myOut);
			System.setOut(ps);

			System.out.print(value + "\t");
			System.out.print(value0 + "\t");
			System.out.print(value1 + "\t");
			System.out.print(value2 + "\t");
			System.out.print(value3 + "\t");
			System.out.print(value4 + "\t");
			System.out.print(value5 + "\t");
			System.out.print(value6 + "\t");
			System.out.print(value7 + "\t");
			System.out.print(value8 + "\t");
			//	ps.close();
			//	myOut.close();
			fos.close();

			//config.properties file: contains the properties(url+database_name, username, password) to make JDBC connection with MySQL
			//get the properties and make the connection using Driver Manager
			Properties prop1 = new Properties();
			InputStream is;
			try{
				is = new FileInputStream("C:/Labview/Properties/DSP_II.properties");

				prop1.load(is);
				String url= prop1.getProperty("url");
				String un = prop1.getProperty("username");
				String pass = prop1.getProperty("password");
				//System.out.println("URL: "+url+" UN: "+un+" PASS: "+pass);
				is.close();
				Class.forName("com.mysql.jdbc.Driver");


				System.out.println("\nMySQL JDBC Driver Registered!");
				Connection connection = null;

				Statement stmt = null;
				String query = null;
				PreparedStatement pst = null;

				//Create the table
				try {
					connection = DriverManager.getConnection(url, un, pass);  
					pst = connection.prepareStatement("create table if not exists tbl_caldata_1(ID int not null primary key auto_increment, Serial_no varchar(100), Model_no varchar(100), calibration_dnlink_output_adc_goal varchar(30), calibration_dnlink_output_power_goal varchar(30), calibration_dnlink_output_adc_maximum varchar(30), calibration_dnlink_output_power_maximum varchar(30), calibration_uplink_output_adc_goal varchar(30), calibration_uplink_output_power_goal varchar(30), calibration_uplink_output_adc_maximum varchar(30), calibration_uplink_output_power_maximum varchar(30)) engine=InnoDB default charset=utf8;");
					pst.executeUpdate();
					System.out.println("The table is created");
				}

				catch (SQLException ex) {
					Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
					lgr.log(Level.SEVERE, ex.getMessage(), ex);
				}

				finally {
					try {
						if (connection != null) {
							connection.close();
						}
						if (pst != null) {
							pst.close();
						}

					} catch (SQLException ex) {
						Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
						lgr.log(Level.WARNING, ex.getMessage(), ex);
					}
				}

				//Insert the text file data (values) into table
				String fileName11= "caldata.properties";
				if (file.getName().equals(fileName11)){
					try {
						connection = DriverManager.getConnection(url, un, pass);                 	   
						System.out.println("Inserting data into the caldata1 table");
						stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

						query = "LOAD DATA INFILE 'C:/Labview/Properties/caldata1property.txt' INTO TABLE tbl_caldata_1(Serial_no, Model_no, calibration_dnlink_output_adc_goal, calibration_dnlink_output_power_goal, calibration_dnlink_output_adc_maximum, calibration_dnlink_output_power_maximum, calibration_uplink_output_adc_goal, calibration_uplink_output_power_goal, calibration_uplink_output_adc_maximum, calibration_uplink_output_power_maximum)";

						stmt.executeUpdate(query);
						System.out.println("The data is inserted into the caldata1 table");
					}        

					catch(Exception e)
					{
						e.printStackTrace();
						stmt = null;
					}

					finally {
						try {
							if (connection != null) {
								connection.close();
							}
							if (stmt != null) {
								stmt.close();
							}
							// if (query != null) {
							//     query.close();
							//  }

						} catch (SQLException ex) {
							Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
							lgr.log(Level.WARNING, ex.getMessage(), ex);
						}
					}
				}
			} 
			catch (ClassNotFoundException e) {
				System.out.println("Where is your MySQL JDBC Driver?");
				e.printStackTrace();
				//	return;
			}

		}
		catch (IOException e) {  
			e.printStackTrace();  
		}

		return(map3);
	}


	//Caldata2.properties: read file, map key-values in TreeMap
	public static TreeMap<String, String> getPropertiesCaldata2(File file) throws IOException {
		final int lhs = 0;
		final int rhs = 1;

		TreeMap<String, String> map6 = new TreeMap<String, String>();
		BufferedReader  bfr = new BufferedReader(new FileReader(file));

		String line;
		String [] pair = null;
		while ((line = bfr.readLine()) != null) {
			try {
				if (!line.startsWith("#") && !line.isEmpty()) {
					pair = line.trim().split("=");
					if (pair.length == 2 ) {
						map6.put(pair[lhs].trim(), pair[rhs].trim());
					} 
					else {
						map6.put(pair[lhs].trim(), " ");
					}

				}
			}  catch (Exception e){
				e.printStackTrace();
			}
		}

		//get the specific properties and their values and write them into text file, close text file.
		String value = " ";
		String value0 = " ";
		String value1 = map6.get("calibration.dnlink.output.adc.goal");
		String value2 = map6.get("calibration.dnlink.output.power.goal");
		String value3 = map6.get("calibration.dnlink.output.adc.maximum");
		String value4 = map6.get("calibration.dnlink.output.power.maximum");
		String value5 = map6.get("calibration.uplink.output.adc.goal");
		String value6 = map6.get("calibration.uplink.output.power.goal");
		String value7 = map6.get("calibration.uplink.output.adc.maximum");
		String value8 = map6.get("calibration.uplink.output.power.maximum");

		bfr.close();

		File f = new File("C:\\Labview\\Properties\\caldata2property.txt");
		if(!f.exists())
		{
			try {
				f.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(f);
			//I want to print in standard "System.out" and in "file"
			TeeOutputStream myOut=new TeeOutputStream(System.out, fos);
			PrintStream ps = new PrintStream(myOut);
			System.setOut(ps);

			System.out.print(value + "\t");
			System.out.print(value0 + "\t");
			System.out.print(value1 + "\t");
			System.out.print(value2 + "\t");
			System.out.print(value3 + "\t");
			System.out.print(value4 + "\t");
			System.out.print(value5 + "\t");
			System.out.print(value6 + "\t");
			System.out.print(value7 + "\t");
			System.out.print(value8 + "\t");
			//	ps.close();
			//	myOut.close();
			fos.close();

			//config.properties file: contains the properties(url+database_name, username, password) to make JDBC connection with MySQL
			//get the properties and make the connection using Driver Manager
			Properties prop1 = new Properties();
			InputStream is;
			try{
				is = new FileInputStream("C:/Labview/Properties/DSP_II.properties");

				prop1.load(is);
				String url= prop1.getProperty("url");
				String un = prop1.getProperty("username");
				String pass = prop1.getProperty("password");
				//System.out.println("URL: "+url+" UN: "+un+" PASS: "+pass);
				is.close();
				Class.forName("com.mysql.jdbc.Driver");


				System.out.println("\nMySQL JDBC Driver Registered!");
				Connection connection = null;

				Statement stmt = null;
				String query = null;
				PreparedStatement pst = null;

				//Create the table
				try {
					connection = DriverManager.getConnection(url, un, pass);  
					pst = connection.prepareStatement("create table if not exists tbl_caldata_2(ID int not null primary key auto_increment, Serial_no varchar(100), Model_no varchar(100), calibration_dnlink_output_adc_goal varchar(30), calibration_dnlink_output_power_goal varchar(30), calibration_dnlink_output_adc_maximum varchar(30), calibration_dnlink_output_power_maximum varchar(30), calibration_uplink_output_adc_goal varchar(30), calibration_uplink_output_power_goal varchar(30), calibration_uplink_output_adc_maximum varchar(30), calibration_uplink_output_power_maximum varchar(30)) engine=InnoDB default charset=utf8;");
					pst.executeUpdate();
					System.out.println("The table is created");
				}


				catch (SQLException ex) {
					Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
					lgr.log(Level.SEVERE, ex.getMessage(), ex);
				}

				finally {
					try {
						if (connection != null) {
							connection.close();
						}
						if (pst != null) {
							pst.close();
						}

					} catch (SQLException ex) {
						Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
						lgr.log(Level.WARNING, ex.getMessage(), ex);
					}
				}

				//Insert the text file data (values) into table
				String fileName11= "caldata.properties";
				if (file.getName().equals(fileName11)){
					try {
						connection = DriverManager.getConnection(url, un, pass);                 	   
						System.out.println("Inserting data into the caldata2 table");
						stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

						query = "LOAD DATA INFILE 'C:/Labview/Properties/caldata2property.txt' INTO TABLE tbl_caldata_2(Serial_no, Model_no, calibration_dnlink_output_adc_goal, calibration_dnlink_output_power_goal, calibration_dnlink_output_adc_maximum, calibration_dnlink_output_power_maximum, calibration_uplink_output_adc_goal, calibration_uplink_output_power_goal, calibration_uplink_output_adc_maximum, calibration_uplink_output_power_maximum)";

						stmt.executeUpdate(query);
						System.out.println("The data is inserted into the caldata2 table");
					}        

					catch(Exception e)
					{
						e.printStackTrace();
						stmt = null;
					}

					finally {
						try {
							if (connection != null) {
								connection.close();
							}
							if (stmt != null) {
								stmt.close();
							}
							// if (query != null) {
							//     query.close();
							//  }

						} catch (SQLException ex) {
							Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
							lgr.log(Level.WARNING, ex.getMessage(), ex);
						}
					}
				}
			} 
			catch (ClassNotFoundException e) {
				System.out.println("Where is your MySQL JDBC Driver?");
				e.printStackTrace();
				//	return;
			}

		}
		catch (IOException e) {  
			e.printStackTrace();  
		}

		return(map6);
	}


	//Link1.properties: read file, map key-values in TreeMap
	public static TreeMap<String, String> getPropertiesLink1(File file) throws IOException {
		final int lhs = 0;
		final int rhs = 1;

		TreeMap<String, String> map4 = new TreeMap<String, String>();
		BufferedReader  bfr = new BufferedReader(new FileReader(file));

		String line;
		String [] pair = null;
		while ((line = bfr.readLine()) != null) {
			try {
				if (!line.startsWith("#") && !line.isEmpty()) {
					pair = line.trim().split("=");
					if (pair.length == 2 ) {
						map4.put(pair[lhs].trim(), pair[rhs].trim());
					} 
					else {
						map4.put(pair[lhs].trim(), " ");
					}

				}
			}  catch (Exception e){
				e.printStackTrace();
			}
		}

		//get the specific properties and their values and write them into text file, close text file.
		String value = " ";
		String value0 = " ";
		String value1 = map4.get("link.config.board.rev");
		String value2 = map4.get("link.config.board.type");
		String value3 = map4.get("link.uplink.attenuator.one");
		String value4 = map4.get("link.dnlink.attenuator.one");
		String value5 = map4.get("link.uplink.attenuator.two");
		String value6 = map4.get("link.dnlink.attenuator.two");
		String value7 = map4.get("link.uplink.attenuator.three");
		String value8 = map4.get("link.dnlink.attenuator.three");
		String value9 = map4.get("link.dnlink.dac.byte1");
		String value10 = map4.get("link.dnlink.dac.byte2");
		String value11 = map4.get("link.dnlink.dac.byte3");
		String value12 = map4.get("link.dnlink.dac.byte4");
		String value13 = map4.get("link.dnlink.dac.byte5");
		String value14 = map4.get("link.dnlink.dac.byte6");
		String value15 = map4.get("link.dnlink.dac.byte7");
		String value16 = map4.get("link.dnlink.dac.byte8");
		String value17 = map4.get("link.dnlink.dac.byte9");
		String value18 = map4.get("link.dnlink.dac.byte10");
		String value19 = map4.get("link.dnlink.dac.byte11");
		String value20 = map4.get("link.dnlink.dac.byte12");
		String value21 = map4.get("link.dnlink.dac.byte13");
		String value22 = map4.get("link.uplink.dac.byte1");
		String value23 = map4.get("link.uplink.dac.byte2");
		String value24 = map4.get("link.uplink.dac.byte3");
		String value25 = map4.get("link.uplink.dac.byte4");
		String value26 = map4.get("link.uplink.dac.byte5");
		String value27 = map4.get("link.uplink.dac.byte6");
		String value28 = map4.get("link.uplink.dac.byte7");
		String value29 = map4.get("link.uplink.dac.byte8");
		String value30 = map4.get("link.uplink.dac.byte9");
		String value31 = map4.get("link.uplink.dac.byte10");
		String value32 = map4.get("link.uplink.dac.byte11");
		String value33 = map4.get("link.uplink.dac.byte12");
		String value34 = map4.get("link.uplink.dac.byte13");
		String value35 = map4.get("link.dnlink.dac1.byte1");
		String value36 = map4.get("link.dnlink.dac1.byte2");
		String value37 = map4.get("link.dnlink.dac1.byte3");
		String value38 = map4.get("link.dnlink.dac1.byte4");
		String value39 = map4.get("link.dnlink.dac1.byte5");
		String value40 = map4.get("link.dnlink.dac1.byte6");
		String value41 = map4.get("link.dnlink.dac1.byte7");
		String value42 = map4.get("link.dnlink.dac1.byte8");
		String value43 = map4.get("link.dnlink.dac1.byte9");
		String value44 = map4.get("link.dnlink.dac1.byte10");
		String value45 = map4.get("link.dnlink.dac1.byte11");
		String value46 = map4.get("link.dnlink.dac1.byte12");
		String value47 = map4.get("link.dnlink.dac1.byte13");
		String value48 = map4.get("link.dnlink.dac1.byte14");
		String value49 = map4.get("link.dnlink.dac1.byte15");
		String value50 = map4.get("link.dnlink.dac1.byte16");
		String value51 = map4.get("link.dnlink.dac1.byte17");
		String value52 = map4.get("link.dnlink.dac1.byte18");
		String value53 = map4.get("link.dnlink.dac1.byte19");
		String value54 = map4.get("link.uplink.dac1.byte1");
		String value55 = map4.get("link.uplink.dac1.byte2");
		String value56 = map4.get("link.uplink.dac1.byte3");
		String value57 = map4.get("link.uplink.dac1.byte4");
		String value58 = map4.get("link.uplink.dac1.byte5");
		String value59 = map4.get("link.uplink.dac1.byte6");
		String value60 = map4.get("link.uplink.dac1.byte7");
		String value61 = map4.get("link.uplink.dac1.byte8");
		String value62 = map4.get("link.uplink.dac1.byte9");
		String value63 = map4.get("link.uplink.dac1.byte10");
		String value64 = map4.get("link.uplink.dac1.byte11");
		String value65 = map4.get("link.uplink.dac1.byte12");
		String value66 = map4.get("link.uplink.dac1.byte13");
		String value67 = map4.get("link.uplink.dac1.byte14");
		String value68 = map4.get("link.uplink.dac1.byte15");
		String value69 = map4.get("link.uplink.dac1.byte16");
		String value70 = map4.get("link.uplink.dac1.byte17");
		String value71 = map4.get("link.uplink.dac1.byte18");
		String value72 = map4.get("link.uplink.dac1.byte19");

		bfr.close();

		File f = new File("C:\\Labview\\Properties\\link1property.txt");
		if(!f.exists())
		{
			try {
				f.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(f);
			//I want to print in standard "System.out" and in "file"
			TeeOutputStream myOut=new TeeOutputStream(System.out, fos);
			PrintStream ps = new PrintStream(myOut);
			System.setOut(ps);

			System.out.print(value + "\t");
			System.out.print(value0 + "\t");
			System.out.print(value1 + "\t");  
			System.out.print(value2 + "\t");
			System.out.print(value3 + "\t");
			System.out.print(value4 + "\t"); 	
			System.out.print(value5 + "\t"); 	
			System.out.print(value6 + "\t"); 	
			System.out.print(value7 + "\t"); 	
			System.out.print(value8 + "\t"); 	
			System.out.print(value9 + "\t"); 	
			System.out.print(value10 + "\t"); 	
			System.out.print(value11 + "\t"); 	
			System.out.print(value12 + "\t"); 		
			System.out.print(value13 + "\t"); 	
			System.out.print(value14 + "\t"); 	
			System.out.print(value15 + "\t"); 	
			System.out.print(value16 + "\t"); 	
			System.out.print(value17 + "\t"); 	
			System.out.print(value18 + "\t"); 	
			System.out.print(value19 + "\t"); 	
			System.out.print(value20 + "\t"); 	
			System.out.print(value21 + "\t"); 	
			System.out.print(value22 + "\t"); 	
			System.out.print(value23 + "\t"); 	
			System.out.print(value24 + "\t"); 	
			System.out.print(value25 + "\t"); 	
			System.out.print(value26 + "\t"); 	
			System.out.print(value27 + "\t"); 	
			System.out.print(value28 + "\t"); 	
			System.out.print(value29 + "\t"); 	
			System.out.print(value30 + "\t"); 	
			System.out.print(value31 + "\t"); 	
			System.out.print(value32 + "\t"); 	
			System.out.print(value33 + "\t"); 	
			System.out.print(value34 + "\t"); 
			System.out.print(value35 + "\t"); 
			System.out.print(value36 + "\t"); 
			System.out.print(value37 + "\t"); 
			System.out.print(value38 + "\t"); 
			System.out.print(value39 + "\t"); 
			System.out.print(value40 + "\t"); 
			System.out.print(value41 + "\t"); 
			System.out.print(value42 + "\t"); 
			System.out.print(value43 + "\t"); 	
			System.out.print(value44 + "\t"); 	
			System.out.print(value45 + "\t"); 	
			System.out.print(value46 + "\t"); 	
			System.out.print(value47 + "\t"); 	
			System.out.print(value48 + "\t"); 	
			System.out.print(value49 + "\t"); 	
			System.out.print(value50 + "\t"); 	
			System.out.print(value51 + "\t"); 	
			System.out.print(value52 + "\t"); 	
			System.out.print(value53 + "\t"); 	
			System.out.print(value54 + "\t"); 	
			System.out.print(value55 + "\t"); 	
			System.out.print(value56 + "\t"); 	
			System.out.print(value57 + "\t"); 	
			System.out.print(value58 + "\t"); 	
			System.out.print(value59 + "\t"); 	
			System.out.print(value60 + "\t"); 	
			System.out.print(value61 + "\t"); 	
			System.out.print(value62 + "\t"); 	
			System.out.print(value63 + "\t"); 	
			System.out.print(value64 + "\t"); 	
			System.out.print(value65 + "\t"); 	
			System.out.print(value66 + "\t"); 	
			System.out.print(value67 + "\t"); 	
			System.out.print(value68 + "\t"); 	
			System.out.print(value69 + "\t"); 	
			System.out.print(value70 + "\t"); 	
			System.out.print(value71 + "\t"); 
			System.out.print(value72 + "\t");

			fos.close();

			//config.properties file: contains the properties(url+database_name, username, password) to make JDBC connection with MySQL
			//get the properties and make the connection using Driver Manager
			Properties prop1 = new Properties();
			InputStream is;
			try {
				is = new FileInputStream("C:/Labview/Properties/DSP_II.properties");

				prop1.load(is);
				String url= prop1.getProperty("url");
				String un = prop1.getProperty("username");
				String pass = prop1.getProperty("password");
				//System.out.println("URL: "+url+" UN: "+un+" PASS: "+pass);
				is.close();
				Class.forName("com.mysql.jdbc.Driver");

				System.out.println("\nMySQL JDBC Driver Registered!");
				Connection connection = null;

				Statement stmt = null;
				String query = null;
				PreparedStatement pst = null;

				//Create the table
				try {
					connection = DriverManager.getConnection(url, un, pass);  
					pst = connection.prepareStatement("create table if not exists tbl_link_1(ID int not null primary key auto_increment, Serial_no varchar(100), Model_no varchar(100), link_config_board_rev varchar(30), link_config_board_type varchar(30), link_uplink_attenuator_one varchar(30), link_dnlink_attenuator_one varchar(30), link_uplink_attenuator_two varchar(30), link_dnlink_attenuator_two varchar(30), link_uplink_attenuator_three varchar(30), link_dnlink_attenuator_three varchar(30), link_dnlink_dac_byte1 varchar(30), link_dnlink_dac_byte2 varchar(30), link_dnlink_dac_byte3 varchar(30), link_dnlink_dac_byte4 varchar(30), link_dnlink_dac_byte5 varchar(30), link_dnlink_dac_byte6 varchar(30), link_dnlink_dac_byte7 varchar(30), link_dnlink_dac_byte8 varchar(30), link_dnlink_dac_byte9 varchar(30), link_dnlink_dac_byte10 varchar(30), link_dnlink_dac_byte11 varchar(30), link_dnlink_dac_byte12 varchar(30), link_dnlink_dac_byte13 varchar(30), link_uplink_dac_byte1 varchar(30), link_uplink_dac_byte2 varchar(30), link_uplink_dac_byte3 varchar(30), link_uplink_dac_byte4 varchar(30), link_uplink_dac_byte5 varchar(30), link_uplink_dac_byte6 varchar(30), link_uplink_dac_byte7 varchar(30), link_uplink_dac_byte8 varchar(30), link_uplink_dac_byte9 varchar(30), link_uplink_dac_byte10 varchar(30), link_uplink_dac_byte11 varchar(30), link_uplink_dac_byte12 varchar(30), link_uplink_dac_byte13 varchar(30), link_dnlink_dac1_byte1 varchar(30), link_dnlink_dac1_byte2 varchar(30), link_dnlink_dac1_byte3 varchar(30), link_dnlink_dac1_byte4 varchar(30), link_dnlink_dac1_byte5 varchar(30), link_dnlink_dac1_byte6 varchar(30), link_dnlink_dac1_byte7 varchar(30), link_dnlink_dac1_byte8 varchar(30), link_dnlink_dac1_byte9 varchar(30), link_dnlink_dac1_byte10 varchar(30), link_dnlink_dac1_byte11 varchar(30), link_dnlink_dac1_byte12 varchar(30), link_dnlink_dac1_byte13 varchar(30), link_dnlink_dac1_byte14 varchar(30), link_dnlink_dac1_byte15 varchar(30), link_dnlink_dac1_byte16 varchar(30), link_dnlink_dac1_byte17 varchar(30), link_dnlink_dac1_byte18 varchar(30), link_dnlink_dac1_byte19 varchar(30), link_uplink_dac1_byte1 varchar(30), link_uplink_dac1_byte2 varchar(30), link_uplink_dac1_byte3 varchar(30), link_uplink_dac1_byte4 varchar(30), link_uplink_dac1_byte5 varchar(30), link_uplink_dac1_byte6 varchar(30), link_uplink_dac1_byte7 varchar(30), link_uplink_dac1_byte8 varchar(30), link_uplink_dac1_byte9 varchar(30), link_uplink_dac1_byte10 varchar(30), link_uplink_dac1_byte11 varchar(30), link_uplink_dac1_byte12 varchar(30), link_uplink_dac1_byte13 varchar(30), link_uplink_dac1_byte14 varchar(30), link_uplink_dac1_byte15 varchar(30), link_uplink_dac1_byte16 varchar(30), link_uplink_dac1_byte17 varchar(30), link_uplink_dac1_byte18 varchar(30), link_uplink_dac1_byte19 varchar(30)) engine=InnoDB default charset=utf8;");
					pst.executeUpdate();
					System.out.println("The table is created");
				}

				catch (SQLException ex) {
					Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
					lgr.log(Level.SEVERE, ex.getMessage(), ex);
				}

				finally {
					try {
						if (connection != null) {
							connection.close();
						}
						if (pst != null) {
							pst.close();
						}

					} catch (SQLException ex) {
						Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
						lgr.log(Level.WARNING, ex.getMessage(), ex);
					}
				}

				//Insert the text file data (values) into table
				String fileName11= "link.properties";
				if (file.getName().equals(fileName11)){
					try {
						connection = DriverManager.getConnection(url, un, pass);                 	   
						System.out.println("Inserting data into the link1 table");
						stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

						query = "LOAD DATA INFILE 'C:/Labview/Properties/link1property.txt' INTO TABLE tbl_link_1(Serial_no, Model_no, link_config_board_rev, link_config_board_type, link_uplink_attenuator_one, link_dnlink_attenuator_one, link_uplink_attenuator_two, link_dnlink_attenuator_two, link_uplink_attenuator_three, link_dnlink_attenuator_three, link_dnlink_dac_byte1, link_dnlink_dac_byte2, link_dnlink_dac_byte3, link_dnlink_dac_byte4, link_dnlink_dac_byte5, link_dnlink_dac_byte6, link_dnlink_dac_byte7, link_dnlink_dac_byte8, link_dnlink_dac_byte9, link_dnlink_dac_byte10, link_dnlink_dac_byte11, link_dnlink_dac_byte12, link_dnlink_dac_byte13, link_uplink_dac_byte1, link_uplink_dac_byte2, link_uplink_dac_byte3, link_uplink_dac_byte4, link_uplink_dac_byte5, link_uplink_dac_byte6, link_uplink_dac_byte7, link_uplink_dac_byte8, link_uplink_dac_byte9, link_uplink_dac_byte10, link_uplink_dac_byte11, link_uplink_dac_byte12, link_uplink_dac_byte13, link_dnlink_dac1_byte1, link_dnlink_dac1_byte2, link_dnlink_dac1_byte3, link_dnlink_dac1_byte4, link_dnlink_dac1_byte5, link_dnlink_dac1_byte6, link_dnlink_dac1_byte7, link_dnlink_dac1_byte8, link_dnlink_dac1_byte9, link_dnlink_dac1_byte10, link_dnlink_dac1_byte11, link_dnlink_dac1_byte12, link_dnlink_dac1_byte13, link_dnlink_dac1_byte14, link_dnlink_dac1_byte15, link_dnlink_dac1_byte16, link_dnlink_dac1_byte17, link_dnlink_dac1_byte18, link_dnlink_dac1_byte19, link_uplink_dac1_byte1, link_uplink_dac1_byte2, link_uplink_dac1_byte3, link_uplink_dac1_byte4, link_uplink_dac1_byte5, link_uplink_dac1_byte6, link_uplink_dac1_byte7, link_uplink_dac1_byte8, link_uplink_dac1_byte9, link_uplink_dac1_byte10, link_uplink_dac1_byte11, link_uplink_dac1_byte12, link_uplink_dac1_byte13, link_uplink_dac1_byte14, link_uplink_dac1_byte15, link_uplink_dac1_byte16, link_uplink_dac1_byte17, link_uplink_dac1_byte18, link_uplink_dac1_byte19)";

						stmt.executeUpdate(query);
						System.out.println("The data is inserted into the link1 table");
					}        

					catch(Exception e)
					{
						e.printStackTrace();
						stmt = null;
					}

					finally {
						try {
							if (connection != null) {
								connection.close();
							}
							if (stmt != null) {
								stmt.close();
							}
							// if (query != null) {
							//     query.close();
							//  }

						} catch (SQLException ex) {
							Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
							lgr.log(Level.WARNING, ex.getMessage(), ex);
						}
					}
				}
			} 
			catch (ClassNotFoundException e) {
				System.out.println("Where is your MySQL JDBC Driver?");
				e.printStackTrace();
				//	return;
			}

		}
		catch (IOException e) {  
			e.printStackTrace();  
		}

		return(map4);
	}


	//Link2.properties: read file, map key-values in TreeMap
	public static TreeMap<String, String> getPropertiesLink2(File file) throws IOException {
		final int lhs = 0;
		final int rhs = 1;

		TreeMap<String, String> map7 = new TreeMap<String, String>();
		BufferedReader  bfr = new BufferedReader(new FileReader(file));

		String line;
		String [] pair = null;
		while ((line = bfr.readLine()) != null) {
			try {
				if (!line.startsWith("#") && !line.isEmpty()) {
					pair = line.trim().split("=");
					if (pair.length == 2 ) {
						map7.put(pair[lhs].trim(), pair[rhs].trim());
					} 
					else {
						map7.put(pair[lhs].trim(), " ");
					}

				}
			}  catch (Exception e){
				e.printStackTrace();
			}
		}

		//get the specific properties and their values and write them into text file, close text file.
		String value = " ";
		String value0 = " ";
		String value1 = map7.get("link.config.board.rev");
		String value2 = map7.get("link.config.board.type");
		String value3 = map7.get("link.uplink.attenuator.one");
		String value4 = map7.get("link.dnlink.attenuator.one");
		String value5 = map7.get("link.uplink.attenuator.two");
		String value6 = map7.get("link.dnlink.attenuator.two");
		String value7 = map7.get("link.uplink.attenuator.three");
		String value8 = map7.get("link.dnlink.attenuator.three");
		String value9 = map7.get("link.dnlink.dac.byte1");
		String value10 = map7.get("link.dnlink.dac.byte2");
		String value11 = map7.get("link.dnlink.dac.byte3");
		String value12 = map7.get("link.dnlink.dac.byte4");
		String value13 = map7.get("link.dnlink.dac.byte5");
		String value14 = map7.get("link.dnlink.dac.byte6");
		String value15 = map7.get("link.dnlink.dac.byte7");
		String value16 = map7.get("link.dnlink.dac.byte8");
		String value17 = map7.get("link.dnlink.dac.byte9");
		String value18 = map7.get("link.dnlink.dac.byte10");
		String value19 = map7.get("link.dnlink.dac.byte11");
		String value20 = map7.get("link.dnlink.dac.byte12");
		String value21 = map7.get("link.dnlink.dac.byte13");
		String value22 = map7.get("link.uplink.dac.byte1");
		String value23 = map7.get("link.uplink.dac.byte2");
		String value24 = map7.get("link.uplink.dac.byte3");
		String value25 = map7.get("link.uplink.dac.byte4");
		String value26 = map7.get("link.uplink.dac.byte5");
		String value27 = map7.get("link.uplink.dac.byte6");
		String value28 = map7.get("link.uplink.dac.byte7");
		String value29 = map7.get("link.uplink.dac.byte8");
		String value30 = map7.get("link.uplink.dac.byte9");
		String value31 = map7.get("link.uplink.dac.byte10");
		String value32 = map7.get("link.uplink.dac.byte11");
		String value33 = map7.get("link.uplink.dac.byte12");
		String value34 = map7.get("link.uplink.dac.byte13");
		String value35 = map7.get("link.dnlink.dac1.byte1");
		String value36 = map7.get("link.dnlink.dac1.byte2");
		String value37 = map7.get("link.dnlink.dac1.byte3");
		String value38 = map7.get("link.dnlink.dac1.byte4");
		String value39 = map7.get("link.dnlink.dac1.byte5");
		String value40 = map7.get("link.dnlink.dac1.byte6");
		String value41 = map7.get("link.dnlink.dac1.byte7");
		String value42 = map7.get("link.dnlink.dac1.byte8");
		String value43 = map7.get("link.dnlink.dac1.byte9");
		String value44 = map7.get("link.dnlink.dac1.byte10");
		String value45 = map7.get("link.dnlink.dac1.byte11");
		String value46 = map7.get("link.dnlink.dac1.byte12");
		String value47 = map7.get("link.dnlink.dac1.byte13");
		String value48 = map7.get("link.dnlink.dac1.byte14");
		String value49 = map7.get("link.dnlink.dac1.byte15");
		String value50 = map7.get("link.dnlink.dac1.byte16");
		String value51 = map7.get("link.dnlink.dac1.byte17");
		String value52 = map7.get("link.dnlink.dac1.byte18");
		String value53 = map7.get("link.dnlink.dac1.byte19");
		String value54 = map7.get("link.uplink.dac1.byte1");
		String value55 = map7.get("link.uplink.dac1.byte2");
		String value56 = map7.get("link.uplink.dac1.byte3");
		String value57 = map7.get("link.uplink.dac1.byte4");
		String value58 = map7.get("link.uplink.dac1.byte5");
		String value59 = map7.get("link.uplink.dac1.byte6");
		String value60 = map7.get("link.uplink.dac1.byte7");
		String value61 = map7.get("link.uplink.dac1.byte8");
		String value62 = map7.get("link.uplink.dac1.byte9");
		String value63 = map7.get("link.uplink.dac1.byte10");
		String value64 = map7.get("link.uplink.dac1.byte11");
		String value65 = map7.get("link.uplink.dac1.byte12");
		String value66 = map7.get("link.uplink.dac1.byte13");
		String value67 = map7.get("link.uplink.dac1.byte14");
		String value68 = map7.get("link.uplink.dac1.byte15");
		String value69 = map7.get("link.uplink.dac1.byte16");
		String value70 = map7.get("link.uplink.dac1.byte17");
		String value71 = map7.get("link.uplink.dac1.byte18");
		String value72 = map7.get("link.uplink.dac1.byte19");

		bfr.close();

		File f = new File("C:\\Labview\\Properties\\link2property.txt");
		if(!f.exists())
		{
			try {
				f.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(f);
			//I want to print in standard "System.out" and in "file"
			TeeOutputStream myOut=new TeeOutputStream(System.out, fos);
			PrintStream ps = new PrintStream(myOut);
			System.setOut(ps);

			System.out.print(value + "\t");
			System.out.print(value0 + "\t");
			System.out.print(value1 + "\t");  
			System.out.print(value2 + "\t");
			System.out.print(value3 + "\t");
			System.out.print(value4 + "\t"); 	
			System.out.print(value5 + "\t"); 	
			System.out.print(value6 + "\t"); 	
			System.out.print(value7 + "\t"); 	
			System.out.print(value8 + "\t"); 	
			System.out.print(value9 + "\t"); 	
			System.out.print(value10 + "\t"); 	
			System.out.print(value11 + "\t"); 	
			System.out.print(value12 + "\t"); 		
			System.out.print(value13 + "\t"); 	
			System.out.print(value14 + "\t"); 	
			System.out.print(value15 + "\t"); 	
			System.out.print(value16 + "\t"); 	
			System.out.print(value17 + "\t"); 	
			System.out.print(value18 + "\t"); 	
			System.out.print(value19 + "\t"); 	
			System.out.print(value20 + "\t"); 	
			System.out.print(value21 + "\t"); 	
			System.out.print(value22 + "\t"); 	
			System.out.print(value23 + "\t"); 	
			System.out.print(value24 + "\t"); 	
			System.out.print(value25 + "\t"); 	
			System.out.print(value26 + "\t"); 	
			System.out.print(value27 + "\t"); 	
			System.out.print(value28 + "\t"); 	
			System.out.print(value29 + "\t"); 	
			System.out.print(value30 + "\t"); 	
			System.out.print(value31 + "\t"); 	
			System.out.print(value32 + "\t"); 	
			System.out.print(value33 + "\t"); 	
			System.out.print(value34 + "\t"); 
			System.out.print(value35 + "\t"); 
			System.out.print(value36 + "\t"); 
			System.out.print(value37 + "\t"); 
			System.out.print(value38 + "\t"); 
			System.out.print(value39 + "\t"); 
			System.out.print(value40 + "\t"); 
			System.out.print(value41 + "\t"); 
			System.out.print(value42 + "\t"); 
			System.out.print(value43 + "\t"); 	
			System.out.print(value44 + "\t"); 	
			System.out.print(value45 + "\t"); 	
			System.out.print(value46 + "\t"); 	
			System.out.print(value47 + "\t"); 	
			System.out.print(value48 + "\t"); 	
			System.out.print(value49 + "\t"); 	
			System.out.print(value50 + "\t"); 	
			System.out.print(value51 + "\t"); 	
			System.out.print(value52 + "\t"); 	
			System.out.print(value53 + "\t"); 	
			System.out.print(value54 + "\t"); 	
			System.out.print(value55 + "\t"); 	
			System.out.print(value56 + "\t"); 	
			System.out.print(value57 + "\t"); 	
			System.out.print(value58 + "\t"); 	
			System.out.print(value59 + "\t"); 	
			System.out.print(value60 + "\t"); 	
			System.out.print(value61 + "\t"); 	
			System.out.print(value62 + "\t"); 	
			System.out.print(value63 + "\t"); 	
			System.out.print(value64 + "\t"); 	
			System.out.print(value65 + "\t"); 	
			System.out.print(value66 + "\t"); 	
			System.out.print(value67 + "\t"); 	
			System.out.print(value68 + "\t"); 	
			System.out.print(value69 + "\t"); 	
			System.out.print(value70 + "\t"); 	
			System.out.print(value71 + "\t"); 
			System.out.print(value72 + "\t");

			//	ps.close();
			//	myOut.close();
			fos.close();

			//config.properties file: contains the properties(url+database_name, username, password) to make JDBC connection with MySQL
			//get the properties and make the connection using Driver Manager
			Properties prop1 = new Properties();
			InputStream is;
			try{
				is = new FileInputStream("C:/Labview/Properties/DSP_II.properties");

				prop1.load(is);
				String url= prop1.getProperty("url");
				String un = prop1.getProperty("username");
				String pass = prop1.getProperty("password");
				//System.out.println("URL: "+url+" UN: "+un+" PASS: "+pass);
				is.close();
				Class.forName("com.mysql.jdbc.Driver");


				System.out.println("\nMySQL JDBC Driver Registered!");
				Connection connection = null;

				Statement stmt = null;
				String query = null;
				PreparedStatement pst = null;

				//Create the table
				try
				{
					connection = DriverManager.getConnection(url, un, pass);  
					pst = connection.prepareStatement("create table if not exists tbl_link_2(ID int not null primary key auto_increment, Serial_no varchar(100), Model_no varchar(100), link_config_board_rev varchar(30), link_config_board_type varchar(30), link_uplink_attenuator_one varchar(30), link_dnlink_attenuator_one varchar(30), link_uplink_attenuator_two varchar(30), link_dnlink_attenuator_two varchar(30), link_uplink_attenuator_three varchar(30), link_dnlink_attenuator_three varchar(30), link_dnlink_dac_byte1 varchar(30), link_dnlink_dac_byte2 varchar(30), link_dnlink_dac_byte3 varchar(30), link_dnlink_dac_byte4 varchar(30), link_dnlink_dac_byte5 varchar(30), link_dnlink_dac_byte6 varchar(30), link_dnlink_dac_byte7 varchar(30), link_dnlink_dac_byte8 varchar(30), link_dnlink_dac_byte9 varchar(30), link_dnlink_dac_byte10 varchar(30), link_dnlink_dac_byte11 varchar(30), link_dnlink_dac_byte12 varchar(30), link_dnlink_dac_byte13 varchar(30), link_uplink_dac_byte1 varchar(30), link_uplink_dac_byte2 varchar(30), link_uplink_dac_byte3 varchar(30), link_uplink_dac_byte4 varchar(30), link_uplink_dac_byte5 varchar(30), link_uplink_dac_byte6 varchar(30), link_uplink_dac_byte7 varchar(30), link_uplink_dac_byte8 varchar(30), link_uplink_dac_byte9 varchar(30), link_uplink_dac_byte10 varchar(30), link_uplink_dac_byte11 varchar(30), link_uplink_dac_byte12 varchar(30), link_uplink_dac_byte13 varchar(30), link_dnlink_dac1_byte1 varchar(30), link_dnlink_dac1_byte2 varchar(30), link_dnlink_dac1_byte3 varchar(30), link_dnlink_dac1_byte4 varchar(30), link_dnlink_dac1_byte5 varchar(30), link_dnlink_dac1_byte6 varchar(30), link_dnlink_dac1_byte7 varchar(30), link_dnlink_dac1_byte8 varchar(30), link_dnlink_dac1_byte9 varchar(30), link_dnlink_dac1_byte10 varchar(30), link_dnlink_dac1_byte11 varchar(30), link_dnlink_dac1_byte12 varchar(30), link_dnlink_dac1_byte13 varchar(30), link_dnlink_dac1_byte14 varchar(30), link_dnlink_dac1_byte15 varchar(30), link_dnlink_dac1_byte16 varchar(30), link_dnlink_dac1_byte17 varchar(30), link_dnlink_dac1_byte18 varchar(30), link_dnlink_dac1_byte19 varchar(30), link_uplink_dac1_byte1 varchar(30), link_uplink_dac1_byte2 varchar(30), link_uplink_dac1_byte3 varchar(30), link_uplink_dac1_byte4 varchar(30), link_uplink_dac1_byte5 varchar(30), link_uplink_dac1_byte6 varchar(30), link_uplink_dac1_byte7 varchar(30), link_uplink_dac1_byte8 varchar(30), link_uplink_dac1_byte9 varchar(30), link_uplink_dac1_byte10 varchar(30), link_uplink_dac1_byte11 varchar(30), link_uplink_dac1_byte12 varchar(30), link_uplink_dac1_byte13 varchar(30), link_uplink_dac1_byte14 varchar(30), link_uplink_dac1_byte15 varchar(30), link_uplink_dac1_byte16 varchar(30), link_uplink_dac1_byte17 varchar(30), link_uplink_dac1_byte18 varchar(30), link_uplink_dac1_byte19 varchar(30)) engine=InnoDB default charset=utf8;");
					pst.executeUpdate();
					System.out.println("The table is created");
				}

				catch (SQLException ex) {
					Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
					lgr.log(Level.SEVERE, ex.getMessage(), ex);
				}

				finally {
					try {
						if (connection != null) {
							connection.close();
						}
						if (pst != null) {
							pst.close();
						}

					} catch (SQLException ex) {
						Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
						lgr.log(Level.WARNING, ex.getMessage(), ex);
					}
				}

				//Insert the text file data (values) into table
				String fileName11= "link.properties";
				if (file.getName().equals(fileName11)){
					try {
						connection = DriverManager.getConnection(url, un, pass);                 	   
						System.out.println("Inserting data into the link2 table");
						stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

						query = "LOAD DATA INFILE 'C:/Labview/Properties/link2property.txt' INTO TABLE tbl_link_2(Serial_no, Model_no, link_config_board_rev, link_config_board_type, link_uplink_attenuator_one, link_dnlink_attenuator_one, link_uplink_attenuator_two, link_dnlink_attenuator_two, link_uplink_attenuator_three, link_dnlink_attenuator_three, link_dnlink_dac_byte1, link_dnlink_dac_byte2, link_dnlink_dac_byte3, link_dnlink_dac_byte4, link_dnlink_dac_byte5, link_dnlink_dac_byte6, link_dnlink_dac_byte7, link_dnlink_dac_byte8, link_dnlink_dac_byte9, link_dnlink_dac_byte10, link_dnlink_dac_byte11, link_dnlink_dac_byte12, link_dnlink_dac_byte13, link_uplink_dac_byte1, link_uplink_dac_byte2, link_uplink_dac_byte3, link_uplink_dac_byte4, link_uplink_dac_byte5, link_uplink_dac_byte6, link_uplink_dac_byte7, link_uplink_dac_byte8, link_uplink_dac_byte9, link_uplink_dac_byte10, link_uplink_dac_byte11, link_uplink_dac_byte12, link_uplink_dac_byte13, link_dnlink_dac1_byte1, link_dnlink_dac1_byte2, link_dnlink_dac1_byte3, link_dnlink_dac1_byte4, link_dnlink_dac1_byte5, link_dnlink_dac1_byte6, link_dnlink_dac1_byte7, link_dnlink_dac1_byte8, link_dnlink_dac1_byte9, link_dnlink_dac1_byte10, link_dnlink_dac1_byte11, link_dnlink_dac1_byte12, link_dnlink_dac1_byte13, link_dnlink_dac1_byte14, link_dnlink_dac1_byte15, link_dnlink_dac1_byte16, link_dnlink_dac1_byte17, link_dnlink_dac1_byte18, link_dnlink_dac1_byte19, link_uplink_dac1_byte1, link_uplink_dac1_byte2, link_uplink_dac1_byte3, link_uplink_dac1_byte4, link_uplink_dac1_byte5, link_uplink_dac1_byte6, link_uplink_dac1_byte7, link_uplink_dac1_byte8, link_uplink_dac1_byte9, link_uplink_dac1_byte10, link_uplink_dac1_byte11, link_uplink_dac1_byte12, link_uplink_dac1_byte13, link_uplink_dac1_byte14, link_uplink_dac1_byte15, link_uplink_dac1_byte16, link_uplink_dac1_byte17, link_uplink_dac1_byte18, link_uplink_dac1_byte19)";

						stmt.executeUpdate(query);
						System.out.println("The data is inserted into the link2 table");
					}        

					catch(Exception e)
					{
						e.printStackTrace();
						stmt = null;
					}

					finally {
						try {
							if (connection != null) {
								connection.close();
							}
							if (stmt != null) {
								stmt.close();
							}
							// if (query != null) {
							//     query.close();
							//  }

						} catch (SQLException ex) {
							Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
							lgr.log(Level.WARNING, ex.getMessage(), ex);
						}
					}
				}
			} 
			catch (ClassNotFoundException e) {
				System.out.println("Where is your MySQL JDBC Driver?");
				e.printStackTrace();
				//	return;
			}

		}
		catch (IOException e) {  
			e.printStackTrace();  
		}

		return(map7);
	}

	public static boolean DSP_IIdeleteSysinfoDir(File file) 
			throws IOException {

		if (file.isDirectory()) {
			String[] children = file.list();
			for (int i=0; i<children.length; i++) {
				boolean success = DSP_IIdeleteSysinfoDir(new File(file, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// The directory is now empty so delete it
		return file.delete();

	} 

	public static boolean DSP_IIdeleteTgzDir(File file) 
			throws IOException {

		if (file.isDirectory()) {
			String[] children = file.list();
			for (int i=0; i<children.length; i++) {
				boolean success = DSP_IIdeleteTgzDir(new File(file, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// The directory is now empty so delete it
		return file.delete();

	} 

	public static void DSP_IIupdateTables() {
		Statement st3 = null;
		Statement st4 = null;

		try {
			Properties prop1 = new Properties();
			InputStream is;
			try{
				is = new FileInputStream("C:/Labview/Properties/DSP_II.properties");

				prop1.load(is);
				String url= prop1.getProperty("url");
				String un = prop1.getProperty("username");
				String pass = prop1.getProperty("password");
				is.close();
				Class.forName("com.mysql.jdbc.Driver");

				System.out.println("\nMySQL JDBC Driver Registered!");
				Connection connection = null;

				//Update tbl_link_1 table
				try {
					connection = DriverManager.getConnection(url, un, pass);                 	   

					st3 = connection.createStatement();
					String query31 = "UPDATE tbl_link_1 INNER JOIN tbl_identity ON tbl_link_1.ID = tbl_identity.ID SET tbl_link_1.Serial_no = tbl_identity.identity_serial_number";
					//"update tbl_link_1 set Serial_no = (select identity_serial_number from tbl_identity) where Serial_no is NULL";
					st3.executeUpdate(query31);

					st4 = connection.createStatement();
					String query41 = "UPDATE tbl_link_1 INNER JOIN tbl_identity ON tbl_link_1.ID = tbl_identity.ID SET tbl_link_1.Model_no = tbl_identity.identity_model_number";
					//"update tbl_link_1 set Model_no = (select identity_model_number from tbl_identity) where Model_no is NULL";
					st4.executeUpdate(query41);

					System.out.println("Table is updated");		
				}
				catch (Exception e) {  
					e.printStackTrace();  
				}
				finally {
					try {
						if (connection != null) {
							connection.close();
						}
						if (st3 != null) {
							st3.close();
						}
						if (st4 != null) {
							st4.close();
						}
						// if (query != null) {
						//     query.close();
						//  }

					}catch (SQLException ex) {
						Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
						lgr.log(Level.WARNING, ex.getMessage(), ex);
					}
				}

				//Update tbl_link_2 table
				try {
					connection = DriverManager.getConnection(url, un, pass);                 	   

					st3 = connection.createStatement();
					String query31 = "UPDATE tbl_link_2 INNER JOIN tbl_identity ON tbl_link_2.ID = tbl_identity.ID SET tbl_link_2.Serial_no = tbl_identity.identity_serial_number";
					//"update tbl_link_2 set Serial_no = (select identity_serial_number from tbl_identity) where Serial_no is NULL";
					st3.executeUpdate(query31);

					st4 = connection.createStatement();
					String query41 = "UPDATE tbl_link_2 INNER JOIN tbl_identity ON tbl_link_2.ID = tbl_identity.ID SET tbl_link_2.Model_no = tbl_identity.identity_model_number";
					//"update tbl_link_2 set Model_no = (select identity_model_number from tbl_identity) where Model_no is NULL";
					st4.executeUpdate(query41);

					System.out.println("Table is updated");		
				}
				catch (Exception e) {  
					e.printStackTrace();  
				}
				finally {
					try {
						if (connection != null) {
							connection.close();
						}
						if (st3 != null) {
							st3.close();
						}
						if (st4 != null) {
							st4.close();
						}
						// if (query != null) {
						//     query.close();
						//  }

					}catch (SQLException ex) {
						Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
						lgr.log(Level.WARNING, ex.getMessage(), ex);
					}
				}
				//Update tbl_caldata_1 table
				try {
					connection = DriverManager.getConnection(url, un, pass);                 	   

					st3 = connection.createStatement();
					String query31 = "UPDATE tbl_caldata_1 INNER JOIN tbl_identity ON tbl_caldata_1.ID = tbl_identity.ID SET tbl_caldata_1.Serial_no = tbl_identity.identity_serial_number";
					//"update tbl_caldata_1 set Serial_no = (select identity_serial_number from tbl_identity) where Serial_no is NULL";
					st3.executeUpdate(query31);

					st4 = connection.createStatement();
					String query41 = "UPDATE tbl_caldata_1 INNER JOIN tbl_identity ON tbl_caldata_1.ID = tbl_identity.ID SET tbl_caldata_1.Model_no = tbl_identity.identity_model_number";
					//"update tbl_caldata_1 set Model_no = (select identity_model_number from tbl_identity) where Model_no is NULL";
					st4.executeUpdate(query41);

					System.out.println("Table is updated");		
				}
				catch (Exception e) {  
					e.printStackTrace();  
				}

				finally {
					try {
						if (connection != null) {
							connection.close();
						}
						if (st3 != null) {
							st3.close();
						}
						if (st4 != null) {
							st4.close();
						}
						// if (query != null) {
						//     query.close();
						//  }

					}catch (SQLException ex) {
						Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
						lgr.log(Level.WARNING, ex.getMessage(), ex);
					}
				}
				//Update tbl_caldata_2 table
				try {
					connection = DriverManager.getConnection(url, un, pass);                 	   

					st3 = connection.createStatement();
					String query31 = "UPDATE tbl_caldata_2 INNER JOIN tbl_identity ON tbl_caldata_2.ID = tbl_identity.ID SET tbl_caldata_2.Serial_no = tbl_identity.identity_serial_number";
					//"update tbl_caldata_2 set Serial_no = (select identity_serial_number from tbl_identity) where Serial_no is NULL";
					st3.executeUpdate(query31);

					st4 = connection.createStatement();
					String query41 = "UPDATE tbl_caldata_2 INNER JOIN tbl_identity ON tbl_caldata_2.ID = tbl_identity.ID SET tbl_caldata_2.Model_no = tbl_identity.identity_model_number";
					//"update tbl_caldata_2 set Model_no = (select identity_model_number from tbl_identity) where Model_no is NULL";
					st4.executeUpdate(query41);

					System.out.println("Table is updated");		
				}
				catch (Exception e) {  
					e.printStackTrace();  
				}
				finally {
					try {
						if (connection != null) {
							connection.close();
						}
						if (st3 != null) {
							st3.close();
						}
						if (st4 != null) {
							st4.close();
						}
						// if (query != null) {
						//     query.close();
						//  }

					}catch (SQLException ex) {
						Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
						lgr.log(Level.WARNING, ex.getMessage(), ex);
					}
				}

				//Update tbl_engr table
				try {
					connection = DriverManager.getConnection(url, un, pass);                 	   

					st3 = connection.createStatement();
					String query31 = "UPDATE tbl_engr INNER JOIN tbl_identity ON tbl_engr.ID = tbl_identity.ID SET tbl_engr.Serial_no = tbl_identity.identity_serial_number"; 
					//"update tbl_engr set Serial_no = (select identity_serial_number from tbl_identity) where Serial_no = NULL";
					st3.executeUpdate(query31);

					st4 = connection.createStatement();
					String query41 = "UPDATE tbl_engr INNER JOIN tbl_identity ON tbl_engr.ID = tbl_identity.ID SET tbl_engr.Model_no = tbl_identity.identity_model_number";
					//"update tbl_engr set Model_no = (select identity_model_number from tbl_identity) where Model_no = NULL";
					st4.executeUpdate(query41);

					System.out.println("Table is updated");		
				}
				catch (Exception e) {  
					e.printStackTrace();  
				}
				finally {
					try {
						if (connection != null) {
							connection.close();
						}
						if (st3 != null) {
							st3.close();
						}
						if (st4 != null) {
							st4.close();
						}


					}catch (SQLException ex) {
						Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
						lgr.log(Level.WARNING, ex.getMessage(), ex);
					}
				}
			}
			catch (ClassNotFoundException e) {
				System.out.println("Where is your MySQL JDBC Driver?");
				e.printStackTrace();
				return;
			}

		}
		catch (Exception e) {  
			e.printStackTrace();  
		}

	}

	public static void DSP_IIMySQLToCSV(){
		File f1 = new File("C:/Labview/CSV Files/DSP_II_config_1Band.csv");
		if(f1.exists())
		{
			try {
				File file = new File("C:/Labview/CSV Files/config_temp.csv");
				f1.renameTo(file);
				f1.delete();
				if(f1.delete()) {
					System.out.println("File is deleted");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		File f2 = new File("C:/Labview/CSV Files/DSP_II_config_2Band.csv");
		if(f2.exists())
		{
			try {
				File file = new File("C:/Labview/CSV Files/config_temp.csv");
				f2.renameTo(file);
				f2.delete();
				if(f2.delete()) {
					System.out.println("File is deleted");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		//config.properties file: contains the properties(url+database_name, username, password) to make JDBC connection with MySQL
		try {
			Properties prop1 = new Properties();
			InputStream is;
			try{
				is = new FileInputStream("C:/Labview/Properties/DSP_II.properties");
				prop1.load(is);
				String url= prop1.getProperty("url");
				String un = prop1.getProperty("username");
				String pass = prop1.getProperty("password");
				is.close();
				Class.forName("com.mysql.jdbc.Driver");


				System.out.println("\nMySQL JDBC Driver Registered!");
				Connection connection = null;

				Statement stmt = null;

				//Create the .csv file for the config_1 (Band 1) data (Properties and their values). .CSV file will contain the property names as headers and the all values
				try	{
					connection = DriverManager.getConnection(url, un, pass);                 	   
					stmt = connection.createStatement();

					//query1 without pilot properties
					String query1 = "select 'identity_serial_number', 'identity_model_number', 'identity_item_number', 'identity_software_version', 'identity_date_built', 'link_config_board_rev', 'link_config_board_type', 'link_uplink_attenuator_one', 'link_dnlink_attenuator_one', 'link_uplink_attenuator_two', 'link_dnlink_attenuator_two', 'link_uplink_attenuator_three', 'link_dnlink_attenuator_three', 'link_dnlink_dac_byte1', 'link_dnlink_dac_byte2', 'link_dnlink_dac_byte3', 'link_dnlink_dac_byte4', 'link_dnlink_dac_byte5', 'link_dnlink_dac_byte6', 'link_dnlink_dac_byte7', 'link_dnlink_dac_byte8', 'link_dnlink_dac_byte9', 'link_dnlink_dac_byte10', 'link_dnlink_dac_byte11', 'link_dnlink_dac_byte12', 'link_dnlink_dac_byte13', 'link_uplink_dac_byte1', 'link_uplink_dac_byte2', 'link_uplink_dac_byte3', 'link_uplink_dac_byte4', 'link_uplink_dac_byte5', 'link_uplink_dac_byte6', 'link_uplink_dac_byte7', 'link_uplink_dac_byte8', 'link_uplink_dac_byte9', 'link_uplink_dac_byte10', 'link_uplink_dac_byte11', 'link_uplink_dac_byte12', 'link_uplink_dac_byte13', 'link_dnlink_dac1_byte1', 'link_dnlink_dac1_byte2', 'link_dnlink_dac1_byte3', 'link_dnlink_dac1_byte4', 'link_dnlink_dac1_byte5', 'link_dnlink_dac1_byte6', 'link_dnlink_dac1_byte7', 'link_dnlink_dac1_byte8', 'link_dnlink_dac1_byte9', 'link_dnlink_dac1_byte10', 'link_dnlink_dac1_byte11', 'link_dnlink_dac1_byte12', 'link_dnlink_dac1_byte13', 'link_dnlink_dac1_byte14', 'link_dnlink_dac1_byte15', 'link_dnlink_dac1_byte16', 'link_dnlink_dac1_byte17', 'link_dnlink_dac1_byte18', 'link_dnlink_dac1_byte19', 'link_uplink_dac1_byte1', 'link_uplink_dac1_byte2', 'link_uplink_dac1_byte3', 'link_uplink_dac1_byte4', 'link_uplink_dac1_byte5', 'link_uplink_dac1_byte6', 'link_uplink_dac1_byte7', 'link_uplink_dac1_byte8', 'link_uplink_dac1_byte9', 'link_uplink_dac1_byte10', 'link_uplink_dac1_byte11', 'link_uplink_dac1_byte12', 'link_uplink_dac1_byte13', 'link_uplink_dac1_byte14', 'link_uplink_dac1_byte15', 'link_uplink_dac1_byte16', 'link_uplink_dac1_byte17', 'link_uplink_dac1_byte18', 'link_uplink_dac1_byte19', 'calibration_dnlink_output_adc_goal', 'calibration_dnlink_output_power_goal', 'calibration_dnlink_output_adc_maximum', 'calibration_dnlink_output_power_maximum', 'calibration_uplink_output_adc_goal', 'calibration_uplink_output_power_goal', 'calibration_uplink_output_adc_maximum', 'calibration_uplink_output_power_maximum', 'engr_acela_cell_out_revb_dn_k', 'engr_acela_cell_out_revb_dn_n', 'engr_acela_cell_out_revb_up_k', 'engr_acela_cell_out_revb_up_n', 'engr_acela_lte_out_revb_dn_k', 'engr_acela_lte_out_revb_dn_n', 'engr_acela_lte_out_revb_up_k', 'engr_acela_lte_out_revb_up_n', 'engr_acela_pcs_out_revb_dn_k', 'engr_acela_pcs_out_revb_dn_n', 'engr_acela_pcs_out_revb_up_k', 'engr_acela_pcs_out_revb_up_n', 'engr_aws_in_revb_dn_k', 'engr_aws_in_revb_dn_n', 'engr_aws_in_revb_up_k', 'engr_aws_in_revb_up_n', 'engr_aws_out_revb_dn_k', 'engr_aws_out_revb_dn_n', 'engr_aws_out_revb_up_k', 'engr_aws_out_revb_up_n', 'engr_cell_in_revb_dn_k', 'engr_cell_in_revb_dn_n', 'engr_cell_in_revb_up_k', 'engr_cell_in_revb_up_n', 'engr_cell_out_revb_dn_k', 'engr_cell_out_revb_dn_n', 'engr_cell_out_revb_up_k', 'engr_cell_out_revb_up_n', 'engr_das_24v_dnlink_coeff_acela', 'engr_das_24v_dnlink_coeff_acela_cell', 'engr_das_24v_dnlink_coeff_acela_lte', 'engr_das_24v_uplink_coeff_acela', 'engr_das_24v_uplink_coeff_acela_cell', 'engr_das_24v_uplink_coeff_acela_lte', 'engr_das_24v_dnlink_coeff_acela_cell1', 'engr_das_24v_dnlink_coeff_acela_lte1', 'engr_das_24v_dnlink_coeff_acela_pcs', 'engr_das_24v_dnlink_coeff_acela_aws', 'engr_das_24v_uplink_coeff_acela_cell1', 'engr_das_24v_uplink_coeff_acela_lte1', 'engr_das_24v_uplink_coeff_acela_pcs', 'engr_das_24v_uplink_coeff_acela_aws', 'engr_lte_in_revb_dn_k', 'engr_lte_in_revb_dn_n', 'engr_lte_in_revb_up_', 'engr_lte_in_revb_up_n', 'engr_lte_out_revb_dn_k', 'engr_lte_out_revb_dn_n', 'engr_lte_out_revb_up_k', 'engr_lte_out_revb_up_n', 'engr_lte2_in_revb_dn_k', 'engr_lte2_in_revb_dn_n', 'engr_lte2_in_revb_up_k', 'engr_lte2_in_revb_up_n', 'engr_lte2_out_revb_dn_k', 'engr_lte2_out_revb_dn_n', 'engr_lte2_out_revb_up_k', 'engr_lte2_out_revb_up_n', 'engr_pcs_in_revb_dn_k', 'engr_pcs_in_revb_dn_n', 'engr_pcs_in_revb_up_k', 'engr_pcs_in_revb_up_n', 'engr_pcs_out_revb_dn_k', 'engr_pcs_out_revb_dn_n', 'engr_pcs_out_revb_up_k', 'engr_pcs_out_revb_up_n', 'engr_pilot_aws_out_revb_dn_k', 'engr_pilot_aws_out_revb_dn_n', 'engr_pilot_aws_out_revb_up_k', 'engr_pilot_aws_out_revb_up_n', 'engr_pilot_cell_out_revb_dn_', 'engr_pilot_cell_out_revb_dn_n', 'engr_pilot_cell_out_revb_up_k', 'engr_pilot_cell_out_revb_up_n', 'engr_pilot_pcs_out_revb_dn_k', 'engr_pilot_pcs_out_revb_dn_n', 'engr_pilot_pcs_out_revb_up_k', 'engr_pilot_pcs_out_revb_up_n', 'engr_scan_rcvr_cal_1', 'engr_scan_rcvr_cal_2', 'engr_scan_rcvr_cal2_0', 'engr_scan_rcvr_cal2_1', 'engr_scan_rcvr_cal2_2', 'engr_scan_rcvr_cell_cal_0', 'engr_scan_rcvr_cell_cal_1', 'engr_scan_rcvr_cell_cal_2', 'engr_scan_rcvr_lte_cal_0', 'engr_scan_rcvr_lte_cal_1', 'engr_scan_rcvr_lte_cal_2', 'engr_smr_in_revb_dn_k', 'engr_smr_in_revb_dn_n', 'engr_smr_in_revb_up_k', 'engr_smr_in_revb_up_n', 'engr_smr_out_revb_dn_k', 'engr_smr_out_revb_dn_n', 'engr_smr_out_revb_up_k', 'engr_smr_out_revb_up_n', 'engr_temp_gain_dn_k', 'engr_temp_gain_dn_n', 'engr_temp_gain_up_k', 'engr_temp_gain_up_n', 'engr_temp_gain1_dn_k', 'engr_temp_gain1_dn_n', 'engr_temp_gain1_up_k', 'engr_temp_gain1_up_n' union all select tbl_identity.identity_serial_number, tbl_identity.identity_model_number, tbl_identity.identity_item_number, tbl_identity.identity_software_version, tbl_identity.identity_date_built, tbl_link_1.link_config_board_rev, tbl_link_1.link_config_board_type, tbl_link_1.link_uplink_attenuator_one, tbl_link_1.link_dnlink_attenuator_one, tbl_link_1.link_uplink_attenuator_two, tbl_link_1.link_dnlink_attenuator_two, tbl_link_1.link_uplink_attenuator_three, tbl_link_1.link_dnlink_attenuator_three, tbl_link_1.link_dnlink_dac_byte1, tbl_link_1.link_dnlink_dac_byte2, tbl_link_1.link_dnlink_dac_byte3, tbl_link_1.link_dnlink_dac_byte4, tbl_link_1.link_dnlink_dac_byte5, tbl_link_1.link_dnlink_dac_byte6, tbl_link_1.link_dnlink_dac_byte7, tbl_link_1.link_dnlink_dac_byte8, tbl_link_1.link_dnlink_dac_byte9, tbl_link_1.link_dnlink_dac_byte10, tbl_link_1.link_dnlink_dac_byte11, tbl_link_1.link_dnlink_dac_byte12, tbl_link_1.link_dnlink_dac_byte13, tbl_link_1.link_uplink_dac_byte1, tbl_link_1.link_uplink_dac_byte2, tbl_link_1.link_uplink_dac_byte3, tbl_link_1.link_uplink_dac_byte4, tbl_link_1.link_uplink_dac_byte5, tbl_link_1.link_uplink_dac_byte6, tbl_link_1.link_uplink_dac_byte7, tbl_link_1.link_uplink_dac_byte8, tbl_link_1.link_uplink_dac_byte9, tbl_link_1.link_uplink_dac_byte10, tbl_link_1.link_uplink_dac_byte11, tbl_link_1.link_uplink_dac_byte12, tbl_link_1.link_uplink_dac_byte13, tbl_link_1.link_dnlink_dac1_byte1, tbl_link_1.link_dnlink_dac1_byte2, tbl_link_1.link_dnlink_dac1_byte3, tbl_link_1.link_dnlink_dac1_byte4, tbl_link_1.link_dnlink_dac1_byte5, tbl_link_1.link_dnlink_dac1_byte6, tbl_link_1.link_dnlink_dac1_byte7, tbl_link_1.link_dnlink_dac1_byte8, tbl_link_1.link_dnlink_dac1_byte9, tbl_link_1.link_dnlink_dac1_byte10, tbl_link_1.link_dnlink_dac1_byte11, tbl_link_1.link_dnlink_dac1_byte12, tbl_link_1.link_dnlink_dac1_byte13, tbl_link_1.link_dnlink_dac1_byte14, tbl_link_1.link_dnlink_dac1_byte15, tbl_link_1.link_dnlink_dac1_byte16, tbl_link_1.link_dnlink_dac1_byte17, tbl_link_1.link_dnlink_dac1_byte18, tbl_link_1.link_dnlink_dac1_byte19, tbl_link_1.link_uplink_dac1_byte1, tbl_link_1.link_uplink_dac1_byte2, tbl_link_1.link_uplink_dac1_byte3, tbl_link_1.link_uplink_dac1_byte4, tbl_link_1.link_uplink_dac1_byte5, tbl_link_1.link_uplink_dac1_byte6, tbl_link_1.link_uplink_dac1_byte7, tbl_link_1.link_uplink_dac1_byte8, tbl_link_1.link_uplink_dac1_byte9, tbl_link_1.link_uplink_dac1_byte10, tbl_link_1.link_uplink_dac1_byte11, tbl_link_1.link_uplink_dac1_byte12, tbl_link_1.link_uplink_dac1_byte13, tbl_link_1.link_uplink_dac1_byte14, tbl_link_1.link_uplink_dac1_byte15, tbl_link_1.link_uplink_dac1_byte16, tbl_link_1.link_uplink_dac1_byte17, tbl_link_1.link_uplink_dac1_byte18, tbl_link_1.link_uplink_dac1_byte19, tbl_caldata_1.calibration_dnlink_output_adc_goal, tbl_caldata_1.calibration_dnlink_output_power_goal, tbl_caldata_1.calibration_dnlink_output_adc_maximum, tbl_caldata_1.calibration_dnlink_output_power_maximum, tbl_caldata_1.calibration_uplink_output_adc_goal, tbl_caldata_1.calibration_uplink_output_power_goal, tbl_caldata_1.calibration_uplink_output_adc_maximum, tbl_caldata_1.calibration_uplink_output_power_maximum, tbl_engr.engr_acela_cell_out_revb_dn_k, tbl_engr.engr_acela_cell_out_revb_dn_n, tbl_engr.engr_acela_cell_out_revb_up_k, tbl_engr.engr_acela_cell_out_revb_up_n, tbl_engr.engr_acela_lte_out_revb_dn_k, tbl_engr.engr_acela_lte_out_revb_dn_n, tbl_engr.engr_acela_lte_out_revb_up_k, tbl_engr.engr_acela_lte_out_revb_up_n, tbl_engr.engr_acela_pcs_out_revb_dn_k, tbl_engr.engr_acela_pcs_out_revb_dn_n, tbl_engr.engr_acela_pcs_out_revb_up_k, tbl_engr.engr_acela_pcs_out_revb_up_n, tbl_engr.engr_aws_in_revb_dn_k, tbl_engr.engr_aws_in_revb_dn_n, tbl_engr.engr_aws_in_revb_up_k, tbl_engr.engr_aws_in_revb_up_n, tbl_engr.engr_aws_out_revb_dn_k, tbl_engr.engr_aws_out_revb_dn_n, tbl_engr.engr_aws_out_revb_up_k, tbl_engr.engr_aws_out_revb_up_n, tbl_engr.engr_cell_in_revb_dn_k, tbl_engr.engr_cell_in_revb_dn_n, tbl_engr.engr_cell_in_revb_up_k, tbl_engr.engr_cell_in_revb_up_n, tbl_engr.engr_cell_out_revb_dn_k, tbl_engr.engr_cell_out_revb_dn_n, tbl_engr.engr_cell_out_revb_up_k, tbl_engr.engr_cell_out_revb_up_n, tbl_engr.engr_das_24v_dnlink_coeff_acela, tbl_engr.engr_das_24v_dnlink_coeff_acela_cell, tbl_engr.engr_das_24v_dnlink_coeff_acela_lte, tbl_engr.engr_das_24v_uplink_coeff_acela, tbl_engr.engr_das_24v_uplink_coeff_acela_cell, tbl_engr.engr_das_24v_uplink_coeff_acela_lte, tbl_engr.engr_das_24v_dnlink_coeff_acela_cell1, tbl_engr.engr_das_24v_dnlink_coeff_acela_lte1, tbl_engr.engr_das_24v_dnlink_coeff_acela_pcs, tbl_engr.engr_das_24v_dnlink_coeff_acela_aws, tbl_engr.engr_das_24v_uplink_coeff_acela_cell1, tbl_engr.engr_das_24v_uplink_coeff_acela_lte1, tbl_engr.engr_das_24v_uplink_coeff_acela_pcs, tbl_engr.engr_das_24v_uplink_coeff_acela_aws, tbl_engr.engr_lte_in_revb_dn_k, tbl_engr.engr_lte_in_revb_dn_n, tbl_engr.engr_lte_in_revb_up_, tbl_engr.engr_lte_in_revb_up_n, tbl_engr.engr_lte_out_revb_dn_k, tbl_engr.engr_lte_out_revb_dn_n, tbl_engr.engr_lte_out_revb_up_k, tbl_engr.engr_lte_out_revb_up_n, tbl_engr.engr_lte2_in_revb_dn_k, tbl_engr.engr_lte2_in_revb_dn_n, tbl_engr.engr_lte2_in_revb_up_k, tbl_engr.engr_lte2_in_revb_up_n, tbl_engr.engr_lte2_out_revb_dn_k, tbl_engr.engr_lte2_out_revb_dn_n, tbl_engr.engr_lte2_out_revb_up_k, tbl_engr.engr_lte2_out_revb_up_n, tbl_engr.engr_pcs_in_revb_dn_k, tbl_engr.engr_pcs_in_revb_dn_n, tbl_engr.engr_pcs_in_revb_up_k, tbl_engr.engr_pcs_in_revb_up_n, tbl_engr.engr_pcs_out_revb_dn_k, tbl_engr.engr_pcs_out_revb_dn_n, tbl_engr.engr_pcs_out_revb_up_k, tbl_engr.engr_pcs_out_revb_up_n, tbl_engr.engr_pilot_aws_out_revb_dn_k, tbl_engr.engr_pilot_aws_out_revb_dn_n, tbl_engr.engr_pilot_aws_out_revb_up_k, tbl_engr.engr_pilot_aws_out_revb_up_n, tbl_engr.engr_pilot_cell_out_revb_dn_, tbl_engr.engr_pilot_cell_out_revb_dn_n, tbl_engr.engr_pilot_cell_out_revb_up_k, tbl_engr.engr_pilot_cell_out_revb_up_n, tbl_engr.engr_pilot_pcs_out_revb_dn_k, tbl_engr.engr_pilot_pcs_out_revb_dn_n, tbl_engr.engr_pilot_pcs_out_revb_up_k, tbl_engr.engr_pilot_pcs_out_revb_up_n, tbl_engr.engr_scan_rcvr_cal_1, tbl_engr.engr_scan_rcvr_cal_2, tbl_engr.engr_scan_rcvr_cal2_0, tbl_engr.engr_scan_rcvr_cal2_1, tbl_engr.engr_scan_rcvr_cal2_2, tbl_engr.engr_scan_rcvr_cell_cal_0, tbl_engr.engr_scan_rcvr_cell_cal_1, tbl_engr.engr_scan_rcvr_cell_cal_2, tbl_engr.engr_scan_rcvr_lte_cal_0, tbl_engr.engr_scan_rcvr_lte_cal_1, tbl_engr.engr_scan_rcvr_lte_cal_2, tbl_engr.engr_smr_in_revb_dn_k, tbl_engr.engr_smr_in_revb_dn_n, tbl_engr.engr_smr_in_revb_up_k, tbl_engr.engr_smr_in_revb_up_n, tbl_engr.engr_smr_out_revb_dn_k, tbl_engr.engr_smr_out_revb_dn_n, tbl_engr.engr_smr_out_revb_up_k, tbl_engr.engr_smr_out_revb_up_n, tbl_engr.engr_temp_gain_dn_k, tbl_engr.engr_temp_gain_dn_n, tbl_engr.engr_temp_gain_up_k, tbl_engr.engr_temp_gain_up_n, tbl_engr.engr_temp_gain1_dn_k, tbl_engr.engr_temp_gain1_dn_n, tbl_engr.engr_temp_gain1_up_k, tbl_engr.engr_temp_gain1_up_n from tbl_identity inner join tbl_link_1 on tbl_identity.ID = tbl_link_1.ID inner join tbl_caldata_1 on tbl_link_1.ID = tbl_caldata_1.ID inner join tbl_engr on tbl_caldata_1.ID = tbl_engr.ID into outfile 'C:/Labview/CSV Files/DSP_II_config_1Band.csv' fields terminated by ',' enclosed by '\"' lines terminated by '\n'";

					//query1 with pilot properties
					//							query1 = "select 'identity_serial_number', 'identity_model_number', 'identity_item_number', 'identity_software_version', 'identity_date_built', 'link_config_board_rev', 'link_config_board_type', 'link_uplink_attenuator_one', 'link_dnlink_attenuator_one', 'link_uplink_attenuator_two', 'link_dnlink_attenuator_two', 'link_uplink_attenuator_three', 'link_dnlink_attenuator_three', 'link_dnlink_dac_byte1', 'link_dnlink_dac_byte2', 'link_dnlink_dac_byte3', 'link_dnlink_dac_byte4', 'link_dnlink_dac_byte5', 'link_dnlink_dac_byte6', 'link_dnlink_dac_byte7', 'link_dnlink_dac_byte8', 'link_dnlink_dac_byte9', 'link_dnlink_dac_byte10', 'link_dnlink_dac_byte11', 'link_dnlink_dac_byte12', 'link_dnlink_dac_byte13', 'link_uplink_dac_byte1', 'link_uplink_dac_byte2', 'link_uplink_dac_byte3', 'link_uplink_dac_byte4', 'link_uplink_dac_byte5', 'link_uplink_dac_byte6', 'link_uplink_dac_byte7', 'link_uplink_dac_byte8', 'link_uplink_dac_byte9', 'link_uplink_dac_byte10', 'link_uplink_dac_byte11', 'link_uplink_dac_byte12', 'link_uplink_dac_byte13', 'link_dnlink_dac1_byte1', 'link_dnlink_dac1_byte2', 'link_dnlink_dac1_byte3', 'link_dnlink_dac1_byte4', 'link_dnlink_dac1_byte5', 'link_dnlink_dac1_byte6', 'link_dnlink_dac1_byte7', 'link_dnlink_dac1_byte8', 'link_dnlink_dac1_byte9', 'link_dnlink_dac1_byte10', 'link_dnlink_dac1_byte11', 'link_dnlink_dac1_byte12', 'link_dnlink_dac1_byte13', 'link_dnlink_dac1_byte14', 'link_dnlink_dac1_byte15', 'link_dnlink_dac1_byte16', 'link_dnlink_dac1_byte17', 'link_dnlink_dac1_byte18', 'link_dnlink_dac1_byte19', 'link_uplink_dac1_byte1', 'link_uplink_dac1_byte2', 'link_uplink_dac1_byte3', 'link_uplink_dac1_byte4', 'link_uplink_dac1_byte5', 'link_uplink_dac1_byte6', 'link_uplink_dac1_byte7', 'link_uplink_dac1_byte8', 'link_uplink_dac1_byte9', 'link_uplink_dac1_byte10', 'link_uplink_dac1_byte11', 'link_uplink_dac1_byte12', 'link_uplink_dac1_byte13', 'link_uplink_dac1_byte14', 'link_uplink_dac1_byte15', 'link_uplink_dac1_byte16', 'link_uplink_dac1_byte17', 'link_uplink_dac1_byte18', 'link_uplink_dac1_byte19', 'calibration_dnlink_output_adc_goal', 'calibration_dnlink_output_power_goal', 'calibration_dnlink_output_adc_maximum', 'calibration_dnlink_output_power_maximum', 'calibration_uplink_output_adc_goal', 'calibration_uplink_output_power_goal', 'calibration_uplink_output_adc_maximum', 'calibration_uplink_output_power_maximum', 'engr_acela_cell_out_revb_dn_k', 'engr_acela_cell_out_revb_dn_n', 'engr_acela_cell_out_revb_up_k', 'engr_acela_cell_out_revb_up_n', 'engr_acela_lte_out_revb_dn_k', 'engr_acela_lte_out_revb_dn_n', 'engr_acela_lte_out_revb_up_k', 'engr_acela_lte_out_revb_up_n', 'engr_acela_pcs_out_revb_dn_k', 'engr_acela_pcs_out_revb_dn_n', 'engr_acela_pcs_out_revb_up_k', 'engr_acela_pcs_out_revb_up_n', 'engr_aws_in_revb_dn_k', 'engr_aws_in_revb_dn_n', 'engr_aws_in_revb_up_k', 'engr_aws_in_revb_up_n', 'engr_aws_out_revb_dn_k', 'engr_aws_out_revb_dn_n', 'engr_aws_out_revb_up_k', 'engr_aws_out_revb_up_n', 'engr_cell_in_revb_dn_k', 'engr_cell_in_revb_dn_n', 'engr_cell_in_revb_up_k', 'engr_cell_in_revb_up_n', 'engr_cell_out_revb_dn_k', 'engr_cell_out_revb_dn_n', 'engr_cell_out_revb_up_k', 'engr_cell_out_revb_up_n', 'engr_das_24v_dnlink_coeff_acela', 'engr_das_24v_dnlink_coeff_acela_cell', 'engr_das_24v_dnlink_coeff_acela_lte', 'engr_das_24v_uplink_coeff_acela', 'engr_das_24v_uplink_coeff_acela_cell', 'engr_das_24v_uplink_coeff_acela_lte', 'engr_das_24v_dnlink_coeff_acela_cell1', 'engr_das_24v_dnlink_coeff_acela_lte1', 'engr_das_24v_dnlink_coeff_acela_pcs', 'engr_das_24v_dnlink_coeff_acela_aws', 'engr_das_24v_uplink_coeff_acela_cell1', 'engr_das_24v_uplink_coeff_acela_lte1', 'engr_das_24v_uplink_coeff_acela_pcs', 'engr_das_24v_uplink_coeff_acela_aws', 'engr_lte_in_revb_dn_k', 'engr_lte_in_revb_dn_n', 'engr_lte_in_revb_up_', 'engr_lte_in_revb_up_n', 'engr_lte_out_revb_dn_k', 'engr_lte_out_revb_dn_n', 'engr_lte_out_revb_up_k', 'engr_lte_out_revb_up_n', 'engr_lte2_in_revb_dn_k', 'engr_lte2_in_revb_dn_n', 'engr_lte2_in_revb_up_k', 'engr_lte2_in_revb_up_n', 'engr_lte2_out_revb_dn_k', 'engr_lte2_out_revb_dn_n', 'engr_lte2_out_revb_up_k', 'engr_lte2_out_revb_up_n', 'engr_pcs_in_revb_dn_k', 'engr_pcs_in_revb_dn_n', 'engr_pcs_in_revb_up_k', 'engr_pcs_in_revb_up_n', 'engr_pcs_out_revb_dn_k', 'engr_pcs_out_revb_dn_n', 'engr_pcs_out_revb_up_k', 'engr_pcs_out_revb_up_n', 'engr_pilot_aws_out_revb_dn_k', 'engr_pilot_aws_out_revb_dn_n', 'engr_pilot_aws_out_revb_up_k', 'engr_pilot_aws_out_revb_up_n', 'engr_pilot_cell_out_revb_dn_', 'engr_pilot_cell_out_revb_dn_n', 'engr_pilot_cell_out_revb_up_k', 'engr_pilot_cell_out_revb_up_n', 'engr_pilot_pcs_out_revb_dn_k', 'engr_pilot_pcs_out_revb_dn_n', 'engr_pilot_pcs_out_revb_up_k', 'engr_pilot_pcs_out_revb_up_n', 'engr_scan_rcvr_cal_1', 'engr_scan_rcvr_cal_2', 'engr_scan_rcvr_cal2_0', 'engr_scan_rcvr_cal2_1', 'engr_scan_rcvr_cal2_2', 'engr_scan_rcvr_cell_cal_0', 'engr_scan_rcvr_cell_cal_1', 'engr_scan_rcvr_cell_cal_2', 'engr_scan_rcvr_lte_cal_0', 'engr_scan_rcvr_lte_cal_1', 'engr_scan_rcvr_lte_cal_2', 'engr_smr_in_revb_dn_k', 'engr_smr_in_revb_dn_n', 'engr_smr_in_revb_up_k', 'engr_smr_in_revb_up_n', 'engr_smr_out_revb_dn_k', 'engr_smr_out_revb_dn_n', 'engr_smr_out_revb_up_k', 'engr_smr_out_revb_up_n', 'engr_temp_gain_dn_k', 'engr_temp_gain_dn_n', 'engr_temp_gain_up_k', 'engr_temp_gain_up_n', 'engr_temp_gain1_dn_k', 'engr_temp_gain1_dn_n', 'engr_temp_gain1_up_k', 'engr_temp_gain1_up_n', 'pilot_a3_calvalue_cell', 'pilot_a3_calvalue_pcs', 'pilot_a3_calvalue_aws' union all select tbl_identity.identity_serial_number, tbl_identity.identity_model_number, tbl_identity.identity_item_number, tbl_identity.identity_software_version, tbl_identity.identity_date_built, tbl_link_1.link_config_board_rev, tbl_link_1.link_config_board_type, tbl_link_1.link_uplink_attenuator_one, tbl_link_1.link_dnlink_attenuator_one, tbl_link_1.link_uplink_attenuator_two, tbl_link_1.link_dnlink_attenuator_two, tbl_link_1.link_uplink_attenuator_three, tbl_link_1.link_dnlink_attenuator_three, tbl_link_1.link_dnlink_dac_byte1, tbl_link_1.link_dnlink_dac_byte2, tbl_link_1.link_dnlink_dac_byte3, tbl_link_1.link_dnlink_dac_byte4, tbl_link_1.link_dnlink_dac_byte5, tbl_link_1.link_dnlink_dac_byte6, tbl_link_1.link_dnlink_dac_byte7, tbl_link_1.link_dnlink_dac_byte8, tbl_link_1.link_dnlink_dac_byte9, tbl_link_1.link_dnlink_dac_byte10, tbl_link_1.link_dnlink_dac_byte11, tbl_link_1.link_dnlink_dac_byte12, tbl_link_1.link_dnlink_dac_byte13, tbl_link_1.link_uplink_dac_byte1, tbl_link_1.link_uplink_dac_byte2, tbl_link_1.link_uplink_dac_byte3, tbl_link_1.link_uplink_dac_byte4, tbl_link_1.link_uplink_dac_byte5, tbl_link_1.link_uplink_dac_byte6, tbl_link_1.link_uplink_dac_byte7, tbl_link_1.link_uplink_dac_byte8, tbl_link_1.link_uplink_dac_byte9, tbl_link_1.link_uplink_dac_byte10, tbl_link_1.link_uplink_dac_byte11, tbl_link_1.link_uplink_dac_byte12, tbl_link_1.link_uplink_dac_byte13, tbl_link_1.link_dnlink_dac1_byte1, tbl_link_1.link_dnlink_dac1_byte2, tbl_link_1.link_dnlink_dac1_byte3, tbl_link_1.link_dnlink_dac1_byte4, tbl_link_1.link_dnlink_dac1_byte5, tbl_link_1.link_dnlink_dac1_byte6, tbl_link_1.link_dnlink_dac1_byte7, tbl_link_1.link_dnlink_dac1_byte8, tbl_link_1.link_dnlink_dac1_byte9, tbl_link_1.link_dnlink_dac1_byte10, tbl_link_1.link_dnlink_dac1_byte11, tbl_link_1.link_dnlink_dac1_byte12, tbl_link_1.link_dnlink_dac1_byte13, tbl_link_1.link_dnlink_dac1_byte14, tbl_link_1.link_dnlink_dac1_byte15, tbl_link_1.link_dnlink_dac1_byte16, tbl_link_1.link_dnlink_dac1_byte17, tbl_link_1.link_dnlink_dac1_byte18, tbl_link_1.link_dnlink_dac1_byte19, tbl_link_1.link_uplink_dac1_byte1, tbl_link_1.link_uplink_dac1_byte2, tbl_link_1.link_uplink_dac1_byte3, tbl_link_1.link_uplink_dac1_byte4, tbl_link_1.link_uplink_dac1_byte5, tbl_link_1.link_uplink_dac1_byte6, tbl_link_1.link_uplink_dac1_byte7, tbl_link_1.link_uplink_dac1_byte8, tbl_link_1.link_uplink_dac1_byte9, tbl_link_1.link_uplink_dac1_byte10, tbl_link_1.link_uplink_dac1_byte11, tbl_link_1.link_uplink_dac1_byte12, tbl_link_1.link_uplink_dac1_byte13, tbl_link_1.link_uplink_dac1_byte14, tbl_link_1.link_uplink_dac1_byte15, tbl_link_1.link_uplink_dac1_byte16, tbl_link_1.link_uplink_dac1_byte17, tbl_link_1.link_uplink_dac1_byte18, tbl_link_1.link_uplink_dac1_byte19, tbl_caldata_1.calibration_dnlink_output_adc_goal, tbl_caldata_1.calibration_dnlink_output_power_goal, tbl_caldata_1.calibration_dnlink_output_adc_maximum, tbl_caldata_1.calibration_dnlink_output_power_maximum, tbl_caldata_1.calibration_uplink_output_adc_goal, tbl_caldata_1.calibration_uplink_output_power_goal, tbl_caldata_1.calibration_uplink_output_adc_maximum, tbl_caldata_1.calibration_uplink_output_power_maximum, tbl_engr.engr_acela_cell_out_revb_dn_k, tbl_engr.engr_acela_cell_out_revb_dn_n, tbl_engr.engr_acela_cell_out_revb_up_k, tbl_engr.engr_acela_cell_out_revb_up_n, tbl_engr.engr_acela_lte_out_revb_dn_k, tbl_engr.engr_acela_lte_out_revb_dn_n, tbl_engr.engr_acela_lte_out_revb_up_k, tbl_engr.engr_acela_lte_out_revb_up_n, tbl_engr.engr_acela_pcs_out_revb_dn_k, tbl_engr.engr_acela_pcs_out_revb_dn_n, tbl_engr.engr_acela_pcs_out_revb_up_k, tbl_engr.engr_acela_pcs_out_revb_up_n, tbl_engr.engr_aws_in_revb_dn_k, tbl_engr.engr_aws_in_revb_dn_n, tbl_engr.engr_aws_in_revb_up_k, tbl_engr.engr_aws_in_revb_up_n, tbl_engr.engr_aws_out_revb_dn_k, tbl_engr.engr_aws_out_revb_dn_n, tbl_engr.engr_aws_out_revb_up_k, tbl_engr.engr_aws_out_revb_up_n, tbl_engr.engr_cell_in_revb_dn_k, tbl_engr.engr_cell_in_revb_dn_n, tbl_engr.engr_cell_in_revb_up_k, tbl_engr.engr_cell_in_revb_up_n, tbl_engr.engr_cell_out_revb_dn_k, tbl_engr.engr_cell_out_revb_dn_n, tbl_engr.engr_cell_out_revb_up_k, tbl_engr.engr_cell_out_revb_up_n, tbl_engr.engr_das_24v_dnlink_coeff_acela, tbl_engr.engr_das_24v_dnlink_coeff_acela_cell, tbl_engr.engr_das_24v_dnlink_coeff_acela_lte, tbl_engr.engr_das_24v_uplink_coeff_acela, tbl_engr.engr_das_24v_uplink_coeff_acela_cell, tbl_engr.engr_das_24v_uplink_coeff_acela_lte, tbl_engr.engr_das_24v_dnlink_coeff_acela_cell1, tbl_engr.engr_das_24v_dnlink_coeff_acela_lte1, tbl_engr.engr_das_24v_dnlink_coeff_acela_pcs, tbl_engr.engr_das_24v_dnlink_coeff_acela_aws, tbl_engr.engr_das_24v_uplink_coeff_acela_cell1, tbl_engr.engr_das_24v_uplink_coeff_acela_lte1, tbl_engr.engr_das_24v_uplink_coeff_acela_pcs, tbl_engr.engr_das_24v_uplink_coeff_acela_aws, tbl_engr.engr_lte_in_revb_dn_k, tbl_engr.engr_lte_in_revb_dn_n, tbl_engr.engr_lte_in_revb_up_, tbl_engr.engr_lte_in_revb_up_n, tbl_engr.engr_lte_out_revb_dn_k, tbl_engr.engr_lte_out_revb_dn_n, tbl_engr.engr_lte_out_revb_up_k, tbl_engr.engr_lte_out_revb_up_n, tbl_engr.engr_lte2_in_revb_dn_k, tbl_engr.engr_lte2_in_revb_dn_n, tbl_engr.engr_lte2_in_revb_up_k, tbl_engr.engr_lte2_in_revb_up_n, tbl_engr.engr_lte2_out_revb_dn_k, tbl_engr.engr_lte2_out_revb_dn_n, tbl_engr.engr_lte2_out_revb_up_k, tbl_engr.engr_lte2_out_revb_up_n, tbl_engr.engr_pcs_in_revb_dn_k, tbl_engr.engr_pcs_in_revb_dn_n, tbl_engr.engr_pcs_in_revb_up_k, tbl_engr.engr_pcs_in_revb_up_n, tbl_engr.engr_pcs_out_revb_dn_k, tbl_engr.engr_pcs_out_revb_dn_n, tbl_engr.engr_pcs_out_revb_up_k, tbl_engr.engr_pcs_out_revb_up_n, tbl_engr.engr_pilot_aws_out_revb_dn_k, tbl_engr.engr_pilot_aws_out_revb_dn_n, tbl_engr.engr_pilot_aws_out_revb_up_k, tbl_engr.engr_pilot_aws_out_revb_up_n, tbl_engr.engr_pilot_cell_out_revb_dn_, tbl_engr.engr_pilot_cell_out_revb_dn_n, tbl_engr.engr_pilot_cell_out_revb_up_k, tbl_engr.engr_pilot_cell_out_revb_up_n, tbl_engr.engr_pilot_pcs_out_revb_dn_k, tbl_engr.engr_pilot_pcs_out_revb_dn_n, tbl_engr.engr_pilot_pcs_out_revb_up_k, tbl_engr.engr_pilot_pcs_out_revb_up_n, tbl_engr.engr_scan_rcvr_cal_1, tbl_engr.engr_scan_rcvr_cal_2, tbl_engr.engr_scan_rcvr_cal2_0, tbl_engr.engr_scan_rcvr_cal2_1, tbl_engr.engr_scan_rcvr_cal2_2, tbl_engr.engr_scan_rcvr_cell_cal_0, tbl_engr.engr_scan_rcvr_cell_cal_1, tbl_engr.engr_scan_rcvr_cell_cal_2, tbl_engr.engr_scan_rcvr_lte_cal_0, tbl_engr.engr_scan_rcvr_lte_cal_1, tbl_engr.engr_scan_rcvr_lte_cal_2, tbl_engr.engr_smr_in_revb_dn_k, tbl_engr.engr_smr_in_revb_dn_n, tbl_engr.engr_smr_in_revb_up_k, tbl_engr.engr_smr_in_revb_up_n, tbl_engr.engr_smr_out_revb_dn_k, tbl_engr.engr_smr_out_revb_dn_n, tbl_engr.engr_smr_out_revb_up_k, tbl_engr.engr_smr_out_revb_up_n, tbl_engr.engr_temp_gain_dn_k, tbl_engr.engr_temp_gain_dn_n, tbl_engr.engr_temp_gain_up_k, tbl_engr.engr_temp_gain_up_n, tbl_engr.engr_temp_gain1_dn_k, tbl_engr.engr_temp_gain1_dn_n, tbl_engr.engr_temp_gain1_up_k, tbl_engr.engr_temp_gain1_up_n, tbl_pilot_1.pilot_a3_calvalue_cell, tbl_pilot_1.pilot_a3_calvalue_pcs, tbl_pilot_1.pilot_a3_calvalue_aws from tbl_identity inner join tbl_link_1 on tbl_identity.ID = tbl_link_1.ID inner join tbl_caldata_1 on tbl_link_1.ID = tbl_caldata_1.ID inner join tbl_engr on tbl_caldata_1.ID = tbl_engr.ID inner join tbl_pilot_1 on tbl_engr.ID = tbl_pilot_1.ID into outfile 'C:/Webserver/config_1Band_withPilot.csv' fields terminated by ',' enclosed by '"' lines terminated by '\n'";
					stmt.executeQuery(query1);
					System.out.println("The config_1Band.csv file is created.");
				}


				catch (SQLException ex) {
					Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
					lgr.log(Level.SEVERE, ex.getMessage(), ex);
				}
				finally {
					try {
						if (connection != null) {
							connection.close();
						}
						if (stmt != null) {
							stmt.close();
						}

					}
					catch (SQLException ex) {
						Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
						lgr.log(Level.WARNING, ex.getMessage(), ex);
					}
				}
				//Create the .csv file for the config_2 (Band 2) data (Properties and their values). .CSV file will contain the property names as headers and the all values
				try
				{
					connection = DriverManager.getConnection(url, un, pass);                 	   
					stmt = connection.createStatement();

					//query2 without pilot properties
					String query2 = "select 'identity_serial_number', 'identity_model_number', 'identity_item_number', 'identity_software_version', 'identity_date_built', 'link_config_board_rev', 'link_config_board_type', 'link_uplink_attenuator_one', 'link_dnlink_attenuator_one', 'link_uplink_attenuator_two', 'link_dnlink_attenuator_two', 'link_uplink_attenuator_three', 'link_dnlink_attenuator_three', 'link_dnlink_dac_byte1', 'link_dnlink_dac_byte2', 'link_dnlink_dac_byte3', 'link_dnlink_dac_byte4', 'link_dnlink_dac_byte5', 'link_dnlink_dac_byte6', 'link_dnlink_dac_byte7', 'link_dnlink_dac_byte8', 'link_dnlink_dac_byte9', 'link_dnlink_dac_byte10', 'link_dnlink_dac_byte11', 'link_dnlink_dac_byte12', 'link_dnlink_dac_byte13', 'link_uplink_dac_byte1', 'link_uplink_dac_byte2', 'link_uplink_dac_byte3', 'link_uplink_dac_byte4', 'link_uplink_dac_byte5', 'link_uplink_dac_byte6', 'link_uplink_dac_byte7', 'link_uplink_dac_byte8', 'link_uplink_dac_byte9', 'link_uplink_dac_byte10', 'link_uplink_dac_byte11', 'link_uplink_dac_byte12', 'link_uplink_dac_byte13', 'link_dnlink_dac1_byte1', 'link_dnlink_dac1_byte2', 'link_dnlink_dac1_byte3', 'link_dnlink_dac1_byte4', 'link_dnlink_dac1_byte5', 'link_dnlink_dac1_byte6', 'link_dnlink_dac1_byte7', 'link_dnlink_dac1_byte8', 'link_dnlink_dac1_byte9', 'link_dnlink_dac1_byte10', 'link_dnlink_dac1_byte11', 'link_dnlink_dac1_byte12', 'link_dnlink_dac1_byte13', 'link_dnlink_dac1_byte14', 'link_dnlink_dac1_byte15', 'link_dnlink_dac1_byte16', 'link_dnlink_dac1_byte17', 'link_dnlink_dac1_byte18', 'link_dnlink_dac1_byte19', 'link_uplink_dac1_byte1', 'link_uplink_dac1_byte2', 'link_uplink_dac1_byte3', 'link_uplink_dac1_byte4', 'link_uplink_dac1_byte5', 'link_uplink_dac1_byte6', 'link_uplink_dac1_byte7', 'link_uplink_dac1_byte8', 'link_uplink_dac1_byte9', 'link_uplink_dac1_byte10', 'link_uplink_dac1_byte11', 'link_uplink_dac1_byte12', 'link_uplink_dac1_byte13', 'link_uplink_dac1_byte14', 'link_uplink_dac1_byte15', 'link_uplink_dac1_byte16', 'link_uplink_dac1_byte17', 'link_uplink_dac1_byte18', 'link_uplink_dac1_byte19', 'calibration_dnlink_output_adc_goal', 'calibration_dnlink_output_power_goal', 'calibration_dnlink_output_adc_maximum', 'calibration_dnlink_output_power_maximum', 'calibration_uplink_output_adc_goal', 'calibration_uplink_output_power_goal', 'calibration_uplink_output_adc_maximum', 'calibration_uplink_output_power_maximum', 'engr_acela_cell_out_revb_dn_k', 'engr_acela_cell_out_revb_dn_n', 'engr_acela_cell_out_revb_up_k', 'engr_acela_cell_out_revb_up_n', 'engr_acela_lte_out_revb_dn_k', 'engr_acela_lte_out_revb_dn_n', 'engr_acela_lte_out_revb_up_k', 'engr_acela_lte_out_revb_up_n', 'engr_acela_pcs_out_revb_dn_k', 'engr_acela_pcs_out_revb_dn_n', 'engr_acela_pcs_out_revb_up_k', 'engr_acela_pcs_out_revb_up_n', 'engr_aws_in_revb_dn_k', 'engr_aws_in_revb_dn_n', 'engr_aws_in_revb_up_k', 'engr_aws_in_revb_up_n', 'engr_aws_out_revb_dn_k', 'engr_aws_out_revb_dn_n', 'engr_aws_out_revb_up_k', 'engr_aws_out_revb_up_n', 'engr_cell_in_revb_dn_k', 'engr_cell_in_revb_dn_n', 'engr_cell_in_revb_up_k', 'engr_cell_in_revb_up_n', 'engr_cell_out_revb_dn_k', 'engr_cell_out_revb_dn_n', 'engr_cell_out_revb_up_k', 'engr_cell_out_revb_up_n', 'engr_das_24v_dnlink_coeff_acela', 'engr_das_24v_dnlink_coeff_acela_cell', 'engr_das_24v_dnlink_coeff_acela_lte', 'engr_das_24v_uplink_coeff_acela', 'engr_das_24v_uplink_coeff_acela_cell', 'engr_das_24v_uplink_coeff_acela_lte', 'engr_das_24v_dnlink_coeff_acela_cell1', 'engr_das_24v_dnlink_coeff_acela_lte1', 'engr_das_24v_dnlink_coeff_acela_pcs', 'engr_das_24v_dnlink_coeff_acela_aws', 'engr_das_24v_uplink_coeff_acela_cell1', 'engr_das_24v_uplink_coeff_acela_lte1', 'engr_das_24v_uplink_coeff_acela_pcs', 'engr_das_24v_uplink_coeff_acela_aws', 'engr_lte_in_revb_dn_k', 'engr_lte_in_revb_dn_n', 'engr_lte_in_revb_up_', 'engr_lte_in_revb_up_n', 'engr_lte_out_revb_dn_k', 'engr_lte_out_revb_dn_n', 'engr_lte_out_revb_up_k', 'engr_lte_out_revb_up_n', 'engr_lte2_in_revb_dn_k', 'engr_lte2_in_revb_dn_n', 'engr_lte2_in_revb_up_k', 'engr_lte2_in_revb_up_n', 'engr_lte2_out_revb_dn_k', 'engr_lte2_out_revb_dn_n', 'engr_lte2_out_revb_up_k', 'engr_lte2_out_revb_up_n', 'engr_pcs_in_revb_dn_k', 'engr_pcs_in_revb_dn_n', 'engr_pcs_in_revb_up_k', 'engr_pcs_in_revb_up_n', 'engr_pcs_out_revb_dn_k', 'engr_pcs_out_revb_dn_n', 'engr_pcs_out_revb_up_k', 'engr_pcs_out_revb_up_n', 'engr_pilot_aws_out_revb_dn_k', 'engr_pilot_aws_out_revb_dn_n', 'engr_pilot_aws_out_revb_up_k', 'engr_pilot_aws_out_revb_up_n', 'engr_pilot_cell_out_revb_dn_', 'engr_pilot_cell_out_revb_dn_n', 'engr_pilot_cell_out_revb_up_k', 'engr_pilot_cell_out_revb_up_n', 'engr_pilot_pcs_out_revb_dn_k', 'engr_pilot_pcs_out_revb_dn_n', 'engr_pilot_pcs_out_revb_up_k', 'engr_pilot_pcs_out_revb_up_n', 'engr_scan_rcvr_cal_1', 'engr_scan_rcvr_cal_2', 'engr_scan_rcvr_cal2_0', 'engr_scan_rcvr_cal2_1', 'engr_scan_rcvr_cal2_2', 'engr_scan_rcvr_cell_cal_0', 'engr_scan_rcvr_cell_cal_1', 'engr_scan_rcvr_cell_cal_2', 'engr_scan_rcvr_lte_cal_0', 'engr_scan_rcvr_lte_cal_1', 'engr_scan_rcvr_lte_cal_2', 'engr_smr_in_revb_dn_k', 'engr_smr_in_revb_dn_n', 'engr_smr_in_revb_up_k', 'engr_smr_in_revb_up_n', 'engr_smr_out_revb_dn_k', 'engr_smr_out_revb_dn_n', 'engr_smr_out_revb_up_k', 'engr_smr_out_revb_up_n', 'engr_temp_gain_dn_k', 'engr_temp_gain_dn_n', 'engr_temp_gain_up_k', 'engr_temp_gain_up_n', 'engr_temp_gain1_dn_k', 'engr_temp_gain1_dn_n', 'engr_temp_gain1_up_k', 'engr_temp_gain1_up_n' union all select tbl_identity.identity_serial_number, tbl_identity.identity_model_number, tbl_identity.identity_item_number, tbl_identity.identity_software_version, tbl_identity.identity_date_built, tbl_link_2.link_config_board_rev, tbl_link_2.link_config_board_type, tbl_link_2.link_uplink_attenuator_one, tbl_link_2.link_dnlink_attenuator_one, tbl_link_2.link_uplink_attenuator_two, tbl_link_2.link_dnlink_attenuator_two, tbl_link_2.link_uplink_attenuator_three, tbl_link_2.link_dnlink_attenuator_three, tbl_link_2.link_dnlink_dac_byte1, tbl_link_2.link_dnlink_dac_byte2, tbl_link_2.link_dnlink_dac_byte3, tbl_link_2.link_dnlink_dac_byte4, tbl_link_2.link_dnlink_dac_byte5, tbl_link_2.link_dnlink_dac_byte6, tbl_link_2.link_dnlink_dac_byte7, tbl_link_2.link_dnlink_dac_byte8, tbl_link_2.link_dnlink_dac_byte9, tbl_link_2.link_dnlink_dac_byte10, tbl_link_2.link_dnlink_dac_byte11, tbl_link_2.link_dnlink_dac_byte12, tbl_link_2.link_dnlink_dac_byte13, tbl_link_2.link_uplink_dac_byte1, tbl_link_2.link_uplink_dac_byte2, tbl_link_2.link_uplink_dac_byte3, tbl_link_2.link_uplink_dac_byte4, tbl_link_2.link_uplink_dac_byte5, tbl_link_2.link_uplink_dac_byte6, tbl_link_2.link_uplink_dac_byte7, tbl_link_2.link_uplink_dac_byte8, tbl_link_2.link_uplink_dac_byte9, tbl_link_2.link_uplink_dac_byte10, tbl_link_2.link_uplink_dac_byte11, tbl_link_2.link_uplink_dac_byte12, tbl_link_2.link_uplink_dac_byte13, tbl_link_2.link_dnlink_dac1_byte1, tbl_link_2.link_dnlink_dac1_byte2, tbl_link_2.link_dnlink_dac1_byte3, tbl_link_2.link_dnlink_dac1_byte4, tbl_link_2.link_dnlink_dac1_byte5, tbl_link_2.link_dnlink_dac1_byte6, tbl_link_2.link_dnlink_dac1_byte7, tbl_link_2.link_dnlink_dac1_byte8, tbl_link_2.link_dnlink_dac1_byte9, tbl_link_2.link_dnlink_dac1_byte10, tbl_link_2.link_dnlink_dac1_byte11, tbl_link_2.link_dnlink_dac1_byte12, tbl_link_2.link_dnlink_dac1_byte13, tbl_link_2.link_dnlink_dac1_byte14, tbl_link_2.link_dnlink_dac1_byte15, tbl_link_2.link_dnlink_dac1_byte16, tbl_link_2.link_dnlink_dac1_byte17, tbl_link_2.link_dnlink_dac1_byte18, tbl_link_2.link_dnlink_dac1_byte19, tbl_link_2.link_uplink_dac1_byte1, tbl_link_2.link_uplink_dac1_byte2, tbl_link_2.link_uplink_dac1_byte3, tbl_link_2.link_uplink_dac1_byte4, tbl_link_2.link_uplink_dac1_byte5, tbl_link_2.link_uplink_dac1_byte6, tbl_link_2.link_uplink_dac1_byte7, tbl_link_2.link_uplink_dac1_byte8, tbl_link_2.link_uplink_dac1_byte9, tbl_link_2.link_uplink_dac1_byte10, tbl_link_2.link_uplink_dac1_byte11, tbl_link_2.link_uplink_dac1_byte12, tbl_link_2.link_uplink_dac1_byte13, tbl_link_2.link_uplink_dac1_byte14, tbl_link_2.link_uplink_dac1_byte15, tbl_link_2.link_uplink_dac1_byte16, tbl_link_2.link_uplink_dac1_byte17, tbl_link_2.link_uplink_dac1_byte18, tbl_link_2.link_uplink_dac1_byte19, tbl_caldata_2.calibration_dnlink_output_adc_goal, tbl_caldata_2.calibration_dnlink_output_power_goal, tbl_caldata_2.calibration_dnlink_output_adc_maximum, tbl_caldata_2.calibration_dnlink_output_power_maximum, tbl_caldata_2.calibration_uplink_output_adc_goal, tbl_caldata_2.calibration_uplink_output_power_goal, tbl_caldata_2.calibration_uplink_output_adc_maximum, tbl_caldata_2.calibration_uplink_output_power_maximum, tbl_engr.engr_acela_cell_out_revb_dn_k, tbl_engr.engr_acela_cell_out_revb_dn_n, tbl_engr.engr_acela_cell_out_revb_up_k, tbl_engr.engr_acela_cell_out_revb_up_n, tbl_engr.engr_acela_lte_out_revb_dn_k, tbl_engr.engr_acela_lte_out_revb_dn_n, tbl_engr.engr_acela_lte_out_revb_up_k, tbl_engr.engr_acela_lte_out_revb_up_n, tbl_engr.engr_acela_pcs_out_revb_dn_k, tbl_engr.engr_acela_pcs_out_revb_dn_n, tbl_engr.engr_acela_pcs_out_revb_up_k, tbl_engr.engr_acela_pcs_out_revb_up_n, tbl_engr.engr_aws_in_revb_dn_k, tbl_engr.engr_aws_in_revb_dn_n, tbl_engr.engr_aws_in_revb_up_k, tbl_engr.engr_aws_in_revb_up_n, tbl_engr.engr_aws_out_revb_dn_k, tbl_engr.engr_aws_out_revb_dn_n, tbl_engr.engr_aws_out_revb_up_k, tbl_engr.engr_aws_out_revb_up_n, tbl_engr.engr_cell_in_revb_dn_k, tbl_engr.engr_cell_in_revb_dn_n, tbl_engr.engr_cell_in_revb_up_k, tbl_engr.engr_cell_in_revb_up_n, tbl_engr.engr_cell_out_revb_dn_k, tbl_engr.engr_cell_out_revb_dn_n, tbl_engr.engr_cell_out_revb_up_k, tbl_engr.engr_cell_out_revb_up_n, tbl_engr.engr_das_24v_dnlink_coeff_acela, tbl_engr.engr_das_24v_dnlink_coeff_acela_cell, tbl_engr.engr_das_24v_dnlink_coeff_acela_lte, tbl_engr.engr_das_24v_uplink_coeff_acela, tbl_engr.engr_das_24v_uplink_coeff_acela_cell, tbl_engr.engr_das_24v_uplink_coeff_acela_lte, tbl_engr.engr_das_24v_dnlink_coeff_acela_cell1, tbl_engr.engr_das_24v_dnlink_coeff_acela_lte1, tbl_engr.engr_das_24v_dnlink_coeff_acela_pcs, tbl_engr.engr_das_24v_dnlink_coeff_acela_aws, tbl_engr.engr_das_24v_uplink_coeff_acela_cell1, tbl_engr.engr_das_24v_uplink_coeff_acela_lte1, tbl_engr.engr_das_24v_uplink_coeff_acela_pcs, tbl_engr.engr_das_24v_uplink_coeff_acela_aws, tbl_engr.engr_lte_in_revb_dn_k, tbl_engr.engr_lte_in_revb_dn_n, tbl_engr.engr_lte_in_revb_up_, tbl_engr.engr_lte_in_revb_up_n, tbl_engr.engr_lte_out_revb_dn_k, tbl_engr.engr_lte_out_revb_dn_n, tbl_engr.engr_lte_out_revb_up_k, tbl_engr.engr_lte_out_revb_up_n, tbl_engr.engr_lte2_in_revb_dn_k, tbl_engr.engr_lte2_in_revb_dn_n, tbl_engr.engr_lte2_in_revb_up_k, tbl_engr.engr_lte2_in_revb_up_n, tbl_engr.engr_lte2_out_revb_dn_k, tbl_engr.engr_lte2_out_revb_dn_n, tbl_engr.engr_lte2_out_revb_up_k, tbl_engr.engr_lte2_out_revb_up_n, tbl_engr.engr_pcs_in_revb_dn_k, tbl_engr.engr_pcs_in_revb_dn_n, tbl_engr.engr_pcs_in_revb_up_k, tbl_engr.engr_pcs_in_revb_up_n, tbl_engr.engr_pcs_out_revb_dn_k, tbl_engr.engr_pcs_out_revb_dn_n, tbl_engr.engr_pcs_out_revb_up_k, tbl_engr.engr_pcs_out_revb_up_n, tbl_engr.engr_pilot_aws_out_revb_dn_k, tbl_engr.engr_pilot_aws_out_revb_dn_n, tbl_engr.engr_pilot_aws_out_revb_up_k, tbl_engr.engr_pilot_aws_out_revb_up_n, tbl_engr.engr_pilot_cell_out_revb_dn_, tbl_engr.engr_pilot_cell_out_revb_dn_n, tbl_engr.engr_pilot_cell_out_revb_up_k, tbl_engr.engr_pilot_cell_out_revb_up_n, tbl_engr.engr_pilot_pcs_out_revb_dn_k, tbl_engr.engr_pilot_pcs_out_revb_dn_n, tbl_engr.engr_pilot_pcs_out_revb_up_k, tbl_engr.engr_pilot_pcs_out_revb_up_n, tbl_engr.engr_scan_rcvr_cal_1, tbl_engr.engr_scan_rcvr_cal_2, tbl_engr.engr_scan_rcvr_cal2_0, tbl_engr.engr_scan_rcvr_cal2_1, tbl_engr.engr_scan_rcvr_cal2_2, tbl_engr.engr_scan_rcvr_cell_cal_0, tbl_engr.engr_scan_rcvr_cell_cal_1, tbl_engr.engr_scan_rcvr_cell_cal_2, tbl_engr.engr_scan_rcvr_lte_cal_0, tbl_engr.engr_scan_rcvr_lte_cal_1, tbl_engr.engr_scan_rcvr_lte_cal_2, tbl_engr.engr_smr_in_revb_dn_k, tbl_engr.engr_smr_in_revb_dn_n, tbl_engr.engr_smr_in_revb_up_k, tbl_engr.engr_smr_in_revb_up_n, tbl_engr.engr_smr_out_revb_dn_k, tbl_engr.engr_smr_out_revb_dn_n, tbl_engr.engr_smr_out_revb_up_k, tbl_engr.engr_smr_out_revb_up_n, tbl_engr.engr_temp_gain_dn_k, tbl_engr.engr_temp_gain_dn_n, tbl_engr.engr_temp_gain_up_k, tbl_engr.engr_temp_gain_up_n, tbl_engr.engr_temp_gain1_dn_k, tbl_engr.engr_temp_gain1_dn_n, tbl_engr.engr_temp_gain1_up_k, tbl_engr.engr_temp_gain1_up_n from tbl_identity inner join tbl_link_2 on tbl_identity.ID = tbl_link_2.ID inner join tbl_caldata_2 on tbl_link_2.ID = tbl_caldata_2.ID inner join tbl_engr on tbl_caldata_2.ID = tbl_engr.ID into outfile 'C:/Labview/CSV Files/DSP_II_config_2Band.csv' fields terminated by ',' enclosed by '\"' lines terminated by '\n'";

					//query2 with pilot properties
					//				query2 = "select 'identity_serial_number', 'identity_model_number', 'identity_item_number', 'identity_software_version', 'identity_date_built', 'link_config_board_rev', 'link_config_board_type', 'link_uplink_attenuator_one', 'link_dnlink_attenuator_one', 'link_uplink_attenuator_two', 'link_dnlink_attenuator_two', 'link_uplink_attenuator_three', 'link_dnlink_attenuator_three', 'link_dnlink_dac_byte1', 'link_dnlink_dac_byte2', 'link_dnlink_dac_byte3', 'link_dnlink_dac_byte4', 'link_dnlink_dac_byte5', 'link_dnlink_dac_byte6', 'link_dnlink_dac_byte7', 'link_dnlink_dac_byte8', 'link_dnlink_dac_byte9', 'link_dnlink_dac_byte10', 'link_dnlink_dac_byte11', 'link_dnlink_dac_byte12', 'link_dnlink_dac_byte13', 'link_uplink_dac_byte1', 'link_uplink_dac_byte2', 'link_uplink_dac_byte3', 'link_uplink_dac_byte4', 'link_uplink_dac_byte5', 'link_uplink_dac_byte6', 'link_uplink_dac_byte7', 'link_uplink_dac_byte8', 'link_uplink_dac_byte9', 'link_uplink_dac_byte10', 'link_uplink_dac_byte11', 'link_uplink_dac_byte12', 'link_uplink_dac_byte13', 'link_dnlink_dac1_byte1', 'link_dnlink_dac1_byte2', 'link_dnlink_dac1_byte3', 'link_dnlink_dac1_byte4', 'link_dnlink_dac1_byte5', 'link_dnlink_dac1_byte6', 'link_dnlink_dac1_byte7', 'link_dnlink_dac1_byte8', 'link_dnlink_dac1_byte9', 'link_dnlink_dac1_byte10', 'link_dnlink_dac1_byte11', 'link_dnlink_dac1_byte12', 'link_dnlink_dac1_byte13', 'link_dnlink_dac1_byte14', 'link_dnlink_dac1_byte15', 'link_dnlink_dac1_byte16', 'link_dnlink_dac1_byte17', 'link_dnlink_dac1_byte18', 'link_dnlink_dac1_byte19', 'link_uplink_dac1_byte1', 'link_uplink_dac1_byte2', 'link_uplink_dac1_byte3', 'link_uplink_dac1_byte4', 'link_uplink_dac1_byte5', 'link_uplink_dac1_byte6', 'link_uplink_dac1_byte7', 'link_uplink_dac1_byte8', 'link_uplink_dac1_byte9', 'link_uplink_dac1_byte10', 'link_uplink_dac1_byte11', 'link_uplink_dac1_byte12', 'link_uplink_dac1_byte13', 'link_uplink_dac1_byte14', 'link_uplink_dac1_byte15', 'link_uplink_dac1_byte16', 'link_uplink_dac1_byte17', 'link_uplink_dac1_byte18', 'link_uplink_dac1_byte19', 'calibration_dnlink_output_adc_goal', 'calibration_dnlink_output_power_goal', 'calibration_dnlink_output_adc_maximum', 'calibration_dnlink_output_power_maximum', 'calibration_uplink_output_adc_goal', 'calibration_uplink_output_power_goal', 'calibration_uplink_output_adc_maximum', 'calibration_uplink_output_power_maximum', 'engr_acela_cell_out_revb_dn_k', 'engr_acela_cell_out_revb_dn_n', 'engr_acela_cell_out_revb_up_k', 'engr_acela_cell_out_revb_up_n', 'engr_acela_lte_out_revb_dn_k', 'engr_acela_lte_out_revb_dn_n', 'engr_acela_lte_out_revb_up_k', 'engr_acela_lte_out_revb_up_n', 'engr_acela_pcs_out_revb_dn_k', 'engr_acela_pcs_out_revb_dn_n', 'engr_acela_pcs_out_revb_up_k', 'engr_acela_pcs_out_revb_up_n', 'engr_aws_in_revb_dn_k', 'engr_aws_in_revb_dn_n', 'engr_aws_in_revb_up_k', 'engr_aws_in_revb_up_n', 'engr_aws_out_revb_dn_k', 'engr_aws_out_revb_dn_n', 'engr_aws_out_revb_up_k', 'engr_aws_out_revb_up_n', 'engr_cell_in_revb_dn_k', 'engr_cell_in_revb_dn_n', 'engr_cell_in_revb_up_k', 'engr_cell_in_revb_up_n', 'engr_cell_out_revb_dn_k', 'engr_cell_out_revb_dn_n', 'engr_cell_out_revb_up_k', 'engr_cell_out_revb_up_n', 'engr_das_24v_dnlink_coeff_acela', 'engr_das_24v_dnlink_coeff_acela_cell', 'engr_das_24v_dnlink_coeff_acela_lte', 'engr_das_24v_uplink_coeff_acela', 'engr_das_24v_uplink_coeff_acela_cell', 'engr_das_24v_uplink_coeff_acela_lte', 'engr_das_24v_dnlink_coeff_acela_cell1', 'engr_das_24v_dnlink_coeff_acela_lte1', 'engr_das_24v_dnlink_coeff_acela_pcs', 'engr_das_24v_dnlink_coeff_acela_aws', 'engr_das_24v_uplink_coeff_acela_cell1', 'engr_das_24v_uplink_coeff_acela_lte1', 'engr_das_24v_uplink_coeff_acela_pcs', 'engr_das_24v_uplink_coeff_acela_aws', 'engr_lte_in_revb_dn_k', 'engr_lte_in_revb_dn_n', 'engr_lte_in_revb_up_', 'engr_lte_in_revb_up_n', 'engr_lte_out_revb_dn_k', 'engr_lte_out_revb_dn_n', 'engr_lte_out_revb_up_k', 'engr_lte_out_revb_up_n', 'engr_lte2_in_revb_dn_k', 'engr_lte2_in_revb_dn_n', 'engr_lte2_in_revb_up_k', 'engr_lte2_in_revb_up_n', 'engr_lte2_out_revb_dn_k', 'engr_lte2_out_revb_dn_n', 'engr_lte2_out_revb_up_k', 'engr_lte2_out_revb_up_n', 'engr_pcs_in_revb_dn_k', 'engr_pcs_in_revb_dn_n', 'engr_pcs_in_revb_up_k', 'engr_pcs_in_revb_up_n', 'engr_pcs_out_revb_dn_k', 'engr_pcs_out_revb_dn_n', 'engr_pcs_out_revb_up_k', 'engr_pcs_out_revb_up_n', 'engr_pilot_aws_out_revb_dn_k', 'engr_pilot_aws_out_revb_dn_n', 'engr_pilot_aws_out_revb_up_k', 'engr_pilot_aws_out_revb_up_n', 'engr_pilot_cell_out_revb_dn_', 'engr_pilot_cell_out_revb_dn_n', 'engr_pilot_cell_out_revb_up_k', 'engr_pilot_cell_out_revb_up_n', 'engr_pilot_pcs_out_revb_dn_k', 'engr_pilot_pcs_out_revb_dn_n', 'engr_pilot_pcs_out_revb_up_k', 'engr_pilot_pcs_out_revb_up_n', 'engr_scan_rcvr_cal_1', 'engr_scan_rcvr_cal_2', 'engr_scan_rcvr_cal2_0', 'engr_scan_rcvr_cal2_1', 'engr_scan_rcvr_cal2_2', 'engr_scan_rcvr_cell_cal_0', 'engr_scan_rcvr_cell_cal_1', 'engr_scan_rcvr_cell_cal_2', 'engr_scan_rcvr_lte_cal_0', 'engr_scan_rcvr_lte_cal_1', 'engr_scan_rcvr_lte_cal_2', 'engr_smr_in_revb_dn_k', 'engr_smr_in_revb_dn_n', 'engr_smr_in_revb_up_k', 'engr_smr_in_revb_up_n', 'engr_smr_out_revb_dn_k', 'engr_smr_out_revb_dn_n', 'engr_smr_out_revb_up_k', 'engr_smr_out_revb_up_n', 'engr_temp_gain_dn_k', 'engr_temp_gain_dn_n', 'engr_temp_gain_up_k', 'engr_temp_gain_up_n', 'engr_temp_gain1_dn_k', 'engr_temp_gain1_dn_n', 'engr_temp_gain1_up_k', 'engr_temp_gain1_up_n', 'pilot_a3_calvalue_cell', 'pilot_a3_calvalue_pcs', 'pilot_a3_calvalue_aws' union all select tbl_identity.identity_serial_number, tbl_identity.identity_model_number, tbl_identity.identity_item_number, tbl_identity.identity_software_version, tbl_identity.identity_date_built, tbl_link_2.link_config_board_rev, tbl_link_2.link_config_board_type, tbl_link_2.link_uplink_attenuator_one, tbl_link_2.link_dnlink_attenuator_one, tbl_link_2.link_uplink_attenuator_two, tbl_link_2.link_dnlink_attenuator_two, tbl_link_2.link_uplink_attenuator_three, tbl_link_2.link_dnlink_attenuator_three, tbl_link_2.link_dnlink_dac_byte1, tbl_link_2.link_dnlink_dac_byte2, tbl_link_2.link_dnlink_dac_byte3, tbl_link_2.link_dnlink_dac_byte4, tbl_link_2.link_dnlink_dac_byte5, tbl_link_2.link_dnlink_dac_byte6, tbl_link_2.link_dnlink_dac_byte7, tbl_link_2.link_dnlink_dac_byte8, tbl_link_2.link_dnlink_dac_byte9, tbl_link_2.link_dnlink_dac_byte10, tbl_link_2.link_dnlink_dac_byte11, tbl_link_2.link_dnlink_dac_byte12, tbl_link_2.link_dnlink_dac_byte13, tbl_link_2.link_uplink_dac_byte1, tbl_link_2.link_uplink_dac_byte2, tbl_link_2.link_uplink_dac_byte3, tbl_link_2.link_uplink_dac_byte4, tbl_link_2.link_uplink_dac_byte5, tbl_link_2.link_uplink_dac_byte6, tbl_link_2.link_uplink_dac_byte7, tbl_link_2.link_uplink_dac_byte8, tbl_link_2.link_uplink_dac_byte9, tbl_link_2.link_uplink_dac_byte10, tbl_link_2.link_uplink_dac_byte11, tbl_link_2.link_uplink_dac_byte12, tbl_link_2.link_uplink_dac_byte13, tbl_link_2.link_dnlink_dac1_byte1, tbl_link_2.link_dnlink_dac1_byte2, tbl_link_2.link_dnlink_dac1_byte3, tbl_link_2.link_dnlink_dac1_byte4, tbl_link_2.link_dnlink_dac1_byte5, tbl_link_2.link_dnlink_dac1_byte6, tbl_link_2.link_dnlink_dac1_byte7, tbl_link_2.link_dnlink_dac1_byte8, tbl_link_2.link_dnlink_dac1_byte9, tbl_link_2.link_dnlink_dac1_byte10, tbl_link_2.link_dnlink_dac1_byte11, tbl_link_2.link_dnlink_dac1_byte12, tbl_link_2.link_dnlink_dac1_byte13, tbl_link_2.link_dnlink_dac1_byte14, tbl_link_2.link_dnlink_dac1_byte15, tbl_link_2.link_dnlink_dac1_byte16, tbl_link_2.link_dnlink_dac1_byte17, tbl_link_2.link_dnlink_dac1_byte18, tbl_link_2.link_dnlink_dac1_byte19, tbl_link_2.link_uplink_dac1_byte1, tbl_link_2.link_uplink_dac1_byte2, tbl_link_2.link_uplink_dac1_byte3, tbl_link_2.link_uplink_dac1_byte4, tbl_link_2.link_uplink_dac1_byte5, tbl_link_2.link_uplink_dac1_byte6, tbl_link_2.link_uplink_dac1_byte7, tbl_link_2.link_uplink_dac1_byte8, tbl_link_2.link_uplink_dac1_byte9, tbl_link_2.link_uplink_dac1_byte10, tbl_link_2.link_uplink_dac1_byte11, tbl_link_2.link_uplink_dac1_byte12, tbl_link_2.link_uplink_dac1_byte13, tbl_link_2.link_uplink_dac1_byte14, tbl_link_2.link_uplink_dac1_byte15, tbl_link_2.link_uplink_dac1_byte16, tbl_link_2.link_uplink_dac1_byte17, tbl_link_2.link_uplink_dac1_byte18, tbl_link_2.link_uplink_dac1_byte19, tbl_caldata_2.calibration_dnlink_output_adc_goal, tbl_caldata_2.calibration_dnlink_output_power_goal, tbl_caldata_2.calibration_dnlink_output_adc_maximum, tbl_caldata_2.calibration_dnlink_output_power_maximum, tbl_caldata_2.calibration_uplink_output_adc_goal, tbl_caldata_2.calibration_uplink_output_power_goal, tbl_caldata_2.calibration_uplink_output_adc_maximum, tbl_caldata_2.calibration_uplink_output_power_maximum, tbl_engr.engr_acela_cell_out_revb_dn_k, tbl_engr.engr_acela_cell_out_revb_dn_n, tbl_engr.engr_acela_cell_out_revb_up_k, tbl_engr.engr_acela_cell_out_revb_up_n, tbl_engr.engr_acela_lte_out_revb_dn_k, tbl_engr.engr_acela_lte_out_revb_dn_n, tbl_engr.engr_acela_lte_out_revb_up_k, tbl_engr.engr_acela_lte_out_revb_up_n, tbl_engr.engr_acela_pcs_out_revb_dn_k, tbl_engr.engr_acela_pcs_out_revb_dn_n, tbl_engr.engr_acela_pcs_out_revb_up_k, tbl_engr.engr_acela_pcs_out_revb_up_n, tbl_engr.engr_aws_in_revb_dn_k, tbl_engr.engr_aws_in_revb_dn_n, tbl_engr.engr_aws_in_revb_up_k, tbl_engr.engr_aws_in_revb_up_n, tbl_engr.engr_aws_out_revb_dn_k, tbl_engr.engr_aws_out_revb_dn_n, tbl_engr.engr_aws_out_revb_up_k, tbl_engr.engr_aws_out_revb_up_n, tbl_engr.engr_cell_in_revb_dn_k, tbl_engr.engr_cell_in_revb_dn_n, tbl_engr.engr_cell_in_revb_up_k, tbl_engr.engr_cell_in_revb_up_n, tbl_engr.engr_cell_out_revb_dn_k, tbl_engr.engr_cell_out_revb_dn_n, tbl_engr.engr_cell_out_revb_up_k, tbl_engr.engr_cell_out_revb_up_n, tbl_engr.engr_das_24v_dnlink_coeff_acela, tbl_engr.engr_das_24v_dnlink_coeff_acela_cell, tbl_engr.engr_das_24v_dnlink_coeff_acela_lte, tbl_engr.engr_das_24v_uplink_coeff_acela, tbl_engr.engr_das_24v_uplink_coeff_acela_cell, tbl_engr.engr_das_24v_uplink_coeff_acela_lte, tbl_engr.engr_das_24v_dnlink_coeff_acela_cell1, tbl_engr.engr_das_24v_dnlink_coeff_acela_lte1, tbl_engr.engr_das_24v_dnlink_coeff_acela_pcs, tbl_engr.engr_das_24v_dnlink_coeff_acela_aws, tbl_engr.engr_das_24v_uplink_coeff_acela_cell1, tbl_engr.engr_das_24v_uplink_coeff_acela_lte1, tbl_engr.engr_das_24v_uplink_coeff_acela_pcs, tbl_engr.engr_das_24v_uplink_coeff_acela_aws, tbl_engr.engr_lte_in_revb_dn_k, tbl_engr.engr_lte_in_revb_dn_n, tbl_engr.engr_lte_in_revb_up_, tbl_engr.engr_lte_in_revb_up_n, tbl_engr.engr_lte_out_revb_dn_k, tbl_engr.engr_lte_out_revb_dn_n, tbl_engr.engr_lte_out_revb_up_k, tbl_engr.engr_lte_out_revb_up_n, tbl_engr.engr_lte2_in_revb_dn_k, tbl_engr.engr_lte2_in_revb_dn_n, tbl_engr.engr_lte2_in_revb_up_k, tbl_engr.engr_lte2_in_revb_up_n, tbl_engr.engr_lte2_out_revb_dn_k, tbl_engr.engr_lte2_out_revb_dn_n, tbl_engr.engr_lte2_out_revb_up_k, tbl_engr.engr_lte2_out_revb_up_n, tbl_engr.engr_pcs_in_revb_dn_k, tbl_engr.engr_pcs_in_revb_dn_n, tbl_engr.engr_pcs_in_revb_up_k, tbl_engr.engr_pcs_in_revb_up_n, tbl_engr.engr_pcs_out_revb_dn_k, tbl_engr.engr_pcs_out_revb_dn_n, tbl_engr.engr_pcs_out_revb_up_k, tbl_engr.engr_pcs_out_revb_up_n, tbl_engr.engr_pilot_aws_out_revb_dn_k, tbl_engr.engr_pilot_aws_out_revb_dn_n, tbl_engr.engr_pilot_aws_out_revb_up_k, tbl_engr.engr_pilot_aws_out_revb_up_n, tbl_engr.engr_pilot_cell_out_revb_dn_, tbl_engr.engr_pilot_cell_out_revb_dn_n, tbl_engr.engr_pilot_cell_out_revb_up_k, tbl_engr.engr_pilot_cell_out_revb_up_n, tbl_engr.engr_pilot_pcs_out_revb_dn_k, tbl_engr.engr_pilot_pcs_out_revb_dn_n, tbl_engr.engr_pilot_pcs_out_revb_up_k, tbl_engr.engr_pilot_pcs_out_revb_up_n, tbl_engr.engr_scan_rcvr_cal_1, tbl_engr.engr_scan_rcvr_cal_2, tbl_engr.engr_scan_rcvr_cal2_0, tbl_engr.engr_scan_rcvr_cal2_1, tbl_engr.engr_scan_rcvr_cal2_2, tbl_engr.engr_scan_rcvr_cell_cal_0, tbl_engr.engr_scan_rcvr_cell_cal_1, tbl_engr.engr_scan_rcvr_cell_cal_2, tbl_engr.engr_scan_rcvr_lte_cal_0, tbl_engr.engr_scan_rcvr_lte_cal_1, tbl_engr.engr_scan_rcvr_lte_cal_2, tbl_engr.engr_smr_in_revb_dn_k, tbl_engr.engr_smr_in_revb_dn_n, tbl_engr.engr_smr_in_revb_up_k, tbl_engr.engr_smr_in_revb_up_n, tbl_engr.engr_smr_out_revb_dn_k, tbl_engr.engr_smr_out_revb_dn_n, tbl_engr.engr_smr_out_revb_up_k, tbl_engr.engr_smr_out_revb_up_n, tbl_engr.engr_temp_gain_dn_k, tbl_engr.engr_temp_gain_dn_n, tbl_engr.engr_temp_gain_up_k, tbl_engr.engr_temp_gain_up_n, tbl_engr.engr_temp_gain1_dn_k, tbl_engr.engr_temp_gain1_dn_n, tbl_engr.engr_temp_gain1_up_k, tbl_engr.engr_temp_gain1_up_n, tbl_pilot_2.pilot_a3_calvalue_cell, tbl_pilot_2.pilot_a3_calvalue_pcs, tbl_pilot_2.pilot_a3_calvalue_aws from tbl_identity inner join tbl_link_2 on tbl_identity.ID = tbl_link_2.ID inner join tbl_caldata_2 on tbl_link_2.ID = tbl_caldata_2.ID inner join tbl_engr on tbl_caldata_2.ID = tbl_engr.ID inner join tbl_pilot_2 on tbl_engr.ID = tbl_pilot_2.ID into outfile 'C:/Webserver/config_2Band_withPilot.csv' fields terminated by ', ' lines terminated by '\n'";
					stmt.executeQuery(query2);
					System.out.println("The config_2Band.csv file is created.");
				}


				catch (SQLException ex) {
					Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
					lgr.log(Level.SEVERE, ex.getMessage(), ex);
				}
				finally {
					try {
						if (connection != null) {
							connection.close();
						}
						if (stmt != null) {
							stmt.close();
						}

					}
					catch (SQLException ex) {
						Logger lgr = Logger.getLogger(ProcessFiles.class.getName());
						lgr.log(Level.WARNING, ex.getMessage(), ex);
					}
				}

			}
			catch (ClassNotFoundException e) {
				System.out.println("Where is your MySQL JDBC Driver?");
				e.printStackTrace();
			}	
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}	

}
