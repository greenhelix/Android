package com.google.field_ingresshelper;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class DataShow extends AppCompatActivity {

    public ArrayList<String> portalName = new ArrayList<>(); //포털 이름 arrayList
    public ArrayList<String> portalPosition = new ArrayList<>(); //포털 좌표 arrayList
    public ArrayList<String> bkKeyList = new ArrayList<>(); // 북마크 아이디 키 arrayList
    public ArrayList<String> pKeyList = new ArrayList<>(); // 포털 키 arrayList

    public ArrayList<String> polygonList = new ArrayList<>(); //polygon좌표 arrayList
    public ArrayList<String> polylineList = new ArrayList<>(); //polyline좌표 arrayList

    public Intent sendIntent;
    public Intent inputIntent;
    public String bookMarkShow = "";
    public String drawShow = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_show);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recylclerView);
        //getApplicationContext아니여도 this로 대체 가능
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());                 //기본적으로 lineralayout vertical 로 들어가게 한다.

        recyclerView.setLayoutManager(linearLayoutManager);                                                         // recyclerView 에 linearlayout vertical성향을 반영.

                                                                         // 그리고 다시 recyclerView에 어댑터를 탑재 해준다.
        /* JSON 데이터에서 직접적으로 특정값을 까내서 가져오는 파트. */
        try{
                                                                                                                    //텍스트 입력은 반드시 txt형이고,  띄어쓰기가 없는 형태여야 한다.json형태면 못읽어옴
            inputIntent = getIntent();                                                                              // InputJSON.java에서 인텐트를 받아온다.

            bookMarkShow = inputIntent.getStringExtra("bookmark");                                           // 받아온 인텐트 안에, 설정한 Name을 가져온다.
            drawShow = inputIntent.getStringExtra("draw");
            Log.d("jsonGetSuccess", "북마크>>>>> "+bookMarkShow);
            Log.d("jsonGetSuccess", "DrawShow>>>>> "+drawShow);

            // 북마크
            if(bookMarkShow.length() != 0) {
                /*만약 더 중괄호가 여러개라면, 여기서 원하는 목표 지점까지 벗겨주면된다... 좀 번거로운데 아마 더 좋은 방법이 있을듯 */
                JSONObject bookPortal = new JSONObject(loadJSON(bookMarkShow));

                String portals = bookPortal.getString("portals");
                JSONObject portalObj = new JSONObject(portals);

                Iterator bkId = portalObj.keys();                                                                       //json데이터에서 key값만 저장잠시해준다.

                while(bkId.hasNext()){                                                                                  //폴더아이디를 가져와서 list에 담아준다.
                    String bkKey = bkId.next().toString();
                    Log.e("Key", "folderKey>>>>>>>>>>>>>>>>"+bkKey);
                    bkKeyList.add(bkKey);
                }
                String folderID= "";
                for(int bk = 0; bk <bkKeyList.size()-1; bk++){                                                          //반복문을 통해 각 폴더id 내에 데이터를 가져온다.

                    folderID = portalObj.getString(bkKeyList.get(bk));
                    JSONObject folderDetail = new JSONObject(folderID);

                    String bookMarks = folderDetail.getString("bkmrk");
                    JSONObject bookMarkID = new JSONObject(bookMarks);

                    Iterator id = bookMarkID.keys();                                                                    //bkmrk에 있는 키 값들을 잠시 iterator에 다 담는다.

                    while (id.hasNext()) {                                                                              //iterator에 담긴 키들을 쫘악 리스트에 넣어준다.
                    String pKey = id.next().toString();
                    Log.e("KEY", "bookMarkKey>>>>>>>"+pKey);
                    pKeyList.add(pKey);
                    }
                    String bookMarkKey ="";
                    for (int i = 0; i < pKeyList.size(); i++) {                                                         //key값이 다 다른 각 포탈들 안을 반복적으로 부셔서, 안에 필요한 값을 가져온다.

                        bookMarkKey = bookMarkID.getString(pKeyList.get(i));
                        JSONObject bookMarkDetail = new JSONObject(bookMarkKey);                                        // 이곳에 각 키안에 좌표와 포털이름 등이 하나씩들어있다.

                        portalName.add(bookMarkDetail.getString("label"));
                        portalPosition.add(bookMarkDetail.getString("latlng"));
                        Log.e("포털좌표", "포털좌표>>>>> " + portalPosition.get(i));
                        bookMarkKey = "";                                                                               // 북마크 키들도 새로 초기화 해준다.
                    }
                    pKeyList.clear();                                                                                   //한번 담겼다가 다시 초기화 해줘야 새로운키들이 담긴다.

                }
                Log.e("포털최종", "좌표모음>>>>> "+portalPosition);


            }else{
                bookMarkShow = "값이 없습니다.";
            }

            //그리기
            if(drawShow.length() != 0) {                                                                            //drawSHow가 null값이라면, 실행을 하지않는다.
                // draw
                JSONArray drawPortal = new JSONArray(loadJSON(drawShow));
                for (int j = 0; j < drawPortal.length(); j++) {
                    String drawName = drawPortal.getString(j);
                    Log.e("drawName", drawName);
                    JSONObject draw01 = new JSONObject(drawName);
                    String drawPos = draw01.getString("latLngs");
                    Log.e("drawPoss", drawPos);
                    JSONArray gonORline = new JSONArray(drawPos);

                     for(int z = 0; z <gonORline.length(); z++){                                                    //폴리곤/폴리라인 리스트에 좌표값 넣기
                        if(gonORline.length() == 3){
                            String drawing = gonORline.getString(z);
                            JSONObject oneSpot = new JSONObject(drawing);
                            String lat = oneSpot.getString("lat");
                            String lng = oneSpot.getString("lng");
                            polygonList.add(lat+","+lng);}
                        if(gonORline.length() == 2){
                            String drawing = gonORline.getString(z);
                            JSONObject oneSpot = new JSONObject(drawing);
                            String lat = oneSpot.getString("lat");
                            String lng = oneSpot.getString("lng");
                            polylineList.add(lat+","+lng);}
                    }
                }
                Log.d("polygon", polygonList+"");
                Log.d("polyline", polylineList+"");
            }else{
                drawShow = "값이 없습니다.";
            }


        }catch (JSONException je){
            Log.e("json", "json exception 발생~~~");
            je.printStackTrace();
        }
        /* CardView로 보여주기위해 작업이 된 어댑터를 가져와서 각 arrayLIst에 넣어준 값들을 반영해준다. */
        CustomAdapter jsonadapter = new CustomAdapter(DataShow.this, portalName,portalPosition);
        recyclerView.setAdapter(jsonadapter);

        /* 메뉴버튼으로 다시 메인 지도화면으로 이동. 동시에 인텐트를 같이 가져감 */
        FloatingActionButton fab2 = findViewById(R.id.btn_sendData);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIntent = new Intent(DataShow.this, MainActivity.class);
                sendIntent.putStringArrayListExtra("position", portalPosition);
                sendIntent.putStringArrayListExtra("polygon", polygonList);
                sendIntent.putStringArrayListExtra("polyline", polylineList);
                startActivity(sendIntent);                                                                          // sendIntent를 가지고 main액티비티를 실행시켜준다.
                finish();
            }
        });
    } //Oncreate END

    /* json파일 가져오는 파트. 파일이름을 넣는부분. */
    public String loadJSON(String a) {
        String json = null;
        String data = a;    Log.d("loadJSON", "loadJSON 정상가동>>>>> "+data);
        try {// 파일이 들어가는 부분. string값을 변환 시켜서 json 확인.
            InputStream is = new ByteArrayInputStream(data.getBytes());
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

}