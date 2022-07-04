package com.example.tabbedactivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PhoneBookAdapter extends RecyclerView.Adapter<PhoneBookAdapter.MyViewHolder> {
    Context context;

    ArrayList<PhoneBook> phoneList = new ArrayList<>();

    PhoneBookAdapter(Context context){
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        LayoutInflater inflater =LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.phone_item, parent, false);

        return new MyViewHolder(view);
    }
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        PhoneBook phone = phoneList.get(position);

        holder.id_text.setText(String.valueOf(phone.getPhone_id()));
        holder.name_text.setText(String.valueOf(phone.getPhone_name()));
        holder.number_text.setText(String.valueOf(phone.getPhone_number()));

        byte[] phoneImage = phone.getUser_image();
        if (phoneImage!= null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(phoneImage, 0, phoneImage.length);
            holder.imageView.setImageBitmap(bitmap);
        }

    }

    @Override
    public int getItemCount(){
        return phoneList.size();
    }

    public void removeItem(int position){
        phoneList.remove(position);
    }

    public void addItem(PhoneBook item){
        phoneList.add(item);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id_text, name_text, number_text;
        ImageView imageView;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            id_text = itemView.findViewById(R.id.id_text);
            name_text = itemView.findViewById(R.id.name_text);
            imageView = itemView.findViewById(R.id.user_image);
            number_text = itemView.findViewById(R.id.number_text);
            mainLayout = itemView.findViewById(R.id.main_layout);
        }
    }
}
