package com.redblood53.fetchmedia;

import android.app.Activity;
import android.content.Context;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import android.os.AsyncTask;
/**
 * FetchmediaPlugin
 */
public class FetchmediaPlugin implements MethodCallHandler, PrepareSongsListTask.SongScanCompleteListerner  {
  private final MethodChannel channel;
  private Activity activity;
  Result getAllAudioFilesResult;
  /**
   * Plugin registration.
   */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "fetchmedia");
    channel.setMethodCallHandler(new FetchmediaPlugin(registrar.activity(), channel));
  }
  private FetchmediaPlugin(Activity activity, MethodChannel channel){
    this.activity = activity;
    this.channel = channel;
    this.channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    this.getAllAudioFilesResult = result;
    if (call.method.equals("getAllAudioFiles")) {
        PrepareSongFromStorage prepareSongFromStorage = PrepareSongFromStorage.getInstance();
        PrepareSongsListTask prepareSongsListTask = new PrepareSongsListTask(prepareSongFromStorage, this.activity, (PrepareSongsListTask.SongScanCompleteListerner)this);
        prepareSongsListTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
      // result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else {
      result.notImplemented();
    }
  }
    /**
     * this function will invoked when song scanning is complete. So its a impletement function for PrepareSongsListTask
     * which is invoked by PrepareSongsListTask.doInBackground() handler
     * @param context
     */
    @Override
    public void onSongScanCompleted(Context context) {
        // channel.invokeMethod("getAllAudioFiles", true);
        this.getAllAudioFilesResult.success(PrepareSongFromStorage.getInstance().songList);
    }
}
