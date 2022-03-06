package io.lamart.aholdart

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.lamart.aholdart.main.MainFragment


class AholdActivity : AppCompatActivity() {

    private val viewModel: AholdViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            viewModel.initialize()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory =
        AholdViewModelProviderFactory(
            defaultFactory = super.getDefaultViewModelProviderFactory(),
            getLogic = { aholdApplication.logic }
        )

}