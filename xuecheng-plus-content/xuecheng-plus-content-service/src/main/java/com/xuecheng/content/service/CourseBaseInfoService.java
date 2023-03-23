package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import java.util.HashMap;

public interface CourseBaseInfoService {
    /**
     * 根据查询条件和分页条件，获取分页课程信息
     * @param queryCourseParamsDto
     * @param pageParams
     * @return
     */
    PageResult<CourseBase> queryCourseBaseList(QueryCourseParamsDto queryCourseParamsDto, PageParams pageParams);
    CourseBaseInfoDto createCourseBase(Long compangId, AddCourseDto addCourseDto);
    CourseBaseInfoDto getCourseBaseInfo(Long courseId);
    HashMap<String,String> map=new HashMap<>();
}
