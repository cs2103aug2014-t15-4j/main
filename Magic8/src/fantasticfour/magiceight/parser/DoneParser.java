package fantasticfour.magiceight.parser;

import java.util.ArrayList;

import fantasticfour.magiceight.Magic8CommandObject;

public class DoneParser implements IParser {
	
	public ArrayList<Token> tokenize(String input) {
		boolean first = true;
		ArrayList<Token> outToken = new ArrayList<Token>();
		
		String[] seps = {"-", "to"};
		boolean sepFound = false;
		
		for(String str : input.split("[\\s+,]")) {
			if(first) {
				outToken.add(new Token(Token.Type.function, str));
				first = false;
				continue;
			}
			
			if(str.startsWith("#")) {
				outToken.add(new Token(Token.Type.tag, str.substring(1, str.length())));
			} else if(str.matches("\\d+") && sepFound) {
				sepFound = false;
				outToken.add(new Token(Token.Type.endIndex, str));
			} else if(str.matches("\\d+")) {
				outToken.add(new Token(Token.Type.index, str));
			} else {
				for(String sep : seps) {
					if(str.equalsIgnoreCase(sep)) {
						sepFound = true;
						break;
					} else if(str.contains(sep)) {
						outToken.add(new Token(Token.Type.index, str.split(sep)[0]));
						outToken.add(new Token(Token.Type.endIndex, str.split(sep)[1]));
						break;
					}
				}
			}
		}		
		return outToken;
	}
	
    public Magic8CommandObject parse(String input) {
    	ArrayList<Token> tokens = tokenize(input);
    	String fn = "";
    	ArrayList<String> tg = new ArrayList<String>();
    	ArrayList<Integer> in = new ArrayList<Integer>();
    	
    	for(Integer i = 0; i < tokens.size(); i++) {
    		Token token = tokens.get(i);
    		if(token.getType() == Token.Type.function){
    			fn = token.getValue();
    		} else if(token.getType() == Token.Type.tag) {
    			tg.add(token.getValue());
    		} else if(token.getType() == Token.Type.index){
    			if(i == tokens.size()-1) {
    				in.add(Integer.parseInt(token.getValue()));
    			} else if(tokens.get(i+1).getType() == Token.Type.index) {
    				in.add(Integer.parseInt(token.getValue()));
    			} else if (tokens.get(i+1).getType() == Token.Type.endIndex) {
    				Integer startIter = Integer.parseInt(token.getValue());
    				Integer endIter = Integer.parseInt(tokens.get(i+1).getValue());
    				for(Integer iter = startIter; iter <= endIter; iter++) {
    					in.add(iter);
    				}
    			}
    		}
    	}
    	
    	if(tg.size() == 0) {
    		tg = null;
    	}
    	
    	if(in.size() == 0) {
    		in = null;
    	}
    	
        return new Magic8CommandObject(fn, null, tg, null, in, null, null);
    }
}
