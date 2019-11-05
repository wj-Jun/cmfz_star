<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <!-- 引入 echarts.js -->
    <script src="${pageContext.request.contextPath}/echarts/echarts.min.js"></script>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '用户注册趋势图'
        },
        tooltip: {},
        legend: {
            data:['男','女']
        },
        xAxis: {
            data: ["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"]
        },
        yAxis: {},
        series: [{
            name: '男',
            type:'line',//折线图(Line)
            data: [5, 20, 36, 10, 10, 20]
        },{
            name: '女',
            type:'line',//折线图(Line)
            data: [5, 12, 45, 134, 54, 100]
        }

        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    $.ajax({
        url:"${pageContext.request.contextPath}/user/queryAll",
        datatype:"json",
        type:"post",
        success:function (result) {
            console.log(result);
            myChart.setOption({
                series: [{
                    name: '男',
                    type:'line',//折线图(Line)
                    data: result.nan
                },{
                    name: '女',
                    type:'line',//折线图(Line)
                    data: result.integers
                }

                ]
            })
        }
    })

</script>
</body>
</html>