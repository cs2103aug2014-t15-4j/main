package fantasticfour.magiceight.parser;

import fantasticfour.magiceight.Magic8CommandObject;

public interface IParser {
    public abstract Magic8CommandObject parse(String str);
}
