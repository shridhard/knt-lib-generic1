package knt.lib.generic1;

/**
4
 * Factory for creating instances of {@link StringPadder}.
5
 */
public final class StringPadderFactory {
    private StringPadderFactory() {
    }
    /**
12
     * Creates an instance of {@link StringPadder}.
13
     *
14
     * @return the new instance
15
     */
    public static StringPadder createStringPadder() {
        return new StringPadderImpl();
    }

}