package org.dreamingincode.wordlist.core;

public enum WordClass {
	
	NOUN("n."),
	VERB("v."),
	ADJECTIVE("adj."),
	ADVERB("adv."),
	PRONOUN("pron."),
	PREPOSITION("prep."),
	CONJUNCTION("conj."),
	ABBREVIATION("abbr."),
	DETERMINER("TODO"),
	EXCLAMATION("TODO");
	
	
	public static final String separator = "&";
	
	
	private String abbreviation;
	
	
	WordClass(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	
	
	public String getAbbreviation() {
		return abbreviation;
	}
	
}
