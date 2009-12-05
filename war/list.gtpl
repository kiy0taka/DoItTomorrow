<% include '/WEB-INF/includes/header.gtpl' %>
<%
def toParams = { override = [:] -> (params + override).findAll { it.value }.collect {"${it.key}=${it.value}"}.join('&') }
%>
<div id="tab">
  <div style="list-style-type:none; clear:both; margin-left: 35px">
    <div class="tabNavi" ${params.status ? "style='background-color: silver'" : ""}>
      <a href="list.groovy?${toParams([status:'', page:''])}">すべて</a>
    </div>
    <div class="tabNavi" ${params.status == 'today' ? "" : "style='background-color: silver'"}>
      <a href="list.groovy?${toParams([status:'today', page:''])}">今日やる</a>
    </div>
    <div class="tabNavi" ${params.status == 'tomorrow' ? "" : "style='background-color: silver'"}>
      <a href="list.groovy?${toParams([status:'tomorrow', page:''])}">明日やる</a>
    </div>
    <div class="tabNavi" style="background-color: silver">
      <a href="graph.gtpl">グラフ</a>
    </div>
  </div>
  <div class="tabPanel autopagerize_page_element">
  <%
  def fmt = new java.text.SimpleDateFormat('yyyy/MM/dd HH:mm')
  fmt.timeZone = TimeZone.getTimeZone('JST')
  request.tweets.each { out << """
    <div style='border-top: 1px solid silver; margin-top:-5px;'>
    <div style='font-size:14px; padding:5px 5px 10px 5px'>
      <span><a href='list.groovy?user=${it.fromUser}'>@${it.fromUser}</a></span>
      <span>${it.text
              .replaceAll('(https?://[^\\s]+)', '<a href="$1" target="_blank" style="font-weight: normal">$1</a>')
              .replaceAll('(^@|\\s+@)([0-9a-zA-Z_]+)', '<a href="http://twitter.com/$2" target="_blank" style="font-weight: normal">@$2</a>')
      }</span>
      <div style="width:400px">
      <a style="font-size:12px; color:silver; font-weight:bold" target="_blank" href="http://twitter.com/${it.fromUser}/status/${it.key.id}">${fmt.format(it.createdAt)}</a>
      </div>
    </div>
    </div>"""
  } %>
  </div>
</div>
<div class="autopagerize_insert_before" style="padding-top:5px">
<% if ((params.page?:0).toInteger() > 1) { %>
<a href='list.groovy?${toParams([page:(params.page.toInteger()-1)])}'>« Prev</a>
<% } else { %>
<span style="color:silver">« Prev</span>
<% } %>
<% if (request.hasNext) { %>
<a href='list.groovy?${toParams([page:(params.page?:1).toInteger()+1])}' rel='next'>Next »</a>
<% } else { %>
<span style="color:silver">Next »</span>
<% } %>
</div>
<% include '/WEB-INF/includes/footer.gtpl' %>
