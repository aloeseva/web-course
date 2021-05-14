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
                      action="/courses/${course.id}/lesson/${lesson.id}/test/${test.id}/question/create"
                      enctype="multipart/form-data"
                >


                    <label class="question__label">Вопрос</label>
                    <input
                            type="text"
                            class="question__input"
                            name="question_text"
                            id="question_text"
                            placeholder="Text"
                            required
                    >


                    <label class="question__label">Балл</label>
                    <input
                            type="text"
                            class="question__input"
                            name="max_val"
                            id="max_val"
                            placeholder="10"
                            required
                    >


                    <label class="question__label">Тип вопроса</label>
                    <select class="question__input" id="question_type" name="question_type"
                            onchange="changeTypeOfAnswer()">
                        <option value="option">С выбором</option>
                        <option value="text" selected>Текстовое поле</option>
                    </select>


                    <div id="textAnswer" class="textAnswer">
                        <label class="question__label">Ответ</label>
                        <input
                                type="text"
                                class="question__input"
                                name="answer_text"
                                id="answer"
                                placeholder="Ответ"
                        >
                    </div>

                    <div class="optionButtons">
                        <button id="optionButton1" type="button" class="question__btn optionButtons__item" onclick="addOption()"
                                style="display: none">+
                        </button>
                        <button id="optionButton2" type="button" class="question__btn optionButtons__item" onclick="remove()"
                                style="display: none">-
                        </button>
                    </div>

                    <input type="text" id="count" name="count" value="1" hidden>
                    <div id="optionAnswer" style="display: none">
                        <div id="option1" class="option">
                            <input class="question__cb" type="checkbox" id="cb1" name="cb1" value="1">
                            <input class="question__input" type="text" id="text1" name="text1" placeholder="Ответ">
                        </div>
                    </div>
                    <input type="hidden" value="${_csrf.token}" name="_csrf" />

                    <button type="submit" class="question__btn">Создать</button>
                    <a class="question__btn" href="/courses/${course.id}/lesson/${lesson.id}/test/${test.id}">Назад</a>
                </form>

            </div>
        </div>
    </div>

    <@f.footer />

</div>
</body>
</html>