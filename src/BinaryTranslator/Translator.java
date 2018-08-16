/*
 * Copyright (C) 2018 castellir
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package BinaryTranslator;

import java.util.Scanner;

/**
 * Convert from ASCII to binary
 *
 * @author castellir
 */
public class Translator {

    private final static Scanner KB_READER = new Scanner(System.in);

    private static String[] binVal; //will hold final strings

    /**
     * Invokes conversion and prints
     *
     * @param args
     */
    public static void main(String args[]) {
        convert();
        for (String val : binVal) {
            System.out.print(val);
        }
    }

    /**
     * Handles conversions
     */
    private static void convert() {
        System.out.print("\nEnter a string: ");

        String inpt = KB_READER.nextLine();
        char[] chars = inpt.toCharArray(); //splits up chars from string

        binVal = new String[chars.length];

        //converts each char into binary val and adds to string array
        for (int j = 0; j < binVal.length; j++) {
            int temp = (int) chars[j];
            binVal[j] = Integer.toBinaryString(temp);
        }
    }
}
