import java.awt.Color;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Signuptest extends JFrame {
    private final JTextField usernameField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final Repository repository;
    //private final JTextField answerField = new JTextField();
    private final JButton sendButton = new JButton("Send");
    private final JButton loadButton = new JButton("Load");
    private final JButton clearButton = new JButton("Clear");
    private final JButton loginButton = new JButton("Login");
    public String savedName = "Kalin";
    public String savedEmail = "ivanpetrov@gmail.com";
    public String savedpassword = "123456";
    //public String savedAnswer = "50$";

    public Signuptest() {
        this(new Repository());
    }

    public Signuptest(Repository repository) {
        super("JFrame Demo");
        this.repository = repository;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(410, 400);
        setResizable(true);
        setLayout(null);        

        // setBounds(x,` y, width, height) — координати и размери в пиксели.
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(30, 30, 80, 25);
        usernameField.setBounds(120, 30, 260, 25);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(30, 75, 80, 25);
        emailField.setBounds(120, 75, 260, 25);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(30, 125, 80, 25);
        passwordField.setBounds(120, 125, 260, 25);

       // JLabel answerLabel = new JLabel("Answer");
        //answerLabel.setBounds(30, 175, 80, 25); 
        //answerField.setBounds(120, 175, 260, 25);

        int buttonWidth = 130;
        int xCenter = (getWidth() - buttonWidth) / 2;
        loadButton.setBounds(xCenter, 175, buttonWidth, 30);
        sendButton.setBounds(xCenter, 215, buttonWidth, 30);
        clearButton.setBounds(xCenter, 255, buttonWidth, 30);
        int loginButtonWidth = 150;
        int loginButtonHeight = 35;
        loginButton.setBounds(xCenter - (loginButtonWidth - buttonWidth) / 2,
                293, loginButtonWidth, loginButtonHeight);
        loginButton.addActionListener(event -> {
            new MainFrame(repository).setVisible(true);
            dispose();
        });

        // questionButton.setBounds(30, 170, 120, 30);

        loadButton.addActionListener(event -> load());
        clearButton.addActionListener(event -> clear());
        sendButton.setBackground(new Color(0, 200, 83));
        sendButton.setForeground(Color.WHITE);
        loadButton.setBackground(new Color(100, 181, 246));
        loadButton.setForeground(Color.WHITE);
        clearButton.setBackground(new Color(198, 40, 40));
        clearButton.setForeground(Color.WHITE);


        sendButton.addActionListener(e -> send());

        add(usernameLabel);
        add(usernameField);
        add(emailLabel);
        add(emailField);
        add(passwordField);
        add(passwordLabel);
        add(sendButton);
        add(loadButton);
        add(clearButton);
        add(loginButton);
      //  add(answerField);
      //  add(answerLabel);
        setLocationRelativeTo(null);

    }

    private void send() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a username, email, and password.",
                    "Missing information",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            repository.createUser(username, email, password);
            JOptionPane.showMessageDialog(
                    this,
                    "Your account was created successfully.",
                    "Registration complete",
                    JOptionPane.INFORMATION_MESSAGE
            );
            passwordField.setText("");
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to save your account. Check your database connection and make sure the username and email are not already in use.",
                    "Registration error",
                    JOptionPane.ERROR_MESSAGE
            );
            exception.printStackTrace();
        }
    }

    private void load() {
        usernameField.setText(savedName);
        emailField.setText(savedEmail);
        passwordField.setText(savedpassword);
    }

    private void clear() {
        usernameField.setText("");
        emailField.setText("");
        passwordField.setText("");
    }
}

    
