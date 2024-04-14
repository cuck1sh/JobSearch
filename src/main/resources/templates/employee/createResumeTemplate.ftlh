<#import  "../layout.ftlh" as main>
<@main.layout>

    <div class="col-12 col-lg-6 p-4 mx-auto border rounded-2 mb-4">
        <h1 class="text-center mt-2 mb-3 text-danger">Создание резюме:</h1>
        <form id="form">

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
                                <option value=${category.name}>${category.name}</option>
                            </#list>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="salary" class="form-label">Ожидаемый уровень оклада</label>
                        <input name="salary" type="number" class="form-control" id="salary" placeholder="Введите оклад">
                    </div>
                </div>
            </div>

            <h4>О себе</h4>
            <div id="workExperienceInfo">
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
                </div>
            </div>

            <div id="educationInfo">
                <div class="row mb-3 " id="education">
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

                    <button type="button" id="educationButton"
                            class="btn col-3 m-2 text-decoration-none bg-info p-2 text-dark bg-opacity-10 rounded-3">
                        <span class="fs-1">+</span>
                    </button>
                </div>
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
            const form = document.getElementById('form');
            const workExp = document.getElementById('workExp');
            const workExpButton = document.getElementById('workExpButton');
            const education = document.getElementById('education');
            const educationButton = document.getElementById('educationButton');

            workExpButton.addEventListener('click', () => {
                let rowForWorkExp = document.createElement('div');
                rowForWorkExp.className = 'row py-2 mb-3 mx-0 ';
                workExp.after(rowForWorkExp);

                let colForWorkExp = document.createElement('div');
                colForWorkExp.className = 'col-7 workExpHuexp pt-2 bg-success text-dark bg-opacity-10 rounded-3';
                rowForWorkExp.appendChild(colForWorkExp);

                let marginForYears = document.createElement('div');
                marginForYears.className = 'mb-3';
                colForWorkExp.appendChild(marginForYears);

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
                colForWorkExp.appendChild(marginCompany);

                let labelCompany = document.createElement('label');
                labelCompany.className = 'form-label';
                labelCompany.setAttribute('for', 'companyName')
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
                colForWorkExp.appendChild(marginPosition);

                let labelPosition = document.createElement('label');
                labelPosition.className = 'form-label';
                labelPosition.setAttribute('for', 'position')
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
                colForWorkExp.appendChild(marginResponsibilities);

                let labelResponsibilities = document.createElement('label');
                labelResponsibilities.className = 'form-label';
                labelResponsibilities.setAttribute('for', 'responsibilities')
                marginResponsibilities.appendChild(labelResponsibilities);

                let labelResponsibilitiesText = document.createTextNode('Обязанности:');
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
                rowForWorkExp.appendChild(buttonCol);

                let fsMinus = document.createElement('span');
                fsMinus.className = 'fs-1';
                buttonCol.appendChild(fsMinus);
                let fsMinusText = document.createTextNode('-');
                fsMinus.appendChild(fsMinusText);

                let buttonColEvent = document.getElementById(buttonColI);

                buttonColEvent.addEventListener('click', () => {
                    rowForWorkExp.remove();
                });
            });

            educationButton.addEventListener('click', () => {
                let rowForEducation = document.createElement('div');
                rowForEducation.className = 'row py-2 mb-3 mx-0 ';
                education.after(rowForEducation);

                let colForEducation = document.createElement('div');
                colForEducation.className = 'col-7 educationInfoHuyo pt-2 bg-info text-dark bg-opacity-10 rounded-3';
                rowForEducation.appendChild(colForEducation);

                let marginForInstitution = document.createElement('div');
                marginForInstitution.className = 'mb-3';
                colForEducation.appendChild(marginForInstitution);

                let labelInstitution = document.createElement('label');
                labelInstitution.className = 'form-label';
                labelInstitution.setAttribute('for', 'institution')
                marginForInstitution.appendChild(labelInstitution);

                let labelInstitutionText = document.createTextNode('Название образовательного учреждения:');
                labelInstitution.appendChild(labelInstitutionText);

                let inputInstitution = document.createElement('input');
                inputInstitution.className = 'form-control';
                inputInstitution.name = 'institution';
                inputInstitution.id = 'institution';
                inputInstitution.type = 'text';
                inputInstitution.placeholder = 'Введите название';
                inputInstitution.required = true;
                marginForInstitution.appendChild(inputInstitution);

                let marginForProgram = document.createElement('div');
                marginForProgram.className = 'mb-3';
                colForEducation.appendChild(marginForProgram);

                let labelProgram = document.createElement('label');
                labelProgram.className = 'form-label';
                labelProgram.setAttribute('for', 'program')
                marginForProgram.appendChild(labelProgram);

                let labelProgramText = document.createTextNode('Программа обучения:');
                labelProgram.appendChild(labelProgramText);

                let inputProgram = document.createElement('input');
                inputProgram.className = 'form-control';
                inputProgram.name = 'program';
                inputProgram.id = 'program';
                inputProgram.type = 'text';
                inputProgram.required = true;
                marginForProgram.appendChild(inputProgram);

                let marginForStartDate = document.createElement('div');
                marginForStartDate.className = 'mb-3';
                colForEducation.appendChild(marginForStartDate);

                let labelStartDate = document.createElement('label');
                labelStartDate.className = 'form-label';
                labelStartDate.setAttribute('for', 'startDate')
                marginForStartDate.appendChild(labelStartDate);

                let labelStartDateText = document.createTextNode('Дата поступления:');
                labelStartDate.appendChild(labelStartDateText);

                let inputStartDate = document.createElement('input');
                inputStartDate.className = 'form-control';
                inputStartDate.name = 'startDate';
                inputStartDate.id = 'startDate';
                inputStartDate.type = 'date';
                inputStartDate.required = true;
                marginForStartDate.appendChild(inputStartDate);

                let marginForEndDate = document.createElement('div');
                marginForEndDate.className = 'mb-3';
                colForEducation.appendChild(marginForEndDate);

                let labelEndDate = document.createElement('label');
                labelEndDate.className = 'form-label';
                labelEndDate.setAttribute('for', 'endDate')
                marginForEndDate.appendChild(labelEndDate);

                let labelEndDateText = document.createTextNode('Дата окончания:');
                labelEndDate.appendChild(labelEndDateText);

                let inputEndDate = document.createElement('input');
                inputEndDate.className = 'form-control';
                inputEndDate.name = 'endDate';
                inputEndDate.id = 'endDate';
                inputEndDate.type = 'date';
                inputEndDate.required = true;
                marginForEndDate.appendChild(inputEndDate);

                let marginForDegree = document.createElement('div');
                marginForDegree.className = 'mb-3';
                colForEducation.appendChild(marginForDegree);

                let labelDegree = document.createElement('label');
                labelDegree.className = 'form-label';
                labelDegree.setAttribute('for', 'degree')
                marginForDegree.appendChild(labelDegree);

                let labelDegreeText = document.createTextNode('Полученная степень:');
                labelDegree.appendChild(labelDegreeText);

                let inputDegree = document.createElement('input');
                inputDegree.className = 'form-control';
                inputDegree.name = 'degree';
                inputDegree.id = 'degree';
                inputDegree.type = 'text';
                marginForDegree.appendChild(inputDegree);

                let j = 1;

                let educationButtonCol = document.createElement('div');
                educationButtonCol.className = 'btn col-3 m-2 d-flex justify-content-center align-items-center text-decoration-none bg-danger p-2 text-dark bg-opacity-10 rounded-3';
                j += 1;
                let educationButtonColI = 'educationButtonCol-' + j
                educationButtonCol.id = educationButtonColI;
                rowForEducation.appendChild(educationButtonCol);

                let educationFsMinus = document.createElement('span');
                educationFsMinus.className = 'fs-1';
                educationButtonCol.appendChild(educationFsMinus);
                let educationFsMinusText = document.createTextNode('-');
                educationFsMinus.appendChild(educationFsMinusText);

                let educationButtonColEvent = document.getElementById(educationButtonColI);

                educationButtonColEvent.addEventListener('click', () => {
                    rowForEducation.remove();
                });
            });

            function creationHandler(event) {
                event.preventDefault();
                const form = event.target
                let data = new FormData(form)

                const workExpHuexp = document.getElementsByClassName('workExpHuexp');
                const educationInfoHuyo = document.getElementsByClassName('educationInfoHuyo');

                let workExperienceInfoDtos = [];
                let educationInfos = [];

                for (let work of workExpHuexp) {
                    workExperienceInfoDtos.push({
                        years: parseInt(work.querySelector('#years').value),
                        company_name: work.querySelector('#companyName').value,
                        position: work.querySelector('#position').value,
                        responsibilities: work.querySelector('#responsibilities').value
                    });
                }

                for (let educ of educationInfoHuyo) {
                    educationInfos.push({
                        institution: educ.querySelector('#institution').value,
                        program: educ.querySelector('#program').value,
                        start_date: educ.querySelector('#startDate').value,
                        end_date: educ.querySelector('#endDate').value,
                        degree: educ.querySelector('#degree').value
                    });
                }

                let json = JSON.stringify({
                    name: data.get('name'),
                    category: data.get('category'),
                    salary: parseFloat(data.get('salary')),
                    work_experience_infos: workExperienceInfoDtos,
                    education_infos: educationInfos,
                    contacts: {
                        telegram: data.get('telegram'),
                        phoneNumber: data.get('phoneNumber'),
                        email: data.get('email'),
                        facebook: data.get('facebook'),
                        linkedIn: data.get('linkedIn')
                    },
                    is_active: data.get('isActive')
                });

                console.log(json);

                fetch('/employee/resumes/add', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: json
                });

            }

            form.addEventListener('submit', creationHandler);
        });
    </script>


</@main.layout>