package com.example.collegedemo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.example.collegedemo.common.ids;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private static final String TAG = "Adapter";
    Context context;
    ArrayList<model> models;
    ArrayList<Integer> prices = new ArrayList<>();
    ArrayList<Integer> times = new ArrayList<>();

    OnClickCheck clickCheck;
    int check;

    public Adapter(Context context, ArrayList<model> models, OnClickCheck clickCheck,int check) {
        this.context = context;
        this.models = models;
        this.clickCheck = clickCheck;
        this.check = check;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pos_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        Log.e(TAG, "onBindViewHolder: "+"Course: "+ models.get(i).name );
        if(check==1){
            holder.cb.setChecked(false);
        }else{
            prices.add(models.get(i).price);
            times.add(models.get(i).time);
            holder.cb.setChecked(true);
            int totP=0,totT = 0;
            for(int a=0;a<prices.size();a++){
                totP += prices.get(a);
            }   for(int b=0;b<times.size();b++){
                totT += times.get(b);
            }
            clickCheck.onClick(holder.cb,totP,totT);
        }
        holder.name.setText("Course: "+ models.get(i).name);
        holder.price.setText("Cost: $" + models.get(i).price);
        holder.time.setText("Time: " + models.get(i).time+" Hours");
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    prices.add(models.get(i).price);
                    times.add(models.get(i).time);
                    ids.add(models.get(i).id);
                } else {
                    prices.remove(new Integer(models.get(i).price));
                    times.remove(new Integer(models.get(i).time));
                    ids.remove(new Integer(models.get(i).id));
                }
                Log.e(TAG, "onCheckedChanged: " + prices.toString());
                if(prices.size()==0){
                    clickCheck.onClick(buttonView,0,0);

                }else{
                    int totP=0,totT = 0;
                    for(int i=0;i<prices.size();i++){
                        totP += prices.get(i);
                    }   for(int i=0;i<times.size();i++){
                        totT += times.get(i);
                    }

                  clickCheck.onClick(buttonView,totP,totT);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, time;
        CheckBox cb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            time = itemView.findViewById(R.id.time);
            cb = itemView.findViewById(R.id.cb);

        }
    }
}
