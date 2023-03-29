package com.example.wxservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wxservice.dto.IccDto;
import com.example.wxservice.mapper.IccMapper;
import com.example.wxservice.service.IccService;
import org.springframework.stereotype.Service;

@Service
public class IccServiceImpl extends ServiceImpl<IccMapper, IccDto> implements IccService {
}
