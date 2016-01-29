package com.orion.googlebridge.drive.util;

import java.util.HashMap;
import java.util.Map;

public class DriveMimeTypeSupported {

	public static final String KIX = "KIX";
	public static final String ARC = "ARC";
	public static final String OPSPREADSHEET = "OPSPREADSHEET";
	public static final String PLAIN = "PLAIN";
	public static final String ZIP = "ZIP";
	public static final String MG_VIDEO = "MG_VIDEO";
	public static final String MPG_AUDIO = "MPG_AUDIO";
	public static final String JPG = "JPG";
	public static final String EXCL = "EXCL";
	public static final String PPT = "PPT";
	public static final String OPWORD = "OPWORD";
	public static final String MSWORD = "MSWORD";
	public static final String PDF = "PDF";
	public static final String VIDEO = "video";
	public static final String UNKNOWN = "unknown";
	public static final String SPREADSHEET = "spreadsheet";
	public static final String SITES = "sites";
	public static final String SCRIPT = "script";
	public static final String PRESENTATION = "presentation";
	public static final String PHOTO = "photo";
	public static final String MAP = "map";
	public static final String FUSIONTABLE = "fusiontable";
	public static final String FORM = "form";
	public static final String FOLDER = "folder";
	public static final String FILE = "file";
	public static final String DRAWING = "drawing";
	public static final String DOC = "doc";
	public static final String AUDIO = "audio";
	private static Map<String, String> supportedMime = new HashMap<String, String>();
	static {
		supportedMime.put(AUDIO, "application/vnd.google-apps.audio");
		supportedMime.put(DOC, "application/vnd.google-apps.document"); // Google Docs
		supportedMime.put(DRAWING, "application/vnd.google-apps.drawing"); // Google Drawing
		supportedMime.put(FILE, "application/vnd.google-apps.file"); // Google Drive file
		supportedMime.put(FOLDER, "application/vnd.google-apps.folder"); // Google Drive folder
		supportedMime.put(FORM, "application/vnd.google-apps.form"); // Google Forms
		supportedMime.put(FUSIONTABLE, "application/vnd.google-apps.fusiontable");// Google Fusion Tables
		supportedMime.put(MAP, "application/vnd.google-apps.map"); // Google My Maps
		supportedMime.put(PHOTO, "application/vnd.google-apps.photo");
		supportedMime.put(PRESENTATION, "application/vnd.google-apps.presentation");// Google Slides
		supportedMime.put(SCRIPT, "application/vnd.google-apps.script");// Google Apps Scripts
		supportedMime.put(SITES, "application/vnd.google-apps.sites"); // Google Sites
		supportedMime.put(SPREADSHEET, "application/vnd.google-apps.spreadsheet");// Google Sheets
		supportedMime.put(UNKNOWN, "application/vnd.google-apps.unknown");
		supportedMime.put(VIDEO, "application/vnd.google-apps.video");
		supportedMime.put(PDF, "application/pdf");
		supportedMime.put(MSWORD, "application/msword");
		supportedMime.put(OPWORD, "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		supportedMime.put(PPT, "application/vnd.ms-powerpoint.presentation.macroEnabled.12");
		supportedMime.put(EXCL, "application/vnd.ms-excel");
		supportedMime.put(JPG, "image/jpeg");
		supportedMime.put(MPG_AUDIO, "audio/mpeg");
		supportedMime.put(MG_VIDEO,"video/mpeg");
		supportedMime.put(ZIP, "application/zip");
		supportedMime.put(PLAIN, "text/plain");
		supportedMime.put(OPSPREADSHEET, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		supportedMime.put(ARC, "application/vnd.android.package-archive");
		supportedMime.put(KIX, "application/vnd.google-apps.kix");
	}
	
	public static String getMimeTypeByKey(String key)
	{
		return supportedMime.get(key);
	}
	
}
