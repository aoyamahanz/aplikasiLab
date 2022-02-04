/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Herman Patria
 */
@Entity
@Table(name = "data_peminjam")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DataPeminjam.findAll", query = "SELECT d FROM DataPeminjam d")
    , @NamedQuery(name = "DataPeminjam.findById", query = "SELECT d FROM DataPeminjam d WHERE d.id = :id")
    , @NamedQuery(name = "DataPeminjam.findByNisNik", query = "SELECT d FROM DataPeminjam d WHERE d.nisNik = :nisNik")
    , @NamedQuery(name = "DataPeminjam.findByNama", query = "SELECT d FROM DataPeminjam d WHERE d.nama = :nama")
    , @NamedQuery(name = "DataPeminjam.findByJenisKelamin", query = "SELECT d FROM DataPeminjam d WHERE d.jenisKelamin = :jenisKelamin")
    , @NamedQuery(name = "DataPeminjam.findByNoTlp", query = "SELECT d FROM DataPeminjam d WHERE d.noTlp = :noTlp")
    , @NamedQuery(name = "DataPeminjam.findByRole", query = "SELECT d FROM DataPeminjam d WHERE d.role = :role")})
public class DataPeminjam implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @Column(name = "nis_nik")
    private String nisNik;
    @Basic(optional = false)
    @Column(name = "nama")
    private String nama;
    @Basic(optional = false)
    @Column(name = "jenis_kelamin")
    private String jenisKelamin;
    @Basic(optional = false)
    @Column(name = "no_tlp")
    private String noTlp;
    @Basic(optional = false)
    @Column(name = "role")
    private String role;

    public DataPeminjam() {
    }

    public DataPeminjam(String id) {
        this.id = id;
    }

    public DataPeminjam(String id, String nisNik, String nama, String jenisKelamin, String noTlp, String role) {
        this.id = id;
        this.nisNik = nisNik;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.noTlp = noTlp;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNisNik() {
        return nisNik;
    }

    public void setNisNik(String nisNik) {
        this.nisNik = nisNik;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getNoTlp() {
        return noTlp;
    }

    public void setNoTlp(String noTlp) {
        this.noTlp = noTlp;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataPeminjam)) {
            return false;
        }
        DataPeminjam other = (DataPeminjam) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DataPeminjam[ id=" + id + " ]";
    }
    
}
