package just.android.galery.utils

import android.net.Uri

class ImageData (
    val id : Long,
    val name : String,
    val size : String,
    val date : String?,
    val width : Int,
    val height : Int,
    val contentUri : Uri
) {}