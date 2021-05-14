<#import "../../macro/headerMacro.ftl" as h>
<#import "../../macro/footerMacro.ftl" as f>
<#import "../../macro/headGeneral.ftl" as g>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create lesson</title>

    <@g.head />

    <link rel="stylesheet"
          href="/css/course/lesson/createLesson.css">
</head>
<body>

<div class="wrapper">

    <@h.header />

    <div class="content">
        <div class="container">

            <div class="lesson__courseName">${course.name}</div>

            <form class="lesson" method="POST" action="/courses/${course.id}/lesson/create">

                <label class="lesson__label">Название урока</label>
                <input
                        type="text"
                        class="lesson__input"
                        name="lessonName"
                        id="lesson_name"
                        placeholder="Введение"
                >


                <label class="lesson__label">Описание</label>
                <input
                        type="text"
                        class="lesson__input"
                        name="lessonDescription"
                        id="description"
                        placeholder="Первый урок курса"
                >


                <label class="lesson__label">Сложность</label>
                <input
                        type="text"
                        class="lesson__input"
                        name="lessonDifficulty"
                        id="difficulty"
                        placeholder="1"
                >


                <input hidden type="text" name="courseId" value="${course.id}">
                <input type="hidden" value="${_csrf.token}" name="_csrf" />

                <button type="submit" class="lesson__btn">Создать</button>
                <a href="/courses/${course.id}/home" class="back">Назад</a>
            </form>

        </div>
    </div>

    <@f.footer />

</div>
</body>
</html>