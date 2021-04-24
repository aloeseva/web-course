<#import "../../../macro/headerMacro.ftl" as h>
<#import "../../../macro/footerMacro.ftl" as f>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create material</title>

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/general.css') }}">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/header.css') }}">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/footer.css') }}">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@300;400;500;700&display=swap"
          rel="stylesheet">
    <script src="{{ url_for('static', filename='js/header.js') }}"></script>
    <script src="https://kit.fontawesome.com/8cf596884a.js" crossorigin="anonymous"></script>

    <link rel="stylesheet"
          href="{{ url_for('static', filename='css/course/lesson/material/createMaterial.css') }}">
</head>
<body>

<div class="wrapper">

    <@h.header />

    <div class="content">
        <div class="container">
            <form class="material" method="POST"
                  action="/courses/${course.id}/lesson/${lesson.id}/create/material"
                  enctype="multipart/form-data">

                <label class="material__label">Название файла:</label>
                <input
                        type="text"
                        class="material__input"
                        name="materialName"
                        id="material_name"
                        placeholder="Material name"
                >


                <label class="material__label">Выберите файл:</label>
                <input
                        class="material__input"
                        type="file"
                        name="file"
                        id="file"
                >
                <input type="hidden" value="${_csrf.token}" name="_csrf" />

                <button type="submit" class="material__btn">Добавить</button>
                <a class="back"
                   href="/courses/${course.id}/lesson/${lesson.id}">Назад</a>
            </form>
        </div>
    </div>

    <@f.footer />

</div>


</body>
</html>