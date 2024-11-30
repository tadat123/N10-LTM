package View;

import Controller.Client;
import Model.User;

import javax.swing.ImageIcon;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class TurtorialFrm extends javax.swing.JFrame {

    public TurtorialFrm() {
        initComponents();

        this.setTitle("Picking Paddy and Rice");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("assets/pdicon.png").getImage());

        // Ensure Client.user is not null before accessing its properties
        if (Client.user != null) {
            jLabel4.setText(Client.user.getNickname());
            jLabel6.setText(String.valueOf(Client.user.getNOG()));
            jLabel8.setText(String.valueOf(Client.user.getNOW()));
        } else {
            jLabel4.setText("Unknown");
            jLabel6.setText("0");
            jLabel8.setText("0");
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(240, 240, 240));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // Nickname label
        jLabel3.setText("Nickname:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // User nickname
        jLabel4.setText("{abc}");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // Total games label
        jLabel5.setText("Total Games:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // Total games value
        jLabel6.setText("{def}");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // Total wins label
        jLabel7.setText("Total Wins:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // Total wins value
        jLabel8.setText("{xyz}");

        jButton1.setBackground(new java.awt.Color(220, 53, 69));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // Exit button
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Exit");
        jButton1.addActionListener(evt -> Client.closeView(Client.View.TURTORIAL));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap(50, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(110, 110, 110))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4))
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6))
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel8))
                                .addGap(30, 30, 30)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
}
