package org.androidtown.basiclist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //인텐트를 통해 넘어온 값 꺼내기
        Intent intent = getIntent(); //startActiviy를 통해 넘어온 intent를 꺼낸다.
//        // 인텐트에서 값의 묶음인 번들을 꺼내고
//        Bundle bundle = intent.getExtras();
//        //번들에서 최종값을 꺼낸다. //클래스는 보낼 수 없다.//serialize, 클래스를 문서화 시키는 것.
//        String result = bundle.getString("valueKey");
        // 인텐트에서 바로 값을 꺼내기
        String result = intent.getStringExtra("valueKey");

        TextView textView =(TextView)findViewById(R.id.textView);
        textView.setText(result);
    }
}
