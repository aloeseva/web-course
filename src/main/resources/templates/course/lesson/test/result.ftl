<#import "../../../macro/headerMacro.ftl" as h>
<#import "../../../macro/footerMacro.ftl" as f>
<#import "../../../macro/headGeneral.ftl" as g>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Result</title>

    <@g.head />

    <link rel="stylesheet" href="/css/course/lesson/test/result.css">
    <script src="/js/result.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>

</head>
<body>

<div class="wrapper">

    <@h.header />

    <div class="content">
        <div class="container">

            <div class="result">
                <div class="result__attempts">
                    <#list attempt_count as attempt>
                        <#if current_attempt != attempt + 1>
                            <a href="/courses/${course.id}/lesson/${lesson.id}/test/${test.id}/result/${attempt + 1}"
                               class="attempt">${attempt + 1}</a>
                        <#else >
                            <a href="/courses/${course.id}/lesson/${lesson.id}/test/${test.id}/result/${attempt + 1}"
                               class="selected__attempt">${attempt + 1}</a>
                        </#if>
                    </#list>
                </div>

                <div class="result__row">
                    <div class="result__chart">
                        <canvas id="myChart" width="400" height="400"></canvas>
                    </div>
                    <div class="result__progress">
                        Ваш результат: ${ user_result } из ${ max_result }!
                    </div>
                </div>

                <a href="/courses/${course.id}/lesson/${lesson.id}/test/${test.id}"
                   class="test__btn">Попробовать еще!</a>

                <a href="/courses/${course.id}/lesson/${lesson.id}"
                   class="test__btn">Вернуться к уроку</a>
            </div>

            <script>
                drawPie(${max_result}, ${user_result})
            </script>
        </div>
    </div>

    <@f.footer />

</div>
</body>
</html>