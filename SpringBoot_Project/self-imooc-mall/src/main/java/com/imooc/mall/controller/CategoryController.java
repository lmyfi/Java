package com.imooc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.pojo.Category;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.model.request.AddCategoryReq;
import com.imooc.mall.model.request.UpdateCategoryReq;
import com.imooc.mall.model.vo.CategoryVO;
import com.imooc.mall.service.CategoryService;
import com.imooc.mall.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * 商品分类控制层
 *
 */
@Controller
public class CategoryController {


    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;


    /**
     * 后台添加目录
     * 添加商品分类 接口
     * @param session
     * @param addCategoryReq
     * @return
     */
    @ApiOperation("后台添加目录")
    @PostMapping("admin/category/add")
    @ResponseBody
    public ApiRestResponse addCategory(HttpSession session, @Valid  @RequestBody AddCategoryReq addCategoryReq){
//        if (addCategoryReq.getName() == null || addCategoryReq.getType() == null
//                || addCategoryReq.getParentId() == null ||addCategoryReq.getOrderNum() == null ){
//            //参数为空，则无法正常添加
//            return ApiRestResponse.error(ImoocMallExceptionEnum.PARA_NOT_NULL);
//        }
        //获取登录用户信息
        User currentUser = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        if (currentUser == null){
            //没有用户登陆，先抛出未登录异常
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN);
        }
        //验证是否为管理员
        boolean checkAdminRole = userService.checkAdminRole(currentUser);
        if (checkAdminRole){
            //为真，为管理员，可以进行后续处理
            categoryService.addCategory(addCategoryReq);
            return ApiRestResponse.success();
        }else{
            //登陆用户不是管理员，返回错误信息
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_ADMIN);
        }
    }

    @ApiOperation("后台更新目录接口")
    @PostMapping("admin/category/update")
    @ResponseBody
    public ApiRestResponse updateCategory(HttpSession session, @Valid @RequestBody UpdateCategoryReq updateCategoryReq){
        //获取登录用户信息
        User currentUser = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        if (currentUser == null){
            //没有用户登陆，先抛出未登录异常
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN);
        }
        //验证是否为管理员
        boolean checkAdminRole = userService.checkAdminRole(currentUser);
        if (checkAdminRole){
            //为真，为管理员，可以进行后续处理,更新操作
            Category category = new Category();
            BeanUtils.copyProperties(updateCategoryReq,category);
            categoryService.updateCategory(category);
            return ApiRestResponse.success();
        }else{
            //登陆用户不是管理员，返回错误信息
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_ADMIN);
        }
    }

    @ApiOperation("后台删除目录接口")
    @PostMapping("admin/category/delete")
    @ResponseBody
    public ApiRestResponse deleteCategory(@RequestParam Integer id){
        categoryService.deleteCategory(id);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台分页目录接口")
    @PostMapping("admin/category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForAdmin(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        PageInfo pageInfo = categoryService.listCategoryForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @ApiOperation("给普通用户看的分页信息")
    @PostMapping("category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForCustom(){
        List<CategoryVO> categoryVOS = categoryService.listCategoryCustom(0);
        return ApiRestResponse.success(categoryVOS);
    }
}
