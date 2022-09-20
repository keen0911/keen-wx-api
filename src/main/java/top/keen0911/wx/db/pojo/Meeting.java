package top.keen0911.wx.db.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会议表
 * @TableName tb_meeting
 */
@TableName(value ="tb_meeting")
@Data
public class Meeting implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * UUID
     */
    @TableField(value = "uuid")
    private String uuid;

    /**
     * 会议题目
     */
    @TableField(value = "title")
    private String title;

    /**
     * 创建人ID
     */
    @TableField(value = "creator_id")
    private Long creator_id;

    /**
     * 日期
     */
    @TableField(value = "date")
    private String date;

    /**
     * 开会地点
     */
    @TableField(value = "place")
    private String place;

    /**
     * 开始时间
     */
    @TableField(value = "start")
    private String start;

    /**
     * 结束时间
     */
    @TableField(value = "end")
    private String end;

    /**
     * 会议类型（1在线会议，2线下会议）
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 参与者
     */
    @TableField(value = "members")
    private Object members;

    /**
     * 会议内容
     */
    @TableField(value = "desc")
    private String desc;

    /**
     * 工作流实例ID
     */
    @TableField(value = "instance_id")
    private String instance_id;

    /**
     * 出席人员名单
     */
    @TableField(value = "present")
    private Object present;

    /**
     * 未出席人员名单
     */
    @TableField(value = "unpresent")
    private Object unpresent;

    /**
     * 状态（1.申请中，2.审批未通过，3.审批通过，4.会议进行中，5.会议结束）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date create_time;

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
        Meeting other = (Meeting) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUuid() == null ? other.getUuid() == null : this.getUuid().equals(other.getUuid()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getCreator_id() == null ? other.getCreator_id() == null : this.getCreator_id().equals(other.getCreator_id()))
            && (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()))
            && (this.getPlace() == null ? other.getPlace() == null : this.getPlace().equals(other.getPlace()))
            && (this.getStart() == null ? other.getStart() == null : this.getStart().equals(other.getStart()))
            && (this.getEnd() == null ? other.getEnd() == null : this.getEnd().equals(other.getEnd()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getMembers() == null ? other.getMembers() == null : this.getMembers().equals(other.getMembers()))
            && (this.getDesc() == null ? other.getDesc() == null : this.getDesc().equals(other.getDesc()))
            && (this.getInstance_id() == null ? other.getInstance_id() == null : this.getInstance_id().equals(other.getInstance_id()))
            && (this.getPresent() == null ? other.getPresent() == null : this.getPresent().equals(other.getPresent()))
            && (this.getUnpresent() == null ? other.getUnpresent() == null : this.getUnpresent().equals(other.getUnpresent()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUuid() == null) ? 0 : getUuid().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getCreator_id() == null) ? 0 : getCreator_id().hashCode());
        result = prime * result + ((getDate() == null) ? 0 : getDate().hashCode());
        result = prime * result + ((getPlace() == null) ? 0 : getPlace().hashCode());
        result = prime * result + ((getStart() == null) ? 0 : getStart().hashCode());
        result = prime * result + ((getEnd() == null) ? 0 : getEnd().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getMembers() == null) ? 0 : getMembers().hashCode());
        result = prime * result + ((getDesc() == null) ? 0 : getDesc().hashCode());
        result = prime * result + ((getInstance_id() == null) ? 0 : getInstance_id().hashCode());
        result = prime * result + ((getPresent() == null) ? 0 : getPresent().hashCode());
        result = prime * result + ((getUnpresent() == null) ? 0 : getUnpresent().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uuid=").append(uuid);
        sb.append(", title=").append(title);
        sb.append(", creator_id=").append(creator_id);
        sb.append(", date=").append(date);
        sb.append(", place=").append(place);
        sb.append(", start=").append(start);
        sb.append(", end=").append(end);
        sb.append(", type=").append(type);
        sb.append(", members=").append(members);
        sb.append(", desc=").append(desc);
        sb.append(", instance_id=").append(instance_id);
        sb.append(", present=").append(present);
        sb.append(", unpresent=").append(unpresent);
        sb.append(", status=").append(status);
        sb.append(", create_time=").append(create_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}