package com.newagedevs.url_shortener.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat.startActivity
import com.newagedevs.url_shortener.data.model.UrlData
import java.net.URL


fun Context.showToast(message:String){
    Toast.makeText(this, message , Toast.LENGTH_SHORT).show()
}

fun UrlData.getUrl(): Pair<Boolean, String?> {
    val isShortUrl = this.originalUrl == this.expandedUrl
    val url = if (isShortUrl) {
        this.shortenedUrl
    } else {
        this.expandedUrl
    }
    return Pair(isShortUrl, url)
}


fun shareTheApp(context: Context) {
    ShareCompat.IntentBuilder.from((context as Activity)).setType("text/plain")
        .setChooserTitle("Chooser title")
        .setText("http://play.google.com/store/apps/details?id=" + context.packageName)
        .startChooser()
}

fun shareUrl(context: Context, url:String) {
    ShareCompat
        .IntentBuilder(context)
        .setType("text/plain")
        .setChooserTitle("Chooser title")
        .setText(url)
        .startChooser()
}

fun openMailApp(context: Context, subject: String, mail: Array<String>) {
    try {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, mail)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        startActivity(context, intent, null)
    } catch (ex: ActivityNotFoundException) {
        Toast.makeText(
            context,
            "There are no email app installed on your device",
            Toast.LENGTH_SHORT
        ).show()
    }
}

fun openAppStore(context: Context, link: String, error: (String?) -> Unit) {
    try {
        startActivity(context, Intent(Intent.ACTION_VIEW, Uri.parse(link)), null)
    } catch (e:Exception) {
        error(e.message)
    }
}

fun openWebPage(context: Context, url: String?, error: (String?) -> Unit) {
    try {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(context, browserIntent, null)
    } catch (e:Exception) {
        error(e.message)
    }
}

fun isValidUrl(url: String): Boolean {
    return try {
        URL(url).toURI()
        true
    } catch (e: Exception) {
        false
    }
}
