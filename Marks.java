import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class MarksManagementSystem extends JFrame {
private JTextArea outputArea;
private JButton backButton;
private Connection connection;
private String studentId;
public MarksManagementSystem(String studentId)
{
this.studentId = studentId;
setTitle("Marks Management System");
setSize(400, 300);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLocationRelativeTo(null);
initComponents();
initializeDatabaseConnection();
calculateMarks();
}
private void initComponents()
{
JPanel panel = new JPanel(new BorderLayout());
outputArea = new JTextArea(10, 30);
outputArea.setEditable(false);
JScrollPane scrollPane = new JScrollPane(outputArea);
panel.add(scrollPane, BorderLayout.CENTER);
backButton = new JButton("Back");
backButton.addActionListener(new ActionListener()
{
@Override
public void actionPerformed(ActionEvent e)
{
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
String password = "Password@123";
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
connection = DriverManager.getConnection(url, user, password);
} catch (Exception e) {
e.printStackTrace();
}
}
private void calculateMarks() {
try {
PreparedStatement marksStatement = connection.prepareStatement("SELECT semester, GPA
FROM marks WHERE student_id = ?");
marksStatement.setString(1, studentId);
ResultSet resultSet =marksStatement.executeQuery();
double totalGPASum = 0;
int semesterCount = 0;
outputArea.setText("Marks Details for Student ID: " + studentId + "\n\n");
outputArea.append("Semester\tGPA\n");
while (resultSet.next())
{
String semester = resultSet.getString("semester");
double GPA = resultSet.getDouble("GPA");
outputArea.append(String.format("%s\t%.2f\n", semester, GPA));
totalGPASum += GPA;
semesterCount++;
}
double CGPA = totalGPASum / semesterCount;
outputArea.append("\nCalculated CGPA: " + String.format("%.2f", CGPA));
}
catch (SQLException e) {
e.printStackTrace();
outputArea.setText("An error occurred while fetching marks information.");
}
}
private void goBackToHomePage() {
dispose();
HomePage homePage = new HomePage(studentId);
homePage.setVisible(true);
}
}
