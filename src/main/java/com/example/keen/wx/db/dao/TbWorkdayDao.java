package com.example.keen.wx.db.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface TbWorkdayDao {
    Integer searchTodayIsWorkday();
    ArrayList<String> searchWorkdayInRange(HashMap param);
}
