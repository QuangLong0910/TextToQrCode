package developer.kotlin.qrapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import developer.kotlin.qrapp.databinding.ActivityHistoryQrBinding

class History : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryQrBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryQrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = QrTextAdapter()
        binding.recyclerView.setHasFixedSize(true)
       binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        qrTextViewModel.getAll().observe(this, Observer {
            adapter.setData(it)
        })


    }
    private val qrTextViewModel : QrTextViewModel by lazy {
        val application = this.application
        ViewModelProvider(this,QrTextViewModel.QrTextModelFactory(application))[QrTextViewModel::class.java]
    }
}