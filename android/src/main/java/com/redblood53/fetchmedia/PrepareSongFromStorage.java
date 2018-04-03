package com.redblood53.fetchmedia;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import java.util.HashMap;

import java.util.ArrayList;

/**
 * Created by pradeep sharma on 02/10/17.
 */

public class PrepareSongFromStorage {
    String GENRE_ID = MediaStore.Audio.Genres._ID;
    String GENRE_NAME = MediaStore.Audio.Genres.NAME;
    String SONG_ID = MediaStore.Audio.Media._ID;
    String SONG_TITLE = MediaStore.Audio.Media.TITLE;
    String SONG_ARTIST = MediaStore.Audio.Media.ARTIST;
    String SONG_ALBUM = MediaStore.Audio.Media.ALBUM;
    String SONG_YEAR = MediaStore.Audio.Media.YEAR;
    String SONG_TRACK_NO = MediaStore.Audio.Media.TRACK;
    String SONG_FILEPATH = MediaStore.Audio.Media.DATA;
    String SONG_DURATION = MediaStore.Audio.Media.DURATION;
    String SONG_ALBUM_ID=MediaStore.Audio.Albums.ALBUM_ID;
    /**
     * these below two line creates a singleton desgin pattern for this file.
     */
    private static final PrepareSongFromStorage ourInstance = new PrepareSongFromStorage();
    public static PrepareSongFromStorage getInstance() {
        return ourInstance;
    }

    private PrepareSongFromStorage() {
    }
    public ArrayList<HashMap<String,Object>> songList = new ArrayList<>(0);

    public void scanSongFromStorage(Context context){
        ContentResolver contentResolver = context.getContentResolver();
        scanSongList(contentResolver, context);
        // scanArtistList(contentResolver, context);
        // scaSongByAlbum(contentResolver, context);
    }

    /**
     * method for creating songListBy album basis
     * @// TODO: 15/10/17 need to implement when you have extra time.
     * @param contentResolver
     * @param context
     */

    private void scaSongByAlbum(ContentResolver contentResolver, Context context) {
    }

    /**
     * method for accessing the artistList via sonng which will cover both external and internal storage
     * @ TODO: 15/10/17 need to implement this method when 17/10
     * @param contentResolver
     * @param context
     */
    private void scanArtistList(ContentResolver contentResolver, Context context) {
    }

    /**
     *  method for accessing the soncglist from Storage using ContentResolver which will cover both external and internal storage.
     * @param contentResolver
     * @param context
     */
    private void scanSongList(ContentResolver contentResolver, Context context) {
        Cursor cursor;
        Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String filterSelection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        String[] columnsForSong = {
                SONG_ID,
                SONG_TITLE,
                SONG_ARTIST,
                SONG_ALBUM,
                SONG_YEAR,
                SONG_TRACK_NO,
                SONG_FILEPATH,
                SONG_DURATION,
                SONG_ALBUM_ID,
        };
        cursor = contentResolver.query(contentUri, columnsForSong,filterSelection,null, null);
        if(cursor!=null && cursor.moveToFirst()){
            
            do{
                HashMap<String,Object> audio = new HashMap<>();
                audio.put("id", cursor.getInt(cursor.getColumnIndex(SONG_ID)));
                audio.put("filePath",cursor.getString(cursor.getColumnIndex(SONG_FILEPATH)));
                audio.put("albumId", cursor.getLong(cursor.getColumnIndex(SONG_ALBUM_ID)));
                audio.put("title", cursor.getString(cursor.getColumnIndex(SONG_TITLE)));
                audio.put("artist", cursor.getString(cursor.getColumnIndex(SONG_ARTIST)));
                audio.put("album", cursor.getString(cursor.getColumnIndex(SONG_ALBUM)));
                audio.put("year", cursor.getInt(cursor.getColumnIndex(SONG_YEAR)));
                audio.put("trackNo",cursor.getInt(cursor.getColumnIndex(SONG_TRACK_NO)));
                audio.put("durationInMS", cursor.getInt(cursor.getColumnIndex(SONG_DURATION)));
                songList.add(audio);

            }while (cursor.moveToNext());
            cursor.close();
        }
    }
}
