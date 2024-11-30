package View;

import Controller.Client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class MainFrm extends javax.swing.JFrame {

    public MainFrm() {
        initComponents();
        this.setTitle("Picking Paddy and Rice");
        this.setIconImage(new ImageIcon("assets/pdicon.png").getImage());
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        jTextArea1.setEditable(false);
        initEventListeners();
    }

    private void initEventListeners() {
        StartButton.addActionListener(this::StartButtonActionPerformed);
        TurtorialButton.addActionListener(this::TurtorialButtonActionPerformed);
        DashboardButton.addActionListener(this::DashboardButtonActionPerformed);
        SettingButton.addActionListener(this::SettingButtonActionPerformed);
        SignOutButton.addActionListener(this::SignOutButtonActionPerformed);
        jButton1.addActionListener(this::jButton1ActionPerformed);
        jButton3.addActionListener(this::jButton3ActionPerformed);
    }

    private void initComponents() {
        // Header Panel
        jPanel1 = new JPanel();
        jPanel1.setBackground(new Color(0, 102, 153));
        jLabel1 = new JLabel("TẤM NHẶT THÓC", SwingConstants.CENTER);
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setForeground(Color.WHITE);

        // Chat Panel
        jTextArea1 = new JTextArea(8, 20); // Giảm số dòng hiển thị
        jTextArea1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTextArea1.setLineWrap(true);
        jTextArea1.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(jTextArea1);

        jTextField1 = new JTextField();
        jTextField1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jTextField1.setMargin(new Insets(5, 5, 5, 5));

        jButton1 = new JButton("Gửi");
        jButton1.setBackground(new Color(0, 153, 51));
        jButton1.setForeground(Color.WHITE);
        jButton1.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Function Buttons
        StartButton = createButton("Chơi cá nhân", new Color(51, 153, 255));
        SettingButton = createButton("Chơi 2 người", new Color(51, 153, 255));
        DashboardButton = createButton("Bảng xếp hạng", new Color(102, 102, 255));
        TurtorialButton = createButton("Thông tin", new Color(102, 102, 255));
        SignOutButton = createButton("Đăng xuất", new Color(255, 51, 51));
        jButton3 = createButton("Lịch sử đấu", new Color(255, 204, 0));

        // Layout Setup
        setLayout(new BorderLayout());
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(jLabel1, BorderLayout.CENTER);

        // Chat Section
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        inputPanel.add(jTextField1, BorderLayout.CENTER);
        inputPanel.add(jButton1, BorderLayout.EAST);
        centerPanel.add(inputPanel, BorderLayout.SOUTH);

        // Button Section
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 15, 15)); // Các nút lớn hơn và khoảng cách rộng hơn
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        buttonPanel.add(StartButton);
        buttonPanel.add(SettingButton);
        buttonPanel.add(DashboardButton);
        buttonPanel.add(TurtorialButton);
        buttonPanel.add(jButton3);
        buttonPanel.add(SignOutButton);

        add(jPanel1, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(600, 700); // Tăng kích thước cửa sổ
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 50)); // Nút lớn hơn
        return button;
    }

    private void StartButtonActionPerformed(ActionEvent evt) {
        Client.openView(Client.View.GAMEPLAY);
        Client.closeView(Client.View.MAIN);
    }

    private void TurtorialButtonActionPerformed(ActionEvent evt) {
        Client.openView(Client.View.TURTORIAL);
    }

    private void DashboardButtonActionPerformed(ActionEvent evt) {
        Client.openView(Client.View.DASHBOARD);
    }

    private void SettingButtonActionPerformed(ActionEvent evt) {
        Client.closeView(Client.View.MAIN);
        Client.openView(Client.View.FINDROOM);
    }

    private void SignOutButtonActionPerformed(ActionEvent evt) {
        try {
            Client.socketHandle.write("offline," + Client.user.getID());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
        Client.closeView(Client.View.MAIN);
        Client.openView(Client.View.LOGIN);
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        sendMessage();
    }

    private void jButton3ActionPerformed(ActionEvent evt) {
        Client.openView(Client.View.HISTORY);
    }

    private void sendMessage() {
        try {
            if (jTextField1.getText().isEmpty())
                throw new Exception("Vui lòng nhập nội dung");
            String temp = jTextArea1.getText();
            temp += "Tôi: " + jTextField1.getText() + "\n";
            jTextField1.setText("");
            jTextArea1.setText(temp);
            Client.socketHandle.write("chat-server," + jTextField1.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }

    public void addMessage(String message) {
        String temp = jTextArea1.getText();
        temp += message + "\n";
        jTextArea1.setText(temp);
    }

    private JButton DashboardButton;
    private JButton SettingButton;
    private JButton SignOutButton;
    private JButton StartButton;
    private JButton TurtorialButton;
    private JButton jButton1;
    private JButton jButton3;
    private JLabel jLabel1;
    private JPanel jPanel1;
    private JTextArea jTextArea1;
    private JTextField jTextField1;
}
