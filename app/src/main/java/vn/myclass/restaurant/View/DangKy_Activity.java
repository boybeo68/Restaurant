package vn.myclass.restaurant.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import vn.myclass.restaurant.Controller.Dangki_Controller;
import vn.myclass.restaurant.Model.ThanhVienModel;
import vn.myclass.restaurant.R;

/**
 * Created by boybe on 10/18/2017.
 */

public class DangKy_Activity extends AppCompatActivity {
    Button btnDangKy;
    EditText edEmailDK, edPassDK, edNhapLaiPassDK;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    ProgressDialog progressDialog;
    Dangki_Controller dangki_controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangki);
        btnDangKy = (Button) findViewById(R.id.btnDangKy);
        edEmailDK = (EditText) findViewById(R.id.edEmailDK);
        edPassDK = (EditText) findViewById(R.id.edPassDK);
        edNhapLaiPassDK = (EditText) findViewById(R.id.edNhapLaiPassDK);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage(getString(R.string.VuiLongCho));
                progressDialog.setIndeterminate(true);
                progressDialog.show();
                String email = edEmailDK.getText().toString();
                String pass = edPassDK.getText().toString();
                String nhapLaiPass = edNhapLaiPassDK.getText().toString();
                String thongbao = "";
                if (email.trim().length() == 0) {
                    progressDialog.dismiss();
                    thongbao = getString(R.string.VuiLongNhap) + " " + getString(R.string.email);
                    Toast.makeText(DangKy_Activity.this, thongbao, Toast.LENGTH_SHORT).show();
                } else if (email.matches("[a-z\\d]+([\\.\\_]?[a-z\\d]+)+@[a-z\\d]+(\\.[a-z]+)+") == false) {
                    progressDialog.dismiss();
                    thongbao = getString(R.string.EmailKhongHopLe);
                    Toast.makeText(DangKy_Activity.this, thongbao, Toast.LENGTH_SHORT).show();
                } else if (pass.trim().length() == 0) {
                    progressDialog.dismiss();
                    thongbao = getString(R.string.VuiLongNhap) + " " + getString(R.string.MatKhau);
                    Toast.makeText(DangKy_Activity.this, thongbao, Toast.LENGTH_SHORT).show();
                } else if (pass.trim().length() < 6) {
                    progressDialog.dismiss();
                    thongbao = "Mật khẩu không được < 6 ký tự";
                    Toast.makeText(DangKy_Activity.this, thongbao, Toast.LENGTH_SHORT).show();
                } else if (nhapLaiPass.trim().length() == 0) {
                    progressDialog.dismiss();
                    thongbao = getString(R.string.VuiLongNhap) + " lại " + getString(R.string.MatKhau);
                    Toast.makeText(DangKy_Activity.this, thongbao, Toast.LENGTH_SHORT).show();
                } else if (!nhapLaiPass.equals(pass)) {
                    progressDialog.dismiss();
                    thongbao = getString(R.string.NhapLaiPass) + " " + getString(R.string.KhongHopLe);
                    Toast.makeText(DangKy_Activity.this, thongbao, Toast.LENGTH_SHORT).show();
                } else {
                    CreadteAcout(email, pass);
                }

            }
        });
    }

    private void CreadteAcout(final String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(DangKy_Activity.this, getString(R.string.success),
                            Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(DangKy_Activity.this,DangNhapActivity.class);
                    startActivity(intent);

                }
            }
        });
    }
}
