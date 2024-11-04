package me.s097t0r1.ktcast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import me.s097t0r1.core.mvi.base.BaseContainerActivity
import me.s097t0r1.core.mvi.base.host.HostViewModel
import me.s097t0r1.core.mvi.base.host.HostViewModelOwner
import me.s097t0r1.core.navigation.message.StartFlowMessage
import me.s097t0r1.core.ui_components.components.AlertSnackBar
import me.s097t0r1.core.ui_components.components.AlertSnackBarHost
import me.s097t0r1.ktcast.databinding.ActivityMainBinding
import me.s097t0r1.ktcast.mvi.MainSideEffect
import org.orbitmvi.orbit.compose.collectSideEffect

class MainActivity : BaseContainerActivity<ActivityMainBinding>(), HostViewModelOwner {

    override val viewModelFactory: ViewModelProvider.Factory = MainViewModelFactory()

    private lateinit var viewModel: MainViewModel

    override val containerId: Int by lazy { binding.container.id }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(HostViewModel::class.java) as MainViewModel
    }

    override fun onViewCreated(binding: ActivityMainBinding) {
        super.onViewCreated(binding)
        setListeners()
    }

    @Composable
    override fun Content() {

        val alertSnackBarHost = remember { AlertSnackBarHost() }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {

            AlertSnackBar(
                alertSnackBarHost,
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 12.dp)
                    .fillMaxWidth()
                    .height(64.dp)
            )
        }

        CollectSideEffects(alertSnackBarHost)
    }

    @Composable
    private fun CollectSideEffects(alertSnackBarHost: AlertSnackBarHost) {
        viewModel.collectSideEffect { sideEffect ->
            when (sideEffect) {
                is MainSideEffect.Alert -> alertSnackBarHost.show(
                    message = sideEffect.message,
                    alertType = sideEffect.alertType,
                    durationInMillis = 2000L
                )
                else -> {}
            }
        }
    }

    private fun setListeners() {
        binding.mtToolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun openLaunchScreen() {}

    override fun setupToolbar(): Toolbar = binding.mtToolbar

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean
    ): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater, parent, attachToParent)
    }

}
