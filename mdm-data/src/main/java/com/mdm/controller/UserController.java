package com.mdm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdm.common.AjaxResponse;
import com.mdm.common.BaseController;
import com.mdm.common.Constant;
import com.mdm.entity.User;
import com.mdm.service.IUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping(value = "user")
@Api(description = Constant.MODULE_POINT)
public class UserController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    
    private final IUserService iUserservice;
    
    public UserController(IUserService iUserservice) {
        this.iUserservice=iUserservice;
    }
    
    @ApiOperation(value = "新增用户", notes = "请求参数:用户实体 user ")
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public AjaxResponse add(@RequestBody User user) {
        try {
            int str=iUserservice.insert(user);
            return AjaxResponse.reponseBody(Constant.SUCCESS_CODE, Constant.SUCCESS_MSG, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @ApiOperation(value = "查看所有用户", notes = " ")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public AjaxResponse list() {
        try {
            List<User> list=iUserservice.userlist();
            return AjaxResponse.reponseBody(Constant.SUCCESS_CODE, Constant.SUCCESS_MSG, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @ApiOperation(value = "根据ID查询用户", notes = "userid 用户id")
    @ResponseBody
    @RequestMapping(value = "/byid/{userid}", method = RequestMethod.GET)
    public AjaxResponse getByid(@PathVariable Integer userid) {
     try {
         System.out.println("-----------------------");
         System.out.println(userid);
        User user=iUserservice.selectByUserId(Integer.valueOf(userid));
        return AjaxResponse.reponseBody(Constant.SUCCESS_CODE, Constant.SUCCESS_MSG, user);
    } catch (Exception e) {
        e.printStackTrace();
    }
        return null;
    }
    
}
