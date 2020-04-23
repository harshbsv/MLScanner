package com.soloroid.textscan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class OutputText : AppCompatActivity() {

    lateinit var goBack: Button
    lateinit var editTextOutput: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_output_text)
        goBack=findViewById(R.id.goBack)
        editTextOutput=findViewById(R.id.editTextOutput)
        goBack.setOnClickListener {
            editTextOutput.setText("")
            val goHomeScreen=Intent(this,MainActivity::class.java)
            startActivity(goHomeScreen)
        }
        val receivedIntent=Intent()
        val str=receivedIntent.getStringExtra("read_text")
        editTextOutput.setText(str)
    }


}
