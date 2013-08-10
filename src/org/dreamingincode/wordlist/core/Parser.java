package org.dreamingincode.wordlist.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.dreamingincode.wordlist.core.Parser.IllegalInputException.Type;

class Parser {
	
	public static class IllegalInputException extends Exception {
		
		private static final long serialVersionUID = 1L;
		
		
		private Integer lineNumber;
		
		enum Type {
			
			EMPTY_INPUT("内容为空"),
			ILLEGAL_ANNOTATION("标记解析错误"),
			EMPTY_WORD("单词为空"),
			ILLEGAL_WORD_CLASS("词性解析错误");
			
			
			String string;
			
			
			Type(String string) {
				this.string = string;
			}
			
			public String toString() {
				return string;
			}
			
		}
		
		private Type type;
		
		
		public IllegalInputException(Integer lineNumber, Type type) {
			this.lineNumber = lineNumber;
			this.type = type;
		}
		
		
		public Integer getLineNumber() {
			return lineNumber;
		}
		
		public Type getType() {
			return type;
		}
		
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("单词表解析失败。");
			builder.append(System.getProperty("line.separator"));
			builder.append(System.getProperty("line.separator"));
			builder.append("错误类型：");
			builder.append(type.toString());
			builder.append(System.getProperty("line.separator"));
			builder.append("行：");
			builder.append(lineNumber);
			return builder.toString();
		}
		
	}
	
	
	private static class Holder<T> {
		
		public T value;
		
		
		Holder(T value) {
			this.value = value;
		}
		
	}
	
	private static class EntryStackInfo {
		
		EntryRelation relation;
		Entry entry;
		
		public EntryStackInfo(EntryRelation relation, Entry entry) {
			this.relation = relation;
			this.entry = entry;
		}
		
	}
	
	
	public static WordList parse(Reader reader) throws IOException, IllegalInputException {
		BufferedReader bufferedReader = new BufferedReader(reader);
		Integer lineNumber = 0;
		
		// Name
		String name;
		name = bufferedReader.readLine();
		++lineNumber;
		if (name == null) {
			throw new IllegalInputException(lineNumber, Type.EMPTY_INPUT);
		}
		
		// Entries
		List<Entry> entries = new ArrayList<Entry>();
		String string;
		LinkedList<EntryStackInfo> entryStack = new LinkedList<EntryStackInfo>();
		while ((string = bufferedReader.readLine()) != null) {
			++lineNumber;
			if (string.trim().length() != 0) {
				parseLine(entries, string, entryStack, lineNumber);
			}
		}
		
		// WordList
		return new WordList(name, entries);
	}
	
	private static void parseLine(List<Entry> entries, String string, LinkedList<EntryStackInfo> entryStack, Integer lineNumber) throws IllegalInputException
	{
		Holder<Integer> start = new Holder<Integer>(0);
		EntryRelation relation = parseAnnotation(string, start, entryStack, lineNumber);
		String word = parseWord(string, start, lineNumber);
		List<Definition> definitions = parseDefinitions(string, start, lineNumber);
		Entry entry = new Entry(word, definitions);
		if (relation != null) {
			entryStack.peek().entry.addSubEntry(relation, entry);
		} else {
			entries.add(entry);
		}
		entryStack.push(new EntryStackInfo(relation, entry));
	}
	
	private static EntryRelation parseAnnotation(String string, Holder<Integer> start, LinkedList<EntryStackInfo> entryStack, Integer lineNumber) throws IllegalInputException {
		// Parse
		Boolean found = false;
		LinkedList<EntryRelation> stack = new LinkedList<EntryRelation>();
		while (true) {
			for (EntryRelation entryRelation : EntryRelation.values()) {
				String annotation = entryRelation.getAnnotation();
				if (string.startsWith(annotation, start.value)) {
					found = true;
					stack.push(entryRelation);
					start.value += annotation.length();
				}
			}
			if (found) {
				found = false;
			} else {
				break;
			}
		}
		
		// Entry stack
		if (stack.size() > entryStack.size()) {
			throw new IllegalInputException(lineNumber, Type.ILLEGAL_ANNOTATION);
		}
		while (entryStack.size() > stack.size()) {
			entryStack.pop();
		}
		if (stack.size() > 0) {
			Iterator<EntryRelation> iterParsed = stack.iterator();
			Iterator<EntryStackInfo> iterStack = entryStack.iterator();
			EntryRelation parsedRelation = iterParsed.next();
			while (iterParsed.hasNext()) {
				parsedRelation = iterParsed.next();
				EntryRelation previousRelation = iterStack.next().relation;
				if (parsedRelation != previousRelation) {
					throw new IllegalInputException(lineNumber, Type.ILLEGAL_ANNOTATION);
				}
			}
			return stack.peek();
		} else {
			return null;
		}
	}
	
	private static Boolean isChinese(Character c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		} else {
			return false;
		}
	}
	
//	private static boolean isChineseByName(String str) {
//		if (str == null) {
//			return false;
//		}
//		String reg = "\\p{InCJK Unified Ideographs}&&\\P{Cn}";
//		Pattern pattern = Pattern.compile(reg);
//		return pattern.matcher(str.trim()).find();
//	}
	
	private static String parseWord(String string, Holder<Integer> start, Integer lineNumber) throws IllegalInputException {
		// Chinese character
		Integer position = start.value;
		for (; position < string.length(); ++position) {
			if (isChinese(string.charAt(position))) {
				break;
			}
		}
		
		// Word class
		Integer result = -1;
		for (WordClass wordClass : WordClass.values()) {
			result = string.indexOf(wordClass.getAbbreviation(), start.value);
			if (result != -1 && result < position) {
				position = result;
			}
		}
		
		// Word
		String word = string.substring(start.value, position).trim();
		if (word.length() == 0) {
			throw new IllegalInputException(lineNumber, Type.EMPTY_WORD);
		}
		// Set the starting position for the next parse
		start.value = position;
		return word;
	}
	
	private static Definition parseDefinition(String string, Integer lineNumber) throws IllegalInputException {
		// Word class
		Set<WordClass> wordClasses = new TreeSet<WordClass>();
		Integer start = 0;
		Boolean nextExpected = false;
		Boolean continueLoop = false;
		while (true) {
			for (WordClass wordClass : WordClass.values()) {
				if (string.startsWith(wordClass.getAbbreviation(), start)) {
					wordClasses.add(wordClass);
					start += wordClass.getAbbreviation().length();
					if (string.startsWith(WordClass.separator, start)) {
						start += WordClass.separator.length();
						nextExpected = true;
						continueLoop = true;
					} else {
						nextExpected = false;
						continueLoop = false;
						break;
					}
				}
			}
			if (continueLoop) {
				continueLoop = false;
			} else {
				break;
			}
		}
		if (nextExpected) {
			throw new IllegalInputException(lineNumber, Type.ILLEGAL_WORD_CLASS);
		}
		
		// Content
		String content = string.substring(start);
		
		// Definition
		return new Definition(wordClasses, content);
	}
	
	private static ArrayList<Definition> parseDefinitions(String string, Holder<Integer> start, Integer lineNumber) throws IllegalInputException {
		String [] definitionStrings = string.substring(start.value).split(" ");
		ArrayList<Definition> definitionList = new ArrayList<Definition>();
		for (String definitionString : definitionStrings) {
			definitionList.add(parseDefinition(definitionString, lineNumber));
		}
		return definitionList;
	}
	
}

