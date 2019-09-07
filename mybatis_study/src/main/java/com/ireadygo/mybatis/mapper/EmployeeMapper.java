package com.ireadygo.mybatis.mapper;

import com.ireadygo.mybatis.bean.Employee;

public interface EmployeeMapper {
    Employee getEmpById(Integer id);

    void addEmp(Employee employee);

    Integer updateEmp(Employee employee);

    void deleteEmp(Integer id);
}
