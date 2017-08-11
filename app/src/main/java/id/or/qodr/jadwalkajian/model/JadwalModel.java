package id.or.qodr.jadwalkajian.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by adul on 03/05/17.
 */

public class JadwalModel implements Serializable {
    public static final String KEY_POSTER = "poster";
    public static final String KEY_TAG = "tag";
    public static final String KEY_ADDRESS = "alamat";
    public static final String KEY_STATUS = "status";
    public int id;
    public String jenis_kajian;
    public String foto_masjid;
    public String poster;
    public String setiap_hari;
    public String pekan;
    public String tanggal;
    public String mulai;
    public String sampai;
    public String tema;
    public String pemateri;
    public String tag;
    public String lokasi;
    public String alamat;
    public Double lat;
    public Double lng;
    public String cp;
    public String status;

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_TYPE = "jenis_kajian";
    public static final String KEY_URL_MASJID = "foto_masjid";
    public static final String KEY_DAY = "setiap_hari";
    public static final String KEY_WEEK = "pekan";
    public static final String KEY_DATE = "tanggal";
    public static final String KEY_START = "mulai";
    public static final String KEY_END = "sampai";
    public static final String KEY_TOPIC = "tema";
    public static final String KEY_HOST = "pemateri";
    public static final String KEY_LOC = "lokasi";
    public static final String KEY_LAT = "lat";
    public static final String KEY_LNG = "lng";
    public static final String KEY_CP = "cp";
    // table name
    public static final String TABLE_USER = "jadwal";

    public JadwalModel() {
    }

    public JadwalModel(int id, String jenis_kajian, String foto_masjid,String poster, String setiap_hari,
                       String pekan, String tanggal, String mulai, String sampai, String tema,
                       String pemateri,String tag, String lokasi,String alamat, Double lat, Double lng,
                       String cp,String status) {
        this.id = id;
        this.jenis_kajian = jenis_kajian;
        this.foto_masjid = foto_masjid;
        this.poster = poster;
        this.setiap_hari = setiap_hari;
        this.pekan = pekan;
        this.tanggal = tanggal;
        this.mulai = mulai;
        this.sampai = sampai;
        this.tema = tema;
        this.pemateri = pemateri;
        this.tag = tag;
        this.lokasi = lokasi;
        this.alamat = alamat;
        this.lat = lat;
        this.lng = lng;
        this.cp = cp;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJenis_kajian() {
        return jenis_kajian;
    }

    public void setJenis_kajian(String jenis_kajian) {
        this.jenis_kajian = jenis_kajian;
    }

    public String getFoto_masjid() {
        return foto_masjid;
    }

    public void setFoto_masjid(String foto_masjid) {
        this.foto_masjid = foto_masjid;
    }

    public String getSetiap_hari() {
        return setiap_hari;
    }

    public void setSetiap_hari(String setiap_hari) {
        this.setiap_hari = setiap_hari;
    }

    public String getPekan() {
        return pekan;
    }

    public void setPekan(String pekan) {
        this.pekan = pekan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getMulai() {
        return mulai;
    }

    public void setMulai(String mulai) {
        this.mulai = mulai;
    }

    public String getSampai() {
        return sampai;
    }

    public void setSampai(String sampai) {
        this.sampai = sampai;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getPemateri() {
        return pemateri;
    }

    public void setPemateri(String pemateri) {
        this.pemateri = pemateri;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }
}
