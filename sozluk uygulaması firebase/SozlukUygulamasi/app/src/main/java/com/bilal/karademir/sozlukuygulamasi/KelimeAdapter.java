package com.bilal.karademir.sozlukuygulamasi;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class KelimeAdapter extends RecyclerView.Adapter<KelimeAdapter.CardTasarimTutucu>{

   private Context mContext;
    private List<Kelimeler> kelimelerList;

    public KelimeAdapter(Context context, List<Kelimeler> kelimelerList) {
        this.mContext = context;
        this.kelimelerList = kelimelerList;
    }

    @NonNull
    @Override
    public CardTasarimTutucu onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_sozluk,viewGroup,false);


        return new CardTasarimTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardTasarimTutucu cardTasarimTutucu, final int i) {

        final Kelimeler kelime = kelimelerList.get(i);
        cardTasarimTutucu.textViewIngilizce.setText(kelime.getIngilizce());
        cardTasarimTutucu.textViewTurkce.setText(kelime.getTurkce());

        cardTasarimTutucu.kelime_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,DetayActivity.class);
                intent.putExtra("nesne",kelime);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return kelimelerList.size();
    }


    public class CardTasarimTutucu extends RecyclerView.ViewHolder{

        private TextView textViewIngilizce,textViewTurkce;
        private CardView kelime_card;



        public CardTasarimTutucu(@NonNull View itemView) {
            super(itemView);

            kelime_card = itemView.findViewById(R.id.kelime_card);
            textViewIngilizce = itemView.findViewById(R.id.textViewIngilizce);
            textViewTurkce = itemView.findViewById(R.id.textViewTurkce);

        }
    }
}
