package just.truth.galery.ui.albums

import android.app.Activity
import android.content.ContentUris
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Artists.Albums
import android.util.DisplayMetrics
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowMetrics
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import just.truth.galery.LLL
import just.truth.galery.databinding.AlbumsFragmentBinding

class AlbumsFragment : Fragment() {


    private var _binding: AlbumsFragmentBinding? = null
    private val binding get() = _binding!!

    lateinit var adapter : AlbumsRVAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AlbumsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AlbumsRVAdapter(requireActivity().screenParams())
        binding.albumsRV.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.albumsRV.adapter = adapter
        binding.imageView.setOnClickListener { queryImageStorage() }
        LLL(mAlbumsList.size.toString())
    }

    val mAlbumsList = ArrayList<Album>()
    @RequiresApi(Build.VERSION_CODES.Q)
    fun queryImageStorage() {
        val imageProjection = arrayOf(
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media._ID
        )
        val imageSortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"
        val cursor = requireActivity().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            imageProjection,
            null,
            null,
            imageSortOrder
        )
        cursor.use {
            it?.let {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val nameColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                val sizeColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
                val dateColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)
                while (it.moveToNext()) {
                    val id = it.getLong(idColumn)
                    val name = it.getString(nameColumn)
                    val size = it.getString(sizeColumn)
                    val date = it.getString(dateColumn)
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    // add the URI to the list
                    // generate the thumbnail
                    val thumbnail = requireActivity().contentResolver.loadThumbnail(contentUri, Size(480, 480), null)
                    LLL("$name $size $date")
                    binding.imageView.setImageBitmap(thumbnail)
                }
            } ?: kotlin.run {
                Log.e("TAG", "Cursor is null!")
            }
        }
    }

    fun Activity.screenParams(): Int {
        val width =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                windowManager.currentWindowMetrics.bounds.width()
            else
            {
                val metrics = DisplayMetrics()
                @Suppress("DEPRECATION")
                windowManager.defaultDisplay.getMetrics(metrics)
                metrics.widthPixels
            }
        return width
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

