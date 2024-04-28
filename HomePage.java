import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class HomePage extends JFrame implements ActionListener
{
private JButton biodataButton, marksButton, attendanceButton, eventsButton, feesButton,
logoutButton;
private JLabel collegeNameLabel;
private String studentId;
public HomePage(String studentId)
{
this.studentId = studentId;
setTitle("Home Page");
setSize(800, 600);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLocationRelativeTo(null);
JPanel buttonPanel = new JPanel();
buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
collegeNameLabel = new JLabel("Rolex College of Engineering and Technology");
collegeNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
collegeNameLabel.setFont(new Font("Arial", Font.BOLD, 24));
add(collegeNameLabel, BorderLayout.NORTH);
addButton("Biodata", buttonPanel);
addButton("Marks", buttonPanel);
addButton("Attendance", buttonPanel);
addButton("Events", buttonPanel);
addButton("Fees", buttonPanel);
addButton("Library", buttonPanel);
addButton("Logout", buttonPanel);
add(buttonPanel, BorderLayout.CENTER);
setVisible(true);
}
public HomePage()
{
}
private void addButton(String text, JPanel panel)
{
JButton button = new JButton(text);
button.setAlignmentX(Component.CENTER_ALIGNMENT);
button.setMaximumSize(new Dimension(200, 30));

button.addActionListener(this);
panel.add(Box.createVerticalStrut(10));
panel.add(button);
}
@Override
public void actionPerformed(ActionEvent e)
{
String command = e.getActionCommand();
switch (command) {
case "Biodata":
StudentBioDataGUI studentBioDataGUI = new StudentBioDataGUI(studentId);
studentBioDataGUI.setVisible(true);
break;
case "Marks":
MarksManagementSystem marksManagementSystem = new
MarksManagementSystem(studentId);
marksManagementSystem.setVisible(true);
break;
case "Attendance":
AttendanceSystemGUI attendanceSystemGUI = new AttendanceSystemGUI(studentId);
attendanceSystemGUI.setVisible(true);
break;
case "Events":
CollegeEventsGUI collegeEventsGUI = new CollegeEventsGUI(studentId);
collegeEventsGUI.setVisible(true);
break;
case "Fees":
FeeManagementSystem feeSystem = new FeeManagementSystem(studentId);
feeSystem.setVisible(true);
break;
case "Library":
StudentLibraryBooksGUI studentLibraryBooksGUI = new
StudentLibraryBooksGUI(studentId);
studentLibraryBooksGUI.setVisible(true);
break;
case "Logout":
LoginFrame loginFrame = new LoginFrame();
loginFrame.setVisible(true);
dispose();
break;
default:
JOptionPane.showMessageDialog(this, "Unknown action.");
break;
}
}
}
