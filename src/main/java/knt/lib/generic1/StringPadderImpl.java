package knt.lib.generic1;

/**
 * This class consists of implementation methods for String padding
 */
public class StringPadderImpl implements StringPadder {

    StringPadderImpl() {
    }

    @Override
    public String padLeft(String stringToPad, int totalLength) {
        if (stringToPad == null)
            return null;
        else
            return padLeft(stringToPad, totalLength, ' ');
    }

    @Override

    public String padLeft(String stringToPad, int totalLength, char paddingCharacter) {
        if (stringToPad == null)
            return null;
        else
            return getStringToBeAdded(stringToPad, totalLength, paddingCharacter) + stringToPad;

    }

    @Override
    public String padRight(String stringToPad, int totalLength) {
        if (stringToPad == null)
            return null;
        else
            return padRight(stringToPad, totalLength, ' ');
    }

    @Override
    public String padRight(String stringToPad, int totalLength, char paddingCharacter) {
        if (stringToPad == null)
            return null;
        else
            return stringToPad + getStringToBeAdded(stringToPad, totalLength, paddingCharacter);
    }

    private String getStringToBeAdded(String stringToPad, int totalLength, char paddingCharacter) {
        if (stringToPad == null)
            return null;
        int quantity = totalLength - stringToPad.length();
        if (quantity > 0) {
            return Character.toString(paddingCharacter).repeat(quantity);
        } else {
            return "";
        }
    }
}
