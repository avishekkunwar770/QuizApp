package quiz_application;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.sql.*;

public class Leaderboard extends JPanel {

    public Leaderboard() {
        setOpaque(false);
        setLayout(new BorderLayout(20, 20));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(StyleConstants.PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("TOP SCORES", JLabel.CENTER);
        titleLabel.setFont(StyleConstants.TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Create a table to display leaderboard
        String[] columns = { "Rank", "Player Name", "Score", "Difficulty" };
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try {
            ResultSet rs = DBConnection.getLeaderboard();
            int rank = 1;
            while (rs.next()) {
                String player = rs.getString("name");
                int score = rs.getInt("score");
                String difficulty = rs.getString("difficulty"); // Matches query alias "AS difficulty"
                model.addRow(new Object[] { rank++, player, score, difficulty });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching leaderboard: " + e.getMessage());
        }

        JTable leaderboardTable = new JTable(model);
        leaderboardTable.setFont(StyleConstants.BODY_FONT);
        leaderboardTable.setRowHeight(40);
        leaderboardTable.getTableHeader().setFont(StyleConstants.BUTTON_FONT);
        leaderboardTable.getTableHeader().setBackground(StyleConstants.SECONDARY_COLOR);
        leaderboardTable.getTableHeader().setForeground(Color.WHITE);
        leaderboardTable.setGridColor(new Color(240, 240, 240));
        leaderboardTable.setSelectionBackground(StyleConstants.ACCENT_COLOR);

        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        scrollPane.setBackground(StyleConstants.BACKGROUND_COLOR);
        add(scrollPane, BorderLayout.CENTER);

        JButton homeButton = new JButton("Return to Home");
        StyleConstants.styleButton(homeButton);
        homeButton.addActionListener(e -> {
            MainFrame.getInstance().showLogin();
        });

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setOpaque(false);
        footer.add(homeButton);
        add(footer, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
    }
}
