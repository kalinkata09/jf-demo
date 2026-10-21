package jf.demo.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ForgotPassword extends JFrame {
    private final JTextField emailField = new JTextField();

    public ForgotPassword(Runnable onBack) {
        super("Forgot Password");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 180);
        setResizable(false);
        setLayout(null);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(30, 30, 80, 25);
        emailField.setBounds(110, 30, 150, 25);

        JButton backButton = new JButton("Back to Login");
        backButton.setBounds(75, 80, 150, 30);
        backButton.addActionListener(event -> dispose());

        add(emailLabel);
        add(emailField);
        add(backButton);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent event) {
                onBack.run();
            }
        });
        setLocationRelativeTo(null);
    }
}
