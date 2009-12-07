<%
def isList = request.requestURI == '/list.gtpl'
def isGraph = request.requestURI == '/graph.gtpl'
%>
  <div style="list-style-type:none; clear:both; margin-left: 35px">
    <div class="tabNavi" ${isList && !params.status ? "" : "style='background-color: silver'"}>
      <a href="${ViewHelper.toURL('list.groovy', params + [status:'', page:''])}">すべて</a>
    </div>
    <div class="tabNavi" ${isList && params.status == 'today' ? "" : "style='background-color: silver'"}>
      <a href="${ViewHelper.toURL('list.groovy', params + [status:'today', page:''])}">今日やる</a>
    </div>
    <div class="tabNavi" ${isList && params.status == 'tomorrow' ? "" : "style='background-color: silver'"}>
      <a href="${ViewHelper.toURL('list.groovy', params + [status:'tomorrow', page:''])}">明日やる</a>
    </div>
    <div class="tabNavi" ${request.requestURI == '/graph.gtpl' ? "" : "style='background-color: silver'"}>
      <a href="graph.gtpl">グラフ</a>
    </div>
  </div>