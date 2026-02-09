import javax.swing.*;

public class TestGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Test");
        frame.setSize(200, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel("Hello World", SwingConstants.CENTER);
        frame.add(label);
        frame.setVisible(true);
        System.out.println("GUI Visible");
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }
        System.out.println("Test Complete");
        System.exit(0);
    }
}
