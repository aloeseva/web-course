<#import "../macro/headerMacro.ftl" as h>
<#import "../macro/footerMacro.ftl" as f>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Information about {{ course.name }}</title>

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/general.css') }}">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/header.css') }}">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/footer.css') }}">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@300;400;500;700&display=swap"
          rel="stylesheet">
    <script src="{{ url_for('static', filename='js/header.js') }}"></script>
    <script src="https://kit.fontawesome.com/8cf596884a.js" crossorigin="anonymous"></script>


    <link rel="stylesheet" href="{{ url_for('static', filename='css/course/courseInfo.css') }}">
</head>
<body>


<div class="wrapper">

    <@h.header />

    <div class="content">
        <div class="container">
            <div class="course">
                <div class="course__img">
                    <#if course.imageName!>
                        <img src="/static/image/${course.imageName}"
                             alt="course">
                    <#else >
                        <img src="/static/image/course.png"
                             alt="course">
                    </#if>
                </div>

                <div class="course__name">
                    ${course.name}
                </div>


                <div class="course__description__title">
                    Описание:
                </div>


                <div class="course__description">
                    ${ course.description}
                </div>

                <a href="/courses/${course.id}/enroll">
                    <div class="course__enroll__btn">
                        <#if enrolled>
                            Зайти
                        <#else>
                            Записаться
                        </#if>
                    </div>
                </a>
            </div>
        </div>
    </div>

    <@f.footer />

</div>
</body>
</html>