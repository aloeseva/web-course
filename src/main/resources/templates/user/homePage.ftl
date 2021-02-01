<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${user.username}</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/general.css') }}">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/header.css') }}">
    <link rel="stylesheet" href="{{ url_for('static', filename='css/footer.css') }}">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@300;400;500;700&display=swap"
          rel="stylesheet">
    <script src="{{ url_for('static', filename='js/header.js') }}"></script>
    <script src="https://kit.fontawesome.com/8cf596884a.js" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="{{ url_for('static', filename='css/userHomePage.css') }}">
</head>
<body>

<div class="wrapper">

    {% from 'macro/headerMacro.html' import header %}
    {% from 'macro/footerMacro.html' import footer %}

    {{ header(current_user) }}


    <div class="content">
        <div class="container">

            <form class="user" method="POST" action="{{ url_for("user_page") }}">

                {% if is_teacher %}
                    <a href="{{ url_for('created_courses') }}" class="createdCourses"> Созданные курсы</a>
                {% endif %}



                <label class="user__label">Login</label>
                <input
                        type="text"
                        class="user__input"
                        name="login_change"
                        id="login_change"
                        placeholder="Login"
                        value={{ user.login }}
                >


                <label class="user__label">E-mail</label>
                <input
                        type="email"
                        class="user__input"
                        name="email_change"
                        id="email_change"
                        placeholder="E-mail"
                        value={{ user.email }}
                >


                <label class="user__label">Registration date</label>
                <div class="user__date" type="text" name="reg_date" id="reg_date">
                    {{ user.registration_date }}
                </div>


                <label class="user__label">Имя</label>
                <input
                        type="text"
                        class="user__input"
                        name="first_name_change"
                        id="first_name_change"
                        placeholder="Иван"
                        value={% if user.first_name != None %}{{ user.first_name }}{% endif %}
                >


                <label class="user__label">Фамилия</label>
                <input
                        type="text"
                        class="user__input"
                        name="last_name_change"
                        id="last_name_change"
                        placeholder="Иванов"
                        value={% if user.last_name != None %}{{ user.last_name }}{% endif %}
                >


                <button type="submit" class="user__btn">Сохранить</button>

            </form>

        </div>
    </div>

    {{ footer(current_user) }}

</div>
</body>
</html>