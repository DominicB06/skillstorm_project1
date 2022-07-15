package com.skillstorm.project1.services;

public class URLParserService {

	// if url looks like warehouse/1
	// we need the number 1
	public int extractIDFromURL(String url) {
		System.out.println(url);
		String[] splitString = url.split("/");
		System.out.println(splitString[1]);
		
		return Integer.parseInt(splitString[1]);
	}
	
	// if url looks like warehouse/1000+vanguard+blvd
	// we need 1000 vanguard blvd
	public String extractStringFromURL(String url) {
		System.out.println(url);
		String[] splitString = url.split("/");
		System.out.println(splitString[1]);
		
		String[] before = splitString[1].split("-");
		StringBuilder after = new StringBuilder();
		
		for(String i : before) {
			after.append(i + " ");
		}
		
		// have to remove the space at the end of the string
		after.deleteCharAt(after.length());
		
		System.out.println(after.toString());
		return after.toString();
		
	}
	
	public static void main(String[] args) {
		URLParserService parser = new URLParserService();
		
		System.out.println(parser.extractStringFromURL("warehouse/1000-vanguard-blvd"));
	}
}
