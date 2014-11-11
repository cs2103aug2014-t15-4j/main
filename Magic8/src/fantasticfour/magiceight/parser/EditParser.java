package fantasticfour.magiceight.parser;

import java.util.ArrayList;
import java.util.Calendar;

import fantasticfour.magiceight.Magic8CommandObject;

//@author A0080527H
public class EditParser implements IParser {
	public ArrayList<Token> tokenize(String input) {
		boolean first = true;
		boolean second = true;
		ArrayList<Token> outToken = new ArrayList<Token>();
		
		for(String str : input.split("\\s+")) {
			if(first) {
				outToken.add(new Token(Token.Type.function, str));
				first = false;
				continue;
			}
			
			if(second) {
				outToken.add(new Token(Token.Type.index, str));
				second = false;
				continue;
			}
			
			if(str.startsWith("(")) {
				outToken.add(new Token(Token.Type.startDate, str.substring(1, str.length())));
			} else if (str.startsWith(")")) {
				outToken.add(new Token(Token.Type.endDate, str.substring(1, str.length())));
			} else if (str.startsWith("#")) {
				outToken.add(new Token(Token.Type.tag, str.substring(1, str.length())));
			} else {
				outToken.add(new Token(Token.Type.description, str));
			}
		}		
		return outToken;
	}
	
    public Magic8CommandObject parse(String input) {
    	ArrayList<Token> tokens = tokenize(input);
    	String fn = "";
    	String td = "";
    	ArrayList<Integer> in = new ArrayList<Integer>();
    	ArrayList<String> tg = new ArrayList<String>();
		Calendar sd = null;
		Calendar dd = null;
    	
    	for(Token token : tokens) {
    		if(token.getType() == Token.Type.function){
    			fn = token.getValue();
    		} else if(token.getType() == Token.Type.index) {
    			in.add(Integer.parseInt(token.getValue()));
    		} else if(token.getType() == Token.Type.description) {
    			td = td + token.getValue() + " ";
    		} else if(token.getType() == Token.Type.tag) {
    			tg.add(token.getValue());
    		} else if(token.getType() == Token.Type.startDate) {
    			sd = DateUtil.parseString(token.getValue(), false);
    		} else if(token.getType() == Token.Type.endDate) {
    			dd = DateUtil.parseString(token.getValue(), true);
    		}
    	}
    	
    	if(tg.size() == 0) {
    		tg = null;
    	}
    	
    	if(in.size() == 0) {
    		in = null;
    	}
    	
        return new Magic8CommandObject(fn, td, tg, null, in, sd, dd);
    }
}
