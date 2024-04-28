import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class StudentLibraryBooksGUI extends JFrame
{
private JTextArea outputArea;
private JButton backButton;
private Connection connection;
private String studentId;
public StudentLibraryBooksGUI(String studentId)
{
this.studentId = studentId;
setTitle("Library Books Taken");
setSize(400, 300);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLocationRelativeTo(null);
initComponents();
initializeDatabaseConnection();
fetchAndDisplayLibraryBooks();
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
private void fetchAndDisplayLibraryBooks()
{
try
{
PreparedStatement statement = connection.prepareStatement("SELECT book_title FROM
library WHERE student_id = ?");
statement.setString(1, studentId);
ResultSet resultSet = statement.executeQuery();
outputArea.setText("Library Books Taken for Student ID: " + studentId + "\n\n");
outputArea.append("Book Title\n");
boolean hasResults = false;
while (resultSet.next())
{
hasResults = true;
String bookTitle = resultSet.getString("book_title");
// Display book information
outputArea.append(String.format("%s\n", bookTitle));
}
if (!hasResults)
{
outputArea.append("No books found.");
}
}
catch (SQLException e)
{
e.printStackTrace();
outputArea.setText("An error occurred while fetching library books information: " +
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
