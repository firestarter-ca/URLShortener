package com.urlshortener.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLObject {
	private final String shortURLPrefix = "http://my-urls.com/";
	private String origURL;
	private String shortURL;
	private int urlID = 0;
	private char[] encoding = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	
	public URLObject(String url, boolean longURL) {
		System.out.println("Validating URL: [" + url + "]");
		if (isValidURL(url, longURL)) {
			if (longURL) {
				this.origURL = url;
			} else {
				this.shortURL = url;
			}
		} else {
			this.origURL = "invalid";
			this.shortURL = "invalid";
			this.setUrlID(-1);
		}
	}
	
	public URLObject(String longURL, String shortURL) {
		URLObject longURLObject = new URLObject(longURL, true);
		URLObject shortURLObject = new URLObject(shortURL, false);
		
		this.origURL = longURLObject.getURL();
		this.shortURL = shortURLObject.getShortURL();
	}
	
	public String encode() {
		StringBuffer shortenedURL = new StringBuffer();
		int recordID = getRecordID();
		
		// Encode resulting number
		while (recordID > 0) {
			shortenedURL.append(encoding[recordID % 62]);
            recordID = recordID / 62;
		}
		
		return shortURLPrefix + shortenedURL.toString();
	}
	
	public int decode() {
		int recordID = 0;
		final String suffix = getSuffix();
		
		for (int i = 0; i < suffix.length(); i++)
        {
            if ('0' <= suffix.charAt(i) &&
            		suffix.charAt(i) <= '9')
            recordID = recordID * 62 + suffix.charAt(i) - '0';
            if ('a' <= suffix.charAt(i) &&
            		suffix.charAt(i) <= 'z')
            	recordID = recordID * 62 + suffix.charAt(i) - 'a' + 26;
            if ('A' <= suffix.charAt(i) &&
            		suffix.charAt(i) <= 'Z')
            	recordID = recordID * 62 + suffix.charAt(i) - 'A' + 52;
        }
        return recordID;
	}
	
	public String getSuffix() {
		String shortSuffix = shortURL.substring(shortURLPrefix.length());
		System.out.println("Looking for subdomain: [" + shortSuffix + "]");
		
		return shortSuffix;
	}
	
	private int getRecordID() {
		int currUrlID = getUrlID();
		setUrlID(currUrlID + 1);
		
		return getUrlID();
	}
	
	public boolean isValidURL(String url, boolean longURL) {
		String urlPattern;
		if (longURL) {
			urlPattern = "^(http|https):\\/\\/[a-zA-Z0-9:\\.\\-\\/]+";
			System.out.println("Long URL: [" + url + "]");
		} else {
			urlPattern = this.shortURLPrefix + "[a-zA-Z0-9]+";
			System.out.println("Short URL: [" + url + "]");
		}
		Pattern pattern = Pattern.compile(urlPattern);
		Matcher matcher = pattern.matcher(url);
		
		return matcher.matches();
	}
	
	public int getUrlID() {
		return urlID;
	}
	
	public void setUrlID(int value) {
		urlID = value;
	}
	
	public String getURL() {
		return origURL;
	}
	
	public String getShortURL() {
		return shortURL;
	}
	
	public void setShortURL(String url) {
		shortURL = url;
	}
}
