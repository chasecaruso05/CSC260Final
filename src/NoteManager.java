import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;

public class NoteManager extends JFrame {
    private JTextArea textArea;
    private JTextField titleField;
    private JButton saveButton, loadButton;
    private HashMap<String, String> notes;

    public NoteManager() {
        notes = new HashMap<>();

        titleField = new JTextField("Enter title", 20);
        textArea = new JTextArea(10, 30);
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");

        JPanel panel = new JPanel();
        panel.add(titleField);
        panel.add(new JScrollPane(textArea));
        panel.add(saveButton);
        panel.add(loadButton);

        add(panel);

        // Save function
        saveButton.addActionListener(e -> {
            String title = titleField.getText();
            String content = textArea.getText();
            notes.put(title, content);
            JOptionPane.showMessageDialog(this, "Note saved.");
        });

        // Load function
        loadButton.addActionListener(e -> {
            String title = titleField.getText();
            if (notes.containsKey(title)) {
                textArea.setText(notes.get(title));
            } else {
                JOptionPane.showMessageDialog(this, "Note not found.");
            }
        });

        setTitle("Note Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NoteManager::new);
    }
    
}
