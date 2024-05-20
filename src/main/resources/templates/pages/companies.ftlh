<#import  "../layout.ftlh" as main>
<#import "../pager.ftlh" as p>
<@main.layout ; spring>

    <h1 class="text-center text-danger mb-3"><@spring.message 'companies.title'/></h1>

    <div class="row row-cols-1 row-cols-lg-3 row-cols-xl-5 g-4 justify-content-center">
        <#list page.content as company>
            <div class="col">
                <div class="card h-100">
                    <img src="/api/users/avatar/${company.id}" class="card-img-top h-75" alt="avatar">
                    <div class="card-body">
                        <h5 class="card-title mb-3">${company.name}</h5>
                        <p class="card-text mb-1"><@spring.message 'companies.email'/> ${company.email}</p>
                        <p class="card-text"><@spring.message 'companies.phone'/> ${company.phoneNumber}</p>
                    </div>
                    <div class="card-footer">
                        <small>
                            <a href="/users/profile/${company.email}" class="text-body-secondary">
                                <@spring.message 'companies.link'/>
                            </a>
                        </small>
                    </div>
                </div>
            </div>
        </#list>
    </div>

    <@p.pager url page filter sort/>
</@main.layout>