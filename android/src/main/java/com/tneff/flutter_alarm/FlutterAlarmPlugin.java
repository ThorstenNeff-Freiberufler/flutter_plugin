package com.tneff.flutter_alarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterAlarmPlugin */
public class FlutterAlarmPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {

  private Context context;

  private Activity activity;

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  private FlutterAlarmPlugin plugin;

  public FlutterAlarmPlugin() {}

  public FlutterAlarmPlugin(Context context) {
    this.context = context;
  }

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    final MethodChannel channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "flutter_alarm");
    plugin = new FlutterAlarmPlugin(flutterPluginBinding.getApplicationContext());
    channel.setMethodCallHandler(plugin);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
        result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals("showRating")) {
        rateApp();
    } else {
      result.notImplemented();
    }
  }

  public void rateApp() {

    String message = context.getString(R.string.RateAppMessage);

    DialogInterface.OnClickListener OKButtonListener = new DialogInterface.OnClickListener(){
      @Override public void onClick(DialogInterface dialog, int which) {
        GoToMarket();
        try {dialog.dismiss();} catch (Exception e) {e.printStackTrace();}
      }
    };

    DialogInterface.OnClickListener NoButtonListener = new DialogInterface.OnClickListener(){
      @Override public void onClick(DialogInterface dialog, int which) {
        try {dialog.dismiss();} catch (Exception e) {e.printStackTrace();}
      }
    };

    new AlertDialog.Builder(activity)
            .setTitle(null)
            .setMessage(message)
            .setNegativeButton(context.getString(R.string.RateAppYes),OKButtonListener)
            .setPositiveButton(context.getString(R.string.RateAppNo),NoButtonListener)
            .show();
  }

  private void GoToMarket() {
    Uri uri = Uri.parse("market://details?id=com.sic.taxibutton");
    Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
    try {
      context.startActivity(myAppLinkToMarket);
    } catch (ActivityNotFoundException e) {
      Toast.makeText(context, " unable to find market app", Toast.LENGTH_LONG).show();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {}

  @Override
  public void onAttachedToActivity(ActivityPluginBinding activityPluginBinding) {
    activity = activityPluginBinding.getActivity();
    plugin.setActivity(activity);
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {}

  @Override
  public void onReattachedToActivityForConfigChanges(ActivityPluginBinding activityPluginBinding) {}

  @Override
  public void onDetachedFromActivity() {}
}
