<#import  "../layout.ftlh" as main>
<#import "../pager.ftlh" as p>
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
                            <#if user.accountType == 'EMPLOYEE'>
                                <p>Возраст: ${user.age}</p>
                            </#if>

                            <!-- Button trigger modal -->
                            <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                    data-bs-target="#exampleModal">
                                Написать
                            </button>
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
                <h2>Резюме:</h2>
            <#else>
                <h2>Созданные вакансии:</h2>
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
                                                    href="/employee/resumes/${item.id}"
                                                    class="text-dark text-decoration-none">Посмотреть</a></button>
                                    <#else>
                                        <button type="button"
                                                class="btn border-black border-1 bg-success p-2 text-dark bg-opacity-10">
                                            <a
                                                    href="/vacancies/${item.id}"
                                                    class="text-dark text-decoration-none">Посмотреть</a></button>
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

    <!-- Modal -->
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="form">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">Откликнитесь</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        Список вакансий
                        <#list vacancies as vacancy>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="vacancyId" value="${vacancy.id}"
                                       id="vacancy${vacancy?index}">
                                <label class="form-check-label" for="vacancy${vacancy?index}">
                                    ${vacancy.name}
                                </label>
                            </div>
                        </#list>
                        <hr>
                        Список резюме
                        <#list resumes as resume>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="resumeId" value="${resume.id}"
                                       id="resume${resume?index}">
                                <label class="form-check-label" for="resume${resume?index}">
                                    ${resume.name}
                                </label>
                            </div>
                        </#list>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                        <button type="submit" class="btn btn-primary">Откликнуться</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        'use strict'

        let form = document.getElementById('form')

        form.addEventListener('submit', submitHandler)

        function submitHandler(event) {
            event.preventDefault()
            let data = new FormData(event.target)
            let vacancyId = data.get('vacancyId')
            let resumeId = data.get('resumeId')

            makeRequest('/api/responses/vacancy/' + vacancyId + '/resume/' + resumeId, {method: 'get'})
                .then(responseId => {
                    console.log('RESPONSE ID: ' + responseId)
                    window.location.href = '/messages/' + responseId
                })
                .catch(e => console.log(e))

            async function makeRequest(url, options) {
                let response = await fetch(url, options)

                if (response.ok) {
                    return await response.json()
                } else {
                    let error = new Error(response.status.toString());
                    error.response = response;
                    throw error;
                }
            }
        }

    </script>

</@main.layout>