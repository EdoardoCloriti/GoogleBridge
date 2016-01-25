package com.orion.googlebridge.gmail.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.orion.googlebridge.GoogleBridge;
import com.orion.googlebridge.gmail.IGMailBridge;

public class GMailBridgeImpl extends GoogleBridge implements IGMailBridge {

	public GMailBridgeImpl(String applicationName) throws GeneralSecurityException, IOException {
		super(applicationName);
	}

	@Override
	protected GoogleAuthorizationCodeFlow getFlow(FileDataStoreFactory dataStoreFactory, GoogleClientSecrets clientSecrets) throws IOException {
		return new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, clientSecrets, Collections.singleton(GmailScopes.MAIL_GOOGLE_COM)).setDataStoreFactory(dataStoreFactory).build();
	}

	public Gmail getService(Credential credential) {
		Gmail gmailService = new Gmail.Builder(httpTransport, jsonFactory, credential).setApplicationName(APPLICATION_NAME).build();
		return gmailService;
	}

}
