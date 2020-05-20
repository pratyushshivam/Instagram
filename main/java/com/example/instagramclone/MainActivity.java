package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.LogOutCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener {
    EditText usernameEditText;
    EditText passwordEditText;


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.share_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.logout) {
            finish();
            return true;
        }return true;
    }



    public void showUserList()
    {
        Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
        startActivity(intent);
    }


    Boolean signUpModeActive=true;
    public void loginClick(View view)
    {
       ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
           @Override
           public void done(ParseUser user, ParseException e) {
               if(user!=null)
               {
                   Toast.makeText(MainActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                   showUserList();
               }
               else
               {
                   Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
               }
           }
       });
    }






    public void signUpClicked(View view)
    {

        if(usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches(""))
        {
            Toast.makeText(this, "Username/Password is required!", Toast.LENGTH_SHORT).show();
        }
        else if(passwordEditText.getText().toString().length()>1 && passwordEditText.getText().toString().length()<5)
        {
            Toast.makeText(this, "The Password is weak", Toast.LENGTH_SHORT).show();
        }
        else if (usernameEditText.getText().toString().matches(" ") || passwordEditText.getText().toString().matches(" "))
        {
            Toast.makeText(this, "Username/Password cannot be empty!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ParseUser user = new ParseUser();
            user.setUsername(usernameEditText.getText().toString());
            user.setPassword(passwordEditText.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null)
                    {
                        Log.i("sign", "Sign Up Success");
                        showUserList();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordEditText.setOnKeyListener(this);
        ImageView logoImageView = findViewById(R.id.logoImageView);
        RelativeLayout backgroundLayout = findViewById(R.id.backgroundLayout);


        //logoImageView.setOnClickListener(this);
        logoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usernameEditText.getText().toString().equals("") && passwordEditText.getText().toString().equals(""))
                {
                    Toast.makeText(MainActivity.this, "Enter the details first asshole", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        });
      //  backgroundLayout.setOnClickListener(this);
        backgroundLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usernameEditText.getText().toString().equals("") && passwordEditText.getText().toString().equals(""))
                {
                    Toast.makeText(MainActivity.this, "Enter the details first asshole", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }

            }
        });

        if(ParseUser.getCurrentUser()!=null)
        {
            showUserList();
        }


        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event)
    {
        // hey! when someone hits the key of keyboard, how are we going to handle the situation?

        if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN)
        {
            signUpClicked(v);
        }
        return false;
    }

}


