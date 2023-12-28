package com.tomerrosenfeld.fadingtextview.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tomerrosenfeld.fadingtextview.databinding.FragmentXmlExampleBinding
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar
import kotlin.time.Duration.Companion.seconds

class XmlExampleFragment : Fragment() {

    private var _binding: FragmentXmlExampleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentXmlExampleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.timeoutBar.setOnProgressChangeListener(object : DiscreteSeekBar.OnProgressChangeListener {
            override fun onProgressChanged(seekBar: DiscreteSeekBar?, value: Int, fromUser: Boolean) {
                with(binding.fadingTextView) {
                    setTimeout(value.seconds)
                    forceRefresh()
                }
            }

            override fun onStartTrackingTouch(seekBar: DiscreteSeekBar?) = Unit

            override fun onStopTrackingTouch(seekBar: DiscreteSeekBar?) = Unit

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}