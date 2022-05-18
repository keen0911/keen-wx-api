package com.example.emos.wx.service;

import java.util.HashMap;

public interface CheckinService {
    public String validCanCheckIn(int userId, String date);

    void checkin(HashMap param);
}
