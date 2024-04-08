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

            <div class="row my-3">
                <h2 class="my-2">Контакты:</h2>
                <#list resume.contacts as contact>
                    <div class="col-sm-6 mb-3 mb-sm-0 mx-auto">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">${contact.type.type}</h5>
                                <p class="card-text">Контакт: ${contact.info}</p>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>


        </div>
        <div class="card-footer text-body-secondary">
            Дата обновления ${resume.updateTime}
        </div>

    </div>

</@main.layout>