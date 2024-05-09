<#import  "../layout.ftlh" as main>
<@main.layout ; spring>

    <div class="w-50 p-4 mx-auto border rounded-2 mb-4">
        <form action="/auth/register" method="post" enctype="multipart/form-data">
            <div class="mb-3">
                <label for="account_type" class="form-label">Укажите тип вашего аккаунта: соискатель или
                    работодатель</label>
                <@spring.formSingleSelect "userDto.accountType",
                {"0": "Тип аккаунта", "EMPLOYEE": "Соискатель", "EMPLOYER": "Работодатель"},
                "id='account_type' class='form-select' required" />
            </div>
            <div class="mb-3">
                <label for="name" class="form-label">Имя</label>
                <@spring.formInput "userDto.name" 'id="name" class="form-control" placeholder="Иван" required'/>
                <@spring.showErrors "<br>" "error text-danger" />
            </div>
            <div class="mb-3">
                <label for="surname" class="form-label">Фамилия</label>
                <@spring.formInput "userDto.surname" 'id="surname" class="form-control" placeholder="Иванов"'/>
            </div>
            <div class="mb-3">
                <label for="age" class="form-label">Возраст</label>
                <@spring.formInput "userDto.age" 'id="age" class="form-control" placeholder="25 лет"'/>
                <@spring.showErrors "<br>" "error text-danger" />
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email адрес</label>
                <@spring.formInput "userDto.email" 'id="email" class="form-control" placeholder="example@mail.com" required'/>
                <@spring.showErrors "<br>" "error text-danger" />
            </div>
            <div class="mb-3">
                <label for="password" class="form-label mb-0">Password</label>
                <div id="passwordHelp" class="form-text">Пароль должен быть не менее 4 символов, иметь 1 заглавную букву
                    и 1 цифру
                </div>
                <@spring.formPasswordInput "userDto.password" 'id="password" class="form-control" required'/>
                <@spring.showErrors "<br>" "error text-danger" />
            </div>
            <div class="mb-3">
                <label for="phone_number" class="form-label">Номер телефона</label>
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

            <button type="submit" class="btn btn-primary">Создать</button>
        </form>
    </div>

</@main.layout>