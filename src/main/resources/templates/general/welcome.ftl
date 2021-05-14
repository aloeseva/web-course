<#import "../macro/headerMacro.ftl" as h>
<#import "../macro/footerMacro.ftl" as f>
<#import "../macro/headGeneral.ftl" as g>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>EWA | Education Web App</title>

    <meta property="og:title" content="EWA"/>
    <meta property="og:description" content="Education Web App"/>
    <meta property="og:image"
          content="/image/ewa.jpg"/>
    <meta property="og:type" content="website"/>

    <@g.head />

    <link rel="stylesheet" href="/css/welcome.css">
</head>
<body>

<div class="wrapper">

    <@h.header />

    <div class="content">
        <div class="container">
            <div class="welcome">
                <div class="welcome__text">
                    Добро пожаловать в образовательное веб приложение или просто EWA!
                </div>

                <a href="/courses/${2}/lesson/${3}/test/${4}" class="welcome__btn">
                    Демонстрационный тест
                </a>

                <a href="/courses" class="welcome__btn">Все курсы</a>

            </div>
        </div>
    </div>

    <@f.footer />

</div>
</body>
</html>