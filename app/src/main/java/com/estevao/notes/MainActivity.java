package com.estevao.notes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private static MiniNoteVIew noteLayer;
    private ImageView btnNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindBtnNewNote();
    }

    private void bindBtnNewNote() {
        btnNewNote = (ImageView) findViewById(R.id.open);
        btnNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkDrawOverlayPermission();
                initNoteLayer(getApplication().getApplicationContext());

            }
        });
    }


    public static void initNoteLayer(Context context) {
        if (noteLayer == null)
            noteLayer = new MiniNoteVIew(context);

    }


    public void checkDrawOverlayPermission() {
        if (!Settings.canDrawOverlays(MainActivity.this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        if (requestCode == 1) {
            if (Settings.canDrawOverlays(this)) {
                initNoteLayer(getApplication().getApplicationContext());
            }
        }
    }
}
