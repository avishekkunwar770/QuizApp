package quiz_application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionFetcher {

    // Method to fetch questions by level
    public static List<Question> getQuestionsByLevel(String level) {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM questions WHERE level = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, level);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String questionText = resultSet.getString("question");
                String optionA = resultSet.getString("option_a");
                String optionB = resultSet.getString("option_b");
                String optionC = resultSet.getString("option_c");
                String optionD = resultSet.getString("option_d");
                char correctAnswer = resultSet.getString("correct_answer").charAt(0);

                questions.add(new Question(questionText, optionA, optionB, optionC, optionD, correctAnswer));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching questions: " + e.getMessage());
        }

        return questions;
    }
}