package com.chefling.project.tushar_ui;

import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView button_img;
    static Bitmap bitmap=null;
    TextView title;
    TextView tcancel;
    EditText trecipe;
    EditText crecipe;
    EditText notes;
    Button button_begin;
    Button button_chef;
    Button button_master;
    Button button_next;
    TextView text_serves;
    TextView text_cook;
    TextView pageno;
    static TextView pretv = null;
    static TextView ppretv = null;
    static TextView pppretv = null;
    ListView dialog_ListView_zero;
    ListView dialog_ListView_one;
    ListView dialog_ListView_two;
    static final int CUSTOM_DIALOG_RECIPE_ID = 0;
    static final int CUSTOM_DIALOG_SERVES_ID = 1;
    static final int CUSTOM_DIALOG_COOK_ID = 2;
    static final int CUSTOM_DIALOG_NEXT_ID = 3;
    static int ID = 0;
    String hours = "";
    String min = "";
    String serves = "";
    String recipe = "";
    String[] listServes = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10+"};
    String[] listRecipe = {"Breakfast", "Desert", "Salads"};
    String[] listHours = {"0", "1", "2", "3", "4"};
    String[] listMin = {"5", "10", "15", "20", "25"};

    static String user_info[];
    private static int IMG_RESULT = 1;
    String ImageDecode;
    ImageView imageViewLoad;
    Button LoadImage;
    Intent intent;
    String[] FILE;

    //    static Object j = null;
    String img, imgstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(myToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

//        this.getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        imageViewLoad = (ImageView) findViewById(R.id.profilePic);
        button_img = (ImageView) findViewById(R.id.img_set);
        title = (TextView) findViewById(R.id.textTitle);
        tcancel = (TextView) findViewById(R.id.textCancel);
        trecipe = (EditText) findViewById(R.id.editRecipe);
        crecipe = (EditText) findViewById(R.id.editCrecipe);
        button_begin = (Button) findViewById(R.id.button_beginner);
        button_chef = (Button) findViewById(R.id.button_chef);
        button_master = (Button) findViewById(R.id.button_master);
        button_next = (Button) findViewById(R.id._next);
        text_serves = (TextView) findViewById(R.id.text_serves);
        text_cook = (TextView) findViewById(R.id.text_cook);
        pageno = (TextView) findViewById(R.id.textPageno);
        button_begin.setTextColor(getResources().getColor(R.color.colorWhite));
        setTypefaceAll();
        trecipe.requestFocus();
        //load previous image

        try {
            SharedPreferences sharedPref1 = this.getSharedPreferences("sharedImage", Context.MODE_PRIVATE);
            imgstr = sharedPref1.getString("namePreferance", "");
            img = sharedPref1.getString("imagePreferance", "");
            if (!imgstr.isEmpty() || !img.isEmpty())
                imageViewLoad.setImageBitmap(decodeBase64(img));
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void setTypefaceAll() {
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueLTStd-Roman.ttf");
        title.setTypeface(custom_font);
        tcancel.setTypeface(custom_font);
        trecipe.setTypeface(custom_font);
        crecipe.setTypeface(custom_font);
        button_begin.setTypeface(custom_font);
        button_chef.setTypeface(custom_font);
        button_master.setTypeface(custom_font);
        button_next.setTypeface(custom_font);
        text_serves.setTypeface(custom_font);
        text_cook.setTypeface(custom_font);
        pageno.setTypeface(custom_font);

    }

    public void setImage(View view) {
        new LongOperation().execute("");
        }


    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, IMG_RESULT);

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            // txt.setText(result);
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }


    public void feedback(View view)
    {
        showDialog(99);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == IMG_RESULT && resultCode == RESULT_OK && null != data) {
                Uri URI = data.getData();
                bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(URI));
                try {
                    SharedPreferences sharedImgPref = getApplicationContext().getSharedPreferences("sharedImage", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedImgPref.edit();
                    editor.putString("namePreferance", "Profile Pic");
                    editor.putString("imagePreferance", encodeTobase64(bitmap));
                    editor.apply();
                    imageViewLoad.setImageBitmap(bitmap);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }
    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        ID = id;
        Dialog dialog = null;

        switch(id) {
            case CUSTOM_DIALOG_SERVES_ID:
                dialog = addServes(listServes, null);
                break;
            case CUSTOM_DIALOG_RECIPE_ID:
                dialog = addServes(listRecipe, null);
                break;
            case CUSTOM_DIALOG_COOK_ID:
                dialog = addServes(listHours, listMin);
                break;
            case 99:
                dialog = addServes(null, null);
                break;
        }

        return dialog;
    }
    @Override
    protected void onPrepareDialog(int id, Dialog dialog, Bundle bundle) {
        ID = id;
        super.onPrepareDialog(id, dialog, bundle);
        switch(id) {
            case CUSTOM_DIALOG_SERVES_ID:
                break;
            case CUSTOM_DIALOG_RECIPE_ID:
                break;
            case CUSTOM_DIALOG_COOK_ID:
                break;
            case CUSTOM_DIALOG_NEXT_ID:
                break;
            case 99:
                break;
        }
    }
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.editCrecipe:
            {
//                recipe = "";
//                try {
//                    SharedPreferences sharedPref1 = this.getSharedPreferences("sharedImage", Context.MODE_PRIVATE);
//                    imgstr = sharedPref1.getString("namePreferance", "");
//                    img = sharedPref1.getString("imagePreferance", "");
//                    if (!imgstr.isEmpty() || !img.isEmpty())
//                        imageViewLoad.setImageBitmap(decodeBase64(img));
//                } catch (Exception e) {
//                    Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
//                }
                showDialog(CUSTOM_DIALOG_RECIPE_ID);
                break;
            }
            case R.id.button_beginner:
            {
                button_begin.setTextColor(getResources().getColor(R.color.colorWhite));
                button_chef.setTextColor(getResources().getColor(R.color.colorText));
                button_master.setTextColor(getResources().getColor(R.color.colorText));

                button_begin.setBackgroundDrawable(getResources().getDrawable(R.drawable.inv_button_shape));
                button_chef.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape));
                button_master.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape));
                break;
            }
            case R.id.button_chef:
            {
                button_begin.setTextColor(getResources().getColor(R.color.colorText));
                button_chef.setTextColor(getResources().getColor(R.color.colorWhite));
                button_master.setTextColor(getResources().getColor(R.color.colorText));

                button_chef.setBackgroundDrawable(getResources().getDrawable(R.drawable.inv_button_shape));
                button_begin.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape));
                button_master.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape));
                break;
            }
            case R.id.button_master:
            {
                button_begin.setTextColor(getResources().getColor(R.color.colorText));
                button_chef.setTextColor(getResources().getColor(R.color.colorText));
                button_master.setTextColor(getResources().getColor(R.color.colorWhite));

                button_master.setBackgroundDrawable(getResources().getDrawable(R.drawable.inv_button_shape));
                button_chef.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape));
                button_begin.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape));
                break;
            }
            case R.id.text_serves:
            {
                serves = "";
                showDialog(CUSTOM_DIALOG_SERVES_ID);
                break;
            }
            case R.id.text_cook:
            {
                hours = "";
                min = "";
                showDialog(CUSTOM_DIALOG_COOK_ID);
                break;
            }
            case R.id._next:
            {
                showDialog(99);
                break;
            }
        }

    }

    public Dialog addServes(String[] list, String[] list_one)
    {
         Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueLTStd-Roman.ttf");
         final Dialog dialog = new Dialog(MainActivity.this);
        final TextView cancel, done;
        hours="";
        min="";
        serves="";
        recipe="";
        if(ID==CUSTOM_DIALOG_COOK_ID)
        {
            if(!text_cook.getText().toString().equals("Cooking Time"))
                text_cook.setText("");
            dialog.setContentView(R.layout.cooking_time_layout);
            dialog.setTitle("Custom Dialog");
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener(){
                @Override
                public void onCancel(DialogInterface dialog) {
                    if(dialog_ListView_zero!=null)
                        dialog_ListView_zero.clearChoices();
                    if(dialog_ListView_one!=null)
                        dialog_ListView_one.clearChoices();
                    if(dialog_ListView_two!=null)
                        dialog_ListView_two.clearChoices();

                }});

            cancel = (TextView)dialog.findViewById(R.id.textCancel_two);
            done  = (TextView)dialog.findViewById(R.id.textDone_two);

            ListAdapter tushAdapter = new CustomAdapter(this, list, custom_font);
            dialog_ListView_zero = (ListView)dialog.findViewById(R.id.listHours);
            dialog_ListView_zero.setAdapter(tushAdapter);
            dialog_ListView_zero.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(pretv!=null)
                    {
                        pretv.setText(pretv.getText().toString().replace("hours","").replace(" ",""));
                    }
                    TextView tv = (TextView)view.findViewById(R.id.tushText);
                    tv.setText(tv.getText().toString().replace("hours","").replace(" ","")+" hours");
                    pretv = tv;
                    final Object j = parent.getItemAtPosition(position);
                    hours = j.toString()+" hours";
                }});

            ListAdapter tushAdapter_one = new CustomAdapter(this, list_one, custom_font);
            dialog_ListView_one = (ListView)dialog.findViewById(R.id.listMin);
            dialog_ListView_one.setAdapter(tushAdapter_one);
            dialog_ListView_one.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(ppretv!=null)
                    {
                        ppretv.setText(ppretv.getText().toString().replace("min","").replace(" ",""));
                    }
                    TextView tv = (TextView)view.findViewById(R.id.tushText);
                    tv.setText(tv.getText().toString().replace("min", "").replace(" ","")+" min");
                    ppretv = tv;
                    final Object j = parent.getItemAtPosition(position);
                    min = j.toString()+" min";

                }});
        }
        else if(ID==99)
        {
            dialog.setContentView(R.layout.rating);
            dialog.setTitle("Custom Dialog");
            Button submit = (Button)dialog.findViewById(R.id.button_sub);
            submit.setTypeface(custom_font);
            submit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            return dialog;
        }
        else
        {
            dialog.setContentView(R.layout.dialoglayout);
            dialog.setTitle("Custom Dialog");
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener(){
                @Override
                public void onCancel(DialogInterface dialog) {
                    if(pppretv!=null)
                    {
                        pppretv.setTextColor(getResources().getColor(R.color.colorDotted));
                    }
                    Toast.makeText(MainActivity.this,
                            "OnCancelListener",
                            Toast.LENGTH_LONG).show();
                }});

            cancel = (TextView)dialog.findViewById(R.id.textCancel_one);
            done  = (TextView)dialog.findViewById(R.id.textDone_one);
            ListAdapter tushAdapter = new CustomAdapter(this, list, custom_font);
            dialog_ListView_two = (ListView)dialog.findViewById(R.id.dialoglist);
            dialog_ListView_two.setAdapter(tushAdapter);
            dialog_ListView_two.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    pppretv = (TextView)view.findViewById(R.id.tushText);
                    final Object j = parent.getItemAtPosition(position);
                    if(ID==CUSTOM_DIALOG_SERVES_ID)
                    {
                        serves = j.toString();
                    }
                    else if(ID==CUSTOM_DIALOG_RECIPE_ID)
                    {
                        recipe = j.toString();
                    }
                }});
        }



        done.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(!hours.isEmpty()|| !min.isEmpty())
                    {
                        if(!hours.isEmpty()&&!min.isEmpty())
                        text_cook.setText(hours+" "+min);
                        else if(!hours.isEmpty())
                        {
                            text_cook.setText(hours);
                        }else if(!min.isEmpty())
                        {
                            text_cook.setText(min);
                        }
                    }
                    if(!serves.isEmpty())
                        text_serves.setText(serves);
                    if(!recipe.isEmpty())
                        crecipe.setText(recipe);


                if(dialog_ListView_zero!=null)
                dialog_ListView_zero.clearChoices();
                if(dialog_ListView_one!=null)
                dialog_ListView_one.clearChoices();
                if(dialog_ListView_two!=null)
                dialog_ListView_two.clearChoices();

                dismissDialog(ID);
            }
        }));

        cancel.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dialog_ListView_zero!=null)
                    dialog_ListView_zero.clearChoices();
                if(dialog_ListView_one!=null)
                    dialog_ListView_one.clearChoices();
                if(dialog_ListView_two!=null)
                    dialog_ListView_two.clearChoices();
                dismissDialog(ID);
            }
        }));
        return dialog;

    }
}
