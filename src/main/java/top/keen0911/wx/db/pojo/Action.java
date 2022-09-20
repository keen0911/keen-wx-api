package top.keen0911.wx.db.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 行为表
 * @TableName tb_action
 */
@TableName(value ="tb_action")
@Data
public class Action implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Object id;

    /**
     * 行为编号
     */
    @TableField(value = "action_code")
    private String action_code;

    /**
     * 行为名称
     */
    @TableField(value = "action_name")
    private String action_name;

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
        Action other = (Action) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAction_code() == null ? other.getAction_code() == null : this.getAction_code().equals(other.getAction_code()))
            && (this.getAction_name() == null ? other.getAction_name() == null : this.getAction_name().equals(other.getAction_name()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAction_code() == null) ? 0 : getAction_code().hashCode());
        result = prime * result + ((getAction_name() == null) ? 0 : getAction_name().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", action_code=").append(action_code);
        sb.append(", action_name=").append(action_name);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}