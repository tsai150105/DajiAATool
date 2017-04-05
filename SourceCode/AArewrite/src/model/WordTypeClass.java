package model;

public class WordTypeClass {
	String keyString;
	String word;
	String sizeSwitch = "0";
	String replaceAllSwitch = "0";
	String wordType = "Microsoft YaHei";
	public WordTypeClass(String keyString, String word,String sizeSwitch,String replaceAllSwitch,String wordType) {
		this.keyString = keyString;
		this.word = word;
		this.sizeSwitch = sizeSwitch;
		this.replaceAllSwitch = replaceAllSwitch;
		this.wordType = wordType;
	}
	
	public String getKeyString() {
		return keyString;
	}
	
	public String getWord() {
		return word;
	}
	
	public String getSizeSwitch() {
		return sizeSwitch;
	}
	
	public String getReplaceAllSwitch() {
		return replaceAllSwitch;
	}
	
	public String getWordType() {
		return wordType;
	}
}
