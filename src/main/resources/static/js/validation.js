document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("addUserForm");
    const email = document.getElementById("email");
    const emailError = document.getElementById("emailError");

    if (form) {
        form.addEventListener("submit", function (event) {
            const password = document.getElementById("password");
            const tgLink = document.getElementById("tgLink");

            let isValid = form.checkValidity();

            const validatePassword = () => {
                if (!password.value.match(/^(?=.*\d)(?=.*[a-zA-Z]).{8,}$/)) {
                    password.classList.add('is-invalid');
                    return false;
                } else {
                    password.classList.remove('is-invalid');
                    return true;
                }
            };

            const validateTgLink = () => {
                if (!tgLink.value.match(/^t\.me\/.*/)) {
                    tgLink.classList.add('is-invalid');
                    return false;
                } else {
                    tgLink.classList.remove('is-invalid');
                    return true;
                }
            };

            isValid = validatePassword() && validateTgLink() && isValid;

            if (!isValid) {
                event.preventDefault();
                event.stopPropagation();
                form.classList.add('was-validated');
            } else {
                event.preventDefault();
                const formData = new FormData(form);
                const data = Object.fromEntries(formData.entries());

                fetch('/api/user/create', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(data),
                })
                    .then(response => response.text().then(text => {
                        try {
                            const jsonResponse = text ? JSON.parse(text) : {};
                            if (response.ok) {
                                console.log('Success:', jsonResponse);
                                const modal = document.getElementById('addUser');
                                if (modal) {
                                    const bsModal = bootstrap.Modal.getInstance(modal);
                                    if (bsModal) bsModal.hide();
                                }

                                setTimeout(() => {
                                    const clipboardText = `Email: ${data.email}\nPassword: ${data.password}`;
                                    navigator.clipboard.writeText(clipboardText).then(() => {
                                    }).catch(err => {
                                        console.error('Failed to copy to clipboard:', err);
                                    });
                                }, 100);

                            } else {
                                throw new Error(jsonResponse.message || 'Unknown error occurred');
                            }
                        } catch (error) {
                            throw new Error('Error parsing JSON: ' + error.message);
                        }
                    }))
                    .catch(error => {
                        console.error('Error:', error);
                        if (error.message.includes('User already exists')) {
                            emailError.textContent = "Пользователь с такой почтой уже существует. Пожалуйста, используйте другую почту.";
                            email.classList.add('is-invalid');
                        } else {
                            emailError.textContent = '';
                            email.classList.remove('is-invalid');
                        }
                    });
            }
        });
    }
});