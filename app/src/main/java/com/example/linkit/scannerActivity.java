package com.example.linkit;

import static java.lang.Long.parseLong;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linkit.model.Page;
import com.example.linkit.model.PageDAO;
import com.example.linkit.model.PageDatabase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class scannerActivity extends AppCompatActivity {
    private ImageView captured;
    private TextView resultText;
    private Button snapBtn, detecBtn;
    private Bitmap imageBitmap;

    private PageDatabase pageDatabase;
    PageDAO pageDAO;

    static final  int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        captured = findViewById(R.id.capturedImage);
        resultText = findViewById(R.id.resultText);
        snapBtn = findViewById(R.id.clickSnap);
        detecBtn = findViewById(R.id.detectText);

        // databse intilization
        pageDatabase = Room.databaseBuilder(
                getApplicationContext(),PageDatabase.class,"AppDB").allowMainThreadQueries().build();
        pageDAO = pageDatabase.getPageDao();


        detecBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetectText();
            }
        });


        // snap button click listner
        snapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckPermission())
                {
                    CaptureImage();
                }
                else {
                    RequestPermissions();
                }
            }
        });
    }



    // calling CaptureImage on permission grant
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0){
            boolean cameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if(cameraPermission){
                Toast.makeText(this,"permission granted capture image called",Toast.LENGTH_SHORT).show();
                CaptureImage();
            }
            else
            {
                Toast.makeText(this,"permission denied",Toast.LENGTH_SHORT).show();
            }
        }
    }





    private String DetectText() {
        String finalTextResult = "Has Nothing";

        InputImage image = InputImage.fromBitmap(imageBitmap,0);
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        Task<Text> result = recognizer.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
            @Override
            public void onSuccess(Text text) {
               StringBuilder result = new StringBuilder();
                for(Text.TextBlock block : text.getTextBlocks()){
                    String blockText = block.getText();
                    Point[] blockCorner = block.getCornerPoints();
                    Rect blockFrame = block.getBoundingBox();

                    for(Text.Line line : block.getLines() )
                    {
                        String lineText = line.getText();
                        Point[] lineCornerPoint = line.getCornerPoints();
                        Rect lineRect = line.getBoundingBox();

                        for(Text.Element element : line.getElements()){
                            String elementText = element.getText();
                            result.append(elementText);
                        }

                        // display the resulys
                        resultText.setText(blockText);




                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"failed to detect text" , Toast.LENGTH_SHORT).show();
            }
        });

        finalTextResult = (String) resultText.getText();
        return finalTextResult;

    }
    private boolean CheckPermission() {
        int cameraPermission = ContextCompat.checkSelfPermission(getApplicationContext(),CAMERA_SERVICE);
        return cameraPermission == PackageManager.PERMISSION_GRANTED;
    }
    // request permission for
    private void RequestPermissions() {
        int PERMISSION_CODE = 200;
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.CAMERA
        } , PERMISSION_CODE );
        }
    private void CaptureImage() {
        openSomeActivityForResult();
    }





    public void openSomeActivityForResult() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        someActivityResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {


                        // There are no request codes
                        Intent data = result.getData();


                        // binding data on view elements
                        Bundle extras  = data.getExtras();
                        imageBitmap = (Bitmap) extras.get("data");
                        captured.setImageBitmap((imageBitmap));
                        String textResult = DetectText();
                        textResult = "TEST";
                        SaveAndDirect(textResult);
                        
                        // on detection text call save and direction to webpage activity 

                    }
                }
            });

    private void SaveAndDirect(String pageCode) {

        Page page = pageDAO.getPageFromPageCode(pageCode);




        if(page == null)
        {

            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            View view = layoutInflater.inflate(R.layout.layout_savedialog,null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(scannerActivity.this);
            alertDialogBuilder.setView(view);


            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            Button LinkIt = view.findViewById(R.id.BTN_link);
            EditText EDIT_URL = view.findViewById(R.id.EDIT_URL);
            LinkIt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(scannerActivity.this,"LInk IT working result is: "+ pageCode,Toast.LENGTH_SHORT).show();

                    Page page = new Page(pageCode,EDIT_URL.getText().toString());
                    pageDAO.addPage(page);
                    alertDialog.dismiss();
                }
            });

        }
        else
        {
            Toast.makeText(scannerActivity.this,"redirecting to: "+ page.getUrl(),Toast.LENGTH_SHORT).show();
            String url = page.getUrl();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        }







    }


}