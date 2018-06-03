package com.jh.platform.mapper;

import com.jh.platform.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author jianghong
 * @Description
 * @create 2018-06-01 16:25
 **/
@Mapper
public interface LogUserMapper {

    @Select("SELECT * FROM yy_user WHERE usercode = #{usercode}")
    User findUserByName(@Param("usercode") String usercode);

}
