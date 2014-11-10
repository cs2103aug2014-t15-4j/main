package fantasticfour.magiceight.parser;

import java.util.ArrayList;

import fantasticfour.magiceight.Magic8CommandObject;

public class DisplayParser implements IParser {
	public ArrayList<Token> tokenize(String input) {
		boolean first = true;
		ArrayList<Token> outToken = new ArrayList<Token>();
		
		for(String str : input.split("\\s+")) {
			if(first) {
				outToken.add(new Token(Token.Type.function, str));
				first = false;
				continue;
			}
			
			if (str.startsWith("#")) {
				outToken.add(new Token(Token.Type.tag, str.substring(1, str.length())));
			} else {
				outToken.add(new Token(Token.Type.keyword, str));
			}
		}		
		return outToken;
	}
	
    public Magic8CommandObject parse(String input) {
    	ArrayList<Token> tokens = tokenize(input);
    	String fn = "";
    	ArrayList<String> kw = new ArrayList<String>();
    	ArrayList<String> tg = new ArrayList<String>();
    	
    	for(Token token : tokens) {
    		if(token.getType() == Token.Type.function){
    			fn = token.getValue();
    		} else if(token.getType() == Token.Type.tag) {
    			tg.add(token.getValue());
    		} else if(token.getType() == Token.Type.keyword) {
    			kw.add(token.getValue());
    		} 
    	}
    	
    	if(tg.size() == 0) {
    		tg = null;
    	}
    	
    	if(kw.size() == 0) {
    		kw = null;
    	}
    	
        return new Magic8CommandObject(fn, null, tg, kw, null, null, null);
    }
}
