<%-- 
    Document   : index
    Created on : Apr 15, 2017, 12:04:37 PM
    Author     : plintus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/HeadersRouter" />
    </head>
    <body onload="socketinit(getWsUri() + '/' + wsName, onOpen, onMessage, onError, onClose);" id="body">
        <%@include file="/jspf/vars.jspf" %>
        <%@include file="/jspf/sockets.jspf" %>
        <div class="main">
            <div class="header">
                <jsp:include page="/CheckBrowser" />
            </div>
            <div class="topmenu">
                <%@include file="/jspf/topmenu.jspf" %>
            </div>
            <div class="workspace">
                <jsp:include page="/Router" />
            </div>
            <div class="footer">
            </div>
        </div>
    </body>
    <jsp:include page="/SetIp" />
</html>
