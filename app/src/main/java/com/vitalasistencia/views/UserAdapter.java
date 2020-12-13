package com.vitalasistencia.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vitalasistencia.R;
import com.vitalasistencia.models.BUser;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>
        implements View.OnClickListener {
    private ArrayList<BUser> items;
    private View.OnClickListener listener;

    // Clase interna:
    // Se implementa el ViewHolder que se encargará
    // de almacenar la vista del elemento y sus datos
    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private ImageView photo_reciclerview;
        private TextView email;
        private TextView address;

        public UserViewHolder(View itemView) {
            super(itemView);
            photo_reciclerview = (ImageView) itemView.findViewById(R.id.photo_reciclerview);
            email = (TextView) itemView.findViewById(R.id.firstName);
            address = (TextView) itemView.findViewById(R.id.secondName);
        }

        public void UserBind(BUser item) {
            email.setText(item.getEmail());
            address.setText(item.getAddress());
            try {
                byte[] decodedString = Base64.decode(item.getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                photo_reciclerview.setImageBitmap(decodedByte);
            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println("Error desconocido");
            }
        }
    }

    // Contruye el objeto adaptador recibiendo la lista de datos
    public UserAdapter(@NonNull ArrayList<BUser> items) {
        this.items = items;
    }

    // Se encarga de crear los nuevos objetos ViewHolder necesarios
    // para los elementos de la colección.
    // Infla la vista del layout, crea y devuelve el objeto ViewHolder
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row, parent, false);
        row.setOnClickListener(this);

        UserViewHolder users = new UserViewHolder(row);
        return users;
    }

    // Se encarga de actualizar los datos de un ViewHolder ya existente.
    @Override
    public void onBindViewHolder(UserViewHolder viewHolder, int position) {
        BUser item = items.get(position);
        viewHolder.UserBind(item);
    }

    // Indica el número de elementos de la colección de datos.
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Asigna un listener al elemento
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onClick(view);
    }
}
