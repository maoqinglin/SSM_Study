package com.ireadygo.mybatis;

import com.ireadygo.mybatis.bean.Department;
import com.ireadygo.mybatis.bean.Employee;
import com.ireadygo.mybatis.mapper.DepartmentMapper;
import com.ireadygo.mybatis.mapper.EmployeeMapper;
import com.ireadygo.mybatis.mapper.EmployeeMapperAnnotation;
import com.ireadygo.mybatis.mapper.EmployeeMapperPlus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;


public class AppPlusTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testResultMap() throws IOException {

        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        try (SqlSession openSession = sqlSessionFactory.openSession()) {
            // 获取接口的实现类对象
            // 为接口自动的创建一个代理对象，代理对象执行增删改查操作
            EmployeeMapperPlus mapperPlus = openSession.getMapper(EmployeeMapperPlus.class);

//            Employee employee = mapperPlus.getEmpById(3);
            Employee employee = mapperPlus.getEmpAndDept(3);
            System.out.println(employee);
            System.out.println(employee.getDept());
        }
    }

    @Test
    public void testDept() throws IOException {

        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        try (SqlSession openSession = sqlSessionFactory.openSession()) {
            // 获取接口的实现类对象
            // 为接口自动的创建一个代理对象，代理对象执行增删改查操作
            DepartmentMapper departmentMapper = openSession.getMapper(DepartmentMapper.class);

//            Department dept = departmentMapper.getDeptById(1);

//            Department dept = departmentMapper.getDeptByIdPlus(1);

            Department dept = departmentMapper.getDeptByIdStep(1);
            System.out.println(dept.getEmps());
//            System.out.println(dept.getEmps());
        }
    }

    @Test
    public void testByStep() throws IOException {

        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        try (SqlSession openSession = sqlSessionFactory.openSession()) {
            // 获取接口的实现类对象
            // 为接口自动的创建一个代理对象，代理对象执行增删改查操作
            EmployeeMapperPlus mapperPlus = openSession.getMapper(EmployeeMapperPlus.class);

            Employee employee = mapperPlus.getEmpAndDeptStep(3);
            System.out.println(employee);  // 调用Employee对象的toString方法会触发懒加载，需要配置<setting name="lazyLoadTriggerMethods" value=""/>
//            System.out.println(employee.getDept());
        }
    }

    @Test
    public void testDiscriminator() throws IOException {

        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        try (SqlSession openSession = sqlSessionFactory.openSession()) {
            // 获取接口的实现类对象
            // 为接口自动的创建一个代理对象，代理对象执行增删改查操作
            EmployeeMapperPlus mapperPlus = openSession.getMapper(EmployeeMapperPlus.class);

            Employee employee = mapperPlus.getEmpDis(6);
            System.out.println(employee);
            System.out.println(employee.getDept());
        }
    }
}
