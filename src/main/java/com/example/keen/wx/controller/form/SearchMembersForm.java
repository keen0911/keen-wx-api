package com.example.keen.wx.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class SearchMembersForm {
    @NotBlank
    private String members;
}