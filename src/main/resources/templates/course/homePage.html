<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>{{ course.name }}</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/general.css') }}">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/header.css') }}">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/footer.css') }}">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@300;400;500;700&display=swap"
          rel="stylesheet">
    <script src="{{ url_for('static', filename='js/header.js') }}"></script>
    <script src="https://kit.fontawesome.com/8cf596884a.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">

    <link rel="stylesheet" href="{{ url_for('static', filename='css/course/courseHomePage.css') }}">
</head>
<body>

<div class="wrapper">

    {% from 'macro/headerMacro.html' import header %}
    {% from 'macro/footerMacro.html' import footer %}
    {{ header(current_user) }}


    <div class="content">
        <div class="container">
            <div class="course">
                <form class="course__search__row" action="{{ url_for('course_home_page', course_id=course.id) }}"
                      method="GET">
                    <input type="text" class="course__input" name="name" placeholder="Название курса">
                    <input type="text" class="course__input" name="description" placeholder="Сложность">
                    <button type="submit" class="course__btn">Найти</button>
                </form>

                <div class="course__title">
                    Курс: {{ course.name }}
                </div>

                {% if is_creator %}
                    <a class="create_lesson_btn" href="{{ url_for( 'create_lesson',course_id=course.id) }}">
                        Создать урок
                    </a>
                {% endif %}


                <div class="course__title">
                    Уроки:
                </div>

                {% for lesson in lessons %}
                    <div class="course__lesson">
                        <div class="lesson__title">
                            Название: {{ lesson.name }}
                        </div>
                        <div class="lesson__difficulty">
                            Сложность: {{ lesson.difficulty }}
                        </div>


                        <a class="lesson__btn"
                           href="{{ url_for('lesson_home_page', course_id=course.id, lesson_id=lesson.id) }}">Пройти</a>
                    </div>
                {% endfor %}

            </div>
        </div>
    </div>

    {{ footer(current_user) }}

</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
        integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
        integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
        crossorigin="anonymous"></script>
</body>
</html>