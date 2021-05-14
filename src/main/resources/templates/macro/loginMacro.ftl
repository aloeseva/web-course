<#include "security.ftl">

<#macro login path isRegisterForm>
    <form class="loginForm" method="POST" action="${path}">

        <label class="form__label">Логин</label>

        <input class="login__input" type="text" name="username"
               id="login_input"
               value="<#if user??>${user.username}</#if>">
        <#if usernameError??>
            <div class="reg__error">
                <ul class=flashes>
                    <li>${usernameError}</li>
                </ul>
            </div>
        </#if>

        <label class="form__label">Пароль</label>

        <div class="password">
            <input class="password__input" type="password"
                   name="password" id="password_input">

            <i class="fas fa-eye-slash password__icon" id="showPassword" onclick="showHidePassword()"
               style="display: none"></i>
            <i class="fas fa-eye password__icon" id="hidePassword" onclick="showHidePassword()"></i>
        </div>
        <#if passwordError??>
            <div class="reg__error">
                <ul class=flashes>
                    <li>${passwordError}</li>
                </ul>
            </div>
        </#if>

        <#if isRegisterForm>
            <label class="form__label">Повтор пароля</label>

            <div class="password">
                <input type="password" class="password__input"
                       name="password2"
                       id="password_input_conf">

                <i class="fas fa-eye-slash password__icon" id="showPasswordConf"
                   onclick="showHidePassword()"
                   style="display: none"></i>
                <i class="fas fa-eye password__icon" id="hidePasswordConf" onclick="showHidePassword()"></i>
            </div>
            <#if password2Error??>
                <div class="reg__error">
                    <ul class=flashes>
                        <li>${password2Error}</li>
                    </ul>
                </div>
            </#if>

            <label class="form__label">Почта</label>
            <input value="<#if user??>${user.email}</#if>" type="email"
                   class="login__input" id="email_input"
                   name="email"
                   aria-describedby="emailHelp" placeholder="some@some.com">
            <#if emailError??>
                <div class="reg__error">
                    <ul class=flashes>
                        <li>${emailError}</li>
                    </ul>
                </div>
            </#if>
        </#if>

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>

        <button class="loginButton"
                type="submit"><#if isRegisterForm>Create<#else>Sign In</#if></button>

        <#if !isRegisterForm>
            <a class="registrationLink" href="/registration">Не зарегестрированны?</a>
        <#else>
            <a class="registrationLink" href="/login">Войдите в аккаунт, если уже есть в системе</a>
        </#if>
    </form>

</#macro>
