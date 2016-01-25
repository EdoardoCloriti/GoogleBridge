package com.orion.googlebridge.drive;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.Drive;

public interface IDriveBridge {

	public Credential authentication(String dir) throws GeneralSecurityException, IOException;
	
	public Drive getService(Credential credential);
	
	public void viewFile(Drive service) throws IOException;
}
