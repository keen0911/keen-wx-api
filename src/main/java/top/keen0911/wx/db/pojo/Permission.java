package top.keen0911.wx.db.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName tb_permission
 */
@TableName(value ="tb_permission")
@Data
public class Permission implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Object id;

    /**
     * 权限
     */
    @TableField(value = "permission_name")
    private String permission_name;

    /**
     * 模块ID
     */
    @TableField(value = "module_id")
    private Object module_id;

    /**
     * 行为ID
     */
    @TableField(value = "action_id")
    private Object action_id;

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
        Permission other = (Permission) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPermission_name() == null ? other.getPermission_name() == null : this.getPermission_name().equals(other.getPermission_name()))
            && (this.getModule_id() == null ? other.getModule_id() == null : this.getModule_id().equals(other.getModule_id()))
            && (this.getAction_id() == null ? other.getAction_id() == null : this.getAction_id().equals(other.getAction_id()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPermission_name() == null) ? 0 : getPermission_name().hashCode());
        result = prime * result + ((getModule_id() == null) ? 0 : getModule_id().hashCode());
        result = prime * result + ((getAction_id() == null) ? 0 : getAction_id().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", permission_name=").append(permission_name);
        sb.append(", module_id=").append(module_id);
        sb.append(", action_id=").append(action_id);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}