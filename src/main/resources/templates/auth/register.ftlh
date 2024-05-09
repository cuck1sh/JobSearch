<#import  "../layout.ftlh" as main>
<@main.layout ; spring>

    <div class="w-50 p-4 mx-auto border rounded-2 mb-4">
        <form action="/auth/register" method="post" id="form" enctype="multipart/form-data">
            <div class="mb-3">
                <label for="accountType" class="form-label">Укажите тип вашего аккаунта: соискатель или
                    работодатель</label>
                <@spring.formSingleSelect "userDto.accountType",
                {"0": "Тип аккаунта", "EMPLOYEE": "Соискатель", "EMPLOYER": "Работодатель"},
                "id='accountType' class='form-select' required" />
            </div>
            <div class="mb-3" id="nameBlock">
                <label for="name" id="labelName" class="form-label">Имя</label>
                <@spring.formInput "userDto.name" 'id="name" class="form-control" required'/>
                <@spring.showErrors "<br>" "error text-danger" />
            </div>
            <div class="mb-3" id="surnameBlock">
                <label for="surname" class="form-label">Фамилия</label>
                <@spring.formInput "userDto.surname" 'id="surname" class="form-control"'/>
            </div>
            <div class="mb-3" id="ageBlock">
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
                <label for="phoneNumber" class="form-label">Номер телефона</label>
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

    <script>
        'use strict'

        window.addEventListener('load', () => {

            let accountType = document.getElementById('accountType')
            let selectedOption = accountType.value

            let form = document.getElementById('form')
            let nameBlock = document.getElementById('nameBlock')

            buildForm(selectedOption)

            accountType.addEventListener('change', e => {
                let selected = e.target.value

                buildForm(selected)
            })


            function buildForm(selectedOption) {
                let surnameBlock = document.getElementById('surnameBlock')
                let ageBlock = document.getElementById('ageBlock')
                let labelName = document.getElementById('labelName')

                if (selectedOption === 'EMPLOYEE') {
                    if (labelName.innerText === 'Название компании') {
                        labelName.innerText = 'Имя'
                    }
                    if (!surnameBlock) {
                        createSurnameAndAgeBlock()
                    }
                } else if (selectedOption === 'EMPLOYER') {
                    if (labelName.innerText === 'Имя') {
                        labelName.innerText = 'Название компании'
                    }
                    if (surnameBlock) {
                        surnameBlock.remove()
                        ageBlock.remove()
                    }
                }
            }


            function createSurnameAndAgeBlock() {
                let newSurnameBlock = document.createElement('div')
                newSurnameBlock.className = 'mb-3'
                newSurnameBlock.id = 'surnameBlock'
                newSurnameBlock.innerHTML =
                    '<label for="surname" class="form-label">Фамилия</label>' +
                    `<@spring.formInput "userDto.surname" 'id="surname" class="form-control"'/>`
                form.appendChild(newSurnameBlock)
                nameBlock.after(newSurnameBlock)

                let newAgeBlock = document.createElement('div')
                newAgeBlock.className = 'mb-3'
                newAgeBlock.id = 'ageBlock'
                newAgeBlock.innerHTML =
                    '<label for="age" class="form-label">Возраст</label>' +
                    `<@spring.formInput "userDto.age" 'id="age" class="form-control"'/>`
                form.appendChild(newAgeBlock)
                newSurnameBlock.after(newAgeBlock)
            }

        })

    </script>


</@main.layout>