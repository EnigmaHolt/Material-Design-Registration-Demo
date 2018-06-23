package edu.usc.haowu.codetestforpheramor.registration.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.appyvet.materialrangebar.RangeBar;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.usc.haowu.codetestforpheramor.R;
import edu.usc.haowu.codetestforpheramor.registration.event.UserInterestEvent;

/**
 * @author Hao Wu
 * @time 2018/06/21
 * @since
 */
public class UserInterestFragment extends Fragment {
    @BindView(R.id.select_interest_gender)
    MaterialBetterSpinner select_interest_gender;
    @BindView(R.id.select_age_range)
    RangeBar select_age_range;

    private String intersted_gender = "";
    private int min_age = -1;
    private int max_age = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_interest,container,false);
        ButterKnife.bind(this,view);
        init();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            postEvent();
        }
    }

    private void init(){
        final String[] GENDER = getResources().getStringArray(R.array.gender_list);

        ArrayAdapter<String> gender_adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, GENDER);
        select_interest_gender.setAdapter(gender_adapter);
        select_interest_gender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intersted_gender = GENDER[i];
                postEvent();
            }
        });

        select_age_range.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                min_age = Integer.valueOf(leftPinValue);
                max_age = Integer.valueOf(rightPinValue);
                postEvent();
            }
        });
    }

    private void postEvent(){
        UserInterestEvent event = new UserInterestEvent();

        if(!intersted_gender.isEmpty()&&max_age!=-1&&min_age!=-1){
            event.setInterestedGender(intersted_gender);
            event.setInterestedMinAge(min_age);
            event.setInterestedMaxAge(max_age);

            Log.d("age",String.valueOf(min_age)+" "+String.valueOf(max_age));
            event.setValidated(true);
        }else {
            event.setValidated(false);
        }
        EventBus.getDefault().post(event);

    }

    public static UserInterestFragment newInstance(){
        UserInterestFragment fragment = new UserInterestFragment();
        return fragment;
    }
}
