package io.lamart.aholdart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class AholdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

    }

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory =
        AholdViewModelProviderFactory(
            defaultFactory = super.getDefaultViewModelProviderFactory(),
            getLogic = { aholdApplication.logic }
        )

}