<#assign totalPages="${(page.totalPages gt 2000)?string('2000',page.totalPages)}" >
<div class="wz_page">
<#if totalPages?number gt 1>
	<#if (page.pageNo?number) gt 1>
		<a href="${pageUrl}?pageNo=${((page.pageNo?number-1) gt 0)?string(page.pageNo?number-1,page.pageNo?number)}&">上一页</a>
	</#if>
	<#if page.pageNo?number == 1>
		<a class="cur_a" href="${pageUrl}?pageNo=1&">1</a>&nbsp;
	<#else>
		<a href="${pageUrl}?pageNo=1&">1</a>&nbsp;
	</#if>
	<#if totalPages?number gt 0>
		<#if page.pageNo?number lt 10>
			<#if page.pageNo?number gte 2>
			<#list 2..page.pageNo?number as i>
				<#if page.pageNo?number==i>
					<a class="cur_a" href="${pageUrl}?pageNo=${i}&">${i}</a>&nbsp;
				<#else>
					<a href="${pageUrl}?pageNo=${i}&">${i}</a>&nbsp;  
				</#if>
			</#list>
			</#if>
		<#else>
			  ...&nbsp;  
			  <#list page.pageNo?number-4..page.pageNo?number as i>
				<#if page.pageNo?number==i>
					<a class="cur_a" href="${pageUrl}?pageNo=${i}&">${i}</a>&nbsp;
				<#else>
					<a href="${pageUrl}?pageNo=${i}&">${i}</a>&nbsp;  
				</#if>
			  </#list>
		</#if>
		<#if page.pageNo?number gte totalPages?number-4 || totalPages?number-4 lte 0>
			<#if page.pageNo?number+1 lte totalPages?number>
			<#list page.pageNo?number+1..totalPages?number as i>
				<a href="${pageUrl}?pageNo=${i}&">${i}</a>&nbsp;  
				<#if page.pageNo?number == i>
					<a class="cur_a" href="${pageUrl}?pageNo=${i}&">${i}</a>&nbsp;
				</#if>
			</#list>
			</#if>
		<#else>
			<#list page.nextPage..page.pageNo?number+4 as i>
				<#if page.pageNo?number == i>
					<a class="cur_a"  href="${pageUrl}?pageNo=${i}&">${i}</a>&nbsp;
				<#else>
					<a href="${pageUrl}?pageNo=${i}&">${i}</a>&nbsp;  
				</#if>
			</#list>
			  ...&nbsp;  
			  <a href="${pageUrl}?pageNo=${totalPages}&">${totalPages}</a>&nbsp;  
		</#if>
		<#if page.pageNo?number lt totalPages?number>
			<a href="${pageUrl}?pageNo=${((page.nextPage) lt totalPages?number)?string(page.nextPage,totalPages?number)}&">下一页</a>
		</#if>
	</#if>
	
</#if>
</div>