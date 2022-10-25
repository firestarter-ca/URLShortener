package com.urlshortener.service;

import com.urlshortener.util.URLObject;

public class URLRedirect {
	private String origURL;
	private String shortURL;
	
	public URLRedirect(String shortURL) {
		URLObject urlObject = new URLObject(shortURL, false);
		shortURL = urlObject.getShortURL();
	}
	
	public String getOrigURL() {
		return this.origURL;
	}
	
	public void setOrigURL(String url) {
		this.origURL = url;
	}
	
	public String getShortURL() {
		return this.shortURL;
	}
}
