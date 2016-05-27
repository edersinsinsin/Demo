package eder.padilla.personal.works.redhabitat20.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import eder.padilla.personal.works.redhabitat20.R;
import eder.padilla.personal.works.redhabitat20.modelos.Visita;

/**This class will select the behavior of our recycler views.**/
public class AdaptadorVisitas
        extends RecyclerView.Adapter<AdaptadorVisitas.TitularesViewHolder>
        implements View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Visita> datos;
    private Context context;
    /**Here its gonna we going to declare the views we gonna use in our recyclerviews .**/
    public static class TitularesViewHolder
            extends RecyclerView.ViewHolder {

        private TextView txtTitulo;
        private TextView txtSubtitulo;
        private View mContainer;

        public TitularesViewHolder(View itemView) {
            super(itemView);

            txtTitulo = (TextView) itemView.findViewById(R.id.LblTitulo);
            txtSubtitulo = (TextView) itemView.findViewById(R.id.LblSubTitulo);
            mContainer = itemView.findViewById(R.id.layout_container);
        }

        /**Set text to the headers and also set the background .**/
        public void bindTitular(Visita t) {
            txtTitulo.setText(t.getNombre());
            txtSubtitulo.setText(t.getDireccion());
            if (t.getTipo().trim().equalsIgnoreCase("programada")) {
                mContainer.setBackgroundColor(mContainer.getResources().getColor(R.color.visita_programada));
            }else if(t.getTipo().trim().equalsIgnoreCase("norealizada")){
                mContainer.setBackgroundColor(mContainer.getResources().getColor(R.color.visita_no_realizada));
            }else if(t.getTipo().trim().equalsIgnoreCase("cancelada")){
                mContainer.setBackgroundColor(mContainer.getResources().getColor(R.color.visita_cancelada));
            }else if(t.getTipo().trim().equalsIgnoreCase("finalizada")){
                mContainer.setBackgroundColor(mContainer.getResources().getColor(R.color.visitas_finalizadas));
            }else {
                mContainer.setBackgroundColor(mContainer.getResources().getColor(R.color.white));
            }
        }
    }

    public AdaptadorVisitas(ArrayList<Visita> datos, Context context) {
        this.datos = datos;
        this.context = context;
    }
    /**Set the view that its gonna inflate inside the Recycler View.**/
    @Override
    public TitularesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.visita, viewGroup, false);
        itemView.setOnClickListener(this);
        TitularesViewHolder titularesViewHolder = new TitularesViewHolder(itemView);
        return titularesViewHolder;
    }

    @Override
    public void onBindViewHolder(TitularesViewHolder viewHolder, int pos) {
        Visita item = datos.get(pos);

        viewHolder.bindTitular(item);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onClick(view);
    }
}

