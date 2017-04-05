import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

import model.WordTypeClass;

/**
 * @author WaaghHO 筍民
 * @since 2017.01.24
 * @version 0.1.0.1
 */
public class RewriteAA {
	
	
	static List<WordTypeClass> replaceTxt = new ArrayList<WordTypeClass>();
	
	public static void main(String[] args){
	

		String inputFileNameString = "";
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
        System.out.println("輸入要轉換的網頁名稱(放在WebPa1"
        		+ "ge資料夾下)，比如index.htm：");
        inputFileNameString = scanner.nextLine();
        
        //沒輸入就用舊的
        try {
        	String inputTemp = "";
			BufferedReader br1 = new BufferedReader(new InputStreamReader( new FileInputStream("./TempData/LastWebName.txt"),"utf8" ));
			while(br1.ready()){
				inputTemp = br1.readLine();
			}			
			if(inputFileNameString.equals("")){			
				inputFileNameString = inputTemp;	
	        }
			br1.close();
			
			System.out.println("Read : " + inputFileNameString);
			OutputStreamWriter br2 = new OutputStreamWriter(new FileOutputStream("./TempData/LastWebName.txt"),"utf8");
			br2.write(inputFileNameString);
			br2.close();
			
    	} catch (Exception e) {
			e.printStackTrace();
		}
        
        
		changeTxtWord(
				"./WebPage/" + inputFileNameString,
				"./WebPage/new" + inputFileNameString,
				"./OutPut/translation.csv"
				);
		System.out.println("Finish to : new" + inputFileNameString);
	}

	private static void changeTxtWord(String inputPath, String outPutPath, String replaceTxtPath){
		String originTxt = getFileText(inputPath); //取得檔案內容
		getReplaceTxtList(replaceTxtPath); //取得檔案內
		String outPutTxt = originTxt;
		
		//覆蓋
		for (WordTypeClass entry : replaceTxt) {  
			try {
				outPutTxt = doReplace(outPutTxt, entry);
			} catch (Exception e) {
				System.out.println("Error : this sentence cant not replace");
			}
		} 	
		writeFileText(outPutPath, outPutTxt); 	 //將換過的檔案內容寫回去
	} 
	
	private static String doReplace(String stringBeReplace, WordTypeClass wordTypeClass){
		String raplacetxt = "<span style=\"font-family:" + wordTypeClass.getWordType()+ ";";
		
		
		String valueString = wordTypeClass.getWord();
		
		//去除key字可能多複製的空白
		String keyString = deleteSpaceWord(wordTypeClass.getKeyString());
		
		//少的補字
		if(keyString.length() > valueString.length()){
			for(int i = 0 ; i < keyString.length() - keyString.length() ; i++){
				valueString = valueString + "　";
			}
		}
		
		//字體大小
		if(wordTypeClass.getSizeSwitch().equals("1")){
			raplacetxt = raplacetxt + "font-size:10px;";
		}else if(!wordTypeClass.getSizeSwitch().equals("0")){
			raplacetxt = raplacetxt + "font-size:"+ wordTypeClass.getSizeSwitch() + "px;";
		}
		
		//組完全句
		raplacetxt = raplacetxt + "\">" + valueString + "</span>";;
		
		switch (wordTypeClass.getReplaceAllSwitch()) {
		case "0":
			System.out.println("Change : " + keyString + " to " + wordTypeClass.getWord());
			//System.out.println(""+keyString+valueString+raplacetxt);
			stringBeReplace = stringBeReplace.replaceFirst(Matcher.quoteReplacement(keyString), Matcher.quoteReplacement(raplacetxt)); //換掉某個字	
			break;
		case "1":
			System.out.println("Change ALL: " + keyString + " to " + wordTypeClass.getWord());
			stringBeReplace = stringBeReplace.replaceAll(Matcher.quoteReplacement(keyString), Matcher.quoteReplacement(raplacetxt)); //換掉某個字全部
			break;
		default:
			System.out.println("Exception to replace");
			break;
		}
		
		return stringBeReplace;	
	}
	
	private static String getFileText(String path){
		StringBuffer strBuf = new StringBuffer();
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader( new FileInputStream(path),"utf8" ));
			while(br.ready()){
				String brStr = br.readLine();
				strBuf.append(brStr);
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		//return testString;
		return strBuf.toString();
	} 
	
	private static void getReplaceTxtList(String path){
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader( new FileInputStream(path),"utf16" ));
			while(br.ready()){
				String brStr = br.readLine();
				String tempArray[]  = brStr.split("\\t");//分割tab  
				String key = "null";
				String word = "null";
				String sizeSwitch = "0";
				String replaceAllSwitch = "0";
				String wordType = "Microsoft YaHei";
				
				/* *
				 * 
				 *  位置
				 *  0 : 原文 
				 *  1 : 修改文字
				 *  2 : 是否全部覆蓋
				 *  3 : 字體大小	ps.例外:1 為10號
				 *  
				 * */
				try {
					if(!tempArray[4].equals("")){
						wordType = tempArray[4];
					}	
				} catch (Exception e) {}
				
				try {		
					if(!tempArray[2].equals("")){
						replaceAllSwitch = tempArray[2];
					}	
				} catch (Exception e) {}
				
				try {
					if(!tempArray[3].equals("")){
						sizeSwitch = tempArray[3];
					}
				} catch (Exception e) {}
				
				
				try {
					word = tempArray[1];
				} catch (Exception e) {}
				
				try {
					key = tempArray[0];
				} catch (Exception e) {}
				
				
				if(!key.equals("null") && !key.equals("")){
					//System.out.println("Key: " + key + word);
					replaceTxt.add(new WordTypeClass(key, word, sizeSwitch, replaceAllSwitch, wordType));		
				}
				
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}

	} 
	
	private static String deleteSpaceWord(String originString){
		
		char[] originStringChars = originString.toCharArray();
		int startIndex = 0;
		int endIndex = originStringChars.length - 1;
		for(int i = 0 ; i < originStringChars.length ; i++){
			if(originStringChars[i] != "　".toCharArray()[0]){
				startIndex = i;
				break;
			}
		}
		
		for(int i = originStringChars.length - 1 ; i >= 0 ; i--){
			if(originStringChars[i] != "　".toCharArray()[0]){
				endIndex = i;
				break;
			}
		}
		
		char[] newStringChars = new char[endIndex - startIndex + 1];
		int newIndex = 0;
		for(int i = 0 ; i < originStringChars.length ; i++){
			if(i >= startIndex && i <= endIndex){
				newStringChars[newIndex] = originStringChars[i];
				newIndex++;
			}
		}
		
		return String.valueOf(newStringChars).trim();
	}
	
	private static int writeFileText(String path,String txt){
		
		try {
			OutputStreamWriter br = new OutputStreamWriter(new FileOutputStream(path),"utf8");
			br.write(txt);
			br.close();
		}catch(IOException e){
			e.printStackTrace();
			return -1;
		}
		
		return 1;
	} 
}
