package com.orion.googlebridge.drive;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files.Insert;
import com.google.api.services.drive.model.File;

public interface IDriveBridge {

	public Credential authentication(String dir) throws GeneralSecurityException, IOException;

	public Drive getService(Credential credential);

	public void viewFile(Drive service) throws IOException;

	public Insert createFile(Drive service, String title, String description, String mimeKey, String parentId, java.io.File fileContent) throws IOException;

	public Insert createDirectory(Drive service, String directoryName, String parentId) throws IOException;

	public void generateUserPermission(Drive service, String fieldId, String user, String role, String mail) throws IOException;

	public void generateDomainPermission(Drive service, String fieldId, String user, String role, String domain) throws IOException;

	public File getFileById(Drive service, String id) throws IOException;

	public File getFileByTitle(Drive service, String title) throws IOException;
}
