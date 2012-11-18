<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/WEB-INF/commons/taglibs.jsp"%>
<%@include file="/WEB-INF/commons/common-header.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"
	src="${ctx }/resources/js/markitup/jquery.markitup.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx }/resources/js/markitup/skins/markitup/style.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx }/resources/js/markitup/sets/default/style.css" />
</head>
<body>
	<script type="text/javascript">
		mySettings = {
			onShiftEnter : {
				keepDefault : false,
				replaceWith : '<br />\n'
			},
			onCtrlEnter : {
				keepDefault : false,
				openWith : '\n<p>',
				closeWith : '</p>\n'
			},
			onTab : {
				keepDefault : false,
				openWith : '	 '
			},
			markupSet : [
					{
						name : 'Heading 1',
						key : '1',
						openWith : '<h1(!( class="[![Class]!]")!)>',
						closeWith : '</h1>',
						placeHolder : 'Your h1 tag in here...'
					},
					{
						name : 'Heading 2',
						key : '2',
						openWith : '<h2(!( class="[![Class]!]")!)>',
						closeWith : '</h2>',
						placeHolder : 'Your h2 tag in here...'
					},
					{
						name : 'Heading 3',
						key : '3',
						openWith : '<h3(!( class="[![Class]!]")!)>',
						closeWith : '</h3>',
						placeHolder : 'Your h3 tag in here...'
					},
					{
						name : 'Heading 4',
						key : '4',
						openWith : '<h4(!( class="[![Class]!]")!)>',
						closeWith : '</h4>',
						placeHolder : 'Your h4 tag in here...'
					},
					{
						name : 'Heading 5',
						key : '5',
						openWith : '<h5(!( class="[![Class]!]")!)>',
						closeWith : '</h5>',
						placeHolder : 'Your h5 tag in here...'
					},
					{
						name : 'Heading 6',
						key : '6',
						openWith : '<h6(!( class="[![Class]!]")!)>',
						closeWith : '</h6>',
						placeHolder : 'Your h6 tag in here...'
					},
					{
						name : 'Paragraph',
						openWith : '<p(!( class="[![Class]!]")!)>',
						closeWith : '</p>'
					},
					{
						separator : '---------------'
					},
					{
						name : 'Bold',
						key : 'B',
						openWith : '(!(<strong>|!|<b>)!)',
						closeWith : '(!(</strong>|!|</b>)!)'
					},
					{
						name : 'Italic',
						key : 'I',
						openWith : '(!(<em>|!|<i>)!)',
						closeWith : '(!(</em>|!|</i>)!)'
					},
					{
						name : 'Stroke through',
						key : 'S',
						openWith : '<del>',
						closeWith : '</del>'
					},
					{
						separator : '---------------'
					},
					{
						name : 'Ul',
						openWith : '<ul>\n',
						closeWith : '</ul>\n'
					},
					{
						name : 'Li',
						openWith : '<li>',
						closeWith : '</li>'
					},
					{
						separator : '---------------'
					},
					{
						name : 'Picture',
						key : 'P',
						replaceWith : '<img src="[![Source:!:http://]!]" alt="[![Alternative text]!]" />'
					},
					{
						name : 'Link',
						key : 'L',
						openWith : '<a href="[![Link:!:http://]!]"(!( title="[![Title]!]")!)>',
						closeWith : '</a>',
						placeHolder : 'Your text to link...'
					}, {
						separator : '---------------'
					}, {
						name : 'Clean',
						className : 'clean',
						replaceWith : function(markitup) {
							return markitup.selection.replace(/<(.*?)>/g, "")
						}
					}, {
						name : 'Preview',
						className : 'preview',
						call : 'preview'
					} ]
		};
		$(document).ready(function() {
			$('#content').markItUp(mySettings);
			$('.add').click(function() {
				$.markItUp({
					openWith : '<opening tag>',
					closeWith : '<\/closing tag>',
					placeHolder : "New content"
				});
				return false;
			});
			$('.toggle').click(function() {
				if ($("#content.markItUpEditor").length === 1) {
					$("#content").markItUpRemove();
					$("span", this).text("get markItUp! back");
				} else {
					$('#content').markItUp(mySettings);
					$("span", this).text("remove markItUp!");
				}
				return false;
			});
		});
	</script>

	<form:form method="post" modelAttribute="template">
		<input type="hidden" name="_method" value="${_method }" />
		<!--content start-->
		<div class="content">
			<div class="table">
				<div class="contentNav">
					<h1>模板管理</h1>
				</div>
				<div class="tips">
					<img src="${ctx}/resources/img/tips.gif" align="left" />&nbsp;所有带有<span
						class="red" style="color: red;">*</span>为必填项
				</div>
				<div class="info border">

					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tbody>
							<tr>
								<td width="150" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>模板名称：</td>
								<td colspan="2"><form:input path="name"
										cssClass="input5 fontMar" /></td>
							</tr>
							<tr>
								<td width="150" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>模板编码：</td>
								<td colspan="2"><form:select path="encode">
										<form:option value="UTF-8" label="UTF-8" />
									</form:select></td>
							</tr>
							<tr>
								<td width="150" align="right" nowrap="nowrap"><span
									class="red" style="color: red;">*</span>内容：</td>
								<td colspan="2"><form:textarea path="content" rows="60"
										cols="150" /></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="contactBbutton">
					<input id="ok" type="submit" value="提交" class="button1" />&nbsp; <input
						id=“back” type="button" value="返回" class="button1"
						onclick="javascript:history.go(-1);" />
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>
