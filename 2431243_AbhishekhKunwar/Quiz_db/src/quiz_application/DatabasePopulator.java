package quiz_application;

import java.sql.*;

public class DatabasePopulator {
    
    public static void populateDatabase() {
        try (Connection conn = DBConnection.getConnection()) {
            // Clear existing questions
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM questions");
            
            // Populate Beginner questions
            addBeginnerQuestions(conn);
            
            // Populate Intermediate questions
            addIntermediateQuestions(conn);
            
            // Populate Advanced questions
            addAdvancedQuestions(conn);
            
            System.out.println("Database populated successfully with 60 questions!");
            
            // Verify counts
            ResultSet rs = stmt.executeQuery("SELECT level, COUNT(*) as count FROM questions GROUP BY level");
            System.out.println("\nQuestion counts by level:");
            while (rs.next()) {
                System.out.println(rs.getString("level") + ": " + rs.getInt("count") + " questions");
            }
            
        } catch (SQLException e) {
            System.err.println("Error populating database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void addBeginnerQuestions(Connection conn) throws SQLException {
        String[][] questions = {
            {"What does HTML stand for?", "Hyper Text Markup Language", "High Tech Modern Language", "Home Tool Markup Language", "Hyperlinks and Text Markup Language", "a"},
            {"Which programming language is known as the language of the web?", "Python", "JavaScript", "Java", "C++", "b"},
            {"What does CSS stand for?", "Computer Style Sheets", "Cascading Style Sheets", "Creative Style Sheets", "Colorful Style Sheets", "b"},
            {"Which symbol is used for single-line comments in Java?", "//", "/*", "#", "--", "a"},
            {"What is the correct file extension for Python files?", ".python", ".pt", ".py", ".pyt", "c"},
            {"Which data type is used to store text in most programming languages?", "int", "float", "string", "boolean", "c"},
            {"What does SQL stand for?", "Structured Query Language", "Simple Question Language", "Standard Query Language", "System Query Language", "a"},
            {"Which HTML tag is used to create a hyperlink?", "<link>", "<a>", "<href>", "<url>", "b"},
            {"What is the result of 10 + 5 * 2 in most programming languages?", "30", "20", "25", "15", "b"},
            {"Which keyword is used to define a function in Python?", "function", "def", "func", "define", "b"},
            {"What does RAM stand for?", "Random Access Memory", "Read Access Memory", "Rapid Access Memory", "Run Access Memory", "a"},
            {"Which operator is used for equality comparison in most languages?", "=", "==", "===", "equals", "b"},
            {"What is the binary representation of decimal 5?", "101", "110", "100", "111", "a"},
            {"Which tag is used to display images in HTML?", "<image>", "<img>", "<picture>", "<src>", "b"},
            {"What does IDE stand for?", "Integrated Development Environment", "Internet Development Environment", "Internal Development Environment", "Integrated Design Environment", "a"},
            {"Which loop is guaranteed to execute at least once?", "for loop", "while loop", "do-while loop", "foreach loop", "c"},
            {"What is the correct syntax to print in Python?", "echo()", "print()", "console.log()", "printf()", "b"},
            {"Which data structure uses LIFO principle?", "Queue", "Stack", "Array", "Tree", "b"},
            {"What does URL stand for?", "Universal Resource Locator", "Uniform Resource Locator", "Universal Reference Link", "Uniform Reference Locator", "b"},
            {"Which symbol is used to access object properties in JavaScript?", "->", ".", "::", "@", "b"}
        };
        
        insertQuestions(conn, questions, "Beginner");
    }
    
    private static void addIntermediateQuestions(Connection conn) throws SQLException {
        String[][] questions = {
            {"What is the time complexity of binary search?", "O(n)", "O(log n)", "O(n^2)", "O(1)", "b"},
            {"Which design pattern ensures a class has only one instance?", "Factory", "Singleton", "Observer", "Strategy", "b"},
            {"What does OOP stand for?", "Object Oriented Programming", "Object Oriented Process", "Operational Oriented Programming", "Object Order Programming", "a"},
            {"Which HTTP method is idempotent?", "POST", "GET", "PATCH", "All of the above", "b"},
            {"What is polymorphism in OOP?", "Multiple forms of a method", "Data hiding", "Code reusability", "Memory management", "a"},
            {"Which data structure is best for implementing a priority queue?", "Array", "Linked List", "Heap", "Stack", "c"},
            {"What does REST stand for in web services?", "Representational State Transfer", "Remote State Transfer", "Representational System Transfer", "Remote System Transfer", "a"},
            {"Which sorting algorithm has the best average time complexity?", "Bubble Sort", "Quick Sort", "Selection Sort", "Insertion Sort", "b"},
            {"What is encapsulation in OOP?", "Wrapping data and methods together", "Inheriting properties", "Multiple inheritance", "Method overloading", "a"},
            {"Which protocol is used for secure web communication?", "HTTP", "FTP", "HTTPS", "SMTP", "c"},
            {"What is the purpose of a constructor in OOP?", "Destroy objects", "Initialize objects", "Copy objects", "Compare objects", "b"},
            {"Which database type is MongoDB?", "Relational", "NoSQL", "Graph", "Hierarchical", "b"},
            {"What does API stand for?", "Application Programming Interface", "Advanced Programming Interface", "Application Process Interface", "Advanced Process Interface", "a"},
            {"Which keyword is used for inheritance in Java?", "implements", "extends", "inherits", "derives", "b"},
            {"What is the space complexity of merge sort?", "O(1)", "O(log n)", "O(n)", "O(n^2)", "c"},
            {"Which pattern is used to create objects without specifying exact class?", "Singleton", "Factory", "Prototype", "Builder", "b"},
            {"What is a closure in JavaScript?", "Function with access to outer scope", "Closed function", "Private method", "Static function", "a"},
            {"Which SQL clause is used to filter grouped results?", "WHERE", "HAVING", "FILTER", "GROUP BY", "b"},
            {"What is the default port for HTTP?", "443", "8080", "80", "3000", "c"},
            {"Which method is used to prevent method overriding in Java?", "static", "final", "private", "abstract", "b"}
        };
        
        insertQuestions(conn, questions, "Intermediate");
    }
    
    private static void addAdvancedQuestions(Connection conn) throws SQLException {
        String[][] questions = {
            {"What is the time complexity of Dijkstra algorithm using binary heap?", "O(V^2)", "O(E log V)", "O(V log E)", "O(E + V)", "b"},
            {"Which consensus algorithm does Bitcoin use?", "Proof of Stake", "Proof of Work", "Byzantine Fault Tolerance", "Raft", "b"},
            {"What is the CAP theorem in distributed systems?", "Consistency, Availability, Partition tolerance", "Concurrency, Atomicity, Performance", "Cache, API, Protocol", "Cluster, Application, Processing", "a"},
            {"Which garbage collection algorithm does Java primarily use?", "Reference Counting", "Mark and Sweep", "Generational Collection", "Manual Collection", "c"},
            {"What is the purpose of a semaphore in concurrent programming?", "Memory allocation", "Process synchronization", "Thread creation", "Deadlock prevention", "b"},
            {"Which data structure is used in depth-first search?", "Queue", "Stack", "Heap", "Hash Table", "b"},
            {"What is eventual consistency in distributed databases?", "Immediate consistency", "Consistency achieved over time", "No consistency", "Strong consistency", "b"},
            {"Which algorithm is used for pattern matching in strings?", "Dijkstra", "KMP Algorithm", "Binary Search", "Merge Sort", "b"},
            {"What is a race condition in multithreading?", "Fast execution", "Uncontrolled access to shared resource", "Thread priority", "Deadlock", "b"},
            {"Which tree traversal uses a queue?", "Preorder", "Inorder", "Postorder", "Level-order", "d"},
            {"What is the Byzantine Generals Problem?", "Sorting problem", "Consensus in distributed systems", "Graph traversal", "Memory management", "b"},
            {"Which technique is used to prevent SQL injection?", "Encryption", "Prepared Statements", "Hashing", "Compression", "b"},
            {"What is the time complexity of building a heap?", "O(n log n)", "O(n)", "O(log n)", "O(n^2)", "b"},
            {"Which protocol ensures reliable data transfer in TCP/IP?", "UDP", "TCP", "IP", "HTTP", "b"},
            {"What is a deadlock in operating systems?", "Fast execution", "Circular wait for resources", "Memory leak", "Thread termination", "b"},
            {"Which algorithm is used in Git for version control?", "Merkle Tree", "Binary Tree", "AVL Tree", "Red-Black Tree", "a"},
            {"What is the purpose of a load balancer?", "Increase storage", "Distribute traffic", "Encrypt data", "Compress files", "b"},
            {"Which data structure provides O(1) average case for insert, delete, search?", "Array", "Linked List", "Hash Table", "Binary Tree", "c"},
            {"What is the actor model in concurrent programming?", "Thread-based model", "Message-passing concurrency", "Lock-based synchronization", "Shared memory model", "b"},
            {"Which algorithm is used for finding strongly connected components?", "Dijkstra", "Kosaraju Algorithm", "Prim Algorithm", "Kruskal Algorithm", "b"}
        };
        
        insertQuestions(conn, questions, "Advanced");
    }
    
    private static void insertQuestions(Connection conn, String[][] questions, String level) throws SQLException {
        String sql = "INSERT INTO questions (question, option_a, option_b, option_c, option_d, correct_answer, level) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        for (String[] q : questions) {
            pstmt.setString(1, q[0]);
            pstmt.setString(2, q[1]);
            pstmt.setString(3, q[2]);
            pstmt.setString(4, q[3]);
            pstmt.setString(5, q[4]);
            pstmt.setString(6, q[5]);
            pstmt.setString(7, level);
            pstmt.executeUpdate();
        }
    }
    
    public static void main(String[] args) {
        populateDatabase();
    }
}
