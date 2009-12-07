<% include '/WEB-INF/includes/header.gtpl' %>
<%
def data = memcacheService.get(params.date==null?'hourStatics':'dateStatics')
%>
<div id="tab">
  <% include '/WEB-INF/includes/tabNavi.gtpl' %>
  <div class="tabPanel">
  <div style="font-size:12px; text-align:right; padding:5px;">
  <% if (params.date==null) { %>
    <span>時間別</span>
    <a href="graph.gtpl?date">曜日別</a>
  <% } else { %>
    <a href="graph.gtpl?hours">時間別</a>
    <span>曜日別</span>
  <% } %>
  </div>
  <img src="http://chart.apis.google.com/chart?chs=555x300&amp;cht=lc&amp;chd=t:${data*.value.join(",")}&amp;chxt=x,y&amp;chxl=0:|${data*.title.join("|")}" alt="グラフ">
  <div style="text-align:right; font-size:12px; padding:5px">（最大値を100としたグラフ）</div>
  </div>
</div>
<% include '/WEB-INF/includes/footer.gtpl' %>