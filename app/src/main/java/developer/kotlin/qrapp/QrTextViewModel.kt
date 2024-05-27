package developer.kotlin.qrapp

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class QrTextViewModel(application: Application) : ViewModel() {
    private var qrTextRepository: QrTextRepository = QrTextRepository(application)

    fun insertQr(qrText: QrText) = viewModelScope.launch {
        qrTextRepository.insertQr(qrText)
    }

    fun getAll(): LiveData<List<QrText>> = qrTextRepository.getAllQr()
    class QrTextModelFactory(private val application: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(QrTextViewModel::class.java)) {
                return QrTextViewModel(application) as T
            }

            throw IllegalArgumentException("Unable construct viewModel")
        }
    }
}