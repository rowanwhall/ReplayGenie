package personal.rowan.viewerapp.searchparameter

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import personal.rowan.viewerapp.*
import personal.rowan.viewerapp.searchresult.SearchResultActivity

private const val MAX_SELECTION = 1

/**
 * Created by Rowan Hall
 */
@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchParameterActivity : AppCompatActivity() {

    private val viewModel: SearchParameterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_parameter)

        val formatChips = findViewById<SingleChoiceChipGroup>(R.id.cg_format)
        val eloChips = findViewById<SingleChoiceChipGroup>(R.id.cg_elo)
        val selectedChips = findViewById<EntryChipGroup<SearchParameterItemViewState>>(R.id.cg_selected)
        val searchButton = findViewById<ExtendedFloatingActionButton>(R.id.btn_search)

        setupFormatChips(formatChips)
        setupEloChips(eloChips)
        setupSearchButton(searchButton)
        setupAutoComplete(selectedChips)

        fun bindToViewState(viewState: SearchParameterViewState) {
            formatChips.selectChoice(viewState.format)
            eloChips.selectChoice(viewState.eloParameter)
            selectedChips.setData(viewState.selectedItems.values.toList())
            searchButton.isEnabled = viewState.selectedItems.isNotEmpty()
        }
        bindToViewState(viewModel.getValue())
        viewModel.liveData().observe(this) {
            bindToViewState(it)
        }
    }

    private fun setupFormatChips(formatChips: SingleChoiceChipGroup) {
        val defaultFormat = FormatParameter.REGULATION_D
        formatChips.setOnCheckedStateChangeListener { _, checkedIds ->
            val selectedFormat = if (checkedIds.isEmpty()) {
                defaultFormat
            } else {
                when (checkedIds[0]) {
                    R.id.chip_format_regd -> FormatParameter.REGULATION_D
                    R.id.chip_format_regc -> FormatParameter.REGULATION_C
                    R.id.chip_format_series2 -> FormatParameter.SERIES_TWO
                    R.id.chip_format_series1 -> FormatParameter.SERIES_ONE
                    else -> defaultFormat
                }
            }
            viewModel.setFormat(selectedFormat)
        }
        viewModel.setFormat(defaultFormat)
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

    private fun setupAutoComplete(selectedChips: EntryChipGroup<SearchParameterItemViewState>) {
        selectedChips.setCloseListener(object : CloseListener<SearchParameterItemViewState> {
            override fun onClose(item: SearchParameterItemViewState) {
                viewModel.removeItem(item.item)
            }
        })
        val editText = findViewById<AutoCompleteTextView>(R.id.et_search)
        val autoCompleteAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            AUTOCOMPLETE_NAMES
        )
        editText.setAdapter(autoCompleteAdapter)
        editText.setOnItemClickListener { _, _, _, _ ->
            if (selectedChips.getCount() >= MAX_SELECTION) {
                // If we only allow one item, just clear the selected chips entirely
                if (MAX_SELECTION == 1) {
                    val selectedItem = editText.text.toString()
                    viewModel.selectItem(selectedItem, true)
                } else {
                    Toast.makeText(
                        this,
                        R.string.search_parameter_team_overflow_error,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                val selectedItem = editText.text.toString()
                viewModel.selectItem(selectedItem)
            }
            editText.text.clear()
        }
    }
}