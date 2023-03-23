package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.CourseCategory;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class CourseCategoryTreeDto extends CourseCategory implements Serializable {
    List childrenTreeNodes;
}
