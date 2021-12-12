package com.lapiragua.applapiragua.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lapiragua.applapiragua.LugarPatrimonialActivity;
import com.lapiragua.applapiragua.R;
import com.lapiragua.applapiragua.model.LugarPatrimonial;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdapterItemCardLugar extends RecyclerView.Adapter<AdapterItemCardLugar.ItemCardLugarHolder> {

    ArrayList<LugarPatrimonial> lugaresList;
    ArrayList<LugarPatrimonial> lugaresListOriginal;
    private Context context;


    public AdapterItemCardLugar(Context context, ArrayList<LugarPatrimonial> lugaresList, ArrayList<LugarPatrimonial> lugaresListOriginal) {
        this.context = context;
        this.lugaresList = lugaresList;
        this.lugaresListOriginal = lugaresListOriginal;
    }


    @NonNull
    @Override
    public ItemCardLugarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //aca es cuando asigna el view holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lugar_buscar, parent, false);
        ItemCardLugarHolder holder = new ItemCardLugarHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCardLugarHolder holder, int position) {

        LugarPatrimonial lugarPatrimonial = lugaresList.get(position);
        holder.TV_name.setText(lugarPatrimonial.getNombre());
        holder.TV_localizacion.setText(lugarPatrimonial.getUbicacion());
        holder.TV_palabrasClave.setText(lugarPatrimonial.getPalabraClave());
        holder.TV_etiqueta.setText(lugarPatrimonial.getEtiqueta());
        Glide.with(context).load(lugarPatrimonial.getImageUrl()).into(holder.IM_photo);


    }

    @Override
    public int getItemCount() {
        return lugaresList.size();
    }

    public static class ItemCardLugarHolder extends RecyclerView.ViewHolder {

        TextView TV_name;
        TextView TV_localizacion;
        TextView TV_palabrasClave;
        TextView TV_etiqueta;
        ImageView IM_photo;
        Button btn_viewLugar;

        public ItemCardLugarHolder(@NonNull View itemView) {
            super(itemView);

            TV_name = itemView.findViewById(R.id.textView_buscar_name);
            TV_localizacion = itemView.findViewById(R.id.textView_buscar_localizacion);
            TV_palabrasClave = itemView.findViewById(R.id.textView_buscar_PalabrasClave);
            TV_etiqueta = itemView.findViewById(R.id.textView_buscar_Etiqueta);
            IM_photo = itemView.findViewById(R.id.imageView10);
            btn_viewLugar = itemView.findViewById(R.id.imageButton_viewLugar);
            btn_viewLugar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(TV_etiqueta.getText());

                    String codeQR;
                    codeQR = TV_etiqueta.getText().toString();
                    Intent intent = new Intent(itemView.getContext(), LugarPatrimonialActivity.class);
                    intent.putExtra("codeQR", codeQR);
                    itemView.getContext().startActivity(intent);
                }
            });


        }
    }

    public void filtrarLugares(String textSearch) {

        int longitud = textSearch.length();
        if (longitud == 0) {
            lugaresList.clear();
            lugaresList.addAll(lugaresListOriginal);
        } else {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

                List<LugarPatrimonial> collection = lugaresListOriginal.stream()
                        .filter(i -> i.getPalabraClave().toLowerCase().contains(textSearch.toLowerCase()))
                        .collect(Collectors.toList());
                lugaresList.clear();
                lugaresList.addAll(collection);
            } else {

                for (LugarPatrimonial c : lugaresListOriginal) {
                    System.out.println("C: " + c.getNombre());
                    if (c.getNombre().toLowerCase().contains(textSearch.toLowerCase())) {
                        lugaresList.add(c);
                    }
                }
            }
        }

        notifyDataSetChanged();
    }


}
