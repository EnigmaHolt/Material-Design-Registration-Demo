package edu.usc.haowu.codetestforpheramor.registration.event;

import android.graphics.Bitmap;

/**
 * @author Hao Wu
 * @time 2018/06/21
 * @since
 */
public class EmailPasswordEvent extends BasicEvent {
    private String email;
    private String password;
    private Bitmap avator;

    public Bitmap getAvator() {
        return avator;
    }

    public void setAvator(Bitmap avator) {
        this.avator = avator;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
