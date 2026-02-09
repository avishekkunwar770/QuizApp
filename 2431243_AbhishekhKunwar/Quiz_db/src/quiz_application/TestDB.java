package quiz_application;

import java.util.List;
import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        System.out.println("Testing DB Connection...");
        try {
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
                System.out.println("Connection Successful!");
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Testing QuestionFetcher...");
        try {
            List<Question> questions = QuestionFetcher.getQuestionsByLevel("Beginner");
            System.out.println("Fetched " + questions.size() + " questions.");
            for (Question q : questions) {
                System.out.println("Q: " + q.getQuestion());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
