package edu.usc.haowu.codetestforpheramor.registration.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.nanchen.compresshelper.CompressHelper;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.reactivestreams.Subscription;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.usc.haowu.codetestforpheramor.R;
import edu.usc.haowu.codetestforpheramor.api.HttpService;
import edu.usc.haowu.codetestforpheramor.customview.BitmapUtil;
import edu.usc.haowu.codetestforpheramor.customview.NoScrollViewPager;
import edu.usc.haowu.codetestforpheramor.customview.TransformerAnimation;
import edu.usc.haowu.codetestforpheramor.model.BaseModel;
import edu.usc.haowu.codetestforpheramor.model.User;
import edu.usc.haowu.codetestforpheramor.registration.event.BasicEvent;
import edu.usc.haowu.codetestforpheramor.registration.event.EmailPasswordEvent;
import edu.usc.haowu.codetestforpheramor.registration.event.UserInformationEvent;
import edu.usc.haowu.codetestforpheramor.registration.event.UserInterestEvent;
import edu.usc.haowu.codetestforpheramor.registration.presenter.RegistrationPresenter;
import edu.usc.haowu.codetestforpheramor.registration.view.fragment.EmailAndPasswordFragment;
import edu.usc.haowu.codetestforpheramor.registration.view.fragment.UserInformationFragment;
import edu.usc.haowu.codetestforpheramor.registration.view.fragment.UserInterestFragment;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class Registration extends AppCompatActivity implements IRegistration {

    @BindView(R.id.btn_register)
    Button btn_register;
    @BindView(R.id.vp_fragment_container)
    NoScrollViewPager vp_fragment_container;

    private ArrayList<Fragment> fragments = new ArrayList<>();

    private int currentPage = 0;


    private User user = new User();
    private Bitmap avator;

    private ProgressDialog progressDialog;
    private RegistrationPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        init();
        presenter = new RegistrationPresenter(this, this);
    }


    @Override
    public void init() {
        EmailAndPasswordFragment emailAndPasswordFragment = EmailAndPasswordFragment.newInstance();
        UserInformationFragment userInformationFragment = UserInformationFragment.newInstance();
        UserInterestFragment userInterestFragment = UserInterestFragment.newInstance();
        fragments.add(emailAndPasswordFragment);
        fragments.add(userInformationFragment);
        fragments.add(userInterestFragment);


        vp_fragment_container.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });

        vp_fragment_container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == fragments.size() - 1) {
                    btn_register.setText("Submit");
                } else {
                    btn_register.setText("Next");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        vp_fragment_container.setOffscreenPageLimit(3);


        vp_fragment_container.setPageTransformer(true, new TransformerAnimation());

        vp_fragment_container.setScrollble(false);

        btn_register.setClickable(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BasicEvent event) {
        if (event.isValidated()) {
            btn_register.setBackgroundColor(getResources().getColor(R.color.activate));
            btn_register.setClickable(true);

        } else {
            btn_register.setBackgroundColor(getResources().getColor(R.color.unactivate));
            btn_register.setClickable(false);
        }

        if (event instanceof EmailPasswordEvent) {
            avator = ((EmailPasswordEvent) event).getAvator();
            user.setEmail(((EmailPasswordEvent) event).getEmail());
            user.setPassword(((EmailPasswordEvent) event).getPassword());
        } else if (event instanceof UserInformationEvent) {
            user.setFirstName(((UserInformationEvent) event).getFirstName());
            user.setLastName(((UserInformationEvent) event).getLastName());
            user.setGender(((UserInformationEvent) event).getGender());
            user.setHeight(((UserInformationEvent) event).getHeight());
            user.setAge(((UserInformationEvent) event).getAge());
            user.setZipcode(((UserInformationEvent) event).getZipCode());
            user.setRace(((UserInformationEvent) event).getRace());
            user.setReligions(((UserInformationEvent) event).getReligion());
            user.setDob(((UserInformationEvent) event).getDob());
        } else if (event instanceof UserInterestEvent) {
            user.setInterestedGender(((UserInterestEvent) event).getInterestedGender());
            user.setInterestedMinAge(((UserInterestEvent) event).getInterestedMinAge());
            user.setInterestedMaxAge(((UserInterestEvent) event).getInterestedMaxAge());
        }
    }


    @Override
    public void onBackPressed() {
        if (currentPage != 0) {
            currentPage -= 1;
            vp_fragment_container.setCurrentItem(currentPage);
        } else {
            super.onBackPressed();
        }

    }

    /**
     * Submit register request
     */
    @OnClick(R.id.btn_register)
    void onClickAction(View view) {
        if (currentPage < fragments.size() - 1) {
            currentPage += 1;
            vp_fragment_container.setCurrentItem(currentPage);
        } else {
            presenter.submit(user);
        }
    }

    @Override
    public void showProgressBar() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Register...");
        progressDialog.show();
    }

    @Override
    public void hideProgressBar() {
        progressDialog.hide();
    }

    @Override
    public void submitSuccess() {

        final Intent intent = new Intent(Registration.this, Review.class);
        intent.putExtra("user", user);
        if (avator != null) {
            AndPermission.with(this)
                    .runtime()
                    .permission(Permission.WRITE_EXTERNAL_STORAGE)
                    .permission(Permission.READ_EXTERNAL_STORAGE)
                    .onGranted(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {
                            String fileName = "pheramor_user"+System.currentTimeMillis();
                            BitmapUtil.saveBitmap2file(avator,fileName);
                            intent.putExtra("avator", fileName);
                            startActivity(intent);


                        }
                    })
                    .onDenied(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {

                        }
                    })
                    .start();
        }



    }


    @Override
    public void submitFailed() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        fragments.get(0).onActivityResult(requestCode, resultCode, data);
    }
}
