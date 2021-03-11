package greenhelix.androidproject.calculator.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    private MutableLiveData<String> settingText;

    public SettingsViewModel() {
        settingText = new MutableLiveData<>();
        settingText.setValue("Setting Here");
    }

    public LiveData<String> getText() {
        return settingText;
    }
}
