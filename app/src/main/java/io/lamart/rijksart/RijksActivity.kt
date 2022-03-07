package io.lamart.rijksart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class RijksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

    }

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory =
        RijksViewModelProviderFactory(
            defaultFactory = super.getDefaultViewModelProviderFactory(),
            getLogic = { rijksApplication.logic }
        )

}