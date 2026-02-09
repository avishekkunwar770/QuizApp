-- Create database and tables if they don't exist
CREATE DATABASE IF NOT EXISTS quizDB;
USE quizDB;

-- Create questions table
CREATE TABLE IF NOT EXISTS questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question TEXT NOT NULL,
    option_a VARCHAR(255) NOT NULL,
    option_b VARCHAR(255) NOT NULL,
    option_c VARCHAR(255) NOT NULL,
    option_d VARCHAR(255) NOT NULL,
    correct_answer CHAR(1) NOT NULL,
    level VARCHAR(50) NOT NULL
);

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    score INT DEFAULT 0,
    level VARCHAR(50)
);

-- Clear existing questions
DELETE FROM questions;

-- Insert 20 Beginner Level Questions
INSERT INTO questions (question, option_a, option_b, option_c, option_d, correct_answer, level) VALUES
('What does HTML stand for?', 'Hyper Text Markup Language', 'High Tech Modern Language', 'Home Tool Markup Language', 'Hyperlinks and Text Markup Language', 'a', 'Beginner'),
('Which programming language is known as the language of the web?', 'Python', 'JavaScript', 'Java', 'C++', 'b', 'Beginner'),
('What does CSS stand for?', 'Computer Style Sheets', 'Cascading Style Sheets', 'Creative Style Sheets', 'Colorful Style Sheets', 'b', 'Beginner'),
('Which symbol is used for single-line comments in Java?', '//', '/*', '#', '--', 'a', 'Beginner'),
('What is the correct file extension for Python files?', '.python', '.pt', '.py', '.pyt', 'c', 'Beginner'),
('Which data type is used to store text in most programming languages?', 'int', 'float', 'string', 'boolean', 'c', 'Beginner'),
('What does SQL stand for?', 'Structured Query Language', 'Simple Question Language', 'Standard Query Language', 'System Query Language', 'a', 'Beginner'),
('Which HTML tag is used to create a hyperlink?', '<link>', '<a>', '<href>', '<url>', 'b', 'Beginner'),
('What is the result of 10 + 5 * 2 in most programming languages?', '30', '20', '25', '15', 'b', 'Beginner'),
('Which keyword is used to define a function in Python?', 'function', 'def', 'func', 'define', 'b', 'Beginner'),
('What does RAM stand for?', 'Random Access Memory', 'Read Access Memory', 'Rapid Access Memory', 'Run Access Memory', 'a', 'Beginner'),
('Which operator is used for equality comparison in most languages?', '=', '==', '===', 'equals', 'b', 'Beginner'),
('What is the binary representation of decimal 5?', '101', '110', '100', '111', 'a', 'Beginner'),
('Which tag is used to display images in HTML?', '<image>', '<img>', '<picture>', '<src>', 'b', 'Beginner'),
('What does IDE stand for?', 'Integrated Development Environment', 'Internet Development Environment', 'Internal Development Environment', 'Integrated Design Environment', 'a', 'Beginner'),
('Which loop is guaranteed to execute at least once?', 'for loop', 'while loop', 'do-while loop', 'foreach loop', 'c', 'Beginner'),
('What is the correct syntax to print in Python?', 'echo()', 'print()', 'console.log()', 'printf()', 'b', 'Beginner'),
('Which data structure uses LIFO principle?', 'Queue', 'Stack', 'Array', 'Tree', 'b', 'Beginner'),
('What does URL stand for?', 'Universal Resource Locator', 'Uniform Resource Locator', 'Universal Reference Link', 'Uniform Reference Locator', 'b', 'Beginner'),
('Which symbol is used to access object properties in JavaScript?', '->', '.', '::', '@', 'b', 'Beginner');

-- Insert 20 Intermediate Level Questions
INSERT INTO questions (question, option_a, option_b, option_c, option_d, correct_answer, level) VALUES
('What is the time complexity of binary search?', 'O(n)', 'O(log n)', 'O(n^2)', 'O(1)', 'b', 'Intermediate'),
('Which design pattern ensures a class has only one instance?', 'Factory', 'Singleton', 'Observer', 'Strategy', 'b', 'Intermediate'),
('What does OOP stand for?', 'Object Oriented Programming', 'Object Oriented Process', 'Operational Oriented Programming', 'Object Order Programming', 'a', 'Intermediate'),
('Which HTTP method is idempotent?', 'POST', 'GET', 'PATCH', 'All of the above', 'b', 'Intermediate'),
('What is polymorphism in OOP?', 'Multiple forms of a method', 'Data hiding', 'Code reusability', 'Memory management', 'a', 'Intermediate'),
('Which data structure is best for implementing a priority queue?', 'Array', 'Linked List', 'Heap', 'Stack', 'c', 'Intermediate'),
('What does REST stand for in web services?', 'Representational State Transfer', 'Remote State Transfer', 'Representational System Transfer', 'Remote System Transfer', 'a', 'Intermediate'),
('Which sorting algorithm has the best average time complexity?', 'Bubble Sort', 'Quick Sort', 'Selection Sort', 'Insertion Sort', 'b', 'Intermediate'),
('What is encapsulation in OOP?', 'Wrapping data and methods together', 'Inheriting properties', 'Multiple inheritance', 'Method overloading', 'a', 'Intermediate'),
('Which protocol is used for secure web communication?', 'HTTP', 'FTP', 'HTTPS', 'SMTP', 'c', 'Intermediate'),
('What is the purpose of a constructor in OOP?', 'Destroy objects', 'Initialize objects', 'Copy objects', 'Compare objects', 'b', 'Intermediate'),
('Which database type is MongoDB?', 'Relational', 'NoSQL', 'Graph', 'Hierarchical', 'b', 'Intermediate'),
('What does API stand for?', 'Application Programming Interface', 'Advanced Programming Interface', 'Application Process Interface', 'Advanced Process Interface', 'a', 'Intermediate'),
('Which keyword is used for inheritance in Java?', 'implements', 'extends', 'inherits', 'derives', 'b', 'Intermediate'),
('What is the space complexity of merge sort?', 'O(1)', 'O(log n)', 'O(n)', 'O(n^2)', 'c', 'Intermediate'),
('Which pattern is used to create objects without specifying exact class?', 'Singleton', 'Factory', 'Prototype', 'Builder', 'b', 'Intermediate'),
('What is a closure in JavaScript?', 'Function with access to outer scope', 'Closed function', 'Private method', 'Static function', 'a', 'Intermediate'),
('Which SQL clause is used to filter grouped results?', 'WHERE', 'HAVING', 'FILTER', 'GROUP BY', 'b', 'Intermediate'),
('What is the default port for HTTP?', '443', '8080', '80', '3000', 'c', 'Intermediate'),
('Which method is used to prevent method overriding in Java?', 'static', 'final', 'private', 'abstract', 'b', 'Intermediate');

-- Insert 20 Advanced Level Questions
INSERT INTO questions (question, option_a, option_b, option_c, option_d, correct_answer, level) VALUES
('What is the time complexity of Dijkstra algorithm using binary heap?', 'O(V^2)', 'O(E log V)', 'O(V log E)', 'O(E + V)', 'b', 'Advanced'),
('Which consensus algorithm does Bitcoin use?', 'Proof of Stake', 'Proof of Work', 'Byzantine Fault Tolerance', 'Raft', 'b', 'Advanced'),
('What is the CAP theorem in distributed systems?', 'Consistency, Availability, Partition tolerance', 'Concurrency, Atomicity, Performance', 'Cache, API, Protocol', 'Cluster, Application, Processing', 'a', 'Advanced'),
('Which garbage collection algorithm does Java primarily use?', 'Reference Counting', 'Mark and Sweep', 'Generational Collection', 'Manual Collection', 'c', 'Advanced'),
('What is the purpose of a semaphore in concurrent programming?', 'Memory allocation', 'Process synchronization', 'Thread creation', 'Deadlock prevention', 'b', 'Advanced'),
('Which data structure is used in depth-first search?', 'Queue', 'Stack', 'Heap', 'Hash Table', 'b', 'Advanced'),
('What is eventual consistency in distributed databases?', 'Immediate consistency', 'Consistency achieved over time', 'No consistency', 'Strong consistency', 'b', 'Advanced'),
('Which algorithm is used for pattern matching in strings?', 'Dijkstra', 'KMP Algorithm', 'Binary Search', 'Merge Sort', 'b', 'Advanced'),
('What is a race condition in multithreading?', 'Fast execution', 'Uncontrolled access to shared resource', 'Thread priority', 'Deadlock', 'b', 'Advanced'),
('Which tree traversal uses a queue?', 'Preorder', 'Inorder', 'Postorder', 'Level-order', 'd', 'Advanced'),
('What is the Byzantine Generals Problem?', 'Sorting problem', 'Consensus in distributed systems', 'Graph traversal', 'Memory management', 'b', 'Advanced'),
('Which technique is used to prevent SQL injection?', 'Encryption', 'Prepared Statements', 'Hashing', 'Compression', 'b', 'Advanced'),
('What is the time complexity of building a heap?', 'O(n log n)', 'O(n)', 'O(log n)', 'O(n^2)', 'b', 'Advanced'),
('Which protocol ensures reliable data transfer in TCP/IP?', 'UDP', 'TCP', 'IP', 'HTTP', 'b', 'Advanced'),
('What is a deadlock in operating systems?', 'Fast execution', 'Circular wait for resources', 'Memory leak', 'Thread termination', 'b', 'Advanced'),
('Which algorithm is used in Git for version control?', 'Merkle Tree', 'Binary Tree', 'AVL Tree', 'Red-Black Tree', 'a', 'Advanced'),
('What is the purpose of a load balancer?', 'Increase storage', 'Distribute traffic', 'Encrypt data', 'Compress files', 'b', 'Advanced'),
('Which data structure provides O(1) average case for insert, delete, search?', 'Array', 'Linked List', 'Hash Table', 'Binary Tree', 'c', 'Advanced'),
('What is the actor model in concurrent programming?', 'Thread-based model', 'Message-passing concurrency', 'Lock-based synchronization', 'Shared memory model', 'b', 'Advanced'),
('Which algorithm is used for finding strongly connected components?', 'Dijkstra', 'Kosaraju Algorithm', 'Prim Algorithm', 'Kruskal Algorithm', 'b', 'Advanced');

-- Verify the data
SELECT level, COUNT(*) as question_count FROM questions GROUP BY level;
