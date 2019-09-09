package com.ireadygo.mybatis;

import static org.junit.Assert.assertTrue;

import com.ireadygo.mybatis.bean.Employee;
import com.ireadygo.mybatis.mapper.EmployeeMapper;
import com.ireadygo.mybatis.mapper.EmployeeMapperAnnotation;
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

/**
 * 1、接口编程
 * MyBatis：Mapper  ---》  xxMapper.xml
 * 2、SqlSession代表和数据库的一次会话，用完必须关闭
 * 3、SqlSession 和 Connection 一样都是非线程安全。每次使用都应该去获取新的对象。
 * 4、Mapper接口没有实现类，但是MyBatis会为这个接口生成一个一个代理对象
 * （将接口与xml进行绑定）
 * EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
 * 5、两个重要的配置文件：
 * MyBatis的全局配置文件：包含数据库连接池信息，事务管理器信息等。。。系统运行环境信息
 * SQL映射文件：保存了每一个SQL语句的映射信息，将SQL抽取出来
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void test() throws IOException {
        /**
         * 1、根据xml配置文件（全局配置文件）创建一个 SqlSessionFactory 对象
         *  有数据源及一些运行环境信息
         * 2、sql 映射文件，配置了每一个sql，以及sql的封装规则
         * 3、将sql映射文件注册在全局配置文件中
         * */
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        try (SqlSession openSession = sqlSessionFactory.openSession()) {
            // 获取接口的实现类对象
            // 为接口自动的创建一个代理对象，代理对象执行增删改查操作
            EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
            Employee employee = employeeMapper.getEmpById(1);
            System.out.println(employee);
        }
    }

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testMapperAnnotation() throws IOException {
        /**
         * 1、根据xml配置文件（全局配置文件）创建一个 SqlSessionFactory 对象
         *  有数据源及一些运行环境信息
         * 2、sql 映射文件，配置了每一个sql，以及sql的封装规则
         * 3、将sql映射文件注册在全局配置文件中
         * */
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        try (SqlSession openSession = sqlSessionFactory.openSession()) {
            // 获取接口的实现类对象
            // 为接口自动的创建一个代理对象，代理对象执行增删改查操作
            EmployeeMapperAnnotation employeeMapper = openSession.getMapper(EmployeeMapperAnnotation.class);
            Employee employee = employeeMapper.getEmpById(1);
            System.out.println(employee);
        }
    }

    /**
     * 测试增删改
     * 1、MyBatis允许增删改直接定义以下类型返回值
     * Integer、Long、Boolean、void
     * 2、我们需要手动提交数据
     * SqlSession openSession = sqlSessionFactory.openSession() ---> 需要手动提交
     * SqlSession openSession = sqlSessionFactory.openSession(true) ---> 自动提交
     */
    @Test
    public void testCURD() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession openSession = sqlSessionFactory.openSession()) {
            EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
//            添加
            Employee employee = new Employee(null, "小盈盈", 0, "555@gamil.com");
            employeeMapper.addEmp(employee);
            System.out.println("employee: " + employee);

//            修改
//            Employee employee = new Employee(3, "小云云", 0, "111@gamil.com");
//            Integer result = employeeMapper.updateEmp(employee);
//            System.out.println("result: " + result);

//            删除
//            employeeMapper.deleteEmp(1);
            // 手动提交
            openSession.commit();
        }
    }

    @Test
    public void testMulParams() throws IOException {
        /**
         * 1、根据xml配置文件（全局配置文件）创建一个 SqlSessionFactory 对象
         *  有数据源及一些运行环境信息
         * 2、sql 映射文件，配置了每一个sql，以及sql的封装规则
         * 3、将sql映射文件注册在全局配置文件中
         * */
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        try (SqlSession openSession = sqlSessionFactory.openSession()) {
            // 获取接口的实现类对象
            // 为接口自动的创建一个代理对象，代理对象执行增删改查操作
            EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
            Employee employee = employeeMapper.getEmpByIdAndLastName(4, "小盈");
            System.out.println(employee);
        }
    }

    @Test
    public void testMapParams() throws IOException {
        /**
         * 1、根据xml配置文件（全局配置文件）创建一个 SqlSessionFactory 对象
         *  有数据源及一些运行环境信息
         * 2、sql 映射文件，配置了每一个sql，以及sql的封装规则
         * 3、将sql映射文件注册在全局配置文件中
         * */
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        try (SqlSession openSession = sqlSessionFactory.openSession()) {
            // 获取接口的实现类对象
            // 为接口自动的创建一个代理对象，代理对象执行增删改查操作
            EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", 4);
            paramMap.put("lastName", "小盈");
            paramMap.put("table", "tbl_employee");

            Employee employee = employeeMapper.getEmpByMap(paramMap);
            System.out.println(employee);
        }
    }

    @Test
    public void testReturnList() throws IOException {

        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        try (SqlSession openSession = sqlSessionFactory.openSession()) {
            // 获取接口的实现类对象
            // 为接口自动的创建一个代理对象，代理对象执行增删改查操作
            EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);

            List<Employee> employeeList = employeeMapper.getEmpsByLastNameLike("%盈%");
            for (Employee employee : employeeList) {
                System.out.println(employee);
            }
        }
    }

    @Test
    public void testReturnMap() throws IOException {

        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        try (SqlSession openSession = sqlSessionFactory.openSession()) {
            // 获取接口的实现类对象
            // 为接口自动的创建一个代理对象，代理对象执行增删改查操作
            EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);

//            Map<String, Object> map = employeeMapper.getEmpByIdReturnMap(3);

            Map<Integer,Employee> map = employeeMapper.getEmpByLastNameLikeReturnMap("%盈%");
            System.out.println(map);
        }
    }
}
