package com.ireadygo.mybatis.mapper;

import com.ireadygo.mybatis.bean.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapper {

    // 多条记录封装一个map：Map<Integer, Employee> ：键是这条记录的主键，值是记录封装后的JavaBean
    // 告诉MyBatis封装这个map的时候使用哪个属性作为map的key
    @MapKey("lastName")
    Map<Integer,Employee> getEmpByLastNameLikeReturnMap(String lastName);

    // 返回一条记录的map：key=列名，value=列的值
    Map<String,Object> getEmpByIdReturnMap(Integer id);

    List<Employee> getEmpsByLastNameLike(String lastName);

    Employee getEmpByMap(Map<String,Object> map);

    Employee getEmpByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);

    Employee getEmpById(Integer id);

    void addEmp(Employee employee);

    Integer updateEmp(Employee employee);

    void deleteEmp(Integer id);
}
