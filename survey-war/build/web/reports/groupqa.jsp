<%-- 
    Document   : groupqa
    Created on : Apr 6, 2020, 12:40:49 PM
    Author     : plintus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Система опроса</title>
        <meta name="keywords" content="Система опроса" />
        <meta name="description" content="Система опроса" />
        <script type="text/javascript" src="../js/libs/prototype/prototype.js"></script>
        <link rel="icon" href="../images/favicon.ico" type="image/x-icon">
        <link href='../css/site.css' rel='stylesheet' type='text/css'>
        <link href="https://fonts.googleapis.com/css?family=PT+Sans:400,400i,700,700i&subset=cyrillic" rel="stylesheet"> 
        <meta name="viewport" content="width=1200, initial-scale=2.0">
    </head>
    <body>
        <table class='defaulttable' style="width:100%;">
            <tbody id="report">
                <jsp:include page="/GroupQA" />
            </tbody>
        </table>
    </body>
</html>
