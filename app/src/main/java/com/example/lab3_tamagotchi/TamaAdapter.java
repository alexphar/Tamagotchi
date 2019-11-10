package com.example.lab3_tamagotchi;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class TamaAdapter extends ArrayAdapter<Tamagotchi> {

    private Context mContext;
    private List<Tamagotchi> tamaList = new ArrayList<>();
    View listItem;

    public TamaAdapter(@NonNull Context context, @LayoutRes ArrayList<Tamagotchi> list) {
        super(context, 0 , list);
        mContext = context;
        tamaList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        final Tamagotchi oneTama = tamaList.get(position);

        TextView name = listItem.findViewById(R.id.txtTama);
        name.setText(oneTama.getTama());

        final TextView food = listItem.findViewById(R.id.txtFood);
        final String s = String.valueOf(oneTama.getFood());
        //Log.d("tamaadapter", s);

            if (oneTama.getFood() < 0 || oneTama.getFood() > 20) { //change the list item bg if food amount exceeds. could also be modified to check the state
                listItem.setBackgroundResource(R.color.red);
                notifyDataSetChanged();
            }

        food.post(new Runnable() {
            @Override
            public void run() {
                food.setText("Food left: " + s);
                notifyDataSetChanged();
            }
        });
        return listItem;
    }
}