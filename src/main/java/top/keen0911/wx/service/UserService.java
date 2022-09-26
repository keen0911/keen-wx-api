package top.keen0911.wx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.keen0911.wx.db.pojo.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface UserService extends IService<User> {
    int registerUser(String registerCode, String code, String nickname, String photo);

    Set<String> searchUserPermissions(int userId);

    Integer login(String code);

    User searchById(int userId);

    String searchUserHiredate(int userId);

    HashMap searchUserSummary(int userId);

    public ArrayList<HashMap> searchUserGroupByDept(String keyword);

    public ArrayList<HashMap> searchMembers(List param);

    public List<HashMap> selectUserPhotoAndName(List param);

    public String searchMemberEmail(int id);

    public void setWebLoginId(int id,String tid);

    void sendAccToEmail(User form);
}
