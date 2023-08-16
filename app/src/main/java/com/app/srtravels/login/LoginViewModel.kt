package com.app.srtravels.login

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.srtravels.login.model.UserLogin
import com.app.srtravels.util.LoginResult

class LoginViewModel : ViewModel() {
    var user: UserLogin = UserLogin()
    // val preferences = PreferenceManager.getDefaultSharedPreferences(application)
    var loginResult = MutableLiveData<Int>()
    var passwordValidation = MutableLiveData<Int>()
    var usernameValidation = MutableLiveData<Int>()
    var passwordConfirmation = ObservableField<String>()

    fun login() {
        validatePassword()
        validateUsername()

        if (isInputValid()) {
            checkIfUserLogged()
        }
    }

    fun signUp() {
        validateUsername()
        validatePassword()

        if (isInputValid() && isPasswordConfirmed()) { // check if data is valid and password typed correctly
            // write user data to shared pref
            //  writeUserToSharedPref()
            loginResult.value = LoginResult.SUCCESSFUL.value
        }
    }

  /*  private fun writeUserToSharedPref() {
        val editor = preferences.edit()
        editor.putString(PREF_USERNAME, user.username)
        editor.putString(PREF_USER_PASSWORD, user.password)
        editor.apply()
    }*/

    private fun checkIfUserLogged() {
       /* val loggedInUsername =
            preferences.getString(PREF_USERNAME, "Default")
        val loggedInPassword =
            preferences.getString(PREF_USER_PASSWORD, "")*/

        //  Log.d("mtag", "$loggedInUsername + $loggedInPassword")

        /*if (loggedInUsername != user.username.trim() || loggedInPassword != user.password.trim()) {
            loginResult.value = LoginResult.LOGIN_ERROR.value
            return
        }
*/
        loginResult.value = LoginResult.SUCCESSFUL.value
    }

    // fun isPasswordValid() = user.userPassword.length > 5

    private fun validatePassword() {
        when {
            user.password.isEmpty() -> passwordValidation.value = LoginResult.EMPTY_PASSWORD.value
            user.password.length < 5 -> passwordValidation.value = LoginResult.SHORT_PASSWORD.value
            else -> passwordValidation.value = LoginResult.OK.value
        }
    }

    private fun validateUsername() {
        when {
            user.username.isEmpty() -> usernameValidation.value = LoginResult.EMPTY_USERNAME.value
            user.username.length > 10 -> usernameValidation.value = LoginResult.LONG_USERNAME.value
            else -> usernameValidation.value = LoginResult.OK.value
        }
    }

    private fun isInputValid() =
        passwordValidation.value == LoginResult.OK.value && usernameValidation.value == LoginResult.OK.value

    private fun isPasswordConfirmed(): Boolean {
        if (passwordConfirmation.get() != user.password) {
            passwordValidation.value = LoginResult.PASSWORD_CONFIRMATION_ERROR.value
            return false
        }
        return true
    }
}
