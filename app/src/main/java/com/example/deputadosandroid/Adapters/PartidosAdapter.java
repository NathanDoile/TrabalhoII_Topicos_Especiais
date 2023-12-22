package com.example.deputadosandroid.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deputadosandroid.Model.Partido;
import com.example.deputadosandroid.R;

import java.util.List;

public class PartidosAdapter  extends RecyclerView.Adapter<PartidosAdapter.ViewHolder> {

    private List<Partido> partidoList;

    private OnItemClickListener listener;

    public PartidosAdapter(List<Partido> partidoList, OnItemClickListener listener) {
        this.partidoList = partidoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_partido, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Partido partido = partidoList.get(position);

        holder.textNome.setText(partido.getNome());
        holder.textSigla.setText(partido.getSigla());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(partido);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return partidoList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Partido partido);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNome, textSigla;
        ImageView partido_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textNome = itemView.findViewById(R.id.nome_partido);
            textSigla = itemView.findViewById(R.id.sigla_partido);
//            partido_img = itemView.findViewById(R.id.partido_img);
        }
    }
}
