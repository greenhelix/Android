package com.greenhelix.javadagger.androidDagger;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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

public class MainFragment extends Fragment {
    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    String activityName;

    @Inject
    Integer randomeNumber;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(getActivity() instanceof MainActivity){
            ((MainActivity) getActivity()).getComponent()
                    .mainFragmentComponentBuilder()
                    .setModule(new MainFragmentModule())
                    .setFragment(this)
                    .build()
                    .inject(this);
        }

        Log.d("MainFragment", activityName);
        Log.d("MainFragment", "randomNumber = "+randomeNumber);
    }
}
