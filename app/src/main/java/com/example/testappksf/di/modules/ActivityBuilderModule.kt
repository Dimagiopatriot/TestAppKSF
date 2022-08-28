package com.example.testappksf.di.modules

import com.example.testappksf.di.scope.MainScope
import com.example.testappksf.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @MainScope
    @ContributesAndroidInjector(
        modules = [
            ViewModelsModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}