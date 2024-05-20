<#import  "../layout.ftlh" as main>
<@main.layout ; spring>

    <div class="w-50 p-4 mx-auto border rounded-2 mb-4">
        <form action="/users/update" method="post" enctype="multipart/form-data">
            <div class="mb-3">
                <#if userDto.getAccountType() == 'EMPLOYEE'>
                    <label for="name" class="form-label"><@spring.message 'editing.profile.name'/></label>
                <#else>
                    <label for="name" class="form-label"><@spring.message 'editing.profile.company'/></label>
                </#if>
                <@spring.formInput "userDto.name" 'id="name" class="form-control" required'/>
                <@spring.showErrors "<br>" "error text-danger" />
            </div>
            <#if userDto.getAccountType() == 'EMPLOYEE'>
                <div class="mb-3">
                    <label for="surname" class="form-label"><@spring.message 'editing.profile.surname'/></label>
                    <@spring.formInput "userDto.surname" 'id="surname" class="form-control"'/>
                </div>

                <div class="mb-3">
                    <label for="age" class="form-label"><@spring.message 'editing.profile.age'/></label>
                    <@spring.formInput "userDto.age" 'id="age" class="form-control" placeholder="25 лет"'/>
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>
            </#if>

            <div class="mb-3">
                <label for="phoneNumber" class="form-label mb-0"><@spring.message 'editing.profile.phone'/></label>
                <div id="phoneNumberHelp" class="form-text">
                    <@spring.message 'editing.profile.phone.help'/> 996-ddd-dd-dd-dd
                </div>
                <@spring.formInput "userDto.phoneNumber" 'id="phoneNumber" class="form-control" placeholder="996-ddd-dd-dd-dd" required'/>
                <@spring.showErrors "<br>" "error text-danger" />
            </div>
            <div class="mb-3">
                <label for="formFile" class="form-label"><@spring.message 'editing.profile.avatar'/></label>
                <input name="file" class="form-control" type="file" id="formFile">
            </div>

            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                       value="${(_csrf.token)!'csrf-token'}"/>
            </#if>

            <button type="submit" class="btn btn-primary"><@spring.message 'editing.profile.button'/></button>
        </form>
    </div>

</@main.layout>