<#import "../macro/headerMacro.ftl" as h>
<#import "../macro/footerMacro.ftl" as f>
<#include "../macro/security.ftl">

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All our courses</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/general.css') }}">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/header.css') }}">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/footer.css') }}">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@300;400;500;700&display=swap"
          rel="stylesheet">
    <script src="{{ url_for('static', filename='js/header.js') }}"></script>
    <script src="https://kit.fontawesome.com/8cf596884a.js" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="{{ url_for('static', filename='css/course/allCourses.css') }}">
</head>
<body>

<div class="wrapper">

    <@h.header />

    <div class="content">
        <div class="container">
            <div class="courses">

                <form class="course__search__row" action="/courses" method="GET">
                    <input class="course__input" type="text" name="filter">
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