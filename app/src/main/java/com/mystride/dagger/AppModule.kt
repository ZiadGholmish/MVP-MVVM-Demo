package com.mystride.dagger

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.regions.Regions
import com.google.gson.Gson
import com.mystride.constatns.UserPoolConstants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(application: Application) {

    private var application: Application = application

    @Singleton
    @Provides
    fun provideApplication(): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideShared(): SharedPreferences {
        return application.getSharedPreferences("my_stride_shared", 0)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideUserPool(context: Context): CognitoUserPool {
        return CognitoUserPool(context, UserPoolConstants.userPoolId, UserPoolConstants.clientId,
                UserPoolConstants.clientSecret, UserPoolConstants.cognitoRegion)
    }

    @Singleton
    @Provides
    fun provideCachingCredentialsProvider(context: Context): CognitoCachingCredentialsProvider {
        return CognitoCachingCredentialsProvider(
                context,
                UserPoolConstants.identityId, // Identity pool ID
                Regions.US_EAST_2)
    }


}

