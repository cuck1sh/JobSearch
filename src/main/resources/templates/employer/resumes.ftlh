<#import  "../layout.ftlh" as main>
<#import "../pager.ftlh" as p>
<@main.layout ; spring>

    <h1 class="text-center text-danger mb-1"><@spring.message 'resumes.h1'/></h1>

    <div class="dropdown d-flex justify-content-center m-3">
        <button class="btn border me-2 dropdown-toggle" type="button" data-bs-toggle="dropdown"
                aria-expanded="false">
            SORT: <@spring.message 'resumes.sort.responses.qty'/>
        </button>
        <ul class="dropdown-menu">
            <li><a class="dropdown-item"
                   href="${url}?page=0&size=${page.getSize()}&filter=${filter}"><@spring.message 'resumes.none'/></a>
            </li>
            <li><a class="dropdown-item"
                   href="${url}?page=0&size=#{page.getSize()}&sort=RespondedApplicantsResumeId,desc&filter=${filter}">
                    <@spring.message 'resumes.sort.descending'/>
                </a>
            </li>
            <li><a class="dropdown-item"
                   href="${url}?page=0&size=#{page.getSize()}&sort=RespondedApplicantsResumeId,asc&filter=${filter}">
                    <@spring.message 'resumes.sort.ascending'/>
                </a>
            </li>
        </ul>

        <button class="btn border me-2 dropdown-toggle" type="button" data-bs-toggle="dropdown"
                aria-expanded="false">
            SORT: <@spring.message 'resumes.sort.date.creation'/>
        </button>
        <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="${url}?page=0&size=${page.getSize()}&filter=${filter}">
                    <@spring.message 'resumes.none'/>
                </a>
            </li>
            <li><a class="dropdown-item"
                   href="${url}?page=0&size=#{page.getSize()}&sort=CreatedDate,desc&filter=${filter}">
                    <@spring.message 'resumes.sort.new'/>
                </a>
            </li>
            <li><a class="dropdown-item"
                   href="${url}?page=0&size=#{page.getSize()}&sort=CreatedDate,asc&filter=${filter}">
                    <@spring.message 'resumes.sort.old'/>
                </a>
            </li>
        </ul>

        <button class="btn border dropdown-toggle" type="button" data-bs-toggle="dropdown"
                aria-expanded="false">
            <@spring.message 'resumes.filter.category'/>
        </button>
        <ul class="dropdown-menu">
            <li><a class="dropdown-item"
                   href="${url}?page=0&size=#{page.getSize()}&sort=${sort}"><@spring.message 'resumes.none'/></a></li>
            <#list categories as category>
                <li><a class="dropdown-item"
                       href="?page=0&size=#{page.getSize()}&sort=${sort}&filter=${category.name}">${category.name}</a>
                </li>
            </#list>
        </ul>
    </div>

    <div class="row row-cols-1 row-cols-md-3 g-4 justify-content-center">
        <#list page.content as resume>
            <div class="col">
                <div class="card h-100">
                    <div class="card-body">
                        <a href="/resumes/${resume.id}"><h3 class="card-title mb-4">${resume.name}</h3></a>

                        <#if resume.category??>
                            <#if resume.category.parent??>
                                <h6 class="card-title"><@spring.message 'resumes.parent.category'/> ${resume.category.parent}</h6>
                            </#if>

                            <#if resume.category.name??>
                                <h6 class="card-title"><@spring.message 'resumes.category'/> ${resume.category.name}</h6>
                            </#if>
                        </#if>

                        <h6 class="card-title">${resume.userEmail}</h6>


                        <#if resume.salary??>
                            <h4 class="card-title"><@spring.message 'resumes.salary'/> ${resume.salary}</h4>
                        </#if>

                    </div>
                    <div class="card-footer">
                        <small class="text-body-secondary"><@spring.message 'resumes.update.time'/> ${resume.updateTime.toLocalDate()}</small>
                    </div>
                </div>
            </div>
        </#list>
    </div>

    <@p.pager url page filter sort/>

</@main.layout>