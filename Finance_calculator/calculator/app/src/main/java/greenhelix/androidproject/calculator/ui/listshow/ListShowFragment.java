package greenhelix.androidproject.calculator.ui.listshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import greenhelix.androidproject.calculator.R;

public class ListShowFragment extends Fragment {
    private ListShowViewModel listShowViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                              ViewGroup container,  Bundle savedInstanceState){
        listShowViewModel = ViewModelProviders.of(this).get(ListShowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_listshow, container, false);
        final TextView textView = root.findViewById(R.id.text_listshow);
        listShowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(
                    @Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
