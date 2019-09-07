package com.ireadygo.mybatis.mapper;

import com.ireadygo.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Select;

public interface EmployeeMapperAnnotation {

    @Select("select * from tbl_employee where id=#{id}")
    Employee getEmpById(Integer id);
}
