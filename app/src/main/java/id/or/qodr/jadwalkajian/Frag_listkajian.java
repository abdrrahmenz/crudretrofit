package id.or.qodr.jadwalkajian;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import id.or.qodr.jadwalkajian.adapter.Rcv_listkajian;
import id.or.qodr.jadwalkajian.helper.DBAdapter;
import id.or.qodr.jadwalkajian.helper.SQLiteHandler;
import id.or.qodr.jadwalkajian.model.JadwalModel;

/**
 * Created by adul on 03/05/17.
 */

public class Frag_listkajian extends Fragment {

    RecyclerView rv;
    SearchView sv;
    SwipeRefreshLayout swipeRefreshLayout;
    Rcv_listkajian adapter;
    ArrayList<JadwalModel> listjadwal=new ArrayList<>();
    private SQLiteHandler dblite;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = container;

        View v = inflater.inflate(R.layout.list_kajian, container, false);
        dblite = new SQLiteHandler(getActivity());

        //recycler
        rv= (RecyclerView) v.findViewById(R.id.rv_listkajian);
        sv= (SearchView) v.findViewById(R.id.sv);
        swipeRefreshLayout= (SwipeRefreshLayout) v.findViewById(R.id.swiper);

        //SET PROPS
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setItemAnimator(new DefaultItemAnimator());
        //ADAPTER
        adapter=new Rcv_listkajian(getActivity(),listjadwal,swipeRefreshLayout);
        //RETRIEVE
        retrieve();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getJadwal(newText);
                return false;
            }
        });

        return v;
    }

    private void getJadwal(String searchTerm)
    {
        listjadwal.clear();
        DBAdapter db=new DBAdapter(getActivity());
        db.openDB();
        JadwalModel p=null;
        Cursor c=db.retrieve(searchTerm);
        if (!searchTerm.isEmpty()) {
            while (c.moveToNext()) {
                int id = c.getInt(0);
                String tag = c.getString(11);
                p = new JadwalModel();
                p.setId(id);
                p.setPemateri(tag);
                listjadwal.add(p);
            }
            db.closeDB();
            rv.setAdapter(adapter);
        }else {
            retrieve();
        }
    }

    private void retrieve() {
        final ProgressDialog progress = ProgressDialog.show(getActivity(), "Loading","Please wait ...");
        listjadwal.clear();
        DBAdapter db=new DBAdapter(getActivity());
        db.openDB();
        //RETRIEVE
        Cursor c=db.getAllJadwalLis();
        //LOOP AND ADD TO ARRAYLIST
        while (c.moveToNext())
        {
            int id=c.getInt(0);
            String type=c.getString(1);
            String masjid=c.getString(2);
            String poster=c.getString(3);
            String day=c.getString(4);
            String week=c.getString(5);
            String date=c.getString(6);
            String start=c.getString(7);
            String end=c.getString(8);
            String topic=c.getString(9);
            String host=c.getString(10);
            String tag=c.getString(11);
            String loc=c.getString(12);
            String address=c.getString(13);
            double lat=c.getDouble(14);
            double lng=c.getDouble(15);
            String cp=c.getString(16);
            String status=c.getString(17);
//            JadwalModel model=new JadwalModel(id,type,masjid,poster,day,week, date, start, end,topic,host,tag,loc,address,lat,lng, cp,status);
            JadwalModel s = new JadwalModel();
            s.setId(id);
            s.setJenis_kajian(type);
            s.setFoto_masjid(masjid);
            s.setPoster(poster);
            s.setSetiap_hari(day);
            s.setPekan(week);
            s.setTanggal(date);
            s.setMulai(start);
            s.setSampai(end);
            s.setTema(topic);
            s.setPemateri(host);
            s.setTag(tag);
            s.setLokasi(loc);
            s.setAlamat(address);
            s.setLat(lat);
            s.setLng(lng);
            s.setCp(cp);
            s.setStatus(status);
            //ADD TO ARRAYLIS
            listjadwal.add(s);
        }
        //CHECK IF ARRAYLIST ISNT EMPTY
        if(!(listjadwal.size()<1))
        {
            rv.setAdapter(adapter);
        }
        progress.dismiss();
        db.closeDB();
    }
}
