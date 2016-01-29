package com.orion.googlebridge.drive.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files.Insert;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentReference;
import com.google.api.services.drive.model.Permission;
import com.orion.googlebridge.GoogleBridge;
import com.orion.googlebridge.drive.IDriveBridge;
import com.orion.googlebridge.drive.util.DriveMimeTypeSupported;

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

	public Insert createFile(Drive service, String title, String description, String mimeKey, String parentId, java.io.File fileContent) throws IOException {
		File file = new File();
		file.setTitle(title);
		file.setDescription(description);
		file.setMimeType(DriveMimeTypeSupported.getMimeTypeByKey(mimeKey));
		if (parentId != null && parentId.length() > 0)
			file.setParents(Arrays.asList(new ParentReference().setId(parentId)));
		FileContent content = new FileContent(DriveMimeTypeSupported.getMimeTypeByKey(mimeKey), fileContent);
		return service.files().insert(file, content);

	}

	public Insert createDirectory(Drive service, String directoryName, String parentId) throws IOException {
		File file = new File();
		file.setTitle(directoryName);
		file.setMimeType(DriveMimeTypeSupported.getMimeTypeByKey(DriveMimeTypeSupported.FOLDER));
		if (parentId != null && parentId.length() > 0)
			file.setParents(Arrays.asList(new ParentReference().setId(parentId)));
		return service.files().insert(file);

	}

	public void generateUserPermission(Drive service, String fieldId, String user, String role, String mail) throws IOException {
		JsonBatchCallback<Permission> callback = new JsonBatchCallback<Permission>() {

			public void onSuccess(Permission permission, HttpHeaders headers) throws IOException {
				System.out.println("Permission ID: " + permission.getId());
			}

			@Override
			public void onFailure(GoogleJsonError error, HttpHeaders headers) throws IOException {
				throw new RuntimeException(error.getMessage());
			}
		};
		BatchRequest batch = service.batch();

		Permission userPermission = new Permission();
		userPermission.setType("user");
		userPermission.setRole(role);
		userPermission.setEmailAddress(mail);
		com.google.api.services.drive.Drive.Permissions.Insert insert = service.permissions().insert(fieldId, userPermission);
		insert.setFileId("id");
		insert.queue(batch, callback);
		batch.execute();
	}

	public void generateDomainPermission(Drive service, String fieldId, String user, String role, String domain) throws IOException {
		JsonBatchCallback<Permission> callback = new JsonBatchCallback<Permission>() {

			public void onSuccess(Permission permission, HttpHeaders headers) throws IOException {
				System.out.println("Permission ID: " + permission.getId());
			}

			@Override
			public void onFailure(GoogleJsonError error, HttpHeaders headers) throws IOException {
				throw new RuntimeException(error.getMessage());
			}
		};
		BatchRequest batch = service.batch();

		Permission domainPermission = new Permission();
		domainPermission.setType("domain");
		domainPermission.setRole(role);
		domainPermission.setDomain(domain);
		com.google.api.services.drive.Drive.Permissions.Insert insert = service.permissions().insert(fieldId, domainPermission);
		insert.setFileId("id");
		insert.queue(batch, callback);
		batch.execute();
	}

	public File getFileById(Drive service, String id)throws IOException
	{
		FileList fileList=service.files().list().execute();
		List<File> list = fileList.getItems();
		for(File file: list)
		{
			if(id.equals(file.getId()))
				return file;
		}
		return null;
	}
	
	public File getFileByTitle(Drive service, String title) throws IOException
	{
		FileList fileList = service.files().list().execute();
		List<File> list= fileList.getItems();
		for(File file: list)
		{
			if(title.equals(file.getTitle()))
				return file;
		}
		return null;
	}
	public void viewFile(Drive service) throws IOException {
		FileList fileList = service.files().list().execute();
		List<File> list = fileList.getItems();
		for (File file : list) {
			System.out.println("ID: " + file.getId() + "- Title: " + file.getTitle());
		}
	}

}
