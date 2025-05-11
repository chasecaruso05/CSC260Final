import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;

public class Calendar260 {
    private JFrame frame;
    private JPanel calendarPanel;
    private JLabel monthYearLabel;
    private LocalDate currentDate;
    private HashMap<Long, ArrayList<String>> eventStorage;
    @SuppressWarnings("unused")
    private StringBuilder sb;

 
 
    public Calendar260() {
        currentDate = LocalDate.now();
        eventStorage = new HashMap<>();
        MainFrame();
    }

    private void MainFrame() {
        frame = new JFrame("Calendar 260");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

       
        
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        //do month naviagtion buttons (tut?)

        //calendar navigation

        JButton prevButton = new JButton("<");
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDate = currentDate.minusMonths(1); 
                updateCalendar();
            }
        });

        JButton nextButton = new JButton(">");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentDate = currentDate.plusMonths(1); 
                updateCalendar();
            }
        });

        monthYearLabel = new JLabel("", SwingConstants.CENTER);
        monthYearLabel.setFont(new Font("Arial", Font.BOLD, 16));

        topPanel.add(prevButton, BorderLayout.WEST);
        topPanel.add(monthYearLabel, BorderLayout.CENTER);
        topPanel.add(nextButton, BorderLayout.EAST);

        frame.add(topPanel, BorderLayout.NORTH);

      //rows and columns done
        calendarPanel = new JPanel();
        calendarPanel.setLayout(new GridLayout(0, 7)); 
        frame.add(calendarPanel, BorderLayout.CENTER);

        
        updateCalendar();

        frame.setVisible(true);
    }

    private void updateCalendar() {
       
        calendarPanel.removeAll();

       
        YearMonth yearMonth = YearMonth.of(currentDate.getYear(), currentDate.getMonthValue());
        monthYearLabel.setText(currentDate.getMonth() + " " + currentDate.getYear());

        
        String[] weekDays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        for (String day : weekDays) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Arial", Font.BOLD, 14));
            calendarPanel.add(dayLabel);
        }

       
        int firstDayOfMonth = yearMonth.atDay(1).getDayOfWeek().getValue() % 7; 
        int daysInMonth = yearMonth.lengthOfMonth();

        //spacing
        for (int i = 0; i < firstDayOfMonth; i++) {
            calendarPanel.add(new JLabel(""));
        }

        // buttons for the calendar
        for (int day = 1; day <= daysInMonth; day++) {
            long storageKey = LocalDate.of(currentDate.getYear(), currentDate.getMonthValue(), day).toEpochDay();
            ArrayList<String> storedEvents = eventStorage.get(storageKey);
            JButton dayButton = new JButton(String.valueOf(day));
            if (storedEvents != null) {
                dayButton.setBackground(new Color(255, 165, 0));
                sb = new StringBuilder();
                dayButton.addActionListener(e -> {
                    int result = JOptionPane.showOptionDialog(frame, "This is " + dayButton.getText() + " of " + monthYearLabel.getText() + ".\nAdd Event?", 
                        "Edit Day", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (result == JOptionPane.YES_OPTION) {
                        String newEvent = JOptionPane.showInputDialog(frame, "Enter Event:");
                        if (newEvent != null && !newEvent.trim().isEmpty()) {
                            storedEvents.add(newEvent);
                            eventStorage.put(storageKey, storedEvents);
                        }
                    } else if (result == JOptionPane.NO_OPTION) {
                        StringBuilder eventList = new StringBuilder();
                        eventList.append("Events on ").append(dayButton.getText()).append(" of ").append(monthYearLabel.getText()).append(":\n");
                        for (String event : storedEvents) {
                            eventList.append(event).append("\n");
                        }
                        JOptionPane.showMessageDialog(frame, eventList.toString(), dayButton.getText() + " " + monthYearLabel.getText(), JOptionPane.INFORMATION_MESSAGE);
                    }
                    updateCalendar();
                });                
            } else {
                sb = new StringBuilder();
                dayButton.addActionListener(e -> {
                    ArrayList<String> newStoredEvents = new ArrayList<>();
                    int result = JOptionPane.showOptionDialog(frame, "This is " + dayButton.getText() + " of " + monthYearLabel.getText() + ".\nAdd Event?", 
                        "Edit Day", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (result == JOptionPane.YES_OPTION) {
                        String newEvent = JOptionPane.showInputDialog(frame, "Enter Event:");
                        if (newEvent != null && !newEvent.trim().isEmpty()) {
                            newStoredEvents = new ArrayList<>();
                            newStoredEvents.add(newEvent);
                            eventStorage.put(storageKey, newStoredEvents);
                        }
                    } else if (result == JOptionPane.NO_OPTION) {
                        StringBuilder eventList = new StringBuilder();
                        for (String event : newStoredEvents) {
                            eventList.append(event).append("\n");
                        }
                        JOptionPane.showMessageDialog(frame, "There are no events on this day yet.", dayButton.getText() + " " + monthYearLabel.getText(), JOptionPane.INFORMATION_MESSAGE);
                    }
                    updateCalendar();
                });
            }

            //checking buttons 
            
            
            
            calendarPanel.add(dayButton);
        }

       
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calendar260::new);
    }
}