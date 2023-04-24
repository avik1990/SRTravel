package com.app.srtravels.login.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class UserLogin : BaseObservable() {
    @get:Bindable
    var username: String = ""
        set(value) {
            field = value
            // notifyPropertyChanged(BR.username)
        }

    @get:Bindable
    var password: String = ""
        set(value) {
            field = value
            // notifyPropertyChanged(BR.password)
        }
}
