package com.example.lab3_tamagotchi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Tamagotchi.Interface {

    private ListView listView;
    private TamaAdapter tAdapter;
    int dead = 0;
    ArrayList<Tamagotchi> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.runOnUiThread(new Runnable() {
            public void run() {
                tamaFarm();
            }
        });
    }

    public void tamaFarm() {
        listView = findViewById(R.id.listView);
        for (int i = 0; i < 5; i++) {
            list.add(new Tamagotchi(this, "Tamagotchi" + i));

        }
            tAdapter = new TamaAdapter(this, list);
            listView.setAdapter(tAdapter);
        }

    @Override
    public int getFood() {
        return 0;
    }

    @Override
    public void setFood(final int food) { //function to feed tamas and inform about the change
                this.runOnUiThread(new Runnable() {
            public void run() {

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            int foodHere = food;
                            list.get(position).setFood(foodHere+10);
                            tAdapter.notifyDataSetChanged();
                    }
                });
                if (food < 0) {
                    tAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public String getTama() {
        return null;
    }

    @Override
    public void setTama(String tama) {
    }

    @Override
    public void endGame(final boolean tamaState) { //checking if tamagotchis are alive (true/false) and if so, a dialog appears

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    if (!tamaState) { //if tama is dead (state false)
                        dead++;
                        Log.d("dead", "" + dead);
                    }
                    if (dead == list.size()) {

                        Toast.makeText(MainActivity.this, "at tama endgame", Toast.LENGTH_LONG).show();

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        Log.d("tama", "endgame alert");
                        builder.setMessage("Tamas have died")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        System.exit(0);
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
    });
}
}
