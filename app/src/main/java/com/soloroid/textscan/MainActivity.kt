package com.soloroid.textscan

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //lateinit var imageSelect: Button
    lateinit var outputLaunch: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        outputLaunch=findViewById(R.id.textRead)
    }

    //android:onClick="selectImage"
    fun selectImage(v: View){
        val intent= Intent()
        intent.type="image/*"
        intent.action= Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1 && resultCode== Activity.RESULT_OK)
            imageView.setImageURI(data!!.data)
    }

    fun startRecognising(v: View){
        if(imageView.drawable!=null){
            editTextOutput.setText("")
            v.isEnabled=true
            val bitmap=(imageView.drawable as BitmapDrawable).bitmap
            val image= FirebaseVisionImage.fromBitmap(bitmap)
            val detector= FirebaseVision.getInstance().onDeviceTextRecognizer
            detector.processImage(image).addOnSuccessListener { firebaseVisionText ->
                    v.isEnabled=true
                    processResultText(firebaseVisionText)
                }
                .addOnFailureListener {
                    v.isEnabled=true
                    editTextOutput.setText("Failed.")
                }
        }
        else{
            Toast.makeText(this,"Select an Image First", Toast.LENGTH_LONG).show()
        }
    }

    private fun processResultText(resultText: FirebaseVisionText){
        if(resultText.textBlocks.size==0){
            editTextOutput.setText("No text found.")
            return
        }
        for (block in resultText.textBlocks){
            val blockText=block.text
            editTextOutput.append(blockText+"\n")
        }
        //var readData=editTextOutput.text.toString()
        //val outputIntent=Intent(this, OutputText::class.java)
        //outputIntent.putExtra("read_text",readData)
    }
}
