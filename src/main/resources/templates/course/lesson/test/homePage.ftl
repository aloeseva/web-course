<#import "../../../macro/headerMacro.ftl" as h>
<#import "../../../macro/footerMacro.ftl" as f>
<#import "../../../macro/headGeneral.ftl" as g>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Test</title>

    <@g.head />

    <link rel="stylesheet" href="/css/course/lesson/test/testHomePage.css">
    <script src="/js/testHomePage.js"></script>
</head>
<body>

<div class="wrapper">
    <@h.header />

    <div class="content">
        <div class="container">
            <div class="test__name"> ${ test.name }</div>

            <#if is_creator>
                <a class="create_btn"
                   href="/courses/${course.id}/lesson/${lesson.id}/test/${test.id}/question/create">
                    Добавить вопрос
                </a>
            </#if>

            <form action="/courses/${course.id}/lesson/${lesson.id}/test/${test.id}/result"
            method="POST">
            <div class="test">
                <#list questions as question>
                    <div class="question">
                        <div class="firstRow">
                            <div class="question__name">
                                ${ question?index + 1}. ${ question.text }
                            </div>

                            <div class="question__flagOff" id="${ question.id }"
                                 onclick="flagChange(${ question.id })">
                                <i class="fas fa-flag"></i>
                            </div>
                            <div class="question__flagOn" id="${ question.id }On"
                                 onclick="flagChange(${ question.id })">
                                <i class="far fa-flag"></i>
                            </div>
                        </div>

                        <div class="question__val">
                            Максимальный балл: ${ question.maxVal }
                        </div>

                        <#if question.QType == 'option'>
                            <#list question.answers as answer>
                                <div class="answer__option">
                                    <input type="checkbox" name="${ answer.id }" class="answer__cb">
                                    <div class="answer__cb__text">
                                        ${ answer.text }
                                    </div>
                                </div>
                            </#list>
                        <#elseif question.QType == 'text'>
                            <#list question.answers as answer>
                                <input class="answer__input" type="text" name="${ answer.id }">
                            </#list>
                        </#if>

                        <#if is_creator>
                            <a href="/courses/${course.id}/lesson/${lesson.id}/test/${test.id}/question/${question.id}/edit"
                               class="test__btn">Редактировать</a>

                        </#if>

                        <#if is_creator>
                            <a href="/courses/${course.id}lesson/${lesson.id}/test/${test.id}/question/${question.id}/delete"
                               class="test__btn">Удалить</a>
                        </#if>

                    </div>
                </#list>
                <input type="hidden" value="${_csrf.token}" name="_csrf"/>

                <button type="submit" class="test__btn" id="testSubmit">Проверить</button>

            </div>
            </form>
        </div>
    </div>

    <@f.footer />

</div>
</body>
</html>