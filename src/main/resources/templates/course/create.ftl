<#import "../macro/headerMacro.ftl" as h>
<#import "../macro/footerMacro.ftl" as f>
<#import "../macro/headGeneral.ftl" as g>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Course main page</title>

    <@g.head />

    <link rel="stylesheet" href="/css/course/createCourse.css">
</head>
<body>


<div class="wrapper">

    <@h.header />

    <div class="content">
        <div class="container">
            <form class="mt-3" method="POST" action="/courses/create" enctype="multipart/form-data">
                <label class="course__label">Название курса</label>
                <input
                        type="text"
                        class="course__input"
                        name="course_name"
                        id="course_name"
                        placeholder="Введение в программирование"
                >


                <label class="course__label">Описание</label>
                <input
                        type="text"
                        class="course__input"
                        name="description"
                        id="description"
                        placeholder="Вводный курс в программирование"
                >

                <label class="course__label">Выберите файл:</label>
                <input
                        class="course__input"
                        type="file"
                        name="file"
                        id="file"
                >
                <input type="hidden" value="${_csrf.token}" name="_csrf" />

                <button type="submit" class="course__btn">Создать</button>
            </form>

            <a href="/courses/all" class="back">Назад</a>
        </div>
    </div>

    <@f.footer />

</div>


</body>
</html>