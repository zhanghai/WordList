package org.dreamingincode.wordlist.core;

public enum EntryRelation {
	
	SIMILAR("^"),
	SYNONYM("~"),
	USAGE("@"),
	DERIVATION("#");
	
	
	private String annotation;
	
	
	EntryRelation(String annotation) {
		this.annotation = annotation;
	}
	
	
	public String getAnnotation() {
		return annotation;
	}
	
}
