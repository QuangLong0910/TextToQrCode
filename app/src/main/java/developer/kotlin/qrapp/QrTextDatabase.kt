package developer.kotlin.qrapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [QrText::class], version = 1)
abstract class QrTextDatabase : RoomDatabase() {
    abstract fun getQrTextDao(): QrTextDao
    companion object {
        @Volatile
        private var instance: QrTextDatabase? = null
        fun getInstance(context: Context): QrTextDatabase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context, QrTextDatabase::class.java, "QrTextDatabase").build()
            }
            return instance!!
        }
    }

}