package developer.kotlin.qrapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QrTextDao {
    @Insert
    suspend fun insertQrText(qrText: QrText)
    @Query("select * from QrText")
    fun getAll(): LiveData<List<QrText>>

}