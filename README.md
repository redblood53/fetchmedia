# FetchMedia

A Flutter plugin (Android) to fetch audio files from device.
 

## Usage

[Example](https://github.com/redblood53/fetchmedia/blob/master/example/lib/main.dart) 

To use this plugin : 


```yaml
  dependencies:
    flutter:
      sdk: flutter
    fetchmedia:
```

- instantiate  Fetchmedia instance

```dart
//...
List<LinkedHashMap> mediaList;
    try {
      // make sure You asked For permission
      final bool permission = await requestPermissionIfNotGiven();
      if (permission) {
        mediaList = await new Fetchmedia().allAudioFiles();
      }

      print("palteform ${mediaList}");
    } on PlatformException {
      print("error on fetching");
    }
//...
```