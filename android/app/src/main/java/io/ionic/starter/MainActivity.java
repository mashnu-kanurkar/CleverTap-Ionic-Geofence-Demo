package io.ionic.starter;

import android.os.Bundle;
import com.ct.plugins.example.*;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    registerPlugin(MyCustomPluginPlugin.class);
    registerPlugin(GeofencePluginPlugin.class);
    super.onCreate(savedInstanceState);
  }
}
