<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    $(function () {
        $("#table").jqGrid({
            url : '${pageContext.request.contextPath}/album/selectAll',
            datatype : "json",
            height : 200,
            colNames : [ '编号', '专辑名称', '专辑作者', '专辑封面', '音乐数量', '专辑简介','创建时间' ],
            colModel : [
                {name : 'id'},
                {name : 'name',editable:true},
                {name : 'starid',editable:true,edittype:"select",editoptions:{dataUrl:"${pageContext.request.contextPath}/star/starName"},formatter:function (value,option,rows) {
                        return rows.star.realname;
                    }},
                {name : 'cover',editable:true,edittype:"file",formatter:function(cellvalue, options, rowObject){
                        return "<img style='width:100px;height:47px;' src='${pageContext.request.contextPath}/album/img/"+rowObject.cover+"'>"
                    }},
                {name : 'count',editable:true},
                {name : 'brief',editable:true},
                {name : 'createdate',editable:true,edittype:"date",formatter:'date',formatoptions:{newfornat:'yyyy-MM-dd'}}
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
            editurl : "${pageContext.request.contextPath}/album/edit",
            subGridRowExpanded : function(subgrid_id, id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id  +"' class='scroll'></table>" +
                    "<div id='" + pager_id + "' class='scroll'></div>");
                $("#" + subgrid_table_id).jqGrid(
                    {
                        url : "${pageContext.request.contextPath}/chapter/selectAll?albumId=" + id,
                        datatype : "json",
                        colNames : [ '编号', '名字', '歌手', '大小','时长', '创建时间','在线播放' ],
                        colModel : [
                            {name : "id",hidden:true},
                            {name : "name",editable:true,edittype:"file"},
                            {name : "singer",editable:true},
                            {name : "sizes"},
                            {name : "duration"},
                            {name : "createdate"},
                            {name : "operation",width:300,formatter:function (value,option,rows) {
                                    return "<audio controls>\n" +
                                        "  <source src='${pageContext.request.contextPath}/album/imge/"+rows.name+"' >\n" +
                                        "</audio>";
                                }}
                        ],
                        styleUI:"Bootstrap",
                        rowNum : 2,
                        pager : pager_id,
                        autowidth:true,
                        height : '100%',
                        editurl:"${pageContext.request.contextPath}/chapter/edit?albumId="+id,
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {
                        edit : false,
                        add : true,
                        del : false,
                        search:false
                    },{},{
                        //    控制添加
                        closeAfterAdd:true,
                        afterSubmit:function (response) {
                            var status = response.responseJSON.status;
                            if(status){
                                var cid = response.responseJSON.message;
                                $.ajaxFileUpload({
                                    url:"${pageContext.request.contextPath}/chapter/upload",
                                    type:"post",
                                    fileElementId:"name",
                                    data:{id:cid,albumId:id},
                                    success:function () {
                                        $("#"+subgrid_table_id).trigger("reloadGrid");
                                    }
                                })
                            }
                            return "123";
                        }
                    });
            }
        }).jqGrid('navGrid', '#pager',{add: true, edit : true, del : true},{
                //控制修改操作
                closeAfterEdit:true,
                beforeShowForm:function (fmt) {
                    //让文件上传为只读状态
                    fmt.find("#cover").attr("disabled",true);
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
                            url:"${pageContext.request.contextPath}/album/upload",
                            type:"post",
                            fileElementId:"cover",
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