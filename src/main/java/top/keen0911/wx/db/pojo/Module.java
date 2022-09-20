package top.keen0911.wx.db.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 模块资源表
 * @TableName tb_module
 */
@TableName(value ="tb_module")
@Data
public class Module implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Object id;

    /**
     * 模块编号
     */
    @TableField(value = "module_code")
    private String module_code;

    /**
     * 模块名称
     */
    @TableField(value = "module_name")
    private String module_name;

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
        Module other = (Module) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getModule_code() == null ? other.getModule_code() == null : this.getModule_code().equals(other.getModule_code()))
            && (this.getModule_name() == null ? other.getModule_name() == null : this.getModule_name().equals(other.getModule_name()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getModule_code() == null) ? 0 : getModule_code().hashCode());
        result = prime * result + ((getModule_name() == null) ? 0 : getModule_name().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", module_code=").append(module_code);
        sb.append(", module_name=").append(module_name);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}