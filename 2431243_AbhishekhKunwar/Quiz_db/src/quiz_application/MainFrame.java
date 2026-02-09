package quiz_application;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainContainer;
    private static MainFrame instance;

    public MainFrame() {
        instance = this;
        setTitle("Quiz Pro - Ultimate Edition");
        setSize(1400, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(StyleConstants.BACKGROUND_COLOR);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);
        mainContainer.setOpaque(false);

        // Initial view
        showLogin();

        add(mainContainer);
        setVisible(true);
    }

    public static MainFrame getInstance() {
        return instance;
    }

    public void showLogin() {
        mainContainer.add(new LoginPanel(), "login");
        cardLayout.show(mainContainer, "login");
    }

    public void showQuiz(User user, String level) {
        mainContainer.add(new QuizApp(user, level), "quiz");
        cardLayout.show(mainContainer, "quiz");
    }

    public void showAdmin() {
        mainContainer.add(new AdminPanel(), "admin");
        cardLayout.show(mainContainer, "admin");
    }

    public void showLeaderboard() {
        mainContainer.add(new Leaderboard(), "leaderboard");
        cardLayout.show(mainContainer, "leaderboard");
    }

    public static void main(String[] args) {
        // Disable system Look and Feel to use our custom colors
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(MainFrame::new);
    }
}
