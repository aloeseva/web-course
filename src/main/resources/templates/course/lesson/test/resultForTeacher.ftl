<#import "../../../macro/headerMacro.ftl" as h>
<#import "../../../macro/footerMacro.ftl" as f>
<#import "../../../macro/headGeneral.ftl" as g>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>EWA | Educational web app</title>

    <@g.head />

    <link rel="stylesheet" href="/css/course/lesson/test/allResultForTeacher.css">

</head>
<body>
<div class="wrapper">

    <@h.header />

    <div class="content">
        <div class="container">

            <div class="test__name">
                ${ test.name }
            </div>

            <div class="all_results">
                <#list userResult as result>
                    <div class="user__result">
                        <div class="user__result__name">
                            <#if result.user.firstName?? && result.user.lastName??>
                                <b>Имя: </b> ${ result.user.firstName}
                                <b>Фамилия: </b>${ result.user.lastName}
                            <#else >
                                <b>Логин: </b>${ result.user.username}
                            </#if>
                        </div>
                        <div class="user__result__right">
                            <#if result.attemptCount != 0>
                                <b>Лучший результат: </b>${result.bestUserResult} / ${maxPossibleResult}
                                <b>Всего попыток: </b>${result.attemptCount}
                                <a class="result__link"
                                   href="/courses/${course.id}/lesson/${lesson.id}/test/${test.id}/user/${result.user.id}/result/teacher/${1}">Подробнее</a>
                            <#else>
                                Пользователь еще не прошел тест.
                            </#if>
                        </div>
                    </div>
                </#list>
                <div class="user__result">
                    <div class="user__result__name">
                        <b>Колличество пользователей: </b>${ countOfUser } <b>Средняя оценка: </b>${ avgOfResult }
                    </div>
                </div>
            </div>

        </div>
    </div>

    <@f.footer />

</div>
</body>
</html>