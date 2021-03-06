package ddwucom.mobile.ma02_20190972;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class FindDrugActivity extends AppCompatActivity {

    final static String TAG = "FindDrugActivity";

    EditText etSearchDrugName;
    TextView tvDrugName;
    TextView tvDrugSeq;
    TextView tvDrugEffect;
    TextView tvDrugMethod;
    TextView tvDrugDeposit;
    TextView tvDrugSide;
    ImageView drugImg;

    String imgAddress;

    ArrayList<DrugDto> resultList;
    DrugXmlParser parser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug);

        etSearchDrugName = findViewById(R.id.etSearchDrugName);
        tvDrugName = findViewById(R.id. tvDrugName);
        tvDrugSeq = findViewById(R.id. tvDrugSeq);
        tvDrugEffect = findViewById(R.id. tvDrugEffect);
        tvDrugMethod = findViewById(R.id. tvDrugMethod);
        tvDrugDeposit = findViewById(R.id. tvDrugDeposit);
        tvDrugSide = findViewById(R.id. tvDrugSide);
        drugImg = findViewById(R.id.drugImg);

        resultList = new ArrayList<>();

    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_drugSearch:
                if (!isOnline()) {
                    Toast.makeText(FindDrugActivity.this, "??????????????? ?????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    String address = "http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList";
                    String query = etSearchDrugName.getText().toString();

                    new DrugAsyncTask().execute(address, query);
                } catch(Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    class DrugAsyncTask extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(FindDrugActivity.this, "Wait", "Downloading...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String address = strings[0];
            String query = strings[1];
            String apiURL = null;

            try{
                StringBuilder urlBuilder = new StringBuilder(address);
                urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=CWrcYagrwEq%2FhRKG2ZkksDsrla7VXjk4Mj6707whjJ%2BcJqOB93U%2FTG0lDkMwD7DIxq%2Bl0hf9YRSgyDgQGg5D0g%3D%3D");
                urlBuilder.append("&" + URLEncoder.encode("itemName", "UTF-8") + "=" + URLEncoder.encode(query, "UTF-8"));

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
            progressDialog.dismiss();

            parser = new DrugXmlParser();
            resultList = parser.parse(result);     // ??????API ????????? ?????? ??????

            if (resultList.size() == 0) {
                Toast.makeText(FindDrugActivity.this, "No data!", Toast.LENGTH_SHORT).show();
            } else {
                tvDrugName.setText(resultList.get(0).getItemName());
                tvDrugSeq.setText(resultList.get(0).getItemSeq());
                tvDrugEffect.setText(resultList.get(0).getEffect());
                tvDrugMethod.setText(resultList.get(0).getUseMethod());
                tvDrugDeposit.setText(resultList.get(0).getDepositMethod());
                tvDrugSide.setText(resultList.get(0).getSideEffect());
                imgAddress = resultList.get(0).getImgLink();
                new DrugImgAsyncTask().execute(imgAddress);
            }
        }


    }

    class DrugImgAsyncTask extends AsyncTask<String, Integer, String> {

        Bitmap imgBitmap;

        @Override
        protected String doInBackground(String... strings) {
            String imgAddress = strings[0];

            imgBitmap = downloadImage(imgAddress);
            return imgAddress;
        }
        @Override
        protected void onPostExecute(String s) {
            drugImg.setImageBitmap(imgBitmap);

        }

    }

    /* ???????????? ?????? ?????? */
    private boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /* URLConnection ??? ???????????? ???????????? ?????? ??? ??????, ?????? ??? ????????? InputStream ?????? */
    private InputStream getNetworkConnection(HttpURLConnection conn) throws Exception {
        conn.setReadTimeout(3000);
        conn.setConnectTimeout(3000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code: " + conn.getResponseCode());
        }

        return conn.getInputStream();
    }

    /* ??????(address)??? ???????????? ????????? ???????????? ????????? ??? ?????? */
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

    /* ??????(apiAddress)??? ???????????? ????????? ???????????? ????????? ??? ?????? */
    protected Bitmap downloadImage(String address) {
        HttpsURLConnection conn = null;
        InputStream stream = null;
        Bitmap result = null;

        try {
            URL url = new URL(address);
            conn = (HttpsURLConnection)url.openConnection();
            stream = getNetworkConnection(conn);
            result = readStreamToBitmap(stream);
            if (stream != null) stream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "????????? ??????");
        } finally {
            if (conn != null) conn.disconnect();
        }

        return result;
    }


    /* InputStream??? ???????????? ???????????? ?????? ??? ?????? */
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

    /* InputStream??? ???????????? ??????????????? ?????? ??? ?????? */
    protected Bitmap readStreamToBitmap(InputStream stream) {
        return BitmapFactory.decodeStream(stream);
    }
}
