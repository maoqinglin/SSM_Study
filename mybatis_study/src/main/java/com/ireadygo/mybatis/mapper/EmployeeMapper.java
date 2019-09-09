package com.ireadygo.mybatis.mapper;

import com.ireadygo.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface EmployeeMapper {

    Employee getEmpByMap(Map<String,Object> map);

    Employee getEmpByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);

    Employee getEmpById(Integer id);

    void addEmp(Employee employee);

    Integer updateEmp(Employee employee);

    void deleteEmp(Integer id);
}
