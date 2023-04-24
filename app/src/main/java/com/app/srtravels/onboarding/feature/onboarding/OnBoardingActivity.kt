package com.app.srtravels.onboarding.feature.onboarding

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.srtravels.databinding.OnboardingActivityBinding

class OnBoardingActivity : AppCompatActivity() {

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = OnboardingActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
