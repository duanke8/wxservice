package com.example.wxservice.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.wxservice.entity.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<UserDto> {
}
