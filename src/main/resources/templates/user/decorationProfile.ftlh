<#import  "../layout.ftlh" as main>
<#import "../pager.ftlh" as p>
<@main.layout ; spring>

    <div class="w-75 px-5 py-3 my-4 mx-auto border border-2 border-black rounded-4">
        <#if user.accountType == "EMPLOYEE">
            <h1 class="text-center my-3 text-danger"><@spring.message 'profile.employee.title'/></h1>
        <#else>
            <h1 class="text-center my-3 text-danger"><@spring.message 'profile.employer.title'/></h1>
        </#if>
        <div class="mt-5">
            <div class="row row-cols-1 row-cols-lg-2 g-3">
                <div class="col">
                    <div class="row">
                        <div class="col-4 border border-black border-2 rounded-4 d-flex align-items-center py-3">
                            <img src="/api/users/avatar/${user.id}" alt="avatar" class="card-img">
                        </div>
                        <div class="col-7 ms-3">
                            <h2>${user.name}</h2>
                            <#if user.accountType == 'EMPLOYEE'>
                                <p><@spring.message 'profile.age'/> ${user.age}</p>
                            </#if>

                            <#if resumes?? && vacancies??>
                                <!-- Button trigger modal -->
                                <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                        data-bs-target="#exampleModal">
                                    <@spring.message 'decoration.write'/>
                                </button>

                                <!-- Modal -->
                                <div class="modal fade" id="exampleModal" tabindex="-1"
                                     aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <form id="form">
                                                <div class="modal-header">
                                                    <h1 class="modal-title fs-5"
                                                        id="exampleModalLabel"><@spring.message 'decoration.response'/></h1>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <@spring.message 'decoration.vacancies.list'/>
                                                    <#list vacancies as vacancy>
                                                        <div class="form-check">
                                                            <input class="form-check-input" type="radio"
                                                                   name="vacancyId" value="${vacancy.id}"
                                                                   id="vacancy${vacancy?index}">
                                                            <label class="form-check-label"
                                                                   for="vacancy${vacancy?index}">
                                                                ${vacancy.name}
                                                            </label>
                                                        </div>
                                                    </#list>
                                                    <hr>
                                                    <@spring.message 'decoration.resumes.list'/>
                                                    <#list resumes as resume>
                                                        <div class="form-check">
                                                            <input class="form-check-input" type="radio" name="resumeId"
                                                                   value="${resume.id}"
                                                                   id="resume${resume?index}">
                                                            <label class="form-check-label" for="resume${resume?index}">
                                                                ${resume.name}
                                                            </label>
                                                        </div>
                                                    </#list>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary"
                                                            data-bs-dismiss="modal">
                                                        <@spring.message 'decoration.button.close'/>
                                                    </button>
                                                    <button type="submit" class="btn btn-primary">
                                                        <@spring.message 'decoration.button.response'/>
                                                    </button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </#if>

                        </div>
                    </div>
                </div>

                <div class="col d-flex justify-content-center">
                    <div class="row">
                        <div class="d-flex mb-3">
                            <div class="card me-2 w-100 border-black border-2 rounded-5">
                                <div class="card-body align-content-center text-center fw-semibold p-2 fs-4">
                                    <#if user.phoneNumber??>
                                        ${user.phoneNumber}
                                    <#else>
                                        -
                                    </#if>
                                </div>
                            </div>
                            <i class="bi bi-telephone-inbound align-content-center fs-1"></i>
                        </div>

                        <div class="d-flex mb-3">
                            <div class="card me-2 w-100 border-black border-2 rounded-5">
                                <div class="card-body align-content-center text-center fw-semibold p-2 fs-4">
                                    <#if user.email??>
                                        ${user.email}
                                    <#else>
                                        -
                                    </#if>
                                </div>
                            </div>
                            <i class="bi bi-envelope-at align-content-center fs-1"></i>
                        </div>

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
                                    <a href="/resumes/${item.id}" class="text-decoration-none text-dark">
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
                                            <a
                                                    href="/resumes/${item.id}"
                                                    class="text-dark text-decoration-none">
                                                <@spring.message 'decoration.button.check'/>
                                            </a>
                                        </button>
                                    <#else>
                                        <button type="button"
                                                class="btn border-black border-1 bg-success p-2 text-dark bg-opacity-10">
                                            <a
                                                    href="/vacancies/${item.id}"
                                                    class="text-dark text-decoration-none">
                                                <@spring.message 'decoration.button.check'/>
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



    <script src="/static/js/decorationProfile.js"></script>

</@main.layout>