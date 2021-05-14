<#import "../../../macro/headerMacro.ftl" as h>
<#import "../../../macro/footerMacro.ftl" as f>
<#import "../../../macro/headGeneral.ftl" as g>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create test</title>

    <@g.head />

    <link rel="stylesheet" href="/css/course/lesson/test/createTest.css">
</head>
<body>

<div class="wrapper">

    <@h.header />

    <div class="content">
        <div class="container">
            <form class="test" method="POST"
                  action="/courses/${course.id}/lesson/${lesson.id}/test/create"
                  enctype="multipart/form-data">

                <label class="test__label">Название теста</label>
                <input
                        type="text"
                        class="test__input"
                        name="testName"
                        id="test_name"
                        placeholder="Название теста"
                >

                <label class="test__label">Последний срок сдачи</label>
                <input
                        type="text"
                        class="test__input"
                        name="expDate"
                        id="exp_date"
                        placeholder="19.10.2020"
                >
                <input type="hidden" value="${_csrf.token}" name="_csrf" />

                <button type="submit" class="test__btn">Создать</button>

                <a class="back" href="/courses/${course.id}/lesson/${lesson.id}">Назад</a>


            </form>
        </div>
    </div>

    <@f.footer />

</div>
</body>
</html>