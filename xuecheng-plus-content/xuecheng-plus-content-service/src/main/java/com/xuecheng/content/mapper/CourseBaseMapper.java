package com.xuecheng.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuecheng.content.model.po.CourseBase;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 课程基本信息 Mapper 接口
 * </p>
 *
 * @author itcast
 */
@Repository
@Mapper
public interface CourseBaseMapper extends BaseMapper<CourseBase> {

}
