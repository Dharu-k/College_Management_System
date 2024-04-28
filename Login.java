import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class LoginFrame extends JFrame
{
private JTextField usernameField;
private JPasswordField passwordField;
private JButton loginButton;
private JLabel statusLabel;
private static final String DB_URL = "jdbc:mysql://localhost:3306/login_schema";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "Password@123";
public LoginFrame()
{
setTitle("Login Page");
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setExtendedState(JFrame.MAXIMIZED_BOTH);
setLocationRelativeTo(null);
createUI();
setVisible(true);
}
private void createUI()
{
JPanel contentPanel = new JPanel(new BorderLayout());
JPanel loginPanel = new JPanel(new GridBagLayout());
loginPanel.setOpaque(false);
GridBagConstraints gbc = new GridBagConstraints();
gbc.fill = GridBagConstraints.HORIZONTAL;
gbc.insets = new Insets(10, 10, 10, 10);
addUsernamePanel(loginPanel, gbc);
addPasswordPanel(loginPanel, gbc);
addButtonPanel(loginPanel, gbc);
addStatusLabel(loginPanel, gbc);
contentPanel.add(loginPanel, BorderLayout.CENTER);
setContentPane(contentPanel);
}
private void addUsernamePanel(JPanel loginPanel, GridBagConstraints gbc)
{
JPanel usernamePanel = new JPanel(new BorderLayout());
usernamePanel.setOpaque(false);
JLabel usernameLabel = new JLabel("Username:");

usernameField = new JTextField();
usernameField.setPreferredSize(new Dimension(200, 30));
usernameField.setBorder(BorderFactory.createCompoundBorder(
BorderFactory.createLineBorder(Color.BLACK, 1),
BorderFactory.createEmptyBorder(5, 5, 5, 5)
));
usernamePanel.add(usernameLabel, BorderLayout.WEST);
usernamePanel.add(usernameField, BorderLayout.CENTER);
gbc.gridx = 0;
gbc.gridy = 0;
loginPanel.add(usernamePanel, gbc);
}
private void addPasswordPanel(JPanel loginPanel, GridBagConstraints gbc)
{
JPanel passwordPanel = new JPanel(new BorderLayout());
passwordPanel.setOpaque(false);
JLabel passwordLabel = new JLabel("Password:");
passwordField = new JPasswordField();
passwordField.setPreferredSize(new Dimension(200, 30));
passwordField.setBorder(BorderFactory.createCompoundBorder(
BorderFactory.createLineBorder(Color.BLACK, 1),
BorderFactory.createEmptyBorder(5, 5, 5, 5)
));
passwordPanel.add(passwordLabel, BorderLayout.WEST);
passwordPanel.add(passwordField, BorderLayout.CENTER);
gbc.gridx = 0;
gbc.gridy = 1;
loginPanel.add(passwordPanel, gbc);
}
private void addButtonPanel(JPanel loginPanel, GridBagConstraints gbc)
{
JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
buttonPanel.setOpaque(false);
loginButton = new JButton("Login");
buttonPanel.add(loginButton);
gbc.gridx = 0;
gbc.gridy = 2;
loginPanel.add(buttonPanel, gbc);
loginButton.addActionListener(new ActionListener()
{
@Override
public void actionPerformed(ActionEvent e)
{
handleLogin();

}
});
}
private void addStatusLabel(JPanel loginPanel, GridBagConstraints gbc)
{
statusLabel = new JLabel();
statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
gbc.gridx = 0;
gbc.gridy = 3;
loginPanel.add(statusLabel, gbc);
}
private void handleLogin()
{
String username = usernameField.getText();
String password = new String(passwordField.getPassword());
if (authenticateUser(username, password))
{
String studentId = retrieveStudentId(username);
if (studentId != null)
{
dispose();
HomePage homePage = new HomePage(studentId);
homePage.setVisible(true);
JOptionPane.showMessageDialog(this, "Login successful. Redirecting to home page.");
}
else
{
statusLabel.setText("Unable to retrieve student ID.");
}
}
else
{
statusLabel.setText("Invalid username or password. Please try again.");
}
}
private boolean authenticateUser(String username, String password) {
try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
String query = "SELECT * FROM user WHERE username = ? AND password = ?";
try (PreparedStatement pstmt = conn.prepareStatement(query)) {
pstmt.setString(1, username);
pstmt.setString(2, password);
try (ResultSet rs = pstmt.executeQuery())
{
return rs.next();
}

}
}
catch (SQLException e)
{
e.printStackTrace();
return false;
}
}
private String retrieveStudentId(String username)
{
try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD))
{
String query = "SELECT student_id FROM user WHERE username = ?";
try (PreparedStatement pstmt = conn.prepareStatement(query))
{
pstmt.setString(1, username);
try (ResultSet rs = pstmt.executeQuery()) {
if (rs.next()) {
return rs.getString("student_id");
}
}
}
}
catch (SQLException e)
{
e.printStackTrace();
return null;
}
return null;
}
public static void main(String[] args)
{
SwingUtilities.invokeLater(LoginFrame::new);
}
}
