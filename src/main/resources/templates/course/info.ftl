<#import "../macro/headerMacro.ftl" as h>
<#import "../macro/footerMacro.ftl" as f>
<#import "../macro/headGeneral.ftl" as g>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Information about ${ course.name }</title>

    <@g.head />

    <link rel="stylesheet" href="/css/course/courseInfo.css">
</head>
<body>


<div class="wrapper">

    <@h.header />

    <div class="content">
        <div class="container">
            <div class="course">
                <div class="course__img">
                    <#if course.imageName??>
                        <img src="/image/${course.imageName}"
                             alt="course">
                    <#else >
                        <img src="/image/course.png"
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