package View;

import Controller.Client;
import javax.swing.*;
import java.awt.*;

/**
 * Improved and Enlarged Login Form for "Tấm Nhặt Thóc"
 */
public class LoginFrm extends javax.swing.JFrame {

    public LoginFrm() {
        initComponents();
        this.setTitle("Tấm Nhặt Thóc");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public LoginFrm(String taiKhoan, String matKhau) {
        initComponents();
        jPasswordField1.setText(matKhau);
        jTextField1.setText(taiKhoan);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(rootPane, message);
    }

    public void log(String message) {
        JOptionPane.showMessageDialog(rootPane, "ID của server thread là:" + message);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        unLabel = new javax.swing.JLabel();
        pwdLabel = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jTextField1 = new javax.swing.JTextField();
        LoginButton = new javax.swing.JButton();
        RegisterButton = new javax.swing.JButton();
        titlePanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // Title Panel
        titlePanel.setBackground(new java.awt.Color(0, 102, 153));
        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // Large Title Font
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("TẤM NHẶT THÓC");
        titlePanel.add(titleLabel);

        // Username Label
        unLabel.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 24)); // Enlarged font
        unLabel.setText("Username:");

        // Password Label
        pwdLabel.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 24)); // Enlarged font
        pwdLabel.setText("Password:");

        // Username Field
        jTextField1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 20)); // Enlarged field font
        jTextField1.setMargin(new Insets(10, 10, 10, 10));

        // Password Field
        jPasswordField1.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 20)); // Enlarged field font
        jPasswordField1.setMargin(new Insets(10, 10, 10, 10));

        // Login Button
        LoginButton.setText("Login");
        LoginButton.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 20)); // Enlarged button font
        LoginButton.setBackground(new java.awt.Color(0, 153, 51));
        LoginButton.setForeground(Color.WHITE);
        LoginButton.setFocusPainted(false);
        LoginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        LoginButton.addActionListener(this::LoginButtonActionPerformed);

        // Register Button
        RegisterButton.setText("Register");
        RegisterButton.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 20)); // Enlarged button font
        RegisterButton.setBackground(new java.awt.Color(255, 102, 0));
        RegisterButton.setForeground(Color.WHITE);
        RegisterButton.setFocusPainted(false);
        RegisterButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RegisterButton.addActionListener(this::RegisterButtonActionPerformed);

        // Layout
        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(LoginButton, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                                .addGap(30, 30, 30)
                                                .addComponent(RegisterButton, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(unLabel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(pwdLabel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
                                                .addGap(30, 30, 30)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jTextField1)
                                                        .addComponent(jPasswordField1, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(unLabel)
                                .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(pwdLabel)
                                .addComponent(jPasswordField1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(LoginButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addComponent(RegisterButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(titlePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(titlePanel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setSize(600, 500); // Enlarged frame size
    }// </editor-fold>//GEN-END:initComponents

    private void RegisterButtonActionPerformed(java.awt.event.ActionEvent evt) {
        Client.closeView(Client.View.LOGIN);
        Client.openView(Client.View.REGISTER);
    }

    private void LoginButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String taikhoan = jTextField1.getText();
            if (taikhoan.isEmpty()) throw new Exception("Vui lòng nhập tên tài khoản");
            String matkhau = String.copyValueOf(jPasswordField1.getPassword());
            if (matkhau.isEmpty()) throw new Exception("Vui lòng nhập mật khẩu");
            Client.closeAllViews();
            Client.openView(Client.View.GAMENOTICE, "Đăng nhập", "Đang xác thực thông tin đăng nhập");
            Client.socketHandle.write("client-verify," + taikhoan + "," + matkhau);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }

    // Variables declaration
    private javax.swing.JButton LoginButton;
    private javax.swing.JButton RegisterButton;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel pwdLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel titlePanel;
    private javax.swing.JLabel unLabel;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration
}