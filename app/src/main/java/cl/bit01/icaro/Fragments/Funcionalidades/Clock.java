package cl.bit01.icaro.Fragments.Funcionalidades;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import cl.bit01.icaro.ApiClient.ApiResponseHandler;
import cl.bit01.icaro.ApiClient.ApiTime;
import cl.bit01.icaro.R;
import cl.bit01.icaro.Utils.GPSTracker;

import static cl.bit01.icaro.R.id.clock_minutes;

public class Clock extends Fragment {
    private String layoutMode;
    private ProgressDialog progress;
    private GPSTracker gps;

    private RelativeLayout layout_clock;
    private TextView hour;
    private TextView minutes;

    public Clock() {
        setLayoutMode("local_hour");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;

        if (getLayoutMode().equals("local_hour")) {
            view = inflater.inflate(R.layout.fragment_clock, container, false);
            layout_clock = (RelativeLayout) view.findViewById(R.id.layout_clock);
            hour = (TextView) view.findViewById(R.id.clock_hour);
            minutes = (TextView) view.findViewById(clock_minutes);
            layout_clock.setVisibility(View.INVISIBLE);
            gps = new GPSTracker(getActivity());
            setClock();
        }
        return view;
    }

    private void setClock() {
        try {
            ApiTime client = new ApiTime();
            ApiTime.setContext(getActivity());
            if (gps.canGetLocation()) {
                client.retrieveWorldTime(gps.getLatitude(), gps.getLongitude(), new ApiResponseHandler() {
                    @Override
                    public void onStart() {
                        progress = new ProgressDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                        progress.setTitle(gps.getString(R.string.get_data));
                        progress.setMessage(gps.getString(R.string.wait_for_a_minute));
                        progress.show();
                    }

                    @Override
                    public void onFinish() {
                        layout_clock.setVisibility(View.VISIBLE);
                        progress.dismiss();
                    }

                    @Override
                    public void onSuccess(HashMap<String, String> dataReturned) {
                        hour.setText(dataReturned.get("hour"));
                        minutes.setText(dataReturned.get("minutes"));
                    }

                    @Override
                    public void onError() {
                        Log.d("Icaro", "Get Data From World Weather API Failure");
                        Toast.makeText(getActivity(), "HAY ERROR CONECTANDO", Toast.LENGTH_LONG).show();
                    }
                });
            }
        } catch (Exception e) {
            Log.e("API Error", Log.getStackTraceString(e));
        }
    }

    public String getLayoutMode() {
        return this.layoutMode;
    }

    public void setLayoutMode(String layoutMode) {
        this.layoutMode = layoutMode;
    }
}