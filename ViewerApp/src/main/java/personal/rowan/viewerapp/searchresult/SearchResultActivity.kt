package personal.rowan.viewerapp.searchresult

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import kotlinx.coroutines.launch
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
    private val adapter = SearchResultPagingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.srl_search)
        val emptyView = findViewById<TextView>(R.id.tv_empty)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_search)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        lifecycleScope.launch {
            viewModel.pagedLiveData().observe(this@SearchResultActivity) {
                adapter.submitData(lifecycle, it)
            }
        }
        adapter.addLoadStateListener {
            val source = it.source
            swipeRefreshLayout.isRefreshing = source.refresh is LoadState.Loading
            if (source.append is LoadState.NotLoading && source.refresh is LoadState.NotLoading && adapter.itemCount < 1) {
                emptyView.visibility = View.VISIBLE
            } else {
                emptyView.visibility = View.GONE
            }
            if (source.append is LoadState.Error || source.refresh is LoadState.Error) {
                Log.e(TAG, "Firestore paging error")
                showErrorAndFinish()
            }
        }

        @Suppress("DEPRECATION")
        val parameters = intent.getParcelableExtra<SearchParameter>(EXTRA_KEY_PARAMETERS) ?: {
            showErrorAndFinish()
        }
        swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }
        viewModel.getPagedFeed(parameters as SearchParameter)
    }

    private fun showErrorAndFinish() {
        Toast.makeText(this, R.string.search_result_error, Toast.LENGTH_SHORT).show()
        finish()
    }
}