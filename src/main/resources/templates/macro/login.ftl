<#include "security.ftl">

<#macro login path isRegisterForm>
    <form class="loginForm" method="POST" action="${path}">

        <label class="form__label">Login</label>

        <input class="login__input ${(usernameError??)?string('is-invalid', '')}" type="text" name="login_input"
               id="login_input"
               value="<#if user??>${user.username}</#if>">
        <#if usernameError??>
            <div class="invalid-feedback">
                ${usernameError}
            </div>
        </#if>

        <label class="form__label">Password</label>

        <div class="password">
            <input class="password__input ${(passwordError??)?string('is-invalid', '')}" type="password"
                   name="password_input" id="password_input">
            <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if>
            <i class="fas fa-eye-slash password__icon" id="showPassword" onclick="showHidePassword()"
               style="display: none"></i>
            <i class="fas fa-eye password__icon" id="hidePassword" onclick="showHidePassword()"></i>
        </div>


        <#if isRegisterForm>
            <label class="form__label">Почта</label>
            <input value="<#if user??>${user.email}</#if>" type="email"
                   class="login__input ${(emailError??)?string('is-invalid', '')}" id="email_input"
                   name="email_input"
                   aria-describedby="emailHelp" placeholder="some@some.com" required>
            <#if emailError??>
                <div class="invalid-feedback">
                    ${emailError}
                </div>
            </#if>

            <label class="form__label">Повтор пароля</label>

            <div class="password">
                <input type="password" class="password__input ${(password2Error??)?string('is-invalid', '')}"
                       name="password2"
                       id="password_input_conf" required>
                <#if password2Error??>
                    <div class="invalid-feedback">
                        ${password2Error}
                    </div>
                </#if>
                <i class="fas fa-eye-slash password__icon" id="showPasswordConf"
                   onclick="showHidePassword()"
                   style="display: none"></i>
                <i class="fas fa-eye password__icon" id="hidePasswordConf" onclick="showHidePassword()"></i>
            </div>
        </#if>

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>

        <#if !isRegisterForm><a class="registrationLink" href="/registration">Не
            зарегестрированны?</a></#if>

        <button class="loginButton"
                type="submit"><#if isRegisterForm>Create<#else>Sign In</#if></button>
    </form>

</#macro>
