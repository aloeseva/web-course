<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All our courses</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/general.css') }}">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/header.css') }}">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/footer.css') }}">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@300;400;500;700&display=swap"
          rel="stylesheet">
    <script src="{{ url_for('static', filename='js/header.js') }}"></script>
    <script src="https://kit.fontawesome.com/8cf596884a.js" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="{{ url_for('static', filename='css/course/allCourses.css') }}">
</head>
<body>

<div class="wrapper">

    {% from 'macro/headerMacro.html' import header %}
    {% from 'macro/footerMacro.html' import footer %}

    {{ header(current_user) }}


    <div class="content">
        <div class="container">
            <div class="courses">

                <form class="course__search__row" action="{{ url_for("all_courses") }}" method="GET">
                    <input class="course__input" type="text" name="course_name">
                    <input class="course__btn" type="submit" value="Поиск">
                </form>

                {% if is_teacher %}
                    <a class="create_course_btn" href="{{ url_for('create_course') }}">Создать курс</a>
                {% endif %}

                <div class="courses__row">

                    {% for course in courses %}
                        <div class="course">

                            <div class="course__img">

                                {% if course.image_name != "" %}
                                    <img src="{{ url_for('static', filename='image/' + course.image_name) }}"
                                         alt="course">
                                {% else %}
                                    <img src="{{ url_for('static', filename='image/course.png') }}" alt="course">
                                {% endif %}

                            </div>
                            <div class="course__last">
                                <div class="course__title">
                                    {{ course.name }}
                                </div>
                                <a class="course__link" href="{{ url_for('show_course',course_id=course.id) }}">Подробнее</a>
                            </div>
                        </div>
                    {% endfor %}
                </div>
            </div>
        </div>
    </div>

    {{ footer(current_user) }}

</div>
</body>
</html>