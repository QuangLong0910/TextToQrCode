package developer.kotlin.qrapp

import android.app.Application
import androidx.lifecycle.LiveData

class QrTextRepository (app:Application){
    private var qrTextDao : QrTextDao
    init {
        val qrTextDatabase : QrTextDatabase = QrTextDatabase.getInstance(app)
        qrTextDao = qrTextDatabase.getQrTextDao()
    }

    suspend fun insertQr(qrText: QrText) = qrTextDao.insertQrText(qrText)
    fun getAllQr(): LiveData<List<QrText>> = qrTextDao.getAll()

}