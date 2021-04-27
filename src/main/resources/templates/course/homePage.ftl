<#import "../macro/headerMacro.ftl" as h>
<#import "../macro/footerMacro.ftl" as f>
<#import "../macro/headGeneral.ftl" as g>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${ course.name }</title>

    <@g.head />

    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
          crossorigin="anonymous">

    <link rel="stylesheet" href="/css/course/courseHomePage.css">
</head>
<body>

<div class="wrapper">

    <@h.header />

    <div class="content">
        <div class="container">
            <div class="course">
                <#--                <form class="course__search__row" action="courses/${course.id}/home"-->
                <#--                      method="GET">-->
                <#--                    <input type="text" class="course__input" name="name" placeholder="Название курса">-->
                <#--                    <input type="text" class="course__input" name="description" placeholder="Сложность">-->
                <#--                    <button type="submit" class="course__btn">Найти</button>-->
                <#--                </form>-->

                <div class="course__title">
                    Курс: ${course.name }
                </div>

                <#if is_creator>
                    <a class="create_lesson_btn"
                       href="{{ url_for( 'create_lesson',course_id=course.id) }}">
                        Создать урок
                    </a>
                </#if>


                <div class="course__title">
                    Уроки:
                </div>

                <#list lessons as lesson>
                    <div class="course__lesson">
                        <div class="lesson__title">
                            Название: ${lesson.name}
                        </div>
                        <div class="lesson__difficulty">
                            Сложность: ${lesson.difficulty}
                        </div>


                        <a class="lesson__btn"
                           href="/courses/${course.id}/lesson/${lesson.id}">Пройти</a>
                    </div>
                </#list>

            </div>
        </div>
    </div>

    <@f.footer />

</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
        integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
        integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
        crossorigin="anonymous"></script>
</body>
</html>