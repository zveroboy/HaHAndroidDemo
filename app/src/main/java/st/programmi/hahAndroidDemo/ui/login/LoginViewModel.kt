package st.programmi.hahAndroidDemo.ui.login

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import st.programmi.hahAndroidDemo.R
import st.programmi.hahAndroidDemo.data.Repository
import st.programmi.hahAndroidDemo.model.YandexWeather
import st.programmi.hahAndroidDemo.utils.Event
import st.programmi.hahAndroidDemo.utils.Validator
import java.lang.Exception

class LoginViewModel(application: Application) : AndroidViewModel(application){
    private val localApplication = application
    private val repository = Repository()
    val isLoading = MutableLiveData<Boolean>().apply { value = false }
    var lastResponse = MutableLiveData<YandexWeather.Json>()
    val email = MutableLiveData<String>()
    val emailError = MutableLiveData<String>()
    private var emailValidator = Validator.Base(email, listOf(
        Validator.Rule({ !it.isNullOrBlank() }, application.getString(R.string.error_field_required)),
        Validator.Rule({ Patterns.EMAIL_ADDRESS.matcher(it).matches() }, application.getString(R.string.error_email_format))
    ))
    val password = MutableLiveData<String>()
    val passwordHelperText = MutableLiveData<String>()
    val passwordError = MutableLiveData<String>()
    private val passwordMinLength = 6
    private val lowerCaseLetterRegex = """[a-z]""".toRegex()
    private val upperCaseLetterRegex = """[A-Z]""".toRegex()
    private val digitRegex = """\d""".toRegex()
    private var passwordValidator = Validator.Base(password, listOf(
        Validator.Rule({ !it.isNullOrBlank() }, application.getString(R.string.error_field_required)),
        Validator.Rule({ it!!.length >= passwordMinLength }, application.getString(R.string.error_field_min_length, passwordMinLength)),
        Validator.Rule({ lowerCaseLetterRegex.containsMatchIn(it!!) }, application.getString(R.string.error_field_require_lower_case_letter)),
        Validator.Rule({ upperCaseLetterRegex.containsMatchIn(it!!) }, application.getString(R.string.error_field_require_upper_case_letter)),
        Validator.Rule({ digitRegex.containsMatchIn(it!!) }, application.getString(R.string.error_field_require_digit))
    ))
    val focusChanged = MutableLiveData<Event<Pair<View, Boolean>>>()
    val focusListener = View.OnFocusChangeListener{ v, hasFocus ->
        focusChanged.postValue(Event((v to hasFocus)))
    }
    val passwordEndIconClickListener = View.OnClickListener {
        passwordHelperText.value = if(passwordHelperText.value.isNullOrBlank()){
            application.getString(R.string.password_helper)
        }else{
            ""
        }
    }
    fun submit(view: View){
        val validationErrors = validate()
        if(validationErrors.isEmpty()){
            viewModelScope.launch{
                isLoading.postValue(true)
                try {
                    lastResponse.postValue(repository.getWeather())
                }catch(e:Exception){
                    Toast.makeText(localApplication, e.toString(), Toast.LENGTH_LONG).show()
                }
                isLoading.postValue(false)
            }
        }
    }
    private fun validate(): List<String>{
        val emailResult = emailValidator.validate()
        val passwordResult = passwordValidator.validate()
        emailError.postValue(if(emailResult.isEmpty()) null else emailResult.joinToString(",\n"))
        passwordError.postValue(if(passwordResult.isEmpty()) null else passwordResult.joinToString(",\n"))
        return emailResult + passwordResult
//        return emptyList()
    }
    companion object {
        const val TAG = "LoginViewModel"
    }
}
