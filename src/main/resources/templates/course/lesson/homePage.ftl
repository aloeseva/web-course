<#import "../../macro/headerMacro.ftl" as h>
<#import "../../macro/footerMacro.ftl" as f>
<#import "../../macro/headGeneral.ftl" as g>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Lesson</title>

    <@g.head />

    <link rel="stylesheet"
          href="/css/course/lesson/lessonHomePage.css">
</head>
<body>

<div class="wrapper">

    <@h.header />

    <div class="content">
        <div class="container">
            <div class="lesson">
                <div class="lesson__name">
                    ${lesson.name}
                </div>
                <div class="lesson__pre">
                    Описание:
                </div>
                <div class="lesson__description">
                    ${lesson.description}
                </div>

                <div class="lesson__pre">
                    Сложность: ${lesson.difficulty}
                </div>

                <a class="back__btn" href="/courses/${course.id}/home">Назад</a>

                <#if isCreator>
                    <a class="create_btn"
                       href="/courses/${course.id}/lesson/${lesson.id}/create/material">
                        Add material
                    </a>

                    <a class="create_btn"
                       href="/courses/${course.id}/lesson/${lesson.id}/test/create">
                        Add test
                    </a>
                </#if>

                <div class="material__title">
                    Материалы
                </div>

                <#list materials as material>
                    <div class="material">
                        <div class="material__name">
                            ${material.name}
                        </div>
                        <div class="">
                            <a class="material__btn"
                               href="/material/${material.id}">
                                Скачать
                            </a>
                        </div>
                    </div>
                </#list>

                <div class="test__title">
                    Тесты
                </div>

                <#list tests as test>
                    <div class="test">
                        <div class="test__name">
                            ${test.test.name}
                        </div>

                        <#--                    <div class="test__type">#}-->
                        <#--                        {# Тип: {{ test.t_type }}#}-->
                        <#--                        {#-->
                        <#--                    </div>-->

                        <div class="test__exp">
                            Последний срок сдачи: ${test.test.expDate}
                        </div>

                        <a class="test__btn"
                           href="/courses/${course.id}/lesson/${lesson.id}/test/${test.test.id}">
                            Решить
                        </a>
                        <#if isCreator>
                            <a class="test__btn"
                               href="/courses/${course.id}/lesson/${lesson.id}/test/${test.test.id}/result/all">
                                Результаты
                            </a>
                        <#else>
                            <#if test.solved>
                                <a class="test__btn"
                                   href="/courses/${course.id}/lesson/${lesson.id}/test/${test.test.id}/result/${1}">
                                    Мои результаты
                                </a>
                            </#if>
                        </#if>
                    </div>
                </#list>
            </div>
        </div>
    </div>

    <@f.footer />

</div>


</body>
</html>