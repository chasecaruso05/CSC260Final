import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.YearMonth;

public class Calendar260 {
    private JFrame frame;
    private JPanel calendarPanel;
    private JLabel monthYearLabel;
    private LocalDate currentDate;

 
 
    public Calendar260() {
        currentDate = LocalDate.now();
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
            JButton dayButton = new JButton(String.valueOf(day));
            
           //checking buttons
            dayButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(frame, 
                    "This is  " + dayButton.getText() + " of " + monthYearLabel.getText() + ".");
            });
            calendarPanel.add(dayButton);
        }

       
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calendar260::new);
    }
}