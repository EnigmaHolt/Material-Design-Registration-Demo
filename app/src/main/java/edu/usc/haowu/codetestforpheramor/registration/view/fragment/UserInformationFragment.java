package edu.usc.haowu.codetestforpheramor.registration.view.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.usc.haowu.codetestforpheramor.R;
import edu.usc.haowu.codetestforpheramor.registration.event.UserInformationEvent;

/**
 * @author Hao Wu
 * @time 2018/06/21
 * @since
 */
public class UserInformationFragment extends Fragment {
    @BindView(R.id.input_first_name)
    TextInputLayout input_first_name;
    @BindView(R.id.input_last_name)
    TextInputLayout input_last_name;
    @BindView(R.id.input_zip_code)
    TextInputLayout input_zip_code;
    @BindView(R.id.input_height)
    TextInputLayout input_height;
    @BindView(R.id.input_age)
    TextInputLayout input_age;
    @BindView(R.id.select_gender)
    MaterialBetterSpinner select_gender;
    @BindView(R.id.select_race)
    MaterialBetterSpinner select_race;
    @BindView(R.id.select_religion)
    MaterialBetterSpinner select_religion;
    @BindView(R.id.select_dob)
    TextInputLayout select_dob;




    private boolean isZipCodeChecked = false;
    private boolean isAgeChecked = false;
    private boolean isHeightChecked = false;

    private String gender="";
    private String race="";
    private String religion="";
    private String dob="";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_information, container, false);
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

    public static UserInformationFragment newInstance() {
        UserInformationFragment fragment = new UserInformationFragment();
        return fragment;
    }

    private void init() {
        final String[] GENDER = getResources().getStringArray(R.array.gender_list);
        final String[] RACE = getResources().getStringArray(R.array.race_list);
        final String[] RELIGION = getResources().getStringArray(R.array.religion_list);


        ArrayAdapter<String> gender_adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, GENDER);
        ArrayAdapter<String> race_adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, RACE);
        ArrayAdapter<String> religion_adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, RELIGION);

        select_gender.setAdapter(gender_adapter);
        select_race.setAdapter(race_adapter);
        select_religion.setAdapter(religion_adapter);

        select_gender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gender = GENDER[i];
                postEvent();
            }
        });

        select_race.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                race = RACE[i];
                postEvent();
            }
        });

        select_religion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                religion = RELIGION[i];
                postEvent();
            }
        });

        input_height.getEditText().addTextChangedListener(heightWatcher);
        input_zip_code.getEditText().addTextChangedListener(zipCodeWatcher);
        input_age.getEditText().addTextChangedListener(ageWatcher);

        select_dob.getEditText().setInputType(InputType.TYPE_NULL);
        select_dob.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    showDatePickerDialog();
                }
            }
        });
    }

    TextWatcher heightWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!validateHeight(input_height.getEditText().getText().toString())){
                input_height.setError("please input correct height");
            }else{
                input_height.setErrorEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    TextWatcher zipCodeWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!validateZipCode(input_zip_code.getEditText().getText().toString())){
                input_zip_code.setError("please input correct zip code.");
            }else{
                input_zip_code.setErrorEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    TextWatcher ageWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!validateAge(input_age.getEditText().getText().toString())){
                input_age.setError("please input correct age");
            }else {
                input_age.setErrorEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private boolean validateZipCode(String zipCode) {
        isZipCodeChecked =  zipCode.length() == 5;
        postEvent();
        return isZipCodeChecked;
    }

    private boolean validateHeight(String height) {
        isHeightChecked =  height.length() == 3;
        postEvent();
        return isHeightChecked;
    }

    private boolean validateAge(String age){
        isAgeChecked = age.length()>0 && age.length()<4;
        postEvent();

        return isAgeChecked;
    }

    public void postEvent(){
        UserInformationEvent event = new UserInformationEvent();
        if(isZipCodeChecked&&isHeightChecked&&isAgeChecked&&input_first_name.getEditText().length()>0&&input_last_name.getEditText().length()>0&&!dob.isEmpty() && !gender.isEmpty()&&!race.isEmpty()&&!religion.isEmpty()){
            event.setValidated(true);
            event.setFirstName(input_first_name.getEditText().getText().toString());
            event.setLastName(input_last_name.getEditText().getText().toString());
            event.setGender(gender);
            event.setAge(Integer.valueOf(input_age.getEditText().getText().toString()));
            event.setHeight(Integer.valueOf(input_height.getEditText().getText().toString()));
            event.setZipCode(Integer.valueOf(input_zip_code.getEditText().getText().toString()));
            event.setDob(dob);
            event.setRace(race);
            event.setReligion(religion);
        }else{
            event.setValidated(false);
        }
        EventBus.getDefault().post(event);

    }

    private void showDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                dob = year+"/"+(monthOfYear+1)+"/"+dayOfMonth;

                select_dob.getEditText().setText(dob);

                postEvent();
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

    }

}
