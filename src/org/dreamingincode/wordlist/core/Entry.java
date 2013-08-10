package org.dreamingincode.wordlist.core;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Entry implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private String word;
	
	private List<Definition> definitions;
	
	public static class SubEntryInformation implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		
		public EntryRelation relation;
		public Entry entry;
		
		public SubEntryInformation(EntryRelation relation, Entry entry) {
			this.relation = relation;
			this.entry = entry;
		}
		
	}
	
	private List<SubEntryInformation> subEntries = new ArrayList<SubEntryInformation>();
	
	
	public Entry(String word, List<Definition> definitions) {
		this.word = word;
		this.definitions = definitions;
	}
	
	
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public List<Definition> getDefinitions() {
		return definitions;
	}
	
	public List<SubEntryInformation> getSubEntries() {
		return subEntries;
	}
	
	
	public void addSubEntry(EntryRelation relation, Entry entry) {
		subEntries.add(new SubEntryInformation(relation, entry));
	}
	
	
	public void print(Writer writer, String precedingAnnotation) throws IOException {
		writer.write(precedingAnnotation);
		writer.write(word);
		for (Definition definition : definitions) {
			writer.write(" ");
			definition.print(writer);
		}
		writer.write(System.getProperty("line.separator"));
		for (SubEntryInformation subEntry : subEntries) {
			subEntry.entry.print(writer, precedingAnnotation + subEntry.relation.getAnnotation());
		}
	}
	
	public String definitionsToString() {
		StringBuilder stringBuilder = new StringBuilder();
		Boolean firstPrinted = false;
		for (Definition definition : definitions) {
			if (firstPrinted) {
				stringBuilder.append(" ");
			}
			definition.toString(stringBuilder);
			firstPrinted = true;
		}
		return stringBuilder.toString();
	}
	
	public void toString(StringBuilder stringBuilder) {
		stringBuilder.append(word);
		for (Definition definition : definitions) {
			stringBuilder.append(" ");
			definition.toString(stringBuilder);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		toString(stringBuilder);
		return stringBuilder.toString();
	}
	
}
