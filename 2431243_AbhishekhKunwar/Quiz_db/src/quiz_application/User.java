package quiz_application;

public class User {
    private int id;
    private String name;
    private int age;
    private int score;
    private String level;

    public User(int id, String name, int age, int score, String level) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.score = score;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getScore() {
        return score;
    }

    public String getLevel() {
        return level;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}