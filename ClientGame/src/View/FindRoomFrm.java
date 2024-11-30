package View;

import Controller.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class FindRoomFrm extends JFrame {
    private Timer timer;
    private boolean isFinded;

    public FindRoomFrm() {
        initComponents();
        this.setTitle("Picking Paddy and Rice");
        this.setIconImage(new ImageIcon("assets/image/pdicon.png").getImage());
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        jProgressBar1.setValue(70);
        isFinded = false;

        startFind();
        sendFindRequest();
    }

    public void stopAllThread() {
        timer.stop();
    }

    public void startFind() {
        jLabel3.setVisible(false);
        timer = new Timer(1000, e -> {
            int count = Integer.parseInt(jLabel2.getText().split(":")[1]) - 1;
            if (count >= 0) {
                jLabel2.setText(String.format("00:%02d", count));
                jProgressBar1.setValue((count * 100) / 20);
            } else {
                ((Timer) (e.getSource())).stop();
                try {
                    Client.socketHandle.write("cancel-room,");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(rootPane, ex.getMessage());
                }
                int res = JOptionPane.showConfirmDialog(rootPane, "Tìm kiếm thất bại, bạn muốn thử lại lần nữa chứ?");
                if (res == JOptionPane.YES_OPTION) {
                    startFind();
                    sendFindRequest();
                } else {
                    Client.closeView(Client.View.FINDROOM);
                    Client.openView(Client.View.MAIN);
                }
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    public void sendFindRequest() {
        try {
            Client.socketHandle.write("quick-room,");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }

    public void showFindedRoom() {
        isFinded = true;
        timer.stop();
        jLabel3.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setPaint(new GradientPaint(0, 0, new Color(64, 64, 128), getWidth(), 0, new Color(128, 128, 192)));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jProgressBar1 = new JProgressBar();
        jButton1 = new JButton();
        jLabel3 = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Header Panel
        jPanel1.setPreferredSize(new Dimension(500, 60));

        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setForeground(Color.WHITE);
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("Đang tìm đối thủ");

        GroupLayout panel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );
        panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        // Timer Label
        jLabel2.setFont(new Font("Segoe UI", Font.BOLD, 20));
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.setText("00:20");

        // Progress Bar
        jProgressBar1.setValue(0);
        jProgressBar1.setForeground(new Color(51, 204, 255));
        jProgressBar1.setBackground(new Color(220, 220, 220));
        jProgressBar1.setBorder(BorderFactory.createLineBorder(new Color(64, 64, 128), 1));

        // Status Label
        jLabel3.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel3.setForeground(new Color(34, 139, 34));
        jLabel3.setText("Đã tìm thấy đối thủ, đang vào phòng");
        jLabel3.setVisible(false);

        // Exit Button
        jButton1.setText("Thoát");
        jButton1.setFont(new Font("Segoe UI", Font.BOLD, 16));
        jButton1.setBackground(new Color(220, 53, 69));
        jButton1.setForeground(Color.WHITE);
        jButton1.setFocusPainted(false);
        jButton1.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        jButton1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButton1.addActionListener(this::jButton1ActionPerformed);

        // Layout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jProgressBar1, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                        .addGap(20)
                        .addComponent(jLabel2)
                        .addGap(20)
                        .addComponent(jProgressBar1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addGap(20)
                        .addComponent(jLabel3)
                        .addGap(20)
                        .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                        .addGap(20)
        );

        pack();
        setSize(500, 350);
        setLocationRelativeTo(null);
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        if (isFinded)
            return;
        try {
            Client.socketHandle.write("cancel-room,");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
        timer.stop();
        Client.closeView(Client.View.FINDROOM);
        Client.openView(Client.View.MAIN);
    }

    // Variables declaration
    private JButton jButton1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JPanel jPanel1;
    private JProgressBar jProgressBar1;
}
