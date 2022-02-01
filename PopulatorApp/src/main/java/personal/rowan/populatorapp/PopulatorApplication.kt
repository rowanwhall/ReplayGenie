package personal.rowan.populatorapp

import android.app.Application
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Rowan Hall
 */

@HiltAndroidApp
class PopulatorApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Firebase.auth.signInAnonymously()
    }
}