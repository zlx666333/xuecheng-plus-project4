package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseCategoryController {

  @Autowired
  private CourseCategoryService courseCategoryService;
  @GetMapping("/course-category/tree-nodes")
  public List<CourseCategoryTreeDto> queryTreeNodes() {
    return courseCategoryService.queryTreeNodes("1");
  }



}
