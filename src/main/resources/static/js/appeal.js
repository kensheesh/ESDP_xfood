function addFileInput() {
    const container = document.getElementById('file-upload-container');
    const newInput = document.createElement('div');
    newInput.className = 'file-upload';
    newInput.innerHTML = `
        <input type="file" class="form-control fileUploader mt-3" name="files[]" multiple>
        <div class="error-message"></div>
    `;
    container.appendChild(newInput);
    initializeFileUploaders();
}

function initializeFileUploaders() {
    const fileUploaders = document.querySelectorAll('.fileUploader');

    fileUploaders.forEach(fileUploader => {
        fileUploader.addEventListener('change', (event) => {
            const file = event.target.files[0];
            const errorMessageElement = event.target.nextElementSibling;
            if (file) {
                validateFileType(file, event.target, errorMessageElement);
            }
        });
    });
}

function validateFileType(file, input, errorMessageElement) {
    const acceptedTypes = ['png', 'jpg', 'jpeg', 'mp4', 'mov'];
    const acceptedTypesLong = ['image/png', 'image/jpg', 'image/jpeg', 'video/mp4', 'video/quicktime'];

    const nameExtension = file.name.split('.').pop().toLowerCase();

    if (!acceptedTypes.includes(nameExtension) || !acceptedTypesLong.includes(file.type)) {
        input.value = null;
        errorMessageElement.textContent = "Загрузите фотографию или видео в формате png, jpg, jpeg, mp4 или mov";
        errorMessageElement.style.display = "block";
    } else {
        errorMessageElement.textContent = "";
        errorMessageElement.style.display = "none";
    }
}

document.getElementById('form').addEventListener('submit', function (event) {
    event.preventDefault();

    const fileUploaders = document.querySelectorAll('.fileUploader');
    let allFilesValid = true;

    fileUploaders.forEach(fileUploader => {
        const file = fileUploader.files[0];
        const errorMessageElement = fileUploader.nextElementSibling;
        if (!file) {
            errorMessageElement.textContent = "Загрузите фотографию или видео в формате png, jpg, jpeg, mp4 или mov.";
            errorMessageElement.style.display = "block";
            allFilesValid = false;
        } else {
            errorMessageElement.textContent = "";
            errorMessageElement.style.display = "none";
        }
    });

    if (!allFilesValid) {
        return;
    }

    const form = event.target;
    const formData = new FormData(form);

    fetch(form.action, {
        method: form.method,
        body: formData
    }).then(response => {
        if (response.ok) {
            $('#successModal').modal('show');
            setTimeout(function () {
                let uuid = document.getElementById('uuid').value;
                window.location.href = '/checks/'+uuid+'/result';
            }, 3000);
        } else {
            response.json().then(data => {
                handleValidationErrors(data.errors);
            }).catch(error => {
                alert('Ошибка при загрузке файлов. Пожалуйста, убедитесь, что:\n' +
                    '- Вы загружаете не более 5 фотографий, каждая не превышает 3 МБ.\n' +
                    '- Вы загружаете не более 4 видео-файлов, каждый не превышает 25 МБ.');
            });
        }
    }).catch(error => {
        console.error('Error:', error);
        alert('Ошибка при загрузке файлов. Пожалуйста, убедитесь, что:\n' +
            '- Вы загружаете не более 5 фотографий, каждая не превышает 3 МБ.\n' +
            '- Вы загружаете не более 4 видео-файлов, каждый не превышает 25 МБ.');
    });
});

function handleValidationErrors(errors) {
    errors.forEach(error => {
        const errorMessageElement = document.getElementById(error.field + `-error`);
        if (errorMessageElement) {
            errorMessageElement.textContent = error.defaultMessage;
            errorMessageElement.style.display = "block";
        }
    });
}

let editingFiles = false;

function toggleEditFiles() {
    const fileUploaders = document.querySelectorAll('.file-upload');
    fileUploaders.forEach(fileUpload => {
        let deleteButton = fileUpload.querySelector('.delete-button');
        if (editingFiles) {
            if (deleteButton) {
                deleteButton.remove();
            }
        } else {
            if (!deleteButton) {
                const newDeleteButton = document.createElement('button');
                newDeleteButton.className = 'btn btn-danger delete-button mt-2';
                newDeleteButton.textContent = 'Удалить';
                newDeleteButton.onclick = function () {
                    fileUpload.remove();
                };
                fileUpload.appendChild(newDeleteButton);
            }
        }
    });
    editingFiles = !editingFiles;
}

initializeFileUploaders();