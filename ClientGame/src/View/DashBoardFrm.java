/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Controller.Client;
import Model.User;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author tadat
 */
public class DashBoardFrm extends javax.swing.JFrame {
    private DefaultTableModel tableModel;
    private List<User> listUserStatics;
    private List<String> rankSrc;
    /**
     * Creates new form RankFrm
     */
    public DashBoardFrm() {
        initComponents();
        this.setTitle("Picking Paddy and Rice");
        tableModel = (DefaultTableModel) jTable1.getModel();
        this.setIconImage(new ImageIcon("assets/image/pdicon.png").getImage());
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Thêm code để làm đẹp giao diện
        // Đặt màu nền cho JFrame
        getContentPane().setBackground(new Color(230, 240, 250));

        // Tùy chỉnh JTable
        jTable1.setBackground(Color.WHITE);
        jTable1.setForeground(Color.BLACK);
        jTable1.setRowHeight(30);
        jTable1.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Tùy chỉnh header của JTable
        jTable1.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        jTable1.getTableHeader().setBackground(new Color(32, 136, 203));
        jTable1.getTableHeader().setForeground(Color.WHITE);

        // Tùy chỉnh các nút và label khác nếu có
        // Ví dụ:
        // myButton.setBackground(new Color(32, 136, 203));
        // myButton.setForeground(Color.WHITE);
        // myButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        rankSrc = new ArrayList<>();
        rankSrc.add("gold-rank");
        rankSrc.add("silver-rank");
        rankSrc.add("bronze-rank");
        for(int i=0; i<5; i++){
            rankSrc.add("normal-rank");
        }
        try {
            Client.socketHandle.write("get-rank-charts,");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
    }
    
    public void setDataToTable(List<User> users){
        this.listUserStatics = users;
        tableModel.setRowCount(0);
        int i=0;
        jTable1.setRowHeight(80); 
        for(User user : listUserStatics){
            ImageIcon originalIcon = new ImageIcon("assets/" + rankSrc.get(i) + ".png");
        // Thay đổi kích thước ảnh thành 30x30
            ImageIcon resizedIcon = new ImageIcon(originalIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));
            tableModel.addRow(new Object[]{
                i+1,
                user.getNickname(),
                resizedIcon
            });
            i++;
        }

    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Object[][] rows = { }; String[] columns = {"Hạng","Nickname","Rank"}; DefaultTableModel model = new DefaultTableModel(rows, columns){     @Override     public Class<?> getColumnClass(int column){         switch(column){             case 0: return String.class;             case 1: return String.class;             case 2: return ImageIcon.class;             default: return Object.class;         }     } };
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Bảng xếp hạng");

        jTable1.setModel(model);
        jTable1.setFillsViewportHeight(true);
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Thoát");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jButton1)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Client.closeView(Client.View.DASHBOARD); 
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
