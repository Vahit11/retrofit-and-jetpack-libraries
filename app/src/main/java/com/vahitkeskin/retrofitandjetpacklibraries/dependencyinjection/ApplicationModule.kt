package com.vahitkeskin.retrofitandjetpacklibraries.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vahitkeskin.retrofitandjetpacklibraries.R
import com.vahitkeskin.retrofitandjetpacklibraries.api.RetrofitAPI
import com.vahitkeskin.retrofitandjetpacklibraries.repository.ArtImageRepository
import com.vahitkeskin.retrofitandjetpacklibraries.repository.ArtImageRepositoryInterface
import com.vahitkeskin.retrofitandjetpacklibraries.roomdb.ArtDao
import com.vahitkeskin.retrofitandjetpacklibraries.roomdb.ArtImageDatabase
import com.vahitkeskin.retrofitandjetpacklibraries.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun injectionRoomData(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ArtImageDatabase::class.java,
        "ArtBookDB"
    ).build()

    @Singleton
    @Provides
    fun injectionDao(database: ArtImageDatabase) = database.artDao()

    @Singleton
    @Provides
    fun injectionRetrofitAPI(): RetrofitAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitAPI::class.java)
    }

    @Singleton
    @Provides
    fun injectionNormalRepository(dao: ArtDao, api: RetrofitAPI) =
        ArtImageRepository(dao, api) as ArtImageRepositoryInterface

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.vhticon)
                .error(R.drawable.vhticon)
        )
}