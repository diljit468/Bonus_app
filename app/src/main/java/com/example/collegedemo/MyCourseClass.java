package com.example.collegedemo;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.collegedemo.common.ids;

public class MyCourseClass extends AppCompatActivity {
    private static final String TAG = "MyCourseClass";
    RecyclerView recycler;
    TextView myCourse,editCourse,price;
    Button update;
    OnClickCheck clickCheck;
    String data,courseNameStr="";
    ImageView back;
    int timeHours;
    ArrayList<model> models=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course);
        init();
        clickCheck=new OnClickCheck() {
            @Override
            public void onClick(View view, int amount, int time) {
                Log.e(TAG, "onClick: "+amount );
                Log.e(TAG, "onClick: "+time );
                timeHours=time;
                price.setText("Total Cost : $ "+amount +"\n"+
                        "Total Time : "+time +" hours");
            }
        };
        try {
            getData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        clicks();

    }
    private void init() {
        back=findViewById(R.id.back);
        editCourse=findViewById(R.id.editCourse);
        myCourse=findViewById(R.id.myCourse);
        recycler=findViewById(R.id.recycler);
        update=findViewById(R.id.update);
        price=findViewById(R.id.price);
        recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(mLayoutManager);
        recycler.setItemAnimator(new DefaultItemAnimator());
        data="{\"data\":[\n" +
                "{\"id\":1,\"name\":\"Java\",\"price\":\"1300\",\"time\":\"6\"},{\"id\":2,\"name\":\"Swift\",\"price\":\"1500\",\"time\":\"5\"},{\"id\":3,\"name\":\"IOS\",\"price\":\"1350\",\"time\":\"5\"},{\"id\":4,\"name\":\"Android\",\"price\":\"1400\",\"time\":\"7\"},{\"id\":5,\"name\":\"Database\",\"price\":\"1000\",\"time\":\"4\"}\n" +
                "]}";
    }
    private void getData() throws JSONException {
        models.clear();
        courseNameStr="";
        JSONObject object=new JSONObject(data);
        JSONArray array=object.getJSONArray("data");
        Log.e(TAG, "getData: "+ids);
        for(int j=0;j<ids.size();j++) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject data = (JSONObject) array.get(i);
                if(ids.get(j)== data.getInt("id")){
                    courseNameStr= courseNameStr+data.getString("name")+"\n \n";
                    model model=new model(data.getInt("id"),data.getString("name"),data.getInt("price"),data.getInt("time"));
                    models.add(model);
                }else{
                    continue;
                }
            }
        }
        myCourse.setText(courseNameStr);
        Adapter adapter=new Adapter(this,models,clickCheck,2);
        recycler.setAdapter(adapter);

    }
    private void clicks() {
        editCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recycler.setVisibility(View.VISIBLE);
                update.setVisibility(View.VISIBLE);
                price.setVisibility(View.VISIBLE);
                myCourse.setVisibility(View.GONE);
                editCourse.setTextColor(Color.RED);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ids.size()==0){
                    Toast.makeText(MyCourseClass.this, "Please select at-least one course", Toast.LENGTH_SHORT).show();
                }else {
                    recycler.setVisibility(View.GONE);
                    update.setVisibility(View.GONE);
                    price.setVisibility(View.GONE);
                    myCourse.setVisibility(View.VISIBLE);
                    editCourse.setTextColor(Color.GRAY);
                    try {
                        getData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(MyCourseClass.this, "Your courses has been updated", Toast.LENGTH_LONG).show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
