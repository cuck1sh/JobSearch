<#import  "../layout.ftlh" as main>
<@main.layout ; spring>


    <div class="card text-center mb-4">
        <div class="card-header">
            <h1>${vacancy.name}</h1>
        </div>
        <div class="card-body">

            <div class="col-sm-6 my-4 mb-sm-0 mx-auto">
                <table class="table text-start table-striped">
                    <tbody>
                    <tr>
                        <th scope="row"><@spring.message 'vacancy.description'/></th>
                        <td><#if vacancy.description??>${vacancy.description}<#else> - </#if></td>
                    </tr>

                    <tr>
                        <th scope="row"><@spring.message 'vacancy.parent.category'/></th>
                        <td>
                            <#if vacancy.category??>
                                <#if vacancy.category.parent??> ${vacancy.category.parent} <#else> - </#if>
                            <#else>
                                N/A
                            </#if>
                        </td>
                    </tr>

                    <tr>
                        <th scope="row"><@spring.message 'vacancy.category'/></th>
                        <td>
                            <#if vacancy.category??>
                                <#if vacancy.category.name??> ${vacancy.category.name} <#else> - </#if>
                            <#else>
                                N/A
                            </#if>
                        </td>
                    </tr>

                    <tr>
                        <th scope="row"><@spring.message 'vacancy.salary'/></th>
                        <td><#if vacancy.salary??> ${vacancy.salary} <#else> - </#if> </td>
                    </tr>

                    <tr>
                        <th scope="row"><@spring.message 'vacancy.expFrom'/></th>
                        <td><#if vacancy.expFrom??> ${vacancy.expFrom} (<@spring.message 'vacancy.years'/>)<#else> - </#if> </td>
                    </tr>

                    <tr>
                        <th scope="row"><@spring.message 'vacancy.expTo'/></th>
                        <td><#if vacancy.expTo??> ${vacancy.expTo} (<@spring.message 'vacancy.years'/>)<#else> - </#if> </td>
                    </tr>

                    <tr>
                        <th scope="row"><@spring.message 'vacancy.profile'/></th>
                        <td class="pt-2"><a href="/users/profile/${vacancy.userEmail}"
                                            class="text-decoration-none border border-primary rounded-4 p-1">${vacancy.userEmail}</a>
                        </td>
                    </tr>

                    <tr>
                        <th scope="row"><@spring.message 'vacancy.active'/></th>
                        <td><#if vacancy.isActive??> ${vacancy.isActive?string('Yes', 'No')} <#else> - </#if> </td>
                    </tr>

                    <tr>
                        <th scope="row"><@spring.message 'vacancy.creation.date'/></th>
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
                <@spring.message 'vacancy.response'/>
            </button>

            <!-- Modal -->
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5"
                                id="exampleModalLabel"><@spring.message 'vacancy.responses.title'/></h1>
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
                                                <div class="fw-bold text-start mb-2">${resume.name}</div>
                                                <#if resume.category??>
                                                    <#if resume.category.name??>
                                                        <p class="mb-1">
                                                            <@spring.message 'vacancy.responses.category'/> ${resume.category.name}
                                                        </p>
                                                    </#if>
                                                </#if>
                                                <#if resume.salary??>
                                                    <@spring.message 'vacancy.responses.salary'/> ${resume.salary}
                                                <#else>
                                                    <@spring.message 'vacancy.responses.salary'/> -
                                                </#if>

                                            </div>
                                            <button type="button"
                                                    class="btn btn-success my-auto"
                                                    onclick="responseVacancy(${vacancy.id}, ${resume.id})">
                                                <@spring.message 'vacancy.responses.pin'/>
                                            </button>
                                        </li>
                                    </#list>
                                </ol>
                            <#else>
                                <@spring.message 'vacancy.no.resumes'/>
                            </#if>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                <@spring.message 'vacancy.close.button'/>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </#if>

        <#if responses??>
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-success mx-auto mb-4 position-relative" data-bs-toggle="modal"
                    data-bs-target="#exampleModal">
                <@spring.message 'vacancy.responses'/>

                <#if responsesQty gt 0>
                    <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                        ${responsesQty}
                        <span class="visually-hidden">unread messages</span>
                    </span>
                </#if>
            </button>

            <!-- Modal -->
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5"
                                id="exampleModalLabel"><@spring.message 'vacancy.responses.title2'/></h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <#if responses?size gt 0>
                                <ol class="list-group list-group-numbered">
                                    <#list responses as response>
                                        <li class="list-group-item d-flex justify-content-between align-items-start">
                                            <div class="ms-2 me-auto text-start">
                                                <div class="fw-bold">
                                                    <a href="/users/profile/${response.resume.userEmail}"
                                                       class="text-dark">${response.resume.userEmail}</a>
                                                </div>
                                                <p class="mb-2"><@spring.message 'vacancy.resumes'/>
                                                    <a href="/resumes/${response.resume.id}"
                                                       class="text-dark">${response.resume.name}</a>
                                                </p>
                                                <#if response.resume.salary??>
                                                    <@spring.message 'vacancy.responses.salary'/> ${response.resume.salary}
                                                <#else>
                                                    <@spring.message 'vacancy.responses.salary'/> -
                                                </#if>

                                            </div>

                                            <span class="badge text-bg-primary rounded-pill">
                                                                    <a href="/messages/${response.id}"
                                                                       class="text-white text-decoration-none"><@spring.message 'vacancy.messages'/></a>
                                                                </span>
                                        </li>
                                    </#list>
                                </ol>
                            <#else>
                                <@spring.message 'vacancy.no.responses'/>
                            </#if>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                <@spring.message 'vacancy.close.button'/>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </#if>


        <div class="card-footer text-body-secondary">
            <@spring.message 'vacancy.update.time'/> ${vacancy.updateTime.toLocalDate()}
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