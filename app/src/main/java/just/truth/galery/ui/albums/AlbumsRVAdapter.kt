package just.truth.galery.ui.albums

import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import just.truth.galery.R

class AlbumsRVAdapter(val width : Int) : RecyclerView.Adapter<AlbumsRVAdapter.AlbumsHolder>() {
    val arr = arrayOf(
        R.drawable.i0,
        R.drawable.i1,
        R.drawable.i2,
        R.drawable.i3,
        R.drawable.i4,
        R.drawable.i5,
        R.drawable.i6,
        R.drawable.i6
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.albums_item, parent, false)
        return AlbumsHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumsHolder, position: Int) {
        holder.imageV.setImageDrawable(holder.imageV.context.getDrawable(arr[position]))
        holder.imageV.layoutParams.height = width / 2
    }

    override fun getItemCount(): Int = arr.size

    class AlbumsHolder (view: View) : RecyclerView.ViewHolder(view) {
        val imageV : ImageView = view.findViewById(R.id.albums_IMG_item)
    }
}
