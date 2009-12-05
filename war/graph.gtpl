<% include '/WEB-INF/includes/header.gtpl' %>
<%
def data = memcacheService.get(params.date==null?'hourStatics':'dateStatics')
%>
<div id="tab">
  <div style="list-style-type:none; clear:both; margin-left: 35px">
    <div class="tabNavi" style="background-color: silver">
      <a href="list.groovy">すべて</a>
    </div>
    <div class="tabNavi" style="background-color: silver">
      <a href="list.groovy?status=today">今日やる</a>
    </div>
    <div class="tabNavi" style="background-color: silver">
      <a href="list.groovy?status=tomorrow">明日やる</a>
    </div>
    <div class="tabNavi">
      <a href="graph.gtpl">グラフ</a>
    </div>
  </div>
  <div class="tabPanel">
  <div style="font-size:12px; text-align:right; padding:5px;">
    <a href="graph.gtpl?hours">時間別</a>
    <a href="graph.gtpl?date">曜日別</a>
  </div>
  <img src="http://chart.apis.google.com/chart?chs=555x300&amp;cht=lc&amp;chd=t:${data*.value.join(",")}&amp;chxt=x,y&amp;chxl=0:|${data*.title.join("|")}" alt="グラフ">
  <div style="text-align:right; font-size:12px; padding:5px">（最大値を100としたグラフ）</div>
  </div>
</div>
<% include '/WEB-INF/includes/footer.gtpl' %>