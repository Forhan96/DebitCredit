package com.forhan.debitcredit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    public static final String loginURL = "https://www.thecakeshopbd.com/appsapi/login/checkUserLoginAdmin";

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    EditText usernameET, passwordET;
    Button signInBtn;
    String userId, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = getSharedPreferences("login", MODE_PRIVATE);

//        if (sp.getBoolean("logged", false)) {
//            Intent intent = new Intent(LoginActivity.this, UserDataActivity.class);
//            startActivity(intent);
//        }

        usernameET = findViewById(R.id.user_name_et_id);
        passwordET = findViewById(R.id.password_et_id);
        signInBtn = findViewById(R.id.btn_sign_in_id);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit();
            }
        });
    }

    public static final String getmd5ofstring(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString().toUpperCase(Locale.US); // return md5

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void Submit() {
        final String username = usernameET.getText().toString();
        final String pass = passwordET.getText().toString();
        final String password = getmd5ofstring(pass).toUpperCase(Locale.US); // get md5

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Volley Result", "" + response); //the response contains the result from the server, a json string or any other object returned by your server
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    role = (String) jsonObject.get("role");
                    userId = (String) jsonObject.get("userId");
                    Log.i("Role", role);

                    if (role.equals("1")){
                        adminLogin();
                    }
                    else if (role.equals("2")){
                        userLogin();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"No role found",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void userLogin() {
        Intent intent = new Intent(LoginActivity.this, UserActivity.class);
        startActivity(intent);
        editor = sp.edit();
        editor.putBoolean("logged", true).apply();
        editor.putString("userId", userId).apply();
        editor.putString("role", role).apply();
    }

    private void adminLogin() {
        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
        startActivity(intent);
        editor = sp.edit();
        editor.putBoolean("logged", true).apply();
        editor.putString("userId", userId).apply();
        editor.putString("role", role).apply();
    }
}