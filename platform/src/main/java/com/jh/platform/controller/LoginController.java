package com.jh.platform.controller;

import com.jh.platform.controller.result.LoginResult;
import com.jh.platform.controller.vo.LoginVO;
import com.jh.platform.mapper.LogUserMapper;
import com.jh.platform.model.User;
import com.jh.platform.util.PasswordHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * @author jianghong
 * @Description
 * @create 2018-05-22 21:20
 **/
@RestController
@RequestMapping("/yy/client")
public class LoginController extends BaseController {

    @Autowired
    private LogUserMapper logUserMapper;

    @Autowired
    private StringRedisTemplate template;

    @Value("${oneday.error.times}")
    private int oneDayErrorTimes = 6;






    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, LoginResult loginResult) {
        LoginVO loginVO = (LoginVO) objectToVO(request.getAttribute("arg"), LoginVO.class);
        Set<String> keySets = template.keys(loginVO.getUsercode()+"-*");
        if(keySets.size() >= oneDayErrorTimes){
            return "";
        }

//        loginVO.getUsercode()+"-"+loginVO.getHardwareCode();

        User user = logUserMapper.findUserByName(loginVO.getUsercode());
        //user or pass is wrong
        if (user == null||!StringUtils.equalsIgnoreCase(PasswordHelper.decryptPassword(loginVO.getPassword(), user.getCredentialsSalt()), user.getPassword())) {
            loginResult.setResult("error");
            loginResult.setCode(40001);
            return encryptResponseData(loginResult);
        }

        return "jainghong";
    }

}
