package android_serialport_api.Help;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

import android_serialport_api.sample.R;

public class StandardSamplingPDF extends AppCompatActivity {

    private PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_sampling_pdf);

        pdfView = (PDFView) findViewById(R.id.pdfView);
        pdfView.fromFile(getFilePath("1.pdf"))
                .enableSwipe(false)
                .swipeHorizontal(true)
                .enableDoubletap(true)
                .load();
    }





    private String androidMarshmallowSDcardPath() {
        String rootPath = null;
        File f = new File("/storage");
        if (f.isDirectory()) {
            String[] s = f.list();
            for (int i = 0; i < s.length; i++) {
                if(s[i].matches(".*-+.*")) {
                    rootPath ="/storage/" + s[i];
                    break;
                }else if(s[i].matches("exfat_uuid")) {// SONY Z3 SD card path name
                    rootPath ="/storage/" + s[i];
                    break;
                }
            }
        }
        return rootPath;
    }
    public File getFilePath(String pdf){

        String package123 = getApplicationContext().getPackageName();
        String tmp = androidMarshmallowSDcardPath();
        String line = "";
        try {
            File path = new File(tmp+"/Android/data/" + package123 + "/pdf" ,pdf);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
//        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
