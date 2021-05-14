<#import "../macro/headerMacro.ftl" as h>
<#import "../macro/footerMacro.ftl" as f>
<#import "../macro/headGeneral.ftl" as g>

<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>EWA | Educational web app</title>

    <@g.head />

    <link rel="stylesheet" href="/css/course/allCourses.css">
</head>
<body>
<div class="wrapper">

    <@h.header />

    <div class="content">
        <div class="container">

            <div class="courses">

                <a class="create_course_btn" href="/courses/create" style="margin-top: 20px;">Создать курс</a>


                <div class="courses__row">

                    <#list user.createdCourses as course>
                        <div class="course">

                            <div class="course__img">

                                <#if course.imageName??>
                                    <img src="/image/${course.imageName}"
                                         alt="course">
                                <#else>
                                    <img src="/image/course.png" alt="course">
                                </#if>

                            </div>
                            <div class="course__last">
                                <div class="course__title">
                                    ${course.name}
                                </div>
                                <a class="course__link"
                                   href="/courses/${course.id}">Подробнее</a>
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