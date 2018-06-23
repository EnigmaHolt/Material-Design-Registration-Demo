package edu.usc.haowu.codetestforpheramor.registration.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.usc.haowu.codetestforpheramor.R;
import edu.usc.haowu.codetestforpheramor.customview.BitmapUtil;
import edu.usc.haowu.codetestforpheramor.model.User;
import es.dmoral.toasty.Toasty;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Hao Wu
 * @time 2018/06/22
 * @since
 */
public class Review extends AppCompatActivity {
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_gender)
    TextView tv_gender;
    @BindView(R.id.tv_age)
    TextView tv_age;
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_height)
    TextView tv_height;
    @BindView(R.id.tv_race)
    TextView tv_race;
    @BindView(R.id.tv_religion)
    TextView tv_religion;
    @BindView(R.id.tv_dob)
    TextView tv_dob;
    @BindView(R.id.tv_interest_gender)
    TextView tv_interest_gender;
    @BindView(R.id.tv_interest_age)
    TextView tv_interest_age;
    @BindView(R.id.iv_avator)
    CircleImageView iv_avator;

    private User user;
    private String avator_path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);
        user = (User) getIntent().getSerializableExtra("user");
        avator_path = getIntent().getStringExtra("avator");
        if(user!=null){
            init(user);
        }

    }

    private void init(User user){

        Toasty.success(this,"Create Success",Toast.LENGTH_LONG,true).show();

        if(avator_path!=null){
            iv_avator.setImageBitmap(BitmapUtil.getBitmapFromFile(avator_path));
        }
        tv_name.setText(user.getFirstName() + " "+user.getLastName());
        tv_gender.setText(user.getGender());
        tv_age.setText(String.valueOf(user.getAge()));
        tv_email.setText(user.getEmail());
        tv_height.setText(String.valueOf(user.getHeight()));
        tv_race.setText(user.getRace());
        tv_religion.setText(user.getReligions());
        tv_dob.setText(user.getDob());
        tv_interest_gender.setText(user.getInterestedGender());
        tv_interest_age.setText(String.valueOf(user.getInterestedMinAge())+" to "+String.valueOf(user.getInterestedMaxAge()));
    }

}
