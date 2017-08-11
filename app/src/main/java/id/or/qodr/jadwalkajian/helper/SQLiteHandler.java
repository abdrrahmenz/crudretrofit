package id.or.qodr.jadwalkajian.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import id.or.qodr.jadwalkajian.model.JadwalModel;

/**
 * Created by adul on 03/05/17.
 */

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private SQLiteDatabase db;
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "api_kajian";





    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + JadwalModel.TABLE_USER + "("
                + JadwalModel.KEY_ID + " INTEGER PRIMARY KEY," + JadwalModel.KEY_TYPE + " TEXT,"
                + JadwalModel.KEY_URL_MASJID + " TEXT," + JadwalModel.KEY_POSTER + " TEXT,"
                + JadwalModel.KEY_DAY + " TEXT," + JadwalModel.KEY_WEEK + " TEXT,"
                + JadwalModel.KEY_DATE + " TEXT," + JadwalModel.KEY_START + " TEXT,"
                + JadwalModel.KEY_END + " TEXT," + JadwalModel.KEY_TOPIC + " TEXT,"
                + JadwalModel.KEY_HOST + " TEXT," + JadwalModel.KEY_TAG + " TEXT,"
                + JadwalModel.KEY_LOC + " TEXT," + JadwalModel.KEY_ADDRESS + " TEXT,"
                + JadwalModel.KEY_LAT + " REAL," + JadwalModel.KEY_LNG + " REAL,"
                + JadwalModel.KEY_CP + " TEXT," + JadwalModel.KEY_STATUS + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + JadwalModel.TABLE_USER);

        // Create tables again
        onCreate(db);
    }
    public void deleteIFLoad(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ JadwalModel.TABLE_USER);
    }
    /**
     * Storing jadwal details in database
     * */
//    public void addKajian(String _id,String type, String url_masjid, String day, String week, String date,
//                        String start,String end,String topic,String host,String loc,String lat,
//                        String lng,String cp) {
    public void addKajian(JadwalModel modelobj) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(JadwalModel.KEY_ID, modelobj.getId()); // jenis_kajian
        values.put(JadwalModel.KEY_TYPE, modelobj.getJenis_kajian()); // jenis_kajian
        values.put(JadwalModel.KEY_URL_MASJID, modelobj.getFoto_masjid()); // foto_masjid
        values.put(JadwalModel.KEY_POSTER, modelobj.getPoster()); // poster
        values.put(JadwalModel.KEY_DAY, modelobj.getSetiap_hari()); // setiap_hari
        values.put(JadwalModel.KEY_WEEK, modelobj.getPekan()); // pekan
        values.put(JadwalModel.KEY_DATE, modelobj.getTanggal()); // tanggal
        values.put(JadwalModel.KEY_START, modelobj.getMulai()); // mulai
        values.put(JadwalModel.KEY_END, modelobj.getSampai()); // sampai
        values.put(JadwalModel.KEY_TOPIC, modelobj.getTema()); // tema
        values.put(JadwalModel.KEY_HOST, modelobj.getPemateri()); // pemateri
        values.put(JadwalModel.KEY_TAG, modelobj.getTag()); // tag
        values.put(JadwalModel.KEY_LOC, modelobj.getLokasi()); // lokasi
        values.put(JadwalModel.KEY_ADDRESS, modelobj.getAlamat()); // alamat
        values.put(JadwalModel.KEY_LAT, modelobj.getLat()); // lat
        values.put(JadwalModel.KEY_LNG, modelobj.getLng()); // lng
        values.put(JadwalModel.KEY_CP, modelobj.getCp()); // cp
        values.put(JadwalModel.KEY_STATUS, modelobj.getStatus()); // status

        // Inserting Row
        long id = db.replace(JadwalModel.TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d("TAG", "New user inserted into sqlite: " + id);
    }
    public Cursor readAllData(){
        SQLiteDatabase db  = this.getWritableDatabase();

        //query to get the data
        Cursor result = db.rawQuery("select * from "+JadwalModel.TABLE_USER,null);
        return result;
    }

    public Cursor getAllJadwal()
    {
        String[] columns={JadwalModel.KEY_ID,JadwalModel.KEY_TYPE,JadwalModel.KEY_URL_MASJID,JadwalModel.KEY_POSTER,
                JadwalModel.KEY_DAY,JadwalModel.KEY_WEEK,JadwalModel.KEY_DATE,JadwalModel.KEY_START,
                JadwalModel.KEY_END,JadwalModel.KEY_TOPIC,JadwalModel.KEY_HOST,JadwalModel.KEY_TAG,
                JadwalModel.KEY_LOC,JadwalModel.KEY_LAT,JadwalModel.KEY_LNG,JadwalModel.KEY_ADDRESS,
                JadwalModel.KEY_CP,JadwalModel.KEY_STATUS};
        return db.query(DATABASE_NAME,columns,null,null,null,null,null);
    }

}
