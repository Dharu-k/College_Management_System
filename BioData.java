import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class StudentBioDataGUI extends JFrame
{
private JTextArea outputArea;
private JButton backButton;
private Connection connection;
private String studentId;
public StudentBioDataGUI(String studentId)
{
this.studentId = studentId;
setTitle("Student Biodata");
setSize(400, 300);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLocationRelativeTo(null);
initComponents();
initializeDatabaseConnection();
fetchAndDisplayBiodata();
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
}
catch (Exception e)
{
e.printStackTrace();
}
}
private void fetchAndDisplayBiodata()
{
try
{
PreparedStatement statement = connection.prepareStatement("SELECT name, department,
year FROM biodata WHERE student_id = ?");
statement.setString(1, studentId);
ResultSet resultSet = statement.executeQuery();
outputArea.setText("Biodata Details for Student ID: " + studentId + "\n\n");
if (resultSet.next()) {
String name = resultSet.getString("name");
String department = resultSet.getString("department");
int year = resultSet.getInt("year");
outputArea.append(String.format("Name:%s\nDepartment:%s\nYear:%d\n", name,
department, year));
}
else {
outputArea.append("No biodata found for this student ID.");
}
}
catch (SQLException e) {
e.printStackTrace();
outputArea.setText("An error occurred while fetching biodata information: " +
e.getMessage());
}
}
private void goBackToHomePage()
{
dispose();
HomePage homePage = new HomePage(studentId);
homePage.setVisible(true);
}
}
