package just.truth.galery

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import just.truth.galery.databinding.BaseMainBinding

@AndroidEntryPoint
class BaseActivity : AppCompatActivity() {

    private lateinit var binding: BaseMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BaseMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.navView.setupWithNavController(findNavController(R.id.nav_host_base))
        checkPermission()
    }

    fun startDone()
    {
        if (ActivityCompat.checkSelfPermission(
                this,
                READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(
                this,
                READ_MEDIA_VIDEO
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED
        )
        {
            Log.d("LLL", "true")
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R &&
            Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU
        )
        {
            Log.d("LLL", "true")
        }
    }

    fun checkPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    READ_MEDIA_IMAGES
                ) != PackageManager.PERMISSION_GRANTED
                ||
                ActivityCompat.checkSelfPermission(
                    this,
                    READ_MEDIA_VIDEO
                ) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(READ_MEDIA_VIDEO, READ_MEDIA_IMAGES, READ_EXTERNAL_STORAGE),
                        1
                    )
                }
        }
        else
        {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("LLL", "checkprem")
                val permissions = arrayOf(
                    READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    1
                )
            }
        }
    }
    
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("LLL", "onRequestPermissionsResult")
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Log.d("LLL", "onRequestPermissionsResult R")
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Log.d("LLL", "onRequestPermissionsResult Rd")
                    startDone()
                }
            }
            else if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED)
            {
                Log.d("LLL", "onRequestPermissionsResult else")
                startDone()
            }
        } catch (e : Exception)
        {
            Log.e("onRequestPermissionsResult", e.message.toString())
            e.printStackTrace()
        }
    }
}