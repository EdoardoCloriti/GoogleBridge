package com.orion.googlebridge.drive;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files.Insert;
import com.google.api.services.drive.model.File;

public interface IDriveBridge {

	/**
	 * Metodo per l'autenticazione su <code>Google Drive API</code>,
	 * in caso di errore di validazione bisogna controllare che la applicazione 
	 * sia registrata nella <a href="https://console.developers.google.com">Google Developer Console </a>  
	 * @param  dir
	 * @return Credential
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public Credential authentication(String dir) throws GeneralSecurityException, IOException;

	/**
	 * Creazione dei servizi per Google Drive
	 * @param credential
	 * @return
	 */
	public Drive getService(Credential credential);

	/**
	 * Restituisce la lista dei file 
	 * @param service
	 * @throws IOException
	 */
	public void viewFile(Drive service) throws IOException;

	/**
	 * Creazione del File in base ai parametri passati come parametro
	 * @param service
	 * @param title
	 * @param description
	 * @param mimeKey
	 * @param parentId
	 * @param fileContent
	 * @return
	 * @throws IOException
	 */
	public Insert createFile(Drive service, String title, String description, String mimeKey, String parentId, java.io.File fileContent) throws IOException;

	/**
	 * Creazione di una directory
	 * @param service
	 * @param directoryName
	 * @param parentId
	 * @return
	 * @throws IOException
	 */
	public Insert createDirectory(Drive service, String directoryName, String parentId) throws IOException;

	/**
	 * Creazione delle permission per un utente
	 * @param service
	 * @param fieldId
	 * @param user
	 * @param role
	 * @param mail
	 * @throws IOException
	 */
	public void generateUserPermission(Drive service, String fieldId, String user, String role, String mail) throws IOException;

	/**
	 * Creazione delle permissions per un dominio
	 * @param service
	 * @param fieldId
	 * @param user
	 * @param role
	 * @param domain
	 * @throws IOException
	 */
	public void generateDomainPermission(Drive service, String fieldId, String user, String role, String domain) throws IOException;

	/**
	 * Restituisce il file corrispondente all'id passato come parametro
	 * @param service
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public File getFileById(Drive service, String id) throws IOException;

	/**
	 * Restituisce il file corrispondente al titolo passato come parametro
	 * @param service
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public File getFileByTitle(Drive service, String title) throws IOException;
	
	/**
	 * Restituisce l'id corrispondente al file con titolo passato come parametro
	 * @param service
	 * @param title
	 * @return
	 * @throws IOException
	 */
	public String getFileIdByTitle(Drive service, String title) throws IOException;

	/**
	 * elimina il file con l'id passato come parametro
	 * @param service
	 * @param id
	 * @throws IOException
	 */
	public void deleteFile(Drive service, String id) throws IOException;
	
}
