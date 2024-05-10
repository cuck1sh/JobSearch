<#import  "../layout.ftlh" as main>
<@main.layout ; spring>

    <div class="col-12 col-lg-6 p-4 mx-auto border rounded-2 mb-4">
        <h1 class="text-center mt-2 mb-3 text-danger">Создание вакансии:</h1>
        <form action="/employer/vacancies/add" method="post">

            <div class="row">
                <div class="col-7">

                    <div class="mb-3">
                        <label for="name" class="form-label">Название вакансии:</label>
                        <#--                        <input name="name" type="text" class="form-control" id="name" placeholder="Введите название"-->
                        <#--                               required>-->
                        <@spring.formInput "inputVacancyDto.name" 'id="name" class="form-control" placeholder="Введите название" required'/>
                        <@spring.showErrors "<br>" "error text-danger" />
                    </div>

                    <div class="mb-3">
                        <#--                        <label for="category" class="form-label">Категория:</label>-->
                        <#--                        <select name="category" id="category" class="form-select">-->
                        <#--                            <option selected>Выберите категорию</option>-->
                        <#--                            <#list categories as category>-->
                        <#--                                <option value=${category.id}>${category.name}</option>-->
                        <#--                            </#list>-->
                        <#--                        </select>-->

                        <label for="category" class="form-label">Выберите категорию:</label>
                        <@spring.formSingleSelect "inputVacancyDto.category",
                        categories,
                        "id='category' class='form-select' required"/>
                    </div>

                    <div class="mb-3">
                        <label for="salary" class="form-label">Заработная плата</label>
                        <#--                        <input name="salary" type="number" class="form-control" id="salary" placeholder="Введите оклад">-->
                        <@spring.formInput "inputVacancyDto.salary" 'id="salary" class="form-control" placeholder="Введите оклад"'/>
                        <@spring.showErrors "<br>" "error text-danger" />
                    </div>

                    <div class="mb-3">
                        <label for="description" class="form-label">Описание вакансии</label>
                        <#--                        <textarea class="form-control" name="description" id="description" rows="3"></textarea>-->
                        <@spring.formTextarea "inputVacancyDto.description" 'id="description" class="form-control" rows="3"'/>
                        <@spring.showErrors "<br>" "error text-danger" />
                    </div>

                    <div class="mb-3">
                        <P class="mb-2">Требуемый опыт:</P>
                        <div class="row mb-1">
                            <label for="expFrom" class="col-2 col-form-label pe-0">от</label>
                            <div class="col-3 ps-0">
                                <#--                                <input type="number" name="expFrom" class="form-control" id="expFrom">-->
                                <@spring.formInput "inputVacancyDto.expFrom" 'id="expFrom" class="form-control"'/>
                                <@spring.showErrors "<br>" "error text-danger" />
                            </div>
                            <label for="expTo" class="col-2 col-form-label  pe-0">до</label>
                            <div class="col-3 ps-0">
                                <#--                                <input type="number" name="expTo" class="form-control" id="expTo">-->
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
                <#--                <input class="form-check-input" name="isActive" type="checkbox" value=true id="isActive">-->
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