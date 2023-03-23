package com.xuecheng.content.service.impl;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

  @Autowired
  private CourseCategoryMapper courseCategoryMapper;

  @Override
  public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
    List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);
    HashMap<String, CourseCategoryTreeDto> idAndDto = new HashMap<>();
    List<CourseCategoryTreeDto> res = new ArrayList<>();
    courseCategoryTreeDtos.stream().forEach(
        i -> {
          idAndDto.put(i.getId(), i);
          //添加一级节点到结果list
          if (i.getParentid().equals(id)) {
            res.add(i);
          }
          //添加二级节点
          if (idAndDto.containsKey(i.getParentid())) {
            CourseCategoryTreeDto parentNode = idAndDto.get(i.getParentid());
            if (parentNode.getChildrenTreeNodes() == null) {
              parentNode.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
            }
            List childrenTreeNodes = parentNode.getChildrenTreeNodes();
            childrenTreeNodes.add(i);
          }
        }
    );
    return res;
  }
}
