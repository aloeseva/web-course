<#import "../macro/headerMacro.ftl" as h>
<#import "../macro/footerMacro.ftl" as f>
<#import "../macro/headGeneral.ftl" as g>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Мои курсы</title>

    <@g.head />

    <link rel="stylesheet" href="/css/course/allCourses.css">
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

                <div class="courses__row">

                    <#list courses as course>
                        <div class="course">

                            <div class="course__img">

                                <#if course.imageName??>
                                    <img src="/image/${course.imageName}"
                                         alt="course">
                                <#else >
                                    <img src="/image/course.png"
                                         alt="course">
                                </#if>

                            </div>
                            <div class="course__last">
                                <div class="course__title">
                                    ${course.name}
                                </div>
                                <div class="course__link">
                                    <a href="/courses/${course.id}">Подробнее</a>
                                </div>
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