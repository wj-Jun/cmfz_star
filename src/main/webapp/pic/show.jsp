<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script>
        $(function () {
            $("#userTable").jqGrid({
                url : '${pageContext.request.contextPath}/pic/selectAll',
                datatype : "json",
                colNames : [ '编号', '名称', '封面', '描述', '状态','上传日期'],
                colModel : [
                    {name:"id",hidden:true},//colmodel参数全部写在  列属性都写在对应的列里面
                    {name:"name",editable:true},
                    {name:"cover",editable:true,edittype:"file", formatter:function(cellvalue, options, rowObject){
                            return "<img style='width:100px;height:60px;' src='${pageContext.request.contextPath}/pic/img/"+rowObject.cover+"'>"
                        }
                    },
                    {name:"description",editable:true,},
                    {name:"status",editable:true,edittype:"select",editoptions:{value:"正常:正常;冻结:冻结"}},
                    {name:"createdate",formatter:'date',formatoptions:{newfornat:'yyyy-MM-dd'}}
                ],
                height:250,
                autowidth:true,
                styleUI:"Bootstrap",
                pager:"#pager",//用来指定分页工具栏  后台接收参数变量名 page 当前页 和 rows 每页显示条数
                rowNum:3,//每页显示记录数 推荐最好是rowList中一个子元素
                rowList:[3,10,20,30],//下拉列表 指定每页显示记录数
                sortname : 'id',
                viewrecords : true,
                caption : "轮播图列表",
            }).jqGrid("navGrid","#pager",{edit : true,add : true,del : true,search:false},{
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
                            url:"${pageContext.request.contextPath}/pic/upload",
                            type:"post",
                            fileElementId:"cover",
                            data:{id:id},
                            success:function (response) {
                                //自动刷新jqgrid表格
                                $("#userTable").trigger("reloadGrid");
                            }
                        });
                    }
                    return "123";
                }
            });
        });

    </script>

</head>
<body>
<div class="panel panel-heading">
    <h3 >所有的轮播图</h3>
</div>

<!--创建表格-->
<table id="userTable" ></table>

<!--分页-->
<div id="pager" style="height: 40px"></div>

</body>


</html>