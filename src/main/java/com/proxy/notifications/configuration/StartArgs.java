package com.proxy.notifications.configuration;

import java.util.ArrayList;
import java.util.List;

import com.proxy.notifications.configuration.variable.Global;

public class StartArgs {
	private List<String[]> stlArgsList;
	public StartArgs()
	{
		stlArgsList = new ArrayList<String[]>();
		stlArgsList.add(Global.getGstrnotadduser());
	    stlArgsList.add(Global.getGstrnotremoveuser());
	    stlArgsList.add(Global.getGstrnotshowuser());
	    stlArgsList.add(Global.getGstrnotconfigure());
	    stlArgsList.add(Global.getGstrnothelp());
	}
	
	public void pickStartArgs(String[] args)
	{
		
			//add
			if (args[0].trim().equals(stlArgsList.get(0)[0]) ||
				args[0].trim().equals(stlArgsList.get(0)[1]) )
			{
				if (!(args.length<Integer.valueOf(stlArgsList.get(0)[3])))
				{
					addUser(args);
				}else
				{
					System.out.println(Global.getGstrnotargumentsamount()+stlArgsList.get(0)[3]);
				}
			}else 
			//remove
			if (args[0].trim().equals(stlArgsList.get(1)[0]) ||
					args[0].trim().equals(stlArgsList.get(1)[1]) )
			{
				if (!(args.length<Integer.valueOf(stlArgsList.get(1)[3])))
				{
					removeUser(args);
				}else
				{
					System.out.println(Global.getGstrnotargumentsamount()+stlArgsList.get(1)[3]);
				}
			}else 
			//show
			if (args[0].trim().equals(stlArgsList.get(2)[0]) ||
				args[0].trim().equals(stlArgsList.get(2)[1]) )
			{
				if (!(args.length<Integer.valueOf(stlArgsList.get(2)[3])))
				{
					showUsers(args);
				}else
				{
					System.out.println(Global.getGstrnotargumentsamount()+stlArgsList.get(2)[3]);
				}
			}else
			//config
			if (args[0].trim().equals(stlArgsList.get(3)[0]) ||
				args[0].trim().equals(stlArgsList.get(3)[1]) )
			{
				config();
			}else	
			//help
			if (args[0].trim().equals(stlArgsList.get(4)[0]) ||
					args[0].trim().equals(stlArgsList.get(4)[1]) )
			{
				if (!(args.length<Integer.valueOf(stlArgsList.get(4)[3])))
				{
					help(args, stlArgsList);
				}else
				{
					System.out.println(Global.getGstrnotargumentsamount()+stlArgsList.get(4)[3]);
				}
			}
	}
	
	private boolean addUser(String[] args)
	{
		boolean userCreated = false;
		String strCfgPath = Global.getGstrcfgpath();
		String strFileName = Global.getGstruserlist();
		if (!CfgInputOutput.createFile(strCfgPath, strFileName))
		{
			System.out.println(Global.getGstrnotfldduserfilecreate());
		}else {
			System.out.println(Global.getGstrnotuserfilecreate());
		}
		CfgInputOutput.addUserFile(args[1],args[2],strCfgPath,strFileName);
		userCreated = true;
		return userCreated;
	}
	private boolean removeUser(String[] args)
	{
		if(CfgInputOutput.removeUserFile(args[1], Global.getGstrcfgpath(), Global.getGstruserlist()))
		{
			return true;
		}else
		{
			return false;
		}
		
	}
	private void config()
	{
		CfgInputOutput.fillIni(Global.getGstrcfgpath(), Global.getGstrcfgname());
	}
	private void showUsers(String[] args)
	{
		List<String[]> userList = CfgInputOutput.getUserList(Global.getGstrcfgpath(), Global.getGstruserlist());
		for (int i = 0; i<userList.size();i++) {
			System.out.println(Global.getGstrnotshowusersname()+userList.get(i)[0]+Global.getGstrnotshowuserspassword()+userList.get(i)[1]+"';");
		}
	}
	private void help(String[] args, List<String[]> stlArgsList)
	{
		for (String[] strArrForLoop : stlArgsList) {
			for (String strForLoop : strArrForLoop) {
				System.out.print(
						strForLoop+"; "
						);
			}
			System.out.println();
		}
	}
	public List<String[]> getStlArgsList() {
		return stlArgsList;
	}
}
