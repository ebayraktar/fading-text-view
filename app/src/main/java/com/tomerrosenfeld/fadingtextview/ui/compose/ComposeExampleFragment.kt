package com.tomerrosenfeld.fadingtextview.ui.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.NoOpUpdate
import androidx.fragment.app.Fragment
import com.tomer.fadingtextview.FadingTextView
import com.tomerrosenfeld.fadingtextview.R
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.isActive
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Created by emrebayraktar on 28,December,2023
 */
class ComposeExampleFragment : Fragment() {
    private val jokes = intArrayOf(R.array.examples_1, R.array.examples_2, R.array.examples_3)
    private val defaultValue = 2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                var timeout: Duration by remember {
                    mutableStateOf(defaultValue.seconds)
                }
                MaterialTheme {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        FadingTextView { view ->
                            with(view) {
                                setTexts(jokes[Random.nextInt(jokes.size)])
                                setTimeout(timeout)
                                forceRefresh()
                            }
                        }
                        DiscreteSeekBar { view ->
                            with(view) {
                                max = 20
                                min = 1
                                progress = defaultValue
                                setOnProgressChangeListener(object : DiscreteSeekBar.OnProgressChangeListener {
                                    override fun onProgressChanged(seekBar: DiscreteSeekBar?, value: Int, fromUser: Boolean) {
                                        timeout = value.seconds
                                    }

                                    override fun onStartTrackingTouch(seekBar: DiscreteSeekBar?) = Unit

                                    override fun onStopTrackingTouch(seekBar: DiscreteSeekBar?) = Unit

                                })
                            }
                        }
                    }
                }

            }
        }
        return root
    }
}

@Composable
private fun FadingTextView(update: (view: FadingTextView) -> Unit = NoOpUpdate) {
    var fadingTextView: FadingTextView? = null
    AndroidView(
        factory = { context ->
            FadingTextView(context)
        },
        update = { view ->
            fadingTextView = view
            update(view)
        },
    )

    // This launch effect invalidates view every frame. This behaviour supports fading animation.
    LaunchedEffect(Unit) {
        var lastFrame = 0L
        while (isActive) {
            val nextFrame = awaitFrame() / 100_000L
            if (lastFrame != 0L) {
                fadingTextView?.invalidate()
            }
            lastFrame = nextFrame
        }
    }
}

@Composable
private fun DiscreteSeekBar(update: (view: DiscreteSeekBar) -> Unit = NoOpUpdate) {
    AndroidView(
        factory = { context ->
            DiscreteSeekBar(context)
        },
        update = update,
    )
}