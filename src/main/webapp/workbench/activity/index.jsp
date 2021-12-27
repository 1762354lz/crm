<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>
<script type="text/javascript">

	$(function(){
		$("#updateActivity").click(function () {

			updateActivity();
		})

		$("#editActivity").click(function () {
			if ($("input[name=checkboxOne]:checked").length!=1)
				alert("请选择一个");
			else getUserListAndActivityOneUpdate();
		})

		$("#deleteBtn").click(function ()
		{if ($("input[name=checkboxOne]:checked").length==0)
			alert("请至少选择一个");
		else deleteActivity();
		})



		activityPageList(1,4);

		$("#searchActivityList").click(function () {
			$("#hiddenName").val($.trim($("#searchName").val())),
					$("#hiddenOwner").val($.trim($("#searchOwner").val())),
					$("#hiddenStartDate").val($.trim($("#searchStartTime").val())),
					$("#hiddenEndDate").val($.trim($("#searchEndTime").val()))
			activityPageList(1,4);

		})
		$("#checkboxAll").click(function () {
			$("input[name=checkboxOne]").prop("checked",this.checked);
		})
		$("#activityList").on("click","input[name=checkboxOne]",function () {
			$("#checkboxAll").prop("checked",$("input[name=checkboxOne]").length
					==$("input[name=checkboxOne]:checked").length)

		})


		$("#create-marketActivityName").focus(function () {
			$("#create-marketActivityName").css('border','white')
		})
		$("#createBtn").click(function () {

			$(".time").datetimepicker({
				minView: "month",
				language:  'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				pickerPosition: "bottom-left"
			});
			getUserList();

		})


		$("#saveActivityOne").click(function () {
			saveActivityOne();

		})

		

	});
	function saveActivityOne() {
	if ( $("#create-marketActivityName").val()=="")
	{
		$("#create-marketActivityName").css('border','1px solid red');
		$("#createActivityModal").modal("show");

	}
	else {
		$.ajax({
			url: "workbench/activity/saveActivityOne.do",
			data: {
				"owner": $("#create-owner").val(),
				"name": $.trim($("#create-marketActivityName").val()),
				"startDate": $.trim($("#create-startTime").val()),
				"endDate": $.trim($("#create-endTime").val()),
				"cost": $.trim($("#create-cost").val()),
				"description": $.trim($("#create-describe").val())

			},
			dataType: "json",
			type: "post",
			success:function (data) {
				if (data)
				{
					activityPageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

				$("#createActivityModal").modal("hide");
				$("#activityAddForm")[0].reset();
				}
				else
					alert("创建失败");

			}

		})
	}
	}
	function activityPageList( pageNum, pageSize ) {
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
							activityPageList(data.currentPage , data.rowsPerPage);
						}


					});

		}

	})

	}


	function getActivityList() {
		$.ajax(
				{
					url:"workbench/activity/getActivityList.do",
					dataType:"json",
					type:"get",
					success:function (data) {
						var  html="";
						$.each(data,function (i, activity) {
						html+='<tr class="active">';
						html+='<td><input type="checkbox"  /></td>';
						html+='<td><a style="text-decoration: none; cursor: pointer;"';
						html+='onclick="window.location.href=\'workbench/activity/detail.jsp\';">'+activity.name+"</a></td>";
						html+="<td>"+activity.owner+"</td>";
						html+="<td>"+activity.startDate+"</td>"	;
						html+="<td>"+activity.endDate+"</td>";
						html+="</tr>"
						})
						$("#activityList").html(html);
						$("#createActivityModal").modal("hide")
						$("#activityAddForm")[0].reset();
					}
				}


		)

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
	function deleteActivity(){
		if (confirm("确定？")) {
			var $checkboxOne = $("input[name=checkboxOne]:checked");
			var param = [];
			$checkboxOne.each(function () {
				param.push($(this).val());
			})


			$.ajax(
					{
						url: "workbench/activity/deleteActivity.do",
						data: {params: param},
						dataType: "json",
						type: "get",
						success: function (data) {
							if (data)
								activityPageList(1, 4);
							else
								alert("删除失败");

						}

					})
		}}
	function editActivity() {
		$.ajax(
				{
					url: "workbench/activity/editActivity.do",
					data:{},
					dataType: "json",
					type: "post",
					success: function (data) {

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
					data:{"activityId":$("input[name=checkboxOne]:checked").val()},
					dataType: "json",
					type: "get",
					success: function (data) {
						$("#edit-startTime").val(data.startDate);
						$("#edit-endTime").val(data.endDate);
						$("#edit-cost").val(data.cost);
						$("#edit-marketActivityName").val(data.name);
						$("#edit-marketActivityName").prop("readonly",true);
						$("#edit-describe").val(data.description);
						$("#edit-owner").val(data.owner);
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
			alert("11");
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
							"id": $("input[name=One]:checked").val()
						},
						dataType: "json",
						type: "post",
						success: function (data) {
							if (data) {
								activityPageList(1, 4);
								$("#editActivityModal").modal("hide");
							} else alert("失败");

						}
					}
			)
		}





	

</script>
</head>
<body>
<input type="hidden" id="hiddenName">
<input type="hidden" id="hiddenOwner">
<input type="hidden" id="hiddenStartDate">
<input type="hidden" id="hiddenEndDate">



	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form id="activityAddForm" class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-owner">

								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startTime">
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endTime">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveActivityOne">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">

						<div class="form-group">
							<label for="edit-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner">

								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" >
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-startTime" >
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-endTime" >
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" >
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="updateActivity" type="button" class="btn btn-primary" >更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="searchName">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="searchOwner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="searchStartTime" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="searchEndTime">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="searchActivityList">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary"id="createBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button id="editActivity" type="button" class="btn btn-default" ><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" id="deleteBtn" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="checkboxAll"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityList" >

					</tbody>
				</table>
			</div>

			<div id="activityPage"></div>


		</div>
		
	</div>
</body>
</html>