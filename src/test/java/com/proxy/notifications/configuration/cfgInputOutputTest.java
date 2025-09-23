package com.proxy.notifications.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.AssertTrue;

class cfgInputOutputTest {

	CfgInputOutput tstCfg;
	Path tstFolder = Paths.get(System.getProperty("user.dir"),"JUnit");
	Path fileTxt = Paths.get(System.getProperty("user.dir"),"JUnit","testtext.txt");
	Path fileIni = Paths.get(System.getProperty("user.dir"),"JUnit","test.ini");
	Path fileUser = Paths.get(System.getProperty("user.dir"),"JUnit","testUser.csv");
	@BeforeEach
	void setUp() throws Exception {
		tstCfg = new CfgInputOutput();
		try {
			
			Files.delete(fileUser);
			Files.delete(fileTxt);
			Files.delete(fileIni);
		} catch (IOException e) {
		}
	}
	@AfterEach
	void setDown() throws Exception{
		try {
			Files.delete(fileUser);
			Files.delete(fileTxt);
			Files.delete(fileIni);
			FileUtils.deleteDirectory(new File(tstFolder.toString()));
		} catch (IOException e) {
		}
	}

	@Test
	void testCreateFile() {
		assertTrue(CfgInputOutput.createFile(tstFolder.toString(), "testtext.txt"));
		try {
			Files.deleteIfExists(fileTxt);
		} catch (IOException e) {
		}
	//	fail("Not yet implemented");
	}

	@Test
	void testBlankIni() {
		CfgInputOutput.createFile(tstFolder.toString(), "test.ini");
		assertTrue(CfgInputOutput.blankIni(tstFolder.toString(), "test.ini"));
		//fail("Not yet implemented");
	}

	@Test
	void testFillIni() {
		CfgInputOutput.createFile(tstFolder.toString(), "test.ini");
		//tstCfg.fillIni(tstFolder, "test.ini");
		assertTrue(Files.exists(Paths.get(tstFolder.toString(), "test.ini")));
		//fail("Not yet implemented");
	}

	@Test
	void testExitApp() {
		System.out.println("can't be implemented yet");
	}

	@Test
	void testProps() {
		CfgInputOutput.createFile(tstFolder.toString(), "test.ini");
		CfgInputOutput.blankIni(tstFolder.toString(), "test.ini");
		assertNull(CfgInputOutput.props(tstFolder.toString(),"test.ini").getProperty("url"));
		//fail("Not yet implemented");
	}

	@Test
	void testAddUserFile() {
		CfgInputOutput.createFile(tstFolder.toString(), "testUser.csv");
		CfgInputOutput.addUserFile("max", "password", tstFolder.toString(), "testUser.csv");
		assertFalse(CfgInputOutput.addUserFile("max", "password", tstFolder.toString(), "testUser.csv"));
		
		//fail("Not yet implemented");
	}

	@Test
	void testRemoveUserFile() {
		CfgInputOutput.createFile(tstFolder.toString(), "testUser.csv");
		CfgInputOutput.addUserFile("max", "password", tstFolder.toString(), "testUser.csv");
		assertTrue(CfgInputOutput.removeUserFile("max", tstFolder.toString(), "testUser.csv"));
	}

	@Test
	void testExistsUserFile() {
		CfgInputOutput.createFile(tstFolder.toString(), "testUser.csv");
		CfgInputOutput.addUserFile("max", "password", tstFolder.toString(), "testUser.csv");
		assertTrue(CfgInputOutput.existsUserFile("max", "password", tstFolder.toString(), "testUser.csv"));
	}

	@Test
	void testGetUserList() {
		CfgInputOutput.createFile(tstFolder.toString(), "testUser.csv");
		CfgInputOutput.addUserFile("max", "password", tstFolder.toString(), "testUser.csv");
		assertEquals(1,CfgInputOutput.getUserList(tstFolder.toString(), "testUser.csv").size());
		//fail("Not yet implemented");
	}


}
