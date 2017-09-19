package org.androidtown.basiclist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 리스트사용하기.
 */

public class MainActivity extends AppCompatActivity {

    //1. 데이터를 정의
    ArrayList<String> data = new ArrayList<>();
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1. 데이터를 정의(100개의 가상 값을 담는다.)
        for (int i = 0; i < 100; i++) {
            data.add("임시값" + i);
        }


        //2. 데이터와 리스트뷰를 연결하는 아답터를 생성
        CustomAdapter adapter = new CustomAdapter(this, data); // data가 인수로 들어오면! 어댑터에서 data를 처리하는 함수 과정이 생김.
        //3. 아답터와 리스트뷰를 연결

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter); // setAdapter를 해준 순간 CustomeAdapter의 기능이 이곳에서 펼쳐진다는 것을 의미하면
        // 그런 후에는??

    }
}

// 기본 아답터를 상속받아서 구현
class CustomAdapter extends BaseAdapter {
    //데이터 저장소를 아답터 내부에 두는것이 컨트롤 하기 편하다.
    ArrayList<String> data;
    Context context;


    //생성자
    public CustomAdapter(Context context, ArrayList data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    } // 오버라이드된 함수이다. 그리고 data.size()를 리턴해준다. 그러나 언제? 어디서? 어떻게 사용되는지?
    // 예를 들어 getView같은 경우는 눈에 보이는 것이 있기 때문에, CustomAdapter클래스가 MainAcitivity에서 set될때
    //해당 시스템자원과 데이터를 받는 것을 알 수 있다. 그러나 getItem,getItemID, getCount는 ??? 크릭


    //현재 뿌려질 데이터를 리턴해준다.
    @Override
    public Object getItem(int position) {// 포지션이 호출되는 목록 아이템의 위치가 포지션으로 넘어옴.
        return data.get(position);
    }

    //뷰의 아이디를 리턴.
    @Override
    public long getItemId(int position) {
        return position; // 추후에는 다른 용도로 사용가능
    }

    // 목록에 나타나는 아이템 하나하나를 그려준다.
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {//리턴타입이 view
        // 레이아웃 인플레이터로 xml파일을 View객체로 변환
        Holder holder = null;

        System.out.println("call getView()===============" + position);


        if (view == null) { // 아이템 view를 재사용하기 위해서 null 체크를 한다.
            view = LayoutInflater
                    .from(context) // View convertView, ViewGroup viewGroup 중 하나가 null이 발생할 우려가 있음.
                    .inflate(R.layout.list_item, null); // 아이템을 null이라고 지정해주면 가장 쉽게 그 상태로 저장시킴
            //아이템이 최초 호출될 경우는 Holder에 위젯들을 담고,
            holder = new Holder(view); //생성자로 만들어서 뷰를 넣어준다.

            //홀더를 View에 붙여놓는다.
            view.setTag(holder); // 원래 라벨링을 하기위해서 만들었는데, 그냥 여기다가 홀더 객체를 던짐.

        } else {
            // View에 붙어 이는 홀더를 가져온다.
            holder = (Holder) view.getTag(); //tag는 원래 이 용도가 아니였음.
        }

        // 뷰안에 있는 텍스트뷰 위젯에 값을 입력한다.
        holder.textView.setText(data.get(position));//아이템뷰에 뿌려줄 데이터들의 포지션값을 얻음.//밖에 위치해야 차례대로 나옴.


        return view;
    }

    class Holder {
        TextView textView;

        public Holder(View view) {
           textView = (TextView) view.findViewById(R.id.textView);
           init();
        }

        public void init() {
            textView.setOnClickListener(new View.OnClickListener() {
                // 화면에 보여지는 View는
                // 기본적으로 자신이 속한 Component의 컨텍스트를 그대로 가지고 있다.
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(textView.getContext(), DetailActivity.class); //모든 뷰는 컨텍스트(시스템 자원을 공유할 수 있다.
                    intent.putExtra("valueKey", textView.getText());//edit텍스트는 꼭 toString해준다.text뷰는 생략이 가능?// 키 값 항상 체크 잘 해주기!!!!!/// 키, 밸류 형태로 보내짐
                    textView.getContext().startActivity(intent);//
                }
            });
        }
    }
}
