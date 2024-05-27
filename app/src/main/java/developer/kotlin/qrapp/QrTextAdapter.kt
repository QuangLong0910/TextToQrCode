package developer.kotlin.qrapp

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QrTextAdapter() : RecyclerView.Adapter<QrTextAdapter.ViewHolder>() {
    private var imageList: List<QrText> = listOf()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val title : TextView = itemView.findViewById(R.id.Title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemjob, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageEntity = imageList[position]
        val bitmap = BitmapFactory.decodeByteArray(imageEntity.imageData, 0, imageEntity.imageData.size)
        holder.imageView.setImageBitmap(bitmap)
        holder.title.text = imageList[position].title
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
    fun setData(qrText: List<QrText>) {
        this.imageList = qrText
        notifyDataSetChanged()
    }
}
