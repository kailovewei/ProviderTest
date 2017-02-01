package ykk.providertest.com.providertest;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private  Button addData;
    private  Button queryData;
    private  Button updateData;
    private  Button deleteData;
    private Button clearData;
    private EditText editText;
    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addData= (Button) findViewById(R.id.add_data);
        queryData= (Button) findViewById(R.id.query_data);
        updateData= (Button) findViewById(R.id.update_data);
        deleteData= (Button) findViewById(R.id.delete_data);
        clearData= (Button) findViewById(R.id.clear_edit);
        editText= (EditText) findViewById(R.id.edit_Id);

        //添加数据
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri= Uri.parse("content://ykk.sqlite.com.sqlite.provider/book");
                ContentValues values=new ContentValues();
                values.put("name","A Clash of King");
                values.put("author","George Martin");
                values.put("pages",1040);
                values.put("price",22.85);
                Uri newUri=getContentResolver().insert(uri,values);
                newId=newUri.getPathSegments().get(1);
            }
        });

        //查询数据
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("content://ykk.sqlite.com.sqlite.provider/book");
                Cursor cursor=getContentResolver().query(uri,null,null,null,null);
                if(cursor!=null)
                {
                    while(cursor.moveToNext())
                    {
                        String name=cursor.getString(cursor.getColumnIndex("name"));
                        String author=cursor.getString(cursor.getColumnIndex("author"));
                        int pages=cursor.getInt(cursor.getColumnIndex("pages"));
                        double price=cursor.getDouble(cursor.getColumnIndex("price"));
                        String data="name:"+name+"\n"+"author:"+author+"\n"+"pages:"+pages+"\n"+"price:"+price+"\n";
                        editText.setText(data);
                    }
                    cursor.close();
                }

            }
        });
        //更新数据
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("content://ykk.sqlite.com.sqlite.provider/book/"+newId);
                ContentValues values=new ContentValues();
                values.put("name","A Storm of Swords");
                values.put("pages",1216);
                values.put("price",24.05);
                getContentResolver().update(uri,values,null,null);

            }
        });
        //删除数据
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("content://ykk.sqlite.com.sqlite.provider/book/"+newId);
                getContentResolver().delete(uri,null,null);
            }
        });
        //清除EditText中的数据
        clearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
    }
}
