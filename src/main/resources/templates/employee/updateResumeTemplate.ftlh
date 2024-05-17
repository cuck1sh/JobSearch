<#import  "../layout.ftlh" as main>
<@main.layout ; spring>

    <div class="col-12 col-lg-6 p-4 mx-auto border rounded-2 mb-4">
        <h1 class="text-center mt-2 mb-3 text-danger">Редактирование резюме:</h1>
        <form action="/employee/resumes/update" method="post" id="form">

            <@spring.formInput "inputResumeDto.id" 'id="id" class="form-control" ', 'hidden'/>
            <@spring.formInput "inputResumeDto.userEmail" 'id="userEmail" class="form-control" ', 'hidden'/>

            <div class="row">
                <div class="col-7">

                    <div class="mb-3">
                        <label for="name" class="form-label">Название резюме:</label>
                        <@spring.formInput "inputResumeDto.name" 'id="name" class="form-control" placeholder="Введите название" required'/>
                        <@spring.showErrors "<br>" "error text-danger" />
                    </div>

                    <div class="mb-3">

                        <label for="category" class="form-label">Выберите категорию:</label>
                        <@spring.formSingleSelect "inputResumeDto.category",
                        categories,
                        "id='category' class='form-select' required"/>
                    </div>

                    <div class="mb-3">
                        <label for="salary" class="form-label">Ожидаемый уровень оклада</label>
                        <@spring.formInput "inputResumeDto.salary" 'id="salary" class="form-control" placeholder="Введите оклад"'/>
                        <@spring.showErrors "<br>" "error text-danger" />
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
                            class="btn col-3 m-2 text-decoration-none bg-success p-2 text-dark bg-opacity-10 rounded-3">
                        <span class="fs-1">+</span>
                    </button>
                </div>

                <#if inputResumeDto.workExperienceInfoDtos??>
                    <#list (inputResumeDto.workExperienceInfoDtos?size - 1)..0 as i>
                        <div class="row py-2 mb-3 mx-0 workExp-row">
                            <div class="col-7 pt-2 bg-success text-dark bg-opacity-10 rounded-3">
                                <@spring.formInput "inputResumeDto.workExperienceInfoDtos["+i+"].id" 'id="workExp" class="form-control workExp-row" ', 'hidden'/>

                                <div class="mb-3">
                                    <label for="years" class="form-label">Годы работы:</label>
                                    <@spring.formInput "inputResumeDto.workExperienceInfoDtos["+i+"].years" 'id="years" class="form-control" placeholder="стаж" required'/>
                                    <@spring.showErrors "<br>" "error text-danger" />
                                </div>

                                <div class="mb-3">
                                    <label for="companyName" class="form-label">Название компании:</label>
                                    <@spring.formInput "inputResumeDto.workExperienceInfoDtos["+i+"].companyName" 'id="companyName" class="form-control" placeholder="Введите название" required'/>
                                    <@spring.showErrors "<br>" "error text-danger" />
                                </div>

                                <div class="mb-3">
                                    <label for="position" class="form-label">Должность:</label>
                                    <@spring.formInput "inputResumeDto.workExperienceInfoDtos["+i+"].position" 'id="position" class="form-control" required'/>
                                    <@spring.showErrors "<br>" "error text-danger" />
                                </div>

                                <div class="mb-3">
                                    <label for="responsibilities" class="form-label">Обязанности:</label>
                                    <@spring.formInput "inputResumeDto.workExperienceInfoDtos["+i+"].responsibilities" 'id="responsibilities" class="form-control" required'/>
                                    <@spring.showErrors "<br>" "error text-danger" />
                                </div>
                            </div>

                            <div id="${inputResumeDto.workExperienceInfoDtos[i].id}"
                                 class="btn remove-button col-3 m-2 d-flex justify-content-center align-items-center bg-danger p-2 bg-opacity-10 rounded-3"
                                 onclick=deleteExpRow(this)
                            >
                                <span class="fs-1">-</span>
                            </div>

                        </div>
                    </#list>
                </#if>
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

                <#if inputResumeDto.educationInfos??>
                    <#list (inputResumeDto.educationInfos?size - 1)..0 as i>
                        <div class="row py-2 mb-3 mx-0 eduInfo-row">
                            <div class="col-7 pt-2 bg-info text-dark bg-opacity-10 rounded-3">
                                <div class="mb-3">
                                    <label for="institution" class="form-label">Название образовательного
                                        учреждения:</label>
                                    <@spring.formInput "inputResumeDto.educationInfos["+i+"].institution" 'id="institution" class="form-control" placeholder="Введите название" required'/>
                                    <@spring.showErrors "<br>" "error text-danger" />
                                </div>

                                <div class="mb-3">
                                    <label for="program" class="form-label">Программа обучения:</label>
                                    <@spring.formInput "inputResumeDto.educationInfos["+i+"].program" 'id="program" class="form-control" required'/>
                                    <@spring.showErrors "<br>" "error text-danger" />
                                </div>

                                <div class="mb-3">
                                    <label for="startDate" class="form-label">Дата поступления:</label>
                                    <@spring.formInput "inputResumeDto.educationInfos["+i+"].startDate" 'id="startDate" class="form-control" required'/>
                                    <@spring.showErrors "<br>" "error text-danger" />
                                </div>

                                <div class="mb-3">
                                    <label for="endDate" class="form-label">Дата окончания:</label>
                                    <@spring.formInput "inputResumeDto.educationInfos["+i+"].endDate" 'id="endDate" class="form-control" required'/>
                                    <@spring.showErrors "<br>" "error text-danger" />
                                </div>

                                <div class="mb-3">
                                    <label for="degree" class="form-label">Полученная степень:</label>
                                    <@spring.formInput "inputResumeDto.educationInfos["+i+"].degree" 'id="degree" class="form-control"'/>
                                </div>
                            </div>
                            <@spring.formInput "inputResumeDto.educationInfos["+i+"].id" 'id="workExp" class="form-control edu-info-id" ', 'hidden'/>
                            <div id="${inputResumeDto.educationInfos[i].id}"
                                 class="btn remove-button col-3 m-2 d-flex justify-content-center align-items-center bg-danger p-2 bg-opacity-10 rounded-3"
                                 onclick=deleteEduRow(this)
                            >
                                <span class="fs-1">-</span>
                            </div>
                        </div>
                    </#list>
                </#if>


            </div>


            <h4 class="">Ссылки на соц. сети:</h4>

            <div class="row mb-3 row-cols-1 row-cols-lg-2">
                <div class="col">

                    <div class="d-flex">
                        <@spring.formInput "inputResumeDto.contacts.telegram" 'id="telegram" class="form-control me-2 my-3"'/>
                        <@spring.showErrors "<br>" "error text-danger" />
                        <i class="bi bi-telegram align-content-center fs-2"></i>
                    </div>

                    <div class="d-flex">
                        <@spring.formInput "inputResumeDto.contacts.email" 'id="email" class="form-control me-2 my-3" placeholder="example@mail.com"' 'email'/>
                        <@spring.showErrors "<br>" "error text-danger" />
                        <i class="bi bi-envelope-at align-content-center fs-2"></i>
                    </div>

                    <div class="d-flex">
                        <@spring.formInput "inputResumeDto.contacts.phoneNumber" 'id="phoneNumber" class="form-control me-2 my-3" placeholder="996-779-11-22-33"'/>
                        <@spring.showErrors "<br>" "error text-danger" />
                        <i class="bi bi-telephone-inbound align-content-center fs-2"></i>
                    </div>
                </div>

                <div class="col">
                    <div class="d-flex">
                        <@spring.formInput "inputResumeDto.contacts.facebook" 'id="facebook" class="form-control me-2 my-3"'/>
                        <@spring.showErrors "<br>" "error text-danger" />
                        <i class="bi bi-facebook align-content-center fs-2"></i>
                    </div>

                    <div class="d-flex">
                        <@spring.formInput "inputResumeDto.contacts.linkedIn" 'id="linkedIn" class="form-control me-2 my-3"'/>
                        <@spring.showErrors "<br>" "error text-danger" />
                        <i class="bi bi-linkedin align-content-center fs-2"></i>
                    </div>
                </div>
            </div>

            <div class="form-check mb-3">
                <@spring.formCheckbox "inputResumeDto.isActive", 'class="form-check-input" id="isActive"'/>
                <label class="form-check-label" for="isActive">
                    Активно
                </label>
            </div>

            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                       value="${(_csrf.token)!'csrf-token'}"/>
            </#if>

            <div class="d-grid gap-2">
                <button type="submit" class="btn bg-success border p-2 text-dark bg-opacity-10 rounded-3">Опубликовать
                </button>
            </div>
        </form>
    </div>

    <script src="/static/js/resumeUpdating.js"></script>

</@main.layout>