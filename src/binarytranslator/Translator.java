/*
 * Copyright (C) 2018 Ryan Castelli
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

import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * Runs Translator.
 *
 * @author NTropy
 * @version 8/26/2018
 */
public final class Translator extends JFrame {

    /**
     * Allows output to be scrolled through if too long.
     */
    private static final JScrollPane JSCRLP =
            new JScrollPane(TranslatorFrame.accessDisplay());

    /**
     * Panel for input/button.
     */
    private static final JPanel P1 = new JPanel();

    /**
     * Height of translator window.
     */
    private static final int WINDOW_HEIGHT = 600;
    /**
     * Width of translator window.
     */
    private static final int WINDOW_WIDTH = 800;

    /**
     * When Translator object is created configuration will be run.
     */
    private Translator() {
        configureTranslator();
    }

    /**
     * Configures GUI options.
     */
    public void configureTranslator() {

        //adds TranslatorFrame to GUI (this is the brains)
        add(new TranslatorFrame());

        setLayout(new BorderLayout()); //sets layout based on box-style borders

        setResizable(false); //prevents resizing of GUI (could break appearance)

        //adds scrollable display to main frame
        add(JSCRLP, BorderLayout.PAGE_START);
        //add button/input to main frame
        add(P1, BorderLayout.PAGE_END);

        setTitle("Binary Translator"); //sets title of window

        pack(); //allows all objects to be preferred size

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT); //sets size of entire GUI

        setLocationRelativeTo(null); //appears in center of primary display

        setFocusable(true); //can gain focus

        //closing GUI will terminate thread
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Adds components to JPanel.
     * @param flow
     *              FlowLayout for panel
     * @param txt
     *              Input box
     * @param button
     *              Button to send input
     */
    public static void addToPanel(final FlowLayout flow, final JTextField txt,
            final JButton button) {
        P1.setLayout(flow);
        P1.add(txt);
        P1.add(button);
    }

    /**
     * Creates the game runnable.
     * @param args
     *              Command line arguments
     */
    public static void main(final String[] args) {
        //allows long calculations to be performed without blocking GUI
        EventQueue.invokeLater(() -> {
            JFrame ex = new Translator(); //creates new Translator object
            ex.setVisible(true); //makes GUI appear on-screen
            ex.requestFocus(); //focus is granted to GUI
        });
    }
}
