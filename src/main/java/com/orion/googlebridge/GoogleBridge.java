package com.orion.googlebridge;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.orion.googlebridge.Calendar.impl.CalendarBridgeImpl;

public abstract class GoogleBridge {

	protected HttpTransport httpTransport = null;
	protected JsonFactory jsonFactory = null;
	protected String APPLICATION_NAME = null;
	protected static final String DATE_PATTERN = "dd-MM-yyyy HH:mm:ss";
	
	/**
	 * Costruttore del <code>Calendar Bridge</code> Inizializza il nome dell'applicazione, 
	 * il <code>GoogleNetHttpTransport</code> e il  <code>jsonFactory</code>
	 * @param applicationName
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public GoogleBridge(String applicationName) throws GeneralSecurityException, IOException {
		// Set up the HTTP transport and JSON factory
		this.httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		this.jsonFactory = JacksonFactory.getDefaultInstance();
		this.APPLICATION_NAME = applicationName;
	}
	
	/**
	 * Metodo per l'autenticazione su uno dei servizi gestiti dalle API Google,
	 * in caso di errore di validazione bisogna controllare che la applicazione 
	 * sia registrata nella <a href="https://console.developers.google.com">Google Developer Console </a>  
	 * @param dir
	 * @return Credential
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public Credential authentication(String dir) throws GeneralSecurityException, IOException {
		java.io.File DATA_STORE_DIR = new java.io.File(dir);
		FileDataStoreFactory dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);

		// Load client secrets
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(CalendarBridgeImpl.class.getResourceAsStream("/client_secret.json")));

		// Set up authorization code flow
		GoogleAuthorizationCodeFlow flow = getFlow(dataStoreFactory, clientSecrets);

		// Authorize
		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		return credential;
	}
	
	/**
	 * Effettua il Build di <code>FileDataStoreFactory</code> <code>GoogleClientSecrets</code>
	 * @param dataStoreFactory
	 * @param clientSecrets
	 * @return
	 * @throws IOException
	 */
	abstract protected GoogleAuthorizationCodeFlow getFlow(FileDataStoreFactory dataStoreFactory, GoogleClientSecrets clientSecrets) throws IOException;

}
