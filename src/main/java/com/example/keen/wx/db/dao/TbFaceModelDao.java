package com.example.keen.wx.db.dao;

import com.example.keen.wx.db.pojo.TbFaceModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbFaceModelDao {
    String searchFaceModel(int userId);
    void insert (TbFaceModel faceModel);
    void deleteFaceModel(int userId);
}
