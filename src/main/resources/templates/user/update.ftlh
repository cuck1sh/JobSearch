<#import  "../layout.ftlh" as main>
<@main.layout ; spring>

    <div class="w-50 p-4 mx-auto border rounded-2 mb-4">
        <form action="/users/update" method="post" enctype="multipart/form-data">
            <div class="mb-3">
                <#if userDto.getAccountType() == 'EMPLOYEE'>
                    <label for="name" class="form-label">Имя</label>
                <#else>
                    <label for="name" class="form-label">Название компании</label>
                </#if>
                <@spring.formInput "userDto.name" 'id="name" class="form-control" required'/>
                <@spring.showErrors "<br>" "error text-danger" />
            </div>
            <#if userDto.getAccountType() == 'EMPLOYEE'>
                <div class="mb-3">
                    <label for="surname" class="form-label">Фамилия</label>
                    <@spring.formInput "userDto.surname" 'id="surname" class="form-control"'/>
                </div>

                <div class="mb-3">
                    <label for="age" class="form-label">Возраст</label>
                    <@spring.formInput "userDto.age" 'id="age" class="form-control" placeholder="25 лет"'/>
                    <@spring.showErrors "<br>" "error text-danger" />
                </div>
            </#if>

            <div class="mb-3">
                <label for="password" class="form-label mb-0">Password</label>
                <div id="passwordHelp" class="form-text">Пароль должен быть не менее 4 символов, иметь 1 заглавную букву
                    и 1 цифру
                </div>
                <@spring.formPasswordInput "userDto.password" 'id="password" class="form-control"'/>
                <@spring.showErrors "<br>" "error text-danger" />
            </div>
            <div class="mb-3">
                <label for="phoneNumber" class="form-label mb-0">Номер телефона</label>
                <div id="phoneNumberHelp" class="form-text">Формат номера: 996-ddd-dd-dd-dd</div>
                <@spring.formInput "userDto.phoneNumber" 'id="phoneNumber" class="form-control" placeholder="996-ddd-dd-dd-dd" required'/>
                <@spring.showErrors "<br>" "error text-danger" />
            </div>
            <div class="mb-3">
                <label for="formFile" class="form-label">Загрузить аватар</label>
                <input name="file" class="form-control" type="file" id="formFile">
            </div>

            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                       value="${(_csrf.token)!'csrf-token'}"/>
            </#if>

            <button type="submit" class="btn btn-primary">Изменить</button>
        </form>
    </div>

</@main.layout>