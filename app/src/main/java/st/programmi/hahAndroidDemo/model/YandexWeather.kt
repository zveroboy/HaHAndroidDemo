package st.programmi.hahAndroidDemo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class YandexWeather {

    @Parcelize
    data class Fact (
        var temp: Int
    ) : Parcelable
    @Parcelize
    data class Json (
        var fact: Fact
    ) : Parcelable

}