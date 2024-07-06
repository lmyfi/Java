package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

/**
 * 用户Controller
 */
@Component
@Controller
public class UserController {
    //    需要持有用户服务
    @Autowired
    UserService userService;

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/test")
    @ResponseBody
    public User personPage(){
        return userService.getUser();
    }

    /**
     * 用户注册接口
     * @param userName 注册用户名
     * @param password 密码
     * @return
     */
    @PostMapping("/register")
    @ResponseBody //返回Json数据
    public ApiRestResponse register(@RequestParam("username") String userName,
                                    @RequestParam("password") String password) throws ImoocMallException, NoSuchAlgorithmException {
        //用户名为空 返回自定义异常信息
        if (StringUtils.isEmpty(userName)){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_USER_NAME);
        }
        //密码设置不能为空
        if (StringUtils.isEmpty(password)){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_PASSWORD);
        }
        //密码长度不能少于8位
        if (password.length() < 8){
            return ApiRestResponse.error(ImoocMallExceptionEnum.PASSWORD_TOO_SHORT);
        }

        userService.register(userName,password);
        //register方法执行成功，即返回成功信息对象
        return ApiRestResponse.success();
    }

    /**
     * 登录接口
     * @param userName 用户名
     * @param password 密码
     * @param session 用于存放用户信息
     * @return
     */
    @PostMapping("/login")
    @ResponseBody //返回Json序列
    public ApiRestResponse login(@RequestParam("username") String userName,
                                    @RequestParam("password") String password, HttpSession session) throws NoSuchAlgorithmException, ImoocMallException {
        //用户名为空 返回自定义异常信息
        if (StringUtils.isEmpty(userName)){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_USER_NAME);
        }
        //密码设置不能为空
        if (StringUtils.isEmpty(password)){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_PASSWORD);
        }
        //登录成功,准备返回登陆用户信息
        User user = userService.login(userName, password);
        //保存用户信息时，不保存密码，防止别人拿到，所以将密码置为null
        user.setPassword(null);
        session.setAttribute(Constant.IMOOC_MALL_USER,user);//将用户信息，以键-值对的方式存放在session中
        return ApiRestResponse.success(user);
    }

    /**
     * 更新用户个性签名
     * @param signature 用户个性标签签名内容
     * @param session  HttpSession获取登陆用户对象
     * @return
     */
    @PostMapping("/user/update")
    @ResponseBody
    public ApiRestResponse update(@RequestParam("signature") String signature,HttpSession session) throws ImoocMallException {
        //从session中获取用户对象
        User currentUser = (User)session.getAttribute(Constant.IMOOC_MALL_USER);
        if (currentUser == null){
            //没有获取到对象，说明session中没有对象，即没有用户登陆，抛出需要登陆异常
            throw new ImoocMallException(ImoocMallExceptionEnum.NEED_LOGIN);
        }
        User user = new User();
        //获取要更新的用户ID
        user.setId(currentUser.getId());
        //更新个性签名
        user.setPersonalizedSignature(signature);
        //调用更新服务实现类
        userService.updateInformation(user);
        //更新个性签名成功,返回成功信息
        return ApiRestResponse.success();
    }

    /**
     * 登出接口，清除session
     * @param session
     * @return
     */
    @PostMapping("/user/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session){
        session.removeAttribute(Constant.IMOOC_MALL_USER);
        return ApiRestResponse.success();
    }

    /**
     * 管理员登录接口
     * @param userName 用户名
     * @param password 密码
     * @param session 用于存放用户信息
     * @return
     */
    @PostMapping("/adminLogin")
    @ResponseBody //返回Json序列
    public ApiRestResponse adminLogin(@RequestParam("username") String userName,
                                 @RequestParam("password") String password, HttpSession session) throws NoSuchAlgorithmException, ImoocMallException {
        //用户名为空 返回自定义异常信息
        if (StringUtils.isEmpty(userName)){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_USER_NAME);
        }
        //密码设置不能为空
        if (StringUtils.isEmpty(password)){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_PASSWORD);
        }
        //登录成功,准备返回登陆用户信息
        User user = userService.login(userName, password);
        //检查是否为管理员
        if (userService.checkAdminRole(user)){
            //是管理员，执行操作
            //保存用户信息时，不保存密码，防止别人拿到，所以将密码置为null
            user.setPassword(null);
            session.setAttribute(Constant.IMOOC_MALL_USER,user);//将用户信息，以键-值对的方式存放在session中
            return ApiRestResponse.success(user);
        }else {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_ADMIN);
        }
    }


}
