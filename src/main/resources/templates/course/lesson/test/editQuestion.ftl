<#import "../../../macro/headerMacro.ftl" as h>
<#import "../../../macro/footerMacro.ftl" as f>
<#import "../../../macro/headGeneral.ftl" as g>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create question</title>

    <@g.head />

    <script src="/js/createQuestion.js"></script>
    <link rel="stylesheet" href="/css/course/lesson/test/createQuestion.css">
</head>
<body>

<div class="wrapper">

    <@h.header />

    <div class="content">
        <div class="container">
            <div class="question">

                <form class="mt-3" method="POST"
                      action="/courses/${course.id}/lesson/${lesson.id}/test/${test.id}/question/${question.id}/edit"
                      enctype="multipart/form-data"
                >


                    <label class="question__label">Вопрос</label>
                    <input
                            type="text"
                            class="question__input"
                            name="questionText"
                            id="question_text"
                            placeholder="Text"
                            value="${ question.text }"
                            required
                    >


                    <label class="question__label">Балл</label>
                    <input
                            type="text"
                            class="question__input"
                            name="maxVal"
                            id="max_val"
                            value="${ question.maxVal }"
                            placeholder="10"
                            required
                    >


                    <label class="question__label">Тип вопроса</label>
                    <select class="question__input" id="questionType" name="questionType"
                            onchange="changeTypeOfAnswer()">
                        <#if question.QType == "option">
                            <option value="option" selected>С выбором</option>
                            <option value="text">Текстовое поле</option>
                        <#elseif question.QType == "text">
                            <option value="option">С выбором</option>
                            <option value="text" selected>Текстовое поле</option>
                        </#if>
                    </select>

                    <#if question.QType == "text">
                        <div id="textAnswer" class="textAnswer">
                            <label class="question__label">Ответ</label>
                            <input
                                    type="text"
                                    class="question__input"
                                    name="answerText"
                                    id="answer"
                                    placeholder="Ответ"
                                    value="${ question.answers[0].text }"
                            >
                        </div>

                    <#elseif question.QType == "option">
                        <input type="text" id="count" name="count" value="${ answers?size}" hidden>
                        <div id="optionAnswer">
                            <#list answers as count>
                                <div id="option${ count?index + 1}" class="option">
                                    <#if (count.val > 0)>
                                        <input class="question__cb" type="checkbox" id="cb${ count?index + 1}"
                                               name="cb${ count?index + 1}" value="${ count?index + 1}" checked>
                                    <#else>
                                        <input class="question__cb" type="checkbox" id="cb${ count?index + 1}"
                                               name="cb${ count?index + 1}" value="${ count?index + 1}">
                                    </#if>
                                    <input class="question__input" type="text" id="text${ count?index + 1}"
                                           name="text${ count?index + 1}"
                                           placeholder="Ответ" value="${ count.text }">
                                </div>
                            </#list>
                        </div>
                    </#if>
                    <input type="hidden" value="${_csrf.token}" name="_csrf" />

                    <button type="submit" class="question__btn">Изменить</button>
                    <a class="question__btn"
                       href="/courses/${course.id}/lesson/${lesson.id}/test/${test.id}">Назад</a>
                </form>

            </div>
        </div>
    </div>

    <@f.footer />

</div>
</body>
</html>