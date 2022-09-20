package top.keen0911.wx.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.keen0911.wx.db.pojo.FaceModel;

/**
* @author keen
* @description 针对表【tb_face_model】的数据库操作Mapper
* @createDate 2022-09-20 09:39:58
* @Entity FaceModel
*/
public interface FaceModelMapper extends BaseMapper<FaceModel> {

    String searchFaceModel(int userId);
    int insert (FaceModel faceModel);
    void deleteFaceModel(int userId);
}




