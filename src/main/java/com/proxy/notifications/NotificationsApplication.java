package com.proxy.notifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

import com.proxy.notifications.configuration.CfgInputOutput;
import com.proxy.notifications.configuration.StartArgs;
import com.proxy.notifications.configuration.variable.Global;
import com.proxy.notifications.jwt.RsaKeyProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication(scanBasePackages={"com.proxy.notifications"}
		//,exclude = {SecurityAutoConfiguration.class}
)
@AutoConfigureBefore(CacheAutoConfiguration.class)
@EnableCaching
public class NotificationsApplication {
	
	public static void main(String[] args) {
		//cfgInputOutput CfgInputOutput = new cfgInputOutput();
		String strCfgPath = Global.getGstrcfgpath();
		String strFileName = Global.getGstruserlist();
		String strCfgName = Global.getGstrcfgname();
		StartArgs strtArgs = new StartArgs();
       // List<String[]> stlArgsList = strtArgs.getStlArgsList();
		CfgInputOutput.createFile(strCfgPath, strFileName);
		if (args.length <= 0) {
			try {
				new SpringApplicationBuilder(NotificationsApplication.class)
		        .properties(CfgInputOutput.props(
		        		Global.getGstrcfgpath(), 
						Global.getGstrcfgname()
						))
		        .web(WebApplicationType.SERVLET)
		        .build()
		        .run(args);
			}catch(Exception e)
			{
				int i = 0;
				System.out.println("change config. startargument -c");
				while(i == 0) {
					
				}
			}
		}else {
			strtArgs.pickStartArgs(args);
		}		
			
		
	}
	

}
