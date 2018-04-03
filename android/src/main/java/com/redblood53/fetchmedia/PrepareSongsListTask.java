package com.redblood53.fetchmedia;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by pradeep sharma on 02/10/17.
 */

public class PrepareSongsListTask extends AsyncTask<Void,Void,Void> {
    private PrepareSongFromStorage prepareSongFromStorage;
    private Context context;
    private SongScanCompleteListerner scanCompleteListerner;

    public PrepareSongsListTask(PrepareSongFromStorage prepareSongFromStorage, Context context, SongScanCompleteListerner scanCompleteListerner) {
        this.prepareSongFromStorage = prepareSongFromStorage;
        this.context = context;
        this.scanCompleteListerner = scanCompleteListerner;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.d("preparetask", "do in back ground is called");
        prepareSongFromStorage.scanSongFromStorage(context);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        scanCompleteListerner.onSongScanCompleted(context);
    }

    public interface SongScanCompleteListerner{
        void onSongScanCompleted(Context context);
    }
}