package com.orion.googlebridge.gmail;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.gmail.Gmail;

public interface IGMailBridge {

	public Credential authentication(String dir) throws GeneralSecurityException, IOException;

	public Gmail getService(Credential credential);
}
