<#import "../layout.ftlh" as main>

<@main.layout ; spring>

    <div class="text-center">
        <h2><@spring.message 'reset.your.password'/></h2>
        <#if error??>
            <b class="text-danger">${error}</b>
        </#if>
    </div>

    <div class="py-5 h-100">
        <div class="row d-flex align-items-center justify-content-center h-100">
            <div class="col-md-7 col-lg-5 col-xl-5">
                <form action="/auth/reset_password" method="post">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                               value="${(_csrf.token)!'csrf-token'}"/>
                    </#if>

                    <@spring.formHiddenInput "resetDto.token" 'id="token" class="form-control" required'/>
                    <@spring.showErrors "<br>" "error text-danger" />

                    <div class="mb-3" id="passwordBlock">
                        <label for="password" id="labelPassword"
                               class="form-label"><@spring.message 'reset.placeholder'/></label>
                        <@spring.formPasswordInput "resetDto.password" 'id="password" class="form-control" required autofocus'/>
                        <@spring.showErrors "<br>" "error text-danger" />
                    </div>

                    <p class="text-center">
                        <button type="submit" class="btn btn-primary mt-3"><@spring.message 'reset.button'/></button>
                    </p>
                </form>
            </div>
        </div>
    </div>

</@main.layout>