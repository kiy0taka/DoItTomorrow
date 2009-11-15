<% include '/WEB-INF/includes/header.gtpl' %>
<%
def toParams = { override = [:] -> (params + override).findAll { it.value }.collect {"${it.key}=${it.value}"}.join('&') }
%>
<div id="tab">
  <ul style="list-style-type:none; clear:both">
    <li class="tabNavi" style='background-color: ${params.status ? "silver" : ""}'>
      <a href="list.groovy?${toParams([status:'', page:''])}">すべて</a>
    </li>
    <li class="tabNavi" style='background-color: ${params.status == 'today' ? "" : "silver"}'>
      <a href="list.groovy?${toParams([status:'today', page:''])}">今日やる</a>
    </li>
    <li class="tabNavi" style='background-color: ${params.status == 'tomorrow' ? "" : "silver"}'>
      <a href="list.groovy?${toParams([status:'tomorrow', page:''])}">明日やる</a>
    </li>
  </ul>
  <div class="tabPanel autopagerize_page_element">
    <%
    import java.text.SimpleDateFormat
    def fmt = new SimpleDateFormat('yyyy/MM/dd HH:mm')
    fmt.timeZone = TimeZone.getTimeZone('JST')
    request.tweets.each {
      out << """
        <div style='background-color: white;  padding:5px; border-bottom: 1px solid silver;'>
        <div style='font-size:14px;'>
          <span><a href='list.groovy?user=${it.fromUser}'>@${it.fromUser}</a></span>
          <span>${it.text.replaceAll('(https?://[^\\s]+)', '<a href="$1" target="_blank">$1</a>')}</span>
          <div style="width:400">
          <a style="font-size:12px; color:silver; font-weight:bold" target="_blank" href="http://twitter.com/${it.fromUser}/status/${it.key.id}">${fmt.format(it.createdAt)}</a>
          </div>
        </div>
        </div>"""
    }
    %>
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
