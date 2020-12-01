let user = {

};

function getProfile() {
    const fetchPromise = fetch("http://localhost:8080/api/user/profile");
    fetchPromise
        .then((res) => res.json())
        .then((data) => {
        user = data
        })
}

let checkPassword = false;

function checkPasswordMatch() {
    let newPassword = document.getElementById('newPassword').value;
    let confirmNewPassword = document.getElementById('confirmNewPassword').value;
    let passwordMatchOrNotMatch = document.getElementById("divCheckPasswordMatch");
        if (newPassword !== confirmNewPassword) {
            passwordMatchOrNotMatch.innerHTML = "Passwords not match!";
            checkPassword = false
            passwordMatchOrNotMatch.classList.remove("div-match");
            passwordMatchOrNotMatch.classList.add("div-not-match")
        } else {
            passwordMatchOrNotMatch.innerHTML = "Passwords match.";
            checkPassword = true
            passwordMatchOrNotMatch.classList.remove();
            passwordMatchOrNotMatch.classList.add("div-match")
        }

}

function putPassword() {
    let currentPassword = document.getElementById('currentPassword').value;
    let newPassword = document.getElementById('newPassword').value;

    user.password = currentPassword;
    user.newPassword = newPassword;
    if (checkPassword) {
        fetch("http://localhost:8080/api/user/password", {
            method: "PUT",
            body: JSON.stringify(user),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then((res) => res.json())
        let passwordMatchOrNotMatch = document.getElementById("divCheckPasswordMatch");
        passwordMatchOrNotMatch.innerHTML = "Successfully updated."
    }
}