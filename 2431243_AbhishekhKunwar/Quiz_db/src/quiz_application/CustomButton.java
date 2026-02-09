package quiz_application;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {
    private Color bgColor;
    private Color fgColor;
    private Color hoverColor;
    
    public CustomButton(String text, Color background, Color foreground, Color hover) {
        super(text);
        this.bgColor = background;
        this.fgColor = foreground;
        this.hoverColor = hover;
        
        setFont(StyleConstants.BUTTON_FONT);
        setForeground(fgColor);
        setBackground(bgColor);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(18, 40, 18, 40));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setOpaque(true);
        setContentAreaFilled(true);
        setBorderPainted(false);
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(bgColor);
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Force paint background
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());
        
        // Paint text
        g2.setColor(getForeground());
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        g2.drawString(getText(), x, y);
        
        g2.dispose();
    }
}
