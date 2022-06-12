package com.example.inventory2

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val AUTHORITY = "com.example.inventory"
        val URL ="content://$AUTHORITY/person_info"
        val uri: Uri = Uri.parse(URL)
        val PERSON_TABLE_NAME = "person_info"

        val cr = contentResolver.acquireContentProviderClient(uri) //This line is new
        val rs: Cursor? = cr?.query(uri, null, null, null, null) //Line modified
        val _ID = "_id"
        val Country = "Country"
        val Capital = "Capital"

        val Button1: Button = findViewById(R.id.button)
        val Button2: Button = findViewById(R.id.button2)
        val Button3: Button = findViewById(R.id.button3)
        val Button4: Button = findViewById(R.id.button4)
        val Button5: Button = findViewById(R.id.button5)
        val Button6: Button = findViewById(R.id.button6)
        val Text1 : EditText =findViewById(R.id.editTextTextPersonName)
        val Text2 : EditText =findViewById(R.id.editTextTextPersonName2)
        Button1.setOnClickListener{
            if (rs != null) {
                if(rs.moveToNext()!!){
                    Text1.setText(rs.getString(1))
                    Text2.setText(rs.getString(2))
                }
            }
            Button2.setOnClickListener{
                if (rs != null) {
                    if(rs.moveToPrevious()!!){
                        Text1.setText(rs.getString(1))
                        Text2.setText(rs.getString(2))
                    }
                }
            }
            Button3.setOnClickListener{
                var cv = ContentValues()
                cv.put(Country, Text1.text.toString())
                cv.put(Capital,Text2.text.toString())
                contentResolver.insert(uri,cv)
                rs?.requery()
            }
            Button4.setOnClickListener{

                var cv = ContentValues()
                //cv.put(Capital,Text1.text.toString())
                cv.put(Capital,Text2.text.toString())
                contentResolver.update(uri,cv,"country =?",arrayOf(Text1.text.toString()))
                rs?.requery()
            }

            Button5.setOnClickListener{
                contentResolver.delete(uri,null,null)
                rs?.requery()
            }

            Button6.setOnClickListener{
                Text1.setText("")
                Text2.setText("")
                Text1.requestFocus()
            }
        }

    }
}