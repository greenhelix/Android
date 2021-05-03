package com.greenhelix.javadagger.androidDagger;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Random;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
class MainFragmentModule {
    @Provides
    @FragmentScope
    Integer provideInt(){
        return new Random().nextInt();
    }
}

