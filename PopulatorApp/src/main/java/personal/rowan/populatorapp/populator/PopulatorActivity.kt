package personal.rowan.populatorapp.populator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import personal.rowan.sharedmodule.Resource
import personal.rowan.populatorapp.R

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PopulatorActivity : AppCompatActivity() {

    private val viewModel: PopulatorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_populator)

        val pageLabel = findViewById<TextView>(R.id.page_label)
        val statusLabel = findViewById<TextView>(R.id.status_label)
        val uploadButton = findViewById<Button>(R.id.upload_button)

        viewModel.liveData().observe(this) {
            when (it) {
                is Resource.Success -> {
                    val data = it.data!!
                    pageLabel.text = "Last page loaded\n\n" +
                            "replayDataCount: ${data.replayDataCount}\n" +
                            "logsToCheck: ${data.logsToCheck.size}\n" +
                            "dataToUpload: ${data.dataToUpload.size}\n" +
                            "successfulUploadCount: ${data.successfulUploadCount}\n" +
                            "page: ${data.currentPage}"
                    if (data.dataToUpload.isNotEmpty()) {
                        viewModel.changePage(data.currentPage + 1)
                        viewModel.populateBackend()
                    } else {
                        statusLabel.text = "All pages uploaded"
                        viewModel.changePage(1)
                        uploadButton.isEnabled = true
                    }
                }
                is Resource.Error -> {
                    statusLabel.text = it.message ?: "Generic Error"
                    uploadButton.isEnabled = true
                }
                is Resource.Loading -> {
                    statusLabel.text = "Loading..."
                    uploadButton.isEnabled = false
                }
            }
        }

        uploadButton.setOnClickListener {
            viewModel.populateBackend()
        }
    }
}