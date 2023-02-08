package knt.lib.generic1;

/**
4
 * Pads a {@link String}.
5
 * <p>
6
 * The instances of classes that implement this interface are thread-safe and immutable.
7
 */

public interface StringPadder {
    /**
11
     * Returns a new {@link String} that right-aligns the characters in the specified String by padding them with spaces
12
     * on the left, for a specified total length.
13
     *
14
     * @param stringToPad the {@link String} to be padded
15
     * @param totalLength total length of the new {@link String}
16
     * @return the padded {@link String}
17
     */
    String padLeft(String stringToPad, int totalLength);
    /**
21
     * Returns a new {@link String} that right-aligns the characters in the specified String by padding them with the
22
     * specified char on the left, for a specified total length.
23
     *
24
     * @param stringToPad the {@link String} to be padded
25
     * @param totalLength total length of the new {@link String}
26
     * @return the padded {@link String}
27
     */
    String padLeft(String stringToPad, int totalLength, char paddingCharacter);
    /**
31
     * Returns a new {@link String} that left-aligns the characters in the specified String by padding them with spaces
32
     * on the left, for a specified total length.
33
     *
34
     * @param stringToPad the {@link String} to be padded
35
     * @param totalLength total length of the new {@link String}
36
     * @return the padded {@link String}
37
     */
    String padRight(String stringToPad, int totalLength);
    /**
41
     * Returns a new {@link String} that left-aligns the characters in the specified String by padding them with the
42
     * specified char on the left, for a specified total length.
43
     *
44
     * @param stringToPad the {@link String} to be padded
45
     * @param totalLength total length of the new {@link String}
46
     * @return the padded {@link String}
47
     */
    String padRight(String stringToPad, int totalLength, char paddingCharacter);
}