package com.example.app250524

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.LuminanceSource
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.Writer
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.oned.EAN13Writer
import com.google.zxing.qrcode.QRCodeReader
import com.google.zxing.qrcode.QRCodeWriter

class SimpleScanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_scan)

        findViewById<Button>(R.id.btnGenerateQR).setOnClickListener {
            val text = findViewById<EditText>(R.id.edContent).text.toString()
            val bitmap = generateCode(text, BarcodeFormat.QR_CODE)
            findViewById<ImageView>(R.id.imageView).setImageBitmap(bitmap)
        }
        findViewById<Button>(R.id.btnDecodeQR).setOnClickListener {
            val bitmap = findViewById<ImageView>(R.id.imageView).drawable.toBitmap()
            val text = decodeQR(bitmap)
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }
    }

    fun generateCode(text: String, codeType: BarcodeFormat): Bitmap {
        var writer: Writer? = null
        if (codeType == BarcodeFormat.EAN_13) {
            writer = EAN13Writer()
        } else if (codeType == BarcodeFormat.QR_CODE) {
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

    fun decodeQR(bitmap: Bitmap): String {
        val reader = QRCodeReader()
        return reader.decode(bitmapToBinaryBitmap(bitmap)).text
    }

    fun bitmapToBinaryBitmap(src: Bitmap): BinaryBitmap {
        val intArray = IntArray(src.width * src.height)
        src.getPixels(intArray, 0, src.width,0,0, src.width, src.height)
        val source: LuminanceSource = RGBLuminanceSource(src.width, src.height, intArray)
        return BinaryBitmap(HybridBinarizer(source))
    }

}