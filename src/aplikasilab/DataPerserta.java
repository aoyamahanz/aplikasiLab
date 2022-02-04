/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasilab;

import java.sql.Connection;
import java.sql.SQLException;
import koneksi.Koneksi;
import entity.DataPeminjam;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;

/**
 *
 * @author Herman Patria
 */
public class DataPerserta extends javax.swing.JFrame {
    private Connection conn = new koneksi.Koneksi().getConnect();
    private DefaultTableModel table_data;
    private DataPeminjam peminjam;
    private String SQL;
    /**
     * Creates new form DataPerserta
     */
    
    public DataPerserta() {
        initComponents();
        id();
        this.table();
    }
    
    private void id(){
        txtId.setEnabled(false);
    }
    
    private void clear(){
        txtId.setText("");
        txtNisNik.setText("");
        txtNama.setText("");
        btnJenisKelamin.clearSelection();
        txtNo.setText("");
        cbRole.setSelectedIndex(0); 
    }
    
    private void table(){
       
        table_data = new DefaultTableModel();
        table_data.addColumn("ID");
        table_data.addColumn("Nis / Nik");
        table_data.addColumn("Nama");
        table_data.addColumn("Jenis Kelamin");
        table_data.addColumn("No Telepon");
        table_data.addColumn("Role");
        jTable1.setModel(table_data);
        try{
            java.sql.Statement stmt = conn.createStatement();
            SQL = "select * from data_peminjam";
            java.sql.ResultSet rs = stmt.executeQuery(SQL);
            while(rs.next()){
                table_data.addRow(new Object[]{
                   rs.getString("id"),
                   rs.getString("nis_nik"),
                   rs.getString("nama"),
                   rs.getString("jenis_kelamin"),
                   rs.getString("no_tlp"),
                   rs.getString("role") 
                });
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void savePeminjam(){
        try{
            int a = (int) (10000*Math.random());
            String id = "121"+String.valueOf(a);
            String jkel;
            if(rbLaki.isSelected()){
                jkel="laki-laki";
            }else{
                jkel="perempuan";
            }
            peminjam = new DataPeminjam();
            peminjam.setId(id);
            peminjam.setNisNik(txtNisNik.getText());
            peminjam.setNama(txtNama.getText());
            peminjam.setJenisKelamin(jkel);
            peminjam.setNoTlp(txtNo.getText());
            peminjam.setRole((String) cbRole.getSelectedItem());
            
            String sqll = "insert into data_peminjam(id, nis_nik, nama, jenis_kelamin, no_tlp, role) values (?,?,?,?,?,?)";
            PreparedStatement statement1 = conn.prepareStatement(sqll);
            statement1.setString(1, peminjam.getId());
            statement1.setString(2, peminjam.getNisNik());
            statement1.setString(3, peminjam.getNama());
            statement1.setString(4, peminjam.getJenisKelamin());
            statement1.setString(5, peminjam.getNoTlp());
            statement1.setString(6, peminjam.getRole());
            statement1.executeUpdate();
            
            
            JOptionPane.showMessageDialog(null, "Pendaftaran Berhasil");
            clear();
            table();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "pendafataran gagal \n"+e);
        }
    }
    
    private void updatePeminjam(){
        try{
            String jkel;
            if(rbLaki.isSelected()){
                jkel="laki-laki";
            }else{
                jkel="perempuan";
            }
            peminjam = new DataPeminjam();
            peminjam.setId(txtId.getText());
            peminjam.setNisNik(txtNisNik.getText());
            peminjam.setNama(txtNama.getText());
            peminjam.setJenisKelamin(jkel);
            peminjam.setNoTlp(txtNo.getText());
            peminjam.setRole(cbRole.getSelectedItem().toString());
            
            String sql3 = "update data_peminjam set nis_nik = ? , nama = ? , jenis_kelamin = ? , no_tlp = ? , role = ? where id = ?";
            PreparedStatement statement2 = conn.prepareStatement(sql3);
            
            statement2.setString(1, peminjam.getNisNik());
            statement2.setString(2, peminjam.getNama());
            statement2.setString(3, peminjam.getJenisKelamin());
            statement2.setString(4, peminjam.getNoTlp());
            statement2.setString(5, peminjam.getRole());
            statement2.setString(6, peminjam.getId());
            statement2.executeUpdate();
            
            
            JOptionPane.showMessageDialog(null, "Update Data Peminjam Berhasil");
            clear();
            table();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Update gagal \n"+e);
        }
    }
    
    private void deletePeminjam(){
        try{
            peminjam = new DataPeminjam();
            peminjam.setId(txtId.getText());
            
            String sql3 = "delete from data_peminjam where id = ?";
            PreparedStatement statement3 = conn.prepareStatement(sql3);
            statement3.setString(1, peminjam.getId());
            statement3.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Data berhasil di hapus");
            clear();
            table();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Data gagal dihapus \n"+e);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnJenisKelamin = new javax.swing.ButtonGroup();
        aplikasiLabPUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("aplikasiLabPU").createEntityManager();
        dataPeminjamQuery = java.beans.Beans.isDesignTime() ? null : aplikasiLabPUEntityManager.createQuery("SELECT d FROM DataPeminjam d");
        dataPeminjamList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : dataPeminjamQuery.getResultList();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        txtNo = new javax.swing.JTextField();
        txtNisNik = new javax.swing.JTextField();
        rbLaki = new javax.swing.JRadioButton();
        rbPerempuan = new javax.swing.JRadioButton();
        cbRole = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnUpdate = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Data Perserta");

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("ID");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Nis / Nik");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Nama");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("No. Telepon");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Jenis Kelamin");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Role");

        btnJenisKelamin.add(rbLaki);
        rbLaki.setText("Laki - Laki");

        btnJenisKelamin.add(rbPerempuan);
        rbPerempuan.setText("Perempuan");

        cbRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Guru", "Murid" }));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nis / Nik", "Nama", "Jenis Kelamin", "No. Telepon", "Role"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        btnUpdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnBack.setText("Back To Home");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnSave)
                                .addGap(18, 18, 18)
                                .addComponent(btnUpdate)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addGap(101, 101, 101)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNisNik, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(rbLaki)
                                        .addGap(18, 18, 18)
                                        .addComponent(rbPerempuan)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addComponent(btnBack)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNisNik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(rbLaki)
                    .addComponent(rbPerempuan))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(btnSave)
                    .addComponent(btnDelete))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBack)
                .addGap(52, 52, 52))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(223, 223, 223))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        if(txtNisNik.getText().equals("") || txtNama.getText().equals("") || (rbLaki.isSelected()==false && rbPerempuan.isSelected()==false) || txtNo.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Harap isi semua filed");
        }else{
            savePeminjam();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        String jkel;
        String role;
        int baris = jTable1.getSelectedRow();
        txtId.setText(table_data.getValueAt(baris, 0).toString());
        txtNisNik.setText(table_data.getValueAt(baris, 1).toString());
        txtNama.setText(table_data.getValueAt(baris, 2).toString());
        jkel = (String)table_data.getValueAt(baris,3).toString();
        if(jkel.equals("laki-laki")){
            rbLaki.setSelected(true);
        }else{
            rbPerempuan.setSelected(true);
        }
        txtNo.setText(table_data.getValueAt(baris, 4).toString());
        role = (String)table_data.getValueAt(baris, 5).toString();
        if(role.equals("Guru")){
            cbRole.setSelectedIndex(0);
        }else{
            cbRole.setSelectedIndex(1);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        updatePeminjam();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        deletePeminjam();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        Home home = new Home();
        home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DataPerserta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataPerserta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataPerserta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataPerserta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DataPerserta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.persistence.EntityManager aplikasiLabPUEntityManager;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDelete;
    private javax.swing.ButtonGroup btnJenisKelamin;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbRole;
    private java.util.List<entity.DataPeminjam> dataPeminjamList;
    private javax.persistence.Query dataPeminjamQuery;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JRadioButton rbLaki;
    private javax.swing.JRadioButton rbPerempuan;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNisNik;
    private javax.swing.JTextField txtNo;
    // End of variables declaration//GEN-END:variables
}
