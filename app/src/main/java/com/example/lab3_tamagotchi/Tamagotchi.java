package com.example.lab3_tamagotchi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Tamagotchi extends Thread {

    private int food = 10;
    private String tama;
    boolean tamaState;
    Thread t;

    public String getTama() {
        return tama;
    }

    public void setTama(String tama) {
        this.tama = tama;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public interface Interface {
        int getFood();
        void setFood(int food);
        String getTama();
        void setTama(String tama);
        void endGame(boolean tamaState);
    }

    Interface listener;

    Tamagotchi(Interface listenerObj, String tama) {//, int food) {
        listener = listenerObj;
        this.tama = tama;
        //this.food = food;
        t = new Thread(this);
        t.start();
    }

    public void run() {
        try {

            while(true) {
                food--;
                if(food > 0) {
                    tamaState = true;
                    sleep(2000);
                }
                if (food < 0 || food > 20) {
                    tamaState = false;
                    t.interrupt();
                    sleep(2000);
                }
                String foodNotif = String.valueOf(food);
                Log.d("food", foodNotif);

                listener.getFood();
                listener.setFood(food);

            }
        }catch(InterruptedException e) {
            e.printStackTrace();
            System.out.println("Thread interrupted?");
            listener.endGame(tamaState); //checking if tamas are alive/dead. set on interrupt because interrupted thread = dead tama
        }
    }
}
