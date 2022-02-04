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
@Table(name = "data_pengembalian")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DataPengembalian.findAll", query = "SELECT d FROM DataPengembalian d")
    , @NamedQuery(name = "DataPengembalian.findByKdPengembalian", query = "SELECT d FROM DataPengembalian d WHERE d.kdPengembalian = :kdPengembalian")
    , @NamedQuery(name = "DataPengembalian.findByKdPeminjaman", query = "SELECT d FROM DataPengembalian d WHERE d.kdPeminjaman = :kdPeminjaman")})
public class DataPengembalian implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "kd_pengembalian")
    private Integer kdPengembalian;
    @Basic(optional = false)
    @Column(name = "kd_peminjaman")
    private int kdPeminjaman;

    public DataPengembalian() {
    }

    public DataPengembalian(Integer kdPengembalian) {
        this.kdPengembalian = kdPengembalian;
    }

    public DataPengembalian(Integer kdPengembalian, int kdPeminjaman) {
        this.kdPengembalian = kdPengembalian;
        this.kdPeminjaman = kdPeminjaman;
    }

    public Integer getKdPengembalian() {
        return kdPengembalian;
    }

    public void setKdPengembalian(Integer kdPengembalian) {
        this.kdPengembalian = kdPengembalian;
    }

    public int getKdPeminjaman() {
        return kdPeminjaman;
    }

    public void setKdPeminjaman(int kdPeminjaman) {
        this.kdPeminjaman = kdPeminjaman;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kdPengembalian != null ? kdPengembalian.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataPengembalian)) {
            return false;
        }
        DataPengembalian other = (DataPengembalian) object;
        if ((this.kdPengembalian == null && other.kdPengembalian != null) || (this.kdPengembalian != null && !this.kdPengembalian.equals(other.kdPengembalian))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DataPengembalian[ kdPengembalian=" + kdPengembalian + " ]";
    }
    
}
