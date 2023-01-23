package personal.rowan.viewerapp.searchparameter

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import personal.rowan.viewerapp.EloParameter
import personal.rowan.viewerapp.R
import personal.rowan.viewerapp.searchresult.SearchResultActivity

/**
 * Created by Rowan Hall
 */
@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchParameterActivity : AppCompatActivity() {

    private val viewModel: SearchParameterViewModel by viewModels()
    private val listener = object : SearchParameterListener {
        override fun onItemRemoved(viewState: SearchParameterItemViewState) {
            viewModel.removeItem(viewState.item)
        }
    }
    private val adapter: SearchParameterAdapter = SearchParameterAdapter(listOf(), listener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_parameter)

        val eloChips = findViewById<ChipGroup>(R.id.cg_elo)
        eloChips.setOnCheckedStateChangeListener { _, checkedIds ->
            val selectedElo = if (checkedIds.isEmpty()) {
                null
            } else {
                when (checkedIds[0]) {
                    R.id.chip_elo_none -> EloParameter.NONE
                    R.id.chip_elo_low -> EloParameter.LOW
                    R.id.chip_elo_mid -> EloParameter.MID
                    R.id.chip_elo_high -> EloParameter.HIGH
                    else -> null
                }
            }
            viewModel.setElo(selectedElo)
        }
        val searchButton = findViewById<ExtendedFloatingActionButton>(R.id.btn_search)
        searchButton.setOnClickListener {
            navigateToSearchResults()
        }
        val selectedItemsRecycler = findViewById<RecyclerView>(R.id.rv_selected)
        selectedItemsRecycler.layoutManager = LinearLayoutManager(this)
        selectedItemsRecycler.adapter = adapter
        viewModel.liveData().observe(this) {
            adapter.setData(it)
        }

        setupAutoComplete()
    }

    private fun setupAutoComplete() {
        val editText = findViewById<AutoCompleteTextView>(R.id.et_search)
        // todo: load full list from web service or asset file
        val autoCompleteAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            listOf("Iron Hands", "Arcanine", "Gholdengo", "Palafin", "Pelipper", "Amoonguss")
        )
        editText.setAdapter(autoCompleteAdapter)
        editText.setOnItemClickListener { _, _, _, _ ->
            val selectedItem = editText.text.toString()
            viewModel.selectItem(selectedItem)
            editText.text.clear()
        }
    }

    private fun navigateToSearchResults() {
        val intent = Intent(this, SearchResultActivity::class.java)
        intent.putExtra(SearchResultActivity.EXTRA_KEY_PARAMETERS, viewModel.getParameters())
        startActivity(intent)
    }
}