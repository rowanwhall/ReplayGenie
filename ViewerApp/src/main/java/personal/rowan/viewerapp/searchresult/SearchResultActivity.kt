package personal.rowan.viewerapp.searchresult

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import personal.rowan.sharedmodule.Resource
import personal.rowan.viewerapp.R
import personal.rowan.viewerapp.SearchParameter

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchResultActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_KEY_PARAMETERS = "EXTRA_KEY_PARAMETERS"
        private const val TAG = "SearchResultsActivity"
    }

    private val viewModel: SearchResultViewModel by viewModels()
    private val adapter: SearchResultAdapter = SearchResultAdapter(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        val emptyView = findViewById<TextView>(R.id.tv_empty)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_search)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        viewModel.liveData().observe(this) {
            when (it) {
                is Resource.Success -> {
                    val data = it.data!!
                    adapter.setData(data)
                    emptyView.visibility = if (data.items.isEmpty()) View.VISIBLE else View.GONE
                }
                is Resource.Error -> {
                    Log.e(TAG, it.message ?: "No error message")
                    showErrorAndFinish()
                }
                is Resource.Loading -> {
                    Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
                }
            }
        }

        @Suppress("DEPRECATION")
        val parameters = intent.getParcelableExtra<SearchParameter>(EXTRA_KEY_PARAMETERS) ?: {
            showErrorAndFinish()
        }
        viewModel.getReplays((parameters as SearchParameter))
    }

    private fun showErrorAndFinish() {
        Toast.makeText(this, R.string.search_result_error, Toast.LENGTH_SHORT).show()
        finish()
    }
}