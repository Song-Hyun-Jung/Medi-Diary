package ddwucom.mobile.ma02_20190972;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class HospitalActivity extends AppCompatActivity implements OnMapReadyCallback {

    final static String TAG = "HospitalActivity";
    final static int PERMISSION_REQ_CODE = 100;

    private GoogleMap mGoogleMap;
    private LocationManager locationManager;
    LatLng currentLoc;
    private AddressResultReceiver addressResultReceiver;
    TextView tvMyLocation;
    String Q0;
    String Q1;
    String treatment;

    ArrayList<HospitalDto> resultList;
    HospitalXmlParser parser;

    ArrayList<Marker> markerList = new ArrayList();
    BitmapDrawable bitmapdraw;
    Bitmap b;
    Bitmap smallMarker;


    String[] treatmentItems = {"내과", "소아청소년과", "피부과", "정형외과", "산부인과", "안과", "이비인후과", "치과"};
    //D001:내과, D002:소아청소년과, D003:신경과, D004:정신건강의학과, D005:피부과, D006:외과, D007:흉부외과, D008:정형외과, D009:신경외과, D010:성형외과, D011:산부인과, D012:안과, D013:이비인후과, D014:비뇨기과, D016:재활의학과, D017:마취통증의학과, D018:영상의학과, D019:치료방사선과, D020:임상병리과, D021:해부병리과, D022:가정의학과, D023:핵의학과, D024:응급의학과, D026:치과, D034:구강악안면외과
    String treatmentCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        tvMyLocation = (TextView) findViewById(R.id.tvMyLocation);
        tvMyLocation.setText("현재 위치");

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, treatmentItems
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                markerList = null;
                treatment = treatmentItems[position];
                getTreatmentCode(treatment);
                startAddressService(currentLoc);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(checkPermission())
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 10000, 500, locationListener);

        addressResultReceiver = new AddressResultReceiver(new Handler());

        mapLoad();

        //지도에 표시할 아이콘 크기
        bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.hospitalicon);
        b = bitmapdraw.getBitmap();
        smallMarker = Bitmap.createScaledBitmap(b, 90, 90, false);
    }




    /*진료과목 코드*/
    private void getTreatmentCode(String treatment) {
        switch(treatment) {
            case "내과":
                treatmentCode = "D001";
                break;
            case "소아청소년과":
                treatmentCode = "D002";
                break;
            case "피부과":
                treatmentCode = "D005";
                break;
            case "정형외과":
                treatmentCode = "D008";
                break;
            case "산부인과":
                treatmentCode = "D011";
                break;
            case "안과":
                treatmentCode = "D012";
                break;
            case "이비인후과":
                treatmentCode = "D013";
                break;
            case "치과":
                treatmentCode = "D026";
                break;

        }
    }


    /*구글맵을 멤버변수로 로딩*/
    private void mapLoad() {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);      // 매배변수 this: MainActivity 가 OnMapReadyCallback 을 구현하므로

    }

    private void getPlaceDetail(String title){
        HospitalDto hospitalDto = null;
        for(HospitalDto h : resultList){
            if(h.getHospitalName().equals(title)){
                hospitalDto = h;
                break;
            }
        }
        Intent intent = new Intent(HospitalActivity.this, HospitalInfoActivity.class);
        //intent.putExtra("hpid", hospitalDto.getHpid());
        intent.putExtra("treatment", treatment);
        intent.putExtra("currentLat", currentLoc.latitude);
        intent.putExtra("currentLon", currentLoc.longitude);
        intent.putExtra("hospital", hospitalDto);
        Log.d(TAG, "인텐트: " + hospitalDto.getHospitalName());
        Log.d(TAG, "인텐트: " + treatment);
        startActivity(intent);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        if(checkPermission()) {
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.setOnMyLocationClickListener(locationClickListener);
        }

        currentLoc = new LatLng(37.606537, 127.041758);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 17));

        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
               getPlaceDetail(marker.getTitle());
            }
        });
    }

    GoogleMap.OnMyLocationClickListener locationClickListener =
            new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(Location location) {
                    currentLoc = new LatLng(location.getLatitude(), location.getLongitude());
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 17));
                    startAddressService(currentLoc);
                }
            };

    LocationListener locationListener = new LocationListener(){
        @Override
        public void onLocationChanged(Location location){
            currentLoc = new LatLng(location.getLatitude(), location.getLongitude());
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 17));
            startAddressService(currentLoc);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    };

    /* 위도/경도 → 주소 변환 IntentService 실행 */
    private void startAddressService(LatLng latLng) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, addressResultReceiver);
        intent.putExtra(Constants.LAT_DATA_EXTRA, latLng.latitude);
        intent.putExtra(Constants.LNG_DATA_EXTRA, latLng.longitude);
        startService(intent);
    }

    /* 위도/경도 → 주소 변환 ResultReceiver */
    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            String addressOutput = null;
            String address = null;
            if (resultCode == Constants.SUCCESS_RESULT) {
                if (resultData == null)
                    return;
                addressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
                String[] splitAddress = addressOutput.split(" ");
                Q0 = splitAddress[1];
                Q1 = splitAddress[2];
                address = Q0 + "\n" + Q1;
                if (addressOutput == null)
                    address = "";
                tvMyLocation.setText(address);
                Log.d(TAG, addressOutput);
                Log.d(TAG, address);
                String apiAddress = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire";
                String query1 = Q0;
                String query2 = Q1;


                new HospitalActivity.HospitalAsyncTask().execute(apiAddress, query1, query2, treatmentCode);

            } else{
                tvMyLocation.setText(R.string.no_address_found);
            }
        }
    }


    class HospitalAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {
            String address = strings[0];
            String query1 = strings[1];
            String query2 = strings[2];
            String query3 = strings[3];
            String apiURL = null;

            try{
                StringBuilder urlBuilder = new StringBuilder(address);
                urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=CWrcYagrwEq%2FhRKG2ZkksDsrla7VXjk4Mj6707whjJ%2BcJqOB93U%2FTG0lDkMwD7DIxq%2Bl0hf9YRSgyDgQGg5D0g%3D%3D");
                urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("400", "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("Q0", "UTF-8") + "=" + URLEncoder.encode(query1, "UTF-8"));
                if(query2.charAt(query2.length() - 1) == '시' || query2.charAt(query2.length() - 1) == '군' || query2.charAt(query2.length() - 1) == '구')
                    urlBuilder.append("&" + URLEncoder.encode("Q1", "UTF-8") + "=" + URLEncoder.encode(query2, "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("QD", "UTF-8") + "=" + URLEncoder.encode(query3, "UTF-8"));

                apiURL = urlBuilder.toString();
                Log.d(TAG, apiURL);
            }catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String result = downloadContents(apiURL);

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            parser = new HospitalXmlParser();
            resultList = parser.parse(result);     // 오픈API 결과의 파싱 수행
            mGoogleMap.clear();
            if(markerList != null) markerList.clear();
            markerList = new ArrayList<>();
            if (resultList.size() == 0) {
                Toast.makeText(HospitalActivity.this, "No data!", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    for (HospitalDto h : resultList) {
                        Log.d(TAG, h.getHospitalName());
                        if(h.getHospitalLat() != null && h.getHospitalLon() != null) {
                            LatLng hospitalLatLng = new LatLng(Double.parseDouble(h.getHospitalLat()), Double.parseDouble(h.getHospitalLon()));
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(hospitalLatLng);
                            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                            markerOptions.title(h.getHospitalName());
                            //mGoogleMap.addMarker(markerOptions);
                            markerList.add(mGoogleMap.addMarker(markerOptions));
                        }
                    }
                }catch(Exception e){
                    Toast.makeText(HospitalActivity.this, "검색 실패", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }


    }

    /* 네트워크 환경 조사 */
    private boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /* URLConnection 을 전달받아 연결정보 설정 후 연결, 연결 후 수신한 InputStream 반환 */
    private InputStream getNetworkConnection(HttpURLConnection conn) throws Exception {
        conn.setReadTimeout(100000);
        conn.setConnectTimeout(100000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code: " + conn.getResponseCode());
        }

        return conn.getInputStream();
    }

    /* 주소(address)에 접속하여 문자열 데이터를 수신한 후 반환 */
    protected String downloadContents(String address) {
        HttpURLConnection conn = null;
        InputStream stream = null;
        String result = null;

        try {
            URL url = new URL(address);
            conn = (HttpURLConnection)url.openConnection();
            stream = getNetworkConnection(conn);
            result = readStreamToString(stream);
            if (stream != null) stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) conn.disconnect();
        }

        return result;
    }

    /* InputStream을 전달받아 문자열로 변환 후 반환 */
    protected String readStreamToString(InputStream stream){
        StringBuilder result = new StringBuilder();

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String readLine = bufferedReader.readLine();

            while (readLine != null) {
                result.append(readLine + "\n");
                readLine = bufferedReader.readLine();
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    /* 필요 permission 요청 */
    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQ_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 퍼미션을 획득하였을 경우 맵 로딩 실행
                mapLoad();
            } else {
                // 퍼미션 미획득 시 액티비티 종료
                Toast.makeText(this, "앱 실행을 위해 권한 허용이 필요함", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


}
