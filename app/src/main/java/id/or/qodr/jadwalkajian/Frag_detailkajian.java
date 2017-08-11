package id.or.qodr.jadwalkajian;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by adul on 06/05/17.
 */

public class Frag_detailkajian extends Fragment {

    View rootView;
    TextView tvImg,tvTema,tvPemateri,tvLokasi,tvCp;
    ImageView imgMasjid;
    String img,tema,pemateri,lokasi,cp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = container;
        View v = inflater.inflate(R.layout.detail_kajian,container, false);

        imgMasjid = (ImageView) v.findViewById(R.id.img_kajian);
        tvImg = (TextView) v.findViewById(R.id.txt_masjid);
        tvTema = (TextView) v.findViewById(R.id.txt_tema);
        tvPemateri = (TextView) v.findViewById(R.id.txt_pemateri);
        tvLokasi = (TextView) v.findViewById(R.id.txt_lokasi);
        tvCp = (TextView) v.findViewById(R.id.txt_cp);

        img = getArguments().getString("img");
        tema = getArguments().getString("tema");
        pemateri = getArguments().getString("pemateri");
        lokasi = getArguments().getString("lokasi");
        cp = getArguments().getString("cp");

        tvTema.setText("Tema : "+tema);
        tvPemateri.setText("Pemateri : "+pemateri);
        tvLokasi.setText("Lokasi : "+lokasi);
        tvCp.setText("Cp : "+cp);

        PicassoClient.loadImage(getActivity(),img,imgMasjid);

        return v;
    }
}
