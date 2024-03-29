package top.keen0911.wx.db.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @TableName tb_user
 */
@TableName(value ="tb_user")
@Data
@ApiModel
public class User implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 长期授权字符串
     */
    @TableField(value = "open_id")
    private String openId;

    /**
     * 昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 头像网址
     */
    @TableField(value = "photo")
    private String photo;

    /**
     * 姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 性别
     */
    @TableField(value = "sex")
    private Object sex;

    /**
     * 手机号码
     */
    @TableField(value = "tel")
    private String tel;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 入职日期
     */
    @TableField(value = "hiredate")
    private Date hiredate;

    /**
     * 角色
     */
    @TableField(value = "role")
    private Object role;

    /**
     * 是否是超级管理员
     */
    @TableField(value = "root")
    private Boolean root;

    /**
     * 部门编号
     */
    @TableField(value = "dept_id")
    private Integer deptId;

    /**
     * 学号
     */
    @TableField(value = "student_id")
    private String studentId;

    /**
     * 状态
     */
    @TableField(value = "status")
    private Byte status;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
            && (this.getPhoto() == null ? other.getPhoto() == null : this.getPhoto().equals(other.getPhoto()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getTel() == null ? other.getTel() == null : this.getTel().equals(other.getTel()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getHiredate() == null ? other.getHiredate() == null : this.getHiredate().equals(other.getHiredate()))
            && (this.getRole() == null ? other.getRole() == null : this.getRole().equals(other.getRole()))
            && (this.getRoot() == null ? other.getRoot() == null : this.getRoot().equals(other.getRoot()))
            && (this.getDeptId() == null ? other.getDeptId() == null : this.getDeptId().equals(other.getDeptId()))
            && (this.getStudentId() == null ? other.getStudentId() == null : this.getStudentId().equals(other.getStudentId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getOpenId() == null) ? 0 : getOpenId().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getPhoto() == null) ? 0 : getPhoto().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getTel() == null) ? 0 : getTel().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getHiredate() == null) ? 0 : getHiredate().hashCode());
        result = prime * result + ((getRole() == null) ? 0 : getRole().hashCode());
        result = prime * result + ((getRoot() == null) ? 0 : getRoot().hashCode());
        result = prime * result + ((getDeptId() == null) ? 0 : getDeptId().hashCode());
        result = prime * result + ((getStudentId() == null) ? 0 : getStudentId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", open_id=").append(openId);
        sb.append(", nickname=").append(nickname);
        sb.append(", photo=").append(photo);
        sb.append(", name=").append(name);
        sb.append(", sex=").append(sex);
        sb.append(", tel=").append(tel);
        sb.append(", email=").append(email);
        sb.append(", hiredate=").append(hiredate);
        sb.append(", role=").append(role);
        sb.append(", root=").append(root);
        sb.append(", dept_id=").append(deptId);
        sb.append(", student_id=").append(studentId);
        sb.append(", status=").append(status);
        sb.append(", create_time=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}