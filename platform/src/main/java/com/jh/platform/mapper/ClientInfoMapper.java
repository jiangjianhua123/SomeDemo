package com.jh.platform.mapper;

import com.jh.platform.model.ClientInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClientInfoMapper {

    @Select("SELECT * FROM yy_user_client WHERE usercode = #{usercode}")
    List<ClientInfo> findUserClientInfo(@Param("usercode") String usercode);

    @Insert("insert into yy_user_client(usercode,client_version,expires,remark) values (#{usercode},#{client_version},#{expires},#{remark})")
    void insertClientInfo(ClientInfo clientInfo);

}
