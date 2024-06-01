package com.example.app250524

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.Writer
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.oned.EAN13Writer
import com.google.zxing.qrcode.QRCodeWriter

class GenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gen)

        findViewById<Button>(R.id.btnGenerateQR).setOnClickListener {
            val text = findViewById<EditText>(R.id.edContent).text.toString()
            val bitmap = generateCode(text, BarcodeFormat.QR_CODE)
            findViewById<ImageView>(R.id.imageView).setImageBitmap(bitmap)
        }
        findViewById<Button>(R.id.btnGenerateEAN13).setOnClickListener {
            val text = findViewById<EditText>(R.id.edContent).text.toString()
            if (text.length != 12) {
                Toast.makeText(this, "Размер EAN 13 должен быть 12 чисел",Toast.LENGTH_SHORT).show()
            } else {
                val bitmap = generateCode(text, BarcodeFormat.EAN_13)
                findViewById<ImageView>(R.id.imageView).setImageBitmap(bitmap)
            }
        }
    }

    fun generateCode(text: String, codeType: BarcodeFormat): Bitmap {
        var writer: Writer? = null
        if (codeType == BarcodeFormat.EAN_13){
            writer = EAN13Writer()
        } else if (codeType == BarcodeFormat.QR_CODE){
            writer = QRCodeWriter()
        }
        var bitmap: Bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.RGB_565)
        try {
            val bitMatrix: BitMatrix = writer!!.encode(text, codeType, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height

            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return bitmap
    }
}