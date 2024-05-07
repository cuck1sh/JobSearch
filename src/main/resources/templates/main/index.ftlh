<#import  "../layout.ftlh" as main>
<#import "../pager.ftlh" as p>
<@main.layout>

    <h1 class="text-center text-danger mb-1">Вакансии:</h1>

    <div class="dropdown d-flex justify-content-center m-3">
        <button class="btn border me-2 dropdown-toggle" type="button" data-bs-toggle="dropdown"
                aria-expanded="false">
            SORT: Кол-во откликов
        </button>
        <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="${url}?page=0&size=${page.getSize()}&filter=${filter}">none</a></li>
            <li><a class="dropdown-item"
                   href="${url}?page=0&size=#{page.getSize()}&sort=RespondedApplicantsVacancyId,desc&filter=${filter}">По
                    убыванию</a></li>
            <li><a class="dropdown-item"
                   href="${url}?page=0&size=#{page.getSize()}&sort=RespondedApplicantsVacancyId,asc&filter=${filter}">По
                    возрастанию</a></li>
        </ul>

        <button class="btn border me-2 dropdown-toggle" type="button" data-bs-toggle="dropdown"
                aria-expanded="false">
            SORT: Дата создания
        </button>
        <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="${url}?page=0&size=${page.getSize()}&filter=${filter}">none</a></li>
            <li><a class="dropdown-item"
                   href="${url}?page=0&size=#{page.getSize()}&sort=CreatedDate,desc&filter=${filter}">Новые</a>
            </li>
            <li><a class="dropdown-item"
                   href="${url}?page=0&size=#{page.getSize()}&sort=CreatedDate,asc&filter=${filter}">Старые</a>
            </li>
        </ul>

        <button class="btn border dropdown-toggle" type="button" data-bs-toggle="dropdown"
                aria-expanded="false">
            Выберите категорию
        </button>
        <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="${url}?page=0&size=#{page.getSize()}&sort=${sort}">none</a></li>
            <#list categories as category>
                <li><a class="dropdown-item"
                       href="${url}?page=0&size=#{page.getSize()}&sort=${sort}&filter=${category.name}">${category.name}</a>
                </li>
            </#list>
        </ul>
    </div>

    <div class="row row-cols-1 row-cols-md-3 g-4 justify-content-center">
        <#list page.content as vacancy>
            <div class="col">
                <div class="card h-100">
                    <div class="card-body">
                        <a href="vacancies/${vacancy.id}"><h3 class="card-title mb-4">${vacancy.name}</h3></a>

                        <#if vacancy.description??>
                            <h6 class="card-text">${vacancy.description}</h6>
                        </#if>

                        <h6 class="card-title">${vacancy.userEmail}</h6>

                        <#if vacancy.expFrom??>
                            <h6 class="card-title">Опыт от: ${vacancy.expFrom} (лет)</h6>
                        </#if>

                        <#if vacancy.expTo??>
                            <h6 class="card-title">Опыт до: ${vacancy.expTo} (лет)</h6>
                        </#if>

                        <#if vacancy.salary??>
                            <h4 class="card-title">Заработная плата: ${vacancy.salary}</h4>
                        </#if>
                    </div>
                    <div class="card-footer">
                        <small class="text-body-secondary">Дата обновления ${vacancy.updateTime.toLocalDate()}</small>
                    </div>
                </div>
            </div>
        </#list>
    </div>

    <@p.pager url page filter sort/>

</@main.layout>