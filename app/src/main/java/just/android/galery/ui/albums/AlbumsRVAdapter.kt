package just.android.galery.ui.albums

import android.net.Uri
import android.os.Build
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import just.android.galery.R

class AlbumsRVAdapter(val width : Int, val imageList : ArrayList<Uri>) : RecyclerView.Adapter<AlbumsRVAdapter.AlbumsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.albums_item, parent, false)
        return AlbumsHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: AlbumsHolder, position: Int) {
        val t = holder.imageV.context.contentResolver.loadThumbnail(imageList[position], Size(360, 360), null)
        holder.imageV.setImageBitmap(t)
        holder.imageV.layoutParams.height = width / 2
    }

    override fun getItemCount(): Int = imageList.size

    class AlbumsHolder (view: View) : RecyclerView.ViewHolder(view) {
        val imageV : ImageView = view.findViewById(R.id.albums_IMG_item)
    }
}
