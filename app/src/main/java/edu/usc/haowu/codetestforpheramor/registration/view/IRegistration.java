package edu.usc.haowu.codetestforpheramor.registration.view;

/**
 * @author Hao Wu
 * @time 2018/06/22
 * @since
 */
public interface IRegistration {

    void init();
    void showProgressBar();
    void hideProgressBar();
    void submitSuccess();
    void submitFailed();
}
