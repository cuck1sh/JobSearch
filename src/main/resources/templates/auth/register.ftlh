<#import  "../layout.ftlh" as main>
<@main.layout ; spring>

    <div class="w-50 p-4 mx-auto border rounded-2 mb-4">
        <form action="/auth/register" method="post" id="form" enctype="multipart/form-data">
            <div class="mb-3">
                <label for="accountType" class="form-label">
                    <@spring.message 'register.account.type.label'/>
                </label>
                <@spring.formSingleSelect "userDto.accountType",
                {"0": "Тип аккаунта / Account type", "EMPLOYEE": "Соискатель / Employee", "EMPLOYER": "Работодатель / Employer"},
                "id='accountType' class='form-select' required" />
                <@spring.showErrors "<br>" "error text-danger" />
            </div>
            <div class="mb-3" id="nameBlock">
                <label for="name" id="labelName" class="form-label"><@spring.message 'register.name'/></label>
                <@spring.formInput "userDto.name" 'id="name" class="form-control" required'/>
                <@spring.showErrors "<br>" "error text-danger" />
            </div>
            <div class="mb-3" id="surnameBlock">
                <label for="surname" class="form-label"><@spring.message 'register.surname'/></label>
                <@spring.formInput "userDto.surname" 'id="surname" class="form-control"'/>
            </div>
            <div class="mb-3" id="ageBlock">
                <label for="age" class="form-label"><@spring.message 'register.age'/></label>
                <@spring.formInput "userDto.age" 'id="age" class="form-control" placeholder="min 18"'/>
                <@spring.showErrors "<br>" "error text-danger" />
            </div>
            <div class="mb-3">
                <label for="email" class="form-label"><@spring.message 'register.email'/></label>
                <@spring.formInput "userDto.email" 'id="email" class="form-control" placeholder="example@mail.com" required'/>
                <@spring.showErrors "<br>" "error text-danger" />
            </div>
            <div class="mb-3">
                <label for="password" class="form-label mb-0"><@spring.message 'register.password'/></label>
                <div id="passwordHelp" class="form-text">
                    <@spring.message 'register.password.help'/>
                </div>
                <@spring.formPasswordInput "userDto.password" 'id="password" class="form-control" required'/>
                <@spring.showErrors "<br>" "error text-danger" />
            </div>
            <div class="mb-3">
                <label for="phoneNumber" class="form-label mb-0"><@spring.message 'register.phone.number'/></label>
                <div id="phoneNumberHelp" class="form-text"><@spring.message 'register.phone.number.help'/></div>
                <@spring.formInput "userDto.phoneNumber" 'id="phoneNumber" class="form-control" placeholder="996-ddd-dd-dd-dd" required'/>
                <@spring.showErrors "<br>" "error text-danger" />
            </div>
            <div class="mb-3">
                <label for="formFile" class="form-label"><@spring.message 'register.avatar'/></label>
                <input name="file" class="form-control" type="file" id="formFile">
            </div>

            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                       value="${(_csrf.token)!'csrf-token'}"/>
            </#if>

            <button type="submit" class="btn btn-primary"><@spring.message 'register.button'/></button>
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