# ListView
## 사용법
- 멤버변수로 데이터 정의
```Java
 ArrayList<String> data = new ArrayList<>();
 ListView listView;
 ```


 - 데이터와 리스트뷰를 연결하는 아답터 생성
 ```Java
CustomAdapter adapter = new CustomAdapter(this, data);
 ```

 - 아답터와 리스트뷰 연결

 ```Java
 listView = (ListView) findViewById(R.id.listView);
 listView.setAdapter(adapter);
 ```

 - 아답터 클래스 만들기

```Java
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
    } /


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
```

- 베이스 아답터를 상속받은 아답터 클래스를 구현
- 아답터는 리스트뷰에 아이템을 뿌려주는 역할을 한다.
- 아답터 생성시 4개의 오버라이드 함수가 있다. 그중에서 사실상 getView(아이템이 리스트에 나타나게 해주는 함수)가 가장 중요
- 화면에 보이는 아이템들의 재사용성을 위해서 if(view ==null){~~~}을 해주고,
홀더 클래스를 따로 생성한다.

- 홀더클래스

```Java
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
```
