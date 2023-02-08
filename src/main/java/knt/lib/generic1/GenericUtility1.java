package knt.lib.generic1;

import java.util.ArrayList;

public class GenericUtility1 {

    public static int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public static ArrayList<Integer> getRandomArray(int min, int max, int count) {
        ArrayList<Integer> inputArray = new ArrayList<>();

        for (int counterr = 0; counterr < count; counterr++) {
            inputArray.add((int) Math.random() * (max - min + 1 + counterr) + min);
        }
        return inputArray;
    }
}