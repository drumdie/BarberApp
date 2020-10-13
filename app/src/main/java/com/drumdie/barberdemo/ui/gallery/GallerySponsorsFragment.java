package com.drumdie.barberdemo.ui.gallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drumdie.barberdemo.R;

import java.util.ArrayList;

public class GallerySponsorsFragment extends Fragment {
    //public static final String SPONSOR_KEY = "sponsor";
    private GalleryViewModel galleryViewModel;

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater,
                ViewGroup container, Bundle savedInstanceState){
            galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
            final View root = inflater.inflate(R.layout.fragment_gallery_sponsors, container, false);

            RecyclerView sponsorRecycler = root.findViewById(R.id.activity_main_sponsor_recycler);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2); // Default es vertical sino poner (this, LinearLayoutManager.HORIZONTAL)
            sponsorRecycler.setLayoutManager(gridLayoutManager); //sino GridLayourManager(contexto, cantidad de colunnas a mostrar)

            ArrayList<Sponsor> sponsorList = new ArrayList<>();
            sponsorList.add(new Sponsor("Descuentos Especiales", "https://www.instagram.com/__mr.blonde__/", R.drawable.sponsor_mrblonde));
            sponsorList.add(new Sponsor("Barbería Oficial - Los Tabaleros", "https://www.instagram.com/lostabaleros/", R.drawable.sponsor_lostabaleros));
            sponsorList.add(new Sponsor("Descuentos Especiales", "https://perezypunto.com.ar/", R.drawable.sponsor_perezypunto));
            sponsorList.add(new Sponsor("Descuentos Especiales", "https://www.instagram.com/julian.inksane/", R.drawable.sponsor_julian_inksane));
            sponsorList.add(new Sponsor("Descuento de 20% en cualquier producto", "https://www.instagram.com/mam.pasteleria/", R.drawable.sponsor_mampasteleria));
            sponsorList.add(new Sponsor("Descuentos Especiales", "https://www.instagram.com/__mr.blonde__/", R.drawable.sponsor_mrblonde));
            sponsorList.add(new Sponsor("Descuentos Especiales", "https://www.instagram.com/__mr.blonde__/", R.drawable.sponsor_mrblonde));

        SponsorAdapter sponsorAdapter = new SponsorAdapter(getActivity(), sponsorList);
            sponsorRecycler.setAdapter(sponsorAdapter);

            sponsorAdapter.setOnSponsorClickListener(new SponsorAdapter.OnSponsorClickListener() {
                @Override
                public void onSponsorClick(Sponsor sponsor) {
                    //Toast.makeText(getActivity(),sponsor.getName(),Toast.LENGTH_SHORT).show(); //muestra el nombre de la Pet q se le dio click
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(sponsor.getUrl()));
                    startActivity(intent);

            }
        });
            return root;
        }


}

