package com.example.fragmentbestpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(this,"Vocabulary_Book.db",null,1);
        ContentValues values = new ContentValues();

  /*      values.put("Word", "add");
        values.put("Mean","vt. 增加; 补充; 附带说明; 把...包括在内;" );
        db.insert("Vocabulary", null, values);
        values.clear();
        values.put("Word", "delete");
        values.put("Mean","vt.& vi. 删除; " );
        db.insert("Vocabulary", null, values);
        values.clear();
        values.put("Word", "modify");
        values.put("Mean","vt. 更改，修改; （语法上）修饰; " );
        db.insert("Vocabulary", null, values);
        values.clear();
        values.put("Word", "search");
        values.put("Mean","v. 搜寻，搜索; 调查; 搜查; 探索;" +
                "n. 搜索; 调查; 探求; " );
        db.insert("Vocabulary", null, values);*/
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()) {
            case R.id.add:
            case R.id.delete:
            case R.id.modify:
            case R.id.search:  {
                Intent intent = new Intent(MainActivity.this, Add.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.help:  Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }


}
