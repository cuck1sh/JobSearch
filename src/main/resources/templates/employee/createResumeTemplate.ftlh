<#import  "../layout.ftlh" as main>
<@main.layout ; spring>

    <div class="col-12 col-lg-6 p-4 mx-auto border rounded-2 mb-4">
        <h1 class="text-center mt-2 mb-3 text-danger"><@spring.message 'resume.creation.h1'/></h1>
        <form id="form" action="/employee/resumes/add" method="POST">

            <div class="row">
                <div class="col-7">

                    <div class="mb-3">
                        <label for="name" class="form-label"><@spring.message 'resume.creation.name'/></label>
                        <@spring.formInput "inputResumeDto.name" 'id="name" class="form-control" placeholder="Введите название" required'/>
                        <@spring.showErrors "<br>" "error text-danger" />
                    </div>

                    <div class="mb-3">

                        <label for="category" class="form-label"><@spring.message 'resume.creation.category'/></label>
                        <@spring.formSingleSelect "inputResumeDto.category",
                        categories,
                        "id='category' class='form-select' required"/>
                    </div>

                    <div class="mb-3">
                        <label for="salary" class="form-label"><@spring.message 'resume.creation.salary'/></label>
                        <@spring.formInput "inputResumeDto.salary" 'id="salary" class="form-control" placeholder="min 999"'/>
                        <@spring.showErrors "<br>" "error text-danger" />
                    </div>
                </div>
            </div>

            <h4><@spring.message 'resume.creation.yourself'/></h4>
            <div id="workExperienceInfo">
                <div class="row mb-3" id="workExp">
                    <div class="col-7">
                        <div class="card">
                            <div class="card-body">
                                <h6 class="card-text"><@spring.message 'resume.creation.experience'/></h6>
                                <h6 class="fw-normal"><@spring.message 'resume.creation.years'/> ___________</h6>
                                <h6 class="fw-normal"><@spring.message 'resume.creation.company.name'/>
                                    ______________</h6>
                                <h6 class="fw-normal"><@spring.message 'resume.creation.position'/> ____________</h6>
                                <h6 class="fw-normal"><@spring.message 'resume.creation.responsibilities'/>
                                    _________</h6>
                            </div>
                        </div>
                    </div>

                    <button type="button" id="workExpButton"
                            class="btn col-3 m-2 text-decoration-none bg-success p-2 text-dark bg-opacity-10 rounded-3">
                        <span class="fs-1">+</span>
                    </button>
                </div>

                <#if inputResumeDto.workExperienceInfoDtos??>
                    <#list (inputResumeDto.workExperienceInfoDtos?size - 1)..0 as i>
                        <div class="row py-2 mb-3 mx-0 extra-info-row">
                            <div class="col-7 pt-2 bg-success text-dark bg-opacity-10 rounded-3">
                                <div class="mb-3">
                                    <label for="years"
                                           class="form-label"><@spring.message 'resume.creation.years'/></label>
                                    <@spring.formInput "inputResumeDto.workExperienceInfoDtos["+i+"].years" 'id="years" class="form-control" placeholder="2years" required'/>
                                    <@spring.showErrors "<br>" "error text-danger" />
                                </div>

                                <div class="mb-3">
                                    <label for="companyName"
                                           class="form-label"><@spring.message 'resume.creation.company.name'/></label>
                                    <@spring.formInput "inputResumeDto.workExperienceInfoDtos["+i+"].companyName" 'id="companyName" class="form-control" placeholder="Google" required'/>
                                    <@spring.showErrors "<br>" "error text-danger" />
                                </div>

                                <div class="mb-3">
                                    <label for="position"
                                           class="form-label"><@spring.message 'resume.creation.position'/></label>
                                    <@spring.formInput "inputResumeDto.workExperienceInfoDtos["+i+"].position" 'id="position" class="form-control" required'/>
                                    <@spring.showErrors "<br>" "error text-danger" />
                                </div>

                                <div class="mb-3">
                                    <label for="responsibilities"
                                           class="form-label"><@spring.message 'resume.creation.responsibilities'/></label>
                                    <@spring.formInput "inputResumeDto.workExperienceInfoDtos["+i+"].responsibilities" 'id="responsibilities" class="form-control" required'/>
                                    <@spring.showErrors "<br>" "error text-danger" />
                                </div>
                            </div>

                            <div class="btn remove-button col-3 m-2 d-flex justify-content-center align-items-center bg-danger p-2 bg-opacity-10 rounded-3"
                                 onclick=deleteExpRow(this)
                            >
                                <span class="fs-1">-</span>
                            </div>

                        </div>
                    </#list>
                </#if>
            </div>

            <div id="educationInfo">
                <div class="row mb-3 " id="education">
                    <div class="col-7">
                        <div class="card">
                            <div class="card-body">
                                <h6 class="card-text"><@spring.message 'resume.creation.skills'/></h6>
                                <h6 class="fw-normal"><@spring.message 'resume.creation.education'/> ___________</h6>
                                <h6 class="fw-normal"><@spring.message 'resume.creation.school'/> ______________</h6>
                                <h6 class="fw-normal"><@spring.message 'resume.creation.edu.time'/> ____________</h6>
                            </div>
                        </div>
                    </div>

                    <button type="button" id="educationButton"
                            class="btn col-3 m-2 text-decoration-none bg-info p-2 text-dark bg-opacity-10 rounded-3">
                        <span class="fs-1">+</span>
                    </button>
                </div>

                <#if inputResumeDto.educationInfos??>
                    <#list (inputResumeDto.educationInfos?size - 1)..0 as i>
                        <div class="row py-2 mb-3 mx-0 extra-info-row">
                            <div class="col-7 pt-2 bg-info text-dark bg-opacity-10 rounded-3">
                                <div class="mb-3">
                                    <label for="institution" class="form-label">
                                        <@spring.message 'resume.creation.school.name'/>
                                    </label>
                                    <@spring.formInput "inputResumeDto.educationInfos["+i+"].institution" 'id="institution" class="form-control" placeholder="Harvard" required'/>
                                    <@spring.showErrors "<br>" "error text-danger" />
                                </div>

                                <div class="mb-3">
                                    <label for="program"
                                           class="form-label"><@spring.message 'resume.creation.program'/></label>
                                    <@spring.formInput "inputResumeDto.educationInfos["+i+"].program" 'id="program" class="form-control" required'/>
                                    <@spring.showErrors "<br>" "error text-danger" />
                                </div>

                                <div class="mb-3">
                                    <label for="startDate"
                                           class="form-label"><@spring.message 'resume.creation.start.date'/></label>
                                    <@spring.formInput "inputResumeDto.educationInfos["+i+"].startDate" 'id="startDate" class="form-control" required'/>
                                    <@spring.showErrors "<br>" "error text-danger" />
                                </div>

                                <div class="mb-3">
                                    <label for="endDate"
                                           class="form-label"><@spring.message 'resume.creation.end.date'/></label>
                                    <@spring.formInput "inputResumeDto.educationInfos["+i+"].endDate" 'id="endDate" class="form-control" required'/>
                                    <@spring.showErrors "<br>" "error text-danger" />
                                </div>

                                <div class="mb-3">
                                    <label for="degree"
                                           class="form-label"><@spring.message 'resume.creation.degree'/></label>
                                    <@spring.formInput "inputResumeDto.educationInfos["+i+"].degree" 'id="degree" class="form-control"'/>
                                </div>
                            </div>

                            <div class="btn remove-button col-3 m-2 d-flex justify-content-center align-items-center bg-danger p-2 bg-opacity-10 rounded-3"
                                 onclick=deleteExpRow(this)
                            >
                                <span class="fs-1">-</span>
                            </div>
                        </div>
                    </#list>
                </#if>


            </div>


            <h4 class=""><@spring.message 'resume.creation.society'/></h4>

            <div class="row mb-3 row-cols-1 row-cols-lg-2">
                <div class="col">

                    <div class="d-flex">
                        <@spring.formInput "inputResumeDto.contacts.telegram" 'id="telegram" class="form-control me-2 my-3"'/>
                        <@spring.showErrors "<br>" "error text-danger" />
                        <i class="bi bi-telegram align-content-center fs-2"></i>
                    </div>

                    <div class="d-flex">
                        <@spring.formInput "inputResumeDto.contacts.email" 'id="email" class="form-control me-2 my-3" placeholder="example@mail.com"' 'email'/>
                        <@spring.showErrors "<br>" "error text-danger" />
                        <i class="bi bi-envelope-at align-content-center fs-2"></i>
                    </div>

                    <div class="d-flex">
                        <@spring.formInput "inputResumeDto.contacts.phoneNumber" 'id="phoneNumber" class="form-control me-2 my-3" placeholder="996-779-11-22-33"'/>
                        <@spring.showErrors "<br>" "error text-danger" />
                        <i class="bi bi-telephone-inbound align-content-center fs-2"></i>
                    </div>
                </div>

                <div class="col">
                    <div class="d-flex">
                        <@spring.formInput "inputResumeDto.contacts.facebook" 'id="facebook" class="form-control me-2 my-3"'/>
                        <@spring.showErrors "<br>" "error text-danger" />
                        <i class="bi bi-facebook align-content-center fs-2"></i>
                    </div>

                    <div class="d-flex">
                        <@spring.formInput "inputResumeDto.contacts.linkedIn" 'id="linkedIn" class="form-control me-2 my-3"'/>
                        <@spring.showErrors "<br>" "error text-danger" />
                        <i class="bi bi-linkedin align-content-center fs-2"></i>
                    </div>
                </div>
            </div>

            <div class="form-check mb-3">
                <@spring.formCheckbox "inputResumeDto.isActive", 'class="form-check-input" id="isActive"'/>
                <label class="form-check-label" for="isActive">
                    <@spring.message 'resume.creation.active'/>
                </label>
            </div>

            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                       value="${(_csrf.token)!'csrf-token'}"/>
            </#if>

            <div class="d-grid gap-2">
                <button type="submit" class="btn bg-success border p-2 text-dark bg-opacity-10 rounded-3">
                    <@spring.message 'resume.creation.button'/>
                </button>
            </div>
        </form>
    </div>

<#--    Translation to JS from FreeMarker macro-->
    <div hidden="hidden" id="hidden-yearsOfWork"><@spring.message 'resume.creation.years'/></div>
    <div hidden="hidden" id="hidden-companyName"><@spring.message 'resume.creation.company.name'/></div>
    <div hidden="hidden" id="hidden-position"><@spring.message 'resume.creation.position'/></div>
    <div hidden="hidden" id="hidden-responsibilities"><@spring.message 'resume.creation.responsibilities'/></div>

    <div hidden="hidden" id="hidden-schoolName"><@spring.message 'resume.creation.school.name'/></div>
    <div hidden="hidden" id="hidden-program"><@spring.message 'resume.creation.program'/></div>
    <div hidden="hidden" id="hidden-startDate"><@spring.message 'resume.creation.start.date'/></div>
    <div hidden="hidden" id="hidden-endDate"><@spring.message 'resume.creation.end.date'/></div>
    <div hidden="hidden" id="hidden-degree"><@spring.message 'resume.creation.degree'/></div>
    <div hidden="hidden" id="hidden-restriction"><@spring.message 'resume.creation.restriction'/></div>

    <script src="/static/js/resumeCreation.js"></script>
    <script>
        function deleteExpRow(button) {
            const row = button.closest('.extra-info-row');
            if (row) {
                row.remove();
            }
        }
    </script>

</@main.layout>