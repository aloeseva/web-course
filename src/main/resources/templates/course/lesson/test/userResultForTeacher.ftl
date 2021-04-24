<#import "../../../macro/headerMacro.ftl" as h>
<#import "../../../macro/footerMacro.ftl" as f>
<#import "../../../macro/headGeneral.ftl" as g>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Result</title>

    <@g.head />

    <link rel="stylesheet" href="static/css/course/lesson/test/result.css">
    <script src="static/js/result.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>

</head>
<body>

<div class="wrapper">

    <@h.header />

    <div class="content">
        <div class="container">

            <div class="result">
                <div class="result__attempts">
                    {% for attempt in range(1, attempt_count + 1) %}
                        {% if current_attempt != attempt %}
                            <a href="{{ url_for("user_result_for_teacher",course_id=course.id, lesson_id=lesson.id, test_id=test.id, attempt=attempt ,user_id=user.id) }}"
                               class="attempt">{{ attempt }}</a>
                        {% else %}
                            <a href="{{ url_for("user_result_for_teacher",course_id=course.id, lesson_id=lesson.id, test_id=test.id, attempt=attempt,user_id=user.id) }}"
                               class="selected__attempt">{{ attempt }}</a>
                        {% endif %}
                    {% endfor %}
                </div>

                <div class="result__row">
                    <div class="result__chart">
                        <canvas id="myChart" width="400" height="400"></canvas>
                    </div>
                    <div class="result__progress">
                        Ваш результат: {{ user_result }} из {{ max_result }}
                        или {{ (user_result/max_result*100)|round(2, 'floor') }}%!
                    </div>
                </div>

                <a href="{{ url_for("result_for_teacher",course_id=course.id, lesson_id=lesson.id, test_id=test.id) }}"
                   class="test__btn">Все результаты</a>

                <a href="{{ url_for("lesson_home_page",course_id=course.id, lesson_id=lesson.id, test_id=test.id) }}"
                   class="test__btn">Вернуться к уроку</a>
            </div>

            <script>
                drawPie({{ max_result }}, {{ user_result }})
            </script>
        </div>
    </div>

    {{ footer(current_user) }}

</div>
</body>
</html>