package com.spiderindia.departmentsofhighway.Fragmentss;


import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.spiderindia.departmentsofhighway.R;
import com.spiderindia.departmentsofhighway.SqLiteDb.MyDataBaseHandler;
import com.spiderindia.departmentsofhighway.YearPicker.YearPickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBridgeNewFragment extends Fragment {

    Button nextBttn,chooseDocBttn;

    EditText descriptionEdtTxt,locationEdtTxt,startChainageEdtTxt,brdgNoEdtTxt,brdgEdtTxt,yrOfConstrEdtTxt,construcCostEdtTxt,condtnSurvyDateEdtTxt, bridge_key_id;

    String choosenDate,choosenTime,response="", photoPath, filePath;
    Context mContext;
    LinearLayout progress_layout, key_id;
    ProgressBar loading_process;
    YearPickerDialog yearPickerDialog;

    private static final int REQUEST_FOR_PERMISSION = 1;
    ImageView firstImage,secondImag;
    Intent intent;
    private static final int CAMERA_PHOTO = 111;
    private static final int CAMERA_SECOND_PHOTO = 112;
    private Uri imageToUploadUri;
    private Uri secondImageToUploadUri;
    private byte[] imageArray = null,secondImageArray = null;
    String selectedImagePath="",imagePath="",secondSelectedImagePath="",secondImagePath="", encodedFirstImagePath="",encodedSecondImagePath="",encodedDocumentPath="";
    public static int RESULT_LOAD_IMAGE = 1;
    public static int RESULT_SECOND_LOAD_IMAGE = 2;
    Dialog dialog;
    Button chooseFileBttn,chooseSecondFileBttn;
    MyDataBaseHandler dbase;
    String circleId="",divisionId,subDivisionId,roadId,linkId,firstlinkId="",description,location,startChainage,brdgeNo,brdge,yrOfContrctn,contrctnCost,contrctnDate,documentPath,finalDocumentName="No File Choosen";
    private static final int PICKFILE_RESULT_CODE = 5;

    TextView documentNameTxt;

    String responseCircleHolder="",userId,authenticationCode,responseDivision="",responseSubDivision="",responseRoad="",responseLinkId="",firstTimeAfterLogin="";
    SharedPreferences spf;

    @Override
    public void onStart() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Modify",Context.MODE_PRIVATE);

        String data = sharedPreferences.getString("data", "false");

        if (data.equalsIgnoreCase("true")) {

            key_id.setVisibility(View.VISIBLE);

        } else {
            key_id.setVisibility(View.GONE);
        }

        super.onStart();
    }

    @Override
    public void onResume() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Modify",Context.MODE_PRIVATE);

        String data = sharedPreferences.getString("data", "false");

        if (data.equalsIgnoreCase("true")) {

            key_id.setVisibility(View.VISIBLE);

        } else {
            key_id.setVisibility(View.GONE);
        }
        super.onResume();
    }



    public AddBridgeNewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_add_bridge_new, container, false);

        firstImage=(ImageView)root.findViewById(R.id.profile_img);
        secondImag=(ImageView)root.findViewById(R.id.second_img);
        chooseFileBttn=(Button)root.findViewById(R.id.choose_File_bttn_doc);
        chooseSecondFileBttn=(Button)root.findViewById(R.id.choose_second_photo);

        spf = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userId = spf.getString("userId", "");
        authenticationCode = spf.getString("authenticationToken", "");
        firstTimeAfterLogin = spf.getString("firstTimeAfterLogin", "");

        nextBttn=(Button)root.findViewById(R.id.add_brdg_next_bttn);


        descriptionEdtTxt=(EditText)root.findViewById(R.id.descrp_edtTxt);
        locationEdtTxt=(EditText)root.findViewById(R.id.location_edtTxt);
        startChainageEdtTxt=(EditText)root.findViewById(R.id.chainage_edtTxt);
        brdgNoEdtTxt=(EditText)root.findViewById(R.id.bridgeNo_edtTxt);
        bridge_key_id=(EditText) root.findViewById(R.id.bridge_key_id);

        brdgEdtTxt=(EditText)root.findViewById(R.id.bridge_edtTxt);
        yrOfConstrEdtTxt=(EditText)root.findViewById(R.id.yr_of_constrctn_edtTxt);
        construcCostEdtTxt=(EditText)root.findViewById(R.id.constrctn_cost_edtTxt);
        condtnSurvyDateEdtTxt=(EditText)root.findViewById(R.id.constrctn_survey_date_edtTxt);
        chooseDocBttn=(Button) root.findViewById(R.id.choose_doc_bttn);
        documentNameTxt=(TextView) root.findViewById(R.id.document_nam_Txt);



        key_id = (LinearLayout) root.findViewById(R.id.key_id);


        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        condtnSurvyDateEdtTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = (EditText) v;
                editText.setOnFocusChangeListener(v.getOnFocusChangeListener());
                editText.setCursorVisible(false);

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                DecimalFormat formatter = new DecimalFormat("00");

                                choosenDate=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                condtnSurvyDateEdtTxt.setText(choosenDate);

                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });


        yrOfConstrEdtTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                yearPickerDialog.show();
            }
        });

        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear,mMonth,mDay);

        yearPickerDialog = new YearPickerDialog(getActivity(), calendar, new YearPickerDialog.OnDateSetListener() {
            @Override
            public void onYearMonthSet(int year, int month) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");

                yrOfConstrEdtTxt.setText(dateFormat.format(calendar.getTime()));

                //choosenYear=dateFormat.format(calendar.getTime())+"";

            }
        });


        chooseFileBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PermissionRequest("FirstImage");

            }
        });

        chooseSecondFileBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PermissionRequest("SecondImage");

            }
        });

        chooseDocBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                Intent i = Intent.createChooser(intent, "File");
                startActivityForResult(i, PICKFILE_RESULT_CODE);

            }
        });




        return root;
    }
    public void PermissionRequest(String imageIdentity)
    {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_FOR_PERMISSION);
        }
        else if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA},REQUEST_FOR_PERMISSION);
        }
        else
        {
            alertBoxToChooseImage(imageIdentity);


        }

    }

    private void alertBoxToChooseImage(String imageIdentity) {

        dialog = new Dialog(getActivity(), R.style.FullHeightDialog);
        dialog.setContentView(R.layout.popup_to_choose_image);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
        ImageView closeImg = (ImageView) dialog.findViewById(R.id.close_icon_);
        LinearLayout cameraLL=(LinearLayout)dialog.findViewById(R.id.camera_layout);
        LinearLayout galleryLL=(LinearLayout)dialog.findViewById(R.id.gallery_ll);

        final String image=imageIdentity;
        cameraLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                captureCameraImage(image);
            }
        });
        galleryLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery(image);
            }
        });
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

    }

    private void captureCameraImage(String imageIdentity) {

        if(imageIdentity.equalsIgnoreCase("FirstImage"))
        {
            Intent chooserIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File f = new File(Environment.getExternalStorageDirectory(), "POST_IMAGE.jpg");
            chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            imageToUploadUri = Uri.fromFile(f);
            startActivityForResult(chooserIntent, CAMERA_PHOTO);
        }
        else
        {
            Intent chooserIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File f = new File(Environment.getExternalStorageDirectory(), "POST_IMAGE.jpg");
            chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            secondImageToUploadUri = Uri.fromFile(f);
            startActivityForResult(chooserIntent, CAMERA_SECOND_PHOTO);
        }

    }
    public void openGallery(String imageIdentity) {

        if(imageIdentity.equalsIgnoreCase("FirstImage"))
        {
            Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMAGE);
        }
        else
        {
            Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_SECOND_LOAD_IMAGE);
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == CAMERA_PHOTO && resultCode == RESULT_OK) {
                if(imageToUploadUri != null){
                    Uri selectedImage = imageToUploadUri;

                    photoPath = getRealpathfromUri(selectedImage);
                    Toast.makeText(getActivity(), ""+photoPath, Toast.LENGTH_SHORT).show();
                    mContext.getContentResolver().notifyChange(selectedImage, null);
                    selectedImagePath=imageToUploadUri.getPath();


                    Bitmap reducedSizeBitmap = getBitmap(imageToUploadUri.getPath());
                    if(reducedSizeBitmap != null){
                        firstImage.setImageBitmap(reducedSizeBitmap);
                        dialog.dismiss();
                    }else{
                        Toast.makeText(getActivity(),"Error while capturing Image",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"Error while capturing Image",Toast.LENGTH_LONG).show();
                }
            }
            else if (requestCode == CAMERA_SECOND_PHOTO && resultCode == RESULT_OK) {
                if(secondImageToUploadUri != null){
                    Uri selectedImage = secondImageToUploadUri;

                    photoPath = getRealpathfromUri(selectedImage);
                    Toast.makeText(getActivity(), ""+photoPath, Toast.LENGTH_SHORT).show();
                    mContext.getContentResolver().notifyChange(selectedImage, null);
                    secondSelectedImagePath=secondImageToUploadUri.getPath();


                    Bitmap reducedSizeBitmap = getBitmap(secondImageToUploadUri.getPath());
                    if(reducedSizeBitmap != null){
                        secondImag.setImageBitmap(reducedSizeBitmap);
                        dialog.dismiss();
                    }else{
                        Toast.makeText(getActivity(),"Error while capturing Image",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"Error while capturing Image",Toast.LENGTH_LONG).show();
                }
            }

            else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();

                photoPath = getRealpathfromUri(selectedImage);
                Toast.makeText(getActivity(), ""+photoPath, Toast.LENGTH_SHORT).show();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                imageArray = imageToByteArray(new File(picturePath));
                selectedImagePath=picturePath;
                setImagePicture(picturePath,"first_img");
            }
            else if (requestCode == RESULT_SECOND_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                photoPath = getRealpathfromUri(selectedImage);
                Toast.makeText(getActivity(), ""+photoPath, Toast.LENGTH_SHORT).show();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                secondImageArray = imageToByteArray(new File(picturePath));
                secondSelectedImagePath=picturePath;
                setImagePicture(picturePath,"second_img");
            }
            else if (requestCode == PICKFILE_RESULT_CODE && resultCode == RESULT_OK && null != data) {
                documentPath = data.getData().getPath();
                Uri path = data.getData();

                filePath = path.getPath();

                Toast.makeText(getActivity(),""+filePath,Toast.LENGTH_LONG).show();

                try {
                    String myString = documentPath;
                    finalDocumentName = myString.substring(myString.lastIndexOf("/")+1, myString.indexOf("."));

                    documentNameTxt.setText(finalDocumentName);



                }
                catch (Exception e)
                {

                }

            }
            else {

            }

        }
        catch (Exception e)
        {

        }

        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("photoPath",photoPath);
        editor.putString("filePath",photoPath);

        editor.apply();
    }

    private String getRealpathfromUri(Uri selectedImage) {

        String projection = (MediaStore.Images.Media.DATA);
        CursorLoader cursorLoader = new CursorLoader(getActivity(),selectedImage, new String[]{projection}, null,null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        int column_indx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_indx);
        cursor.close();
        return result;
    }

    private Bitmap getBitmap(String path) {

        Uri uri = Uri.fromFile(new File(path));
        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
            in = getActivity().getContentResolver().openInputStream(uri);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();


            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }
            Log.d("", "scale = " + scale + ", orig-width: " + o.outWidth + ", orig-height: " + o.outHeight);

            Bitmap b = null;
            in = getActivity().getContentResolver().openInputStream(uri);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();
                Log.d("", "1th scale operation dimenions - width: " + width + ", height: " + height);

                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                        (int) y, true);
                b.recycle();
                b = scaledBitmap;

                System.gc();
            } else {
                b = BitmapFactory.decodeStream(in);
            }
            in.close();

            Log.d("", "bitmap size - width: " + b.getWidth() + ", height: " +
                    b.getHeight());
            return b;
        } catch (IOException e) {
            Log.e("", e.getMessage(), e);
            return null;
        }
    }

    private byte[] imageToByteArray(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            // create FileInputStream which obtains input bytes from a file in a
            // file system
            // FileInputStream is meant for reading streams of raw bytes such as
            // image data. For reading streams of characters, consider using
            // FileReader.

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];

            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                // Writes to this byte array output stream
                bos.write(buf, 0, readNum);
            }
            byte[] bytes = bos.toByteArray();
            return bytes;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void setImagePicture(String photoPath, String imgIdentity) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);

        if(imgIdentity.equalsIgnoreCase("first_img"))
        {
            firstImage.setImageBitmap(bitmap);
        }
        else
        {
            secondImag.setImageBitmap(bitmap);
        }



        dialog.dismiss();
    }


}
