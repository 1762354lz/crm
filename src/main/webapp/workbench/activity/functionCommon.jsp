<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
</head>
<script type="text/javascript">
    function ClueList( pageNum, pageSize ) {
       alert($.trim($("#searchOwner").val()))
        $.ajax({
            url:"workbench/clue/getClueList.do",
            data:{
                "pageNum":pageNum,
                "pageSize":pageSize,
                "fullname":$.trim($("#searchOwner").val())

            },
            dataType: "json",
            type: "get",
            success:function (data){

                var  html="";
                $.each(data.activityList,function (i, activity) {
                    html+='<tr>';
                    html+='<td><input type="radio" name="contacts" value="'+activity.id+'"/></td>';
                    html+='<td id="'+activity.id+'">'+activity.fullname+'</td>';
//<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.html';">发传单</a></td>

                    html+='<td>'+activity.email+'</td>';
                    html+='<td>'+activity.mphone+'</td>';

                    html+='</tr>';

                })
                $("#clueList").html(html);
                var totalPages;
                totalPages=data.total%pageSize==0?data.total/pageSize:parseInt(data.total/pageSize)+1;

                $("#remarkPage").bs_pagination({
                    currentPage: pageNum, // 页码
                    rowsPerPage: pageSize, // 每页显示的记录条数
                    maxRowsPerPage: 20, // 每页最多显示的记录条数

                    totalRows: data.total, // 总记录条数
                    totalPages: totalPages,
                    visiblePageLinks: 3, // 显示几个卡片

                    showGoToPage: true,
                    showRowsPerPage: true,
                    showRowsInfo: true,
                    showRowsDefaultInfo: true,
                    onChangePage : function(event, data){
                        pageClueList(data.currentPage , data.rowsPerPage);
                    }


                });

            }

        })

    }
    function activityListTransaction( pageNum, pageSize ) {

        $.ajax({
            url:"workbench/activity/activityPageList.do",
            data:{
                "pageNum":pageNum,
                "pageSize":pageSize,
                "searchName":$.trim($("#searchName").val()),
                "searchOwner":$.trim($("#searchOwner").val()),
                "searchStartTime":$.trim($("#searchStartTime").val()),
                "searchEndTime":$.trim($("#searchEndTime").val())
            },
            dataType: "json",
            type: "get",
            success:function (data){

                var  html="";
                $.each(data.activityList,function (i, activity) {
                    //html+='onclick="window.location.href=\'workbench/activity/detail.jsp\';">发传单</a></td>';
                    html+='<tr>';
                    html+='<td><input type="radio" name="activity" value="'+activity.id+'"/></td>';
                    html+='<td id="'+activity.id+'">'+activity.name+'</td>';
//<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.html';">发传单</a></td>

                    html+='<td>'+activity.owner+'</td>';
                    html+='<td>'+activity.startDate+'</td>';
                    html+='<td>'+activity.endDate+'</td>';
                    html+='</tr>';

                })

                $("#activityList").html(html);

                var totalPages;
                totalPages=data.total%pageSize==0?data.total/pageSize:parseInt(data.total/pageSize)+1;

                $("#activityPage").bs_pagination({
                    currentPage: pageNum, // 页码
                    rowsPerPage: pageSize, // 每页显示的记录条数
                    maxRowsPerPage: 20, // 每页最多显示的记录条数

                    totalRows: data.total, // 总记录条数
                    totalPages: totalPages,
                    visiblePageLinks: 3, // 显示几个卡片

                    showGoToPage: true,
                    showRowsPerPage: true,
                    showRowsInfo: true,
                    showRowsDefaultInfo: true,
                    onChangePage : function(event, data){
                        activityListTransaction(data.currentPage , data.rowsPerPage);
                    }


                });

            }

        })

    }

    function ListTransaction( pageNum, pageSize ) {

        $.ajax({
            url:"workbench/clue/ListTransaction.do",
            data:{
                "pageNum":pageNum,
                "pageSize":pageSize,
                "searchName":$.trim($("#searchName").val()),
                "searchOwner":$.trim($("#searchOwner").val()),
                "searchStartTime":$.trim($("#searchStartTime").val()),
                "searchEndTime":$.trim($("#searchEndTime").val())
            },
            dataType: "json",
            type: "get",
            success:function (data){

                var  html="";
                $.each(data.activityList,function (i, activity) {
                html+='<tr class="active">';
                html+='        <td><input value="'+activity.id+'" type="checkbox" name="contacts"/></td>';
                html+='        <td><a style="text-decoration: none; cursor: pointer;"';
                html+='onclick="window.location.href=\'workbench/clue/detailTransaction.do?tranId='+activity.id+'\';">'+activity.name+'</a></td>';
                    //onclick="window.location.href="workbench/clue/detailTransaction.do?id=$("[name=contacts]:checked").val()"";'>'+activity.name+'</a></td>';
               //detail带
           //onclick="window.location.href='workbench/clue/detailTransaction.do?id=$("[name=contacts]:checked").val()
                html+='    <td>'+activity.customerId+'</td>';
                html+='    <td>'+activity.stage+'</td>';
                html+='    <td>'+activity.type+'</td>';
                html+='    <td>'+activity.owner+'</td>';
                html+='    <td>'+activity.source+'</td>';
                html+='    <td>'+activity.contactsId+'</td>';
                html+='    </tr>';


                })

               $("#clueList").html(html);

                var totalPages;
                totalPages=data.total%pageSize==0?data.total/pageSize:parseInt(data.total/pageSize)+1;

                $("#remarkPage").bs_pagination({
                    currentPage: pageNum, // 页码
                    rowsPerPage: pageSize, // 每页显示的记录条数
                    maxRowsPerPage: 20, // 每页最多显示的记录条数

                    totalRows: data.total, // 总记录条数
                    totalPages: totalPages,
                    visiblePageLinks: 3, // 显示几个卡片

                    showGoToPage: true,
                    showRowsPerPage: true,
                    showRowsInfo: true,
                    showRowsDefaultInfo: true,
                    onChangePage : function(event, data){
                    ListTransaction(data.currentPage , data.rowsPerPage);
                    }


                });

            }

        })

    }

    function activityPageListClue( pageNum, pageSize ) {
        $("#checkboxAll").prop("checked",false);
        $.trim($("#searchName").val($("#hiddenName").val())),
            $.trim($("#searchOwner").val($("#hiddenOwner").val())),
            $.trim($("#searchStartTime").val($("#hiddenStartDate").val())),
            $.trim($("#searchEndTime").val($("#hiddenEndDate").val()))

        $.ajax({
            url:"workbench/activity/activityPageList.do",
            data:{
                "pageNum":pageNum,
                "pageSize":pageSize,
                "searchName":$.trim($("#searchName").val()),
                "searchOwner":$.trim($("#searchOwner").val()),
                "searchStartTime":$.trim($("#searchStartTime").val()),
                "searchEndTime":$.trim($("#searchEndTime").val())
            },
            dataType: "json",
            type: "get",
            success:function (data){

                var  html="";
                $.each(data.activityList,function (i, activity) {
                    html+='<tr class="active">';
                    html+='<td><input type="checkbox" name="checkboxOne" value="'+activity.id+'"/></td>';
                    html+='<td><a style="text-decoration: none; cursor: pointer;"';
                    //html+='onclick="window.location.href=\'workbench/activity/detail.jsp\';">发传单</a></td>';

                    html+='onclick="window.location.href=\'workbench/activity/detailActivity.do?';
                    html+='activityDetailId='+activity.id+'\';">'+activity.name+'</a></td>';

//<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.html';">发传单</a></td>

                    html+="<td>"+activity.owner+"</td>";
                    html+="<td>"+activity.startDate+"</td>"	;
                    html+="<td>"+activity.endDate+"</td>";
                    html+='<td><a href="javascript:void(0);"  style="text-decoration: none;">';
                    html+='<span class="glyphicon glyphicon-remove"></span>解除关联</a></td>';

                    html+="</tr>"
                })
                $("#activityList").html(html);
                var totalPages;
                totalPages=data.total%pageSize==0?data.total/pageSize:parseInt(data.total/pageSize)+1;

                $("#activityPage").bs_pagination({
                    currentPage: pageNum, // 页码
                    rowsPerPage: pageSize, // 每页显示的记录条数
                    maxRowsPerPage: 20, // 每页最多显示的记录条数

                    totalRows: data.total, // 总记录条数
                    totalPages: totalPages,
                    visiblePageLinks: 3, // 显示几个卡片

                    showGoToPage: true,
                    showRowsPerPage: true,
                    showRowsInfo: true,
                    showRowsDefaultInfo: true,
                    onChangePage : function(event, data){
                        activityPageListClue(data.currentPage , data.rowsPerPage);
                    }


                });

            }

        })

    }


    function pageRemarkList( pageNum, pageSize ) {

        $.ajax({
            url:"workbench/activity/pageRemarkList.do",
            data:{
                "pageNum":pageNum,
                "pageSize":pageSize,
                "activityId":"${activity.id}"
            },
            dataType: "json",
            type: "get",
            success:function (data){

                var  html="";
                $.each(data.activityList,function (i, remark) {
                html+='<div class="remarkDiv" style="height: 60px;">';
                html+='        <img title="${activity.owner}" src="image/user-thumbnail.png" style="width: 30px; height:30px;">';
                html+='        <div style="position: relative; top: -40px; left: 40px;" >';
                html+='        <h5>'+remark.noteContent+'</h5>';
                html+='   <font color="gray">市场活动</font> <font color="gray">-</font> <b>${activity.name}</b> <small style="color: gray;"> ${activity.createTime} 由${activity.owner}</small>';
                html+='   <div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">';
                html+='       <a class="myHref" onclick="editRemark(\''+remark.noteContent+'\',\''+remark.id+'\')"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>';
                html+='   &nbsp;&nbsp;&nbsp;&nbsp;';
                html+='<a class="myHref" href="javascript:void(0);"onclick="deleteRemark(\''+remark.id+'\')"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>';
                html+='   </div>';
                html+='   </div>';
                html+='   </div>';
                })
                $("#remarkList").html(html);
                var totalPages;
                totalPages=data.total%pageSize==0?data.total/pageSize:parseInt(data.total/pageSize)+1;

                $("#remarkPage").bs_pagination({
                    currentPage: pageNum, // 页码
                    rowsPerPage: pageSize, // 每页显示的记录条数
                    maxRowsPerPage: 20, // 每页最多显示的记录条数

                    totalRows: data.total, // 总记录条数
                    totalPages: totalPages,
                    visiblePageLinks: 3, // 显示几个卡片

                    showGoToPage: true,
                    showRowsPerPage: true,
                    showRowsInfo: true,
                    showRowsDefaultInfo: true,
                    onChangePage : function(event, data){
                        pageRemarkList(data.currentPage , data.rowsPerPage);
                    }


                });

            }

        })

    }

    function pageClueList( pageNum, pageSize ) {

        $.ajax({
            url:"workbench/clue/pageClueList.do",
            data:{
                "pageNum":pageNum,
                "pageSize":pageSize,

            },
            dataType: "json",
            type: "get",
            success:function (data){

                var  html="";
                $.each(data.activityList,function (i, remark) {
                    html+='<div class="remarkDiv" style="height: 60px;">';
                    html+='        <img title="${activity.owner}" src="image/user-thumbnail.png" style="width: 30px; height:30px;">';
                    html+='        <div style="position: relative; top: -40px; left: 40px;" >';
                    html+='        <h5>'+remark.noteContent+'</h5>';
                    html+='   <font color="gray">市场活动</font> <font color="gray">-</font> <b>${activity.name}</b> <small style="color: gray;"> ${activity.createTime} 由${activity.owner}</small>';
                    html+='   <div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">';
                    html+='       <a class="myHref" onclick="editRemark(\''+remark.noteContent+'\',\''+remark.id+'\')"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>';
                    html+='   &nbsp;&nbsp;&nbsp;&nbsp;';
                    html+='<a class="myHref" href="javascript:void(0);"onclick="deleteRemark(\''+remark.id+'\')"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>';
                    html+='   </div>';
                    html+='   </div>';
                    html+='   </div>';
                })
                $("#clueList").html(html);
                var totalPages;
                totalPages=data.total%pageSize==0?data.total/pageSize:parseInt(data.total/pageSize)+1;

                $("#remarkPage").bs_pagination({
                    currentPage: pageNum, // 页码
                    rowsPerPage: pageSize, // 每页显示的记录条数
                    maxRowsPerPage: 20, // 每页最多显示的记录条数

                    totalRows: data.total, // 总记录条数
                    totalPages: totalPages,
                    visiblePageLinks: 3, // 显示几个卡片

                    showGoToPage: true,
                    showRowsPerPage: true,
                    showRowsInfo: true,
                    showRowsDefaultInfo: true,
                    onChangePage : function(event, data){
                        pageClueList(data.currentPage , data.rowsPerPage);
                    }


                });

            }

        })

    }
    function getUserList() {

        $.ajax({
            url:"workbench/activity/getUserList.do",
            dataType:"json",
            type:"get",
            success:function (data) {


                var html= " ";


                $.each(data,function (i,user) {
                    html +="<option value='"+user.id+"'>"+user.name+"</option>";



                })

                $("#create-owner").html(html);

                $("#create-owner").val("${user.id}");
                $("#createActivityModal").modal("show");


            }

        })

    }
    function getUserListAndActivityOneUpdate() {

        getUserListUpdate();
        getActivityOne();
        $("#editActivityModal").modal("show");
    }
    function getActivityOne() {
        $.ajax(
            {
                url: "workbench/activity/getActivityOne.do",
                data:{"activityId":"${activity.id}"},
                dataType: "json",
                type: "get",
                success: function (data) {
                    $("#edit-owner").val(data.owner);
                    $("#edit-startTime").val(data.startDate);
                    $("#edit-endTime").val(data.endDate);
                    $("#edit-cost").val(data.cost);
                    $("#edit-marketActivityName").val(data.name);
                    $("#edit-marketActivityName").prop("readonly",true);
                    $("#edit-describe").val(data.description);

                }

            })
    }
    function getUserListUpdate() {
        $.ajax({
            url: "workbench/activity/getUserList.do",
            dataType: "json",
            type: "get",
            success: function (data) {


                var html = " ";


                $.each(data, function (i, user) {
                    html += "<option value='" + user.id + "'>" + user.name + "</option>";


                })

                $("#edit-owner").html(html);


                /*	ajax(id)
                    $("input[name=checkboxOne]:checked"
                    name
                    $("#edit-marketActivityName").name)
                    $("#editActivityModal").modal("show");*/


            }

        })
    }

    function updateActivity() {

        $.ajax(
            {
                url: "workbench/activity/updateActivity.do",
                data: {
                    "startDate": $.trim($("#edit-startTime").val()),
                    "endDate": $.trim($("#edit-endTime").val()),
                    "cost": $.trim($("#edit-cost").val()),
                    "name": $.trim($("#edit-marketActivityName").val()),
                    "describtion": $.trim($("#edit-describe").val()),
                    "owner": $.trim($("#edit-owner").val()),
                    "id": "${activity.id}"
                },
                dataType: "json",
                type: "post",
                success: function (flag) {
                    if (flag.bool) {
                       $("#cost").html('&nbsp;'+$.trim($("#edit-cost").val()));
                        $("#describtion").html('&nbsp;'+$.trim($("#edit-describe").val()));
                        $("#owner").html($("#edit-owner option:selected").text());
                        $("#endDate").html('&nbsp;'+$.trim($("#edit-endTime").val()));
                        $("#startDate").html('&nbsp;'+$.trim($("#edit-startTime").val()));
                        $("#editBy").html(flag.activity.editBy);
                        $("#editTime").html(flag.activity.editTime);
                        $("#editActivityModal").modal("hide");
                    } else alert("失败");

                }
            }
        )
    }
function selectDetailActivityAjax() {

    getActivityOneDiv();
}
    function getActivityOneDiv() {

        $.ajax(
            {
                url: "workbench/activity/getActivityOne.do",
                data:{"activityId":"${activity.id}"},
                dataType: "json",
                type: "get",
                success: function (data) {

                    var html="";

                    html+='市场活动-'+data.name+'<small id="activityDate">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+data.startDate+'~'+data.endDate+'</small>';
                    $("#activityName").html(html);
                    html="";
                    html+='<div style="position: relative; left: 40px; height: 30px;">';
                    html+='<div style="width: 300px; color: gray;">所有者</div>';
                    html+='<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b></b>'+$("#edit-owner option:selected").text()+'</div>';
                    html+='<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">名称</div>';
                    html+='<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>'+data.name+'</b></div>';
                    html+='<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>';
                    html+='<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>';
                    html+='</div>';

                    html+='<div style="position: relative; left: 40px; height: 30px; top: 10px;">';
                    html+='<div style="width: 300px; color: gray;">开始日期</div>';
                    html+='<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>'+data.startDate+'</b></div>';
                    html+='<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">结束日期</div>';
                    html+='<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>'+data.endDate+'</b></div>';
                    html+='<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>';
                    html+='		<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>';
                    html+='		</div>';
                    html+='		<div style="position: relative; left: 40px; height: 30px; top: 20px;">';
                    html+='		<div style="width: 300px; color: gray;">成本</div>';
                    html+='		<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>'+data.cost+'</b></div>';
                    html+='<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px;"></div>';
                    html+='		</div>';
                    html+='		<div style="position: relative; left: 40px; height: 30px; top: 30px;">';
                    html+='		<div style="width: 300px; color: gray;">创建者</div>';
                    html+='		<div style="width: 500px;position: relative; left: 200px; top: -20px;">';
                    html+='<b>'+data.createBy+'&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">'+data.createTime+'</small></div>';
                    html+='<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>';
                    html+='		</div>';
                    html+='		<div style="position: relative; left: 40px; height: 30px; top: 40px;">';
                    html+='		<div style="width: 300px; color: gray;">修改者</div>';
                    html+='	<div style="width: 500px;position: relative; left: 200px; top: -20px;">';
                    html+='<b>'+data.editBy+'&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">'+data.editTime+'</small></div>';
                    html+='<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>';
                    html+='		</div>';
                    html+='		<div style="position: relative; left: 40px; height: 30px; top: 50px;">';
                    html+='		<div style="width: 300px; color: gray;">描述</div>';
                    html+='		<div style="width: 630px;position: relative; left: 200px; top: -20px;">';
                    html+='		<b>';
                    html+=data.describtion;
                    html+='</b>';
                    html+='</div>';
                    html+='<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>';
                    html+='		</div>';
                    $("#detailActivity").html(html);

                }

            })
    }
    function deleteActivity(){
        if (confirm("确定？")) {
            var param = [];
            param.push("${activity.id}");
            $.ajax(
                {
                    url: "workbench/activity/deleteActivity.do",
                    data: {params: param},
                    dataType: "json",
                    type: "get",
                    success: function (data) {
                        if (data) {
                            $("[name=delete]").html("");
                            $("#remark").prop("readonly",true);
                        }
                            else
                            alert("删除失败");

                    }

                })
        }}



</script>
<body>

</body>
</html>
