package st.programmi.hahAndroidDemo.ui.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import st.programmi.hahAndroidDemo.R
import st.programmi.hahAndroidDemo.databinding.LoginFragmentBinding
import st.programmi.hahAndroidDemo.extension.hideKeyboard
import st.programmi.hahAndroidDemo.extension.showSnackbar
import st.programmi.hahAndroidDemo.utils.EventObserver

class LoginFragment : androidx.fragment.app.Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = LoginFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.lastResponse.observe(viewLifecycleOwner, Observer {
            binding.mainLayout.showSnackbar(getString(R.string.city_weather, it.fact.temp), Snackbar.LENGTH_LONG)
        })
        viewModel.focusChanged.observe(this, EventObserver{(view, isFocused) ->
            if(!isFocused) {
                (activity as AppCompatActivity).hideKeyboard(view)
            }
        })
        return binding.root
    }
}
