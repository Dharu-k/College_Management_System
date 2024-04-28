import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class CollegeEventsGUI extends JFrame implements ActionListener
{
private JPanel buttonPanel;
private JPanel cards;
private CardLayout cardLayout;
private JButton backButton;
private String studentId;
public CollegeEventsGUI(String studentId)
{
this.studentId = studentId;
setTitle("College Events");
setSize(800, 600);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLocationRelativeTo(null);
buttonPanel = new JPanel();
buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
JButton collegeDayButton = createButton("College Day");
JButton techFestButton = createButton("Tech Fest");
JButton holidaysButton = createButton("Holidays");
JButton sportsDayButton = createButton("Sports Day");
JButton culturalFestivalButton = createButton("Cultural Festival");
buttonPanel.add(collegeDayButton);
buttonPanel.add(techFestButton);
buttonPanel.add(holidaysButton);
buttonPanel.add(sportsDayButton);
buttonPanel.add(culturalFestivalButton);
cardLayout = new CardLayout();
cards = new JPanel(cardLayout);
JPanel collegeDayPanel = createEventPanel("College Day", "April 15, 2024", "10:00 AM",
"Auditorium");
JPanel techFestPanel = createEventPanel("Tech Fest", "May 20-22, 2024", "9:00 AM - 5:00 PM",
"Engineering Block");
JPanel holidaysPanel = createEventPanel("Holidays", "June 1-10, 2024", "", "College Closed");
JPanel sportsDayPanel = createEventPanel("Sports Day", "July 5-7, 2024", "8:00 AM - 6:00 PM",
"Sports Ground");
JPanel culturalFestivalPanel = createEventPanel("Cultural Festival", "August 15-17, 2024",
"10:00 AM - 10:00 PM", "Open Air Theater");
cards.add(collegeDayPanel, "College Day");
cards.add(techFestPanel, "Tech Fest");
cards.add(holidaysPanel, "Holidays");

cards.add(sportsDayPanel, "Sports Day");
cards.add(culturalFestivalPanel, "Cultural Festival");
backButton = new JButton("Back");
backButton.addActionListener(this);
add(buttonPanel, BorderLayout.NORTH);
add(cards, BorderLayout.CENTER);
add(backButton, BorderLayout.SOUTH);
setVisible(true);
}
private JButton createButton(String text)
{
JButton button = new JButton(text);
button.addActionListener(this);
return button;
}
private JPanel createEventPanel(String eventName, String date, String time, String venue) {
JPanel panel = new JPanel();
panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
JLabel nameLabel = new JLabel(eventName);
JLabel dateLabel = new JLabel("Date: " + date);
JLabel timeLabel = new JLabel("Time: " + time);
JLabel venueLabel = new JLabel("Venue: " + venue);
panel.add(nameLabel);
panel.add(dateLabel);
panel.add(timeLabel);
panel.add(venueLabel);
return panel;
}
@Override
public void actionPerformed(ActionEvent e) {
String command = e.getActionCommand();
if (command.equals("Back")) {
dispose();
new HomePage(studentId).setVisible(true);
}
else {
cardLayout.show(cards, command);
}
}
public static void main(String[] args) {
SwingUtilities.invokeLater(() -> new CollegeEventsGUI("student_id"));
}
}
