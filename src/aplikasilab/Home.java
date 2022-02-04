/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasilab;
import com.toedter.calendar.JDateChooser;
import entity.DataPeminjam;
import koneksi.Koneksi;
import entity.DataPeminjaman;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import entity.DataPeminjam;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import entity.DataBarang;
import javax.swing.table.DefaultTableModel;
import entity.DataPengembalian;
import java.io.File;
import java.sql.Timestamp;
import java.util.HashMap;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Herman Patria
 */
public class Home extends javax.swing.JFrame {
    private DataPeminjaman pinjam;
    private Connection conn = new koneksi.Koneksi().getConnect();
    private DataPeminjam peminjam;
    private DataBarang barang;
    private DataPengembalian pengembalian;
    private DefaultTableModel tabmodepeminjaman;
    private DefaultTableModel table_data;
    private DefaultTableModel tabModePeminjaman;
    
    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();
        cbBrg();
        TabelModelPengembalian();
        tampilTablePeminjaman();
        batasSpinner();
        tableModelPeminjaman();
    }
    
    private void clear(){
        txtId.setText("");
        txtId.setText("Enter untuk melihat nama");
        txtNama.setText("");
        cbBrgPeminjaman.setSelectedIndex(0);
        txtDeskripsi.setText("");
        spJumlah.setValue(0);
    }
    
    private void clearPengembalian(){
        txtKdPeminjaman.setText("");
        tabmodepeminjaman.setRowCount(0);
    }
    
    //peminjaman
    
    private void keyPressId(){
            try{
                String sql="select nama from data_peminjam where id='"+Integer.parseInt(txtId.getText())+"'";
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery(sql);

                while(rs.next())
                {
                    txtNama.setText(rs.getString("nama"));
                }
            }catch(Exception e)
            {
                JOptionPane.showMessageDialog(null,"Tidak ada id tersebut");
            }        
        
    }
    
    private void batasSpinner(){
        int stok = 0;
        
        try{
            String sql="select stok from data_barang where nama_barang='"+cbBrgPeminjaman.getSelectedItem().toString()+"'";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            
            while(rs.next())
            {
                stok = rs.getInt(1);
            }
        }catch(Exception e){
            
        }
        
        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, stok, 1);
        spJumlah.setModel(model);
    }
    
    private void cbBrg(){
        String sql = "select nama_barang from data_barang";
        List<String> list = new ArrayList();
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                list.add(rs.getString(WIDTH));
            }
            cbBrgPeminjaman.setModel(new javax.swing.DefaultComboBoxModel<>(list.toArray(new String[0])));
        }catch(SQLException e){
            
        }
    }
    
    // addCartPeminjaman
    
    protected void tableModelPeminjaman(){
        Object[] Baris = {"Id", "Nama Barang", "Deskripsi", "Jumlah Unit"};
        tabModePeminjaman = new DefaultTableModel(null,Baris);
    }
    
    private void addCartPeminjaman(){
        peminjamanCartTable.setModel(tabModePeminjaman);
        int a = Integer.parseInt(txtId.getText());
        String b = cbBrgPeminjaman.getSelectedItem().toString();
        String c = txtDeskripsi.getText();
        int d = (int) spJumlah.getValue();
        Object[] data = {a,b,c,d};
        tabModePeminjaman.addRow(data);
    }
    
    private void deleteCartPeminjaman(){
        int row = peminjamanCartTable.getSelectedRow();
        tabModePeminjaman.removeRow(row);
    }
    
    private void processPinjam(){
        String SqlDataPeminjaman = "insert into data_peminjaman(tgl_peminjaman) values (current_timestamp())";
        try{
            PreparedStatement statement = conn.prepareStatement(SqlDataPeminjaman);
            statement.executeUpdate();
            int kd_peminjaman = 0;
            JOptionPane.showMessageDialog(null, "Peminjaman Berhasil");
            try{
                
                String SqlPeminjaman = "select max(kd_peminjaman) From data_peminjaman";
                Statement stm1 = conn.createStatement();
                ResultSet rs1 = stm1.executeQuery(SqlPeminjaman);
                while (rs1.next()){
                    kd_peminjaman = rs1.getInt(1);
                }
            }catch(SQLException e1){
                JOptionPane.showMessageDialog(null, e1);
            }
            
            try{
                int rowcount = peminjamanCartTable.getRowCount();
                String SqlDetailPeminjaman = "insert into detail_peminjaman values (?, ?, ?, ?, ?, ?)";
                for(int x=0; x<rowcount; x++){
                    int a = kd_peminjaman;
                    int b = (int) peminjamanCartTable.getValueAt(x, 0);
                    String c = peminjamanCartTable.getValueAt(x, 1).toString();
                    String d = peminjamanCartTable.getValueAt(x, 2).toString();
                    int e = (int) peminjamanCartTable.getValueAt(x, 3);
                    String f = "belum kembali";
                    
                    PreparedStatement stm2 = conn.prepareStatement(SqlDetailPeminjaman);
                    stm2.setInt(1, a);
                    stm2.setInt(2, b);
                    stm2.setString(3, c);
                    stm2.setInt(4, e);
                    stm2.setString(5, d);
                    stm2.setString(6, f);
                    stm2.executeUpdate();
                    
                    int stok = 0;
                    String SqlBarang1 = "select stok from data_barang where nama_barang='"+c+"'";
                    Statement stm3 = conn.createStatement();
                    ResultSet rs3 = stm3.executeQuery(SqlBarang1);
                    while(rs3.next()){
                        stok = rs3.getInt(1);
                    }
                    stok = stok - e;
                    
                    String sqlBarang2 = "update data_barang set stok='"+stok+"' where nama_barang='"+c+"'";
                    PreparedStatement stm4 = conn.prepareStatement(sqlBarang2);
                    stm4.executeUpdate();
                    
                }
            }catch(SQLException e2){
                JOptionPane.showMessageDialog(null, e2);
            }
            peminjamanCartDialog.setVisible(false);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // pengembalian

    protected void TabelModelPengembalian(){
        Object[] Baris = {"Kd peminjaman", "Tanggal Pinjam", "Id", "Nama barang", "Unit pinjam", "Deskripsi peminjaman", "status"};
        tabmodepeminjaman = new DefaultTableModel(null,Baris);
    }
    
    private void tampilTablePeminjaman(){
        int a=0, c=0, e=0;
        Timestamp b=null;
        String d=null, f=null, g=null;
        
        try{
            Statement stm = conn.createStatement();
            String sql = "select detail_peminjaman.kd_peminjaman, data_peminjaman.tgl_peminjaman, detail_peminjaman.id, detail_peminjaman.nama_barang, detail_peminjaman.stok, detail_peminjaman.deskripsi_peminjaman, detail_peminjaman.status from detail_peminjaman inner join data_peminjaman on detail_peminjaman.kd_peminjaman=data_peminjaman.kd_peminjaman where status='belum kembali'";
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()){
                a = rs.getInt(1);
                b = rs.getTimestamp(2);
                c = rs.getInt(3);
                d = rs.getString(4);
                e = rs.getInt(5);
                f = rs.getString(6);
                g = rs.getString(7);
                tablePeminjaman.setModel(tabmodepeminjaman);
                Object[]data = {a,b,c,d,e,f,g};
                tabmodepeminjaman.addRow(data);
            }
        }catch(SQLException e2){
        JOptionPane.showMessageDialog(null, e2);
    }
    }
    
    private void cariPeminjam(){
        int a=0, c=0, e=0;
        Timestamp b=null;
        String d=null, f=null, g=null;
        
        try{
            Statement stm = conn.createStatement();
            String sql = "select detail_peminjaman.kd_peminjaman, data_peminjaman.tgl_peminjaman, detail_peminjaman.id, detail_peminjaman.nama_barang, detail_peminjaman.stok, detail_peminjaman.deskripsi_peminjaman, detail_peminjaman.status from detail_peminjaman inner join data_peminjaman on detail_peminjaman.kd_peminjaman=data_peminjaman.kd_peminjaman where detail_peminjaman.kd_peminjaman='"+txtKdPeminjaman.getText()+"' and status=0";
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()){
                a = rs.getInt(1);
                b = rs.getTimestamp(2);
                c = rs.getInt(3);
                d = rs.getString(4);
                e = rs.getInt(5);
                f = rs.getString(6);
                g = rs.getString(7);
                tablePeminjaman.setModel(tabmodepeminjaman);
                Object[]data = {a,b,c,d,e,f,g};
                tabmodepeminjaman.addRow(data);
            }
        }catch(SQLException e1){
            JOptionPane.showMessageDialog(null, e1);
        } 
    }
    
    private void prosesPengembalian(){
        try{
            //insert data_pengembalian
            int row = tablePeminjaman.getSelectedRow();
            
            String sqlsave1 = "insert into data_pengembalian(kd_peminjaman) values (?)";
            PreparedStatement stm1 = conn.prepareStatement(sqlsave1);
            stm1.setInt(1, Integer.parseInt(tablePeminjaman.getValueAt(row, 0).toString()));
            stm1.executeUpdate();
            
            //selected
            String kd_pengembalian = "sudah kembali";
            String sqlSelect = "select max(kd_pengembalian) from data_pengembalian";
            Statement select = conn.createStatement();
            ResultSet rs1 = select.executeQuery(sqlSelect);
            while(rs1.next()){
                kd_pengembalian=rs1.getString(1);
            }
            //insert detail_pengembalian
            
            String sqlsave2 = "insert into detail_pengembalian(kd_pengembalian, tgl_peminjaman, id, nama_barang, stok) values (?,?,?,?,?)";
            PreparedStatement stm2 = conn.prepareStatement(sqlsave2);
            stm2.setString(1, kd_pengembalian);
            stm2.setTimestamp(2, (Timestamp) tablePeminjaman.getValueAt(row, 1));
            stm2.setInt(3, (int) tablePeminjaman.getValueAt(row, 2));
            stm2.setString(4, tablePeminjaman.getValueAt(row, 3).toString());
            stm2.setInt(5, (int) tablePeminjaman.getValueAt(row, 4));
            stm2.executeUpdate();
            
            //update status di data_peminjaman
            
            String sts = "sudah kembali";
            String sql1 = "update detail_peminjaman set status=? where kd_peminjaman=? and nama_barang=?";
            
                PreparedStatement statement = conn.prepareStatement(sql1);
                statement.setString(1, sts);
                statement.setInt(2, Integer.parseInt(tablePeminjaman.getValueAt(row, 0).toString()));
                statement.setString(3, tablePeminjaman.getValueAt(row, 3).toString());
                statement.executeUpdate();
            
            
            //update barang yang dikembalikan
            
            int unitPinjam = Integer.parseInt(tablePeminjaman.getValueAt(row, 4).toString());
            int unitAda = 0;
            
            //ambil data stok barang di data_barang
            String sql2 = "select stok from data_barang where nama_barang='"+tablePeminjaman.getValueAt(row, 3).toString()+"'";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql2);
            while(rs.next()){
               unitAda = rs.getInt(1);
            }
            
            int total = unitAda + unitPinjam;
            
            //update stok data_barang
            String sql3 = "update data_barang set stok=? where nama_barang='"+tablePeminjaman.getValueAt(row, 3).toString()+"'";
            
                PreparedStatement statement1 = conn.prepareStatement(sql3);
                statement1.setInt(1, total);
                statement1.executeUpdate();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void previewReport(String filename, HashMap parameter, String jenis){
        try{
            File fileReport = new File ("src\\report\\"+filename);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(fileReport);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, conn);
            
            JasperViewer.viewReport(jasperPrint, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
//            JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\"+jenis+".pdf");
//            JOptionPane.showMessageDialog(null, "Laporan sudah disimpan pada direktori C:\\"+jenis+".pdf");
        }catch(JRException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void previewReportMaster(String filename, String jenis){
        try{
            File fileReport = new File ("src\\report\\"+filename);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(fileReport);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
            
            JasperViewer.viewReport(jasperPrint, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
//            JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\"+jenis+".pdf");
//            JOptionPane.showMessageDialog(null, "Laporan sudah disimpan pada direktori C:\\"+jenis+".pdf");
        }catch(JRException e){
            JOptionPane.showMessageDialog(null, e);
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

        peminjamanCartDialog = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        peminjamanCartTable = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        pengembalianCartDialog = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        btnDashboard = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        btnPeminjaman = new javax.swing.JButton();
        btnPengembalian = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        btnBarang = new javax.swing.JButton();
        btnPeserta = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        btnKeluar = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        btnDataTransaksi = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        jpDasboard = new javax.swing.JPanel();
        jpPeminjaman = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtKdPinjam = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        cartBtn = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        cbBrgPeminjaman = new javax.swing.JComboBox<>();
        spJumlah = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDeskripsi = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        clear = new javax.swing.JButton();
        addCartBtn = new javax.swing.JButton();
        jpPengembalian = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtKdPeminjaman = new javax.swing.JTextField();
        btnCariKdPemijaman_Pengembalian = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablePeminjaman = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jpLaporan = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        cbLaporan_Transaksi = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jdFrom = new com.toedter.calendar.JDateChooser();
        jLabel18 = new javax.swing.JLabel();
        jdTo = new com.toedter.calendar.JDateChooser();
        jSeparator9 = new javax.swing.JSeparator();
        jButton4 = new javax.swing.JButton();

        peminjamanCartDialog.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        peminjamanCartDialog.setSize(new java.awt.Dimension(600, 250));

        jPanel1.setBackground(new java.awt.Color(0, 51, 153));

        peminjamanCartTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nama Barang", "Deskripsi", "Jumlah Unit"
            }
        ));
        jScrollPane3.setViewportView(peminjamanCartTable);

        jButton6.setText("Delete From Cart");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Process");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addGap(18, 18, 18)
                        .addComponent(jButton7)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton7))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout peminjamanCartDialogLayout = new javax.swing.GroupLayout(peminjamanCartDialog.getContentPane());
        peminjamanCartDialog.getContentPane().setLayout(peminjamanCartDialogLayout);
        peminjamanCartDialogLayout.setHorizontalGroup(
            peminjamanCartDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        peminjamanCartDialogLayout.setVerticalGroup(
            peminjamanCartDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pengembalianCartDialogLayout = new javax.swing.GroupLayout(pengembalianCartDialog.getContentPane());
        pengembalianCartDialog.getContentPane().setLayout(pengembalianCartDialogLayout);
        pengembalianCartDialogLayout.setHorizontalGroup(
            pengembalianCartDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        pengembalianCartDialogLayout.setVerticalGroup(
            pengembalianCartDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(0, 51, 153));

        btnDashboard.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDashboard.setText("Dashboard");
        btnDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashboardActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Transaksi");

        btnPeminjaman.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnPeminjaman.setText("Peminjaman");
        btnPeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPeminjamanActionPerformed(evt);
            }
        });

        btnPengembalian.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnPengembalian.setText("Pengembalian");
        btnPengembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPengembalianActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Data Master");

        btnBarang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnBarang.setText("Data barang");
        btnBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarangActionPerformed(evt);
            }
        });

        btnPeserta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnPeserta.setText("Data Peserta");
        btnPeserta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesertaActionPerformed(evt);
            }
        });

        btnKeluar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnKeluar.setText("Keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Data Laporan");

        btnDataTransaksi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDataTransaksi.setText("Laporan");
        btnDataTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataTransaksiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPeminjaman, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator1)
                    .addComponent(btnPengembalian, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                    .addComponent(jSeparator3)
                    .addComponent(btnBarang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPeserta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator4)
                    .addComponent(btnKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDataTransaksi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator5)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel12))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDashboard)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(btnPeminjaman)
                .addGap(18, 18, 18)
                .addComponent(btnPengembalian)
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(btnPeserta)
                .addGap(18, 18, 18)
                .addComponent(btnBarang)
                .addGap(18, 18, 18)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(btnDataTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnKeluar)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jPanel4.setLayout(new java.awt.CardLayout());

        jpDasboard.setBackground(new java.awt.Color(0, 51, 153));

        javax.swing.GroupLayout jpDasboardLayout = new javax.swing.GroupLayout(jpDasboard);
        jpDasboard.setLayout(jpDasboardLayout);
        jpDasboardLayout.setHorizontalGroup(
            jpDasboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 737, Short.MAX_VALUE)
        );
        jpDasboardLayout.setVerticalGroup(
            jpDasboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 723, Short.MAX_VALUE)
        );

        jPanel4.add(jpDasboard, "card4");

        jpPeminjaman.setBackground(new java.awt.Color(0, 51, 153));

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Peminjaman");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Kode Peminjaman");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("ID Peminjam");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Nama Peminjam");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Nama Barang");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Jumlah Unit");

        txtKdPinjam.setEditable(false);

        txtId.setText("Enter untuk melihat nama");
        txtId.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtIdMouseClicked(evt);
            }
        });
        txtId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIdKeyPressed(evt);
            }
        });

        txtNama.setEditable(false);

        cartBtn.setText("Cart");
        cartBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cartBtnActionPerformed(evt);
            }
        });

        cbBrgPeminjaman.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbBrgPeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBrgPeminjamanActionPerformed(evt);
            }
        });

        spJumlah.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        txtDeskripsi.setColumns(20);
        txtDeskripsi.setRows(5);
        jScrollPane1.setViewportView(txtDeskripsi);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Deskripsi Peminjaman");

        clear.setText("Clear");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });

        addCartBtn.setText("Add Cart");
        addCartBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCartBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpPeminjamanLayout = new javax.swing.GroupLayout(jpPeminjaman);
        jpPeminjaman.setLayout(jpPeminjamanLayout);
        jpPeminjamanLayout.setHorizontalGroup(
            jpPeminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPeminjamanLayout.createSequentialGroup()
                .addGroup(jpPeminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpPeminjamanLayout.createSequentialGroup()
                        .addGap(244, 244, 244)
                        .addComponent(addCartBtn)
                        .addGap(18, 18, 18)
                        .addComponent(cartBtn)
                        .addGap(18, 18, 18)
                        .addComponent(clear)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jpPeminjamanLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpPeminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator6, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(jpPeminjamanLayout.createSequentialGroup()
                .addGap(283, 283, 283)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jpPeminjamanLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jpPeminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpPeminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpPeminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtKdPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbBrgPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(77, 77, 77))
        );
        jpPeminjamanLayout.setVerticalGroup(
            jpPeminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPeminjamanLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jpPeminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpPeminjamanLayout.createSequentialGroup()
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(jLabel4))
                    .addComponent(txtKdPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpPeminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpPeminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpPeminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbBrgPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpPeminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpPeminjamanLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jpPeminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(clear)
                            .addComponent(addCartBtn)
                            .addComponent(cartBtn))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jpPeminjamanLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jpPeminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addContainerGap(281, Short.MAX_VALUE))))
        );

        jPanel4.add(jpPeminjaman, "card2");

        jpPengembalian.setBackground(new java.awt.Color(0, 51, 153));

        jLabel13.setBackground(new java.awt.Color(0, 0, 0));
        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Pengembalian");

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Kode Peminjaman");

        txtKdPeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKdPeminjamanActionPerformed(evt);
            }
        });
        txtKdPeminjaman.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKdPeminjamanKeyPressed(evt);
            }
        });

        btnCariKdPemijaman_Pengembalian.setText("Cari");
        btnCariKdPemijaman_Pengembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariKdPemijaman_PengembalianActionPerformed(evt);
            }
        });

        tablePeminjaman.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "kd peminjama", "Tanggal pinjam", "Id", "Nama barang", "Unit pinjam", "Deskripsi", "Status"
            }
        ));
        tablePeminjaman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePeminjamanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablePeminjaman);

        jButton2.setText("Proses");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Refresh");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpPengembalianLayout = new javax.swing.GroupLayout(jpPengembalian);
        jpPengembalian.setLayout(jpPengembalianLayout);
        jpPengembalianLayout.setHorizontalGroup(
            jpPengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPengembalianLayout.createSequentialGroup()
                .addGroup(jpPengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpPengembalianLayout.createSequentialGroup()
                        .addGap(286, 286, 286)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3))
                    .addGroup(jpPengembalianLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jpPengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpPengembalianLayout.createSequentialGroup()
                                .addGroup(jpPengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtKdPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15))
                                .addGap(42, 42, 42)
                                .addComponent(btnCariKdPemijaman_Pengembalian))))
                    .addGroup(jpPengembalianLayout.createSequentialGroup()
                        .addGap(282, 282, 282)
                        .addComponent(jLabel13)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jpPengembalianLayout.setVerticalGroup(
            jpPengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPengembalianLayout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(jLabel13)
                .addGap(31, 31, 31)
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addGroup(jpPengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKdPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCariKdPemijaman_Pengembalian))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(jpPengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(289, 289, 289))
        );

        jPanel4.add(jpPengembalian, "card3");

        jpLaporan.setBackground(new java.awt.Color(0, 51, 153));

        jLabel14.setBackground(new java.awt.Color(0, 0, 0));
        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Laporan Transaksi");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Jenis Laporan");

        cbLaporan_Transaksi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih", "Peminjaman", "Pengembalian", "Peserta", "Barang" }));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Periode");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("s/d");

        jButton4.setText("Preview");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpLaporanLayout = new javax.swing.GroupLayout(jpLaporan);
        jpLaporan.setLayout(jpLaporanLayout);
        jpLaporanLayout.setHorizontalGroup(
            jpLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpLaporanLayout.createSequentialGroup()
                .addGroup(jpLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpLaporanLayout.createSequentialGroup()
                        .addGroup(jpLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpLaporanLayout.createSequentialGroup()
                                .addGap(110, 110, 110)
                                .addGroup(jpLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel16))
                                .addGap(251, 251, 251)
                                .addGroup(jpLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpLaporanLayout.createSequentialGroup()
                                        .addComponent(jdFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jdTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cbLaporan_Transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jpLaporanLayout.createSequentialGroup()
                                .addGap(326, 326, 326)
                                .addComponent(jButton4)))
                        .addGap(0, 52, Short.MAX_VALUE))
                    .addGroup(jpLaporanLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator8)
                            .addComponent(jSeparator9))))
                .addContainerGap())
            .addGroup(jpLaporanLayout.createSequentialGroup()
                .addGap(261, 261, 261)
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpLaporanLayout.setVerticalGroup(
            jpLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpLaporanLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jpLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(cbLaporan_Transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jdFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jdTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addContainerGap(421, Short.MAX_VALUE))
        );

        jPanel4.add(jpLaporan, "card5");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardActionPerformed
        // TODO add your handling code here:
        jpPeminjaman.setVisible(false);
        jpPengembalian.setVisible(false);
        jpDasboard.setVisible(true);
        jpLaporan.setVisible(false);
    }//GEN-LAST:event_btnDashboardActionPerformed

    private void btnPesertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesertaActionPerformed
        // TODO add your handling code here:
        DataPerserta perserta = new DataPerserta();
        perserta.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnPesertaActionPerformed

    private void btnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangActionPerformed
        // TODO add your handling code here:
        DataBarang1 barang1 = new DataBarang1();
        barang1.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBarangActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        // TODO add your handling code here:
        int jawab = JOptionPane.showConfirmDialog(null, "Apa anda ingin keluar ?");
        if(jawab == JOptionPane.YES_OPTION){
            this.dispose();
        }
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void cartBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cartBtnActionPerformed
        // TODO add your handling code here:
        peminjamanCartDialog.setVisible(true);
//        int unit = (int) spJumlah.getValue();
//        if(unit!=0){
//            pinjam();
//        }else{
//            JOptionPane.showMessageDialog(null, "Jumlah unit tidak boleh 0");
//        }
    }//GEN-LAST:event_cartBtnActionPerformed

    private void btnPeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeminjamanActionPerformed
        // TODO add your handling code here:
        jpDasboard.setVisible(false);
        jpPengembalian.setVisible(false);
        jpPeminjaman.setVisible(true);
        jpLaporan.setVisible(false);
        String txtid = txtId.getText();
        if(txtid.equals("")){
            txtId.setText("Enter untuk melihat nama");
        }
    }//GEN-LAST:event_btnPeminjamanActionPerformed

    private void txtIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            keyPressId();
        }
    }//GEN-LAST:event_txtIdKeyPressed

    private void cbBrgPeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBrgPeminjamanActionPerformed
        // TODO add your handling code here:
        batasSpinner();
    }//GEN-LAST:event_cbBrgPeminjamanActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_clearActionPerformed

    private void btnPengembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPengembalianActionPerformed
        // TODO add your handling code here:
        jpDasboard.setVisible(false);
        jpPengembalian.setVisible(true);
        jpPeminjaman.setVisible(false);
        jpLaporan.setVisible(false);
        TabelModelPengembalian();
        tampilTablePeminjaman();
    }//GEN-LAST:event_btnPengembalianActionPerformed

    private void btnCariKdPemijaman_PengembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariKdPemijaman_PengembalianActionPerformed
        // TODO add your handling code here:
//        clearPengembalian();
//        TabelModelPengembalian();
//        tampilTablePeminjaman();
//        cariPeminjam();     
        TabelModelPengembalian();
        cariPeminjam();
    }//GEN-LAST:event_btnCariKdPemijaman_PengembalianActionPerformed

    private void txtKdPeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKdPeminjamanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKdPeminjamanActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int jawab = JOptionPane.showConfirmDialog(null, "Apa barang yang dipinjam sudah dikembalikan ?", "Update data peminjaman", 0);
        if(jawab == JOptionPane.YES_OPTION){
            prosesPengembalian();
        }
        clearPengembalian();
        TabelModelPengembalian();
        tampilTablePeminjaman();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tablePeminjamanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePeminjamanMouseClicked
        // TODO add your handling code here:
        int row = tablePeminjaman.getSelectedRow();
    }//GEN-LAST:event_tablePeminjamanMouseClicked

    private void txtKdPeminjamanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKdPeminjamanKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
        TabelModelPengembalian();
        cariPeminjam();
        }
    }//GEN-LAST:event_txtKdPeminjamanKeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        clearPengembalian();
        TabelModelPengembalian();
        tampilTablePeminjaman();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        HashMap parameter = new HashMap();
        boolean isError = false;
        
        if(!isError){
            String LP = cbLaporan_Transaksi.getSelectedItem().toString();
            switch(LP){
                case "Peminjaman":
                    DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                    DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
                    String date1 = df1.format(jdFrom.getDate());
                    String date2 = df2.format(jdTo.getDate());
                    String dt1 = date1+" 00:00:00";
                    String dt2 = date2+" 23:59:59";
                    Timestamp timestamp1 = Timestamp.valueOf(dt1);
                    Timestamp timestamp2 = Timestamp.valueOf(dt2);
                    parameter.put("fromTgl", timestamp1);
                    parameter.put("toTgl", timestamp2);
                    
                    previewReport("Peminjaman.jasper",parameter,LP);
                    break;
                case "Pengembalian":
                    DateFormat df3 = new SimpleDateFormat("yyyy-MM-dd");
                    DateFormat df4 = new SimpleDateFormat("yyyy-MM-dd");
                    String date3 = df3.format(jdFrom.getDate());
                    String date4 = df4.format(jdTo.getDate());
                    String dt3 = date3+" 00:00:00";
                    String dt4 = date4+" 23:59:59";
                    Timestamp timestamp3 = Timestamp.valueOf(dt3);
                    Timestamp timestamp4 = Timestamp.valueOf(dt4);
                    parameter.put("fromTgl", timestamp3);
                    parameter.put("toTgl", timestamp4);
                    
                    previewReport("Pengembalian.jasper",parameter,LP);
                    break;
                case "Peserta":
                    previewReportMaster("Peserta.jasper",LP);
                    break;
                case "Barang":
                    previewReportMaster("Barang.jasper",LP);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "error");
                    break;
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnDataTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataTransaksiActionPerformed
        // TODO add your handling code here:
        jpDasboard.setVisible(false);
        jpPengembalian.setVisible(false);
        jpPeminjaman.setVisible(false);
        jpLaporan.setVisible(true);
    }//GEN-LAST:event_btnDataTransaksiActionPerformed

    private void txtIdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtIdMouseClicked
        // TODO add your handling code here:
        String txtid = txtId.getText();
        if(txtid.equals("Enter untuk melihat nama")){
        txtId.setText("");    
        }
    }//GEN-LAST:event_txtIdMouseClicked

    private void addCartBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCartBtnActionPerformed
        // TODO add your handling code here:
        int a=0;
        if(peminjamanCartTable.getRowCount()==0){
            addCartPeminjaman();    
        }else{
            for(int i=0; i<peminjamanCartTable.getRowCount(); i++){
                if(peminjamanCartTable.getValueAt(i, 1).toString().equals(cbBrgPeminjaman.getSelectedItem().toString())){
                    a=1;
                    }
            }
            if(a==1){
                JOptionPane.showMessageDialog(null, "Item sudah dimasukan sebelumnya");
            }else{
                addCartPeminjaman();
            }
        }
    }//GEN-LAST:event_addCartBtnActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        deleteCartPeminjaman();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        processPinjam();
    }//GEN-LAST:event_jButton7ActionPerformed

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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCartBtn;
    private javax.swing.JButton btnBarang;
    private javax.swing.JButton btnCariKdPemijaman_Pengembalian;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnDataTransaksi;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnPeminjaman;
    private javax.swing.JButton btnPengembalian;
    private javax.swing.JButton btnPeserta;
    private javax.swing.JButton cartBtn;
    private javax.swing.JComboBox<String> cbBrgPeminjaman;
    private javax.swing.JComboBox<String> cbLaporan_Transaksi;
    private javax.swing.JButton clear;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private com.toedter.calendar.JDateChooser jdFrom;
    private com.toedter.calendar.JDateChooser jdTo;
    private javax.swing.JPanel jpDasboard;
    private javax.swing.JPanel jpLaporan;
    private javax.swing.JPanel jpPeminjaman;
    private javax.swing.JPanel jpPengembalian;
    private javax.swing.JDialog peminjamanCartDialog;
    private javax.swing.JTable peminjamanCartTable;
    private javax.swing.JDialog pengembalianCartDialog;
    private javax.swing.JSpinner spJumlah;
    private javax.swing.JTable tablePeminjaman;
    private javax.swing.JTextArea txtDeskripsi;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtKdPeminjaman;
    private javax.swing.JTextField txtKdPinjam;
    private javax.swing.JTextField txtNama;
    // End of variables declaration//GEN-END:variables
}
