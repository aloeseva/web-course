<#import "../macro/headerMacro.ftl" as h>
<#import "../macro/footerMacro.ftl" as f>
<#include "../macro/security.ftl">
<#import "../macro/headGeneral.ftl" as g>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${name}</title>

    <@g.head />

    <link rel="stylesheet" href="/css/userHomePage.css">
</head>
<body>

<div class="wrapper">

    <@h.header />


    <div class="content">
        <div class="container">

            <form class="user" name="user" method="POST" action="/user/edit">

                <#if isAdmin || isTeacher>
                    <a href="/courses/createdCourses" class="createdCourses"> Созданные
                        курсы</a>
                </#if>

                <label class="user__label">Login</label>
                <input
                        type="text"
                        class="user__input"
                        name="loginChange"
                        id="login_change"
                        placeholder="Login"
                        value=${username}
                >
                <#if usernameError??>
                    <div class="user__error">
                        <ul class=flashes>
                            <li>${usernameError}</li>
                        </ul>
                    </div>
                </#if>

                <label class="user__label">E-mail</label>
                <input
                        type="email"
                        class="user__input"
                        name="emailChange"
                        id="email_change"
                        placeholder="E-mail"
                        value=${email}
                >
                <#if emailError??>
                    <div class="user__error">
                        <ul class=flashes>
                            <li>${emailError}</li>
                        </ul>
                    </div>
                </#if>

                <label class="user__label">Registration date</label>
                <div class="user__date" type="text" name="reg_date" id="reg_date">
                    ${ user.registrationDate }
                </div>


                <label class="user__label">Имя</label>
                <input
                        type="text"
                        class="user__input"
                        name="firstNameChange"
                        id="first_name_change"
                        placeholder="Иван"
                        value=
                        <#if firstName??>
                            ${ firstName}
                        <#else >
                            ${ firstName!}
                        </#if>
                >
                <#if firstNameError??>
                    <div class="user__error">
                        <ul class=flashes>
                            <li>${firstNameError}</li>
                        </ul>
                    </div>
                </#if>

                <label class="user__label">Фамилия</label>
                <input
                        type="text"
                        class="user__input"
                        name="lastNameChange"
                        id="last_name_change"
                        placeholder="Иванов"
                        value=
                        <#if lastName??>
                            ${ lastName}
                        <#else >
                            ${ lastName!}
                        </#if>
                >
                <#if lastNameError??>
                    <div class="user__error">
                        <ul class=flashes>
                            <li>${lastNameError}</li>
                        </ul>
                    </div>
                </#if>

                <input type="hidden" value="${user.id}" name="userId">
                <input type="hidden" value="${_csrf.token}" name="_csrf">

                <button type="submit" class="user__btn">Сохранить</button>


            </form>

        </div>
    </div>
    <@f.footer />
</div>
</body>
</html>
