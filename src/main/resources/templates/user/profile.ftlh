<#import  "../layout.ftlh" as main>
<#import "../pager.ftlh" as p>
<@main.layout ; spring>

    <div class="w-75 px-5 py-3 my-4 mx-auto border border-2 border-black rounded-4">
        <#if user.accountType == "EMPLOYEE">
            <h1 class="text-center my-3 text-danger"><@spring.message 'profile.employee.title'/></h1>
        <#else>
            <h1 class="text-center my-3 text-danger"><@spring.message 'profile.employer.title'/></h1>
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
                            <#if user.accountType == 'EMPLOYEE'>
                                <p><@spring.message 'profile.age'/> ${user.age}</p>
                            </#if>
                            <button type="button"
                                    class="btn border-black border-2 bg-success p-2 text-dark bg-opacity-10"><a
                                        href="/users/update"
                                        class="text-dark text-decoration-none"><@spring.message 'profile.change.button'/></a>
                            </button>
                        </div>
                    </div>
                </div>

                <div class="col d-flex justify-content-end">
                    <div class="row">
                        <#if user.accountType == "EMPLOYEE">
                            <div class="col">
                                <a href="/employee/resumes/add" class="text-dark text-decoration-none">
                                    <button type="button"
                                            class="btn border-black border-2 w-75 h-100 rounded-4 bg-danger p-2 text-dark bg-opacity-10">
                                        +
                                        <p><@spring.message 'profile.employee.create.resume'/></p>
                                    </button>
                                </a>

                            </div>

                            <div class="col">
                                <button type="button"
                                        data-bs-toggle="modal" data-bs-target="#exampleModal"
                                        class="btn border-black border-2 w-100 h-100 rounded-4 position-relative">
                                    <@spring.message 'profile.responses'/>

                                    <#if responsesQuantity gt 0>
                                        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                                        ${responsesQuantity}
                                        <span class="visually-hidden">unread messages</span>
                                        </span>
                                    </#if>
                                </button>
                            </div>

                            <!-- Modal -->
                            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                                 aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5"
                                                id="exampleModalLabel"><@spring.message 'profile.responses.title'/></h1>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <#if responses?size gt 0>
                                                <ol class="list-group list-group-numbered">
                                                    <#list responses as response>
                                                        <li class="list-group-item d-flex justify-content-between align-items-start">
                                                            <div class="ms-2 me-auto">
                                                                <div class="fw-bold">${response.vacancy.companyName}</div>
                                                                <p class="mb-2">
                                                                    <a href="/vacancies/${response.vacancy.id}"
                                                                       class="text-dark">
                                                                        <@spring.message 'profile.responses.vacancy'/> ${response.vacancy.name}
                                                                    </a>
                                                                </p>
                                                                <@spring.message 'profile.responses.resume'/> ${response.resume.name}
                                                            </div>

                                                            <span class="badge text-bg-primary rounded-pill">
                                                                    <a href="/messages/${response.id}"
                                                                       class="text-white text-decoration-none">
                                                                        <@spring.message 'profile.responses.messenger'/>
                                                                    </a>
                                                                </span>
                                                        </li>
                                                    </#list>
                                                </ol>
                                            <#else>
                                                <@spring.message 'profile.no.responses'/>
                                            </#if>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                                <@spring.message 'profile.responses.close'/>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        <#else>
                            <div class="col">
                                <a href="/employer/vacancies/add" class="text-dark text-decoration-none">
                                    <button type="button"
                                            class="btn border-black border-2 w-75 h-100 rounded-4 bg-danger p-2 text-dark bg-opacity-10">
                                        +
                                        <p><@spring.message 'profile.employer.create.vacancy'/></p>
                                    </button>
                                </a>
                            </div>

                            <div class="col">
                                <button type="button"
                                        class="btn border-black border-2 w-100 h-100 rounded-4 position-relative">
                                    <a href="/employer/resumes"
                                       class="text-dark text-decoration-none"><@spring.message 'profile.employer.resumes'/> </a>
                                </button>
                            </div>
                        </#if>

                    </div>
                </div>
            </div>
        </div>


        <div class="my-4">
            <#if user.accountType == "EMPLOYEE">
                <h2><@spring.message 'profile.resumes.title'/></h2>
            <#else>
                <h2><@spring.message 'profile.vacancies.title'/></h2>
            </#if>
            <#if page??>
                <div class="row row-cols-1 row-cols-lg-3 g-4 mt-1">
                    <#list page.content as item>
                        <div class="col card-group">
                            <div class="card border-black border-2 rounded-4 p-3">
                                <div class="card-body">
                                    <#if user.accountType == "EMPLOYEE">
                                    <a href="/employee/resumes/${item.id}" class="text-decoration-none text-dark">
                                        <#else>
                                        <a href="/vacancies/${item.id}" class="text-decoration-none text-dark">
                                            </#if>
                                            <h5 class="card-title mb-4">${item.name}</h5>
                                            <p class="card-text mb-1"><@spring.message 'profile.update.time'/></p>
                                            <p>${item.timestamp.toLocalDate()}</p>
                                        </a>
                                    </a>
                                </div>

                                <div class="card-footer bg-white">
                                    <#if user.accountType == "EMPLOYEE">
                                        <button type="button"
                                                class="btn border-black border-1 bg-success p-2 text-dark bg-opacity-10">
                                            <a href="/employee/resumes/update/${item.id}"
                                               class="text-dark text-decoration-none">
                                                <@spring.message 'profile.change.button'/>
                                            </a>
                                        </button>
                                    <#else>
                                        <button type="button"
                                                class="btn border-black border-1 bg-success p-2 text-dark bg-opacity-10">
                                            <a href="/employer/vacancies/update/${item.id}"
                                               class="text-dark text-decoration-none">
                                                <@spring.message 'profile.change.button'/>
                                            </a>
                                        </button>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </#list>
                </div>

                <@p.pager url page filter sort/>
            </#if>

        </div>
    </div>


</@main.layout>