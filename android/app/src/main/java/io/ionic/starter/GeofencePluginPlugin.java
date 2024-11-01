package io.ionic.starter;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import com.clevertap.android.geofence.CTGeofenceAPI;
import com.clevertap.android.geofence.CTGeofenceSettings;
import com.clevertap.android.geofence.Logger;
import com.clevertap.android.geofence.interfaces.CTGeofenceEventsListener;
import com.clevertap.android.geofence.interfaces.CTLocationUpdatesListener;
import com.clevertap.android.sdk.CleverTapAPI;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import org.json.JSONObject;
@CapacitorPlugin(name = "GeofencePlugin")
public class GeofencePluginPlugin extends Plugin {
  private GeofencePlugin implementation = new GeofencePlugin();

  @PluginMethod
  public void echo(PluginCall call) {
    String value = call.getString("value");

    JSObject ret = new JSObject();
    ret.put("value", implementation.echo(value));
    call.resolve(ret);
  }

  @PluginMethod()
  public void triggerLocation(PluginCall call) {
    call.setKeepAlive(true);
    try {

      CTGeofenceAPI.getInstance(getContext().getApplicationContext()).triggerLocation();
    } catch (IllegalStateException e){
      Log.d("CTGeofence", "exception "+e.getMessage());
    }
    JSObject ret = new JSObject();
    ret.put("value", "New value from android");
    call.resolve(ret);
  }

  @PluginMethod()
  public void initGeoFence(PluginCall call) {

    call.setKeepAlive(true);
    Context context = getContext().getApplicationContext();
    Log.d("plugin", "initiating clevertap geofence");
    CleverTapAPI cleverTapAPI = CleverTapAPI.getDefaultInstance(context);
    Log.d("plugin", "clevertap instance initiated with "+cleverTapAPI.toString());

    CTGeofenceSettings ctGeofenceSettings = new CTGeofenceSettings.Builder()
      .enableBackgroundLocationUpdates(true)//boolean to enable background location updates
      .setLogLevel(Logger.VERBOSE)//Log Level
      .setLocationAccuracy(CTGeofenceSettings.ACCURACY_HIGH)//byte value for Location Accuracy
      .setLocationFetchMode(CTGeofenceSettings.FETCH_LAST_LOCATION_PERIODIC)//byte value for Fetch Mode
      .setGeofenceMonitoringCount(50)//int value for number of Geofences CleverTap can monitor
      .setInterval(30*60*1000)//long value for interval in milliseconds
      .setFastestInterval(30*60*1000)//long value for fastest interval in milliseconds
      .setSmallestDisplacement(200)//float value for smallest Displacement in meters
      .setGeofenceNotificationResponsiveness(0)// int value for geofence notification responsiveness in milliseconds
      .build();

    CTGeofenceAPI.getInstance(context).init(ctGeofenceSettings, cleverTapAPI);

    CTGeofenceAPI.getInstance(context)
      .setOnGeofenceApiInitializedListener(new CTGeofenceAPI.OnGeofenceApiInitializedListener() {
        @Override
        public void OnGeofenceApiInitialized() {
          Log.d("CTGeofence", "initialized fence");          }
      });

    CTGeofenceAPI.getInstance(context)
      .setCtGeofenceEventsListener(new CTGeofenceEventsListener() {
        @Override
        public void onGeofenceEnteredEvent(JSONObject jsonObject) {
          Log.d("CTGeofence", "onGeofenceEnteredEvent triggered");
        }

        @Override
        public void onGeofenceExitedEvent(JSONObject jsonObject) {
          Log.d("CTGeofence", "onGeofenceExitedEvent triggered");
        }
      });

    CTGeofenceAPI.getInstance(context)
      .setCtLocationUpdatesListener(new CTLocationUpdatesListener() {
        @Override
        public void onLocationUpdates(Location location) {
          Log.d("CTGeofence", "new location Lat: "+location.getLatitude() +" and Long: "+location.getLongitude());

        }
      });
    JSObject ret = new JSObject();
    ret.put("isInitiated", true);
    call.resolve(ret);
  }

}
