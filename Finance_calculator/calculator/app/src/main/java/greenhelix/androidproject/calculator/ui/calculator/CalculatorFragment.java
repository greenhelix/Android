package greenhelix.androidproject.calculator.ui.calculator;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import greenhelix.androidproject.calculator.MainActivity;
import greenhelix.androidproject.calculator.R;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculatorFragment extends Fragment {

    /**  -----------------------------선언부------------------------------------------  **/
    private CalculatorViewModel calculatorViewModel;
    // 숫자버튼 집합
    private int[] numericButtons = {R.id.calBtnZero, R.id.calBtnOne, R.id.calBtnTwo,
            R.id.calBtnThree, R.id.calBtnFour, R.id.calBtnFive, R.id.calBtnSix, R.id.calBtnSeven,
            R.id.calBtnEight, R.id.calBtnNine};
    // 연산 버튼 집합
    private int[] operatorButtons = {R.id.calBtnPlus, R.id.calBtnMinus, R.id.calBtnMultiple,
            R.id.calBtnSubstract, R.id.calBtnDivide, R.id.calBtnPlusMinus};
    // 텍스트뷰는 결과물을 보여주는 화면 선언
    private TextView calculatorView;
    // 숫자버튼이든 아니든 최근 눌러진 버튼이 있는지 없는지 확인
    private boolean lastNumeric;
    // 현재상태가 에러인지 나타내는 변수 선언
    private boolean stateError;
    // 이게 참이면, 다른 . 은 붙을 수가 없음. 소수점 의미
    private boolean lastDot;

    /// dialog해보는곳 선언부
    private static final String TAG_TEXT = "text";
    List<Map<String, Object>> dialogItemList;
    String[] text = {"+","-","(", ")","," , "log10(", "abs(", "max(","min(","avg("};
    /**  -------------------------------------------------------------------------   **/

    // oncreateview 를 통해서 실행한다.
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        calculatorViewModel =
                ViewModelProviders.of(this).get(CalculatorViewModel.class);


        final View root = inflater.inflate(R.layout.fragment_calculator, container, false);

        calculatorView = root.findViewById(R.id.show);

        //따로 메서드로 구분하는 것은 mainactivity에서 활용할떄이고
        //fragment에서는 그냥 oncreateview 안에다가 설정해주면 됨.
        // view를 따로 설정해서, 그것을 event설정해주면 된다.
        //onclicklistener를 형성해준다.
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼을 클릭하면 붙어주도록 설정을 해준다.
                Button button = (Button) v;
                if(stateError){
                    // 만약 지금 상태가 에러라면, 에러메세지로 대체한다.
                    calculatorView.setText(button.getText());
                    stateError = false;
                }else{
                    // 만약 그렇지않고 정상이라면, 타당한 표현이니 붙여준다.
                    calculatorView.append(button.getText());
                }
                // set the flag
                lastNumeric = true;
            }
        };
        //간단하게 아이디 값들을 불러주는 반복문이다. 잘 활용하면 좋고, 그냥 findviewbyId는 메인액티비티에서 되고, 여기서는
        // 따로 root에다가 findviewbyid 를 해줘야 한다.
        // 각 숫자버튼들에 리스너를 다 할당해준다.
        for(int id : numericButtons) {
            root.findViewById(id).setOnClickListener(listener);
        }

        // 메서드로 구분을 안하고 그냥 뷰를 하나 더 생성하여 그것을 적용해준듯.
        View.OnClickListener listener2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 상황이 에러라면 붙이지 않고,
                // 최근 입력값이 숫자인 경우에만, 연산자를 붙여준다.
                if(lastNumeric && !stateError){
                    Button button = (Button) v;
                    calculatorView.append(button.getText());
                    lastNumeric = false;
                    lastDot = false; // reset the dot flag 연산자 뒤에는 다른 수가 오므로 . 이 붙을수도 있음
                }
            }
        };
        // assign the listener to all the operator buttons
        for(int id : operatorButtons){
            root.findViewById(id).setOnClickListener(listener2);
        }

        //그냥 root 라는 뷰에다가 이것을 적용해준다.
        //Decimal point
        root.findViewById(R.id.calBtnDecimal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastNumeric && !stateError && !lastDot){
                    calculatorView.append(".");
                    lastNumeric = false;
                    //뒤에 더이상 . 이 붙을 수는 없다는 의미
                    lastDot = true;
                }
            }
        });
        //Clear button
        root.findViewById(R.id.calBtnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatorView.setText(""); // 화면을 클리어한다. AC버튼 이벤트 부분
                // 모든 상태와 규칙들을 초기화 해준다.
                lastNumeric = false;
                stateError = false;
                lastDot = false;
            }
        });

        //외부 메서드를 쓰려면 이렇게 하는게 맞는듯. 일단 root라는 뷰에다가 Findviewbyid해준뒤에 거기 안에다가
        //메서드를 포함시켜야 하느느듯.
        //Equal button
        root.findViewById(R.id.calBtnEqual).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                onEqual();
                // = 을 눌렀을때 답이 뭔지 확인해주는 메서드가 따로 필요해서 이렇게 만든듯.
            }
        });


    //  팝업메뉴 활용한것임.      팝업메뉴 활용한것임.
        root.findViewById(R.id.calBtnPlusMinus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 팝업메뉴리스트에서 어떤것을 선택하고 나서 이뤄질 이벤트에 대한 메서드임.
                showAlertDialog();
            }
        });

        // 리스트뷰에 띄어줄 항목들 넣어주는 반복문.
        dialogItemList = new ArrayList<>();
        for(int i = 0; i<text.length; i++){
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put(TAG_TEXT, text[i]);
            dialogItemList.add(itemMap);
        }



        return root;
    } /**onCreateView**/


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void onEqual(){
        // 현재 적힌 것이 에러라면, 더이상 아무것도 안되고,
        // 최근 입력값이 숫자일때만, 계산이 같은지 확인해준다.
        if(lastNumeric && !stateError){
            // 일단 적힌 것을 긁어온다. calculatorView있던 것을 getText로 가져온뒤, toString을 통해서
            // 스트링값으로 넣어준다.
            String txt = calculatorView.getText().toString();

            if(txt.contains("max") == true){
                Expression expression = new ExpressionBuilder(Double.toString(max(txt))).build();
                Double result = expression.evaluate();
                calculatorView.setText(Double.toString(result));
                lastDot = true;
            }else if(txt.contains("min") == true){
                Expression expression = new ExpressionBuilder(Double.toString(min(txt))).build();
                Double result = expression.evaluate();
                calculatorView.setText(Double.toString(result));
                lastDot = true;
            }else if(txt.contains("avg") == true) {
                Expression expression = new ExpressionBuilder(Double.toString(avg(txt))).build();
                Double result = expression.evaluate();
                calculatorView.setText(Double.toString(result));
                lastDot = true;
            }else {
                // 같은지는 expression 람다식으로 숫자형 계싼 api를 써서 사용함.
                // 이 계산과 동일하면, 리턴해주는 듯.
                // 익스프레션을 선언해준다.

                Expression expression = new ExpressionBuilder(txt).build();


                try {

                    // 계산기 결과 화면을 보여준다. double형으로 쇼해줌.
                    double result = expression.evaluate();
                    calculatorView.setText(Double.toString(result));
                    lastDot = true; // 결과값에 점을 포함시켜서 보여준다. 12.0 이런식으로 소수점도 같이 나옴.
                } catch (ArithmeticException ex) {
                    // 에러메세지를 표현해준다. 화면에 에러라고 뜨게함.
                    Log.d("Error!!", ">>>>>>>>>>>>>onEqual오류입니다.");
                    calculatorView.setText("Error");
                    stateError = true;
                    lastNumeric = false;
                }
            }
        }


    }/**onEqual**/


    /*최대값 구하는 메서드 */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public double max(String input){
        double result = 0;
        String maxString = input.replaceAll("[^0-9]+",",");
        Log.d("Error!!", ">>>>>>>>>>>>>maxString>>>>>>>>>>>>>>"+maxString);
        String show[] = maxString.split(",");
        Log.d("Array!!", ">>>>>>>>>>>>>SHOW>>>>>>>>>>>>>>"+Arrays.toString(show));
        //int[] max = Arrays.asList(show).stream().mapToInt(Integer::parseInt).toArray();

        int[] max = new int[show.length-1];

        for(int i = 0; i<show.length-1; i++){
               max[i] = Integer.parseInt(show[i+1]);
           }
        Log.d("Array!!", ">>>>>>>>>>>>maxArr>>>>>>>>>>>>>>>"+ Arrays.toString(max));
        result = Double.valueOf(max[0]);
        for(int a : max){if(a>result)result = a;}
     return result;
    }/**max()**/

    /*최소값 구하는 메서드 */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public double min(String input){
        double result = 0;
        String minString = input.replaceAll("[^0-9]+",",");
        Log.d("Error!!", ">>>>>>>>>>>>>minString>>>>>>>>>>>>>>"+minString);
        String show[] = minString.split(",");
        Log.d("Array!!", ">>>>>>>>>>>>>SHOW>>>>>>>>>>>>>>"+Arrays.toString(show));

        int[] min = new int[show.length-1];

        for(int i = 0; i<show.length-1; i++){
            min[i] = Integer.parseInt(show[i+1]);
        }
        Log.d("Array!!", ">>>>>>>>>>>>maxArr>>>>>>>>>>>>>>>"+ Arrays.toString(min));
        result = Double.valueOf(min[0]);
        for(int a : min){if(a<result)result = a;}
        return result;
    } /**min() **/

    /*평균값 구하는 메서드 */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public double avg(String input){
        double result = 0;
        String avgString = input.replaceAll("[^0-9]+",",");
        Log.d("AverageString",">>>>>>>>>>>avgString>>>>>>>>>>>>>>>>>"+avgString);
        String show[] = avgString.split(",");
        Log.d("Array!!",">>>>>>>>>>>>>>>>>>>SHOW>>>>>>>>>>>>>>>>>"+Arrays.toString(show));

        int[] avg = new int[show.length-1];
        for(int i = 0; i<show.length-1; i++){
            avg[i] = Integer.parseInt(show[i+1]);
            result += Double.valueOf(avg[i]);
        }
        result /= avg.length;
        Log.d("Array!!", ">>>>>>>>>>>>maxArr>>>>>>>>>>>>>>>"+ Arrays.toString(avg));
        return result ;
    }

    private void showAlertDialog(){

        /*아래 과정은 버튼시 시작되는 메서드로 활용하는 것인데, 클릭시 원하는 기호를 선택하라는 창을 뜨게 하는 부분이다.*/
        //일단 빌더를 올려주는데, 그 빌더가 올라가는 곳이 어딘지 다시 확인 필요 일단 상속으로 해봄
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // 인플레이터를 활용한다. 프래그에서 가능한듯, 인플레이터 생성후, 바로 다음줄에 뷰를 새성하여 불러오면 됨.
        LayoutInflater inflater = getLayoutInflater();
        // 뷰를 통해서, 이벤트시 띄어줄 화면 레이아웃을 선택.
        View view = inflater.inflate(R.layout.curious_dialog, null);
        // 그리고, 빌더에서 세트 뷰를 위의 뷰를 설정해주면 뜸.
        builder.setView(view);

        // listview를 불러오고 선언해줌.
        final ListView listView = view.findViewById(R.id.lv_curiousdialog_list);
        final AlertDialog dialog = builder.create();
        // simple adapter를 여기서 불러온다. 인자값이 다양함. 총 다섯가지
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                dialogItemList,
                R.layout.curious_dialog_row,
                new String[]{TAG_TEXT},
                new int[]{R.id.alertDialogItemTextView});

        /*여기서 부터는 리스트뷰가 보였을시, 그것의 항목들을 선택했을때 이뤄질 이벤트를 설정해주는 파트*/
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                calculatorView.append(text[position]);
                dialog.dismiss();
            }
        });
        //팝업 화면에서 취소 버튼 클릭시 창 닫히게 하는 버튼
        final Button cancel = view.findViewById(R.id.btn_dialog_cancel);
        cancel.findViewById(R.id.btn_dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }/**showAlertDialog**/

} /**CalculatorFragment**/