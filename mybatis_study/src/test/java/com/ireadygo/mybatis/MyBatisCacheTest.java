package com.ireadygo.mybatis;

import com.ireadygo.mybatis.bean.Department;
import com.ireadygo.mybatis.bean.Employee;
import com.ireadygo.mybatis.mapper.DepartmentMapper;
import com.ireadygo.mybatis.mapper.EmployeeMapper;
import com.ireadygo.mybatis.mapper.EmployeeMapperDynamicSQL;
import com.ireadygo.mybatis.mapper.EmployeeMapperPlus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;


public class MyBatisCacheTest {
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

    /**
     *  两级缓存
     *  一级缓存：本地缓存，SqlSession级别的缓存。一级缓存是一直开启的,SQLSession级别的一个map
     *      与数据库同一次会话期间查询到的数据会放在本地缓存中
     *      以后如果需要获取相同的数据，直接从缓存中拿，不需要再去查询数据库
     *
     *      一级缓存失效情况（没有使用到当前一级缓存的情况，效果就是，还需要再向数据库发出查询）
     *      1、SQLSession 不同
     *      2、SQLSession 相同，查询条件不同（当前一级缓存还没有这个数据）
     *      3、SQLSession 相同，两次查询之间进行了增删改操作（这次增删改可能对当前数据有影响）
     *      4、SQLSession 相同，手动清除了一级缓存（缓存清空）
     *
     *  二级缓存：全局缓存，基于namespace级别的缓存，一个namespace对应一个二级缓存；
     *      工作机制：
     *          1、一个会话，查询一条数据，这个数据就会被放在当前会话的一级缓存中；
     *          2、如果会话关闭，一级缓存中的数据会被保存到二级缓存中，新的会话查询信息，就可以参照二级缓存中的内容
     *          3、SQLSession   EmployeeMapper ---》 Employee
     *                          DepartmentMapper ---》 Department
     *              不同的namespace查出的数据会放在自己对应的缓存（map）中
     *
     */
    @Test
    public void testFirstLevelCache() throws IOException {

        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        try (SqlSession openSession = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);

            Employee employee1 = mapper.getEmpById(3);
            System.out.println(employee1);

            Employee employee2 = mapper.getEmpById(3);
            System.out.println(employee2);

            System.out.println(employee1==employee2);
        }
    }

    @Test
    public void testFirstLevelCacheOtherSession() throws IOException {

        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        try (SqlSession openSession = sqlSessionFactory.openSession(); SqlSession otherSession = sqlSessionFactory.openSession();) {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);

            Employee employee1 = mapper.getEmpById(3);
            System.out.println(employee1);

            // 使用另外一个SQLSession
            EmployeeMapper otherMapper = otherSession.getMapper(EmployeeMapper.class);
            Employee employee2 = otherMapper.getEmpById(3);
            System.out.println(employee2);

            System.out.println(employee1==employee2);
        }
    }

    @Test
    public void testFirstLevelCacheDiffCondition() throws IOException {

        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        try (SqlSession openSession = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);

            Employee employee1 = mapper.getEmpById(3);
            System.out.println(employee1);

            // SQLSession 相同，查询条件不同
            Employee employee2 = mapper.getEmpById(4);
            System.out.println(employee2);

            System.out.println(employee1==employee2);
        }
    }

    @Test
    public void testFirstLevelCacheCURD() throws IOException {

        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        try (SqlSession openSession = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);

            Employee employee1 = mapper.getEmpById(3);
            System.out.println(employee1);

            mapper.addEmp(new Employee(null,"test Cache",1,"Cache"));
            openSession.commit();

            // SQLSession 相同，查询之间进行了增删改操作，可能会影响当前查询
            Employee employee2 = mapper.getEmpById(4);
            System.out.println(employee2);

            System.out.println(employee1==employee2);
        }
    }
}
