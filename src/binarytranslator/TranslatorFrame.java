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
package binarytranslator;

import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.util.ArrayList;
import java.util.List;

/**
 * Convert from ASCII to binary and vice versa.
 *
 * @author NTropy
 * @version 8/26/2018
 */
public class TranslatorFrame extends JPanel {


    /**
     * Override ActionEvent if "enter" key pressed.
     */
    private static ActionEvent sendOverride;

    /**
     * ActionEvent ID for button override.
     */
    private static final int ACTION_ID = 1001;

    /**
     * Length of a byte.
     */
    private static final int BYTE_LENGTH = 8;

    /**
     * Used to split integers into digits.
     */
    private static final int DIGIT_SPLIT = 10;

    /**
     * Length of input box.
     */
     private static final int INPUT_LENGTH = 30;

    /**
     * Height of output box.
     */
    private static final int OUTPUT_WIDTH = 30;

    /**
     * Width of output box.
     */
    private static final int OUTPUT_HEIGHT = 50;

    /**
     * Integer value of translation.
     */
    private static int intVal;

    /**
     * Button to send input to program.
     */
    private static JButton jbtnSend;

    /**
     * Output box.
     */
    private static JTextArea jtaDisplay;

    /**
     * Input box.
     */
    private static JTextField jtfInput;

    /**
     * User input.
     */
    private static String input;

    /**
     * Will hold converted values.
     */
    private static String[] binVal;

    /**
     * When TranslatorFrame object is added to a JFrame, initialize will be run.
     */
    public TranslatorFrame() {
        initialize();
    }

    /**
     * Passes output display to JFrame.
     * @return Display box
     */
    public static JTextArea accessDisplay() {
        //specifies size of output box
        jtaDisplay = new JTextArea(OUTPUT_WIDTH, OUTPUT_HEIGHT);

        //prevents editing of output box
        jtaDisplay.setEditable(false);

        //lines wrap down to maintain single string output
        //(unless otherwise specified)
        jtaDisplay.setLineWrap(true);
        return jtaDisplay;
    }

    /**
     * Configures panel of objects.
     */
    private static void initialize() {

        //attempts to use Nimbus appearance of GUI
        try {
            for (UIManager.LookAndFeelInfo info
                    : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException
                | InstantiationException
                | UnsupportedLookAndFeelException exe) {
            //if desired look of GUI is unavailable, send error code to
            //standard err output (be it IDE or console)
            System.err.println(exe);
        }

        //sets character width of input field
        jtfInput = new JTextField(INPUT_LENGTH);

        jbtnSend = new JButton("Send"); //creates button with specified text

        jbtnSend.addActionListener(new Handler()); //adds listener to button

        KeyListener key = new Handler(); //adds handler for key presses

        jtfInput.addKeyListener(key); //adds keylistener for 'enter'

        //allows key to trigger same method as button
        sendOverride = new ActionEvent(jbtnSend, ACTION_ID, "Send");

        Translator.addToPanel(new FlowLayout(), jtfInput, jbtnSend);

        //first output to display
        jtaDisplay.setText(jtaDisplay.getText() + "Awaiting input...");
    }

    /**
     * Handles input and output to GUI.
     */
    private static class Handler implements ActionListener, KeyListener {

        /**
         * On button press grab input from GUI and perform operation.
         *
         * @param ae
         *          ActionEvent sent to handler class
         */
        @Override
        public void actionPerformed(final ActionEvent ae) {

            //if "Send" button or "enter" key pressed
            if (ae.getActionCommand().equals("Send")) {

                //grabs input from box
                input = jtfInput.getText();

                //splits up chars from string
                char[] chars = input.toCharArray();

                //shows your input on-screen
                jtaDisplay.setText(jtaDisplay.getText() + "\nYou: " + input);

                //boolean to track if valid binary number (in byte format)
                boolean isValBin = true;
                //boolean to track if number
                boolean isNum;
                //boolean to track if number could be binary
                boolean isBin = false;

                double numVal = 0; //number value of input

                String finalVal = ""; //will be converted string

                //tries to convert to number to verify if valid number
                try {
                    numVal = Double.valueOf(input);
                    isNum = true;
                    //iterates and determines if number contains anything
                    //other than '1' or '0'
                    for (int j = 0; j < input.length(); j++) {
                        isBin = true;
                        if (!(chars[j] == '1' || chars[j] == '0')) {
                            //invalid char detected, can't be binary
                            isBin = false;
                        }
                    }
                  //can be binary even if invalid number
                  //(could have spaces between values)
                } catch (NumberFormatException e) {
                    isNum = false;

                    //iterates and determines if number contains anything
                    //other than '1' or '0' or ' '
                    for (int j = 0; j < input.length(); j++) {
                        isBin = true;
                        if (!(chars[j] == '1' || chars[j] == '0'
                                || chars[j] == ' ')) {
                            //invalid char detected, can't be binary
                            isBin = false;
                        }
                    }
                }
                if (!isNum && !isBin) {
                    //stores individual converted binary values
                    binVal = new String[chars.length];
                    //converts each char into binary val
                    //and adds to string array
                    for (int j = 0; j < binVal.length; j++) {
                        int temp = (int) chars[j];
                        binVal[j] = Integer.toBinaryString(temp);
                    }
                    //resets finalVal
                    finalVal = "";
                    //iterates through array of new vals
                    //and concatenates to finalVal string
                    for (String val : binVal) {
                        //ensures that each returned value is in byte format
                        while (val.length() % BYTE_LENGTH != 0) {
                            val = "0" + val;
                        }
                        finalVal += val;
                    }
                } else if (isNum && !isBin) {
                    intVal = (int) numVal;
                    //stores individual converted binary values
                    binVal = new String[chars.length];
                    //temp storage for each digit
                    int[] dbs = new int[chars.length];
                    //splits into individual digits
                    for (int j = 0; j < chars.length; j++) {
                        dbs[j] = (int) numVal % DIGIT_SPLIT;
                        numVal /= DIGIT_SPLIT;
                    }
                    //converts each char into binary val
                    //and adds to string array
                    for (int j = 0; j < binVal.length; j++) {
                        double temp = dbs[j];
                        binVal[j] = Integer.toBinaryString((int) temp);
                    }
                    //resets finalVal
                    finalVal = "";
                    //iterates through array of new vals
                    //and concatenates to finalVal string
                    for (String val : binVal) {
                        while (val.length() % BYTE_LENGTH != 0) {
                            val = "0" + val;
                        }
                        finalVal += val;
                    }
                } else if (isBin) {
                    //removes spaces from input
                    String removeSpace = input.replaceAll("\\s+", "");
                    //if entered value is detected
                    //as binary but not in byte format, nothing is done
                    if (removeSpace.length() % BYTE_LENGTH != 0) {
                        jtaDisplay.setText(jtaDisplay.getText()
                                + "\nNot a valid binary string."
                                        + "Must be in byte format");
                        isValBin = false;
                    }
                    if (isValBin) {
                        //List of strings that will be used to split entered
                        //binary string into individual bytes
                        List<String> split = new ArrayList<>((
                                removeSpace.length() + (BYTE_LENGTH - 1))
                                / BYTE_LENGTH);
                        //adds each val to list
                        for (int j = 0; j < removeSpace.length();
                                j += BYTE_LENGTH) {
                            split.add(removeSpace.substring(j,
                                    Math.min(removeSpace.length(),
                                            j + BYTE_LENGTH)));
                        }
                        //converts split vals into strings to be printed
                        //and adds to final val
                        finalVal = split.stream().map((split1) -> (char)
                                Integer.parseInt(split1, 2)).map((tempBinCode)
                                -> tempBinCode.toString()
                        ).reduce(finalVal, String::concat);
                        split.forEach((temp) -> {
                            intVal += Integer.parseInt(temp, 2);
                        });
                    }
                }
                //will work unless entered value was binary and proved invalid
                if (isValBin && !isNum) {
                    jtaDisplay.setText(jtaDisplay.getText() + "\nResult: "
                            + "\nASCII Value: " + finalVal
                            + "\nAwaiting input...");
                } else if (isValBin && (isNum || isBin)) {
                    jtaDisplay.setText(jtaDisplay.getText() + "\nResult: "
                            + "\nASCII Value: " + finalVal + "\nInt Value: "
                            + intVal + "\nAwaiting input...");
                }
            }
            //clears input box on send
            jtfInput.setText("");
        }

        /**
         * Enter key counts as send button.
         *
         * @param ke event code of key typed
         */
        @Override
        public void keyPressed(final KeyEvent ke) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                //sendOverride is action code for "send" button
                actionPerformed(sendOverride);
            }
        }

        /**
         * Necessary override, unused.
         *
         * @param ke event code of key typed
         */
        @Override
        public void keyTyped(final KeyEvent ke) {
        }

        /**
         * Necessary override, unused.
         *
         * @param ke event code of key typed
         */
        @Override
        public void keyReleased(final KeyEvent ke) {
        }
    }
}
