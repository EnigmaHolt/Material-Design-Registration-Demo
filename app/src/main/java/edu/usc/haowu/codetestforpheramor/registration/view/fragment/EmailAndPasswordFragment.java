package edu.usc.haowu.codetestforpheramor.registration.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.usc.haowu.codetestforpheramor.R;
import edu.usc.haowu.codetestforpheramor.registration.event.EmailPasswordEvent;

import static android.app.Activity.RESULT_OK;

/**
 * @author Hao Wu
 * @time 2018/06/21
 * @since
 */
public class EmailAndPasswordFragment extends Fragment {
    @BindView(R.id.profile_image)
    CircleImageView avator;
    @BindView(R.id.input_email)
    TextInputLayout email;
    @BindView(R.id.input_password)
    TextInputLayout password;
    @BindView(R.id.input_repassword)
    TextInputLayout re_password;

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    private boolean emailCheck = false;
    private boolean passwordCheck = false;
    private boolean repasswordCheck = false;

    private static final int INTENT_CODE_IMAGE_CAPTURE = 99;
    private static final int INTENT_CODE_IMAGE_SELECT = 100;

    private int method = 0;

    private Bitmap bitmap;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email_password, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            postEvent();
        }
    }

    @OnClick(R.id.profile_image)
    void onClick() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.CAMERA)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        goToast();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                })
                .start();

    }

    private void goToast() {
        /*final String[] item = {"相册","拍照"};*/
        final String[] item = {"Gallery", "Camera"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Please choose the way to get photo: ");
        builder.setItems(item, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        gallery();
                        method = 0;
                        break;
                    case 1:
                        method = 1;
                        camera();
                        break;
                }
            }
        });
        builder.create().show();
    }

    private void camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getActivity().startActivityForResult(intent, INTENT_CODE_IMAGE_CAPTURE);
    }

    private void gallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        getActivity().startActivityForResult(intent, INTENT_CODE_IMAGE_SELECT);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && (requestCode == INTENT_CODE_IMAGE_SELECT || requestCode == INTENT_CODE_IMAGE_CAPTURE)) {
            try {
                if (method == 0) {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    avator.setImageBitmap(bitmap);

                } else {
                    bitmap = rotateImage((Bitmap) data.getParcelableExtra("data"), 90);
                    avator.setImageBitmap(bitmap);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    private void init() {
        avator.setImageDrawable(getContext().getResources().getDrawable(R.drawable.avatar_holder));
        email.getEditText().addTextChangedListener(emailTextWatch);
        password.getEditText().addTextChangedListener(passwordTextWatch);
        re_password.getEditText().addTextChangedListener(repasswordTextWatch);
    }

    public static EmailAndPasswordFragment newInstance() {
        EmailAndPasswordFragment fragment = new EmailAndPasswordFragment();
        return fragment;
    }


    TextWatcher emailTextWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (!validateEmail(email.getEditText().getText().toString())) {
            } else {
                email.setErrorEnabled(false);
            }
        }
    };


    TextWatcher passwordTextWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            validateRePassword(password.getEditText().getText().toString());
            validatePassword(password.getEditText().getText().toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    TextWatcher repasswordTextWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            validateRePassword(password.getEditText().getText().toString());
            validatePassword(password.getEditText().getText().toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public boolean validateEmail(String e) {
        matcher = pattern.matcher(e);
        emailCheck = matcher.matches();
        if (!emailCheck) {
            email.setError("Not a valid email address");
        } else {
            email.setErrorEnabled(false);
        }
        postEvent();
        return emailCheck;
    }

    private boolean validatePassword(String p) {
        passwordCheck = p.length() > 5;
        if (!passwordCheck) {
            password.setError("password's length need more than 5");

        } else {
            password.setErrorEnabled(false);
        }
        postEvent();
        return passwordCheck;
    }

    private boolean validateRePassword(String p) {
        repasswordCheck = p.equals(re_password.getEditText().getText().toString());
        if (!repasswordCheck) {
            re_password.setError("password is not match");
        } else {
            re_password.setErrorEnabled(false);
        }
        postEvent();
        return repasswordCheck;
    }

    private void postEvent() {
        EmailPasswordEvent event = new EmailPasswordEvent();
        if (emailCheck && passwordCheck && repasswordCheck) {
            event.setAvator(bitmap);
            event.setValidated(true);
            event.setEmail(email.getEditText().getText().toString());
            event.setPassword(password.getEditText().getText().toString());
        } else {
            event.setValidated(false);
        }
        EventBus.getDefault().post(event);

    }

}
