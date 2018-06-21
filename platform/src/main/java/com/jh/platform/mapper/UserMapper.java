package com.jh.platform.mapper;

import com.jh.platform.model.ClientInfo;
import com.jh.platform.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author jianghong
 * @Description
 * @create 2018-06-01 16:25
 **/
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM yy_user WHERE usercode = #{usercode}")
    User findUserByName(@Param("usercode") String usercode);

    @Select("SELECT * FROM yy_user_client WHERE user_id=#{usercode}")
    List<ClientInfo> findUserClientInfo(@Param("usercode") String usercode);

    void updateUser(User user);



}
