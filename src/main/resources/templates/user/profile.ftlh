<#import  "../layout.ftlh" as main>
<@main.layout>

    <div class="w-75 p-5 mx-auto border border-3 rounded-2">
        <#if user.accountType == "EMPLOYEE">
            <h1 class="text-center my-3 text-danger">Личный кабинер Соискателя</h1>
        <#else>
            <h1 class="text-center my-3 text-danger">Личный кабинер Работодателя</h1>
        </#if>
        <div class="d-flex justify-content-between mt-5">
            <div class="row">
                <div class="col border border-2">
                    <img src="../../../../../data/images/default.png" alt="avatar">
                </div>
                <div class="col ms-3">
                    <h2>${user.name}</h2>
                    <p>Возраст: ${user.age}</p>
                    <button type="button" class="btn btn-outline-success border-4"><a
                                href="/users/update">Редактировать</a>
                    </button>
                </div>
            </div>

            <div class="row">

                <#if user.accountType == "EMPLOYEE">
                    <div class="col">
                        <button type="button" class="btn btn-outline-danger border-4 w-75 h-100 rounded-4"><a
                                    href="/employee/resumes/add"></a>+
                            <p>Создать резюме</p></button>
                    </div>
                    <div class="col">
                        <button type="button" class="btn btn-outline-dark border-4 w-100 h-100 rounded-4"><a
                                    href="/api/employee/responses/users/${user.id}">Отклики: ${responsesQuantity}</a>
                        </button>
                    </div>
                <#else>
                    <div class="col">
                        <button type="button" class="btn btn-outline-danger border-4 w-75 h-100 rounded-4"><a
                                    href="/employer/vacancies/add"></a>+
                            <p>Создать вакансию</p></button>
                    </div>
                </#if>
            </div>
        </div>
        <div class="my-4">
            <#if user.accountType == "EMPLOYEE">
                <h2>Резюме:</h2>
            <#else>
                <h2>Вакансии:</h2>
            </#if>
            <div class="row row-cols-1 row-cols-md-3 g-4 mt-3">
                <#list userMainItems as item>
                    <div class="col">
                        <#if user.accountType == "EMPLOYEE">
                        <a href="/employee/resumes/${item.id}">
                            <#else>
                            <a href="/employer/vacancies/${item.id}">
                                </#if>
                                <div class="card border-2 rounded-2 p-3">
                                    <h5 class="card-title">${item.name}</h5>
                                    <p class="card-text">Дата обновления</p>
                                    <p>${item.timestamp}</p>
                                    <div class="card-footer">
                                        <#if user.accountType == "EMPLOYEE">
                                            <button type="button" class="btn btn-outline-success border-4"><a
                                                        href="/employee/resumes/update">Редактировать</a></button>
                                        <#else>
                                            <button type="button" class="btn btn-outline-success border-4"><a
                                                        href="/employer/vacancies/update">Редактировать</a></button>
                                        </#if>
                                    </div>
                                </div>
                            </a>
                    </div>
                </#list>
            </div>

        </div>
    </div>


</@main.layout>