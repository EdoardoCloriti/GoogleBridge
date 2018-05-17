package com.orion.googlebridge;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.drive.Drive;
import com.google.api.services.gmail.Gmail;
import com.orion.googlebridge.Calendar.ICalendarBridge;
import com.orion.googlebridge.Calendar.impl.CalendarBridgeImpl;
import com.orion.googlebridge.drive.IDriveBridge;
import com.orion.googlebridge.drive.impl.DriveBridgeImpl;
import com.orion.googlebridge.gmail.IGMailBridge;
import com.orion.googlebridge.gmail.impl.GMailBridgeImpl;

public class GoogleBridgeMain {

	public static void main(String[] args) throws ParseException {
		String dir = "C:\\.store\\calendar";
		String applicationName = "Google-Bridge-Test";

		try {
			System.out.println("Begin");
//			calendarBridge(dir, applicationName);
			driveBridge(dir, applicationName);
			
		}
		catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("fatto");
	}
	private static void calendarBridge(String dir, String applicationName) throws GeneralSecurityException, IOException, ParseException {
		ICalendarBridge bridge = new CalendarBridgeImpl(applicationName);
		Credential credential = bridge.authentication(dir);
		Calendar service = bridge.getService(credential);
		 boolean isPresent=bridge.existCalendar(service,"Work");
		 bridge.removeCalendar(service, "prova");
		 bridge.printSummary(service);
		 bridge.addCalendar(service, "prova","Ã¨ stata dura ma per ora un calendario condiviso lo ho creato :D ",null,null);
		 bridge.printSummary(service);
		 bridge.removeCalendar(service, "prova");
		 bridge.printSummary(service);
		 bridge.setACL(service, "prova", "orion.development.cloriti@gmail.com", "owner");
		 bridge.removeACL(service, "prova", "orion.development.cloriti@gmail.com", "owner");
		bridge.insertEvent(service, "prova", "evento-prova", "location prova", "questa Ã¨ una prova", "26-01-2016 08:30:00", "26-01-2016 17:30:00", null, null, null, null, null);
	}

	private static void driveBridge(String dir, String applicationName) throws GeneralSecurityException, IOException, ParseException {
		IDriveBridge bridge = new DriveBridgeImpl(applicationName);
		Credential credential = bridge.authentication(dir);
		Drive service = bridge.getService(credential);
		bridge.viewFile(service);
	}
	private static void gmailBridge(String dir, String applicationName) throws GeneralSecurityException, IOException, ParseException {
		IGMailBridge bridge=new GMailBridgeImpl(applicationName);
		Credential credential = bridge.authentication(dir);
		Gmail service=bridge.getService(credential);
	}
}
