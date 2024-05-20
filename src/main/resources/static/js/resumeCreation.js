'use strict'

window.addEventListener('load', () => {
    let workExperienceInfo = document.getElementById('workExperienceInfo');
    let educationInfo = document.getElementById('educationInfo');

    let workExpButton = document.getElementById('workExpButton');
    let educationButton = document.getElementById('educationButton');

    let workExp = document.getElementById('workExp');
    let education = document.getElementById('education');


    let expCounter = -1;
    let eduCounter = -1;

    workExpButton.addEventListener('click', () => {
        expCounter++;
        console.log(expCounter);
        let newWorkExpRow = document.createElement('div');
        newWorkExpRow.className = 'row py-2 mb-3 mx-0 ';
        workExp.after(newWorkExpRow);

        const yearsOfWork = document.getElementById('hidden-yearsOfWork').innerText
        const companyName = document.getElementById('hidden-companyName').innerText
        const position = document.getElementById('hidden-position').innerText
        const responsibilities = document.getElementById('hidden-responsibilities').innerText
        const restriction = document.getElementById('hidden-restriction').innerText

        let newWorkExpCol = document.createElement('div');
        newWorkExpCol.className = 'col-7 pt-2 bg-success text-dark bg-opacity-10 rounded-3';
        newWorkExpCol.innerHTML =
            '<div class = "mb-3">' +
            '<label for="years" class="form-label">' + yearsOfWork + '</label>' +
            '<input class="form-control" name="workExperienceInfoDtos[' + expCounter + '].years" type="number" placeholder="2years" id="years" required>' +
            '</div>' +

            '<div class = "mb-3">' +
            '<label for="companyName" class="form-label">' + companyName + '</label> ' +
            '<input class="form-control" name="workExperienceInfoDtos[' + expCounter + '].companyName" type="text" placeholder="Google" id="companyName" required>' +
            '</div>' +

            '<div class = "mb-3">' +
            '<label for="position" class="form-label">' + position + '</label> ' +
            '<input class="form-control" name="workExperienceInfoDtos[' + expCounter + '].position" type="text" id="position" required>' +
            '</div>' +

            '<div class = "mb-3">' +
            '<label for="responsibilities" class="form-label">' + responsibilities + '</label> ' +
            '<input class="form-control" name="workExperienceInfoDtos[' + expCounter + '].responsibilities" type="text" id="responsibilities" required>' +
            '</div>';
        newWorkExpRow.appendChild(newWorkExpCol);

        let newExpButton = document.createElement('div');
        newExpButton.className = 'btn remove-button col-3 m-2 d-flex justify-content-center align-items-center bg-danger p-2 bg-opacity-10 rounded-3';
        newExpButton.innerHTML = '<span class="fs-1">-</span>';
        newWorkExpRow.appendChild(newExpButton);

        newWorkExpRow.querySelector('.remove-button').addEventListener('click', () => {
            if (newWorkExpRow.previousElementSibling.id === 'workExp') {
                workExperienceInfo.removeChild(newWorkExpRow);
                expCounter--;
            } else {
                let warningBlock = document.createElement('div');
                warningBlock.innerText = restriction;
                warningBlock.className = 'text-danger';
                newWorkExpRow.appendChild(warningBlock);

                setTimeout(() => {
                    warningBlock.remove();
                }, 3000)
            }
        });
    });

    educationButton.addEventListener('click', () => {
        eduCounter++;
        console.log(eduCounter);
        let newEduRow = document.createElement('div');
        newEduRow.className = 'row py-2 mb-3 mx-0 ';
        education.after(newEduRow);

        const schoolName = document.getElementById('hidden-schoolName').innerText
        const program = document.getElementById('hidden-program').innerText
        const startDate = document.getElementById('hidden-startDate').innerText
        const endDate = document.getElementById('hidden-endDate').innerText
        const degree = document.getElementById('hidden-degree').innerText
        const restriction = document.getElementById('hidden-restriction').innerText

        let newEduCol = document.createElement('div');
        newEduCol.className = 'col-7 pt-2 bg-info text-dark bg-opacity-10 rounded-3';
        newEduCol.innerHTML =
            '<div class = "mb-3">' +
            '<label for="institution" class="form-label">' + schoolName + '</label> ' +
            '<input class="form-control" name="educationInfos[' + eduCounter + '].institution" type="text" placeholder="Harvard" id="institution" required>' +
            '</div>' +

            '<div class = "mb-3">' +
            '<label for="program" class="form-label">' + program + '</label> ' +
            '<input class="form-control" name="educationInfos[' + eduCounter + '].program" type="text" id="program" required>' +
            '</div>' +

            '<div class = "mb-3">' +
            '<label for="startDate" class="form-label">' + startDate + '</label> ' +
            '<input class="form-control" name="educationInfos[' + eduCounter + '].startDate" type="date" id="startDate" required>' +
            '</div>' +

            '<div class = "mb-3">' +
            '<label for="endDate" class="form-label">' + endDate + '</label> ' +
            '<input class="form-control" name="educationInfos[' + eduCounter + '].endDate" type="date" id="endDate" required>' +
            '</div>' +

            '<div class = "mb-3">' +
            '<label for="degree" class="form-label">' + degree + '</label> ' +
            '<input class="form-control" name="educationInfos[' + eduCounter + '].degree" type="text" id="degree" required>' +
            '</div>'
            newEduRow.appendChild(newEduCol);

        let newEduButton = document.createElement('div');
        newEduButton.className = 'btn remove-button col-3 m-2 d-flex justify-content-center align-items-center bg-danger p-2 bg-opacity-10 rounded-3';
        newEduButton.innerHTML = '<span class="fs-1">-</span>';
        newEduRow.appendChild(newEduButton);

        newEduRow.querySelector('.remove-button').addEventListener('click', () => {
            if (newEduRow.previousElementSibling.id === 'education') {
                educationInfo.removeChild(newEduRow);
                eduCounter--;
            } else {
                let warningBlock = document.createElement('div');
                warningBlock.innerText = restriction;
                warningBlock.className = 'text-danger';
                newEduRow.appendChild(warningBlock);

                setTimeout(() => {
                    warningBlock.remove();
                }, 3000)
            }
        });
    });

});