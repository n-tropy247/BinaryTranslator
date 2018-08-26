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

package BinaryTranslator;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * Runs Translator
 * @author NTropy
 * @ver 8/26/2018
 */
public final class Translator extends JFrame {
    
    /**
     * When Translator object is created configuration will be run
     */
    private Translator() {
        configureTranslator();
    }
    
    public void configureTranslator() {
        
        add(new TranslatorFrame()); //adds TranslatorFrame to GUI (this is the brains)
        
        setLayout(new BorderLayout()); //sets layout based on box-style borders
        
        setResizable(false); //prevents resizing of GUI (could break appearance)
        
        setSize(800, 600); //sets size of entire GUI
        
        add(TranslatorFrame.jscrlp, BorderLayout.PAGE_START); //adds scrollable display to main frame
        add(TranslatorFrame.p1, BorderLayout.PAGE_END); //add button/input to main frame
        
        pack(); //allows all objects to be preferred size
        
        setTitle("Binary Translator"); //sets title of window
        
        setLocationRelativeTo(null); //appears in center of primary display
        
        setFocusable(true); //can gain focus
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closing GUI will terminate thread
    }
    
    /**
     * Creates the game runnable
     * @param args
     */
    public static void main(String args[]) {
        EventQueue.invokeLater(() -> { //allows long calculations to be performed without blocking GUI
            JFrame ex = new Translator(); //creates new Translator object
            
            ex.setVisible(true); //makes GUI appear on-screen
            
            ex.requestFocus(); //focus is granted to GUI
        });
    }
}
