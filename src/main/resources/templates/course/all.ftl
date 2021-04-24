<#import "../macro/headerMacro.ftl" as h>
<#import "../macro/footerMacro.ftl" as f>
<#include "../macro/security.ftl">
<#import "../macro/headGeneral.ftl" as g>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All our courses</title>

    <@g.head />

    <link rel="stylesheet" href="static/css/course/allCourses.css">
</head>
<body>

<div class="wrapper">

    <@h.header />

    <div class="content">
        <div class="container">
            <div class="courses">

                <form class="course__search__row" action="/courses" method="GET">
                    <input class="course__input" type="text" name="filter">
                    <input type="hidden" value="${_csrf.token}" name="_csrf" />
                    <input class="course__btn" type="submit" value="Поиск">
                </form>

                <#if isTeacher>
                    <a class="create_course_btn" href="/courses/create">Создать курс</a>
                </#if>

                <div class="courses__row">

                    <#list courses as course>
                        <div class="course">

                            <div class="course__img">

                                <#if course.image_name!>
                                    <img src="/static/image/${course.imageName}"
                                         alt="course">
                                <#else>
                                    <img src="/static/image/course.png" alt="course">
                                </#if>

                            </div>
                            <div class="course__last">
                                <div class="course__title">
                                    ${course.name}
                                </div>
                                <a class="course__link" href="/courses/${course.id}">Подробнее</a>
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
        </div>
    </div>

    <@f.footer />

</div>
</body>
</html>