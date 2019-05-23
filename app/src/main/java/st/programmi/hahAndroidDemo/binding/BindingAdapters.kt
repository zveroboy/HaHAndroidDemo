package st.programmi.hahAndroidDemo.binding

import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("onFocus")
fun bindFocusChange(editText: EditText, onFocusChangeListener: View.OnFocusChangeListener) {
    if (editText.onFocusChangeListener == null) {
        editText.onFocusChangeListener = onFocusChangeListener
    }
}
@BindingAdapter("endIconOnClickListener")
fun bindFocusChange(textInputLayout: TextInputLayout, clickListener: View.OnClickListener) {
    textInputLayout.setEndIconOnClickListener(clickListener)
}