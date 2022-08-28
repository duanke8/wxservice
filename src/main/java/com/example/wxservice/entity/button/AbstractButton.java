package com.example.wxservice.entity.button;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: wxservice
 * @description:
 * @author: 段珂 dwx922021
 * @create: 2021-11-20 18:58
 **/
@Data
@NoArgsConstructor
public  class AbstractButton {
    public String name;
    public List<AbstractButton> sub_button;


}
