<#import "../macro/headerMacro.ftl" as h>
<#import "../macro/footerMacro.ftl" as f>
<#import "../macro/headGeneral.ftl" as g>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All users</title>

    <@g.head />

    <link rel="stylesheet" href="/css/allUsers.css">

    <!-- bootstrap -->

    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
          crossorigin="anonymous">
</head>
<body>
<div class="wrapper">

    <@h.header />

    <div class="content">
        <div class="container">

            <div class="users">
                <#list users as user>
                    <div class="user">
                        <div class="col">
                            Login:
                        </div>
                        <div class="col">
                            ${ user.username }
                        </div>

                        <div class="col">
                            Email:
                        </div>
                        <div class="col">
                            ${ user.email }
                        </div>

                        <div class="col">
                            Roles:
                        </div>

                        <Form class="form-inline" method="POST" action="/user/changeRole">
                            <input type="hidden" value="${_csrf.token}" name="_csrf"/>

                            <div class="col">
                                <#list roles as role>
                                    ${role}
                                    <input class="form-control" type="checkbox"
                                           name="${role}"
                                            ${user.roles?seq_contains(role)?string("checked", "")}>
                                </#list>
                            </div>
                            <input hidden name="userId" value="${user.id}">
                            <input type="submit" value="Изменить роль" class="user__btn">
                        </Form>

                        <Form class="form-inline ml-3" method="POST" action="/user/deleteUser">
                            <input type="hidden" value="${_csrf.token}" name="_csrf"/>
                            <input hidden name="userId" value="${ user.id }">
                            <input type="submit" value="Удалить" class="user__btn">
                        </Form>


                    </div>

                </#list>
            </div>

        </div>
    </div>

    <@f.footer />

</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>
</body>
</html>