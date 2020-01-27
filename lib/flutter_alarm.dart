import 'dart:async';

import 'package:flutter/services.dart';

class FlutterAlarm {

  static const MethodChannel _channel = const MethodChannel('flutter_alarm');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static void showRating() {
    _channel.invokeMethod('showRating');
  }

}
