package com.example.inventory2

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

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
        var id_column =0

        val Button1: Button = findViewById(R.id.button)
        val Button2: Button = findViewById(R.id.button2)
        val Button3: Button = findViewById(R.id.button3)
        val Button4: Button = findViewById(R.id.button4)
        val Button5: Button = findViewById(R.id.button5)
        val Button6: Button = findViewById(R.id.button6)
        val Button7: Button = findViewById(R.id.button7)
        val Text1 : EditText =findViewById(R.id.editTextTextPersonName)
        val Text2 : EditText =findViewById(R.id.editTextTextPersonName2)
        Button1.setOnClickListener {
            if (rs != null) {
                if (rs.moveToNext()!!) {
                    id_column = rs.getInt(0)
                    Text1.setText(rs.getString(1))
                    Text2.setText(rs.getString(2))
                }
            }

            Button2.setOnClickListener {
                if (rs != null) {
                    if (rs.moveToPrevious()!!) {
                        id_column = rs.getInt(0)
                        Text1.setText(rs.getString(1))
                        Text2.setText(rs.getString(2))
                    }
                }
            }
            Button3.setOnClickListener {
                //  if(rs?.moveToLast()!!) {
                var cv = ContentValues()
                cv.put(Country, Text1.text.toString())
                cv.put(Capital, Text2.text.toString())
                Toast.makeText(
                    this,
                    "You have insert Country = ${Text1.text.toString()} and Capital = ${Text2.text.toString()}",
                    Toast.LENGTH_LONG
                ).show()
                contentResolver.insert(uri, cv)
                rs?.requery()
                // }
            }
            Button4.setOnClickListener {
                if (rs?.moveToFirst()!!) {
                  //  var id = rs?.getInt(0)
                    var uri2 = Uri.withAppendedPath(uri, id_column.toString())
                    var cv = ContentValues()
                    var country = rs?.getString(1)
                    cv.put(Country, Text1.text.toString())
                    cv.put(Capital, Text2.text.toString())
                    Toast.makeText(
                        this,
                        "You have update Country = ${Text1.text.toString()} and Capital = ${Text2.text.toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                    contentResolver.update(
                        uri2,
                        cv,
                        "country =? and id =?",
                        arrayOf(country, id_column.toString())
                    )
                    //contentResolver.update(uri2,cv,"id=?", arrayOf(rs?.getInt(0).toString()))
                    //contentResolver.update(uri2, cv, null, null)
                    rs?.requery()
                } //rs?.moveToFirst()
            }

            Button5.setOnClickListener {
                //if (rs?.moveToFirst()!!) {

                    var id = rs?.getInt(0)
                    var uri2 = Uri.withAppendedPath(uri, id_column.toString())

                    Toast.makeText(
                        this,
                        "You have delete Country = ${rs?.getString(1)} and Capital = ${
                            rs?.getString(2)
                        }",
                        Toast.LENGTH_LONG
                    ).show()
                    contentResolver.delete(uri2, "Country =?", arrayOf(rs?.getString(1)))

                    rs?.requery()

              //  }
            }

            Button6.setOnClickListener {
                Text1.setText("")
                Text2.setText("")
                Text1.requestFocus()
            }
            Button7.setOnClickListener {
                if (rs != null) {
                    rs.moveToFirst()
                    Text1.setText("")
                    Text2.setText("")

                }

            }
        }

    }
}