package id.or.qodr.jadwalkajian.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.or.qodr.jadwalkajian.Frag_detailkajian;
import id.or.qodr.jadwalkajian.MainActivity;
import id.or.qodr.jadwalkajian.R;
import id.or.qodr.jadwalkajian.helper.DBAdapter;
import id.or.qodr.jadwalkajian.model.JadwalModel;

/**
 * Created by adul on 03/05/17.
 */

public class Rcv_listkajian extends RecyclerView.Adapter<Rch_listkajian> {

    Context c;
    ArrayList<JadwalModel> jadwal;
    SwipeRefreshLayout swipeRefreshLayout;

    public Rcv_listkajian(Context c, ArrayList<JadwalModel> jadwal, SwipeRefreshLayout swipeRefreshLayout) {
        this.c = c;
        this.jadwal = jadwal;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    public Rch_listkajian onCreateViewHolder(ViewGroup parent, int viewType) {
        //VIEW OBJ
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_kajian,parent,false);
        return new Rch_listkajian(v);
    }

    @Override
    public void onBindViewHolder(Rch_listkajian holder, final int position) {
        holder.id.setText(String.valueOf(jadwal.get(position).getId()));
        holder.type.setText(jadwal.get(position).getJenis_kajian());
        holder.masjid.setText(jadwal.get(position).getFoto_masjid());
        holder.day.setText(jadwal.get(position).getSetiap_hari());
        holder.pekan.setText(jadwal.get(position).getPekan());
        holder.tanggal.setText(jadwal.get(position).getTanggal());
        holder.mulai.setText(jadwal.get(position).getMulai());
        holder.sampai.setText(jadwal.get(position).getSampai());
        holder.tema.setText(jadwal.get(position).getTema());
        holder.pemateri.setText(jadwal.get(position).getPemateri());
        holder.lokasi.setText(jadwal.get(position).getLokasi());
        holder.lat.setText(String.valueOf(jadwal.get(position).getLat()));
        holder.lng.setText(String.valueOf(jadwal.get(position).getLng()));
        holder.cp.setText(jadwal.get(position).getCp());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putString("img",jadwal.get(position).getFoto_masjid());
                args.putString("tema",jadwal.get(position).getTema());
                args.putString("pemateri",jadwal.get(position).getPemateri());
                args.putString("lokasi",jadwal.get(position).getLokasi());
                args.putString("cp",jadwal.get(position).getCp());

                Fragment frag = new Frag_detailkajian();
                frag.setArguments(args);
                ((MainActivity)c).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerID,frag)
                        .addToBackStack(null)
                        .commit();

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUpdates();
                Toast.makeText(c, "refres", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void getUpdates()
    {
        final ProgressDialog progress = ProgressDialog.show(c, "Loading","Please wait ...");
        jadwal.clear();
        DBAdapter db=new DBAdapter(c);
        db.openDB();
        Cursor c=db.getAllJadwalLis();
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
            JadwalModel s=new JadwalModel();
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
            jadwal.add(s);
        }
        progress.dismiss();
        db.closeDB();
        swipeRefreshLayout.setRefreshing(false);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return jadwal.size();
    }
}

class Rch_listkajian extends RecyclerView.ViewHolder{

    public TextView masjid,pekan,day,mulai, sampai, tema, pemateri, lokasi, lat, lng, cp, id, type, tanggal, bulan;
    public View rootView;

    public Rch_listkajian(View itemView) {
        super(itemView);

        rootView = itemView;
//        img = (ImageView) itemView.findViewById(R.id.img);
        id = (TextView) rootView.findViewById(R.id.txt_id);
        type = (TextView) rootView.findViewById(R.id.txt_typekjian);
        masjid = (TextView) rootView.findViewById(R.id.txt_masjid);
        day = (TextView) rootView.findViewById(R.id.txt_day);
        pekan = (TextView) rootView.findViewById(R.id.txt_pekan);
        tanggal = (TextView) rootView.findViewById(R.id.txt_tanggal);
        bulan = (TextView) rootView.findViewById(R.id.txt_bulan);
        mulai = (TextView) rootView.findViewById(R.id.txt_mulai);
        sampai = (TextView) rootView.findViewById(R.id.txt_sampai);
        tema = (TextView) rootView.findViewById(R.id.txt_tema);
        pemateri = (TextView) rootView.findViewById(R.id.txt_pemateri);
        lokasi = (TextView) rootView.findViewById(R.id.txt_lokasi);
        lat = (TextView) rootView.findViewById(R.id.txt_lat);
        lng = (TextView) rootView.findViewById(R.id.txt_lng);
        cp = (TextView) rootView.findViewById(R.id.txt_cp);
    }
}
