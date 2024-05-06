<#import  "../layout.ftlh" as main>
<@main.layout>


    <div class="card text-center mb-4">
        <div class="card-header">
            <h1>${vacancy.name}</h1>
        </div>
        <div class="card-body">

            <div class="col-sm-6 my-4 mb-sm-0 mx-auto">
                <table class="table text-start table-striped">
                    <tbody>
                    <tr>
                        <th scope="row">Описание:</th>
                        <td><#if vacancy.description??>${vacancy.description}<#else> - </#if></td>
                    </tr>

                    <tr>
                        <th scope="row">Родительская категория:</th>
                        <td>
                            <#if vacancy.category??>
                                <#if vacancy.category.parent??> ${vacancy.category.parent} <#else> - </#if>
                            <#else>
                                N/A
                            </#if>
                        </td>
                    </tr>

                    <tr>
                        <th scope="row">Категория:</th>
                        <td>
                            <#if vacancy.category??>
                                <#if vacancy.category.name??> ${vacancy.category.name} <#else> - </#if>
                            <#else>
                                N/A
                            </#if>
                        </td>
                    </tr>

                    <tr>
                        <th scope="row">Заработная плата:</th>
                        <td><#if vacancy.salary??> ${vacancy.salary} <#else> - </#if> </td>
                    </tr>

                    <tr>
                        <th scope="row">Опыт от:</th>
                        <td><#if vacancy.expFrom??> ${vacancy.expFrom} (лет)<#else> - </#if> </td>
                    </tr>

                    <tr>
                        <th scope="row">Опыт до:</th>
                        <td><#if vacancy.expTo??> ${vacancy.expTo} (лет)<#else> - </#if> </td>
                    </tr>

                    <tr>
                        <th scope="row">Профиль автора:</th>
                        <td class="pt-2"><a href="/users/profile/${vacancy.userEmail}"
                                            class="text-decoration-none border border-primary rounded-4 p-1">${vacancy.userEmail}</a>
                        </td>
                    </tr>

                    <tr>
                        <th scope="row">Активно:</th>
                        <td><#if vacancy.isActive??> ${vacancy.isActive?string('Да', 'Нет')} <#else> - </#if> </td>
                    </tr>

                    <tr>
                        <th scope="row">Дата создания:</th>
                        <td>${vacancy.createdDate.toLocalDate()}</td>
                    </tr>

                    </tbody>
                </table>
            </div>
        </div>

        <#if resumes??>
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-success mx-auto mb-4" data-bs-toggle="modal"
                    data-bs-target="#exampleModal">
                Откликнуться
            </button>



            <!-- Modal -->
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="exampleModalLabel">Отклики на вакансии</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <#if resumes?size gt 0>
                                <ol class="list-group list-group-numbered">
                                    <#list resumes as resume>
                                        <div id="resumeId" hidden="hidden"></div>
                                        <li class="list-group-item d-flex justify-content-between align-items-start">
                                            <div class="ms-2 me-auto text-start">
                                                <div class="fw-bold text-start">${resume.name}</div>
                                                <#if resume.category??>
                                                    <#if resume.category.name??>
                                                        <p class="mb-2">
                                                            Категория: ${resume.category.name}
                                                        </p>
                                                    </#if>
                                                </#if>
                                                Зарплата: ${resume.salary}
                                            </div>
                                            <button type="button"
                                                    class="btn btn-success my-auto"
                                                    onclick="responseVacancy(${vacancy.id}, ${resume.id})">
                                                Прикрепить
                                            </button>
                                        </li>
                                    </#list>
                                </ol>
                            <#else>
                                У вас еще нет откликов на вакансии
                            </#if>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                Close
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </#if>


        <div class="card-footer text-body-secondary">
            Дата обновления ${vacancy.updateTime.toLocalDate()}
        </div>
    </div>

    <script>
        function responseVacancy(vacancyId, resumeId) {
            const url = '/api/responses/vacancy/' + vacancyId + '/resume/' + resumeId
            fetch(url, {method: 'get'})
                .then(resp => {
                    return resp.json();
                })
                .then(data => {
                    window.location.href = '/messages/' + data

                })
                .catch(e => console.log(e))
        }

    </script>
</@main.layout>