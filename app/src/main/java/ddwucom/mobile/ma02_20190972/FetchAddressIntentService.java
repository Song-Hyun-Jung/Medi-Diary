package ddwucom.mobile.ma02_20190972;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FetchAddressIntentService extends IntentService {

    final static String TAG = "FetchAddress";

    private Geocoder geocoder; //지오코딩 위한 지오코더
    private ResultReceiver receiver; //결과를 돌려주는 receiver가 인텐트에 담겨서 오는데 그걸 보관하기 위해서

    //주의! Service도 Activity와 같이 AndroidManifest에 등록 필요 (AndroidManifest 확인)


    public FetchAddressIntentService() {
        super("FetchLocationIntentService");
    } //생성자


    @Override
    protected void onHandleIntent(@Nullable Intent intent) { //startService하게 되면 생성자에의해 필요한 부분 만들고 난 후 동작.
        //intent에는 mainActivity에서 넣어줬던 intent가 넘어온것-결과받을 receiver, 위도, 경도
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault()); //지오코더 생성, 컨텍스트정보와 지오코딩하는 주소값 종류 설정 ex)한글 스마트폰->한글

//        MainActivity 가 전달한 Intent 로부터 위도/경도와 Receiver 객체 설정
        //1.
        if (intent == null) return;
        double latitude = intent.getDoubleExtra(Constants.LAT_DATA_EXTRA, 0); //위도, 경도, 리시버 intent에서 꺼냄
        double longitude = intent.getDoubleExtra(Constants.LNG_DATA_EXTRA, 0);
        receiver = intent.getParcelableExtra(Constants.RECEIVER); //parcelable로 전달된 객체이기 때문에 ParcelableExtra로 꺼냄.

        List<Address> addresses = null; //지오코더 반환값

//        위도/경도에 해당하는 주소 정보를 Geocoder 에게 요청
        //2.
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); //최대결과. 하나만 뽑아내고 있음. 3, 5, ...
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

//        결과로부터 주소 추출
        //3.
        if (addresses == null || addresses.size()  == 0) {
            Log.e(TAG, getString(R.string.no_address_found));
            deliverResultToReceiver(Constants.FAILURE_RESULT, null);
        } else {
            Address addressList = addresses.get(0); //addresses에서 결과 꺼냄
            ArrayList<String> addressFragments = new ArrayList<String>();

            for(int i = 0; i <= addressList.getMaxAddressLineIndex(); i++) {
                addressFragments.add(addressList.getAddressLine(i)); //정보 담는 작업
            }
            Log.i(TAG, getString(R.string.address_found));
            deliverResultToReceiver(Constants.SUCCESS_RESULT,
                    TextUtils.join(System.getProperty("line.separator"),
                            addressFragments)); //TextUtils.join(개행문자, 문자열List) -> 문자열 List에 담긴 여러 문자열들을 첫번째 매개변수인 개행문자로 구분해가며 하나의 문자열로 변환함

        }
    }


    //    ResultReceiver 에게 결과를 Bundle 형태로 전달
    //4.주소를 다시 receiver에게 전달
    private void deliverResultToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle(); //데이터 묶는 용도로 쓰는 클래스. 데이터 포장
        bundle.putString(Constants.RESULT_DATA_KEY, message); //key값과 내용물 넣어줌
        receiver.send(resultCode, bundle); //intent통해 전달받은 receiver에 내용물 넣어줌
    }

}
