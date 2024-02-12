package com.xuecheng.content.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.beans.BeanUtils;

public class Util {
  public static<T,U,V> T getPageParams(BaseMapper<U> baseMapper, T t,QueryWrapper<U> queryWrapper,V v){
    U u = baseMapper.selectById(1);
    BeanUtils.copyProperties(u,t);
    return t;


  }

}
