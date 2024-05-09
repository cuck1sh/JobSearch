<#import  "../layout.ftlh" as main>
<@main.layout>

    <form action="/api/auth/login" method="post" class="mx-auto w-25 mt-5" id="form">

        <div class="form-outline mb-4">
            <input type="email" name="username" id="form2Example1" class="form-control"/>
            <label class="form-label" for="form2Example1">Email address</label>
        </div>

        <div class="form-outline mb-4">
            <input type="password" name="password" id="form2Example2" class="form-control"/>
            <label class="form-label" for="form2Example2">Password</label>
        </div>

        <#if _csrf??>
            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                   value="${(_csrf.token)!'csrf-token'}"/>
        </#if>

        <div class="d-grid gap-2">
            <button type="submit" class="btn bg-success border p-2 text-dark bg-opacity-10 rounded-3 mb-4">Логин
            </button>
        </div>

        <div class="text-center">
            <p>Нет аккаунта? <a href="/users/register">Зарегистрироваться</a></p>
        </div>
    </form>

    <script>
        window.addEventListener('load', () => {
            let urlParams = new URLSearchParams(window.location.search);
            let param = urlParams.get('error');
            let form = document.getElementById('form')

            if (param != null) {
                let alert = document.createElement('div')
                alert.className = 'alert alert-danger'
                alert.role = 'alert'
                alert.textContent = 'Неправильный логин или пароль'
                form.prepend(alert)

            }
        })
    </script>

</@main.layout>