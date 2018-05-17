package com.orion.googlebridge.Calendar.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Acl;
import com.google.api.services.calendar.model.AclRule;
import com.google.api.services.calendar.model.AclRule.Scope;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.orion.googlebridge.GoogleBridge;
import com.orion.googlebridge.Calendar.ICalendarBridge;

/**
 * Classe che implementa l'interfaccia <code>ICalendarBridge</code> per la gestione dei Google Calendar
 *  
 * @author Edoardo
 *
 */
public class CalendarBridgeImpl extends GoogleBridge  implements ICalendarBridge {

	
	public CalendarBridgeImpl(String applicationName) throws GeneralSecurityException, IOException {
		super(applicationName);
	}

	protected GoogleAuthorizationCodeFlow getFlow(FileDataStoreFactory dataStoreFactory, GoogleClientSecrets clientSecrets) throws IOException {
		return new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, clientSecrets, Collections.singleton(CalendarScopes.CALENDAR))
				.setDataStoreFactory(dataStoreFactory)
				.build();
	}

	public Calendar getService(Credential credential) {
		Calendar calendarService = new com.google.api.services.calendar.Calendar.Builder(httpTransport, jsonFactory, credential).setApplicationName(APPLICATION_NAME).build();
		return calendarService;
	}

	public boolean existCalendar(Calendar service, String summary) throws IOException {
		String pageToken = null;
		boolean isPresent = false;
		CalendarList Calendarlist = service.calendarList().list().setPageToken(pageToken).execute();
		List<CalendarListEntry> list = Calendarlist.getItems();
		do {
			for (CalendarListEntry entry : list) {
				if (summary.equalsIgnoreCase(entry.getSummary()))
					isPresent = true;
			}
		}
		while (pageToken != null);
		return isPresent;
	}

	private String getIdFromSummary(Calendar service, String summary) throws IOException {
		String pageToken = null;
		CalendarList Calendarlist = service.calendarList().list().setPageToken(pageToken).execute();
		List<CalendarListEntry> list = Calendarlist.getItems();
		do {
			for (CalendarListEntry entry : list) {
				if (summary.equalsIgnoreCase(entry.getSummary()))
					return entry.getId();
			}
		}
		while (pageToken != null);
		return null;
	}

	public void printSummary(Calendar service) throws IOException {
		String pageToken = null;
		CalendarList Calendarlist = service.calendarList().list().setPageToken(pageToken).execute();
		List<CalendarListEntry> list = Calendarlist.getItems();
		do {
			for (CalendarListEntry entry : list) {
				System.out.println(entry.getSummary());
			}
		}
		while (pageToken != null);
	}

	public void addCalendar(Calendar service, String summary, String description, String timeZone, String location) throws IOException {
		if (!existCalendar(service, summary)) {
			com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
			calendar.setSummary(summary);
			if (isNullOrEmpry(description))
				calendar.setDescription(description);
			if (isNullOrEmpry(timeZone))
				calendar.setTimeZone(timeZone);
			if (isNullOrEmpry(location))
				calendar.setLocation(location);
			service.calendars().insert(calendar).execute();
		}
	}

	public void removeCalendar(Calendar service, String summary) throws IOException {
		String id = getIdFromSummary(service, summary);
		if (id != null)
			service.calendarList().delete(id).execute();
	}

	public void setACL(Calendar service, String summary, String user, String role) throws IOException {
		AclRule rule = new AclRule();
		// rule.setId(user);
		Scope scope = new Scope();
		scope.setType("user").setValue(user);
		if (validateRole(role))
			rule.setScope(scope).setRole(role);
		else
			rule.setScope(scope).setRole("none");
		String idCalendar = getIdFromSummary(service, summary);
		if (idCalendar != null)
			service.acl().insert(idCalendar, rule).execute();
	}
	public void removeACL(Calendar service, String summary, String user, String role) throws IOException {
		String id = getIdACL(service, summary, user, role);
		if (id != null) {
			service.acl().delete(getIdFromSummary(service, summary), id);
		}
	}
	private String getIdACL(Calendar service, String summary, String user, String role) throws IOException {
		Acl list = service.acl().list(getIdFromSummary(service, summary)).execute();
		for (AclRule rule : list.getItems()) {

			if (user.equals(rule.getScope().getValue()) && role.equalsIgnoreCase(rule.getRole())) {
				return rule.getId();
			}

		}
		return null;
	}

	public void insertEvent(Calendar service, String summary, String eventSummary, String location, String description, String startDate, String endDate, String timeZone, String visibility, List<String> attendeesList, List<String> remiderList, List<String> recurrence) throws IOException,
			ParseException {
		Event event = new Event();
		event.setSummary(eventSummary);
		event.setLocation(location);
		event.setDescription(description);

		EventDateTime start = new EventDateTime();
		start.setDateTime(new DateTime(new SimpleDateFormat(DATE_PATTERN).parse(startDate)));
		if (isNullOrEmpry(timeZone))
			start.setTimeZone(timeZone);
		event.setStart(start);

		EventDateTime end = new EventDateTime();
		end.setDateTime(new DateTime(new SimpleDateFormat(DATE_PATTERN).parse(endDate)));
		if (isNullOrEmpry(timeZone))
			end.setTimeZone(timeZone);
		event.setEnd(end);

		if (recurrence != null && !recurrence.isEmpty())
			event.setRecurrence(recurrence);

		if (attendeesList != null && !attendeesList.isEmpty()) {
			List<EventAttendee> attendees = new ArrayList<EventAttendee>();
			for (String current : attendeesList) {
				attendees.add(new EventAttendee().setEmail(current));
			}
			event.setAttendees(attendees);
		}

		if (remiderList != null && !remiderList.isEmpty()) {
			List<EventReminder> remindersOverrides = new ArrayList<EventReminder>();
			for (String current : remiderList) {
				// si potrebbero settare i minuti, pensare come
				remindersOverrides.add(new EventReminder().setMethod(current));
			}
			Event.Reminders reminders = new Event.Reminders();
			reminders.setUseDefault(false);
			reminders.setOverrides(remindersOverrides);
			event.setReminders(reminders);
		}
		event.setVisibility(visibility);

		event = service.events().insert(getIdFromSummary(service, summary), event).execute();
		System.out.println("Events Created: " + event.getHtmlLink());

	}
	private boolean isNullOrEmpry(String field) {
		return field != null && !"".equals(field);
	}
	
	private boolean validateRole(String role) {
		return "none".equalsIgnoreCase(role) || "freeBusyReader".equalsIgnoreCase(role) || "reader".equalsIgnoreCase(role) || "writer".equalsIgnoreCase(role) || "owner".equalsIgnoreCase(role);
	}
}
