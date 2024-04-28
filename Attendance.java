import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class AttendanceSystemGUI extends JFrame
{
private JTextArea outputArea;
private JButton backButton;
private Connection connection;
private String studentId;
public AttendanceSystemGUI(String studentId)
{
this.studentId = studentId;
setTitle("Attendance Management System");
setSize(400, 300);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLocationRelativeTo(null);
initComponents();
initializeDatabaseConnection();
calculateAttendance();
}
private void initComponents() {
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
String pass = "Password@123";
try {

Class.forName("com.mysql.cj.jdbc.Driver");
connection = DriverManager.getConnection(url, user, pass);
}
catch (Exception e)
{
e.printStackTrace();
}
}
private void calculateAttendance()
{
try
{
PreparedStatement attendanceStatement = connection.prepareStatement("SELECT od,
total_periods, absent_periods FROM attendance WHERE student_id = ?");
attendanceStatement.setString(1, studentId);
ResultSet resultSet = attendanceStatement.executeQuery();
outputArea.setText("Attendance Details for Student ID: " + studentId + "\n\n");
while (resultSet.next()) {
int totalPeriods = resultSet.getInt("total_periods");
int od = resultSet.getInt("od");
int absentPeriods = resultSet.getInt("absent_periods");
outputArea.append(String.format("Total Periods: %d\nOD: %d\nAbsent Periods: %d\n\n",
totalPeriods, od, absentPeriods));
}
resultSet.close();
attendanceStatement.close();
}
catch (SQLException e)
{
e.printStackTrace();
outputArea.setText("An error occurred while fetching attendance information.");
}
}
private void goBackToHomePage() {
dispose();
HomePage homePage = new HomePage(studentId);
homePage.setVisible(true);
try {
if (connection != null && !connection.isClosed())
{
connection.close();
}
}
catch (SQLException e) {
e.printStackTrace();
}
}
}
