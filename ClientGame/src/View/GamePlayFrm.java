package View;

import Controller.Client;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import javax.swing.Timer;

public class GamePlayFrm extends javax.swing.JFrame {
    private int score = 0;             // Điểm ban đầu
    private int timeLeft = 20;         // Thời gian đếm ngược ban đầu (20 giây)
    private int totalPaddyCount = 0;   // Tổng số hạt thóc trong ma trận
    private int collectedPaddyCount = 0; // Số hạt thóc đã thu thập
    private Timer countdownTimer;      // Bộ đếm ngược
    private boolean isTimerStarted = false; // Biến kiểm tra bộ đếm đã bắt đầu chưa

     public GamePlayFrm() {
        initComponents();
        showStartDialog(); // Hiển thị dialog thông báo
    }

    // Hàm hiển thị JDialog khi bắt đầu trò chơi
    private void showStartDialog() {
        JDialog startDialog = new JDialog(this, "Thông báo", true);
        startDialog.setLayout(new BorderLayout());
        startDialog.add(new JLabel("Bạn đã sẵn sàng chơi chưa"), BorderLayout.CENTER);
        JButton okButton = new JButton("OK");
        
        okButton.addActionListener(e -> {
            startDialog.dispose(); // Đóng dialog khi nhấn OK
            generateRandomMatrix(); // Tạo ma trận sau khi đóng dialog
        });

        startDialog.add(okButton, BorderLayout.SOUTH);
        startDialog.setSize(300, 200);
        startDialog.setLocationRelativeTo(this);
        startDialog.setVisible(true);
    }

// Hàm tạo ma trận ngẫu nhiên 0 và 1 trong JPanel
private void generateRandomMatrix() {
    matrixPanel.setLayout(new GridLayout(10, 10)); // Đặt layout 10x10
    Color panelColor = matrixPanel.getBackground(); // Lấy màu nền của panel
    
    // Tải ảnh hạt gạo và hạt thóc từ tài nguyên
    ImageIcon riceIcon = new ImageIcon(new ImageIcon("assets/gao.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    ImageIcon paddyIcon = new ImageIcon(new ImageIcon("assets/thoc.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

    for (int i = 0; i < 100; i++) {
        int randomNumber = (int) (Math.random() * 2); // Số ngẫu nhiên 0 hoặc 1
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(50, 50)); // Đặt kích thước nút nhỏ hơn

        // Đặt hình ảnh tương ứng với loại hạt
        if (randomNumber == 0) {
            button.setIcon(riceIcon); // Hạt gạo
        } else {
            button.setIcon(paddyIcon); // Hạt thóc
            totalPaddyCount++; // Tăng tổng số hạt thóc
        }

        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        // Xử lý sự kiện khi nhấn vào nút
        button.addActionListener(e -> {
            // Bắt đầu bộ đếm thời gian nếu chưa được khởi động
            if (!isTimerStarted) {
                startCountdown(); // Gọi hàm bắt đầu đếm ngược khi nhấn nút đầu tiên
                isTimerStarted = true; // Đảm bảo bộ đếm chỉ khởi động một lần
            }

            if (button.getIcon() == paddyIcon) { // Nếu là hạt thóc
                button.setVisible(false); // Ẩn nút khi nhấn vào
                score++; // Tăng điểm
                collectedPaddyCount++; // Tăng số lượng hạt thóc đã thu thập
                scoreLabel.setText("Score: " + score); // Cập nhật điểm trên giao diện

                // Kiểm tra nếu đã thu thập hết hạt thóc
                if (collectedPaddyCount == totalPaddyCount) {
                    countdownTimer.stop(); // Dừng bộ đếm ngược
                    JOptionPane.showMessageDialog(null, "All collected! Your score: " + score + ", Time left: " + timeLeft + "s");
                }
            } else if (button.getIcon() == riceIcon) {
                score--; // Giảm điểm nếu nhấn vào hạt gạo
                scoreLabel.setText("Score: " + score);
            }
        });

        matrixPanel.add(button); // Thêm nút vào panel
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
                if(timeLeft % 5 == 0 && timeLeft != 0){
                    matrixPanel.removeAll(); // Xóa tất cả các nút trong panel
                    generateRandomMatrix(); // Tạo lại ma trận
                    matrixPanel.revalidate(); // Cập nhật lại layout
                    matrixPanel.repaint(); // Vẽ lại panel
                }
            } else {
                countdownTimer.stop();
                JOptionPane.showMessageDialog(null, "Time's up! Your score: " + score);
            }
        }
    });
    countdownTimer.start();
}


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        timerLabel = new javax.swing.JLabel();
        scoreLabel = new javax.swing.JLabel();
        matrixPanel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        timerLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        timerLabel.setText("Time left: 20s");
        timerLabel.setToolTipText("");

        scoreLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        scoreLabel.setText("Score: 0");

        matrixPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout matrixPanelLayout = new javax.swing.GroupLayout(matrixPanel);
        matrixPanel.setLayout(matrixPanelLayout);
        matrixPanelLayout.setHorizontalGroup(
            matrixPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        matrixPanelLayout.setVerticalGroup(
            matrixPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 455, Short.MAX_VALUE)
        );

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
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(matrixPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(644, Short.MAX_VALUE)
                        .addComponent(timerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(scoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)))
                .addGap(83, 83, 83))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timerLabel)
                    .addComponent(scoreLabel))
                .addGap(37, 37, 37)
                .addComponent(matrixPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(185, Short.MAX_VALUE))
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
