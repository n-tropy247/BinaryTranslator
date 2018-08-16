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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Convert from ASCII to binary
 *
 * @author NTropy
 * @ver 8/16/2018
 */
public class Translator extends JPanel {

    private static ActionEvent sendOverride;

    private static JButton jbtnSend;

    private static JFrame jfrm;

    private static JTextArea jtaDisplay;

    private static JScrollPane jscrlp;

    private static JTextField jtfInput;

    private static String input;

    private static String[] binVal; //will hold final strings

    /**
     * Invokes conversion and prints
     *
     * @param args
     */
    public static void main(String args[]) {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException exe) {
            System.err.println(exe);
        }

        jfrm = new JFrame("Binary Translator");
        jfrm.setLayout(new BorderLayout()); //sets layout based on borders
        jfrm.setSize(800, 600); //sets size

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //gets screen dimensions

        double screenWidth = screenSize.getWidth(); //width of screen
        double screenHeight = screenSize.getHeight(); //height of screen

        jfrm.setLocation((int) screenWidth / 2 - 250, (int) screenHeight / 2 - 210); //sets location of chat to center

        jtaDisplay = new JTextArea(30, 50); //size of display
        jtaDisplay.setEditable(false); //display not editable
        jtaDisplay.setLineWrap(true); //lines wrap down

        jscrlp = new JScrollPane(jtaDisplay); //makes dispaly scrollable

        jtfInput = new JTextField(30); //sets character width of input field

        jbtnSend = new JButton("Send"); //sets button text

        jbtnSend.addActionListener(new handler()); //adds listener to button

        KeyListener key = new handler(); //adds handler for 'enter' key

        jtfInput.addKeyListener(key); //adds keylistener for 'enter'
        jfrm.add(jscrlp, BorderLayout.PAGE_START); //adds scrollable display to main frame

        sendOverride = new ActionEvent(jbtnSend, 1001, "Send"); //allows key to trigger same method as button

        JPanel p1 = new JPanel(); //panel for input/button

        p1.setLayout(new FlowLayout()); //flow layout for input/button
        p1.add(jtfInput, BorderLayout.LINE_END); //adds input to panel
        p1.add(jbtnSend, BorderLayout.LINE_END); //adds button to panel

        jfrm.add(p1, BorderLayout.PAGE_END); //add button/input to main frame

        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //kills application on close
        
        jfrm.setVisible(true); //makes frame visible

        jtaDisplay.setText(jtaDisplay.getText() + "Awaiting input...");
    }

    /**
     * Handles input and output to JFrame
     */
    private static class handler implements ActionListener, KeyListener {

        /**
         * On button press prompt GUI or input
         *
         * @param ae
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getActionCommand().equals("Send")) {
                input = jtfInput.getText();
                jtaDisplay.setText(jtaDisplay.getText() + "\nYou: " + input);
                char[] chars = input.toCharArray(); //splits up chars from string

                binVal = new String[chars.length];

                //converts each char into binary val and adds to string array
                for (int j = 0; j < binVal.length; j++) {
                    int temp = (int) chars[j];
                    binVal[j] = Integer.toBinaryString(temp);
                }
                String finalVal = "";
                for (String val : binVal) {
                    finalVal += val;
                }
                jtaDisplay.setText(jtaDisplay.getText() + "\nResult: " + finalVal +"\nAwaiting input...");
            }
            jtfInput.setText("");
            jfrm.repaint();
        }

        /**
         * Enter key counts as send button
         *
         * @param ke
         */
        @Override
        public void keyPressed(KeyEvent ke) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                actionPerformed(sendOverride);
            }
        }

        /**
         * Necessary override
         *
         * @param ke
         */
        @Override
        public void keyTyped(KeyEvent ke) {
        }

        /**
         * Necessary override
         *
         * @param ke
         */
        @Override
        public void keyReleased(KeyEvent ke) {
        }
    }
}
