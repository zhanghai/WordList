package org.dreamingincode.wordlist.core;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.List;

public class WordList implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private String name;
	
	private List<Entry> entries;
	
	
	public WordList(String name, List<Entry> entries) {
		this.name = name;
		this.entries = entries;
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Entry> getEntries() {
		return entries;
	}
	
	
	public static WordList parse(Reader reader) throws IOException, Parser.IllegalInputException {
		return Parser.parse(reader);
	}
	
	public static WordList parse(File file) throws IOException, Parser.IllegalInputException {
		WordList wordList;
		FileReader fileReader = new FileReader(file);
		try {
			wordList = parse(fileReader);
		} finally {
			fileReader.close();
		}
		return wordList;
	}
	
	public static WordList parse(String fileName) throws IOException, Parser.IllegalInputException {
		WordList wordList;
		FileReader fileReader = new FileReader(fileName);
		try {
			wordList = parse(fileReader);
		} finally {
			fileReader.close();
		}
		return wordList;
	}
	
	public void print(Writer writer) throws IOException {
		writer.write(name);
		writer.write(System.getProperty("line.separator"));
		for (Entry entry : entries) {
			entry.print(writer, "");
		}
		writer.flush();
	}
	
}
