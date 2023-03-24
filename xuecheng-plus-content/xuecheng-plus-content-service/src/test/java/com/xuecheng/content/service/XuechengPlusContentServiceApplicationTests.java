package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.mapper.CourseBaseMapper;
import java.util.List;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@Log
@SpringBootTest(classes = {XuechengPlusContentServiceApplication.class})
class XuechengPlusContentServiceApplicationTests {
    @Autowired
    private CourseBaseMapper courseBaseMapper;
    @Autowired
    private CourseBaseInfoService courseBaseInfoService;
    @Autowired
    private CourseCategoryService courseCategoryService;

    @Test
    void contextLoads() {
        CourseBase courseBase = courseBaseMapper.selectById(22);
        System.out.println(courseBase);

    }
    @Test
    void testQueryPageCourseBase(){
        //构造参数
        QueryCourseParamsDto queryCourseParamsDto=new QueryCourseParamsDto();
        queryCourseParamsDto.setAuditStatus("202004");
        PageParams pageParams = new PageParams();
        pageParams.setPageSize(20L);
        pageParams.setPageNo(1L);

        PageResult<CourseBase> courseBasePageResult = courseBaseInfoService.queryCourseBaseList(
            queryCourseParamsDto, pageParams);
        System.out.println(courseBasePageResult.toString());
    }
    @Test
    void testQueryCourseCatgory(){
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryService.queryTreeNodes(
            "1");
        System.out.println(courseCategoryTreeDtos);
    }

}
