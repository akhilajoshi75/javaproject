import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Question {
    private String topic;
    private String difficulty;
    private String questionText;
    private List<String> options;
    private int correctAnswerIndex;

    public Question(String topic, String difficulty, String questionText, List<String> options, int correctAnswerIndex) {
        this.topic = topic;
        this.difficulty = difficulty;
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getTopic() {
        return topic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public boolean isCorrectAnswer(int index) {
        return index == correctAnswerIndex;
    }

    public String getHint() {
        return "Hint: The correct option starts with '" + options.get(correctAnswerIndex).charAt(0) + "'.";
    }
}

class QuizManager {
    private List<Question> questions;
    private int score;

    public QuizManager() {
        this.questions = new ArrayList<>();
        this.score = 0;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public boolean askQuestionWithTimer(Question q, Scanner scanner) {
        System.out.println(q.getQuestionText());
        List<String> options = q.getOptions();
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }

        long startTime = System.currentTimeMillis();
        System.out.print("Enter your answer (1-" + options.size() + ") or type 'hint' for a hint: ");
        String input = scanner.next();

        long endTime = System.currentTimeMillis();
        if ((endTime - startTime) / 1000 > 10) {
            System.out.println("Time's up!");
            return false;
        }

        if (input.equalsIgnoreCase("hint")) {
            System.out.println(q.getHint());
            score -= 1; // Penalty for using a hint
            return askQuestionWithTimer(q, scanner); // Retry the question after showing the hint
        }

        int answer = Integer.parseInt(input);
        if (answer < 1 || answer > options.size()) {
            System.out.println("Invalid option selected.");
            return false;
        }

        if (q.isCorrectAnswer(answer - 1)) {
            score += 3;
            System.out.println("Correct! +3 points");
            return true;
        } else {
            score -= 1;
            System.out.println("Wrong! -1 point");
            return false;
        }
    }

    public void startQuiz(String topic, String difficulty) {
        Scanner scanner = new Scanner(System.in);
        List<Question> filteredQuestions = new ArrayList<>();
        for (Question q : questions) {
            if (q.getTopic().equalsIgnoreCase(topic) && q.getDifficulty().equalsIgnoreCase(difficulty)) {
                filteredQuestions.add(q);
            }
        }

        if (filteredQuestions.size() < 5) {
            System.out.println("Not enough questions for the selected topic and difficulty.");
            return;
        }

        Collections.shuffle(filteredQuestions);
        List<Question> selectedQuestions = filteredQuestions.subList(0, Math.min(5, filteredQuestions.size()));

        for (Question q : selectedQuestions) {
            askQuestion(q, scanner);
        }
        System.out.println("Quiz Over! Your score: " + score + "/" + (selectedQuestions.size() * 3));
        addToLeaderboard(score); // Save score to leaderboard
    }

    public void startRandomQuiz() {
        Scanner scanner = new Scanner(System.in);
        Collections.shuffle(questions);
        List<Question> randomQuestions = questions.subList(0, Math.min(5, questions.size()));

        for (Question q : randomQuestions) {
            askQuestion(q, scanner);
        }
        System.out.println("Random Quiz Over! Your score: " + score + "/" + (randomQuestions.size() * 3));
        addToLeaderboard(score);
    }

    private static List<Integer> leaderboard = new ArrayList<>();

    private void addToLeaderboard(int score) {
        leaderboard.add(score);
        leaderboard.sort(Collections.reverseOrder());
        if (leaderboard.size() > 5) {
            leaderboard.remove(leaderboard.size() - 1); // Keep top 5 scores
        }
    }

    public static void displayLeaderboard() {
        System.out.println("Top 5 Scores:");
        for (int i = 0; i < leaderboard.size(); i++) {
            System.out.println((i + 1) + ". " + leaderboard.get(i));
        }
    }

    private void askQuestion(Question q, Scanner scanner) {
        System.out.println(q.getQuestionText());
        List<String> options = q.getOptions();
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ": " + options.get(i));
        }
        System.out.print("Enter your answer (1-" + options.size() + "): ");
        int answer = scanner.nextInt();

        if (answer < 1 || answer > options.size()) {
            System.out.println("Invalid option selected.");
            return;
        }

        if (q.isCorrectAnswer(answer - 1)) {
            score += 3;
            System.out.println("Correct! +3 points");
        } else {
            score -= 1;
            System.out.println("Wrong! -1 point");
        }
        System.out.println();
    }
}

public class Quiz2 {
    public static void main(String[] args) {
        QuizManager quizManager = new QuizManager();
        // Add questions to the quizManager here
        quizManager.addQuestion(new Question("Java", "Easy", "Which keyword is used to inherit a class in Java?", Arrays.asList("this", "super", "extends", "implements"), 2));
        quizManager.addQuestion(new Question("Java", "Easy", "Which company developed Java?", Arrays.asList("Microsoft", "Apple", "Sun Microsystems", "IBM"), 2));
        quizManager.addQuestion(new Question("Java", "Easy", "Which of these is a reserved keyword in Java?", Arrays.asList("object", "strictfp", "main", "system"), 1));
        quizManager.addQuestion(new Question("Java", "Easy", "What is the default value of boolean variable?", Arrays.asList("true", "false", "null", "0"), 1));
        quizManager.addQuestion(new Question("Java", "Easy", "Which access modifier is used to make a member accessible within the same package?", Arrays.asList("public", "protected", "private", "default"), 3));
        quizManager.addQuestion(new Question("Java", "Easy", "What is the use of 'break' statement in Java?", Arrays.asList("To exit from a loop", "To skip the current iteration", "To exit from switch case", "To terminate the program"), 0));
        quizManager.addQuestion(new Question("Java", "Easy", "Which operator is used to allocate memory to an array in Java?", Arrays.asList("malloc", "alloc", "new", "create"), 2));

        // Medium
        quizManager.addQuestion(new Question("Java", "Medium", "Which of these is not a Java feature?", Arrays.asList("Object-oriented", "Use of pointers", "Portable", "Dynamic"), 1));
        quizManager.addQuestion(new Question("Java", "Medium", "Which package contains the Random class?", Arrays.asList("java.util", "java.lang", "java.awt", "java.io"), 0));
        quizManager.addQuestion(new Question("Java", "Medium", "Which of these classes are not part of Java’s collection framework?", Arrays.asList("HashMap", "Vector", "Stack", "Dictionary"), 3));
        quizManager.addQuestion(new Question("Java", "Medium", "What is the default value of an instance variable?", Arrays.asList("null", "0", "Depends on the type", "undefined"), 2));
        quizManager.addQuestion(new Question("Java", "Medium", "Which of these exceptions are thrown by JVM?", Arrays.asList("ArrayIndexOutOfBoundsException", "ClassNotFoundException", "FileNotFoundException", "IOException"), 0));
        quizManager.addQuestion(new Question("Java", "Medium", "Which of these is a marker interface in Java?", Arrays.asList("Serializable", "Runnable", "Cloneable", "RandomAccess"), 0));
        quizManager.addQuestion(new Question("Java", "Medium", "What is the difference between '==' and 'equals()' method in Java?", Arrays.asList("== compares references, equals() compares values", "== compares values, equals() compares references", "== compares memory addresses of two objects", "== compares values, equals() compares memory addresses of two objects"), 0));

        // Hard
        quizManager.addQuestion(new Question("Java", "Hard", "What is the return type of the hashCode() method in the Object class?", Arrays.asList("int", "Object", "void", "long"), 0));
        quizManager.addQuestion(new Question("Java", "Hard", "Which of these classes is synchronized?", Arrays.asList("ArrayList", "Vector", "HashSet", "TreeMap"), 1));
        quizManager.addQuestion(new Question("Java", "Hard", "Which keyword is used to prevent inheritance in Java?", Arrays.asList("final", "static", "volatile", "abstract"), 0));
        quizManager.addQuestion(new Question("Java", "Hard", "What is the purpose of the transient keyword?", Arrays.asList("Serialization", "Synchronization", "Persistence", "Caching"), 0));
        quizManager.addQuestion(new Question("Java", "Hard", "Which of these methods is a part of the collection framework?", Arrays.asList("setValue()", "getValue()", "delete()", "add()"), 3));
        quizManager.addQuestion(new Question("Java", "Hard", "What is the difference between 'ArrayList' and 'LinkedList' in Java?", Arrays.asList("ArrayList is faster for random access, LinkedList is faster for insertions and deletions", "LinkedList is faster for random access, ArrayList is faster for insertions and deletions", "Both have similar performance characteristics", "None of the above"), 0));
        quizManager.addQuestion(new Question("Java", "Hard", "What is the purpose of 'volatile' keyword in Java?", Arrays.asList("To indicate that a variable may be changed asynchronously", "To indicate that a variable cannot be changed", "To indicate that a variable is thread-safe", "To indicate that a variable should not be optimized by the compiler"), 0));

        // DBMS Questions
        // Easy
        quizManager.addQuestion(new Question("DBMS", "Easy", "What does SQL stand for?", Arrays.asList("Structured Query Language", "Strong Question Language", "Structured Question Language", "None"), 0));
        quizManager.addQuestion(new Question("DBMS", "Easy", "Which of the following is a database management system?", Arrays.asList("Oracle", "Linux", "Firefox", "Google"), 0));
        quizManager.addQuestion(new Question("DBMS", "Easy", "What type of database is MongoDB?", Arrays.asList("Relational", "NoSQL", "Distributed", "Graph"), 1));
        quizManager.addQuestion(new Question("DBMS", "Easy", "Which command is used to remove a table from a database?", Arrays.asList("DELETE", "DROP", "REMOVE", "TRUNCATE"), 1));
        quizManager.addQuestion(new Question("DBMS", "Easy", "What is a primary key?", Arrays.asList("A unique identifier for each record", "A key used for encryption", "A foreign key", "A key for indexing"), 0));
        quizManager.addQuestion(new Question("DBMS", "Easy", "Which SQL statement is used to retrieve data from a database?", Arrays.asList("GET", "SELECT", "FETCH", "RETRIEVE"), 1));
        quizManager.addQuestion(new Question("DBMS", "Easy", "What does DBMS stand for?", Arrays.asList("Database Management System", "Database Model System", "Data Management System", "Data Model System"), 0));

        // Medium
        quizManager.addQuestion(new Question("DBMS", "Medium", "What is a foreign key?", Arrays.asList("A key used to link two tables", "A key used to encrypt data", "A unique identifier for a table", "A key used for indexing"), 0));
        quizManager.addQuestion(new Question("DBMS", "Medium", "What is normalization?", Arrays.asList("The process of minimizing redundancy in a database", "The process of increasing redundancy in a database", "The process of creating backup copies", "The process of indexing data"), 0));
        quizManager.addQuestion(new Question("DBMS", "Medium", "Which of these is not a type of join in SQL?", Arrays.asList("INNER JOIN", "OUTER JOIN", "LEFT JOIN", "RIGHT JOIN"), 1));
        quizManager.addQuestion(new Question("DBMS", "Medium", "Which SQL clause is used to sort the results of a query?", Arrays.asList("ORDER BY", "GROUP BY", "SORT", "ARRANGE"), 0));
        quizManager.addQuestion(new Question("DBMS", "Medium", "What is a view in SQL?", Arrays.asList("A virtual table based on the result set of a query", "A physical table", "A stored procedure", "A trigger"), 0));
        quizManager.addQuestion(new Question("DBMS", "Medium", "What does ACID stand for in the context of databases?", Arrays.asList("Atomicity, Consistency, Isolation, Durability", "Atomicity, Concurrency, Isolation, Durability", "Atomicity, Consistency, Isolation, Data", "Atomicity, Consistency, Integrity, Durability"), 0));
        quizManager.addQuestion(new Question("DBMS", "Medium", "Which of these is a NoSQL database?", Arrays.asList("MySQL", "Oracle", "MongoDB", "PostgreSQL"), 2));

        // Hard
        quizManager.addQuestion(new Question("DBMS", "Hard", "What is a stored procedure?", Arrays.asList("A precompiled SQL statement stored in the database", "A SQL query that is executed on demand", "A method for creating indexes", "A method for data retrieval"), 0));
        quizManager.addQuestion(new Question("DBMS", "Hard", "What is a trigger in a database?", Arrays.asList("A set of instructions that are executed automatically in response to an event", "A method for data encryption", "A type of database index", "A mechanism for backup"), 0));
        quizManager.addQuestion(new Question("DBMS", "Hard", "Which of these is a property of a relational database?", Arrays.asList("Tables with rows and columns", "Hierarchical data structure", "Graph-based data model", "Object-oriented model"), 0));
        quizManager.addQuestion(new Question("DBMS", "Hard", "What is denormalization?", Arrays.asList("The process of increasing redundancy for performance optimization", "The process of minimizing redundancy", "The process of creating a backup", "The process of indexing data"), 0));
        quizManager.addQuestion(new Question("DBMS", "Hard", "Which SQL command is used to modify the structure of a table?", Arrays.asList("ALTER TABLE", "UPDATE TABLE", "CHANGE TABLE", "MODIFY TABLE"), 0));
        quizManager.addQuestion(new Question("DBMS", "Hard", "What is the purpose of indexing in a database?", Arrays.asList("To speed up data retrieval", "To ensure data integrity", "To normalize data", "To enforce constraints"), 0));
        quizManager.addQuestion(new Question("DBMS", "Hard", "What is an entity-relationship (ER) model?", Arrays.asList("A diagram that shows the relationships between entities in a database", "A method for data encryption", "A type of database index", "A mechanism for backup"), 0));

        // DSA Questions
// Easy
quizManager.addQuestion(new Question("DSA", "Easy", "Which data structure uses LIFO?", Arrays.asList("Queue", "Stack", "Array", "Linked List"), 1));
quizManager.addQuestion(new Question("DSA", "Easy", "Which data structure uses FIFO?", Arrays.asList("Queue", "Stack", "Array", "Linked List"), 0));
quizManager.addQuestion(new Question("DSA", "Easy", "Which data structure is used for recursion?", Arrays.asList("Queue", "Stack", "Array", "Linked List"), 1));
quizManager.addQuestion(new Question("DSA", "Easy", "Which data structure can grow dynamically?", Arrays.asList("Array", "Linked List", "Both", "None"), 1));
quizManager.addQuestion(new Question("DSA", "Easy", "Which data structure is used to implement a graph?", Arrays.asList("Array", "Linked List", "Both", "None"), 2));
quizManager.addQuestion(new Question("DSA", "Easy", "Which data structure is used for Breadth-First Search (BFS)?", Arrays.asList("Queue", "Stack", "Array", "Linked List"), 0));
quizManager.addQuestion(new Question("DSA", "Easy", "Which data structure is typically used for implementing a doubly linked list?", Arrays.asList("Array", "Linked List", "Both", "None"), 1));

// Medium
quizManager.addQuestion(new Question("DSA", "Medium", "Which sorting algorithm has the best average case complexity?", Arrays.asList("Bubble Sort", "Merge Sort", "Quick Sort", "Selection Sort"), 1));
quizManager.addQuestion(new Question("DSA", "Medium", "Which data structure is used in BFS?", Arrays.asList("Stack", "Queue", "Array", "Linked List"), 1));
quizManager.addQuestion(new Question("DSA", "Medium", "What is the time complexity of binary search?", Arrays.asList("O(1)", "O(n)", "O(log n)", "O(n^2)"), 2));
quizManager.addQuestion(new Question("DSA", "Medium", "What is the time complexity of inserting an element at the beginning of a Linked List?", Arrays.asList("O(1)", "O(n)", "O(log n)", "O(n^2)"), 0));
quizManager.addQuestion(new Question("DSA", "Medium", "Which data structure is used in DFS?", Arrays.asList("Stack", "Queue", "Array", "Linked List"), 0));

// Hard
quizManager.addQuestion(new Question("DSA", "Hard", "What is the time complexity of inserting an element at the beginning of an ArrayList?", Arrays.asList("O(1)", "O(n)", "O(log n)", "O(n^2)"), 1));
quizManager.addQuestion(new Question("DSA", "Hard", "Which sorting algorithm is not stable?", Arrays.asList("Merge Sort", "Quick Sort", "Bubble Sort", "Insertion Sort"), 1));
quizManager.addQuestion(new Question("DSA", "Hard", "What is the time complexity of finding an element in a hash table?", Arrays.asList("O(1)", "O(n)", "O(log n)", "O(n log n)"), 0));
quizManager.addQuestion(new Question("DSA", "Hard", "Which data structure is used to implement LRU cache?", Arrays.asList("Array", "Linked List", "HashMap", "Both Linked List and HashMap"), 3));
quizManager.addQuestion(new Question("DSA", "Hard", "What is the space complexity of merge sort?", Arrays.asList("O(1)", "O(n)", "O(log n)", "O(n log n)"), 1));

// Python Questions
// Easy
quizManager.addQuestion(new Question("Python", "Easy", "What is the output of print(2**3)?", Arrays.asList("6", "8", "9", "16"), 1));
quizManager.addQuestion(new Question("Python", "Easy", "Which of the following is a mutable data type in Python?", Arrays.asList("str", "list", "tuple", "int"), 1));
quizManager.addQuestion(new Question("Python", "Easy", "What is the keyword used to define a function in Python?", Arrays.asList("func", "define", "def", "function"), 2));
quizManager.addQuestion(new Question("Python", "Easy", "Which operator is used for multiplication in Python?", Arrays.asList("+", "-", "*", "/"), 2));
quizManager.addQuestion(new Question("Python", "Easy", "What is the extension of a Python file?", Arrays.asList(".java", ".py", ".txt", ".cpp"), 1));

// Medium
quizManager.addQuestion(new Question("Python", "Medium", "Which of these is a mutable data type in Python?", Arrays.asList("str", "list", "tuple", "int"), 1));
quizManager.addQuestion(new Question("Python", "Medium", "What is the output of print('Hello' * 3)?", Arrays.asList("HelloHelloHello", "HelloHelloHelloHello", "HelloHello", "Error"), 0));
quizManager.addQuestion(new Question("Python", "Medium", "Which of these is a Python framework?", Arrays.asList("Django", "Spring", "React", "Laravel"), 0));
quizManager.addQuestion(new Question("Python", "Medium", "What is the keyword used to create a class in Python?", Arrays.asList("class", "define", "struct", "new"), 0));
quizManager.addQuestion(new Question("Python", "Medium", "What is the method inside the class in Python?", Arrays.asList("Function", "Method", "Object", "Attribute"), 1));

// Hard
quizManager.addQuestion(new Question("Python", "Hard", "What does the 'self' keyword in Python represent?", Arrays.asList("A reference to the instance", "A global variable", "A local variable", "None"), 0));
quizManager.addQuestion(new Question("Python", "Hard", "What is the output of print(0.1 + 0.2 == 0.3)?", Arrays.asList("True", "False", "None", "Error"), 1));
quizManager.addQuestion(new Question("Python", "Hard", "Which module in Python is used for regular expressions?", Arrays.asList("re", "regex", "express", "None"), 0));
quizManager.addQuestion(new Question("Python", "Hard", "Which of these functions is used to read a line from a file in Python?", Arrays.asList("read()", "readline()", "readfile()", "None"), 1));
quizManager.addQuestion(new Question("Python", "Hard", "Which of the following is not a keyword in Python?", Arrays.asList("try", "except", "throw", "finally"), 2));

// General Coding Knowledge Questions
// Easy
quizManager.addQuestion(new Question("General Coding Knowledge", "Easy", "What is the extension of a Python file?", Arrays.asList(".java", ".py", ".txt", ".cpp"), 1));
quizManager.addQuestion(new Question("General Coding Knowledge", "Easy", "Which language is primarily used for Android development?", Arrays.asList("Java", "Swift", "Python", "C++"), 0));
quizManager.addQuestion(new Question("General Coding Knowledge", "Easy", "Which language is primarily used for iOS development?", Arrays.asList("Java", "Swift", "Python", "C++"), 1));
quizManager.addQuestion(new Question("General Coding Knowledge", "Easy", "Which HTML tag is used to define an image?", Arrays.asList("<image>", "<img>", "<pic>", "<picture>"), 1));
quizManager.addQuestion(new Question("General Coding Knowledge", "Easy", "Which company developed the C# language?", Arrays.asList("Microsoft", "Apple", "Google", "IBM"), 0));

// Medium
quizManager.addQuestion(new Question("General Coding Knowledge", "Medium", "Which language is known as the backbone of web development?", Arrays.asList("Java", "C", "Python", "JavaScript"), 3));
quizManager.addQuestion(new Question("General Coding Knowledge", "Medium", "What does CSS stand for?", Arrays.asList("Cascading Style Sheets", "Cascading Style Scripts", "Cascading Sheet Styles", "None"), 0));
quizManager.addQuestion(new Question("General Coding Knowledge", "Medium", "Which language is used for styling web pages?", Arrays.asList("HTML", "JQuery", "CSS", "XML"), 2));
quizManager.addQuestion(new Question("General Coding Knowledge", "Medium", "Which language runs in a web browser?", Arrays.asList("Java", "C", "Python", "JavaScript"), 3));
quizManager.addQuestion(new Question("General Coding Knowledge", "Medium", "What does IDE stand for?", Arrays.asList("Integrated Development Environment", "Intelligent Development Environment", "Intellectual Development Environment", "None"), 0));

// Hard
quizManager.addQuestion(new Question("General Coding Knowledge", "Hard", "What is Big-O notation used for?", Arrays.asList("To measure performance", "To measure space complexity", "To measure code readability", "All of the above"), 0));
quizManager.addQuestion(new Question("General Coding Knowledge", "Hard", "What does API stand for?", Arrays.asList("Application Program Interface", "Application Programming Interface", "Application Platform Interface", "None"), 1));
quizManager.addQuestion(new Question("General Coding Knowledge", "Hard", "Which of the following is a version control system?", Arrays.asList("Git", "Linux", "Docker", "Jenkins"), 0));
quizManager.addQuestion(new Question("General Coding Knowledge", "Hard", "Which of these is not a programming paradigm?", Arrays.asList("Object-oriented", "Functional", "Procedural", "Logical"), 3));
quizManager.addQuestion(new Question("General Coding Knowledge", "Hard", "Which language is primarily used for scientific computing?", Arrays.asList("Java", "C", "Python", "JavaScript"), 2));


        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Quiz!");
        System.out.println("1. Start a topic-specific quiz");
        System.out.println("2. Start a random quiz");
        System.out.println("3. View leaderboard");
        System.out.print("Choose an option (1, 2 or 3): ");
        int option = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        if (option == 1) {
            System.out.print("Enter topic (Java/DBMS/DSA/Python/General Coding Knowledge): ");
            String topic = scanner.nextLine();
            System.out.print("Enter difficulty (Easy/Medium/Hard): ");
            String difficulty = scanner.nextLine();
            quizManager.startQuiz(topic, difficulty);
        } else if (option == 2) {
            quizManager.startRandomQuiz();
        } else if (option == 3) {
            QuizManager.displayLeaderboard();
        } else {
            System.out.println("Invalid option selected.");
        }

        scanner.close();
    }
}
