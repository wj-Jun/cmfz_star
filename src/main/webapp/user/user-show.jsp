<%@page pageEncoding="UTF-8" %>
<script>
    $(function () {
        $("#article-show-table").jqGrid({
            url : "${pageContext.request.contextPath}/user/UserselectAll",
            datatype : "json",
            colNames : [ '编号', '用户名', '昵称', '手机号', '省份','城市','头像','签名','性别'],
            colModel : [
                {name : 'id'},
                {name : 'username'},
                {name : 'nickname'},
                {name : 'phone'},
                {name : 'province'},
                {name : 'city'},
                {name : 'photo',formatter:function(cellvalue, options, rowObject){
                        return "<img style='width:100px;height:47px;' src='${pageContext.request.contextPath}/user/img/"+rowObject.photo+"'>"
                    }},
                {name : 'sign'},
                {name : 'sex'}
            ],
            styleUI:"Bootstrap",
            autowidth:true,
            rowNum : 3,
            rowList : [ 3, 5, 10 ],
            pager : '#article-page',
            viewrecords : true,
            caption : "用户信息展示列表",
            editurl : "${pageContext.request.contextPath}/article/edit"
        }).navGrid("#article-page", {edit : false,add : false,del : false});
    })


</script>



<ul class="nav nav-tabs">
    <li role="presentation" class="active"><a href="#">所有用户</a></li>
    <li role="presentation"><a href="${pageContext.request.contextPath}/user/select" onclick="openModal('add','')">导出文章到excle</a></li>
</ul>
<table id="article-show-table"></table>
<div id="article-page" style="height: 40px"></div>
d