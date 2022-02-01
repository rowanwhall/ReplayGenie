package personal.rowan.viewerapp

import android.app.Application
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Rowan Hall
 */

@HiltAndroidApp
class ViewerApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Firebase.auth.signInAnonymously()
    }
}