<#import  "../layout.ftlh" as main>
<@main.layout ; spring>

    <form action="/auth/login" method="post" class="mx-auto w-25 mt-5" id="form">

        <div class="form-outline mb-4">
            <input type="email" name="username" id="form2Example1" class="form-control"/>
            <label class="form-label" for="form2Example1"><@spring.message 'login.email'/></label>
        </div>

        <div class="form-outline mb-4">
            <input type="password" name="password" id="form2Example2" class="form-control"/>
            <label class="form-label" for="form2Example2"><@spring.message 'login.password'/></label>
        </div>

        <#if _csrf??>
            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                   value="${(_csrf.token)!'csrf-token'}"/>
        </#if>

        <div class="d-grid gap-2">
            <button type="submit" class="btn bg-success border p-2 text-dark bg-opacity-10 rounded-3 mb-4">
                <@spring.message 'login.login'/>
            </button>
        </div>


        <div class="text-center">
            <p><a href="/auth/forgot_password"><@spring.message 'login.forgot.password'/></a></p>
            <p><@spring.message 'login.no.account'/> <a href="/users/register"><@spring.message 'login.register'/></a>
            </p>
        </div>
    </form>

    <script src="/static/js/auth/login.js">

    </script>
</@main.layout>


