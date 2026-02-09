package quiz_application;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class StyleConstants {
    // Professional Facebook-Inspired Color Palette
    public static final Color PRIMARY_COLOR = new Color(24, 119, 242); // Facebook Blue
    public static final Color PRIMARY_DARK = new Color(8, 102, 255); // Darker Blue
    public static final Color SECONDARY_COLOR = new Color(66, 103, 178); // Deep Blue
    public static final Color ACCENT_COLOR = new Color(0, 149, 246); // Instagram Blue
    public static final Color BACKGROUND_COLOR = new Color(240, 242, 245); // Light Gray Background
    public static final Color CARD_BACKGROUND = new Color(255, 255, 255); // Pure White Cards
    public static final Color CARD_HOVER = new Color(245, 247, 250); // Light Hover
    public static final Color TEXT_PRIMARY = Color.BLACK; // Pure Black for all text
    public static final Color TEXT_SECONDARY = Color.BLACK; // Pure Black for secondary text too
    public static final Color SUCCESS_COLOR = new Color(66, 183, 42); // Green
    public static final Color DANGER_COLOR = new Color(242, 63, 66); // Red
    public static final Color WARNING_COLOR = new Color(255, 152, 0); // Orange
    public static final Color HOVER_COLOR = new Color(232, 240, 254); // Light Blue Hover
    public static final Color BORDER_COLOR = new Color(218, 220, 224); // Light Border

    // Professional Fonts - Larger and Cleaner
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 48);
    public static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 18);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 17);
    public static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    public static final Font QUESTION_FONT = new Font("Segoe UI", Font.BOLD, 24);

    // Professional Borders
    public static final Border CARD_BORDER = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(40, 50, 40, 50));
    
    public static final Border ROUNDED_BORDER = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 25, 20, 25));

    // Professional Button Styling
    public static void styleButton(JButton button) {
        button.setFont(BUTTON_FONT);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(18, 40, 18, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_DARK);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
    }

    public static void styleSecondaryButton(JButton button) {
        button.setFont(BUTTON_FONT);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 2),
                BorderFactory.createEmptyBorder(16, 38, 16, 38)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(HOVER_COLOR);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                        BorderFactory.createEmptyBorder(16, 38, 16, 38)));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 2),
                        BorderFactory.createEmptyBorder(16, 38, 16, 38)));
            }
        });
    }
    
    public static void styleTextField(JTextField field) {
        field.setFont(BODY_FONT);
        field.setForeground(TEXT_PRIMARY);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));
        field.setCaretColor(PRIMARY_COLOR);
    }
    
    public static void stylePasswordField(JPasswordField field) {
        field.setFont(BODY_FONT);
        field.setForeground(TEXT_PRIMARY);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));
        field.setCaretColor(PRIMARY_COLOR);
    }
}
