import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class StudentManagementGUI extends JFrame {

    private List<Student> students;
    private final Color skyBlue = new Color(135, 206, 235);
    private final Color darkSkyBlue = new Color(100, 149, 237);
    private JPanel mainPanel;

    public StudentManagementGUI() {
        students = new ArrayList<>();
        addDefaultStudents();

        // Set up the frame
        setTitle("Student Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Load the background image
        ImageIcon imageIcon = new ImageIcon("BG.png");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);
        JLabel background = new JLabel(imageIcon);
        background.setLayout(new GridBagLayout());

        // Create buttons with icons
        JButton addButton = createButton("Add Student", "add_icon.png");
        JButton editButton = createButton("Edit Information", "edit_icon.png");
        JButton searchButton = createButton("Search Student", "search_icon.png");
        JButton displayButton = createButton("Display All Students", "display_icon.png");
        JButton exitButton = createButton("Exit", "exit_icon.png");

        // Create a panel for buttons and use GridBagLayout to center them
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false); // Make the panel transparent

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // Padding between buttons
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        buttonPanel.add(addButton, gbc);
        buttonPanel.add(editButton, gbc);
        buttonPanel.add(searchButton, gbc);
        buttonPanel.add(displayButton, gbc);
        buttonPanel.add(exitButton, gbc);

        // Add the button panel to the center of the background
        background.add(buttonPanel, new GridBagConstraints());

        // Add background to the frame
        add(background, BorderLayout.CENTER);

        // Add main panel for displaying content
        mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        background.add(mainPanel, new GridBagConstraints());

        // Add button functionality
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playClickSound();
                displayAddStudentForm();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playClickSound();
                displayEditStudentForm();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playClickSound();
                displaySearchStudentForm();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playClickSound();
                displayAllStudents();
            }
        });

        // Set exit button functionality
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playClickSound();
                System.exit(0);
            }
        });

        // Set visibility
        setVisible(true);
    }

    private JButton createButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setIcon(new ImageIcon(iconPath));
        button.setBackground(skyBlue);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        // Add mouse-over effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(darkSkyBlue);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(skyBlue);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                playClickSound();
            }
        });

        return button;
    }

    private void playClickSound() {
    try {
        File soundFile = new File("c.au");
        if (!soundFile.exists()) {
            System.err.println("Sound file not found: " + soundFile.getAbsolutePath());
            return;
        }

        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(soundFile));
        clip.start();

        // Wait for the clip to finish playing
        clip.addLineListener(event -> {
            if (event.getType() == LineEvent.Type.STOP) {
                event.getLine().close();
            }
        });
    } catch (UnsupportedAudioFileException e) {
        System.err.println("Unsupported audio file: " + e.getMessage());
    } catch (IOException e) {
        System.err.println("Error reading audio file: " + e.getMessage());
    } catch (LineUnavailableException e) {
        System.err.println("Audio line unavailable: " + e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    
    private void clearMainPanel() {
        mainPanel.removeAll();
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void addDefaultStudents() {
        students.add(new Student("John Doe", "A", 15, 90));
        students.add(new Student("Jane Smith", "B", 14, 85));
        students.add(new Student("Alice Johnson", "A", 16, 95));
        students.add(new Student("Bob Brown", "C", 17, 80));
    }

    private void displayAddStudentForm() {
        clearMainPanel();

        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField(20);
        JLabel gradeLabel = new JLabel("Grade:");
        JTextField gradeField = new JTextField(20);
        JLabel attendanceLabel = new JLabel("Attendance:");
        JTextField attendanceField = new JTextField(20);

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(skyBlue);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setBorderPainted(false);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String ageStr = ageField.getText();
                String grade = gradeField.getText();
                String attendanceStr = attendanceField.getText();

                if (name.isEmpty() || ageStr.isEmpty() || grade.isEmpty() || attendanceStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int age = Integer.parseInt(ageStr);
                    int attendance = Integer.parseInt(attendanceStr);

                    Student student = new Student(name, grade, age, attendance);
                    students.add(student);

                    JOptionPane.showMessageDialog(null, "Student added successfully!");
                    clearMainPanel();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid age or attendance. Please enter numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        mainPanel.add(nameLabel, gbc);
        mainPanel.add(nameField, gbc);
        mainPanel.add(ageLabel, gbc);
        mainPanel.add(ageField, gbc);
        mainPanel.add(gradeLabel, gbc);
        mainPanel.add(gradeField, gbc);
        mainPanel.add(attendanceLabel, gbc);
        mainPanel.add(attendanceField, gbc);
        mainPanel.add(submitButton, gbc);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void displayAllStudents() {
        clearMainPanel();

        if (students.isEmpty()) {
            JLabel noStudentsLabel = new JLabel("No students to display.");
            noStudentsLabel.setFont(new Font("Arial", Font.BOLD, 16));
            mainPanel.add(noStudentsLabel);
            return;
        }

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        for (Student student : students) {
            JLabel studentLabel = new JLabel(student.toString());
            studentLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            mainPanel.add(studentLabel);
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void displayEditStudentForm() {
        clearMainPanel();

        if (students.isEmpty()) {
            JLabel noStudentsLabel = new JLabel("No students to edit.");
            noStudentsLabel.setFont(new Font("Arial", Font.BOLD, 16));
            mainPanel.add(noStudentsLabel);
            return;
        }

        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;

        String[] studentNames = students.stream().map(student -> student.name).toArray(String[]::new);
        JComboBox<String> studentComboBox = new JComboBox<>(studentNames);
        mainPanel.add(new JLabel("Select a student to edit:"), gbc);
        mainPanel.add(studentComboBox, gbc);

        JTextField nameField = new JTextField(20);
        JTextField ageField = new JTextField(20);
        JTextField gradeField = new JTextField(20);
        JTextField attendanceField = new JTextField(20);

        studentComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedName = (String) studentComboBox.getSelectedItem();
                Student selectedStudent = students.stream().filter(s -> s.name.equals(selectedName)).findFirst().orElse(null);

                if (selectedStudent != null) {
                    nameField.setText(selectedStudent.name);
                    ageField.setText(String.valueOf(selectedStudent.age));
                    gradeField.setText(selectedStudent.grade);
                    attendanceField.setText(String.valueOf(selectedStudent.attendance));
                }
            }
        });

        JButton updateButton = new JButton("Update");
        updateButton.setBackground(skyBlue);
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);
        updateButton.setBorderPainted(false);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedName = (String) studentComboBox.getSelectedItem();
                Student selectedStudent = students.stream().filter(s -> s.name.equals(selectedName)).findFirst().orElse(null);

                if (selectedStudent != null) {
                    String name = nameField.getText();
                    String ageStr = ageField.getText();
                    String grade = gradeField.getText();
                    String attendanceStr = attendanceField.getText();

                    if (name.isEmpty() || ageStr.isEmpty() || grade.isEmpty() || attendanceStr.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        int age = Integer.parseInt(ageStr);
                        int attendance = Integer.parseInt(attendanceStr);

                        selectedStudent.name = name;
                        selectedStudent.age = age;
                        selectedStudent.grade = grade;
                        selectedStudent.attendance = attendance;

                        JOptionPane.showMessageDialog(null, "Student updated successfully!");
                        clearMainPanel();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid age or attendance. Please enter numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        mainPanel.add(new JLabel("Name:"), gbc);
        mainPanel.add(nameField, gbc);
        mainPanel.add(new JLabel("Age:"), gbc);
        mainPanel.add(ageField, gbc);
        mainPanel.add(new JLabel("Grade:"), gbc);
        mainPanel.add(gradeField, gbc);
        mainPanel.add(new JLabel("Attendance:"), gbc);
        mainPanel.add(attendanceField, gbc);
        mainPanel.add(updateButton, gbc);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void displaySearchStudentForm() {
        clearMainPanel();

        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;

        JLabel searchLabel = new JLabel("Enter name to search:");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(skyBlue);
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.setBorderPainted(false);

        mainPanel.add(searchLabel, gbc);
        mainPanel.add(searchField, gbc);
        mainPanel.add(searchButton, gbc);

        JTextArea resultArea = new JTextArea(10, 30);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        mainPanel.add(scrollPane, gbc);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = searchField.getText().trim();
                if (searchQuery.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Search field cannot be empty.");
                    return;
                }

                StringBuilder results = new StringBuilder();
                for (Student student : students) {
                    if (student.name.toLowerCase().contains(searchQuery.toLowerCase())) {
                        results.append(student).append("\n");
                    }
                }

                if (results.length() == 0) {
                    resultArea.setText("No students found.");
                } else {
                    resultArea.setText(results.toString());
                }
            }
        });

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private class Student {
        private String name;
        private String grade;
        private int age;
        private int attendance;

        public Student(String name, String grade, int age, int attendance) {
            this.name = name;
            this.grade = grade;
            this.age = age;
            this.attendance = attendance;
        }

        @Override
        public String toString() {
            return "Name: " + name + ", Grade: " + grade + ", Age: " + age + ", Attendance: " + attendance + "%";
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting StudentManagementGUI...");
        new StudentManagementGUI();
    }
    
}
