package com.jh.platform.controller;

import com.alibaba.fastjson.JSON;
import com.jh.platform.common.Constant;
import com.jh.platform.controller.result.ChangePWResult;
import com.jh.platform.controller.result.LoginResult;
import com.jh.platform.controller.vo.ChangePWVO;
import com.jh.platform.controller.vo.HearBeat;
import com.jh.platform.controller.vo.LoginVO;
import com.jh.platform.controller.vo.RegisterVO;
import com.jh.platform.mapper.ClientInfoMapper;
import com.jh.platform.mapper.UserMapper;
import com.jh.platform.model.ClientInfo;
import com.jh.platform.model.User;
import com.jh.platform.util.ClientVersionUtil;
import com.jh.platform.util.PasswordHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author jianghong
 * @Description
 * @create 2018-05-22 21:20
 **/
@RestController
@RequestMapping("/yy/client")
public class LoginController extends BaseController {

    private static Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ClientInfoMapper  clientInfoMapper;

    @Autowired
    private StringRedisTemplate template;

    @Value("${oneday.error.times}")
    private int oneDayErrorTimes = 6;

    @Value("${client.max-num}")
    private int clientMaxNum = 3;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, LoginResult loginResult) {
        LOG.info("login:"+request.getAttribute("arg"));
        LoginVO loginVO = null;
        try{
            loginVO = JSON.parseObject((String)request.getAttribute("arg"), LoginVO.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(loginVO==null){
            loginResult.setResult("data format is wrong");
        }
        String clientVersion = loginVO.getClientsVersion();
        if (!ClientVersionUtil.isVersion(clientVersion)) {
            loginResult.setResult("error");
            loginResult.setCode(Constant.USER_CLIENT_VERSION_ILLEGAL);
            return encryptResponseData(loginResult);
        }
        String tempKey = loginVO.getUsercode() + Constant.underline + loginVO.getHardwareCode();
        long timestamps =  Clock.systemUTC().millis();
        String successTempKey = tempKey + Constant.underline + timestamps;
        String errorTempKey = "error" + Constant.underline + tempKey;
        Set<String> keySets = template.keys(loginVO.getUsercode() + Constant.underline + "*");
        //超过当前账号客户端最大数
        if (!keySets.isEmpty() && StringUtils.isBlank(keySets.stream().filter(k -> k.startsWith(tempKey)).findFirst().get()) && keySets.size() >= clientMaxNum) {
            loginResult.setResult("error");
            loginResult.setCode(Constant.USER_LIMIT);
            //return "超过当前账号客户端最大数";
            return encryptResponseData(loginResult);
        }
        String tempKeyError = template.opsForValue().get(errorTempKey);
        if (StringUtils.isNotBlank(tempKeyError) && Integer.parseInt(tempKeyError) > oneDayErrorTimes) {
            //return "登陆失败次数过多，今天限制登陆。";
            loginResult.setResult("error");
            loginResult.setCode(Constant.USER_LOGIN_LIMIT);
            return encryptResponseData(loginResult);
        }
        User user = userMapper.findUserByName(loginVO.getUsercode());
        //user or pass is wrong
        if (user == null || !StringUtils.equalsIgnoreCase(PasswordHelper.decryptPassword(loginVO.getPassword(), user.getCredentialsSalt()), user.getPassword())) {
            loginResult.setResult("error");
            loginResult.setCode(Constant.USER_PWD_OR_NULL);
            if (StringUtils.isBlank(tempKeyError)) {
                template.opsForValue().set(errorTempKey, "1");
            } else {
                template.opsForValue().increment(errorTempKey, 1);
            }
            template.expire(errorTempKey, (24 * 60 * 60 - LocalTime.now().getLong(ChronoField.SECOND_OF_DAY)), TimeUnit.SECONDS);
            return encryptResponseData(loginResult);
        }

        List<ClientInfo> clientInfos = clientInfoMapper.findUserClientInfo(user.getUsercode());
        if (clientInfos==null || clientInfos.isEmpty()) {
            loginResult.setResult("error");
            loginResult.setCode(Constant.USER_CLIENT_NULL);
            return encryptResponseData(loginResult);
        }
        String[] clientVersionArrays = clientVersion.split("\\.");
        ClientInfo clientInfo = clientInfos.stream().filter(c -> c.getClient_version().startsWith(clientVersionArrays[0])).findFirst().get();

        if (!StringUtils.equalsIgnoreCase(clientVersion.substring(0, clientVersion.lastIndexOf(".")), clientInfo.getClient_version().substring(0, clientVersion.lastIndexOf(".")))) {
            loginResult.setResult("error");
            loginResult.setCode(Constant.USER_CLIENT_VERSION_UPDATE);
            return encryptResponseData(loginResult);
        }
        long time = (clientInfo.getExpires().getTime() - Clock.systemUTC().millis()) / 1000;
        if (time <= 0) {
            loginResult.setResult("error");
            loginResult.setCode(Constant.USER_CLIENT_VERSION_OVER);
            return encryptResponseData(loginResult);
        }
        template.opsForHash().put(successTempKey, "usercode", user.getUsercode());
        template.opsForHash().put(successTempKey, "logintime", LocalDateTime.now().toString());
        template.expire(successTempKey, 5, TimeUnit.MINUTES);
        template.delete(errorTempKey);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("timeleft",time);
        dataMap.put("timestamps",timestamps);
        loginResult.setData(dataMap);
        return encryptResponseData(loginResult);
    }


    /**
     * register
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String register(HttpServletRequest request, User user,ClientInfo clientInfo) {
        Map<String, Object> result = new HashMap<>();
        RegisterVO registerVO = JSON.parseObject((String)request.getAttribute("arg"), RegisterVO.class);

        user.setUsercode(registerVO.getUsercode());
        user.setPassword(registerVO.getPassword());
        user.setWeChat(registerVO.getWeChat());
        PasswordHelper.encryptPassword(user);

        clientInfo.setClient_version(registerVO.getClientsVersion());
        clientInfo.setExpires(Timestamp.valueOf(LocalDateTime.now().plusMonths(1)));
        try{
            insertUserAndTime(user,clientInfo);
            result.put("result", "success");
            result.put("code", 0);
        }catch (Exception e){
            e.printStackTrace();
            result.put("result", "error");
            result.put("code", -1);
        }

        return encryptResponseData(result);
    }

    @Transactional
    void insertUserAndTime(User user, ClientInfo clientInfo){
        userMapper.insertUser(user);
        clientInfoMapper.insertClientInfo(clientInfo);
    }


    /**
     * change password
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/changepw", method = RequestMethod.POST)
    public String changepw(HttpServletRequest request, ChangePWResult result) {
        ChangePWVO changePWVO = JSON.parseObject((String)request.getAttribute("arg"), ChangePWVO.class);
        String errorTempKey = "changepw" + Constant.underline + changePWVO.getUsercode();
        User user = userMapper.findUserByName(changePWVO.getUsercode());
        String tempKeyError = template.opsForValue().get(errorTempKey);
        if (StringUtils.isNotBlank(tempKeyError) && Integer.parseInt(tempKeyError) > oneDayErrorTimes) {
            result.setResult("error");
            result.setCode(Constant.USER_PWD_MODIFY_LIMIT);
            return encryptResponseData(result);
        }
        //user or pass is wrong
        if (user == null || !StringUtils.equalsIgnoreCase(PasswordHelper.decryptPassword(changePWVO.getPassword(), user.getCredentialsSalt()), user.getPassword())) {
            result.setCode(Constant.USER_PWD_OR_NULL);
            result.setResult("error");
            if (StringUtils.isBlank(errorTempKey)) {
                template.opsForValue().set(errorTempKey, "1");
            } else {
                template.opsForValue().increment(errorTempKey, 1);
            }
            template.expire(errorTempKey, (24 * 60 * 60 - LocalTime.now().getLong(ChronoField.SECOND_OF_DAY)), TimeUnit.SECONDS);
            return encryptResponseData(result);
        }
        //
        user.setPassword(changePWVO.getPassword());
        PasswordHelper.encryptPassword(user);
        userMapper.updateUser(user);
        template.delete(errorTempKey);
        result.setResult("success");
        return encryptResponseData(result);
    }


    /**
     * heart beat
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/heartbeat", method = RequestMethod.POST)
    public String heartbeat(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        HearBeat hearBeat = JSON.parseObject((String)request.getAttribute("arg"), HearBeat.class);
        String tempKey = hearBeat.getUsercode() + Constant.underline + hearBeat.getCode_Timestamps();
        User user = new User();
        //check has Object
        if (template.opsForHash().hasKey(tempKey, user)) {
            //update expire time
            template.expire(tempKey, 5, TimeUnit.MINUTES);
            result.put("result", "success");
            result.put("code", 0);

        } else {
            result.put("result", "error");
            result.put("code", -1);
        }
        return encryptResponseData(result);
    }


}
