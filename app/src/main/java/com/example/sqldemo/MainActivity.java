package com.example.sqldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView(){
        Button insert = (Button) findViewById(R.id.insert_button);
        Button insert_cleardata = (Button) findViewById(R.id.clear_insert_button);

        Button update = (Button) findViewById(R.id.update_button);
        Button update_cleardata = (Button)findViewById(R.id.clear_update_button);

        Button delete = (Button) findViewById(R.id.delete_button);
        Button delete_cleardata = (Button)findViewById(R.id.clear_delete_button);

        Button query = (Button) findViewById(R.id.query);
        Button clearquery = (Button)findViewById(R.id.clear_query);

        //为所有按钮对象设置监听器
        insert.setOnClickListener(this);
        insert_cleardata.setOnClickListener(this);

        update.setOnClickListener(this);
        update_cleardata.setOnClickListener(this);

        delete.setOnClickListener(this);
        delete_cleardata.setOnClickListener(this);

        query.setOnClickListener(this);
        clearquery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText insert_text = findViewById(R.id.insert_text);
        String insert_date = insert_text.getText().toString();

        EditText delete_text = findViewById(R.id.delete_text);
        String delete_date = delete_text.getText().toString();
        EditText update_before_text = (EditText)findViewById(R.id.update_before_text);
        String update_before_data = update_before_text.getText().toString();
        EditText update_after_text = (EditText)findViewById(R.id.update_after_text);
        String update_after_data = update_after_text.getText().toString();

        TextView textView = (TextView)findViewById(R.id.query_text);
        DBHelper helper = new DBHelper(this,"Demo",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();
        switch (v.getId()){
            case R.id.insert_button:
                ContentValues values = new ContentValues();
                values.put("name",insert_date);
                db.insert("zhang",null,values);
                break;
            case R.id.delete_button:
                db.delete("zhang","name=?",new String[]{delete_date});
                break;
            case R.id.update_button:
                ContentValues values1 =  new ContentValues();
                values1.put("name",update_after_data);
                db.update("zhang",values1,"name=?",new String[]{update_before_data});
                break;
            //查询全部按钮
            case R.id.query:
                //创建游标对象
                Cursor cursor = db.query("zhang",new String[]{"name"},null,null,null,null,null);
                String textview_date = "";
                while (cursor.moveToNext()){
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    textview_date = textview_date+"\n"+name;
                }
                textView.setText(textview_date);
                break;
            default:
                break;
        }
    }
}