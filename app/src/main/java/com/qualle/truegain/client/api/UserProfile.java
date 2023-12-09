package com.qualle.truegain.client.api;

public class UserProfile {

    private User user;
    private float totalLoad;
    private int workoutsCount;

    public UserProfile() {
    }

    public UserProfile(User user, float totalLoad, int workoutsCount) {
        this.user = user;
        this.totalLoad = totalLoad;
        this.workoutsCount = workoutsCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getTotalLoad() {
        return totalLoad;
    }

    public void setTotalLoad(float totalLoad) {
        this.totalLoad = totalLoad;
    }

    public int getWorkoutsCount() {
        return workoutsCount;
    }

    public void setWorkoutsCount(int workoutsCount) {
        this.workoutsCount = workoutsCount;
    }
}
