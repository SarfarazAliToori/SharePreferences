package com.example.sharepreferencesandintent

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myListener()
    }

    fun myListener() {
        btn_Explict.setOnClickListener(this)
        btn_Implicit.setOnClickListener(this)
        btn_getDataSharePref.setOnClickListener(this)
        btn_openDialer.setOnClickListener(this)
        btn_storeDataSharePref.setOnClickListener(this)
        btn_clear.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {

        if (p0 != null) {
            when(p0?.id) {
                R.id.btn_Explict ->  explicitIntent()
                R.id.btn_Implicit -> implicitIntent()
                R.id.btn_openDialer -> openDialerUsingIntent()
                R.id.btn_storeDataSharePref -> storeDataInSharePreferences()
                R.id.btn_getDataSharePref -> readDataFromSharePreferences()
                R.id.btn_clear -> clearSharePreferencesData()
                else -> Toast.makeText(this, "Something Wrong..!", Toast.LENGTH_SHORT).show()

            }
        } else {
            Toast.makeText(this, "Null Value", Toast.LENGTH_SHORT).show()
        }
    }

    fun explicitIntent() {
        var str = ed_myEditText.text.toString().trim()
        if (str == "") {
            Toast.makeText(this, "Enter Something", Toast.LENGTH_SHORT).show()
            ed_myEditText.setError("Enter Something")
        } else {
            val intent = Intent(this,SecondActivity::class.java)
            intent.putExtra("myData",str)
            startActivity(intent)
            ed_myEditText.setText(" ")
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
        }
    }

    fun implicitIntent() {
        val intent = Intent(android.content.Intent.ACTION_SEND)
        intent.type = "Text/Plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hello World")
        startActivity(Intent.createChooser(intent, "Share via"))
    }

    fun openDialerUsingIntent() {
        val str = ed_myEditText.text.toString().trim()
        if (str == "") {
            ed_myEditText.setError("Enter Contact Number")
        } else {
            val intent = Intent(android.content.Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel: $str")
            startActivity(intent)
            ed_myEditText.setText(" ")
        }

    }

    fun storeDataInSharePreferences() {
        val str = ed_myEditText.text.toString().trim()
        if (str == "") {
            Toast.makeText(this, "Enter Your Data", Toast.LENGTH_SHORT).show()
            ed_myEditText.setError("Enter Something Bro")
        } else {
            val  sharePref = getSharedPreferences("myFile", MODE_PRIVATE)
            val editor = sharePref.edit()
            editor.putString("myKey", str)
            editor.apply()
            ed_myEditText.setText(" ")
            Toast.makeText(this, "Data is Save", Toast.LENGTH_SHORT).show()
        }
    }

    fun readDataFromSharePreferences() {
        val sharePref = getSharedPreferences("myFile", MODE_PRIVATE)
        textView.text = sharePref.getString("myKey", null)
        ed_myEditText.setText(" ")
    }

    fun clearSharePreferencesData() {
        val sharePre = getSharedPreferences("myFile", MODE_PRIVATE)
        val editor = sharePre.edit()
        editor.clear()
        editor.apply()
        ed_myEditText.setText(" ")
        textView.text= " "
        Toast.makeText(this, "Clear Data", Toast.LENGTH_SHORT).show()
    }


}