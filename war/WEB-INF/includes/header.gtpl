<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-type" content="text/html;charset=UTF-8">
<title>明日やる</title>
<link rel="shortcut icon" href="/images/gaelyk-small-favicon.png" type="image/png">
<link rel="icon" href="/images/gaelyk-small-favicon.png" type="image/png">
<link rel="stylesheet" type="text/css" href="/css/main.css">
<link rel="start" href="/" title="明日やる">
</head>
<body>
<center>
<div style="text-align:left;position:relative; width:600px">
<h1 style="text-align:center;"><a href="/" style="color: black"><%
      try {
        def messages = memcacheService.get('favorites')?.values() as List
        if (messages) {
          out << messages[new Random().nextInt(messages.size())].text
        } else out << '明日やる！'
      } catch (e) {
        out << '明日やる！'
      }
    %></a></h1>
