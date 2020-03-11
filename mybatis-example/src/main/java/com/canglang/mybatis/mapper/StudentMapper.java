package com.canglang.mybatis.mapper;

import com.canglang.mybatis.po.GradePo;
import com.canglang.mybatis.po.StudentPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author leitao.
 * @category
 * @time: 2020-3-10-17:29
 * @version: 1.0
 * @description:
 **/
@Mapper
public interface StudentMapper {

    StudentPo selectStudentById(int id);

}
