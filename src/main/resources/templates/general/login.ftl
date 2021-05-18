<#import "../macro/headerMacro.ftl" as h>
<#import "../macro/footerMacro.ftl" as f>
<#import "../macro/headGeneral.ftl" as g>
<#import "../macro/loginMacro.ftl" as l>
<#include "../macro/security.ftl">

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>

    <@g.head />

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous"/>
    <script src="/js/login.js"></script>
    <link rel="stylesheet" href="/css/login.css">

</head>
<body>


<div class="wrapper">

    <@h.header />

    <div class="content">
        <div class="container">
            <div class="authorizationForm">
                <div class="authorizationForm__row">
                    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
                        <div class="alert alert-danger" role="alert">
                            Неверные логин или пароль!
                        </div>
                    </#if>
                    <@l.login "/login" false/>
                </div>
            </div>
        </div>
    </div>

    <@f.footer />

</div>

</body>
</html>