package View;

import Controller.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePlayMFrm extends JFrame {
    private int score = 0;
    private int timeLeft = 20;
    private Timer countdownTimer;
    private ArrayList<JButton> buttons = new ArrayList<>();
    private int roomIDX;

    private JLabel roomLabel;
    private JLabel timerLabel;
    private JLabel scoreLabel;
    private JPanel matrixPanel;
    private JPanel dropPanel;
    private JLabel basketLabel;

    private ImageIcon riceIcon;
    private ImageIcon paddyIcon;

    public GamePlayMFrm(int room_ID) {
        this.roomIDX = room_ID;
        initComponents();
        showStartDialog();
        roomLabel.setText("Phòng: " + room_ID);
    }

    private void showStartDialog() {
        JDialog startDialog = new JDialog(this, "Thông báo", true);
        startDialog.setLayout(new BorderLayout());

        JLabel message = new JLabel("Bạn đã sẵn sàng chơi chưa?", SwingConstants.CENTER);
        message.setFont(new Font("Segoe UI", Font.BOLD, 18));
        startDialog.add(message, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        okButton.setBackground(new Color(34, 139, 34));
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.addActionListener(e -> {
            startDialog.dispose();
            startGame();
        });

        startDialog.add(okButton, BorderLayout.SOUTH);
        startDialog.setSize(400, 200);
        startDialog.setLocationRelativeTo(this);
        startDialog.setVisible(true);
    }

    private void startGame() {
        startCountdown();
        generateFallingButtons();
    }

    private void generateFallingButtons() {
        riceIcon = new ImageIcon(new ImageIcon("assets/gao.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        paddyIcon = new ImageIcon(new ImageIcon("assets/thoc.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        Timer buttonGenerator = new Timer(1000, e -> {
            JButton button = new JButton();

            if (Math.random() < 0.5) {
                button.setIcon(riceIcon);
            } else {
                button.setIcon(paddyIcon);
            }

            button.setSize(50, 50);
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);

            int xPosition = (int) (Math.random() * (matrixPanel.getWidth() - 50));
            button.setLocation(xPosition, 0);
            matrixPanel.add(button);
            buttons.add(button);

            Timer moveTimer = new Timer(30, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    button.setLocation(button.getX(), button.getY() + 5);

                    if (button.getY() > matrixPanel.getHeight()) {
                        matrixPanel.remove(button);
                        buttons.remove(button);
                        ((Timer) e.getSource()).stop();
                    }

                    matrixPanel.revalidate();
                    matrixPanel.repaint();
                }
            });

            moveTimer.start();

            enableDragAndDrop(button, button.getIcon().equals(paddyIcon));
        });

        buttonGenerator.start();
    }

    private void enableDragAndDrop(JButton button, boolean isPaddy) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                button.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }
        });

        button.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point location = SwingUtilities.convertPoint(button, e.getPoint(), matrixPanel);
                button.setLocation(location.x - button.getWidth() / 2, location.y - button.getHeight() / 2);
            }
        });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // Get the bounds of the basket
                Rectangle basketBounds = basketLabel.getBounds();
                basketBounds = SwingUtilities.convertRectangle(dropPanel, basketBounds, matrixPanel);

                // Check if the button intersects with the basket bounds
                Rectangle buttonBounds = button.getBounds();
                if (basketBounds.intersects(buttonBounds)) {
                    if (isPaddy) {
                        score++;
                        scoreLabel.setText("Điểm: " + score);
                        System.out.println("Paddy grain dropped into basket! Score: " + score);
                    } else {
                        System.out.println("Rice grain dropped into basket. No points added.");
                    }
                    matrixPanel.remove(button);
                    buttons.remove(button);
                } else {
                    System.out.println("Dropped outside the basket. No points added.");
                }

                matrixPanel.revalidate();
                matrixPanel.repaint();
            }
        });
    }

    private void startCountdown() {
        countdownTimer = new Timer(1000, e -> {
            if (timeLeft > 0) {
                timeLeft--;
                timerLabel.setText("Thời gian: " + timeLeft + "s");
            } else {
                countdownTimer.stop();
                showNotice("Hết giờ! Điểm của bạn: " + score);
                sendScoreToServer(Client.user.getID(), score, roomIDX);
            }
        if(timeLeft == 0) SwingUtilities.getWindowAncestor(matrixPanel).dispose(); 
        });
        countdownTimer.start();
    }

    private void sendScoreToServer(int playerId, int score, int roomIDX) {
        try {
            String message = "game-result," + playerId + "," + score + "," + roomIDX;
            Client.socketHandle.write(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showNotice(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Kết quả", JOptionPane.INFORMATION_MESSAGE);
    }

    private void initComponents() {
        setTitle("Game: Nhặt Thóc");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Header Panel
        JPanel headerPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        headerPanel.setBackground(new Color(54, 57, 63));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        roomLabel = new JLabel("Phòng: ");
        roomLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        roomLabel.setForeground(Color.WHITE);

        timerLabel = new JLabel("Thời gian: 20s");
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        timerLabel.setForeground(new Color(255, 69, 0));

        scoreLabel = new JLabel("Điểm: 0");
        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        scoreLabel.setForeground(new Color(34, 139, 34));

        headerPanel.add(roomLabel);
        headerPanel.add(timerLabel);
        headerPanel.add(scoreLabel);

        // Matrix Panel
        matrixPanel = new JPanel();
        matrixPanel.setBackground(new Color(50, 50, 50)); // Dark background for visibility
        matrixPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        matrixPanel.setLayout(null);

        // Drop Panel (Basket)
        dropPanel = new JPanel();
        dropPanel.setBackground(new Color(240, 255, 200));
        dropPanel.setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34), 2));
        dropPanel.setPreferredSize(new Dimension(800, 100));

        basketLabel = new JLabel(new ImageIcon("assets/basket.png"));
        dropPanel.setLayout(new BorderLayout());
        dropPanel.add(basketLabel, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(matrixPanel, BorderLayout.CENTER);
        add(dropPanel, BorderLayout.SOUTH);
    }
}
