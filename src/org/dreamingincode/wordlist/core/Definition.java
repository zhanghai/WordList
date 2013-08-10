package org.dreamingincode.wordlist.core;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.Set;

public class Definition implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private Set<WordClass> wordClasses;
	
	private String content;
	
	
	public Definition(Set<WordClass> wordClasses, String content) {
		this.wordClasses = wordClasses;
		this.content = content;
	}
	
	
	public Set<WordClass> getWordClasses() {
		return wordClasses;
	}
	
	public void setWordClasses(Set<WordClass> wordClasses) {
		this.wordClasses = wordClasses;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	
	public void print(Writer writer) throws IOException {
		Boolean firstPrinted = false;
		for (WordClass wordClass : wordClasses) {
			if (firstPrinted) {
				writer.write(WordClass.separator);
			}
			writer.write(wordClass.getAbbreviation());
			firstPrinted = true;
		}
		writer.write(content);
	}
	
	public void toString(StringBuilder stringBuilder) {
		Boolean firstPrinted = false;
		for (WordClass wordClass : wordClasses) {
			if (firstPrinted) {
				stringBuilder.append(WordClass.separator);
			}
			stringBuilder.append(wordClass.getAbbreviation());
			firstPrinted = true;
		}
		stringBuilder.append(content);
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		toString(stringBuilder);
		return stringBuilder.toString();
	}
	
}
