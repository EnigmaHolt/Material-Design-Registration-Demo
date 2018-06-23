package edu.usc.haowu.codetestforpheramor.registration.event;

import java.util.List;

/**
 * @author Hao Wu
 * @time 2018/06/22
 * @since
 */
public class UserInterestEvent extends BasicEvent {

    private String interestedGender;
    private int interestedMinAge;
    private int interestedMaxAge;

    public String getInterestedGender() {
        return interestedGender;
    }

    public void setInterestedGender(String interestedGender) {
        this.interestedGender = interestedGender;
    }

    public int getInterestedMinAge() {
        return interestedMinAge;
    }

    public void setInterestedMinAge(int interestedMinAge) {
        this.interestedMinAge = interestedMinAge;
    }

    public int getInterestedMaxAge() {
        return interestedMaxAge;
    }

    public void setInterestedMaxAge(int interestedMaxAge) {
        this.interestedMaxAge = interestedMaxAge;
    }
}
