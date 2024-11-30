package View;

import Controller.Client;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * Improved RegisterFrm UI
 */
public class RegisterFrm extends javax.swing.JFrame {

    public RegisterFrm() {
        initComponents();
        this.setTitle("Tấm Nhặt Thóc - Đăng Ký");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        jPasswordField2 = new JPasswordField();
        jLabel5 = new JLabel();
        RegisterButton1 = new JButton();
        jTextField1 = new JTextField();
        jTextField2 = new JTextField();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(450, 400)); // Tăng kích thước cửa sổ

        // Tiêu đề
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("ĐĂNG KÝ TÀI KHOẢN");
        jLabel1.setForeground(new Color(0, 102, 204));

        // Các nhãn và trường nhập liệu
        jLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        jLabel2.setText("Tên tài khoản:");

        jLabel3.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        jLabel3.setText("Mật khẩu:");

        jLabel4.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        jLabel4.setText("Biệt danh:");

        jTextField1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jPasswordField2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTextField2.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Nút Đăng ký
        RegisterButton1.setFont(new Font("Segoe UI", Font.BOLD, 16));
        RegisterButton1.setBackground(new Color(0, 153, 51));
        RegisterButton1.setForeground(Color.WHITE);
        RegisterButton1.setText("Đăng ký");
        RegisterButton1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        RegisterButton1.addActionListener(this::RegisterButton1ActionPerformed);

        // Liên kết Đăng nhập
        jLabel5.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        jLabel5.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel5.setForeground(new Color(255, 102, 0));
        jLabel5.setText("Đã có tài khoản? Nhấn vào đây để đăng nhập");
        jLabel5.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        // Bố cục giao diện
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(30)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPasswordField2, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)))
                                .addGap(30))
                        .addComponent(RegisterButton1, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(20)
                        .addComponent(jLabel1)
                        .addGap(30)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                        .addGap(20)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(jPasswordField2, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                        .addGap(20)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                        .addGap(30)
                        .addComponent(RegisterButton1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                        .addGap(20)
                        .addComponent(jLabel5)
                        .addGap(20)
        );

        pack();
    }

    private void RegisterButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String username = jTextField1.getText();
            if (username.isEmpty())
                throw new Exception("Vui lòng nhập tên tài khoản");
            String password = String.copyValueOf(jPasswordField2.getPassword());
            if (password.isEmpty())
                throw new Exception("Vui lòng nhập mật khẩu");
            String nickName = jTextField2.getText();
            if (nickName.isEmpty())
                throw new Exception("Vui lòng nhập biệt danh");
            Client.closeAllViews();
            Client.openView(Client.View.GAMENOTICE, "Đăng ký tài khoản", "Đang chờ phản hồi");
            Client.socketHandle.write("register," + username + "," + password + "," + nickName);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {
        Client.closeView(Client.View.REGISTER);
        Client.openView(Client.View.LOGIN);
    }

    // Main Functio

    // Biến
    private JButton RegisterButton1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JPasswordField jPasswordField2;
    private JTextField jTextField1;
    private JTextField jTextField2;
}
