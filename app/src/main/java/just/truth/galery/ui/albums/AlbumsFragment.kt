package just.truth.galery.ui.albums

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowMetrics
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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