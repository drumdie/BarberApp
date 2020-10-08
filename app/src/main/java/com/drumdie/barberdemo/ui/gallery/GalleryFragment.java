package com.drumdie.barberdemo.ui.gallery;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drumdie.barberdemo.MainActivity;
import com.drumdie.barberdemo.R;

import java.util.ArrayList;

public class GalleryFragment extends AppCompatActivity {
    public static final String SPONSOR_KEY = "sponsor";
    //private GalleryViewModel galleryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gallery);


        RecyclerView sponsorRecycler = findViewById(R.id.activity_main_sponsor_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2); // Default es vertical sino poner (this, LinearLayoutManager.HORIZONTAL)
        sponsorRecycler.setLayoutManager(gridLayoutManager); //sino GridLayourManager(contexto, cantidad de colunnas a mostrar)

        ArrayList<Sponsor> sponsorList = new ArrayList<>();
        sponsorList.add(new Sponsor("Mr.Blonde", "https://www.instagram.com/__mr.blonde__/", R.drawable.sponsor_mrblonde));
        sponsorList.add(new Sponsor("Los Tabaleros", "https://www.instagram.com/lostabaleros/", R.drawable.sponsor_lostabaleros));

        SponsorAdapter sponsorAdapter = new SponsorAdapter(this, sponsorList);
        sponsorRecycler.setAdapter(sponsorAdapter);

        sponsorAdapter.setOnSponsorClickListener(new SponsorAdapter.OnSponsorClickListener() {
            @Override
            public void onSponsorClick(Sponsor sponsor) {
               Toast.makeText(GalleryFragment.this,sponsor.getName(),Toast.LENGTH_SHORT).show(); //muestra el nombre de la Pet q se le dio click

                /*Intent intent = new Intent(intent, GalleryFragment.class);
                intent.putExtra(this.SPONSOR_KEY, sponsor);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(GalleryFragment.this).toBundle();
                    startActivity(intent, bundle);
                } else {
                    startActivity(intent);
                }*/
            }
        });
    }

        /*public View onCreateView (@NonNull LayoutInflater inflater,
                ViewGroup container, Bundle savedInstanceState){
            galleryViewModel =
                    ViewModelProviders.of(this).get(GalleryViewModel.class);
            View root = inflater.inflate(R.layout.fragment_gallery, container, false);
            final TextView textView = root.findViewById(R.id.text_gallery);
            galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    textView.setText(s);
                }
            });
            return root;
        }
*/

}

