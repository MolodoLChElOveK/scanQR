package com.example.app250524

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnGenerate = findViewById<Button>(R.id.btnGenerateQR).setOnClickListener {
            val intent = Intent(this, GenActivity::class.java)
            startActivity(intent)
        }
        val btnScan = findViewById<Button>(R.id.btnScanQR).setOnClickListener {
            val intent = Intent(this, ScanCameraActivity::class.java)
            startActivity(intent)
        }
        val btnDecode = findViewById<Button>(R.id.btnDecodeQR).setOnClickListener {
            val intent = Intent(this, SimpleScanActivity::class.java)
            startActivity(intent)
        }
    }
}