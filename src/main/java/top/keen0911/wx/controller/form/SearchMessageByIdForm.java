package top.keen0911.wx.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class SearchMessageByIdForm {
    @NotNull
    private String id;
}
