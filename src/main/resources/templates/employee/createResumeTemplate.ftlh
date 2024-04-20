<#import  "../layout.ftlh" as main>
<@main.layout>

    <div class="col-12 col-lg-6 p-4 mx-auto border rounded-2 mb-4">
        <h1 class="text-center mt-2 mb-3 text-danger">Создание резюме:</h1>
        <form id="form" action="/employee/resumes/add" method="POST">

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
                            <option value="0">Выберите категорию</option>
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
                        <input name="contacts.telegram" type="text" class="form-control me-2 my-3" id="telegram">
                        <i class="bi bi-telegram align-content-center fs-2"></i>
                    </div>

                    <div class="d-flex">
                        <input name="contacts.email" type="email" class="form-control me-2 my-3" id="email"
                               placeholder="example@mail.com">
                        <i class="bi bi-envelope-at align-content-center fs-2"></i>
                    </div>

                    <div class="d-flex">
                        <input name="contacts.phoneNumber" type="text" class="form-control me-2 my-3" id="phoneNumber"
                               placeholder="996-779-11-22-33">
                        <i class="bi bi-telephone-inbound align-content-center fs-2"></i>
                    </div>
                </div>

                <div class="col">
                    <div class="d-flex">
                        <input name="contacts.facebook" type="text" class="form-control me-2 my-3" id="facebook">
                        <i class="bi bi-facebook align-content-center fs-2"></i>
                    </div>

                    <div class="d-flex">
                        <input name="contacts.linkedIn" type="text" class="form-control me-2 my-3" id="linkedIn">
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
            let workExperienceInfo = document.getElementById('workExperienceInfo');
            let educationInfo = document.getElementById('educationInfo');
            let contactsInfo = document.getElementById('contacts_info');

            let workExpButton = document.getElementById('workExpButton');
            let educationButton = document.getElementById('educationButton');
            let contactsButton = document.getElementById('contactsButton');

            let workExp = document.getElementById('workExp');
            let education = document.getElementById('education');
            let contacts = document.getElementById('contacts');


            let expCounter = -1;
            let eduCounter = -1;
            let conCounter = -1;

            workExpButton.addEventListener('click', () => {
                expCounter++;
                console.log(expCounter);
                let newWorkExpRow = document.createElement('div');
                newWorkExpRow.className = 'row py-2 mb-3 mx-0 ';
                workExp.after(newWorkExpRow);

                let newWorkExpCol = document.createElement('div');
                newWorkExpCol.className = 'col-7 pt-2 bg-success text-dark bg-opacity-10 rounded-3';
                newWorkExpCol.innerHTML =
                    '<div class = "mb-3">' +
                    '<label for="years" class="form-label">Годы работы:</label> ' +
                    '<input class="form-control" name="workExperienceInfoDtos[' + expCounter + '].years" type="number" placeholder="стаж" id="years" required>' +
                    '</div>' +

                    '<div class = "mb-3">' +
                    '<label for="companyName" class="form-label">Название компании:</label> ' +
                    '<input class="form-control" name="workExperienceInfoDtos[' + expCounter + '].companyName" type="text" placeholder="Введите название" id="companyName" required>' +
                    '</div>' +

                    '<div class = "mb-3">' +
                    '<label for="position" class="form-label">Должность:</label> ' +
                    '<input class="form-control" name="workExperienceInfoDtos[' + expCounter + '].position" type="text" id="position" required>' +
                    '</div>' +

                    '<div class = "mb-3">' +
                    '<label for="responsibilities" class="form-label">Обязанности:</label> ' +
                    '<input class="form-control" name="workExperienceInfoDtos[' + expCounter + '].responsibilities" type="text" id="responsibilities" required>' +
                    '</div>';
                newWorkExpRow.appendChild(newWorkExpCol);

                let newExpButton = document.createElement('div');
                newExpButton.className = 'btn remove-button col-3 m-2 d-flex justify-content-center align-items-center bg-danger p-2 bg-opacity-10 rounded-3';
                newExpButton.innerHTML = '<span class="fs-1">-</span>';
                newWorkExpRow.appendChild(newExpButton);

                newWorkExpRow.querySelector('.remove-button').addEventListener('click', () => {
                    if (newWorkExpRow.previousElementSibling.id === 'workExp') {
                        workExperienceInfo.removeChild(newWorkExpRow);
                        expCounter--;
                    } else {
                        let warningBlock = document.createElement('div');
                        warningBlock.innerText = 'Вы можете удалять только последний блок';
                        warningBlock.className = 'text-danger';
                        newWorkExpRow.appendChild(warningBlock);

                        setTimeout(() => {
                            warningBlock.remove();
                        }, 3000)
                    }
                });
            });

            educationButton.addEventListener('click', () => {
                eduCounter++;
                console.log(eduCounter);
                let newEduRow = document.createElement('div');
                newEduRow.className = 'row py-2 mb-3 mx-0 ';
                education.after(newEduRow);

                let newEduCol = document.createElement('div');
                newEduCol.className = 'col-7 pt-2 bg-info text-dark bg-opacity-10 rounded-3';
                newEduCol.innerHTML =
                    '<div class = "mb-3">' +
                    '<label for="institution" class="form-label">Название образовательного учреждения:</label> ' +
                    '<input class="form-control" name="educationInfos[' + eduCounter + '].institution" type="text" placeholder="Введите название" id="institution" required>' +
                    '</div>' +

                    '<div class = "mb-3">' +
                    '<label for="program" class="form-label">Программа обучения:</label> ' +
                    '<input class="form-control" name="educationInfos[' + eduCounter + '].program" type="text" id="program" required>' +
                    '</div>' +

                    '<div class = "mb-3">' +
                    '<label for="startDate" class="form-label">Дата поступления:</label> ' +
                    '<input class="form-control" name="educationInfos[' + eduCounter + '].startDate" type="date" id="startDate" required>' +
                    '</div>' +

                    '<div class = "mb-3">' +
                    '<label for="endDate" class="form-label">Дата окончания:</label> ' +
                    '<input class="form-control" name="educationInfos[' + eduCounter + '].endDate" type="date" id="endDate" required>' +
                    '</div>' +

                    '<div class = "mb-3">' +
                    '<label for="degree" class="form-label">Полученная степень:</label> ' +
                    '<input class="form-control" name="educationInfos[' + eduCounter + '].degree" type="text" id="degree" required>' +
                    '</div>' +
                    newEduRow.appendChild(newEduCol);

                let newEduButton = document.createElement('div');
                newEduButton.className = 'btn remove-button col-3 m-2 d-flex justify-content-center align-items-center bg-danger p-2 bg-opacity-10 rounded-3';
                newEduButton.innerHTML = '<span class="fs-1">-</span>';
                newEduRow.appendChild(newEduButton);

                newEduRow.querySelector('.remove-button').addEventListener('click', () => {
                    if (newEduRow.previousElementSibling.id === 'education') {
                        educationInfo.removeChild(newEduRow);
                        eduCounter--;
                    } else {
                        let warningBlock = document.createElement('div');
                        warningBlock.innerText = 'Вы можете удалять только последний блок';
                        warningBlock.className = 'text-danger';
                        newEduRow.appendChild(warningBlock);

                        setTimeout(() => {
                            warningBlock.remove();
                        }, 3000)
                    }
                });
            });

            // contactsButton.addEventListener('click', () => {
            //     conCounter++;
            //     console.log(conCounter);
            //     let newConRow = document.createElement('div');
            //     newConRow.className = 'row mb-3 row-cols-1 row-cols-lg-2';
            //     contacts.after(newConRow);
            //
            //     let newConCol = document.createElement('div');
            //     newConCol.className = 'col';
            //     newConCol.innerHTML =
            //         '<div class="d-flex">' +
            //         '<input name="contacts[' + conCounter + '].telegram" type="text" class="form-control me-2 my-3" id="telegram">' +
            //         '<i class="bi bi-telegram align-content-center fs-2"></i>' +
            //         '</div>' +
            //
            //         '<div class="d-flex">' +
            //         '<input name="contacts[' + conCounter + '].email" type="email" class="form-control me-2 my-3" id="email" placeholder="example@mail.com">' +
            //         '<i class="bi bi-envelope-at align-content-center fs-2"></i>' +
            //         '</div>' +
            //
            //         '<div class="d-flex">' +
            //         '<input name="contacts[' + conCounter + '].phoneNumber" type="text" class="form-control me-2 my-3" id="phoneNumber" placeholder="996-779-11-22-33">' +
            //         '<i class="bi bi-telephone-inbound align-content-center fs-2"></i>' +
            //         '</div>';
            //     newConRow.appendChild(newConCol);
            //
            //     let newConCol2 = document.createElement('div');
            //     newConCol2.className = 'col';
            //     newConCol2.innerHTML =
            //         '<div class="d-flex">' +
            //         '<input name="contacts[' + conCounter + '].facebook" type="text" class="form-control me-2 my-3"  id="facebook">' +
            //         '<i class="bi bi-facebook align-content-center fs-2"></i>' +
            //         '</div>' +
            //
            //         '<div class="d-flex">' +
            //         '<input name="contacts[' + conCounter + '].linkedIn" type="text" class="form-control me-2 my-3" id="linkedIn">' +
            //         '<i class="bi bi-linkedin align-content-center fs-2"></i>' +
            //         '</div>';
            //     newConRow.appendChild(newConCol2);
            //
            //     let newConButton = document.createElement('div');
            //     newConButton.className = 'btn remove-button d-flex me-4 justify-content-center align-items-center bg-danger p-2 bg-opacity-10 rounded-3';
            //     newConButton.innerHTML = '<span class="fs-1">-</span>';
            //     newConCol2.appendChild(newConButton);
            //
            //     newConRow.querySelector('.remove-button').addEventListener('click', () => {
            //         if (newConRow.previousElementSibling.id === 'contacts') {
            //             contactsInfo.removeChild(newConRow);
            //             conCounter--;
            //         } else {
            //             let warningBlock = document.createElement('div');
            //             warningBlock.innerText = 'Вы можете удалять только последний блок';
            //             warningBlock.className = 'text-danger';
            //             newConRow.appendChild(warningBlock);
            //
            //             setTimeout(() => {
            //                 warningBlock.remove();
            //             }, 3000)
            //         }
            //     });
            //
            // });

        });
    </script>

</@main.layout>