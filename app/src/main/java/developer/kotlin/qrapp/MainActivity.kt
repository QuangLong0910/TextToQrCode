package developer.kotlin.qrapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import developer.kotlin.qrapp.payment.PayActivity
import developer.kotlin.qrapp.payment.SharedPreferencesManager

import java.io.ByteArrayOutputStream

import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var textView: TextView
    private lateinit var buttonGenerateQR: Button
    private lateinit var imageViewQR: ImageView
    private lateinit var buttonExportImage : ImageView
    private lateinit var buttonHistory : ImageView
    private lateinit var buttonBuy : ImageView
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferencesManager = SharedPreferencesManager.getInstance(baseContext)

        editText = findViewById(R.id.editText)
        buttonGenerateQR = findViewById(R.id.buttonGenerateQR)
        imageViewQR = findViewById(R.id.imageViewQR)
        buttonExportImage = findViewById(R.id.buttonExportImage)
        textView = findViewById(R.id.sl)
        textView.text = sharedPreferencesManager.getLives().toString()
        buttonBuy = findViewById(R.id.buy)
        buttonBuy.setOnClickListener{
            val intent = Intent(this, PayActivity::class.java)
            startActivity(intent)
        }
        buttonHistory = findViewById(R.id.history)
        buttonHistory.setOnClickListener{
            val intent = Intent(this, History::class.java)
            startActivity(intent)
        }
        buttonGenerateQR.setOnClickListener {
            if(check()){
                val data = editText.text.toString().trim()
                if (data.isNotEmpty()) {
                    try {
                        val bitmap = generateQRCode(data)
                        imageViewQR.setImageBitmap(bitmap)
                        val byteArrayOutputStream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                        val byteArray = byteArrayOutputStream.toByteArray()

                        val qrText = QrText(
                            editText.text.toString(),byteArray
                        )
                        qrTextViewModel.insertQr(qrText)
                        sharedPreferencesManager.removeLife()
                        val newLives = sharedPreferencesManager.getLives()
                        textView.text = newLives.toString()

                    } catch (e: WriterException) {
                        e.printStackTrace()
                    }
                }
            } else {
                val intent = Intent(this, PayActivity::class.java)
                startActivity(intent)
            }


        }
        buttonExportImage.setOnClickListener {
            exportImage()
        }

    }
    private fun exportImage() {
        val bitmap = imageViewQR.drawable.toBitmap()


        val savedImagePath = saveImageToExternalStorage(bitmap)
        if (savedImagePath != null) {
            Toast.makeText(this, "Photo has been saved at $savedImagePath", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Unable to save photo", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveImageToExternalStorage(bitmap: Bitmap): String? {

         val file = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "QR_Image_${System.currentTimeMillis()}.png")
         val outputStream = FileOutputStream(file)
         bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
         outputStream.close()
         return file.absolutePath

    }

    @Throws(WriterException::class)
    private fun generateQRCode(data: String): Bitmap {
        val multiFormatWriter = MultiFormatWriter()
        val bitMatrix: BitMatrix = multiFormatWriter.encode(data, BarcodeFormat.QR_CODE, 500, 500)
        val barcodeEncoder = TextAndQr()
        return barcodeEncoder.createBitmap(bitMatrix)
    }
    private val qrTextViewModel : QrTextViewModel by lazy {
        val application = this.application
        ViewModelProvider(this,QrTextViewModel.QrTextModelFactory(application))[QrTextViewModel::class.java]
    }
    fun check(): Boolean {
        return sharedPreferencesManager.getLives() > 0 || sharedPreferencesManager.isPremium ==  true

    }

}
