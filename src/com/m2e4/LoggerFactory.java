package com.m2e4;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerFactory {

    private LoggerFactory() {}

    /**
     * Instantiates a new Logger bound to a JTextPane
     * @param pane The text pane to bind to
     * @return New Logger instance
     */
    public static Logger makeLogger(JTextPane pane) {
        return new Logger(pane);
    }



    public static class Logger {
        private JTextPane pane;
        private static AttributeSet setInfo = StyleContext.getDefaultStyleContext()
                .addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLACK);
        static float[] colorGreen = Color.RGBtoHSB(0, 150, 0, null);
        private static AttributeSet setResult = StyleContext.getDefaultStyleContext()
                .addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.getHSBColor(colorGreen[0], colorGreen[1], colorGreen[2]));
        private static AttributeSet setDebug = StyleContext.getDefaultStyleContext()
                .addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLUE);
        private static AttributeSet setWarning = StyleContext.getDefaultStyleContext()
                .addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.ORANGE);
        private static AttributeSet setError = StyleContext.getDefaultStyleContext()
                .addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.RED);

        protected Logger(JTextPane pane) {
            this.pane = pane;
            ((DefaultCaret)pane.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        }

        /**
         * Prints a new line of colored text onto the bound text pane
         * @param text Text to be printed
         * @param level Error level that dictates the color of the text
         */
        public synchronized void println(String text, ErrorLevel level) {
            synchronized (pane) {
                AttributeSet set;
                switch (level) {
                    case INFO: set = setInfo; break;
                    case RESULT: set = setResult; break;
                    case DEBUG: set = setDebug; break;
                    case WARNING: set = setWarning; break;
                    case ERROR: set = setError; break;
                    default: set = setInfo;
                }

                try {
                    pane.getDocument().insertString(pane.getDocument().getLength(), String.format("[%s][%s] %s\r\n",
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")), level, text), set);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
        public void println(String text) {
            println(text, ErrorLevel.INFO);
        }

        /**
         * Saves a text log to the specified folder
         * @param folder The folder to save to
         */
        public void saveLog(String folder) {
            // Opens, and if necessary creates, a directory
            File dir = new File(folder);
            if (!dir.exists()) dir.mkdir();

            File[] files = dir.listFiles();

            try {
                // Opens and writes to a new file
                PrintWriter writer = new PrintWriter(
                        String.format("%s/log_%s_%d.txt", folder, LocalDate.now().toString(), files.length),
                        "UTF-8");
                String text = pane.getDocument().getText(0, pane.getDocument().getLength());
                writer.println(text);
                writer.close();
            } catch (FileNotFoundException | UnsupportedEncodingException | BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Used to indicate an error level for Logger instances
     * INFO = Black, RESULT = Green, DEBUG = Blue, WARNING = Orange, ERROR = Red
     */
    public enum ErrorLevel {
        INFO,
        RESULT,
        DEBUG,
        WARNING,
        ERROR
    }

}
