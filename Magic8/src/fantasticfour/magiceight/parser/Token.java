package fantasticfour.magiceight.parser;

public class Token {
	public enum Type {
		function,
		description,
		startDate,
		endDate,
		tag,
		index,
		endIndex,
		keyword
	}
	
	private Type type;
	private String value;
	
	public Token(Type ty, String str) {
		type = ty;
		value = str;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getValue() {
		return value;
	}
}
