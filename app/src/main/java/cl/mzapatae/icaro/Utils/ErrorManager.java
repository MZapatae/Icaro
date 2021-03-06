package cl.mzapatae.icaro.Utils;/*
 * Created by miguelost on 10-02-15.
 */

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

//TODO: Extract Strings and set Errors from Clock and Weather Feature
public class ErrorManager {
    public static void notify(Context mActivity, int errorCode) {
        switch (errorCode) {
            case 1000:
                Toast.makeText(mActivity, "GPS Desconectado, por favor encenderlo.", Toast.LENGTH_LONG).show();
                break;

            case 1001:
                Toast.makeText(mActivity, "Error Obteniendo la Direccion.", Toast.LENGTH_LONG).show();
                break;

            case 1002:
                Toast.makeText(mActivity, "Hay problemas para obtener su ubicacion actual", Toast.LENGTH_LONG).show();
                break;

            default:
                Toast.makeText(mActivity, "Error Desconocido", Toast.LENGTH_LONG).show();
        }
    }

    public static void logError(String className, String tag, String errorMessage) {
        Crashlytics.log(Log.ERROR, "Icaro - " + className + ":" + tag, errorMessage);
    }
}
