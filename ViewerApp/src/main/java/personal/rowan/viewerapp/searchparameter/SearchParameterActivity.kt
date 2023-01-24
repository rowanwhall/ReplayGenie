package personal.rowan.viewerapp.searchparameter

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import personal.rowan.viewerapp.AUTOCOMPLETE_NAMES
import personal.rowan.viewerapp.EloParameter
import personal.rowan.viewerapp.FormatParameter
import personal.rowan.viewerapp.R
import personal.rowan.viewerapp.searchresult.SearchResultActivity

private const val MAX_SELECTION = 3

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
    private val adapter: SearchParameterAdapter = SearchParameterAdapter(listener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_parameter)

        val formatChips = findViewById<ChipGroup>(R.id.cg_format)
        val eloChips = findViewById<ChipGroup>(R.id.cg_elo)
        val selectedItemsRecycler = findViewById<RecyclerView>(R.id.rv_selected)
        val searchButton = findViewById<ExtendedFloatingActionButton>(R.id.btn_search)

        setupRecycler(selectedItemsRecycler)
        setupFormatChips(formatChips)
        setupEloChips(eloChips)
        setupSearchButton(searchButton)
        setupAutoComplete()

        fun bindToViewState(viewState: SearchParameterViewState) {
            adapter.submitList(viewState.selectedItems.values.toList())
            searchButton.isEnabled = viewState.selectedItems.isNotEmpty()
        }
        bindToViewState(viewModel.getValue())
        viewModel.liveData().observe(this) {
            bindToViewState(it)
        }
    }

    private fun setupRecycler(selectedItemsRecycler: RecyclerView) {
        selectedItemsRecycler.layoutManager = LinearLayoutManager(this)
        selectedItemsRecycler.adapter = adapter
    }

    private fun setupFormatChips(formatChips: ChipGroup) {
        formatChips.setOnCheckedStateChangeListener { _, checkedIds ->
            val selectedFormat = if (checkedIds.isEmpty()) {
                FormatParameter.SERIES_TWO
            } else {
                when (checkedIds[0]) {
                    R.id.chip_format_series2 -> FormatParameter.SERIES_TWO
                    R.id.chip_format_series1 -> FormatParameter.SERIES_ONE
                    else -> FormatParameter.SERIES_TWO
                }
            }
            viewModel.setFormat(selectedFormat)
        }
        formatChips.check(R.id.chip_format_series2)
    }

    private fun setupEloChips(eloChips: ChipGroup) {
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
    }

    private fun setupSearchButton(searchButton: ExtendedFloatingActionButton) {
        searchButton.setOnClickListener {
            val intent = Intent(this, SearchResultActivity::class.java)
            intent.putExtra(SearchResultActivity.EXTRA_KEY_PARAMETERS, viewModel.getParameters())
            startActivity(intent)
        }
    }

    private fun setupAutoComplete() {
        val editText = findViewById<AutoCompleteTextView>(R.id.et_search)
        val autoCompleteAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            AUTOCOMPLETE_NAMES
        )
        editText.setAdapter(autoCompleteAdapter)
        editText.setOnItemClickListener { _, _, _, _ ->
            if (adapter.itemCount >= MAX_SELECTION) {
                Toast.makeText(this, R.string.search_parameter_team_overflow_error, Toast.LENGTH_SHORT).show()
            } else {
                val selectedItem = editText.text.toString()
                viewModel.selectItem(selectedItem)
            }
            editText.text.clear()
        }
    }
}