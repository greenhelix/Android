package com.google.field_ingresshelper;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    // 구글맵 참조변수 선언
    private GoogleMap gMap ;

    public Intent intent;
    public ArrayList<String> bmkPinList = new ArrayList<>();
    public ArrayList<String> polygonDList = new ArrayList<>();
    public ArrayList<String> polylineDList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        supportMapFragment.getMapAsync(this);

        FloatingActionButton fab = findViewById(R.id.btn_menu1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(getApplicationContext(), InputJSON.class);
                startActivity(intents);
            }
        });

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;                                                                           //구글 맵 객체를 불러온다.
        gMap.setPadding(0,0,0,500);                                               // 줌버튼 위치 변경 left top right bottom

        UiSettings gMapSetting = gMap.getUiSettings();                                              // 구글 맵 기본 세팅
        gMapSetting.setZoomControlsEnabled(true);                                                   // 줌 버튼 생성
        gMapSetting.setRotateGesturesEnabled(true);                                                 // 화면 회전 가능
        gMapSetting.setCompassEnabled(true);                                                        // 회전시 나침반버튼 생성하여 누르면 정북방향

        LatLng myPosition = new LatLng(37.657668, 127.124454);                             // 위치 지정

        MarkerOptions markerOptions = new MarkerOptions();                                          // 맵에 표시할 마커 설정
        markerOptions.position(myPosition).title("내 위치");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));//기본마커 색상 설정: blue

        gMap.addMarker(markerOptions);                                                              // 마커 생성

        intent = getIntent();                                                                       // 여러개의 마커를 올리기 메서드 및 다른 액티비티 위치 값 가져와서 올리기
        bmkPinList = intent.getStringArrayListExtra("position");
        Log.d("data","마커>>>>>>>>>>>>>>"+bmkPinList);
        polygonDList = intent.getStringArrayListExtra("polygon");
        polylineDList = intent.getStringArrayListExtra("polyline");
        Log.d("data", "폴리곤  >>>>"+polygonDList);
        Log.d("data", "폴리라인>>>>"+polylineDList);

        if(bmkPinList != null){
            double[][] markers = bookPos(bmkPinList);

                   for(int i  = 0; i<markers.length; i++){
                       MarkerOptions markerOptions2 = new MarkerOptions();
                       markerOptions2.position(new LatLng(markers[i][0],markers[i][1])).title("북마크"+(i+1));
                       gMap.addMarker(markerOptions2);
                   }
        }

        if(polygonDList != null){
            double[][] polygon = bookPos(polygonDList);

                for(int i = 0; i<polygon.length; i+=3){
                    PolygonOptions polygonOptions = new PolygonOptions().fillColor(Color.argb(80, 50, 0, 250));
                    polygonOptions.add(
                        new LatLng(polygon[i][0], polygon[i][1]),
                        new LatLng(polygon[i+1][0], polygon[i+1][1]),
                        new LatLng(polygon[i+2][0], polygon[i+2][1])
                    );
                    Polygon gon = googleMap.addPolygon(polygonOptions);
                }
        }

        if(polylineDList != null){
            double[][] polyline = bookPos(polylineDList);

                for(int i = 0; i<polyline.length; i+=2){
                    PolylineOptions polylineOptions = new PolylineOptions().color(Color.BLUE);
                    polylineOptions.add(
                            new LatLng(polyline[i][0], polyline[i][1]),
                            new LatLng(polyline[i+1][0], polyline[i+1][1])
                    );
                    Polyline line = googleMap.addPolyline(polylineOptions);
                }
        }


        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 16));                     // 화면 위치도 내 위치로 이동--> newLatLngZoom (위치, 줌레벨) //  newLatLng(위치)

    }

    /* 북마크 파싱 끝난것을 지도에 띄우기 위해 Convert해주는 메서드 */
    public double[][] bookPos(ArrayList<String> portalPositions) {
        Log.e("CHECK", "CHECK SIZE >>>>>>>>>>> "+portalPositions.size() );
        double[][] result = new double[portalPositions.size()][2];
        for (int i = 0; i < portalPositions.size(); i++) {
            String slice = portalPositions.get(i);
            String[] split = slice.split(",");
            result[i][0] = Double.parseDouble(split[0]);
            result[i][1] = Double.parseDouble(split[1]);
        }
        return result;
    }

}

