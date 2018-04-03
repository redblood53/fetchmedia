import 'dart:async';

import 'package:flutter/services.dart';
import 'dart:collection';
import 'package:flutter/foundation.dart';

class Fetchmedia {
  final MethodChannel channel = new MethodChannel('fetchmedia');
  Future<List<LinkedHashMap>> allAudioFiles() =>
      channel.invokeMethod('getAllAudioFiles');
}
