<#import  "../layout.ftlh" as main>
<@main.layout>

    <div class="w-75 px-5 py-3 my-4 mx-auto border border-2 border-black rounded-4">
        <#if user.accountType == "EMPLOYEE">
            <h1 class="text-center my-3 text-danger">Личный кабинер Соискателя</h1>
        <#else>
            <h1 class="text-center my-3 text-danger">Личный кабинер Работодателя</h1>
        </#if>
        <div class="d-flex justify-content-between mt-5">
            <div class="row row-cols-1 row-cols-lg-2 g-3">
                <div class="col">
                    <div class="row">
                        <div class="col border border-black border-2 rounded-4 d-flex align-items-center">
                            <img src="/api/users/avatar/${user.id}" alt="avatar" class="card-img">
                        </div>
                        <div class="col ms-3">
                            <h2>${user.name}</h2>
                            <p>Возраст: ${user.age}</p>
                            <button type="button"
                                    class="btn border-black border-2 bg-success p-2 text-dark bg-opacity-10"><a
                                        href="/users/update" class="text-dark text-decoration-none">Редактировать</a>
                            </button>
                        </div>
                    </div>
                </div>

                <div class="col d-flex justify-content-end">
                    <div class="row">
                        <#if user.accountType == "EMPLOYEE">
                            <div class="col">
                                <button type="button"
                                        class="btn border-black border-2 w-75 h-100 rounded-4 bg-danger p-2 text-dark bg-opacity-10">
                                    <a href="/employee/resumes/add" class="text-dark text-decoration-none">+ <p>Создать
                                            резюме</p></a>
                                </button>
                            </div>

                            <div class="col">
                                <button type="button"
                                        class="btn border-black border-2 w-100 h-100 rounded-4 position-relative">
                                    <a href="/api/employee/responses/users/${user.id}"
                                       class="text-dark text-decoration-none">Отклики </a>
                                    <#if responsesQuantity gt 0>
                                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                                        ${responsesQuantity}
                                        <span class="visually-hidden">unread messages</span>
                                        </span>
                                    </#if>
                                </button>
                            </div>
                        <#else>
                            <div class="col">
                                <button type="button"
                                        class="btn border-black border-2 w-75 h-100 rounded-4 bg-danger p-2 text-dark bg-opacity-10">
                                    <a href="/employer/vacancies/add" class="text-dark text-decoration-none">+ <p>
                                            Создать
                                            вакансию</p></a>
                                </button>
                            </div>

                            <div class="col">
                                <button type="button"
                                        class="btn border-black border-2 w-100 h-100 rounded-4 position-relative">
                                    <a href="/employer/resumes"
                                       class="text-dark text-decoration-none">Резюме </a>
                                </button>
                            </div>
                        </#if>

                    </div>
                </div>
            </div>
        </div>


        <div class="my-4">
            <#if user.accountType == "EMPLOYEE">
                <h2>Резюме:</h2>
            <#else>
                <h2>Созданные вакансии:</h2>
            </#if>
            <#if userMainItems??>
                <div class="row row-cols-1 row-cols-lg-3 g-4 mt-1">
                    <#list userMainItems as item>
                        <div class="col card-group">
                            <div class="card border-black border-2 rounded-4 p-3">
                                <div class="card-body">
                                    <#if user.accountType == "EMPLOYEE">
                                    <a href="/employee/resumes/${item.id}" class="text-decoration-none text-dark">
                                        <#else>
                                        <a href="/vacancies/${item.id}" class="text-decoration-none text-dark">
                                            </#if>
                                            <h5 class="card-title mb-4">${item.name}</h5>
                                            <p class="card-text mb-1">Дата обновления:</p>
                                            <p>${item.timestamp.toLocalDate()}</p>
                                        </a>
                                    </a>
                                </div>

                                <div class="card-footer bg-white">
                                    <#if user.accountType == "EMPLOYEE">
                                        <button type="button"
                                                class="btn border-black border-1 bg-success p-2 text-dark bg-opacity-10">
                                            <a
                                                    href="/employee/resumes/update/${item.id}"
                                                    class="text-dark text-decoration-none">Редактировать</a></button>
                                    <#else>
                                        <button type="button"
                                                class="btn border-black border-1 bg-success p-2 text-dark bg-opacity-10">
                                            <a
                                                    href="/employer/vacancies/update/${item.id}"
                                                    class="text-dark text-decoration-none">Редактировать</a></button>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </#list>
                </div>
            </#if>

        </div>
    </div>


</@main.layout>