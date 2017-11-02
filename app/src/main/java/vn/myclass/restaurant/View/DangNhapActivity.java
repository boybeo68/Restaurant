package vn.myclass.restaurant.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import vn.myclass.restaurant.R;

/**
 * Created by boybe on 10/14/2017.
 */

public class DangNhapActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, FirebaseAuth.AuthStateListener {
    SignInButton btnDangNhapGoogle;
    GoogleApiClient mGoogleApiClient;
    public static int REQUEST_CODE = 3;
    LoginButton btnDangnhapFacebook;
    public static int CHECK_PROVIDER_DANGNHAP = 0;
    private FirebaseAuth mAuth;
    CallbackManager callbackManager;
    LoginManager loginManager;
    String TAG = "DangNhapAcitivty";
    List<String> permissionFacebook = Arrays.asList("email", "public_profile");
    TextView txtHoac,txtDangKy,txtQuenPass;
    EditText edEmailDN,edPassDN;
    Button btnDangNhap;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.layout_dangnhap);

        mAuth = FirebaseAuth.getInstance();
        LoginManager.getInstance().logOut();
        mAuth.signOut();

        txtHoac = (TextView) findViewById(R.id.txtHoac);
        btnDangNhapGoogle = (SignInButton) findViewById(R.id.btnDangNhapGoogle);
        btnDangnhapFacebook = (LoginButton) findViewById(R.id.btnDangNhapFacebook);
        txtDangKy= (TextView) findViewById(R.id.txtDangKy);
        txtQuenPass= (TextView) findViewById(R.id.txtQuenPass);
        edEmailDN= (EditText) findViewById(R.id.edEmailDN);
        edPassDN= (EditText) findViewById(R.id.edPassDN);
        btnDangNhap= (Button) findViewById(R.id.btnDangNhap);


        btnDangNhapGoogle.setOnClickListener(this);
        btnDangnhapFacebook.setOnClickListener(this);
        txtDangKy.setOnClickListener(this);
        btnDangNhap.setOnClickListener(this);
        txtQuenPass.setOnClickListener(this);
        taoClientDangNhapGoogle();

    }
    public void dangNhapFacebook(){
        btnDangnhapFacebook.setReadPermissions(Arrays.asList("public_profile", "email"));
        btnDangnhapFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
//                CHECK_PROVIDER_DANGNHAP=2;
//                String tokenId=loginResult.getAccessToken().getToken();
//                chungThucFireBase(tokenId);
                Log.d(TAG, "signInWithCredential:success");
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("facebook:onError", error.toString());
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(DangNhapActivity.this, " Success.",
                                    Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "signInWithCredential:success");

                        } else {
                            Toast.makeText(DangNhapActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                        }


                    }
                });
    }

    //Khởi tạo client cho đăng nhập google
    private void taoClientDangNhapGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }
    //end Khởi tạo client cho đăng nhập google

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(this);
    }

    //Mở form đăng nhập bằng google
    private void dangNhapGoogle(GoogleApiClient mGoogleApiClient) {
        CHECK_PROVIDER_DANGNHAP = 1;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, REQUEST_CODE);
    }
    //end Mở form đăng nhập bằng google

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                GoogleSignInAccount account = result.getSignInAccount();
                String tokenId = account.getIdToken();
                chungThucFireBase(tokenId);
            }

        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    //Lây tokenId đã đăng nhập bằng google để đăng nhập trên Firebase
    private void chungThucFireBase(String tokenId) {
        if (CHECK_PROVIDER_DANGNHAP == 1) {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(tokenId, null);
            mAuth.signInWithCredential(authCredential);
        } else if (CHECK_PROVIDER_DANGNHAP == 2) {
            AuthCredential authCredential = FacebookAuthProvider.getCredential(tokenId);
            mAuth.signInWithCredential(authCredential);
        }

    }
    //end Lây tokenId đã đăng nhập bằng google để đăng nhập trên Firebase

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //lắng nghe sự kiện người dùng click vào đăng nhập google
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnDangNhapGoogle:
                dangNhapGoogle(mGoogleApiClient);
                break;
            case R.id.btnDangNhapFacebook:
                dangNhapFacebook();
                break;
            case R.id.txtDangKy:
                Intent intent=new Intent(DangNhapActivity.this,DangKy_Activity.class);
                startActivity(intent);
                break;
            case R.id.btnDangNhap:
                dangNhapEmail();
                break;
            case R.id.txtQuenPass:
                Intent iresetPass=new Intent(DangNhapActivity.this,Resetpass_Activity.class);
                startActivity(iresetPass);
                break;
        }
    }

    private void dangNhapEmail() {

        String email = edEmailDN.getText().toString();
        String pass = edPassDN.getText().toString();
        String thongbao="";
        if (email.trim().length() == 0) {
            thongbao = getString(R.string.VuiLongNhap) + " " + getString(R.string.email);
            Toast.makeText(DangNhapActivity.this, thongbao, Toast.LENGTH_SHORT).show();
        }else if (pass.trim().length() == 0){
            thongbao = getString(R.string.VuiLongNhap) + " " + getString(R.string.MatKhau);
            Toast.makeText(DangNhapActivity.this, thongbao, Toast.LENGTH_SHORT).show();
        }else {
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(DangNhapActivity.this,getString(R.string.TaiKhoanKhongHopLe),Toast.LENGTH_SHORT).show();
                    }else {
//                        progressDialog.dismiss();
                    }
                }
            });
        }


//        Toast.makeText(DangNhapActivity.this,"test nút",Toast.LENGTH_SHORT).show();
    }
    //end lắng nghe sự kiện người dùng click vào đăng nhập google


    //Sự kiện kiểm tra xem người dùng đã đăng nhập thành công hay đăng xuất
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Intent iTrangChu = new Intent(DangNhapActivity.this, Trangchu_Activity.class);
            startActivity(iTrangChu);
//            Toast.makeText(this,user.getEmail(),Toast.LENGTH_LONG).show();
        } else {

        }
    }
}
//end
