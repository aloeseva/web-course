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

            <form class="user" method="POST" action="/user">

                <#if isTeacher && isAdmin>
                    <div>
                        <a href="/createdCourses" class="createdCourses"> Созданные
                            курсы</a>
                    </div>
                </#if>

                <label class="user__label">Login</label>
                <input
                        type="text"
                        class="user__input"
                        name="login_change"
                        id="login_change"
                        placeholder="Login"
                        value=${name}
                >

                <label class="user__label">E-mail</label>
                <input
                        type="email"
                        class="user__input"
                        name="email_change"
                        id="email_change"
                        placeholder="E-mail"
                        value=${user.email}
                >

                <label class="user__label">Registration date</label>
                <div class="user__date" type="text" name="reg_date" id="reg_date">
                    ${ user.registrationDate }
                </div>


                <label class="user__label">Имя</label>
                <input
                        type="text"
                        class="user__input"
                        name="first_name_change"
                        id="first_name_change"
                        placeholder="Иван"
                        value=${ user.firstName!}
                >


                <label class="user__label">Фамилия</label>
                <input
                        type="text"
                        class="user__input"
                        name="last_name_change"
                        id="last_name_change"
                        placeholder="Иванов"
                        value=${ user.lastName!}
                >

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
