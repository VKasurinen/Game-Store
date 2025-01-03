import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.URL

fun resizeBitmapFromUrl(imageUrl: String, targetWidth: Int, targetHeight: Int): Bitmap? {
    val url = URL(imageUrl)
    val originalBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
    return Bitmap.createScaledBitmap(originalBitmap, targetWidth, targetHeight, true)
}