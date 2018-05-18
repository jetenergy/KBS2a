package com.m2e4;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
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
        public void println(String text, ErrorLevel level) {
            pane.setCaretPosition(pane.getDocument().getLength());
            switch (level) {
                case INFO: pane.setCharacterAttributes(setInfo, false); break;
                case DEBUG: pane.setCharacterAttributes(setDebug, false); break;
                case WARNING: pane.setCharacterAttributes(setWarning, false); break;
                case ERROR: pane.setCharacterAttributes(setError, false); break;
            }

            boolean editable = true;
            if (!pane.isEditable()) editable = false;

            if (!editable) pane.setEditable(true);
            pane.replaceSelection(String.format("[%s][%s] %s\r\n",
                    LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME), level, text));
            if (!editable) pane.setEditable(false);
        }
        public void println(String text) {
            println(text, ErrorLevel.INFO);
        }
    }

    /**
     * Used to indicate an error level for Logger instances
     * INFO = Black, DEBUG = Blue, WARNING = Orange, ERROR = Red
     */
    public enum ErrorLevel {
        INFO,
        DEBUG,
        WARNING,
        ERROR
    }

}
