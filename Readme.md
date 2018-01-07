# ADS04 Android

## 수업 내용
- ListView 학습
- Intent 학습

## Code Review

### MainActivity

```Java
/**
 * 리스트사용하기.
 */

public class MainActivity extends AppCompatActivity {

    //1. 데이터 및 리스트 뷰 정의
    ArrayList<String> data = new ArrayList<>();
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2. 데이터를 세팅(100개의 가상 값을 담는다.)
        for (int i = 0; i < 100; i++) {
            data.add("임시값" + i);
        }


        //2. 데이터와 리스트뷰를 연결하는 아답터를 생성
        CustomAdapter adapter = new CustomAdapter(this, data); // data가 인수로 들어오면! 어댑터에서 data를 처리하는 함수 과정이 생김.

        //3. 아답터와 리스트뷰를 연결
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

    }
}
```


### CustomAdapter

```Java
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
    }


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
                    .from(context)                            // View convertView, ViewGroup viewGroup 중 하나가 null이 발생할 우려가 있음.
                    .inflate(R.layout.list_item, null); // 아이템을 null이라고 지정해주면 가장 쉽게 그 상태로 저장시킴
                                                             //아이템이 최초 호출될 경우는 Holder에 위젯들을 담고,
            holder = new Holder(view);

            //홀더를 View에 붙여놓는다.
            view.setTag(holder); // 원래 라벨링을 하기위해서 만들었는데, 그냥 여기다가 홀더 객체를 던짐.

        } else {
            // View에 붙어 있는 홀더를 가져온다.
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
                    textView.getContext().startActivity(intent);
                }
            });
        }
    }
}
```

### DetailActivity
```Java
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

```

## 보충설명

![ListView](http://cfile5.uf.tistory.com/image/252EC849568CB81A26DEBB)

### 참고
안드로이드에서 UI 화면은 View와 ViewGroup을 사용하여 구성됩니다.
View는 사용자에게 어떤 내용을 보여주거나 입력을 받아들이기 위해 화면에 그려지는 기본 객체를 말합니다. TextView, Button, CheckBox 등이 대표적이죠.
반면에 ViewGroup은 이름 그대로 View들이 모인 Group을 말합니다. 즉, 화면에 표시할 하나 이상의 View를 포함하는 컨테이너 역할을 수행하는 것이죠. Layout, ListView 등이 ViewGroup에 속하며 View뿐만 아니라 ViewGroup 또한 포함할 수 있습니다.
ListView는 ViewGroup에 속합니다. 즉, 데이터를 화면에 표시하기 위해서는 View(또는 ViewGroup) 객체가 ListView에 추가되어야 하며, 하나의 View(또는 ViewGroup)는 하나의 아이템 정보를 표시하게 됩니다.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

### Adapter

![Adapter](http://cfile23.uf.tistory.com/image/2347C350568CB93F27AD93)

ListView에 데이터를 추가하여 화면에 표시하기 위해서는 Adapter를 사용해야 합니다. 일반적으로 "어댑터"라는 의미는 "장치 또는 기계의 다른 부분을 연결하는 장치로, 적합하지 않은 두 개의 부분을 전기적 또는 기계적으로 접속하기 위한 장치 또는 도구"를 말합니다. ListView에서 사용하는 Adapter 또한 의미적으로 크게 다르지 않습니다. 유사한 문장으로 표현하자면 __"사용자가 정의한 데이터를 ListView에 출력하기 위해 사용하는 객체로, 사용자 데이터와 화면 출력 View로 이루어진 두 개의 부분을 이어주는 객체"__ 정도가 되겠네요. 즉, Adapter가 하는 역할은 사용자 데이터를 입력받아 View를 생성하는 것이며 Adapter에서 생성되는 View는 ListView 내 하나의 아이템 영역에 표시되는 것입니다.

### Inflate

>> xml 에 씌여져 있는 view 의 정의를 실제 view 객체로 만드는 역할

- 기능 
1. XML 문서에 정의된 레이아웃과 차일드 뷰의 속성을 읽어 실제 뷰 객체를 생성해 내는 동작.

2. 레이아웃의 정보대로 객체를 생성하고 속성 변경 메서드를 순서대로 호출 하는것.

- 사용법

```jAVA
LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

View v = (View) inflater.inflate(R.layout.main, null);

setContentView(v);

//참고
//위에 코드는 결과적으로 setContentView(R.layout.main)와 동일

```

### Intent

- 액티비티 이동간 데이터 전달
```java

Intent intent = new Intent(getApplicationContext(), AnotherActivity.class);

intent.putExtra("name", "rio");
intent.putExtra("age", 10);
startActivity(intent);

```

- 데이터 받기

```Java
Intent intent = getIntent();
String name = intent.getExtras().getString("name");
int age = intent.getExtras().getInt("age");

```

### Intent with Serializable

>> 객체를 저장할 때는 자바의 Serialize 기능을 사용하면 쉽게 구현할 수 있습니다. 자바는 언어 차원에서 객체를 일차원의 데이터로 저장하는 기능을 제공하는데 이 기능이 바로 Serialize 입니다. Serializable 인터페이스를 상속 받으면 디폴토 직렬화 알고리즘이 적용되어 클래스의 모든 인스턴스 필드가 순서대로 저장이 됩니다.

- 예제 코드
1. Intent로 전달한 객체에 Serializable 인터페이스를 상속 받습니다. 아래 그림처럼 빨간 네모 박스가 Serializable 인터페이스를 상속받은 구현방법입니다.

   ![1](http://cfile26.uf.tistory.com/image/241E4E4E56BCAB0A01BFD0)


2. Serializable 인터페이스를 상속받은 객체를 Intent에 담아 전달 받을 클래스로 보냅니다.

(※ Intent로 전달하기 전에 전달 받은 클래스가 Manifest.xml에 등록이 되어 있어야 합니다.)
 
 ![2](http://cfile26.uf.tistory.com/image/2269104D56BCA8DB3AC53E)

3. Intent를 전달받은 클래스에서는 getSerialzableExtra() 메소드로 불러오기만 하면 됩니다.
 
 ![3](http://cfile24.uf.tistory.com/image/2506274E56BCA8EE05499C)





### 출처

- 출처: http://recipes4dev.tistory.com/42 [개발자를 위한 레시피]
- 출처: http://aroundck.tistory.com/39 [돼지왕 왕돼지 놀이터]
- 출처: 인텐트(Intent)로 데이터 전달(putExtra, getExtras)|작성자 jungsquare
- 출처: http://eunplay.tistory.com/28 [어제, 오늘 그리고 내일]

## TODO

- getcontext, context 등 차이
- Intent관련 개념 추후에 정리
- adapter 구조 및 개념 숙지


## Retrospect

- 안드로이드에서 굉장히 중요하다고 얘기하는 listView인 만큼 깊고 넓게 공부를 계속 해야할 듯함

## Output
- 생략


