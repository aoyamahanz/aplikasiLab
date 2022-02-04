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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "data_barang")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DataBarang.findAll", query = "SELECT d FROM DataBarang d")
    , @NamedQuery(name = "DataBarang.findByKdBarang", query = "SELECT d FROM DataBarang d WHERE d.kdBarang = :kdBarang")
    , @NamedQuery(name = "DataBarang.findByNamaBarang", query = "SELECT d FROM DataBarang d WHERE d.namaBarang = :namaBarang")
    , @NamedQuery(name = "DataBarang.findByDeskripsi", query = "SELECT d FROM DataBarang d WHERE d.deskripsi = :deskripsi")
    , @NamedQuery(name = "DataBarang.findByStok", query = "SELECT d FROM DataBarang d WHERE d.stok = :stok")})
public class DataBarang implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "kd_barang")
    private Integer kdBarang;
    @Basic(optional = false)
    @Column(name = "nama_barang")
    private String namaBarang;
    @Basic(optional = false)
    @Column(name = "deskripsi")
    private String deskripsi;
    @Basic(optional = false)
    @Column(name = "stok")
    private int stok;

    public DataBarang() {
    }

    public DataBarang(Integer kdBarang) {
        this.kdBarang = kdBarang;
    }

    public DataBarang(Integer kdBarang, String namaBarang, String deskripsi, int stok) {
        this.kdBarang = kdBarang;
        this.namaBarang = namaBarang;
        this.deskripsi = deskripsi;
        this.stok = stok;
    }

    public Integer getKdBarang() {
        return kdBarang;
    }

    public void setKdBarang(Integer kdBarang) {
        this.kdBarang = kdBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kdBarang != null ? kdBarang.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataBarang)) {
            return false;
        }
        DataBarang other = (DataBarang) object;
        if ((this.kdBarang == null && other.kdBarang != null) || (this.kdBarang != null && !this.kdBarang.equals(other.kdBarang))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DataBarang[ kdBarang=" + kdBarang + " ]";
    }
    
}
