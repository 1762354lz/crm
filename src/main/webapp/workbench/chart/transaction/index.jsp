<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <script src="jquery/jquery-1.11.1-min.js"></script>
    <script src="ECharts/echarts.min.js"></script>

    <script>

$(function () {
    getCharts()
})
        function getCharts() {
            $.ajax({
                url:"workbench/clue/getChars.do",

                dataType: "json",
                type: "get",
                success:function (data) {
                    var echart=echarts.init(document.getElementById('echarts'))
                    var option={

                        title: {
                            text: '交易漏斗图',
                            subtext: '统计交易阶段数量的漏斗图'
                        },

                        series: [
                            {
                                name:'交易漏斗图',
                                type:'funnel',
                                left: '10%',
                                top: 60,
                                //x2: 80,
                                bottom: 60,
                                width: '80%',
                                // height: {totalHeight} - y - y2,
                                min: 0,
                                max: data.total,
                                minSize: '0%',
                                maxSize: '100%',
                                sort: 'descending',
                                gap: 2,
                                label: {
                                    show: true,
                                    position: 'inside'
                                },
                                labelLine: {
                                    length: 10,
                                    lineStyle: {
                                        width: 1,
                                        type: 'solid'
                                    }
                                },
                                itemStyle: {
                                    borderColor: '#fff',
                                    borderWidth: 1
                                },
                                emphasis: {
                                    label: {
                                        fontSize: 20
                                    }
                                },
                                data: data.activityList
                                /*
                                    [
                                        {value: 60, name: '01资质审查'},
                                        {value: 114, name: '02需求分析'},
                                        {value: 220, name: '03价值建议'},
                                        {value: 80, name: '06谈判复审'},
                                        {value: 100, name: '07成交'}
                                    ]
                                */
                            }
                        ]

                    }
                    echart.setOption(option)
                }

            })

        }
    </script>
</head>
<body>
    <div id="echarts" style="width: 600px;height: 400px"></div>
</body>
</html>
