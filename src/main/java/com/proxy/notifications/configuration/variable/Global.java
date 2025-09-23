package com.proxy.notifications.configuration.variable;

import java.nio.file.Paths;
public class Global {
	//files
	private static final String gStrCfgPath 	= Paths.get(System.getProperty("user.dir"),"configuration").toString();
	private static final String gStrCfgName 	= "vsystem.ini";
	private static final String gStrUserList 	= "userlist.csv";
	private static final String gStrPubKey	 	= "publickey.pem";
	private static final String gStrPrvKey	 	= "privatekey.pem";
	
	//sql
	public static final String gStrSQLGetSpecificSettings = 
			"SELECT * from settings where last_Change_By like 'kevin'";
	//variable contents
	//main
	private static final String[] gStrNotAddUser 		= {"-a","--add","if not exists add a user. -a username password","3"};
	private static final String[] gStrNotShowUser 		= {"-s","--show","print a list of every user","1"};
	private static final String[] gStrNotRemoveUser 	= {"-r","--remove","if a user exists remove from list","2"};
	private static final String[] gStrNotConfigure 		= {"-c","--configuration","configure the ini","1"};
	private static final String[] gStrNotHelp 			= {"-h","--help","show commands","1"};
	private static final String gStrNotFlddUserfileCreate 	= "userlist not created";
	private static final String gStrNotUserfileCreate 		= "userlist created";
	private static final String gStrNotShowUsersName 		= "user: ";
	private static final String gStrNotShowUsersPassword	= "; password: ";
	private static final String gStrNotArgumentsAmount	= "arguments required: ";
	//cfg
	private static final String gStrCfgStrtCat			= "category:";
	private static final String gStrCfgStrtObj			= "object:";
	private static final String gStrCfgStrtDesc			= "description:";
	private static final String gStrCfgStrtExp			= "example:";
	private static final String gStrCfgStrtCur			= "currently:";
	private static final String gStrCfgStrtKpEmp		= "keep empty to keep value. else type something in";
	private static final String gStrCfgStrtInp			= "Input:";
	private static final String gStrCfgStrtExt			= "closing app";
	
	private static final String gStrCfgDirNotFound 		= "directory configuration doesn't exist. creating";
	private static final String gStrCfgDirCreated 		= "directory configuration doesn't exist. creating";
	private static final String gStrCfgDirNotCreated 	= "directory configuration doesn't exist. creating";
	private static final String gStrCfgDirExists 		= "directory exists.";
	private static final String gStrCfgCfgExists 		= "Configuration file exists.";
	private static final String gStrCfgCfgNotExists 	= "Configuration file does not exist.";
	private static final String gStrCfgCfgCreated 		= "Configuration created";
	private static final String gStrCfgCfgCouldNotCreate= "Configuration could not be created";
	private static final String gStrCfgCfgWriteToEmpty	= "Writing empty stuff to ini if empty";
	private static final String gStrCfgCfgEmptyAddValues= "'$s1' is empty and requires values";
	private static final String gStrCfgCfgInvalidFormat	= "invalid file format";
	private static final String gStrCfgCfgNotFound		= "file at '$s1' not found or incomplete";
	private static final String gStrCfgUserAdded		= "user '$s1' has been added";
	private static final String gStrCfgUserNotAdded		= "user '$s1' has not been added";
	private static final String gStrCfgUserAddedError	= "error on adding";
	private static final String gStrCfgError			= "error";
	private static final String gStrCfgUserDeleted		= "user '$s1' has been deleted";
	private static final String gStrCfgUserNotDeleted	= "user '$s1' has not been deleted";
	private static final String gStrCfgUserExists		= "user '$s1' exists";
	private static final String gStrCfgAuthenticated	= "authenticated";
	private static final String gStrCfgNotAuthenticated	= "not authenticated";
	private static final String gStrCfgAuthNameUnknown	= "unknown";
	//webSecurity
	private static final String gStrCfgTokenAuthFailed = "token validation failed ";
	public static String getGstrcfgtokenauthfailed() {
		return gStrCfgTokenAuthFailed;
	}
	public static String getGstrcfgauthnameunknown() {
		return gStrCfgAuthNameUnknown;
	}
	public static String getGstrcfgauthenticated() {
		return gStrCfgAuthenticated;
	}
	public static String getGstrcfgnotauthenticated() {
		return gStrCfgNotAuthenticated;
	}
	public static String getGstrcfguserexists() {
		return gStrCfgUserExists;
	}
	public static String getGstrcfguserdeleted() {
		return gStrCfgUserDeleted;
	}
	public static String getGstrcfgusernotdeleted() {
		return gStrCfgUserNotDeleted;
	}
	public static String getGstrcfgerror() {
		return gStrCfgError;
	}
	public static String getGstrcfguseraddederror() {
		return gStrCfgUserAddedError;
	}
	public static String getGstrcfguseradded() {
		return gStrCfgUserAdded;
	}
	public static String getGstrcfgusernotadded() {
		return gStrCfgUserNotAdded;
	}
	public static String getGstrcfgcfgnotfound() {
		return gStrCfgCfgNotFound;
	}
	public static String getGstrcfgcfginvalidformat() {
		return gStrCfgCfgInvalidFormat;
	}
	public static String getGstrcfgcfgwritetoempty() {
		return gStrCfgCfgWriteToEmpty;
	}
	public static String getGstrcfgpath() {
		return gStrCfgPath;
	}
	public static String getGstrcfgname() {
		return gStrCfgName;
	}
	public static String getGstruserlist() {
		return gStrUserList;
	}
	public static String getGstrsqlgetspecificsettings() {
		return gStrSQLGetSpecificSettings;
	}
	public static String[] getGstrnotadduser() {
		return gStrNotAddUser;
	}
	public static String[] getGstrnotshowuser() {
		return gStrNotShowUser;
	}
	public static String[] getGstrnotremoveuser() {
		return gStrNotRemoveUser;
	}
	public static String[] getGstrnotconfigure() {
		return gStrNotConfigure;
	}
	public static String[] getGstrnothelp() {
		return gStrNotHelp;
	}
	public static String getGstrnotfldduserfilecreate() {
		return gStrNotFlddUserfileCreate;
	}
	public static String getGstrnotuserfilecreate() {
		return gStrNotUserfileCreate;
	}
	public static String getGstrnotshowusersname() {
		return gStrNotShowUsersName;
	}
	public static String getGstrnotshowuserspassword() {
		return gStrNotShowUsersPassword;
	}
	public static String getGstrnotargumentsamount() {
		return gStrNotArgumentsAmount;
	}
	public static String getGstrcfgstrtcat() {
		return gStrCfgStrtCat;
	}
	public static String getGstrcfgstrtobj() {
		return gStrCfgStrtObj;
	}
	public static String getGstrcfgstrtdesc() {
		return gStrCfgStrtDesc;
	}
	public static String getGstrcfgstrtexp() {
		return gStrCfgStrtExp;
	}
	public static String getGstrcfgstrtcur() {
		return gStrCfgStrtCur;
	}
	public static String getGstrcfgstrtkpemp() {
		return gStrCfgStrtKpEmp;
	}
	public static String getGstrcfgstrtinp() {
		return gStrCfgStrtInp;
	}
	public static String getGstrcfgstrtext() {
		return gStrCfgStrtExt;
	}
	public static String getGstrcfgdirnotfound() {
		return gStrCfgDirNotFound;
	}
	public static String getGstrcfgdircreated() {
		return gStrCfgDirCreated;
	}
	public static String getGstrcfgdirnotcreated() {
		return gStrCfgDirNotCreated;
	}
	public static String getGstrcfgdirexists() {
		return gStrCfgDirExists;
	}
	public static String getGstrcfgcfgexists() {
		return gStrCfgCfgExists;
	}
	public static String getGstrcfgcfgnotexists() {
		return gStrCfgCfgNotExists;
	}
	public static String getGstrcfgcfgcreated() {
		return gStrCfgCfgCreated;
	}
	public static String getGstrcfgcfgcouldnotcreate() {
		return gStrCfgCfgCouldNotCreate;
	}
	public static String getGstrcfgcfgemptyaddvalues() {
		return gStrCfgCfgEmptyAddValues;
	}
	public static String getGstrpubkey() {
		return gStrPubKey;
	}
	public static String getGstrprvkey() {
		return gStrPrvKey;
	}
	
}
