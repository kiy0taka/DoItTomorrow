<html>
    <head>
        <title>明日やる</title>
        <link rel="shortcut icon" href="/images/gaelyk-small-favicon.png" type="image/png">
        <link rel="icon" href="/images/gaelyk-small-favicon.png" type="image/png">
        <link rel="stylesheet" type="text/css" href="/css/main.css"/>
    </head>
    <body>
        <center>
        <div style="text-align:left;position:relative; width:600px">
        <a href="/" style="color: black">
        <h1 style="text-align:center;">
        <%
          try {
            def messages = memcacheService.get('favorites')?.values() as List
            if (messages) {
              out << messages[new Random().nextInt(messages.size())].text
            } else out << '明日やる！'
          } catch (e) {
            out << '明日やる！'
          }
        %>
        </h1>
        </a>
