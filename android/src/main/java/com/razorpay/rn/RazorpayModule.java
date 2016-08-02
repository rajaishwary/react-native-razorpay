
package com.razorpay.rn;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.razorpay.Checkout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Iterator;
import android.app.Activity;
import javax.annotation.Nullable;
import android.content.Intent;




public class RazorpayModule extends ReactContextBaseJavaModule {

  public RazorpayModule(ReactApplicationContext reactContext) {
    super(reactContext);
    //reactContext.addActivityEventListener(this);
  }

  @Override
  public String getName() {
    return "RazorpayCheckout";
  }

  @ReactMethod
  public void open(ReadableMap options, Callback onSuccess, Callback onError) {
    Activity currentActivity = getCurrentActivity();
    try {
      JSONObject optionsJSON = readableMapToJson(options);
      Checkout checkout = new Checkout();
      checkout.setKeyID(optionsJSON.getString("key"));
      checkout.open(currentActivity, optionsJSON);
    } catch (Exception e) {

    }
  }


  @Nullable
  public static JSONObject readableMapToJson(ReadableMap readableMap) {
    JSONObject jsonObject = new JSONObject();

    if (readableMap == null) {
      return null;
    }

    ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
    if (!iterator.hasNextKey()) {
      return null;
    }

    while (iterator.hasNextKey()) {
      String key = iterator.nextKey();
      ReadableType readableType = readableMap.getType(key);

      try {
        switch (readableType) {
        case Null:
          jsonObject.put(key, null);
          break;
        case Boolean:
          jsonObject.put(key, readableMap.getBoolean(key));
          break;
        case Number:
          // Can be int or double.
          jsonObject.put(key, readableMap.getInt(key));
          break;
        case String:
          jsonObject.put(key, readableMap.getString(key));
          break;
        case Map:
          jsonObject.put(key, readableMapToJson(readableMap.getMap(key)));
          break;
        case Array:
          jsonObject.put(key, readableMap.getArray(key));
        default:
          // Do nothing and fail silently
        }
      } catch (JSONException ex) {
        // Do nothing and fail silently
      }
    }

    return jsonObject;
  }


}