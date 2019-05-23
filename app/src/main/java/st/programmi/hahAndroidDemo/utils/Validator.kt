package st.programmi.hahAndroidDemo.utils

import androidx.lifecycle.LiveData


sealed class Validator {
    class Base(val item: LiveData<String>, val rules: List<Rule> = emptyList()) {
        fun validate():List<String> {
            return rules.filter { !it.check(item.value ?: "") }.map { it.message }
        }
    }
    class Rule (
        val check: (String?)->Boolean,
        var message: String
    )
}