package edu.usc.haowu.codetestforpheramor.registration.model;

import android.content.Context;
import android.widget.Toast;

import edu.usc.haowu.codetestforpheramor.api.HttpService;
import edu.usc.haowu.codetestforpheramor.model.BaseModel;
import edu.usc.haowu.codetestforpheramor.model.User;
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
public class RegistrationInteractor {

    private Context context;
    private CompositeDisposable subscriptions;


    public RegistrationInteractor(Context context) {
        this.context = context;
        subscriptions = new CompositeDisposable();

    }

    public void submitNewUser(User user, final CallBack callBack) {
        subscriptions.clear();
        HttpService.getInstance(context).create(HttpService.ApiService.class).registerNewUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        subscriptions.add(d);
                    }

                    @Override
                    public void onNext(Response<BaseModel> baseModelResponse) {
                        if (baseModelResponse != null && baseModelResponse.isSuccessful()) {
                            callBack.success();
                        } else {

                            callBack.failed("connect failed");
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.failed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public interface CallBack {

        void success();

        void failed(String message);

    }
}
