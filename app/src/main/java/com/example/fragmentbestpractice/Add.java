package com.example.fragmentbestpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Add extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private String[] data={"apple","banana","orange","watermelon","pear","Grape"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbHelper = new MyDatabaseHelper(this,"Vocabulary_Book.db",null,1);
        final ContentValues values = new ContentValues();

  /*      values.put("Word", "add");
        values.put("Mean","vt. 增加; 补充; 附带说明; 把...包括在内;" );
        db.insert("Vocabulary", null, values);
        values.clear();*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
      // final TextView textView = (TextView) findViewById(R.id.text_view);
        final EditText word = (EditText)findViewById(R.id.word);
        final  EditText mean = (EditText) findViewById(R.id.mean);

        //实现增加功能
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int i=1;
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                if(word.getText().toString().length()==0)
                { Toast.makeText(Add.this, "请输入内容", Toast.LENGTH_SHORT).show();
                    i=0;
                }
                Cursor cursor = db.query("Vocabulary", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String user2 = cursor.getString(cursor.getColumnIndex("Word"));
                        if((word.getText().toString()).equals(user2)&&i!=0) {
                           Toast.makeText(Add.this, "单词" + user2 + "已存在", Toast.LENGTH_SHORT).show();
                           i = 0;
                       }
                    } while (cursor.moveToNext()) ;
                }
                cursor.close();
                if(i==1) {
                    values.put("Word", word.getText().toString());
                    values.put("Mean", mean.getText().toString());
                    db.insert("Vocabulary", null, values);
                    Toast.makeText(Add.this, "增加成功", Toast.LENGTH_SHORT).show();

                }
            }
        });

        //实现删除功能
        Button delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i=1;
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                if(word.getText().toString().length()==0)
                { Toast.makeText(Add.this, "请输入内容", Toast.LENGTH_SHORT).show();
                    i=0;
                }
                Cursor cursor = db.query("Vocabulary", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String user2 = cursor.getString(cursor.getColumnIndex("Word"));

                           if(i!=0&&(word.getText().toString()).equals(user2)) {
                               db.delete("Vocabulary","Word =?",new String[]{user2});
                            Toast.makeText(Add.this, "单词" + user2 + "删除成功", Toast.LENGTH_SHORT).show();
                            i = 0;
                        }

                    } while (cursor.moveToNext()) ;
                }
                cursor.close();
                if(i==1)  Toast.makeText(Add.this, "单词" + word.getText().toString() + "不存在", Toast.LENGTH_SHORT).show();
            }
        });

        //实现修改功能
        Button modify = (Button)findViewById(R.id.modify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i=1;
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values1 = new ContentValues();
                if(word.getText().toString().length()==0)
                { Toast.makeText(Add.this, "请输入内容", Toast.LENGTH_SHORT).show();
                    i=0;
                }
                Cursor cursor = db.query("Vocabulary", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String user2 = cursor.getString(cursor.getColumnIndex("Word"));
                        if((word.getText().toString()).equals(user2)) {
                            values1.put("Mean",mean.getText().toString());
                            db.update("Vocabulary",values1,"Word =?",new String[]{user2});
                            Toast.makeText(Add.this, "单词" + user2 + "的释义修改成功", Toast.LENGTH_SHORT).show();
                            i = 0;
                        }
                    } while (cursor.moveToNext()) ;
                }
                cursor.close();
                if(i==1)  Toast.makeText(Add.this, "单词" + word.getText().toString() + "不存在", Toast.LENGTH_SHORT).show();
            }
        });


        //按照单词查询
        Button search1 = (Button) findViewById(R.id.search1);
        search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 List<News> newsList = new ArrayList<>();
                String result = "";
                String str1 = word.getText().toString();
                Log.d("word"," is "+ str1 );
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String selectionArgs[] = new String[] { "%"+str1+"%" };
                String selection = "Word LIKE ?" ;//Word LIKE ?
                Cursor cursor2 = db.query("Vocabulary",null,selection,selectionArgs,null,null,null);
                if (cursor2.moveToFirst()) {
                    do {
                        String user2 = cursor2.getString(cursor2.getColumnIndex("Word"));
                        Log.d("word"," is "+ user2 );
                        String user1 = cursor2.getString(cursor2.getColumnIndex("Mean"));
                        Log.d("mean"," is "+ user1 );
                        News a1 = new News();
                        a1.setTitle(user2);
                        newsList.add(a1);
                        result =result+ user2 + user1;
                    } while (cursor2.moveToNext()) ;
                }
                if(result.length()>0) {
                   // textView.setText(result);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(Add.this);
                    recyclerView.setLayoutManager(layoutManager);
                    NewsTitleFragment newsTitleFragment = new NewsTitleFragment();
                    NewsTitleFragment.NewsAdapter adapter =newsTitleFragment.new NewsAdapter(newsList);
                    recyclerView.setAdapter(adapter);
                }
                else if(result.length()==0)
                    Toast.makeText(Add.this, "没有查找到结果", Toast.LENGTH_SHORT).show();
                cursor2.close();
            }
        });
        //按照释义查询
        Button search2 = (Button) findViewById(R.id.search2);
        search2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = "";
                String str1 = word.getText().toString();
                Log.d("word"," is "+ str1 );
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String selectionArgs[] = new String[] { "%"+str1+"%" };
                String selection = "Mean LIKE ?" ;//Word LIKE ?
                Cursor cursor2 = db.query("Vocabulary",null,selection,selectionArgs,null,null,null);
                if (cursor2.moveToFirst()) {
                    do {
                        String user2 = cursor2.getString(cursor2.getColumnIndex("Word"));
                        Log.d("word"," is "+ user2 );
                        String user1 = cursor2.getString(cursor2.getColumnIndex("Mean"));
                        Log.d("mean"," is "+ user1 );
                        result =result+ user2 + user1;
                    } while (cursor2.moveToNext()) ;
                }
                if(result.length()>0) {
                  //  textView.setText(result);
                }
                else if(result.length()==0)
                    Toast.makeText(Add.this, "没有查找到结果", Toast.LENGTH_SHORT).show();
                cursor2.close();
            }
        });


        //返回
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Add.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
