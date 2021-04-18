package com.example.cockandtail

import android.app.Application
import com.example.cockandtail.UIdata.APImodule
import dagger.BindsInstance
import dagger.Component
import org.jetbrains.annotations.NotNull
import javax.inject.Singleton

@Singleton
@Component(modules = [APImodule::class])
interface ApplicationComponent  {
    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        @NotNull
        fun application(
            /** -->  */
            application: MyApplication
        ): Builder

        fun build(): ApplicationComponent
    }


}

class MyApplication: Application() {
    val appComponent = DaggerApplicationComponent.builder().application(this).build()
}
