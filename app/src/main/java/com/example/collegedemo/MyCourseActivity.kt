package com.example.collegedemo

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.collegedemo.common.ids
import kotlinx.android.synthetic.main.activity_my_course.*
import org.json.JSONObject
import kotlin.collections.ArrayList

abstract class MyCourseActivity : AppCompatActivity() , View.OnClickListener {
    var courseNameSt = ""
   // var models: List<model> = ArrayList()
    var models = ArrayList<model>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_course)
        getData();
        editCourse.setOnClickListener(this)


    }

    private fun getData() {
       val data = """
            {"data":[
            {"id":1,"name":"Java","price":"1300","time":"6"},{"id":2,"name":"Swift","price":"1500","time":"5"},{"id":3,"name":"IOS","price":"1350","time":"5"},{"id":4,"name":"Android","price":"1400","time":"7"},{"id":5,"name":"Database","price":"1000","time":"4"}
            ]}
            """.trimIndent()
        val jsonObject= JSONObject(data)
        val jsonArray=jsonObject.getJSONArray("data")

        for(j in 0 until ids.size){
            log(""+ids[j])
            for(i in 0 until jsonArray.length()){
                val dataItem=jsonArray.getJSONObject(i)
                if(ids[j]==dataItem.getInt("id")){
                    log(""+dataItem)
                    courseNameSt= courseNameSt+dataItem.getString("name")+"\n \n"
                    models.add(model(dataItem.getInt("id"), dataItem.getString("name"), dataItem.getInt("price"), dataItem.getInt("time")))
                }else{
                    continue
                }
            }
            log(models.get(0).getName())
            myCourse.setText(courseNameSt)
        }

    }
    private fun log(s: String){
        Log.e("TAG","data :  "+s)
    }

    override fun onClick(v: View?) {
       when (v){
           editCourse -> {
               Log.e("Tag",""+ids)
               myCourse.visibility = View.GONE
               recycler.visibility = View.VISIBLE
               Log.e("Tag",""+recycler.visibility)
               update.visibility = View.VISIBLE
               editCourse.setTextColor(Color.RED)
               editCourse.isEnabled=false
           }
       }
    }
}

private operator fun <E> List<E>.get(model: E) {

}
