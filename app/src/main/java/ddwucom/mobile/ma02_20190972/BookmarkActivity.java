package ddwucom.mobile.ma02_20190972;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity {
    final static String TAG = "BookmarkActivity";
    final int REQ_CODE = 100;
    final int UPDATE_CODE = 200;

    ListView listView;
    MyAdapter myAdapter;
    ArrayList<HospitalDto> myHospitalList = null;
    HospitalDBManager hospitalDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        listView = findViewById(R.id.customListView);
        myHospitalList = new ArrayList<HospitalDto>();

        myAdapter = new MyAdapter(this, R.layout.custom_adapter_view, myHospitalList);
        listView.setAdapter(myAdapter);
        hospitalDBManager = new HospitalDBManager(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HospitalDto hospital = myHospitalList.get(position);
                Intent intent = new Intent(BookmarkActivity.this, BookmarkInfoActivity.class);
                intent.putExtra("hospital", hospital);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        myHospitalList.clear();
        myHospitalList.addAll(hospitalDBManager.getAllBookmarks());
        myAdapter.notifyDataSetChanged();
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == UPDATE_CODE){
            switch(resultCode){
                case RESULT_OK:
                    Toast.makeText(this, "수정 완료", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "수정 취소", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

     */
}
