package com.proxy.notifications.configuration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.h2.Driver;
import com.proxy.notifications.configuration.variable.Global;
import com.proxy.notifications.jwt.JwtUtils;

import io.jsonwebtoken.lang.Arrays;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CfgInputOutput {
	public static boolean createFile(
			String strPath, 
			String strFilename
	) {
		String strCfgPath = strPath.toString();
		String strUserListPath = strFilename;
		File checkExists = new File(strCfgPath);
		if (!(checkExists.exists() && checkExists.isDirectory())) {
			System.out.println(Global.getGstrcfgdirnotfound());
			try {
				Files.createDirectories(
										Paths.get(
												strCfgPath)
										);
				System.out.println(Global.getGstrcfgdircreated());
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println(Global.getGstrcfgdirnotcreated());
				return false;
			}
		}else {
			System.out.println(Global.getGstrcfgdirexists());
		}
		checkExists = new File(
								Paths.get(
									strCfgPath,
									strUserListPath
									).toString()
								);
		if (!checkExists.exists()) {
			try {
				System.out.println(Global.getGstrcfgcfgnotexists());
				Files.createFile(
						Paths.get(
								strCfgPath,
								strUserListPath)
						);
				System.out.println(Global.getGstrcfgcfgcreated());
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println(Global.getGstrcfgcfgcouldnotcreate());
				return false;
			}
		}
		return true;
	}
	public static boolean blankIni(
			String strPath, 
			String strFilename
	) {
		String strDirPath = strPath;
		String strFileName = strFilename;
		String strFilePath = Paths.get(
									strDirPath, 
									strFileName)
									.toString();
		try {
			System.out.println(Global.getGstrcfgcfgwritetoempty());
			Ini ini = new Ini(
							new File(strFilePath)
							);
			ini.put("Database", "url","");
			ini.put("Database", "username","");
			ini.put("Database", "password","");
			ini.put("Database", "driverClassName","");
			ini.put("Database", "dialect","");
			ini.put("Database", "show-sql","");
			ini.put("Database", "ddl-auto","");
			//ini.put("Authentication", "provider","");
			//ini.put("Authentication", "clientId","");
			//ini.put("Authentication", "clientSecret","");
			ini.put("Server", "port","");
			ini.put("Server", "logLevelRoot","");
			ini.put("Server", "logLevelSpring","");
			ini.put("Server", "logLevelHibernate","");
			ini.put("Server", "showSQLQueries","");
			ini.put("Server", "AllowedOrigins","");
			ini.store();
			return true;
		} catch (NullPointerException e1) {
			System.out.println(
				Global.getGstrcfgcfgemptyaddvalues()
							.replace(
									"$s1", 
									strFilePath
									)
							);
			return false;
		} catch(InvalidFileFormatException e1) {
			System.out.println(Global.getGstrcfgcfginvalidformat());
			return false;
		} catch (IOException e1) {
			System.out.println(Global.getGstrcfgcfgnotfound()
								.replace(
										"$s1", 
										Paths.get(
												strPath,
												strFilename
												).toString()
										)
					);
			return false;
		}
	}
	
	public static void fillIni(
					String strPath, 
					String strFilename
	) {
		List<String[]> stlArgsList = new ArrayList<String[]>();
		
		Ini ini;
		try {
			String strDirPath = strPath;
			String strFileName = strFilename;
			String strFilePath = Paths.get(
											strDirPath, 
											strFileName
											).toString();
			Scanner scnUserInput = new Scanner(System.in);
			String strUserInput = "";
			ini = new Ini(new File( strFilePath));
			stlArgsList.add(new String[] {"Database",	"url",				"sql ip address",								"jdbc:sqlserver://192.168.0.x:8080;databaseName=myDatabase;trustServerCertificate=true",ini.get("Database","url")});
			stlArgsList.add(new String[] {"Database",	"username",			"username to log into the database server",		"ADMIN",ini.get("Database","username")});
			stlArgsList.add(new String[] {"Database",	"password",			"password to log into the database server",		"***",ini.get("Database","password")});
			stlArgsList.add(new String[] {"Database",	"driverClassName",	"driver to be used",							"com.microsoft.sqlserver.jdbc.SQLServerDriver",ini.get("Database","driverClassName")});
			stlArgsList.add(new String[] {"Database",	"dialect",			"component for converting database objects into hibernate objects", "org.hibernate.dialect.SQLServer2012Dialect",ini.get("Database","dialect")});
			stlArgsList.add(new String[] {"Database",	"show-sql",			"show sql queries in console",					"\n'true' = show; \n'false' = dont show;",ini.get("Database","show-sql")});
			stlArgsList.add(new String[] {"Database",	"ddl-auto",			"how hibernate manages the tables etc.",		"'none' = no database changes; \n'validate' = check if database shema matches hibernate. if not, change nothing; \n'update' = automatically change database shema to hibernate; \n'create' = drop previous and create new on app start; \n'create-drop' = same as create, but drops at shutdown",ini.get("Database","ddl-auto")});
			//stlArgsList.add(new String[] {"Authentication","provider",		"Oauth2 provider",								"github",ini.get("Authentication","provider")});
			//stlArgsList.add(new String[] {"Authentication","clientId",		"oauth2 client id",								"github",ini.get("Authentication","clientId")});
			//stlArgsList.add(new String[] {"Authentication","clientSecret",	"Oauth2 client secret",							"github",ini.get("Authentication","clientSecret")});
			stlArgsList.add(new String[] {"Server",		"port",				"port for server to be accessed from",			"80",ini.get("Server","port")});
			stlArgsList.add(new String[] {"Server",		"logLevelRoot",		"global logging level",							"'OFF' = none; \n'FATAL' = only fatal; \n'ERROR' = only error; \n'WARN' = only warnings; \n'INFO' = only information messages; \n'DEBUG' = detailled debug messages; \n'TRACE' = detailled general informations; \n'ALL' = everything",ini.get("Server","logLevelRoot")});
			stlArgsList.add(new String[] {"Server",		"logLevelSpring",	"spring web related logging level",				"'OFF' = none; \n'FATAL' = only fatal; \n'ERROR' = only error; \n'WARN' = only warnings; \n'INFO' = only information messages; \n'DEBUG' = detailled debug messages; \n'TRACE' = detailled general informations; \n'ALL' = everything",ini.get("Server","logLevelSpring")});
			stlArgsList.add(new String[] {"Server",		"logLevelHibernate","Hibernate related logging level",				"'OFF' = none; \n'FATAL' = only fatal; \n'ERROR' = only error; \n'WARN' = only warnings; \n'INFO' = only information messages; \n'DEBUG' = detailled debug messages; \n'TRACE' = detailled general informations; \n'ALL' = everything",ini.get("Server","logLevelHibernate")});			
			stlArgsList.add(new String[] {"Server",		"AllowedOrigins",	"Frontend IPs and Ports. Comma seperated",		"http://0.0.0.0:5173,http://192.168.0.123:5173, http://localhost:5173"});			
			for(int i = 0; i<stlArgsList.size();i++)
			{
				String[] stlLine = stlArgsList.get(i);
				System.out.println(Global.getGstrcfgstrtcat() +stlLine[0]);
				System.out.println(
								"\n"+
								Global.getGstrcfgstrtobj()+
								stlLine[1]
								);
				System.out.println(
								"\n"+
								Global.getGstrcfgstrtdesc()+
								"\n"+
								stlLine[2]
								);
				System.out.println(
								"\n"+
								Global.getGstrcfgstrtexp()+
								stlLine[3]
								);
				System.out.println(
								"\n"+
								Global.getGstrcfgstrtcur()+
								stlLine[4]
								);
				System.out.println(
								"\n\n"+
								Global.getGstrcfgstrtkpemp()
								);
				strUserInput = "";
				System.out.print(Global.getGstrcfgstrtinp());
				strUserInput = scnUserInput.nextLine();
				/*var res = strUserInput.isEmpty()?
						ini.put(
								stlLine[0], 
								stlLine[1],
								stlLine[4]
								):
						ini.put(
								stlLine[0], 
								stlLine[1],
								strUserInput
								);*/
			}
			ini.put(
					"Server", 
					"showSQLQueries",
					Boolean.valueOf(stlArgsList.get(10)[4])
					);
			ini.store();
			scnUserInput.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public static void exitApp() {
		System.out.println(
				Global.getGstrcfgstrtext()
				);
		System.exit(0);
	}
	public static Properties props(
							String path, 
							String iniName
							) {
	    Properties properties = new Properties();
		try {
			Ini ini = new Ini(
						new File(
							Paths.get(
									path, 
									iniName
							).toString()
						)
			);
			if(ini.get("Server", "test") == null)
			{
				System.out.println("normal database");
				properties.setProperty("spring.datasource.url",ini.get("Database", "url"));
				properties.setProperty("spring.datasource.username",ini.get("Database","username"));
				properties.setProperty("spring.datasource.password",ini.get("Database","password"));
				properties.setProperty("spring.datasource.driverClassName",ini.get("Database","driverClassName"));
				properties.setProperty("spring.jpa.hibernate.dialect",ini.get("Database","dialect"));
				properties.setProperty("spring.jpa.show-sql",ini.get("Database","show-sql"));
				properties.setProperty("spring.jpa.hibernate.ddl-auto",ini.get("Database","ddl-auto"));
				properties.setProperty("server.port",ini.get("Server","port"));
			}
			else
			{
				System.out.println("h2 database");
				properties.setProperty("spring.datasource.url","jdbc:h2:mem:testdb");
				properties.setProperty("spring.datasource.username","sa");
				properties.setProperty("spring.datasource.password","");
				properties.setProperty("spring.datasource.driverClassName","org.h2.Driver");
				properties.setProperty("spring.jpa.hibernate.dialect","org.hibernate.dialect.H2Dialect");
				properties.setProperty("spring.jpa.hibernate.ddl-auto","update");
				properties.setProperty("server.port","80");
			}
			properties.setProperty("logging.level.root",ini.get("Server","logLevelRoot"));
			properties.setProperty("logging.level.org.springframework.web",ini.get("Server","logLevelSpring"));
			properties.setProperty("logging.level.org.hibernate",ini.get("Server","logLevelHibernate"));
			properties.setProperty("spring.jpa.show-sql",ini.get("Server","showSQLQueries"));
			properties.setProperty("spring.datasource.hikari.connectionTimeout","30000");
			properties.setProperty("spring.datasource.hikari.idleTimeout","600000");
			properties.setProperty("spring.datasource.hikari.maxLifetime","1800000");
			//podman
			properties.setProperty("server.address","0.0.0.0");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(Global.getGstrcfgcfgnotfound()
											.replace(
													"$s1", 
													Paths.get(
															Global.getGstrcfgpath(),
															Global.getGstrcfgname()
															).toString()
													)
			);
			String strPath = Global.getGstrcfgpath();
			String strFileName = Global.getGstrcfgname();
			createFile(strPath,strFileName);
			exitApp();
		}
	    return properties;
	  }
	
	public static boolean addUserFile(
			String username, 
			String password, 
			String strCfgPath, 
			String strFileName
	) {
		String strFilePath = Paths.get(
									strCfgPath, 
									strFileName
									).toString(); 			
		try {
			if(!existsUserFile(
					username, 
					password, 
					strCfgPath,
					strFileName)
					)
			{
				FileWriter fw = new FileWriter(strFilePath, true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    bw.write(username+";"+BCrypt.hashpw(
			    								password,
			    								BCrypt.gensalt(12)
			    								)
			    );
			    bw.newLine();
			    bw.close();
			    System.out.println(Global.getGstrcfguseradded()
			    							.replace(
			    									"$s1", 
			    									username
			    									)
			    );
			    return true;
			}else {

			    System.out.println(Global.getGstrcfgusernotadded()
											.replace(
													"$s1", 
													username
													)
			    );
			    return false;
			}
		}catch(IOException E) {

			System.out.println(Global.getGstrcfguseraddederror());
		}
		return false;
	}
	
	public static boolean removeUserFile(
			String username, 
			String strCfgPath, 
			String strFileName
	) {
		String filePath = Paths.get(
								strCfgPath, 
								strFileName
								).toString();
		File flOrig = new File(filePath);
		File flTmp = new File(filePath+".tmp");
		boolean bolUserFound = false;
		try {
			BufferedReader readOrig = new BufferedReader(new FileReader(flOrig));
			ArrayList<String> lines = new ArrayList<>();
			String line;
			
			while((line = readOrig.readLine()) != null) {
				if (!line.split(";")[0].equals(username)) {
					
					lines.add(line);
				}else {
					bolUserFound = true;
					
				}
			}
			readOrig.close();
			BufferedWriter writeTemp = new BufferedWriter(new FileWriter(flTmp));
			for(String l: lines)
			{
				writeTemp.write(l);
				writeTemp.newLine();
			}
			writeTemp.close();
			if(flOrig.delete())
			{
				flTmp.renameTo(flOrig);
			}else {
				System.out.println(Global.getGstrcfgerror());
			}
			if(bolUserFound)
			{
				System.out.println(Global.getGstrcfguserdeleted()
						.replace(
								"$s1", 
								username
								)
						);
			}else
			{
				System.out.println(Global.getGstrcfgusernotdeleted()
						.replace(
								"$s1", 
								username
								)
						);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bolUserFound;
	}
	
	public static boolean existsUserFile(
			String username, 
			String password, 
			String strCfgPath, 
			String strFileName
	) {
		String strFilePath = Paths.get(strCfgPath, strFileName).toString(); 
		try {
			FileReader FR = new FileReader(strFilePath);
			BufferedReader BR = new BufferedReader(FR);
			String line;
			String[] userList;
			while((line = BR.readLine()) != null) {
				userList = line.split(";");
				if (userList[0].trim().equals(username))
				{
					System.out.println(Global.getGstrcfguserexists()
							.replace(
									"$s1", 
									username
									)
							);
					return true;
				}
			}
		return false;
		}catch(IOException E)
		{
			return false;
		}
	}
	public static List<String[]> getUserList(
			String strCfgPath, 
			String strFileName
	)
	{	
		List<String[]> stllUserList = new ArrayList<String[]>();
		String strFilePath = Paths.get(strCfgPath, strFileName).toString(); 
		try {
			FileReader FR = new FileReader(strFilePath);
			BufferedReader BR = new BufferedReader(FR);
			String line;
			String[] strUserList;
			int i = 0;
			while((line = BR.readLine()) != null) {
				strUserList = line.split(";");
				stllUserList.add(strUserList);
			}

			return stllUserList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void logRequests(
			HttpServletRequest request,
			Authentication authentication
	) throws ServletException, IOException {
		String strMMethod = request.getMethod();
		String strMUri = request.getRequestURI();
		String strMQuery = request.getQueryString();
//		System.out.println(authentication);
		String strMName;
		String strMAuthenticated = Global.getGstrcfgnotauthenticated();;// = Global.getGstrcfgnotauthenticated();
		if(authentication != null)
		{
			strMName = !authentication.getName().startsWith("ey") ? authentication.getName():new JwtUtils().extractUsername(authentication.getName());
			strMAuthenticated = authentication.isAuthenticated()?Global.getGstrcfgauthenticated():Global.getGstrcfgnotauthenticated();
		}else if(request.getHeader("Authorization") != null)
		{
			strMName = request.getHeader("Authorization").startsWith("Bearer ey")?new JwtUtils().extractUsername(request.getHeader("Authorization")):Global.getGstrcfgauthnameunknown();
		}else
		{
			strMName = Global.getGstrcfgauthnameunknown();
		}
		String strMIp = request.getRemoteAddr();
		DateTimeFormatter datetimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		System.out.println(LocalDateTime.now().format(datetimeFormat)+";"+strMIp+";"+strMMethod+";"+strMUri+";"+strMName+";"+strMAuthenticated);
	}
	public static List<String> getCorsOrigin(
			String strPath, 
			String strFilename) 
	{
		Ini ini;
		List<String> strLstCorsOrigin = new ArrayList<String>();
		try {
			String strDirPath = strPath;
			String strFileName = strFilename;
			String strFilePath = Paths.get(
											strDirPath, 
											strFileName
											).toString();
			ini = new Ini(new File( strFilePath));
			String iniS = ini.get("Server", "AllowedOrigins");
			strLstCorsOrigin = Arrays.asList(iniS.split(","));
			//System.out.println(strLstCorsOrigin);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return strLstCorsOrigin;
	}


}
