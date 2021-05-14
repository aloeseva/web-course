<#include "../macro/security.ftl">
<#import "loginMacro.ftl" as l>

<#macro header>

    <header class="header">
        <div class="container">
            <div class="header__row">
                <a href="/" class="header__logo__1">
                    EWA | Educational web app
                </a>
                <div href="/" class="header__logo__2">
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
                                    <a id="Profile" href="/user">Профиль</a>
                                    <form action="/logout" method="post">
                                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                        <button type="submit">
                                            Выйти</button>
                                    </form>
                                    <#if isAdmin>
                                        <a class="nav-link" href="/user/all">User list</a>
                                    </#if>
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
