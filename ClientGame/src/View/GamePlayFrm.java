package View;

import Controller.Client;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;

public class GamePlayFrm extends javax.swing.JFrame {
    private int score = 0;             // Điểm ban đầu
    private int timeLeft = 20;         // Thời gian đếm ngược ban đầu (20 giây)
    private int totalPaddyCount = 0;   // Tổng số hạt thóc trong ma trận
    private int collectedPaddyCount = 0; // Số hạt thóc đã thu thập
    private Timer countdownTimer;      // Bộ đếm ngược
    private boolean isTimerStarted = false; // Biến kiểm tra bộ đếm đã bắt đầu chưa
    private ArrayList<Timer> buttonTimers = new ArrayList<>(); // Lưu trữ các Timer của nút

     public GamePlayFrm() {
        initComponents();
        showStartDialog(); // Hiển thị dialog thông báo
        customizeUI();
    }
     
   // Hiển thị JDialog khi bắt đầu trò chơi
    private void showStartDialog() {
        JDialog startDialog = new JDialog(this, "Thông báo", true);
        startDialog.setLayout(new BorderLayout());
        startDialog.add(new JLabel("Bạn đã sẵn sàng chơi chưa"), BorderLayout.CENTER);
        JButton okButton = new JButton("OK");

        okButton.addActionListener(e -> {
            startDialog.dispose(); // Đóng dialog khi nhấn OK
            generateRandomMatrix(); // Tạo ma trận sau khi đóng dialog
            startCountdown(); // Bắt đầu bộ đếm ngược
        });

        startDialog.add(okButton, BorderLayout.SOUTH);
        startDialog.setSize(300, 200);
        startDialog.setLocationRelativeTo(this);
        startDialog.setVisible(true);
    }

    // Hàm tạo các nút ngẫu nhiên trong matrixPanel
    private void generateRandomMatrix() {
        Color panelColor = matrixPanel.getBackground();

        // Tải ảnh hạt gạo và hạt thóc từ tài nguyên
        ImageIcon riceIcon = new ImageIcon(new ImageIcon("assets/gao.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        ImageIcon paddyIcon = new ImageIcon(new ImageIcon("assets/thoc.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        for (int i = 0; i < 10; i++) { // Tạo 10 nút ngẫu nhiên
            int randomNumber = (int) (Math.random() * 2); // Số ngẫu nhiên 0 hoặc 1
            JButton button = new JButton();
            button.setSize(50, 50); // Đặt kích thước nút
            button.setLocation((int) (Math.random() * (matrixPanel.getWidth() - 50)), 0);

            // Đặt hình ảnh tương ứng với loại hạt
            if (randomNumber == 0) {
                button.setIcon(riceIcon); // Hạt gạo
            } else {
                button.setIcon(paddyIcon); // Hạt thóc
                totalPaddyCount++;
            }

            button.setOpaque(true);
            button.setBorderPainted(false);
            button.setFocusPainted(false);

            // Sự kiện kéo thả cho nút
            button.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    button.setLocation(e.getXOnScreen() - matrixPanel.getLocationOnScreen().x - button.getWidth() / 2,
                                       e.getYOnScreen() - matrixPanel.getLocationOnScreen().y - button.getHeight() / 2);
                }
            });

            button.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent e) {
                    if (dropPanel.getBounds().contains(button.getBounds())) {
                        if (button.getIcon() == paddyIcon) { // Nếu là hạt thóc
                            matrixPanel.remove(button); // Xóa nút khỏi panel
                            score++;
                            scoreLabel.setText("Score: " + score);

                        } else if (button.getIcon() == riceIcon) { // Nếu là hạt gạo
                            score--; // Giảm điểm nếu thả hạt gạo
                            scoreLabel.setText("Score: " + score);
                            matrixPanel.remove(button);
                        }
                        matrixPanel.revalidate();
                        matrixPanel.repaint();
                    }
                }
            });

            matrixPanel.add(button);

            // Timer để di chuyển nút từ trên xuống dưới
            Timer moveTimer = new Timer(100, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    button.setLocation(button.getX(), button.getY() + 2); // Di chuyển chậm xuống dưới
                    
                    // Kiểm tra nếu nút chạm đến dropPanel (label)
                    if (button.getY() + button.getHeight() >= matrixPanel.getHeight() - dropPanel.getHeight()) {
                        Rectangle buttonBounds = button.getBounds();
                        Rectangle dropBounds = dropPanel.getBounds();
                        dropBounds.setLocation(0, matrixPanel.getHeight() - dropPanel.getHeight()); // Đặt lại vị trí của dropPanel cho chuẩn

                        // Kiểm tra nếu nút nằm trong vùng dropPanel
                        if (dropBounds.intersects(buttonBounds)) {
                            if (button.getIcon().toString().contains("thoc")) { // Nếu là hạt thóc
                                score++; // Tăng điểm
                                collectedPaddyCount++; // Đếm hạt thóc đã thu thập
                                scoreLabel.setText("Score: " + score);
                            } else if (button.getIcon().toString().contains("gao")) { // Nếu là hạt gạo
                                score--; // Giảm điểm
                                scoreLabel.setText("Score: " + score);
                            }
                            matrixPanel.remove(button); // Xóa nút khỏi matrixPanel
                            ((Timer) e.getSource()).stop(); // Dừng timer di chuyển của nút
                        }
                    }

                    // Kiểm tra nếu nút đã vượt khỏi chiều cao của panel và loại bỏ nó
                    if (button.getY() > matrixPanel.getHeight()) {
                        matrixPanel.remove(button);
                        ((Timer) e.getSource()).stop();
                    }

                    matrixPanel.revalidate();
                    matrixPanel.repaint();
                }
            });
            moveTimer.start(); // Bắt đầu di chuyển nút
            buttonTimers.add(moveTimer); // Thêm timer vào danh sách
            matrixPanel.add(button); // Thêm nút vào matrixPanel
        }
    }

    // Hàm bắt đầu đếm ngược thời gian 20 giây
    private void startCountdown() {
        countdownTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft > 0) {
                    timeLeft--;
                    timerLabel.setText("Time left: " + timeLeft + "s");

                    // Mỗi 5 giây tạo lại nút ngẫu nhiên
                    if(timeLeft % 3 == 0 && timeLeft != 0){
//                        matrixPanel.removeAll(); // Xóa tất cả các nút trong panel
                        generateRandomMatrix(); // Tạo lại ma trận
                        matrixPanel.revalidate(); // Cập nhật lại layout
                        matrixPanel.repaint(); // Vẽ lại panel
                    }
                } else {
                    countdownTimer.stop();
                    for (Timer timer : buttonTimers) {
                        timer.stop(); 
                    }
                    JOptionPane.showMessageDialog(null, "Time's up! Your score: " + score);
//                    int response = JOptionPane.showConfirmDialog(null, 
//                        "Do you want to play again?", 
//                        "Game Over", 
//                        JOptionPane.YES_NO_OPTION);
//                     if (response == JOptionPane.YES_OPTION) {
//                    if (roomId == null || roomId.isEmpty()) {
//                        Client.openView(Client.View.GAMEPLAY); 
//                    } else {
//                        Client.closeView(Client.View.GAMEPLAY);
//                        Client.openView(Client.View.GAMEMULTI); // Đoạn này có trở lại được phòng cũ hay không?
//                    }
//                } else {
//                        Client.closeView(Client.View.GAMEPLAY);
//                        Client.openView(Client.View.MAIN); 
//                }
                }
            }
        });
        countdownTimer.start();
    }

    private void customizeUI() {
        // Set background colors
        matrixPanel.setBackground(new Color(240, 240, 240));
        dropPanel.setBackground(new Color(255, 255, 255));

        // Set font styles
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        // Set drop panel icon
        dropPanel.setIcon(new ImageIcon(new ImageIcon("assets/basket.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        matrixPanel = new javax.swing.JPanel();
        dropPanel = new javax.swing.JLabel();
        timerLabel = new javax.swing.JLabel();
        scoreLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        matrixPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        dropPanel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/basket.png"))); // NOI18N

        javax.swing.GroupLayout matrixPanelLayout = new javax.swing.GroupLayout(matrixPanel);
        matrixPanel.setLayout(matrixPanelLayout);
        matrixPanelLayout.setHorizontalGroup(
            matrixPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, matrixPanelLayout.createSequentialGroup()
                .addGap(0, 857, Short.MAX_VALUE)
                .addComponent(dropPanel))
        );
        matrixPanelLayout.setVerticalGroup(
            matrixPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, matrixPanelLayout.createSequentialGroup()
                .addContainerGap(475, Short.MAX_VALUE)
                .addComponent(dropPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        timerLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        timerLabel.setText("Time left: 20s");
        timerLabel.setToolTipText("");

        scoreLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        scoreLabel.setText("Score: 0");

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem1.setText("New Game");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem2.setText("Exit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem3.setText("Turtorial");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(25, Short.MAX_VALUE)
                        .addComponent(matrixPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(184, 694, Short.MAX_VALUE)
                        .addComponent(timerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115)
                        .addComponent(scoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 43, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timerLabel)
                    .addComponent(scoreLabel))
                .addGap(18, 18, 18)
                .addComponent(matrixPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
           Client.closeView(Client.View.GAMEPLAY);
           Client.openView(Client.View.GAMEPLAY); 
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
//        exitGame();
          Client.closeView(Client.View.GAMEPLAY);
          Client.openView(Client.View.MAIN);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

//    public static void main(String[] args) {
//        // Thiết lập giao diện để chạy chương trình
//        javax.swing.SwingUtilities.invokeLater(() -> {
//            GamePlayFrm gamePlayFrm = new GamePlayFrm();
//            gamePlayFrm.setVisible(true); // Hiển thị cửa sổ
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel dropPanel;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel matrixPanel;
    private javax.swing.JLabel scoreLabel;
    private javax.swing.JLabel timerLabel;
    // End of variables declaration//GEN-END:variables
//    public void exitGame() {
//        try {
//            Client.socketHandle.write("left-room,");
//            Client.closeAllViews();
//            Client.openView(Client.View.MAIN);
//        } catch (IOException ex) {
//            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
//        }
//        Client.closeAllViews();
//        Client.openView(Client.View.MAIN);
//    }
}
