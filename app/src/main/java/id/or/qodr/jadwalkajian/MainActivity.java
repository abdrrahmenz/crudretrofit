package id.or.qodr.jadwalkajian;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import id.or.qodr.jadwalkajian.adapter.Rcv_listkajian;
import id.or.qodr.jadwalkajian.helper.DBAdapter;
import id.or.qodr.jadwalkajian.helper.SQLiteHandler;
import id.or.qodr.jadwalkajian.model.JadwalModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static OkHttpClient okHttpClient;
    public static final String BASE_URL = "http://api.bbenkpartnersolo.com/";
    RecyclerView rv;
    DrawerLayout drawer;
    Rcv_listkajian adapter;
    ArrayList<JadwalModel> jadwalist;

    private SQLiteHandler dblite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        okHttpClient = new OkHttpClient();

        // SQLite database handler
        dblite = new SQLiteHandler(getApplicationContext());
        if (jadwalist == null) {
            jadwalist = new ArrayList<JadwalModel>();
        }

        //REFERENCE DRAWER,TOGGLE ITS INDICATOR
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //REFERNCE NAV VIEW AND ATTACH ITS ITEM SELECTION LISTENER
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    //CLOSE DRAWER WHEN BACK BTN IS CLICKED,IF OPEN
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //RAISED WHEN NAV VIEW ITEM IS SELECTED
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //OPEN APPROPRIATE FRAGMENT WHEN NAV ITEM IS SELECTED
        if (id == R.id.drawer_hari) {
            //PERFORM TRANSACTION TO REPLACE CONTAINER WITH FRAGMENT
            Toast.makeText(getApplicationContext(), "hAri ni", Toast.LENGTH_SHORT).show();
            MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.containerID, KajianHari.newInstance()).commit();
        } else if (id == R.id.drawer_pekan) {
            MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.containerID, KajianPekan.newInstance()).commit();
        } else if (id == R.id.drawer_bulan) {
            MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.containerID, KajianBulan.newInstance()).commit();
        } else if (id == R.id.interuniverse) {

        } else if (id == R.id.nav_share) {
        } else if (id == R.id.nav_send) {
        }
        //REFERENCE AND CLOSE DRAWER LAYOUT
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
//                startActivity(new Intent(this, Login.class));
//                return true;
                getDataKajianFromAPI();
            case R.id.load:
//                startActivity(new Intent(this, Login.class));
//                return true;
                Frag_listkajian frag = new Frag_listkajian();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerID, frag)
                        .commit();

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    void getDataKajianFromAPI(){
        final ProgressDialog progress = ProgressDialog.show(this, "Loading","Please wait ...");
        Request request = new Request.Builder().url(MainActivity.BASE_URL+"jadwal").build();
        MainActivity.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("adul", "onResponse: "+e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                        Snackbar.make(drawer, "Internet error", Snackbar.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String strResponse = response.body().string();
                Log.i("adul", "onResponse: "+strResponse);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            progress.dismiss();
                            JSONObject jsonResponse = new JSONObject(strResponse);
                            if (jsonResponse.getBoolean("result")){
                                JSONArray array = jsonResponse.getJSONArray("jadwal");
                                dblite.deleteIFLoad();
                                for(int i = 0; i < array.length(); i++) {
                                    JadwalModel modelobj = new JadwalModel();
                                    JSONObject store = array.getJSONObject(i);

                                    modelobj.setId(store.getInt("id"));
                                    modelobj.setJenis_kajian(store.getString("jenis_kajian"));
                                    modelobj.setFoto_masjid(store.getString("foto_masjid"));
                                    modelobj.setPoster(store.getString("poster"));
                                    modelobj.setPekan(store.getString("pekan"));
                                    modelobj.setTanggal(store.getString("tanggal"));
                                    modelobj.setMulai(store.getString("mulai"));
                                    modelobj.setSampai(store.getString("sampai"));
                                    modelobj.setTema(store.getString("tema"));
                                    modelobj.setPemateri(store.getString("pemateri"));
                                    modelobj.setTag(store.getString("tag"));
                                    modelobj.setLokasi(store.getString("lokasi"));
                                    modelobj.setAlamat(store.getString("alamat"));
                                    modelobj.setLat(store.getDouble("lat"));
                                    modelobj.setLng(store.getDouble("lng"));
                                    modelobj.setCp(store.getString("cp"));
                                    modelobj.setStatus(store.getString("status"));
                                    jadwalist.add(modelobj);
//                                    String id = store.getString("id");
//                                    String type = store.getString("jenis_kajian");
//                                    String masjid = store.getString("foto_masjid");
//                                    String day = store.getString("setiap_hari");
//                                    String week = store.getString("pekan");
//                                    String date = store.getString("tanggal");
//                                    String start = store.getString("mulai");
//                                    String end = store.getString("sampai");
//                                    String topic = store.getString("tema");
//                                    String host = store.getString("pemateri");
//                                    String loc = store.getString("lokasi");
//                                    String lat = store.getString("lat");
//                                    String lng = store.getString("lng");
//                                    String cp = store.getString("cp");
//                                    adapterListProduct.setJson_listproduct(array);

                                    // Inserting row in users table
                                    dblite.addKajian(modelobj);
//                                    Frag_listkajian frag = new Frag_listkajian();
//                                    getSupportFragmentManager()
//                                            .beginTransaction()
//                                            .replace(R.id.frag_container, frag)
//                                            .commit();
                                    Log.i("adul", "run: "+modelobj.getId());
                                }
                                Snackbar.make(drawer, "Success save Data",Snackbar.LENGTH_SHORT).show();

                            } else {
                                Snackbar.make(drawer, "Else ",Snackbar.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


}
