package com.urlshortener.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.urlshortener.db.URLCollection;
import com.urlshortener.util.URLObject;

@RestController
public class URLShortenerController {
	public URLCollection db = new URLCollection();

	@GetMapping("/shorten")
	public ResponseEntity<String> shorten(@RequestParam(value = "url") String origURL) {
		URLObject urlObject = new URLObject(origURL, true);
		
		if (urlObject.getURL() == "invalid") {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("CODE 400. The provided parameter is not a valid URL!");
		}
		
		db.insertURL(urlObject);
		
		return ResponseEntity.status(HttpStatus.OK).body(
				String.format("CODE 200. Short URL [%s] created", urlObject.encode()));
	}
	
	@GetMapping("/redirect")
	public ModelAndView redirect(@RequestParam(value = "url") String shortURL) {
		int origRecordID = 0;
		int realPosition = 0;

		URLObject urlObject = new URLObject(shortURL, false);
		origRecordID = urlObject.decode();
		if (db.usedPositions.containsKey(origRecordID)) {
			realPosition = db.usedPositions.get(origRecordID);
		}
		System.out.println("Object URLID: [" + urlObject.getUrlID() + "]");
		System.out.println("Original Record ID: [" + origRecordID + "]");
		System.out.println("Real position: [" + realPosition + "]");
        return new ModelAndView("redirect:" + db.getURLAt(realPosition));
    }
	
	@GetMapping("/custom")
	public ResponseEntity<String> custom(@RequestParam(value = "url") String longURL,
			@RequestParam(value = "surl") String shortURL) {
		URLObject urlObject = new URLObject(longURL, shortURL);
		URLObject shortURLLocation = new URLObject(shortURL, false);
		int proposedRecordID;
		
		if (urlObject.getURL() == "invalid" || urlObject.getShortURL() == "invalid") {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("CODE 400. One of the provided parameters is not a valid URL!");
		}
		
		proposedRecordID = shortURLLocation.decode();
		
		if (db.usedPositions.containsKey(proposedRecordID)) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("CODE 409. Short URL already exists!");
		} else {
			urlObject.setUrlID(proposedRecordID);
			db.customInsert(urlObject, proposedRecordID);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(
				String.format("CODE 200. Short URL [%s] created", urlObject.getShortURL()));
	}
}
