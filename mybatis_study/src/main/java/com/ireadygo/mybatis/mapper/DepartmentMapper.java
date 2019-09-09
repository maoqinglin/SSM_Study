package com.ireadygo.mybatis.mapper;

import com.ireadygo.mybatis.bean.Department;

public interface DepartmentMapper {

    Department getDeptById(Integer id);

    Department getDeptByIdPlus(Integer id);

    Department getDeptByIdStep(Integer id);
}
