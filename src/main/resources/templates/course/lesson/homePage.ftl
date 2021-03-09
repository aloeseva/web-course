<#import "../../macro/headerMacro.ftl" as h>
<#import "../../macro/footerMacro.ftl" as f>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Lesson</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/general.css') }}">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/header.css') }}">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/footer.css') }}">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@300;400;500;700&display=swap"
          rel="stylesheet">
    <script src="{{ url_for('static', filename='js/header.js') }}"></script>
    <script src="https://kit.fontawesome.com/8cf596884a.js" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="{{ url_for('static', filename='css/course/lesson/lessonHomePage.css') }}">
</head>
<body>

<div class="wrapper">

    <@h.header />

    <div class="content">
        <div class="container">
            <div class="lesson">
                <div class="lesson__name">
                    {{ lesson.name }}
                </div>
                <div class="lesson__pre">
                    Описание:
                </div>
                <div class="lesson__description">
                    {{ lesson.description }}
                </div>

                <div class="lesson__pre">
                    Сложность: {{ lesson.difficulty }}
                </div>

                <a class="back__btn" href="{{ url_for( 'course_home_page',course_id=course.id) }}">Назад</a>


                {% if is_creator %}
                    <a class="create_btn"
                       href="{{ url_for( 'create_material',course_id=course.id, lesson_id=lesson.id) }}">
                        Add material
                    </a>

                    <a class="create_btn" href="{{ url_for( 'create_test',course_id=course.id, lesson_id=lesson.id) }}">
                        Add test
                    </a>
                {% endif %}


                <div class="material__title">
                    Материалы
                </div>

                {% for material in materials %}
                    <div class="material">
                        <div class="material__name">
                            {{ material.name }}
                        </div>
                        <div class="">
                            <a class="material__btn"
                               href="{{ url_for( 'download_material',course_id=course.id, lesson_id=lesson.id, material_id=material.id) }}">
                                Скачать
                            </a>
                        </div>
                    </div>
                {% endfor %}

                <div class="test__title">
                    Тесты
                </div>


                {% for test in tests %}
                    <div class="test">
                        <div class="test__name">
                            {{ test.name }}
                        </div>

{#                        <div class="test__type">#}
{#                            Тип: {{ test.t_type }}#}
{#                        </div>#}

                        <div class="test__exp">
                            Последний срок сдачи: {{ test.exp_date }}
                        </div>

                        <a class="test__btn"
                           href="{{ url_for( 'test_home_page',course_id=course.id, lesson_id=lesson.id, test_id=test.id) }}">
                            Решить
                        </a>
                        {% if is_creator %}
                            <a class="test__btn"
                                   href="{{ url_for( 'result_for_teacher',course_id=course.id, lesson_id=lesson.id, test_id=test.id,attempt=1) }}">
                                    Результаты
                                </a>
                        {% else %}
                            {% if solved_tests[test.id] %}
                                <a class="test__btn"
                                   href="{{ url_for( 'result',course_id=course.id, lesson_id=lesson.id, test_id=test.id,attempt=1) }}">
                                    Мои результаты
                                </a>
                            {% endif %}
                        {% endif %}
                    </div>
                {% endfor %}
            </div>
        </div>
    </div>

    <@f.footer />

</div>


</body>
</html>