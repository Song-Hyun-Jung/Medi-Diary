package ddwucom.mobile.ma02_20190972;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<HospitalDto> myDataList;
    private LayoutInflater layoutInflater;

    public MyAdapter(Context context, int layout, ArrayList<HospitalDto> myDataList) {
        this.context = context;
        this.layout = layout;
        this.myDataList = myDataList;
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { return myDataList.size(); }

    @Override
    public Object getItem(int position) { return myDataList.get(position); }

    @Override
    public long getItemId(int position) { return myDataList.get(position).get_id(); }

    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = layoutInflater.inflate(layout, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.bmName = convertView.findViewById(R.id.bmName);
            viewHolder.bmTreatment = convertView.findViewById(R.id.bmTreatment);
            viewHolder.bmTel = convertView.findViewById(R.id.bmTel);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.bmName.setText(myDataList.get(position).getHospitalName());
        viewHolder.bmTreatment.setText(myDataList.get(position).getTreatment());
        viewHolder.bmTel.setText(myDataList.get(position).getHospitalTel());

        return convertView;
    }

    static class ViewHolder{
        TextView bmName;
        TextView bmTreatment;
        TextView bmTel;
    }


}
