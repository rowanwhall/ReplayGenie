package personal.rowan.viewerapp.searchresult

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.*
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.parcelize.Parcelize
import personal.rowan.sharedmodule.Resource
import personal.rowan.viewerapp.R

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchResultActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_KEY_PARAMETERS = "EXTRA_KEY_PARAMETERS"
    }

    private val viewModel: SearchResultViewModel by viewModels()
    private val adapter: SearchResultAdapter = SearchResultAdapter(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_search)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        viewModel.liveData().observe(this) {
            when (it) {
                is Resource.Success -> {
                    adapter.setData(it.data!!)
                }
                is Resource.Error -> {
                    Toast.makeText(this, "error ${it.message}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val parameters = intent.getParcelableExtra<SearchResultParameter>(EXTRA_KEY_PARAMETERS) ?: {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            finish()
        }
        viewModel.getReplays((parameters as SearchResultParameter).selectedItems)
    }
}

@Parcelize
data class SearchResultParameter(val selectedItems: List<String>): Parcelable