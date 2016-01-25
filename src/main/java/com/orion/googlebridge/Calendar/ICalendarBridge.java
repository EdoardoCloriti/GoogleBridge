package com.orion.googlebridge.Calendar;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.calendar.Calendar;

/**
 * Interfaccia comune per la gestione dei calendari di Google con più tecnologie
 * 
 * @author Edoardo
 * 
 */
public interface ICalendarBridge {

	// Authenticazione
	public Credential authentication(String dir) throws GeneralSecurityException, IOException;
	// Creazione dei servizi per i Google Calendar
	public Calendar getService(Credential credential);

	public boolean existCalendar(Calendar service, String summary) throws IOException;

	public void printSummary(Calendar service) throws IOException;

	public void addCalendar(Calendar service, String summary, String description, String timeZone, String location) throws IOException;

	public void removeCalendar(Calendar service, String summary) throws IOException;

	public void setACL(Calendar service, String summary, String user, String role) throws IOException;

	public void removeACL(Calendar service, String summary, String user, String role) throws IOException;

	public void insertEvent(Calendar service, String summary, String eventSummary, String location, String description, String startDate, String endDate, String timeZone, String visibility, List<String> attendeesList, List<String> remiderList, List<String> recurrence) throws IOException, ParseException;

}
