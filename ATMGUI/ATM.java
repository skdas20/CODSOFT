import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ATM extends JFrame {
    private JLabel imageLabel;
    private ImageIcon image1;
    private ImageIcon image2;
    private Random random = new Random();
    private final String VALID_PIN = "1010";
    private final String SOUND_FILE = "sound.wav";
    private final String ACCOUNT_NAME = "Sumit Kumar Das";
    private final String ACCOUNT_NUMBER = String.valueOf(random.nextInt(900000000) + 100000000);  // Random 9-digit number
    private double balance = 5000;  // Initial balance

    public ATM() {
        setTitle("Image Display");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Load images
        image1 = new ImageIcon("image1.png");
        image2 = new ImageIcon("image2.png");

        // Create a label to hold the image
        imageLabel = new JLabel(image1);
        imageLabel.setLayout(new GridBagLayout()); // Use GridBagLayout to position buttons
        add(imageLabel, BorderLayout.CENTER);

        // Create buttons
        String[] buttonLabels = {"Withdraw", "Deposit", "Transfer", "Account Info"};
        JButton[] buttons = new JButton[buttonLabels.length];
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].setBackground(Color.MAGENTA);  // Purple color
            buttons[i].setPreferredSize(new Dimension(150, 50)); // Set button size
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        }

        // Position buttons using GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Position first button at the top-left
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        imageLabel.add(buttons[0], gbc);

        // Position second button at the bottom-left
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        imageLabel.add(buttons[1], gbc);

        // Position third button at the top-right
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        imageLabel.add(buttons[2], gbc);

        // Position fourth button at the bottom-right
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        imageLabel.add(buttons[3], gbc);

        // Add action listeners for buttons
        buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound();
                displayAccountTypeOptions();
            }
        });

        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound();
                promptForPin("deposit");
            }
        });

        buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound();
                promptForPin("transfer");
            }
        });

        buttons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound();
                promptForPin("accountInfo");
            }
        });

        // Schedule image change after 5 seconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> imageLabel.setIcon(image2));
            }
        }, 5000);
    }

    private void displayAccountTypeOptions() {
        imageLabel.removeAll();

        JLabel chooseAccountLabel = new JLabel("Choose Account Type:");
        chooseAccountLabel.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font size
        chooseAccountLabel.setForeground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(chooseAccountLabel, gbc);

        JButton savingsButton = new JButton("Savings");
        savingsButton.setBackground(Color.MAGENTA);
        savingsButton.setPreferredSize(new Dimension(150, 50));
        savingsButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(savingsButton, gbc);

        JButton currentButton = new JButton("Current");
        currentButton.setBackground(Color.MAGENTA);
        currentButton.setPreferredSize(new Dimension(150, 50));
        currentButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(currentButton, gbc);

        JButton backButton = createBackButton();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(backButton, gbc);

        savingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound();
                promptForPin("withdraw");
            }
        });

        currentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound();
                promptForPin("withdraw");
            }
        });

        imageLabel.revalidate();
        imageLabel.repaint();
    }

    private void promptForPin(String action) {
        imageLabel.removeAll();

        JLabel pinLabel = new JLabel("Enter PIN Number:");
        pinLabel.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font size
        pinLabel.setForeground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(pinLabel, gbc);

        JPasswordField pinField = new JPasswordField(10);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(pinField, gbc);

        JButton enterButton = new JButton("Enter");
        enterButton.setBackground(Color.MAGENTA);
        enterButton.setPreferredSize(new Dimension(150, 50));
        enterButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(enterButton, gbc);

        JButton backButton = createBackButton();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(backButton, gbc);

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredPin = new String(pinField.getPassword());
                if (enteredPin.equals(VALID_PIN)) {
                    playSound();
                    switch (action) {
                        case "withdraw":
                            promptForAmount("Withdraw");
                            break;
                        case "deposit":
                            promptForAmount("Deposit");
                            break;
                        case "transfer":
                            promptForRecipientAccount();
                            break;
                        case "accountInfo":
                            displayAccountInfo();
                            break;
                    }
                } else {
                    displayInvalidPinMessage(action);
                }
            }
        });

        imageLabel.revalidate();
        imageLabel.repaint();
    }

    private void promptForAmount(String action) {
        imageLabel.removeAll();

        JLabel amountLabel = new JLabel("Enter Amount:");
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font size
        amountLabel.setForeground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(amountLabel, gbc);

        JTextField amountField = new JTextField(10);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(amountField, gbc);

        JButton enterButton = new JButton("Enter");
        enterButton.setBackground(Color.MAGENTA);
        enterButton.setPreferredSize(new Dimension(150, 50));
        enterButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(enterButton, gbc);

        JButton backButton = createBackButton();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(backButton, gbc);

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound();
                double amount = Double.parseDouble(amountField.getText());
                switch (action) {
                    case "Withdraw":
                        if (balance >= amount) {
                            balance -= amount;
                            displayActionCompletedMessage(action);
                        } else {
                            displayInsufficientFundsMessage();
                        }
                        break;
                    case "Deposit":
                        balance += amount;
                        displayActionCompletedMessage(action);
                        break;
                    case "Transfer":
                        if (balance >= amount) {
                            balance -= amount;
                            displayActionCompletedMessage(action);
                        } else {
                            displayInsufficientFundsMessage();
                        }
                        break;
                    case "modifyBalance":
                        balance = amount;
                        displayActionCompletedMessage("Balance Modification");
                        break;
                }
            }
        });

        imageLabel.revalidate();
        imageLabel.repaint();
    }

    private void promptForRecipientAccount() {
        imageLabel.removeAll();

        JLabel recipientLabel = new JLabel("Enter Recipient's Account Number:");
        recipientLabel.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font size
        recipientLabel.setForeground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(recipientLabel, gbc);

        JTextField recipientField = new JTextField(10);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(recipientField, gbc);

        JButton enterButton = new JButton("Enter");
        enterButton.setBackground(Color.MAGENTA);
        enterButton.setPreferredSize(new Dimension(150, 50));
        enterButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(enterButton, gbc);

        JButton backButton = createBackButton();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(backButton, gbc);

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound();
                promptForAmount("Transfer");
            }
        });

        imageLabel.revalidate();
        imageLabel.repaint();
    }

    private void displayAccountInfo() {
        imageLabel.removeAll();

        JLabel infoLabel = new JLabel("<html>Account Information:<br/>Name: " + ACCOUNT_NAME + "<br/>Account Number: " + ACCOUNT_NUMBER + "<br/>Balance: $" + balance + "</html>");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font size
        infoLabel.setForeground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(infoLabel, gbc);

        JButton backButton = createBackButton();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(backButton, gbc);

        // Add modify balance button
        JButton modifyBalanceButton = new JButton("Modify Balance");
        modifyBalanceButton.setBackground(Color.MAGENTA);
        modifyBalanceButton.setPreferredSize(new Dimension(150, 50));
        modifyBalanceButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(modifyBalanceButton, gbc);

        modifyBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound();
                promptForAmount("modifyBalance");
            }
        });

        imageLabel.revalidate();
        imageLabel.repaint();
    }

    private void displayInvalidPinMessage(String action) {
        imageLabel.removeAll();

        JLabel invalidPinLabel = new JLabel("Invalid PIN. Please try again.");
        invalidPinLabel.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font size
        invalidPinLabel.setForeground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(invalidPinLabel, gbc);

        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.MAGENTA);
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(backButton, gbc);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound();
                promptForPin(action);
            }
        });

        imageLabel.revalidate();
        imageLabel.repaint();
    }

    private void displayActionCompletedMessage(String action) {
        imageLabel.removeAll();

        JLabel completedLabel = new JLabel(action + " Completed Successfully.Current Balance: $" + balance);
        completedLabel.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font size
        completedLabel.setForeground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(completedLabel, gbc);

        JButton backButton = createBackButton();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(backButton, gbc);

        imageLabel.revalidate();
        imageLabel.repaint();
    }

    private void displayInsufficientFundsMessage() {
        imageLabel.removeAll();

        JLabel insufficientFundsLabel = new JLabel("Insufficient funds. Please enter a lesser amount.");
        insufficientFundsLabel.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font size
        insufficientFundsLabel.setForeground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(insufficientFundsLabel, gbc);

        JButton backButton = createBackButton();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        imageLabel.add(backButton, gbc);

        imageLabel.revalidate();
        imageLabel.repaint();
    }

    private JButton createBackButton() {
        JButton backButton = new JButton("Main Menu");
        backButton.setBackground(Color.MAGENTA);
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound();
                resetToMainMenu();
            }
        });
        return backButton;

    }private void resetToMainMenu() {
        imageLabel.removeAll();
        imageLabel.setIcon(image1);

        // Re-create buttons and add action listeners
        String[] buttonLabels = {"Withdraw", "Deposit", "Transfer", "Account Info"};
        JButton[] buttons = new JButton[buttonLabels.length];
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].setBackground(Color.MAGENTA);  // Purple color
            buttons[i].setPreferredSize(new Dimension(150, 50)); // Set button size
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        }

        // Position buttons using GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Position first button at the top-left
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        imageLabel.add(buttons[0], gbc);

        // Position second button at the bottom-left
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        imageLabel.add(buttons[1], gbc);

        // Position third button at the top-right
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        imageLabel.add(buttons[2], gbc);

        // Position fourth button at the bottom-right
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        imageLabel.add(buttons[3], gbc);

        buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound();
                displayAccountTypeOptions();
            }
        });

        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound();
                promptForPin("deposit");
            }
        });

        buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound();
                promptForPin("transfer");
            }
        });

        buttons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound();
                promptForPin("accountInfo");
            }
        });

        imageLabel.revalidate();
        imageLabel.repaint();
    }



    private void playSound() {
        try {
            File soundFile = new File(SOUND_FILE);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(soundFile));
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ATM().setVisible(true);
            }
        });
    }
}
