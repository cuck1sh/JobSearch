<#import  "../layout.ftlh" as main>
<@main.layout>

    <div class="col-12 col-lg-6 p-4 mx-auto border rounded-2 mb-4">
        <h1 class="text-center mt-2 mb-3 text-danger">Создание резюме:</h1>
        <form action="/employee/resumes/add" method="post" id="form">

            <div class="row">
                <div class="col-7">

                    <div class="mb-3">
                        <label for="name" class="form-label">Название резюме:</label>
                        <input name="name" type="text" class="form-control" id="name" placeholder="Введите название"
                               required>
                    </div>

                    <div class="mb-3">
                        <label for="category" class="form-label">Категория:</label>
                        <select name="category" id="category" class="form-select">
                            <option selected>Выберите категорию</option>
                            <#list categories as category>
                                <option value=${category.id}>${category.name}</option>
                            </#list>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="salary" class="form-label">Ожидаемый уровень оклада</label>
                        <input name="salary" type="number" class="form-control" id="salary" placeholder="Введите оклад">
                    </div>
                </div>
            </div>

            <h4 class="">О себе</h4>

            <div class="row mb-3" id="workExp">
                <div class="col-7">
                    <div class="card">
                        <div class="card-body">
                            <h6 class="card-text">Опыт работы:</h6>
                            <h6 class="fw-normal">Год работы: ___________</h6>
                            <h6 class="fw-normal">Название компании: ______________</h6>
                            <h6 class="fw-normal">Должность: ____________</h6>
                            <h6 class="fw-normal">Обязанности: _________</h6>
                        </div>
                    </div>
                </div>

                <button type="button" id="workExpButton"
                        class="btn col-3 m-2 text-decoration-none  bg-success p-2 text-dark bg-opacity-10 rounded-3">
                    <span class="fs-1">+</span>
                </button>

                <#--                <a href="/employee/resume/add/workExp/" target="_blank"-->
                <#--                   class="col-3 m-2 d-flex justify-content-center align-items-center text-decoration-none object-fit-cover border bg-success p-2 text-dark bg-opacity-10 rounded-3">-->
                <#--                    <span class="fs-1">+</span>-->
                <#--                </a>-->
            </div>

            <div class="row mb-3">
                <div class="col-7">
                    <div class="card">
                        <div class="card-body">
                            <h6 class="card-text">Навыки:</h6>
                            <h6 class="fw-normal">Образование: ___________</h6>
                            <h6 class="fw-normal">Где учился: ______________</h6>
                            <h6 class="fw-normal">Срок обучения: ____________</h6>
                        </div>
                    </div>
                </div>

                <a href="/employee/resume/add/education/" target="_blank"
                   class="col-3 m-2 d-flex justify-content-center align-items-center text-decoration-none object-fit-cover border bg-info p-2 text-dark bg-opacity-10 rounded-3">
                    <span class="fs-1">+</span>
                </a>
            </div>

            <h4 class="">Ссылки на соц. сети:</h4>

            <div class="row mb-3 row-cols-1 row-cols-lg-2">
                <div class="col">

                    <div class="d-flex">
                        <input name="telegram" type="text" class="form-control me-2 my-3" id="telegram">
                        <i class="bi bi-telegram align-content-center fs-2"></i>
                    </div>

                    <div class="d-flex">
                        <input name="email" type="email" class="form-control me-2 my-3" id="email"
                               placeholder="example@mail.com">
                        <i class="bi bi-envelope-at align-content-center fs-2"></i>
                    </div>

                    <div class="d-flex">
                        <input name="phoneNumber" type="text" class="form-control me-2 my-3" id="phoneNumber"
                               placeholder="996-779-11-22-33">
                        <i class="bi bi-telephone-inbound align-content-center fs-2"></i>
                    </div>
                </div>

                <div class="col">
                    <div class="d-flex">
                        <input name="facebook" type="text" class="form-control me-2 my-3" id="facebook">
                        <i class="bi bi-facebook align-content-center fs-2"></i>
                    </div>

                    <div class="d-flex">
                        <input name="linkedIn" type="text" class="form-control me-2 my-3" id="linkedIn">
                        <i class="bi bi-linkedin align-content-center fs-2"></i>
                    </div>
                </div>
            </div>

            <div class="form-check mb-3">
                <input class="form-check-input" name="isActive" type="checkbox" value=true id="isActive">
                <label class="form-check-label" for="isActive">
                    Активно
                </label>
            </div>

            <div class="d-grid gap-2">
                <button type="submit" class="btn bg-success border p-2 text-dark bg-opacity-10 rounded-3">Опубликовать
                </button>
            </div>
        </form>
    </div>

    <script>
        'use strict'
        window.addEventListener('load', () => {
            const workExp = document.getElementById('workExp');
            const workExpButton = document.getElementById('workExpButton');

            workExpButton.addEventListener('click', () => {
                let row = document.createElement('div');
                row.className = 'row py-2 mb-3 mx-0 ';
                workExp.after(row);

                let col = document.createElement('div');
                col.className = 'col-7 pt-2 bg-success text-dark bg-opacity-10 rounded-3';
                row.appendChild(col);

                let marginForYears = document.createElement('div');
                marginForYears.className = 'mb-3';
                col.appendChild(marginForYears);

                let labelForYears = document.createElement('label');
                labelForYears.className = 'form-label';
                labelForYears.setAttribute('for', 'years')
                marginForYears.appendChild(labelForYears);

                let labelForYearsText = document.createTextNode('Годы работы:');
                labelForYears.appendChild(labelForYearsText);

                let inputForYears = document.createElement('input');
                inputForYears.className = 'form-control';
                inputForYears.name = 'years';
                inputForYears.id = 'years';
                inputForYears.type = 'number';
                inputForYears.placeholder = 'стаж';
                inputForYears.required = true;
                marginForYears.appendChild(inputForYears);

                let marginCompany = document.createElement('div');
                marginCompany.className = 'mb-3';
                col.appendChild(marginCompany);

                let labelCompany = document.createElement('label');
                labelCompany.className = 'form-label';
                labelCompany.setAttribute('for', 'years')
                marginCompany.appendChild(labelCompany);

                let labelCompanyText = document.createTextNode('Название компании:');
                labelCompany.appendChild(labelCompanyText);

                let inputCompany = document.createElement('input');
                inputCompany.className = 'form-control';
                inputCompany.name = 'companyName';
                inputCompany.id = 'companyName';
                inputCompany.type = 'text';
                inputCompany.placeholder = 'Введите название';
                inputCompany.required = true;
                marginCompany.appendChild(inputCompany);

                let marginPosition = document.createElement('div');
                marginPosition.className = 'mb-3';
                col.appendChild(marginPosition);

                let labelPosition = document.createElement('label');
                labelPosition.className = 'form-label';
                labelPosition.setAttribute('for', 'years')
                marginPosition.appendChild(labelPosition);

                let labelPositionText = document.createTextNode('Должность:');
                labelPosition.appendChild(labelPositionText);

                let inputPosition = document.createElement('input');
                inputPosition.className = 'form-control';
                inputPosition.name = 'position';
                inputPosition.id = 'position';
                inputPosition.type = 'text';
                inputPosition.required = true;
                marginPosition.appendChild(inputPosition);

                let marginResponsibilities = document.createElement('div');
                marginResponsibilities.className = 'mb-3';
                col.appendChild(marginResponsibilities);

                let labelResponsibilities = document.createElement('label');
                labelResponsibilities.className = 'form-label';
                labelResponsibilities.setAttribute('for', 'years')
                marginResponsibilities.appendChild(labelResponsibilities);

                let labelResponsibilitiesText = document.createTextNode('Должность:');
                labelResponsibilities.appendChild(labelResponsibilitiesText);

                let inputResponsibilities = document.createElement('input');
                inputResponsibilities.className = 'form-control';
                inputResponsibilities.name = 'responsibilities';
                inputResponsibilities.id = 'responsibilities';
                inputResponsibilities.type = 'text';
                inputResponsibilities.required = true;
                marginResponsibilities.appendChild(inputResponsibilities);

                let i = 1;

                let buttonCol = document.createElement('div');
                buttonCol.className = 'btn col-3 m-2 d-flex justify-content-center align-items-center text-decoration-none bg-danger p-2 text-dark bg-opacity-10 rounded-3';
                i += 1;
                let buttonColI = 'buttonCol-' + i
                buttonCol.id = buttonColI;
                row.appendChild(buttonCol);

                let fsMinus = document.createElement('span');
                fsMinus.className = 'fs-1';
                buttonCol.appendChild(fsMinus);
                let fsMinusText = document.createTextNode('-');
                fsMinus.appendChild(fsMinusText);

                let buttonColEvent = document.getElementById(buttonColI);

                buttonColEvent.addEventListener('click', () => {
                    row.remove();
                });
            })
        });
    </script>

</@main.layout>