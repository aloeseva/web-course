<#import "../../../macro/headerMacro.ftl" as h>
<#import "../../../macro/footerMacro.ftl" as f>
<#import "../../../macro/headGeneral.ftl" as g>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create material</title>

    <@g.head />


    <link rel="stylesheet"
          href="/css/course/lesson/material/createMaterial.css">
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
                <#if materialError??>
                    <div class="mater__error">
                        <ul class=flashes>
                            <li>${materialError}</li>
                        </ul>
                    </div>
                </#if>
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