package personal.rowan.showdownmetrics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val TAG = "Showdown_MainActivity"

class MainActivity : AppCompatActivity() {

    private val viewModel: PopulatorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Firebase.firestore

        val jsonService = RetrofitServiceFactory
            .createJsonRetrofitService(ShowdownService::class.java,
                "https://replay.pokemonshowdown.com/",
                OkHttpClient(),
                GsonBuilder().create())

        val scalarService = RetrofitServiceFactory
            .createScalarRetrofitService(ShowdownService::class.java,
                "https://replay.pokemonshowdown.com/",
                OkHttpClient())

        val call = jsonService.getReplays(page=2)
        call.enqueue(object : Callback<List<Replay>> {
            override fun onResponse(call: Call<List<Replay>>, response: Response<List<Replay>>) {
                val replays = response.body() ?: return
                replays.forEach {
                    parseAndUploadReplayData(it.id, scalarService, db)
                }
            }

            override fun onFailure(call: Call<List<Replay>>, t: Throwable) {
                Log.d(TAG, "replay list web call failure: ${t.localizedMessage}")
            }
        })
    }

    private fun parseAndUploadReplayData(replayId: String, scalarService: ShowdownService, db: FirebaseFirestore) {
        val call = scalarService.getReplayString(replayId)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val teams = ReplayParser().parseReplay(response.body()!!)
                val replayUrl = "https://replay.pokemonshowdown.com/$replayId"
                val replayData = hashMapOf(
                    "replay" to replayUrl,
                    "team1" to teams.first,
                    "team2" to teams.second
                )
                db.collection("replay-data")
                    .whereEqualTo("replay", replayUrl)
                    .get()
                    .addOnSuccessListener {
                        if (it.isEmpty) {
                            uploadReplayData(replayData, db)
                        }
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "failure to read")
                    }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(TAG, "replay item web call failure: ${t.localizedMessage}")
            }
        })
    }

    private fun uploadReplayData(replayData: HashMap<String, Any>, db: FirebaseFirestore) {
        db.collection("replay-data")
            .add(replayData)
            .addOnSuccessListener {
                Log.d(TAG, "upload success")
            }
            .addOnFailureListener {
                Log.d(TAG, "upload failure")
            }
    }
}