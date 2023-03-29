package com.example.wxservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.wxservice.dto.IccDto;
import com.example.wxservice.service.IccService;
import com.example.wxservice.util.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/icc")
public class IccController {
    @Resource
    private IccService iccService;

    @GetMapping("/getIccListByType")
    public R getIccListByType(@RequestParam("type") String type) {
        QueryWrapper<IccDto> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type);
        List<IccDto> list = iccService.list(queryWrapper);
        return R.ok(list);
    }

    @PostMapping("/saveIccInfo")
    public R saveIccInfo(@RequestBody IccDto iccDto) {
        if(Objects.isNull(iccDto)){
            iccDto=new IccDto();
        }
       iccService.save(iccDto);
        return R.ok();
    }

    @PostMapping("/updateIccInfo")
    public R updateIccInfo(@RequestBody IccDto iccDto) {
        if(Objects.isNull(iccDto)){
            return R.error();
        }
        iccService.updateById(iccDto);
        return R.ok();
    }

    @PostMapping("/deleteIccInfo")
    public R deleteIccInfo(@RequestBody IccDto iccDto) {
        if(Objects.isNull(iccDto.getId())){
            return R.error();
        }
        iccService.removeById(iccDto.getId());
        return R.ok();
    }
}
