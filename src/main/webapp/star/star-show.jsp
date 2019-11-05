<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    $(function () {
        $("#table").jqGrid({
            url : '${pageContext.request.contextPath}/star/selectAll',
            datatype : "json",
            height : 200,
            colNames : [ '编号', '艺名', '真实姓名', '照片', '性别','生日' ],
            colModel : [
                {name : 'id'},
                {name : 'nickname',editable:true},
                {name : 'realname',editable:true},
                {name : 'photo',editable:true,edittype:"file",formatter:function(cellvalue, options, rowObject){
                        return "<img style='width:100px;height:47px;' src='${pageContext.request.contextPath}/star/img/"+rowObject.photo+"'>"
                    }},
                {name : 'sex',editable:true,edittype:"select",editoptions:{value:"男:男;女:女"}},
                {name : 'bir',editable:true,edittype:"date",formatter:'date',formatoptions:{newfornat:'yyyy-MM-dd'}}
            ],
            autowidth:true,
            styleUI:"Bootstrap",
            rowNum : 3,
            rowList : [ 3, 5, 10],
            pager : '#pager',
            sortname : 'id',
            viewrecords : true,
            subGrid : true,
            caption : "所有明星大合集",
            editurl : "${pageContext.request.contextPath}/star/edit",
            subGridRowExpanded : function(subgrid_id, id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id  +"' class='scroll'></table>" +
                    "<div id='" + pager_id + "' class='scroll'></div>");
                $("#" + subgrid_table_id).jqGrid(
                    {
                        url : "${pageContext.request.contextPath}/user/UserselectAll?starId=" + id,
                        datatype : "json",
                        colNames : [ '编号', '用户名', '昵称', '头像','电话', '性别','地址','签名' ],
                        colModel : [
                            {name : "id"},
                            {name : "username"},
                            {name : "nickname"},
                            {name : "photo"},
                            {name : "phone"},
                            {name : "sex"},
                            {name : "address"},
                            {name : "sign"}
                        ],
                        styleUI:"Bootstrap",
                        rowNum : 2,
                        pager : pager_id,
                        autowidth:true,
                        height : '100%'
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit : false,
                        add : false,
                        del : false,
                        search:false
                    });
            },
    }).jqGrid('navGrid', '#pager',{add: true, edit : true, del : true},{
            //控制修改操作
            closeAfterEdit:true,
            beforeShowForm:function (fmt) {
                //让文件上传为只读状态
                fmt.find("#photo").attr("disabled",true);
            }

        },{
              //控制添加
            closeAfterAdd:true,//添加后关闭窗口
            afterSubmit:function (data) {

                console.log(data);
                var status = data.responseJSON.status;
                var id = data.responseJSON.message;
                if(status){
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/star/upload",
                        type:"post",
                        fileElementId:"photo",
                        data:{id:id},
                        success:function (response) {
                            //自动刷新jqgrid表格
                            $("#table").trigger("reloadGrid");
                        }
                    });
                }
                return "123";
            }

        }

    );
    })


</script>

<body>
<div class="panel panel-heading">
    <h3 >展示明星列表</h3>
</div>

<!--创建表格-->
<table id="table" ></table>

<!--分页-->
<div id="pager" style="height: 40px"></div>

</body>