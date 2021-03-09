<#import "../macro/headerMacro.ftl" as h>
<#import "../macro/footerMacro.ftl" as f>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Course main page</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/general.css') }}">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/header.css') }}">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/footer.css') }}">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@300;400;500;700&display=swap"
          rel="stylesheet">
    <script src="{{ url_for('static', filename='js/header.js') }}"></script>
    <script src="https://kit.fontawesome.com/8cf596884a.js" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="{{ url_for('static', filename='css/course/createCourse.css') }}">
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

                <button type="submit" class="course__btn">Создать</button>
            </form>

            <a href="/courses/all" class="back">Назад</a>
        </div>
    </div>

    <@f.footer />

</div>


</body>
</html>