package quiz_application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.sql.*;

public class QuizApp extends JPanel {
    private int currentQuestionIndex = 0;
    private List<Question> questions;
    private int score = 0;
    private JLabel questionLabel, questionCounterLabel, timerLabel;
    private JRadioButton optionA, optionB, optionC, optionD;
    private ButtonGroup optionsGroup;
    private JButton nextButton, submitButton, exitButton;
    private Timer timer;
    private int remainingTime = 30;
    private User currentUser;
    private String level;

    public QuizApp(User user, String level) {
        if (user == null || level == null) {
            JOptionPane.showMessageDialog(null, "Invalid user or level!");
            return;
        }

        this.currentUser = user;
        this.level = level;

        questions = QuestionFetcher.getQuestionsByLevel(level);
        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No questions found for the selected level!");
            return;
        }

        setOpaque(false);
        setLayout(new BorderLayout(20, 20));

        // Question Card
        JPanel questionCard = new JPanel(new BorderLayout(15, 15));
        questionCard.setBackground(Color.WHITE);
        questionCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(StyleConstants.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)));

        questionCounterLabel = new JLabel("Question 1 of " + questions.size(), JLabel.LEFT);
        questionCounterLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        questionCounterLabel.setForeground(StyleConstants.TEXT_PRIMARY);

        questionLabel = new JLabel("<html><div style='text-align: center; width: 900px;'>"
                + questions.get(currentQuestionIndex).getQuestion() + "</div></html>", JLabel.CENTER);
        questionLabel.setFont(StyleConstants.QUESTION_FONT);
        questionLabel.setForeground(StyleConstants.TEXT_PRIMARY);

        timerLabel = new JLabel("30", JLabel.RIGHT);
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        timerLabel.setForeground(StyleConstants.SUCCESS_COLOR);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(questionCounterLabel, BorderLayout.WEST);
        header.add(timerLabel, BorderLayout.EAST);

        questionCard.add(header, BorderLayout.NORTH);
        questionCard.add(questionLabel, BorderLayout.CENTER);
        add(questionCard, BorderLayout.NORTH);

        // Options Panel
        JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        optionsPanel.setOpaque(false);
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        optionA = createOptionRadioButton(questions.get(currentQuestionIndex).getOptionA(), "A");
        optionB = createOptionRadioButton(questions.get(currentQuestionIndex).getOptionB(), "B");
        optionC = createOptionRadioButton(questions.get(currentQuestionIndex).getOptionC(), "C");
        optionD = createOptionRadioButton(questions.get(currentQuestionIndex).getOptionD(), "D");

        optionsGroup = new ButtonGroup();
        optionsGroup.add(optionA);
        optionsGroup.add(optionB);
        optionsGroup.add(optionC);
        optionsGroup.add(optionD);

        optionsPanel.add(optionA);
        optionsPanel.add(optionB);
        optionsPanel.add(optionC);
        optionsPanel.add(optionD);
        add(optionsPanel, BorderLayout.CENTER);

        // Footer Actions
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        footer.setOpaque(false);

        nextButton = new CustomButton("Next Question",
            StyleConstants.PRIMARY_COLOR, Color.WHITE, StyleConstants.PRIMARY_DARK);

        submitButton = new CustomButton("Finish Quiz",
            StyleConstants.SUCCESS_COLOR, Color.WHITE, new Color(50, 150, 30));
        submitButton.setVisible(false);

        exitButton = new CustomButton("Quit Game",
            Color.WHITE, StyleConstants.DANGER_COLOR, new Color(255, 230, 230));
        exitButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(StyleConstants.DANGER_COLOR, 2),
                BorderFactory.createEmptyBorder(16, 38, 16, 38)));

        footer.add(exitButton);
        footer.add(nextButton);
        footer.add(submitButton);
        add(footer, BorderLayout.SOUTH);

        nextButton.addActionListener(e -> nextQuestion());
        submitButton.addActionListener(e -> submitQuiz());
        exitButton.addActionListener(e -> MainFrame.getInstance().showLogin());

        startTimer();
        setVisible(true);
    }

    private JRadioButton createOptionRadioButton(String text, String prefix) {
        JRadioButton rb = new JRadioButton("<html><div style='padding: 8px;'><b style='color: #1877F2; font-size: 18px;'>" + prefix + ".</b> <span style='font-size: 17px;'>" + text + "</span></div></html>");
        rb.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        rb.setForeground(StyleConstants.TEXT_PRIMARY);
        rb.setBackground(Color.WHITE);
        rb.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(StyleConstants.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(22, 30, 22, 30)));
        rb.setFocusPainted(false);
        rb.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        rb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!rb.isSelected()) {
                    rb.setBackground(StyleConstants.HOVER_COLOR);
                    rb.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(StyleConstants.PRIMARY_COLOR, 2),
                            BorderFactory.createEmptyBorder(21, 29, 21, 29)));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!rb.isSelected()) {
                    rb.setBackground(Color.WHITE);
                    rb.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(StyleConstants.BORDER_COLOR, 1),
                            BorderFactory.createEmptyBorder(22, 30, 22, 30)));
                }
            }
        });
        
        return rb;
    }

    private boolean checkAnswer() {
        Question q = questions.get(currentQuestionIndex);
        char correct = Character.toLowerCase(q.getCorrectAnswer());
        if (optionA.isSelected() && correct == 'a')
            return true;
        if (optionB.isSelected() && correct == 'b')
            return true;
        if (optionC.isSelected() && correct == 'c')
            return true;
        if (optionD.isSelected() && correct == 'd')
            return true;
        return false;
    }

    private void updateQuestion() {
        Question q = questions.get(currentQuestionIndex);
        questionLabel
                .setText("<html><div style='text-align: center; width: 900px;'>" + q.getQuestion() + "</div></html>");
        questionCounterLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());

        optionA.setText("<html><div style='padding: 8px;'><b style='color: #1877F2; font-size: 18px;'>A.</b> <span style='font-size: 17px;'>" + q.getOptionA() + "</span></div></html>");
        optionB.setText("<html><div style='padding: 8px;'><b style='color: #1877F2; font-size: 18px;'>B.</b> <span style='font-size: 17px;'>" + q.getOptionB() + "</span></div></html>");
        optionC.setText("<html><div style='padding: 8px;'><b style='color: #1877F2; font-size: 18px;'>C.</b> <span style='font-size: 17px;'>" + q.getOptionC() + "</span></div></html>");
        optionD.setText("<html><div style='padding: 8px;'><b style='color: #1877F2; font-size: 18px;'>D.</b> <span style='font-size: 17px;'>" + q.getOptionD() + "</span></div></html>");

        optionsGroup.clearSelection();
        remainingTime = 30;
        timerLabel.setText("30");
        timerLabel.setForeground(StyleConstants.SUCCESS_COLOR);

        if (currentQuestionIndex == questions.size() - 1) {
            nextButton.setVisible(false);
            submitButton.setVisible(true);
        }
    }

    private void nextQuestion() {
        if (optionsGroup.getSelection() == null) {
            JOptionPane.showMessageDialog(this, "Please select an answer to proceed.");
            return;
        }

        if (checkAnswer())
            score++;
        currentQuestionIndex++;

        if (currentQuestionIndex < questions.size()) {
            updateQuestion();
        }
    }

    private void submitQuiz() {
        if (optionsGroup.getSelection() == null) {
            JOptionPane.showMessageDialog(this, "Please select an answer before finishing.");
            return;
        }
        if (checkAnswer())
            score++;
        finishQuiz();
    }

    private void startTimer() {
        timer = new Timer(1000, e -> {
            remainingTime--;
            timerLabel.setText(String.valueOf(remainingTime));

            if (remainingTime <= 10) {
                timerLabel.setForeground(StyleConstants.DANGER_COLOR);
            } else if (remainingTime <= 20) {
                timerLabel.setForeground(StyleConstants.WARNING_COLOR);
            }

            if (remainingTime <= 0) {
                timer.stop();
                // Auto-move to next question when time expires
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    updateQuestion();
                    timer.start();
                } else {
                    finishQuiz();
                }
            }
        });
        timer.start();
    }

    private void finishQuiz() {
        timer.stop();
        JOptionPane.showMessageDialog(this, "Quiz Completed!\nYour Final Score: " + score + "/" + questions.size());
        saveUserScoreAndLevel();
        MainFrame.getInstance().showLeaderboard();
    }

    private void saveUserScoreAndLevel() {
        String query = "UPDATE users SET score = ?, level = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, score);
            preparedStatement.setString(2, level);
            preparedStatement.setInt(3, currentUser.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
        }
    }

    // Level selection moved to MainFrame or handled differently
    public static void main(String[] args) {
    }
}
