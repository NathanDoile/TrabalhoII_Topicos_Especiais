package com.example.deputadosandroid.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deputadosandroid.Model.Deputado;
import com.example.deputadosandroid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DeputadosAdapter  extends RecyclerView.Adapter<DeputadosAdapter.ViewHolder> {

    private List<Deputado> deputadosList;
    private DeputadosAdapter.OnItemClickListener listener;

    public DeputadosAdapter(List<Deputado> deputadosList, DeputadosAdapter.OnItemClickListener listener) {
        this.deputadosList = deputadosList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_deputado, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Deputado deputado = deputadosList.get(position);

        Picasso.get().load(deputado.getUrlFoto()).into(holder.deputados_img);

        holder.txtNome.setText(deputado.getNome());

        holder.txtPartido.setText(deputado.getSiglaPartido());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(deputado);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return deputadosList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Deputado deputado);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtNome, txtPartido;
        private ImageView deputados_img;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            txtNome = itemView.findViewById(R.id.nome_deputado);

            txtPartido = itemView.findViewById(R.id.sigla_partido_deputado);

            deputados_img = itemView.findViewById(R.id.image_deputado);
        }

    }
}