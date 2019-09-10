package com.ireadygo.mybatis.mapper;

import com.ireadygo.mybatis.bean.Employee;

import java.util.List;

public interface EmployeeMapperDynamicSQL {

    // 携带了哪个字段的查询条件就带上这个字段的值
    List<Employee> getEmpByConditionIf(Employee employee);

    List<Employee> getEmpByConditionChoose(Employee employee);

    void updateEmp(Employee employee);

    List<Employee> getEmpsByConditionForeach(List<Integer> list1);
}
