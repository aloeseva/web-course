<#include "../macro/security.ftl">
<#import "../macro/login.ftl" as l>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>HEADER MACRO</title>


    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="/static/css/general.css">
    <link rel="stylesheet" href="/static/css/header.css">
    <link rel="stylesheet" href="/static/css/footer.css">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@300;400;500;700&display=swap"
          rel="stylesheet">
    <script src="/static/js/header.js"></script>
    <script src="https://kit.fontawesome.com/8cf596884a.js" crossorigin="anonymous"></script>


</head>
<body>
<div class="wrapper">
    <#macro header>
        <header class="header">
            <div class="container">
                <div class="header__row">
                    <a href="/welcome" class="header__logo__1">
                        EWA | Educational web app
                    </a>
                    <div href="welcome" class="header__logo__2">
                        EWA
                    </div>
                    <div class="right__part__menu_btn" onclick="onMenuClick()">
                    <span class="menu__burger" id="menu__burger">
                        <i class="fas fa-bars"></i>
                    </span>
                        <span class="menu__x" id="menu__x">
                        <i class="fas fa-times"></i>
                    </span>
                    </div>
                    <div class="right__part" id="right__part">
                        <ul class="header__links">
                            <li class="header__link"><a href="/courses">Все курсы</a>
                            </li>
                            <#if known>
                                <li class="header__link"><a href="/courses/my">Мои
                                        курсы</a>
                                </li>
                            </#if>
                        </ul>
                        <div class="header__user">
                            <#if known >
                                <div class="header__user__profile" onclick="onProfileClick()">
                                    <div class="user__name">
                                        ${name}
                                    </div>

                                    <div class="header__closed__menu" id="header__closed__menu">
                                        <i class="fas fa-sort-down"></i>
                                    </div>

                                    <div class="header__opened__menu" id="header__opened__menu">
                                        <i class="fas fa-sort-up"></i>
                                    </div>


                                    <div class="user__dropdown" id="user__dropdown">
                                        <span><a id="Profile" href="/user">Профиль</a></span>
                                        <@l.logout />
                                    </div>
                                </div>
                            <#else>
                                <a class="header__login" href="login_page">Войти</a>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>
        </header>
    </#macro>
</div>


</body>
</html>