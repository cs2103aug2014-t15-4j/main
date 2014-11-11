package fantasticfour.magiceight.parser;

import fantasticfour.magiceight.Magic8CommandObject;

//@author A0080527H
public class RedoParser implements IParser{
	public Magic8CommandObject parse(String input) {
		String fn = "redo";
		return new Magic8CommandObject(fn, null, null, null, null, null, null);
	}
}
