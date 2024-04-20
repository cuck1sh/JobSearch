<#import  "../layout.ftlh" as main>
<@main.layout>


    <div class="card text-center mb-4">
        <div class="card-header">
            <h1>${resume.name}</h1>
        </div>
        <div class="card-body">
            <h5 class="card-title">Email: ${resume.userEmail}</h5>

            <#if resume.category??>
                <#if resume.category.parent??>
                    <h5 class="card-title">Родительская категория: ${resume.category.parent}</h5>
                </#if>

                <#if resume.category.name??>
                    <h5 class="card-title">Категория: ${resume.category.name}</h5>
                </#if>
            </#if>

            <#if resume.salary??>
                <h5 class="card-title">Заработная плата: ${resume.salary}</h5>
            </#if>

            <#if resume.isActive??>
                <h5 class="card-title">Активно: ${resume.isActive?string('Да', 'Нет')}</h5>
            </#if>

            <h6 class="card-title">Дата создания ${resume.createdDate} </h6>

            <div class="row mt-3">
                <h2 class="my-2">Опыт работы:</h2>
                <#if resume.workExperienceInfos??>
                    <#list resume.workExperienceInfos as exp>
                        <div class="col-sm-6 mb-3 mb-sm-0 mx-auto">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Компания: ${exp.companyName}</h5>
                                    <p class="card-text mb-1">Стаж (годы): ${exp.years}</p>
                                    <p class="card-text mb-1">Позиция: ${exp.position}</p>
                                    <p class="card-text mb-1">Обязанности: ${exp.responsibilities}</p>
                                </div>
                            </div>
                        </div>
                    </#list>
                </#if>
            </div>

            <div class="row mt-3">
                <h2 class="my-2">Образование:</h2>
                <#if resume.educationInfos??>
                    <#list resume.educationInfos as education>
                        <div class="col-sm-6 mb-3 mb-sm-0 mx-auto">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">Учреждение: ${education.institution}</h5>
                                    <p class="card-text mb-1">Программа обучения: ${education.program}</p>
                                    <p class="card-text mb-1">Дата начала: ${education.startDate}</p>
                                    <p class="card-text mb-1">Дата завершения: ${education.endDate}</p>
                                    <#if education.degree??>
                                        <p class="card-text">Степень: ${education.degree}</p>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </#list>
                </#if>
            </div>

            <div class="row d-flex justify-content-center my-3">
                <h2 class="mt-2">Контакты:</h2>
                <div class="row w-75 my-3 row-cols-1 row-cols-lg-2">
                    <div class="col">

                        <div class="d-flex mb-3">
                            <div class="card me-2 w-100">
                                <div class="card-body p-2">
                                    <#if resume.contacts.phoneNumber??>
                                        ${resume.contacts.phoneNumber}
                                    <#else>
                                        N/A
                                    </#if>
                                </div>
                            </div>
                            <i class="bi bi-telephone-inbound align-content-center fs-2"></i>
                        </div>

                        <div class="d-flex mb-3">
                            <div class="card me-2 w-100">
                                <div class="card-body p-2">
                                    <#if resume.contacts.email??>
                                        ${resume.contacts.email}
                                    <#else>
                                        N/A
                                    </#if>
                                </div>
                            </div>
                            <i class="bi bi-envelope-at align-content-center fs-2"></i>
                        </div>

                        <div class="d-flex mb-3">
                            <div class="card me-2 w-100">
                                <div class="card-body p-2">
                                    <#if resume.contacts.telegram??>
                                        ${resume.contacts.telegram}
                                    <#else>
                                        N/A
                                    </#if>
                                </div>
                            </div>
                            <i class="bi bi-telegram align-content-center fs-2"></i>
                        </div>


                    </div>

                    <div class="col">
                        <div class="d-flex mb-3">
                            <div class="card me-2 w-100">
                                <div class="card-body p-2">
                                    <#if resume.contacts.facebook??>
                                        ${resume.contacts.facebook}
                                    <#else>
                                        N/A
                                    </#if>
                                </div>
                            </div>
                            <i class="bi bi-facebook align-content-center fs-2"></i>
                        </div>

                        <div class="d-flex mb-3">
                            <div class="card me-2 w-100">
                                <div class="card-body p-2">
                                    <#if resume.contacts.linkedIn??>
                                        ${resume.contacts.linkedIn}
                                    <#else>
                                        N/A
                                    </#if>
                                </div>
                            </div>
                            <i class="bi bi-linkedin align-content-center fs-2"></i>
                        </div>
                    </div>
                </div>
            </div>


        </div>
        <div class="card-footer text-body-secondary">
            Дата обновления ${resume.updateTime}
        </div>

    </div>

</@main.layout>