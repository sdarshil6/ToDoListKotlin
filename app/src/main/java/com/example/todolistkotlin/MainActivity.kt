package com.example.todolistkotlin

import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    lateinit var et_item:EditText
    lateinit var bt_add:Button
    lateinit var listView: ListView

    var itemList = ArrayList<String>()
    var fileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        et_item = findViewById(R.id.et_item)
        bt_add = findViewById(R.id.bt_add)
        listView = findViewById(R.id.list)

        itemList = fileHelper.readData(this)
        var arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList)
        listView.adapter = arrayAdapter

        bt_add.setOnClickListener {

            var itemName: String = et_item.text.toString()
            itemList.add(itemName)
            et_item.setText("")
            fileHelper.writeData(this@MainActivity, itemList)
            arrayAdapter.notifyDataSetChanged()

        }

        listView.setOnItemClickListener { adapterView, view, position, l ->
            var alert = AlertDialog.Builder(this)
            alert.setTitle("Delete")
            alert.setMessage("Do you want to delete the item from the list?")
            alert.setCancelable(false)
            alert.setNegativeButton("No", DialogInterface.OnClickListener{dialogInterface, i ->
                dialogInterface.cancel()
            })
            alert.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                itemList.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
                fileHelper.writeData(this@MainActivity, itemList)
            })
            alert.create().show()
        }


    }
}