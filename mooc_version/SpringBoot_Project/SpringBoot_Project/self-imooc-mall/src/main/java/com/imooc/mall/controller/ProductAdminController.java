package com.imooc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.AddProductReq;
import com.imooc.mall.model.request.UpdateProductReq;
import com.imooc.mall.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.UUID;

/**
 *   后台商品管理Controller
 */
@RestController //默认为该类中的所有方法添加了    @ResponseBody
public class ProductAdminController {
    @Autowired
    ProductService productService;

    @ApiOperation("后台管理员添加商品接口")
    @PostMapping("admin/product/add")
    public ApiRestResponse addProduct(@Valid @RequestBody AddProductReq addProductReq){
        //添加商品服务
        productService.addProduct(addProductReq);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台管理员上传图片接口")
    @PostMapping("admin/upload/file")
    public ApiRestResponse upload(HttpServletRequest httpServletRequest, @RequestParam("file")MultipartFile file){
        String fileName = file.getOriginalFilename();//获取文件的原始名字
        String suffixName = fileName.substring(fileName.lastIndexOf("."));//获取最后一个点"."，将图片格式截取下来，如“jpg”
        //生成文件名称UUID
        UUID uuid = UUID.randomUUID();//随机生成UUID
        String newFileName = uuid.toString() + suffixName; //将uuid与图片格式拼接起来，形成新的文件
        //创建文件
        File fileDirectory = new File(Constant.FILE_UPLOAD_DIR);//目录
        File destFile = new File(Constant.FILE_UPLOAD_DIR + newFileName);//目的文件路径名
        //判断文件目录和文件是否创建成功
        if (!fileDirectory.exists()){
            //文件不存在，尝试使用mkdir创建文件目录
            if (!fileDirectory.mkdir()){
                //创建失败，抛出异常
                throw new ImoocMallException(ImoocMallExceptionEnum.MKDIR_FAILED);
            }
        }
        //文件目录和文件都创建成功，将源文件写入为目的文件
        try {
            //上传的图片，写入到目的文件中
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            //成功，传回图片文件路径
            return ApiRestResponse
                    .success(getHost(new URI
                            (httpServletRequest.getRequestURL() + "")) + "/images/" + newFileName);
        } catch (URISyntaxException e) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.UPLOAD_FAILED);
        }

    }

    //获取主机信息
    private URI getHost(URI uri){
        URI effectiveURI;
        try {
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), null, null, null);
        } catch (URISyntaxException e) {
            effectiveURI = null;
        }
        return effectiveURI;
    }

    @ApiOperation("后台管理员更新商品接口")
    @PostMapping("admin/product/update")
    public ApiRestResponse updateProduct(@RequestBody UpdateProductReq updateProductReq){
        Product product = new Product();//新建商品类，作为更新对象
        BeanUtils.copyProperties(updateProductReq,product);
        productService.updateProduct(product);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台管理员删除商品接口")
    @PostMapping("admin/product/delete")
    public ApiRestResponse updateProduct(@RequestParam("id") Integer id){
        //删除商品
        productService.deleteProduct(id);
        return ApiRestResponse.success();
    }

    /**
     *
     * @param ids 要批量上下架的商品id
     * @param sellStatus 上下架状态 1是上架，0是下架
     * @return
     */
    @ApiOperation("后台批量上下架商品接口")
    @PostMapping("admin/product/batchUpdateSellStatus")
    public ApiRestResponse batchUpdateSellStatus(@RequestParam Integer[] ids,
                                                 @RequestParam Integer sellStatus){
        //删除商品
        productService.batchUpdateSellStatus(ids,sellStatus);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台商品列表接口")
    @PostMapping("admin/product/list")
    public ApiRestResponse list(@RequestParam Integer pageNum,
                                                 @RequestParam Integer pageSize){
        //删除商品
        PageInfo pageInfo = productService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }
}
