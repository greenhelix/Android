package greenhelix.androidproject.calculator.ui.calculator;

import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalculatorViewModel extends ViewModel {

    private MutableLiveData<String> calText;


    public CalculatorViewModel() {
        calText = new MutableLiveData<>();
        calText.setValue("Calculator is here");
    }
    public LiveData<String> getText(){
        return calText;
    }


}
