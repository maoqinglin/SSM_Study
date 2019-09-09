package com.ireadygo.mybatis.mapper;

import com.ireadygo.mybatis.bean.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapperPlus {

    Employee getEmpById(Integer id);

    Employee getEmpAndDept(Integer id);

    Employee getEmpAndDeptStep(Integer id);

    //    Employee getEmpsByDeptId(Integer deptId); // 也可以
    List<Employee> getEmpsByDeptId(Integer deptId);

    Employee getEmpDis(Integer id);
}
