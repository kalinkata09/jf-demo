import java.awt.Color;
import java.awt.Cursor;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import jf.demo.ui.ForgotPassword;

public class MainFrame extends JFrame {
    private final JTextField nameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton loginButton = new JButton("Влез");
    private final JButton loadButton = new JButton("Зареди");
    private final JButton signup = new JButton("Регистрация");
    private final JButton forgotPasswordButton = new JButton(
            "<html><u>Forgot Password</u></html>");
    private String savedName = "Dragan";
    private String savedPassword = "Draganov";

    private final Repository repository;

    public MainFrame() {
        this(new Repository());
    }

    // Allows a test to provide a repository without needing a database.
    public MainFrame(Repository repository) {
        super("JFrame Demo");
        this.repository = repository;
        nameField.setName("username");
        passwordField.setName("password");
        loginButton.setName("login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(440, 280);
        setResizable(false);
        setLayout(null);

        

        // setBounds(x, y, width, height) — координати и размери в пиксели.
        JLabel nameLabel = new JLabel("Име");
        nameLabel.setBounds(30, 30, 80, 25);
        nameField.setBounds(120, 30, 260, 25);

        JLabel passwordLabel = new JLabel("Парола");
        passwordLabel.setBounds(30, 75, 80, 25);
        passwordField.setBounds(120, 75, 260, 25);
        forgotPasswordButton.setBounds(255, 100, 125, 22);
        forgotPasswordButton.setForeground(new Color(26, 115, 232));
        forgotPasswordButton.setBorderPainted(false);
        forgotPasswordButton.setFocusPainted(false);
        forgotPasswordButton.setContentAreaFilled(false);
        forgotPasswordButton.setOpaque(false);
        forgotPasswordButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        loginButton.setBounds(120, 125, 120, 30);
        loadButton.setBounds(260, 125, 120, 30);
        signup.setBounds(190, 170, 120, 30);
        loginButton.addActionListener(event -> {
            login();
        });
        loadButton.addActionListener(event -> load());
        signup.addActionListener(event -> signup());
        forgotPasswordButton.addActionListener(event -> {
            ForgotPassword forgotPassword = new ForgotPassword(() -> setVisible(true));
            setVisible(false);
            forgotPassword.setVisible(true);
        });

        add(nameLabel);
        add(nameField);
        add(passwordLabel);
        add(passwordField);
        add(forgotPasswordButton);
        add(loginButton);
        add(loadButton);
        add(signup);
        setLocationRelativeTo(null);
    }

    
        
    private void login() {
        String username = nameField.getText().trim();
        String password = new String(passwordField.getPassword());
        if (username.isEmpty() || password.isEmpty()) {
            showMessage("Моля, попълнете всички полета.", "Грешка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            User user = repository.getUserByUsernameAndPassword(username, password);
            if (user != null) {
                showMessage("Успешен вход! Добре дошли, " + user.getUsername() + "!",
                        "Вход", JOptionPane.INFORMATION_MESSAGE);
            } else {
                showMessage("Грешно потребителско име или парола.", "Грешка", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            showMessage("Неуспешна връзка с базата данни: " + e.getMessage(),
                    "Грешка", JOptionPane.ERROR_MESSAGE);
        }
    }



    // Tests override only the modal dialog, so they need no manual OK click.
    protected void showMessage(String message, String title, int type) {
        JOptionPane.showMessageDialog(this, message, title, type);
    }
    // Позволява натискане на истинския бутон от теста.
    public void clickLoadButton() {
        loadButton.doClick();
    }

    public String getEnteredName() {
        return nameField.getText();
    }
    private void load() {
        nameField.setText(savedName);
        passwordField.setText(savedPassword);
    }

    public void clickSignupButton() {
        signup.doClick();
    }

    private void signup() {
        Signuptest signupFrame = new Signuptest(repository);
        signupFrame.setLocationRelativeTo(this);
        signupFrame.setVisible(true);
        this.dispose();
    }
}
