package com.urlshortener.service;

import com.urlshortener.db.URLCollection;
import com.urlshortener.util.URLObject;

public class URLShortener {
	private final String origURL;
	private final String shortURL;

	public URLShortener(URLObject urlObject, URLCollection db) {
		System.out.println("Received URL: [" + urlObject.getURL() + "]");
		this.origURL = urlObject.getURL();
		db.insertURL(urlObject);
		this.shortURL = urlObject.encode();
	}

	public String getOrigURL() {
		return origURL;
	}
	
	public String getShortURL() {
		return shortURL;
	}
}
