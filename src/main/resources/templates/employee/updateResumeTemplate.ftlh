<#import  "../layout.ftlh" as main>
<@main.layout>

    <div class="col-12 col-lg-6 p-4 mx-auto border rounded-2 mb-4">
        <h1 class="text-center mt-2 mb-3 text-danger">Редактирование резюме:</h1>
        <form action="/employee/resumes/add" method="post">

            <input type="hidden" name="id" value=${resume.id}>

            <div class="row">
                <div class="col-7">

                    <div class="mb-3">
                        <label for="name" class="form-label">Название резюме:</label>
                        <input name="name" type="text"
                                <#if resume.name??>
                                    value="${resume.name}"
                                </#if>
                               class="form-control" id="name" placeholder="Введите название"
                               required>
                    </div>

                    <div class="mb-3">
                        <label for="category" class="form-label">Категория:</label>
                        <select name="category" id="category" class="form-select">
                            <option selected
                                    <#if resume.category??>
                                    value="${resume.category.id}">${resume.category.name}
                                <#else> value="0">Выберите категорию
                                </#if></option>
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

            <div class="row mb-3">
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

                <a href="/employee/resume/add/workExp/${resume.id}"
                   class="col-3 m-2 d-flex justify-content-center align-items-center text-decoration-none object-fit-cover border bg-success p-2 text-dark bg-opacity-10 rounded-3">
                    <span class="fs-1">+</span>
                </a>
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

                <a href="/employee/resume/add/education/${resume.id}"
                   class="col-3 m-2 d-flex justify-content-center align-items-center text-decoration-none object-fit-cover border bg-info p-2 text-dark bg-opacity-10 rounded-3">
                    <span class="fs-1">+</span>
                </a>
            </div>

            <h4 class="">Ссылки на соц. сети:</h4>

            <div class="row mb-3 row-cols-1 row-cols-lg-2">
                <div class="col">

                    <div class="d-flex">
                        <input name="telegram" value type="text" class="form-control me-2 my-3" id="telegram">
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
                <input class="form-check-input" name="isActive" type="checkbox" value=true id="isActive"
                       <#if resume.isActive == true>checked</#if>>
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

</@main.layout>