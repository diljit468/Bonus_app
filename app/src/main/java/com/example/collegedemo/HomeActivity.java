package com.example.collegedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    String data;
    ArrayList<model> models=new ArrayList<>();
    private RecyclerView recyclerView;
    RelativeLayout click;
    TextView select,total;
    int check =1,timeHours=0;
    Button next;
    RadioButton delivery,pick;
    OnClickCheck clickCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        data="{\"data\":[\n" +
                "{\"id\":1,\"name\":\"Java\",\"price\":\"1300\",\"time\":\"6\"},{\"id\":2,\"name\":\"Swift\",\"price\":\"1500\",\"time\":\"5\"},{\"id\":3,\"name\":\"IOS\",\"price\":\"1350\",\"time\":\"5\"},{\"id\":4,\"name\":\"Android\",\"price\":\"1400\",\"time\":\"7\"},{\"id\":5,\"name\":\"Database\",\"price\":\"1000\",\"time\":\"4\"}\n" +
                "]}";

        clickCheck=new OnClickCheck() {
            @Override
            public void onClick(View view, int amount, int time) {
                Log.e(TAG, "onClick: "+amount );
                Log.e(TAG, "onClick: "+time );
                timeHours=time;
                total.setText("Total Cost : $ "+amount +"\n"+
                        "Total Time : "+time +" hours");
            }
        };
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        click =  findViewById(R.id.click);
        select =  findViewById(R.id.select);
        total =  findViewById(R.id.total);
        next =  findViewById(R.id.next);
        delivery = findViewById(R.id.delivery);
        pick = findViewById(R.id.pick);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        try {
            getDataFromJson();
        } catch (JSONException e) {
            Log.e(TAG, "onCreate: "+e );
        }


        // Click
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timeHours==0){
                    select.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    Toast.makeText(HomeActivity.this, "Please choose at-least one course", Toast.LENGTH_SHORT).show();
                }else {
                    if (check == 1) {
                        //21
                        if (timeHours < 21) {
                            Toast.makeText(HomeActivity.this, "Done!!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(HomeActivity.this, MyCourseClass.class));
                        } else {
                            Toast.makeText(HomeActivity.this, "You Studied maximum 21 hours", Toast.LENGTH_SHORT).show();
                        }
                    } else if (check == 2) {
                        if (timeHours < 19) {
                            Toast.makeText(HomeActivity.this, "Done!!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(HomeActivity.this, MyCourseActivity.class));
                        } else {
                            Toast.makeText(HomeActivity.this, "You Studied maximum 19 hours", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(HomeActivity.this, "Choose Valid Choice", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

        RadioGroup rGroup = (RadioGroup)findViewById(R.id.radioGroup);
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked)
                {
                    if(checkedRadioButton.getText().equals("Graduate")){
                        delivery.setChecked(false);
                        pick.setChecked(true);
                        check=1;
                    }else{
                        delivery.setChecked(true);
                        pick.setChecked(false);
                        check=2;
                    }
                }
            }
        });
    }

    private void getDataFromJson() throws JSONException {
        JSONObject object = new JSONObject(data);
        Log.e(TAG, "getDataFromJson: "+object );
        JSONArray array = object.getJSONArray("data");
        for (int i = 0; i < array.length(); i++) {
            JSONObject data = (JSONObject) array.get(i);
            model model=new model(data.getInt("id"),data.getString("name"),data.getInt("price"),data.getInt("time"));
            models.add(model);
        }
        Adapter adapter=new Adapter(this,models,clickCheck,1);
        recyclerView.setAdapter(adapter);
    }

}
