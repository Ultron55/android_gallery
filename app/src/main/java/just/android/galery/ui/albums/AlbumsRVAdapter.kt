package just.android.galery.ui.albums

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import just.android.galery.LLL
import just.android.galery.R
import just.android.galery.utils.ImageData
import just.android.galery.utils.Numbers
import kotlin.math.abs


class AlbumsRVAdapter(val drawablenull: Drawable, val imageList: ArrayList<ImageData>)
    : RecyclerView.Adapter<AlbumsRVAdapter.AlbumsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.albums_item, parent, false)
        return AlbumsHolder(view)
    }

    var lastposition = 0
    var maxbinding = Numbers.columncount * Numbers.columncount

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: AlbumsHolder, position: Int) {
        val handler  = Handler(holder.imageV.context.mainLooper)
        holder.imageV.setImageDrawable(drawablenull)
        handler.post { holder.imageV.layoutParams.height = holder.imageV.width }
        handler.postDelayed( {
            if (maxbinding == Numbers.columncount * 4 && lastposition != 0)
                maxbinding = lastposition + Numbers.columncount * 2
            LLL("p $position lp $lastposition ${abs(position - lastposition) > maxbinding}")
            if (abs(position - lastposition) > maxbinding) return@postDelayed
            Glide.with(holder.imageV.context)
                .load(imageList[position].contentUri)
                .thumbnail(
                    Glide.with(holder.imageV.context)
                        .load(imageList[position].contentUri)
                )
                .placeholder(R.drawable.draw_null)
                .into(holder.imageV)
        }, 500)
        lastposition = position
    }

    override fun getItemCount(): Int = imageList.size

    class AlbumsHolder (view: View) : ViewHolder(view) {
        val imageV : ImageView = view.findViewById(R.id.albums_IMG_item)
    }

    override fun onViewDetachedFromWindow(holder: AlbumsHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.clearAnimation()
    }
}
