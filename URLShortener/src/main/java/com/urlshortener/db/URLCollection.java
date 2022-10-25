package com.urlshortener.db;

import java.util.HashMap;
import java.util.LinkedList;

import com.urlshortener.util.URLObject;

public class URLCollection {
	public LinkedList<URLObject> db;
	public HashMap<Integer, Integer> usedPositions;
	int counter = 0;
	
	public URLCollection() {
		System.out.println("Initializing in-memory DB");
		db = new LinkedList<URLObject>();
		usedPositions = new HashMap<>();
		System.out.println("In-memory DB initialized");
	}
	
	public void insertURL(URLObject newURL) {
		System.out.println("Current position registered in the system: [" + counter + "]");
		System.out.println("Current position held in DB: [" + db.size() + "]");
		while (usedPositions.containsKey(counter)) {
			counter++;
		}
		newURL.setUrlID(counter);
		db.add(newURL);
		usedPositions.put(counter, counter);
		System.out.println("Inserted new URL at position [" + counter + "]");
		counter += 1;
	}
	
	public boolean customInsert(URLObject newURL, int pos) {
		if (usedPositions.containsKey(pos)) {
			return false;
		} else {
			newURL.setUrlID(pos);
			db.add(newURL);
			usedPositions.put(pos, counter);
			counter += 1;
			System.out.println("Inserted new URL at position [" + pos + "]");
			System.out.println("Counter moved to: [" + counter + "]");
		}
		return true;
	}
	
	public String getURLAt(int index) {
		System.out.println("Retrieving URL at position [" + index + "]");
		
		return db.get(index).getURL();
	}
}
