<#import  "../layout.ftlh" as main>
<@main.layout ; spring>

    <div class="col-12 col-lg-6 p-4 mx-auto border rounded-2 mb-4">
        <h1 class="text-center mt-2 mb-3 text-danger">Создание вакансии:</h1>
        <form action="/employer/vacancies/add" method="post">

            <div class="row">
                <div class="col-7">

                    <div class="mb-3">
                        <label for="name" class="form-label">Название вакансии:</label>
                        <@spring.formInput "inputVacancyDto.name" 'id="name" class="form-control" placeholder="Введите название" required'/>
                        <@spring.showErrors "<br>" "error text-danger" />
                    </div>

                    <div class="mb-3">
                        <label for="category" class="form-label">Выберите категорию:</label>
                        <@spring.formSingleSelect "inputVacancyDto.category",
                        categories,
                        "id='category' class='form-select' required"/>
                    </div>

                    <div class="mb-3">
                        <label for="salary" class="form-label">Заработная плата</label>
                        <@spring.formInput "inputVacancyDto.salary" 'id="salary" class="form-control" placeholder="Введите оклад"'/>
                        <@spring.showErrors "<br>" "error text-danger" />
                    </div>

                    <div class="mb-3">
                        <label for="description" class="form-label">Описание вакансии</label>
                        <@spring.formTextarea "inputVacancyDto.description" 'id="description" class="form-control" rows="3"'/>
                        <@spring.showErrors "<br>" "error text-danger" />
                    </div>

                    <div class="mb-3">
                        <P class="mb-2">Требуемый опыт:</P>
                        <div class="row mb-1">
                            <label for="expFrom" class="col-2 col-form-label pe-0">от</label>
                            <div class="col-3 ps-0">
                                <@spring.formInput "inputVacancyDto.expFrom" 'id="expFrom" class="form-control"'/>
                                <@spring.showErrors "<br>" "error text-danger" />
                            </div>
                            <label for="expTo" class="col-2 col-form-label  pe-0">до</label>
                            <div class="col-3 ps-0">
                                <@spring.formInput "inputVacancyDto.expTo" 'id="expTo" class="form-control"'/>
                                <@spring.showErrors "<br>" "error text-danger" />
                            </div>
                        </div>
                        <#if expErr??>
                            <p class="text-danger">число в поле “от” не может быть больше числа в поле “до”</p>
                        </#if>
                    </div>
                </div>
            </div>


            <div class="form-check my-4">
                <@spring.formCheckbox "inputVacancyDto.isActive", 'class="form-check-input" id="isActive"'/>
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

</@main.layout>