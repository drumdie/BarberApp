package com.drumdie.barberdemo.ui.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.drumdie.barberdemo.R;

import java.util.ArrayList;
import java.util.List;

public class SponsorAdapter extends RecyclerView.Adapter<SponsorAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Sponsor> sponsorList;
    private OnSponsorClickListener onSponsorClickListener;

    public interface OnSponsorClickListener{
        void onSponsorClick(Sponsor sponsor); // el argumento es el "Pet" q le hayamos dado click
    }
    public void setOnSponsorClickListener (OnSponsorClickListener onSponsorClickListener){
        this.onSponsorClickListener = onSponsorClickListener;

    }

    public SponsorAdapter( Context context, ArrayList<Sponsor> sponsorList) {
        this.context=context;
        this.sponsorList=sponsorList;
    }
    @NonNull
    @Override
    public SponsorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sponsor_list_item, parent, false); // Infla el layout (layoutId) y devuelve el ViewHolder(view)

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SponsorAdapter.ViewHolder holder, int position) { // enlaza los Views del ViewHolder con los datos q pasamos en PetList
        final Sponsor sponsor = sponsorList.get(position);
        holder.sponsorNameTextView.setText(sponsor.getName());
        holder.sponsorImageView.setImageDrawable(ContextCompat.getDrawable(context,sponsor.getImageId()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSponsorClickListener.onSponsorClick(sponsor);// envia què Pet (itemView) se hizo click (cualquier lado del mismo)
            }
        });

    }

    @Override
    public int getItemCount() {
        return sponsorList.size();
    }  // Devuelve el Num. de elementos del PetList

    class ViewHolder extends RecyclerView.ViewHolder { // puede llamarse de cualquier forma // elementos que estaran en el ViewHolder del Layout inflado
        private TextView sponsorNameTextView;
        private ImageView sponsorImageView;


        public ViewHolder(View itemView) {
            super(itemView); //se lo envia al OnCreateViewHolder para q lo infle
            sponsorNameTextView =(TextView) itemView.findViewById(R.id.sponsor_list_item_name);
            sponsorImageView=(ImageView) itemView.findViewById(R.id.sponsor_list_item_image);


        }
    }





}
