package greenhelix.androidproject.calculator;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import greenhelix.androidproject.calculator.ui.calculator.CalculatorFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavView = findViewById(R.id.bottomNav_view);

        // 이부분 조심. 무조건 같아야함   id 값이 menu.xml 값이랑 겹치지 않으면 메뉴를 눌러도 이동이 안됨. !!!!
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_calculator, R.id.navigation_list, R.id.navigation_settings)
                .build();
        //메인 액티비티에 있는 프래그먼트 소환
        NavController navController = Navigation.findNavController(this,R.id.bottomNav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController,appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavView, navController);


    }



}
