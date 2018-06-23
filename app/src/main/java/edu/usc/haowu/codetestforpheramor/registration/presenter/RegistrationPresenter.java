package edu.usc.haowu.codetestforpheramor.registration.presenter;

import android.content.Context;
import android.widget.Toast;

import edu.usc.haowu.codetestforpheramor.api.HttpService;
import edu.usc.haowu.codetestforpheramor.model.BaseModel;
import edu.usc.haowu.codetestforpheramor.model.User;
import edu.usc.haowu.codetestforpheramor.registration.model.RegistrationInteractor;
import edu.usc.haowu.codetestforpheramor.registration.view.IRegistration;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * @author Hao Wu
 * @time 2018/06/20
 * @since
 */
public class RegistrationPresenter {
    IRegistration iView;
    Context context;
    RegistrationInteractor interactor;


    public RegistrationPresenter(Context context, IRegistration iView) {
        this.iView = iView;
        this.context = context;
        interactor = new RegistrationInteractor(context);

    }

    public void submit(User user){
        iView.showProgressBar();
        interactor.submitNewUser(user, new RegistrationInteractor.CallBack() {
            @Override
            public void success() {
                iView.hideProgressBar();
                iView.submitSuccess();
            }

            @Override
            public void failed(String message) {
                iView.hideProgressBar();
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                iView.submitFailed();

            }
        });


    }
}
