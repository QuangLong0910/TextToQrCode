package developer.kotlin.qrapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


    @Entity(tableName = "QrText")
    class QrText(var title: String,val imageData: ByteArray): Serializable {
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0
    }
