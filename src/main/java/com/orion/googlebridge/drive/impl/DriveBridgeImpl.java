package com.orion.googlebridge.drive.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.orion.googlebridge.GoogleBridge;
import com.orion.googlebridge.drive.IDriveBridge;

public class DriveBridgeImpl extends GoogleBridge implements IDriveBridge {
	
	
	public DriveBridgeImpl(String applicationName) throws GeneralSecurityException, IOException {
		super(applicationName);
	}

	protected GoogleAuthorizationCodeFlow getFlow(FileDataStoreFactory dataStoreFactory, GoogleClientSecrets clientSecrets) throws IOException {
		return new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, clientSecrets, Collections.singleton(DriveScopes.DRIVE)).setDataStoreFactory(dataStoreFactory).build();
	}

	public Drive getService(Credential credential) {
		Drive driveService = new Drive.Builder(httpTransport, jsonFactory, credential).setApplicationName(APPLICATION_NAME).build();
		return driveService;
	}

	public void viewFile(Drive service) throws IOException {
		FileList fileList = service.files().list().setMaxResults(50).execute();
		List<File> list = fileList.getItems();
		for (File file : list) {
			System.out.println("ID: " + file.getId() + "- Title: " + file.getTitle());
		}
	}
}
