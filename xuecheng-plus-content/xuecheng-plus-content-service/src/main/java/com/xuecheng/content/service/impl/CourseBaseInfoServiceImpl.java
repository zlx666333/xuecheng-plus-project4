package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.execption.XueChengPlusException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.content.service.CourseBaseInfoService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Stack;

@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

  @Autowired
  private CourseBaseMapper courseBaseMapper;
  @Autowired
  private CourseMarketMapper courseMarketMapper;
  @Autowired
  private CourseCategoryMapper courseCategoryMapper;

  @Override
  public PageResult<CourseBase> queryCourseBaseList(QueryCourseParamsDto queryCourseParamsDto,
      PageParams pageParams) {
    Page<CourseBase> courseBasePage = new Page<CourseBase>();
    courseBasePage.setCurrent(pageParams.getPageNo());
    courseBasePage.setSize(pageParams.getPageSize());
    HashMap<String, String> ma2 = new HashMap<>();
    //查询条件
    LambdaQueryWrapper<CourseBase> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.like(StringUtils.isNotBlank(queryCourseParamsDto.getCourseName()),
        CourseBase::getName, queryCourseParamsDto.getCourseName());
    lambdaQueryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getAuditStatus()),
        CourseBase::getAuditStatus, queryCourseParamsDto.getAuditStatus());
    lambdaQueryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getPublishStatus()),
        CourseBase::getStatus, queryCourseParamsDto.getPublishStatus());
    Page<CourseBase> resPage = courseBaseMapper.selectPage(courseBasePage, lambdaQueryWrapper);
    PageResult<CourseBase> courseBasePageResult = new PageResult<CourseBase>(resPage.getRecords(),
        resPage.getTotal(), resPage.getCurrent(), resPage.getSize());
    return courseBasePageResult;
  }

  @Override
  @Transactional
  public CourseBaseInfoDto createCourseBase(Long compangId, AddCourseDto addCourseDto) {
    if (StringUtils.isBlank(addCourseDto.getName())) {
      throw new XueChengPlusException("课程名称为空");
    }
    if (StringUtils.isBlank(addCourseDto.getMt())) {
      throw new XueChengPlusException("课程分类为空");
    }
    if (StringUtils.isBlank(addCourseDto.getGrade())) {
      throw new XueChengPlusException("课程等级为空");
    }
    if (StringUtils.isBlank(addCourseDto.getTeachmode())) {
      throw new XueChengPlusException("教育模式为空");
    }
    if (StringUtils.isBlank(addCourseDto.getUsers())) {
      throw new XueChengPlusException("适应人群为空");
    }
    if (StringUtils.isBlank(addCourseDto.getCharge())) {
      throw new XueChengPlusException("收费规则为空");
    }
    CourseBase courseBaseNew = new CourseBase();
    if (compangId != null && compangId != 0) {
      courseBaseNew.setCompanyId(compangId);
    }
    BeanUtils.copyProperties(addCourseDto, courseBaseNew);
    courseBaseNew.setAuditStatus("202002");
    courseBaseNew.setStatus("203001");
    courseBaseNew.setCreateDate(LocalDateTime.now());
    int insert0 = courseBaseMapper.insert(courseBaseNew);
    Long courseId = courseBaseNew.getId();
    Stack<Integer> stack = new Stack<>();

    //插入到课程营销信息表
    CourseMarket courseMarketNew = new CourseMarket();
    String charge = addCourseDto.getCharge();
    if ("201001".equals(charge)) {
      BigDecimal price = addCourseDto.getPrice();
      if (ObjectUtils.isEmpty(price)) {
        throw new XueChengPlusException("收费课程价格不能为空");
      }
    }
    BeanUtils.copyProperties(addCourseDto, courseMarketNew);
    courseMarketNew.setId(courseId);
    courseMarketNew.setPrice(addCourseDto.getPrice().floatValue());
    courseMarketNew.setOriginalPrice(addCourseDto.getOriginalPrice().floatValue());
    int insert1 = courseMarketMapper.insert(courseMarketNew);
    if (insert0 <= 0 || insert1 <= 0) {
      throw new XueChengPlusException("新增课程基本信息失效");
    }
    return getCourseBaseInfo(courseId);
  }

  @Override
  public CourseBaseInfoDto getCourseBaseInfo(Long courseId) {
    CourseBase courseBase = courseBaseMapper.selectById(courseId);
    CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
    if (courseBase == null) {
      return null;
    }
    CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
    BeanUtils.copyProperties(courseBase, courseBaseInfoDto);
    if (courseMarket != null) {
      BeanUtils.copyProperties(courseMarket, courseBaseInfoDto);
    }
    //查询分类对应的中文名称
    CourseCategory courseCategoryBySt = courseCategoryMapper.selectById(courseBase.getSt());
    CourseCategory courseCategoryByMt = courseCategoryMapper.selectById(courseBase.getMt());
    courseBaseInfoDto.setStName(courseCategoryBySt.getName());
    courseBaseInfoDto.setMtName(courseCategoryByMt.getName());
    return courseBaseInfoDto;
  }
}
