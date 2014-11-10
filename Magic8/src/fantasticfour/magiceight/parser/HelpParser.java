package fantasticfour.magiceight.parser;

import fantasticfour.magiceight.Magic8CommandObject;

public class HelpParser implements IParser{
	public Magic8CommandObject parse(String input) {
		String fn = "help";
		return new Magic8CommandObject(fn, null, null, null, null, null, null);
	}
}
