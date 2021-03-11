package greenhelix.androidproject.calculator.ui.listshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListShowViewModel extends ViewModel {
    private MutableLiveData<String> listText;

    public ListShowViewModel() {
        listText = new MutableLiveData<>();
        //mutablelivedata 의 값을 스트링으로 선언해둔뒤, 그 값을 setvalue로 텍스트값을 부여해줌.
        listText.setValue("LIST SHOW");
    }
    // setvalue 된  listText를 불러주는 메서드를 생성함.
    public LiveData<String> getText(){
        return listText;
    }
}
