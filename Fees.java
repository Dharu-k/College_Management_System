import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class FeeManagementSystem extends JFrame {
private JTextArea outputArea;
private JButton backButton;
private Connection connection;
private String studentId;
public FeeManagementSystem(String studentId)
{
this.studentId = studentId;
setTitle("Fee Management System");
setSize(400, 300);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLocationRelativeTo(null);
initComponents();
initializeDatabaseConnection();
calculateFee();
}
private void initComponents()
{
JPanel panel = new JPanel(new BorderLayout());
outputArea = new JTextArea(10, 30);
outputArea.setEditable(false);
JScrollPane scrollPane = new JScrollPane(outputArea);
panel.add(scrollPane, BorderLayout.CENTER);
backButton = new JButton("Back");
backButton.addActionListener(new ActionListener() {
@Override
public void actionPerformed(ActionEvent e) {
goBackToHomePage();
}
});
panel.add(backButton, BorderLayout.SOUTH);
getContentPane().add(panel, BorderLayout.CENTER);
}
private void initializeDatabaseConnection()

{
String url = "jdbc:mysql://localhost:3306/login_schema";
String user = "root";
String pass = "Password@123";
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
connection = DriverManager.getConnection(url, user, pass);
}
catch (Exception e)
{
e.printStackTrace();
}
}
private void calculateFee()
{
try {
PreparedStatement totalFeeStatement = connection.prepareStatement("SELECT total_fee
FROM fees WHERE student_id = ?");
totalFeeStatement.setString(1, studentId);
ResultSet totalFeeResult = totalFeeStatement.executeQuery();
if (!totalFeeResult.next())
{
outputArea.setText("No fee information found for student ID: " + studentId);
return;
}
int totalFee = totalFeeResult.getInt("total_fee");
PreparedStatement paidFeeStatement = connection.prepareStatement("SELECT
SUM(amount) AS paid_fee FROM payments WHERE student_id = ?");
paidFeeStatement.setString(1, studentId);
ResultSet paidFeeResult = paidFeeStatement.executeQuery();
if (paidFeeResult.next())
{
int paidFee = paidFeeResult.getInt("paid_fee");
int balance = totalFee - paidFee;
outputArea.setText("Student Details for ID: " + studentId + "\n\n");
outputArea.append("Fee Details:\n");
outputArea.append("Total Fee: " + totalFee + "\n");
outputArea.append("Paid Fee: " + paidFee + "\n");
outputArea.append("Balance: " + balance + "\n");
}

else
{
outputArea.setText("No payment information found for student ID: " + studentId);
}
}
catch (SQLException e)
{
e.printStackTrace();
outputArea.setText("An error occurred while fetching fee information.");
}
}
private void goBackToHomePage()
{
dispose();
HomePage homePage = new HomePage(studentId);
homePage.setVisible(true);
}
}
