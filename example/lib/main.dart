import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:fetchmedia/fetchmedia.dart';
import 'dart:collection';
import 'package:simple_permissions/simple_permissions.dart';

void main() => runApp(new MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => new _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  initPlatformState() async {
    List<LinkedHashMap> platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      final bool permission = await requestPermissionIfNotGiven();
      if (permission) {
        platformVersion = await new Fetchmedia().allAudioFiles();
      }

      print("palteform ${platformVersion}");
    } on PlatformException {
      print("error on fetching");
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;
  }

  Future<bool> requestPermissionIfNotGiven() async {
    // check plateformVersion before asking for permission
    Permission permission = Permission.WriteExternalStorage;
    final bool isAllowed = await SimplePermissions.checkPermission(permission);
    if (!isAllowed) {
      final bool isSuccess =
          await SimplePermissions.requestPermission(permission);
      return isSuccess;
    }
    return true;
  }

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text('Plugin example app'),
        ),
        body: new Center(
          child: new Text('Running on: $_platformVersion\n'),
        ),
      ),
    );
  }
}
