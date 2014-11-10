package fantasticfour.magiceight.parser;

import fantasticfour.magiceight.Magic8CommandObject;

public class ExitParser implements IParser{
	public Magic8CommandObject parse(String input) {
		String fn = "exit";
		return new Magic8CommandObject(fn, null, null, null, null, null, null);
	}
}
