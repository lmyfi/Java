<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>慕课书评网</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <link rel="stylesheet" href="/resources/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="/resources/raty/lib/jquery.raty.css">
    <script src="/resources/jquery.3.3.1.min.js"></script>
    <script src="/resources/bootstrap/bootstrap.min.js"></script>
    <script src="/resources/art-template.js"></script>
    <script src="/resources/raty/lib/jquery.raty.js"></script>

<#--&lt;#&ndash;    绝对路径&ndash;&gt;-->
<#--    <link rel="stylesheet" href="/src/main/webapp/resources/bootstrap/bootstrap.css">-->
<#--    <link rel="stylesheet" href="/src/main/webapp/resources/raty/lib/jquery.raty.css">-->
<#--    <script src="/src/main/webapp/resources/jquery.3.3.1.min.js"></script>-->
<#--    <script src="/src/main/webapp/resources/bootstrap/bootstrap.min.js"></script>-->
<#--    <script src="/src/main/webapp/resources/art-template.js"></script>-->
<#--    <script src="/src/main/webapp/resources/raty/lib/jquery.raty.js"></script>-->

<#--&lt;#&ndash;星形评分插件&ndash;&gt;-->
<#--    <script>-->
<#--        $.fn.raty.defaults.path = "/resources/raty/lib/images";-->
<#--        // $.fn.raty.defaults.path = "/src/main/webapp/resources/raty/lib/images";-->
<#--        function loadMore(isReset) {-->
<#--            if (isReset == true) {-->
<#--                $("#bookList").html("");-->
<#--                $("#nextPage").val(1);-->
<#--            }-->
<#--            var nextPage = $("#nextPage").val();-->
<#--            var categoryId = $("#categoryId").val();-->
<#--            var order = $("#order").val();-->

<#--            $(function () {-->
<#--                $.ajax({-->
<#--                    url: "/books",-->
<#--                    data: {p: nextPage, "categoryId": categoryId, "order": order},-->
<#--                    type: "get",-->
<#--                    dataType: "json",-->
<#--                    success: function (json) {-->
<#--                        var list = json.records;-->
<#--                        for (var i = 0; i < list.length; i++) {-->
<#--                            var book = list[i];-->
<#--                            var html = template("tpl", book);-->
<#--                            $("#bookList").append(html);-->
<#--                        }-->
<#--                        $(".stars").raty({readonly: true});-->
<#--                        if (json.current < json.pages) {-->
<#--                            $("#nextPage").val(parseInt(json.current) + 1);-->
<#--                            $("#btnMore").show();-->
<#--                            $("#divNoMore").hide();-->
<#--                        } else {-->
<#--                            $("#btnMore").hide();-->
<#--                            $("#divNoMore").show();-->
<#--                        }-->
<#--                    }-->
<#--                })-->
<#--            });-->
<#--        }-->
<#--        loadMore(true);-->
<#--    </script>-->

<#--按钮事件-->
<#--    <script>-->
<#--        $(function () {-->
<#--            $("#btnMore").click(function () {-->
<#--                loadMore(false);-->
<#--            })-->

<#--            $(".category").click(function () {-->
<#--                $(".category").removeClass("highlight");-->
<#--                $(".category").addClass("text-black-50");-->
<#--                $(this).addClass("highlight");-->

<#--                var categoryId = $(this).data("category");-->
<#--                $("#categoryId").val(categoryId);-->

<#--                loadMore(true);-->
<#--            })-->

<#--            $(".order").click(function () {-->
<#--                $(".order").removeClass("highlight");-->
<#--                $(".order").addClass("text-black-50");-->
<#--                $(this).addClass("highlight");-->

<#--                var order = $(this).data("order");-->
<#--                $("#order").val(order);-->

<#--                loadMore(true);-->
<#--            })-->
<#--        })-->
<#--    </script>-->


    <style>
        .highlight {
            color: red !important;
        }

        a:active {
            text-decoration: none !important;
        }

        .container {
            padding: 0px;
            margin: 0px;
        }

        .row {
            padding: 0px;
            margin: 0px;
        }

        .col- * {
            padding: 0px;
        }
    </style>

<#--首页封面形式定义-->
<#--    使用Js模板引擎art-template-->
    <script type="text/html" id="tpl">
        <a href="/book/{{bookId}}" style="color: inherit">
            <div class="row mt-2 book">
                <div class="col-4 mb-2 pr-2">
                    <img class="img-fluid" src="{{cover}}">
                </div>
                <div class="col-8  mb-2 pl-0">
                    <h5 class="text-truncate">{{bookName}}</h5>

                    <div class="mb-2 bg-light small  p-2 w-100 text-truncate">{{author}}</div>


                    <div class="mb-2 w-100">{{subTitle}}</div>

                    <p>
                        <span class="stars" data-score="{{evaluationScore}}" title="gorgeous"/>
                        <span class="mt-2 ml-2">{{evaluationScore}}</span>
                        <span class="mt-2 ml-2">{{evaluationQuantity}}人已评</span>
                    </p>
                </div>
            </div>
        </a>
        <hr>
    </script>

<#--    前端主要部分-->
    <script>
        $.fn.raty.defaults.path = "/resources/raty/lib/images";

        /**
         * loadmore()加载更多数据
         * @param isRest参数设置为true,代表从第一页开始查询，否则按nextPage查询后续页
         */
        function loadmore(isRest){
            if (isRest == true){
                $("#bookList").html("");//将所有前端页面清空
                $("#nextPage").val(1);//初始化显示的页数为第一页
            }
            var nextPage = $("#nextPage").val();//页数
            var categoryId = $("#categoryId").val();//图书编号
            var order = $("#order").val();//排序方法

            //Ajax请求
            $.ajax({
                url : "/books",
                data : {p : nextPage, "categoryId":categoryId, "order":order},
                type : "get",
                dataType : "json",
                success : function (json) {
                    console.info(json);
                    var list = json.records;
                    for (var i = 0; i < list.length; i++){
                        var book = json.records[i];
                        //    将数据结合tpl模板，生成html
                        var html = template("tpl",book);
                        // console.info(html);
                        $("#bookList").append(html);
                    }
                    //显示星形组件
                    $(".stars").raty({readonly: true});
                    //判断是否还有书籍
                    if (json.current < json.pages){
                        $("#nextPage").val(parseInt(json.current) + 1);
                        $("#btnMore").show();
                        $("#divNoMore").hide();
                    }else{
                        $("#btnMore").hide();
                        $("#divNoMore").show();
                    }
                }
            })
        }

        //原生的一页Ajax动态加载图片代码
        $(function (){
            // $.ajax({
            //     url : "/books",
            //     data : {p : 1},
            //     type : "get",
            //     dataType : "json",
            //     success : function (json) {
            //         console.info(json);
            //         var list = json.records;
            //         for (var i = 0; i < list.length; i++){
            //             var book = json.records[i];
            //             //    将数据结合tpl模板，生成html(使用了art-template模板引擎)
            //             var html = template("tpl",book);
            //             $("#bookList").append(html);
            //         }
            //         //显示星形组件
            //         $(".stars").raty({readonly: true});
            //     }
            // })
            loadmore(true);
        })

        <#--    实现加载更多-->
        $(function (){
            $("#btnMore").click(function (){
                loadmore();
            })
            //类别部分设置点击样式
            $(".category").click(function (){
                $(".category").removeClass("highlight");//移除高亮，红色
                $(".category").addClass("text-black-50");//样式设置为黑色
                $(this).addClass("highlight");//为当前点击的部分设置为高亮
                // var categoryId = $(this).data("category");//获取点击时的category对应的值
                var categoryId = $(this).data("category");
                $("#categoryId").val(categoryId);    //将category值赋值给categoryId
                loadmore(true);//点击时刷新页面
            })
            //排序部分设置点击样式
            $(".order").click(function (){
                $(".order").removeClass("highlight");//移除高亮，红色
                $(".order").addClass("text-black-50");//样式设置为黑色
                $(this).addClass("highlight");//为当前点击的部分设置为高亮
                var order = $(this).data("order");
                $("#order").val(order);
                loadmore(true);//点击时刷新页面
            })
        })
    </script>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-light bg-white shadow mr-auto">
        <ul class="nav">
            <li class="nav-item">
                <a href="/">
                    <img src="https://m.imooc.com/static/wap/static/common/img/logo2.png" class="mt-1"
                         style="width: 100px">
                </a>
            </li>

        </ul>

        <#if loginMember??>
            <h6 class="mt-1">
                <a>
                    <img style="width: 2rem;margin-top: -5px" class="mr-1"
                         src="/images/user_icon.png">${loginMember.nickname}
                </a>
            </h6>
        <#else>
            <a href="/login.html" class="btn btn-light btn-sm">
                <img style="width: 2rem;margin-top: -5px" class="mr-1" src="/images/user_icon.png">登录
            </a>
        </#if>
    </nav>
    <div class="row mt-2">


        <div class="col-8 mt-2">
            <h4>热评好书推荐</h4>
        </div>

        <div class="col-8 mt-2">
            <span data-category="-1" style="cursor: pointer" class="highlight  font-weight-bold category">全部</span>
            |
            <#list categoryList as category>
                <a style="cursor: pointer" data-category="${category.categoryId}"
                   class="text-black-50 font-weight-bold category">${category.categoryName}</a>
                <#if category_has_next>
                    |
                </#if>
            </#list>
        </div>

        <div class="col-8 mt-2">
            <span data-order="quantity" style="cursor: pointer"
                  class="order highlight  font-weight-bold mr-3">按热度</span>

            <span data-order="score" style="cursor: pointer"
                  class="order text-black-50 mr-3 font-weight-bold">按评分</span>
        </div>
    </div>
    <div class="d-none">
        <input type="hidden" id="nextPage" value="2">
        <input type="hidden" id="categoryId" value="-1">
        <input type="hidden" id="order" value="quantity">
    </div>

    <div id="bookList">

    </div>
    <button type="button" id="btnMore" data-next-page="1" class="mb-5 btn btn-outline-primary btn-lg btn-block">
        点击加载更多...
    </button>
    <div id="divNoMore" class="text-center text-black-50 mb-5" style="display: none;">没有其他数据了</div>
</div>

</body>
</html>