let user = {

}

function getProfile() {
    const fetchPromise = fetch("http://localhost:8080/api/user/profile");
    let contact_details_username = document.getElementById("username");
    let contact_details_email = document.getElementById("email");
    let contact_details_lastName = document.getElementById("last_name");
    let contact_details_firstName = document.getElementById("first_name");
    let contact_details_birthday = document.getElementById("birthday");

    let delete_user_id = document.getElementById("delete_user_id");
    fetchPromise
        .then((res) => res.json())
        .then((data) => {

            user = data;
            delete_user_id.setAttribute('userId', data.id);
            contact_details_email.insertAdjacentHTML('beforeend', getContactDetailsEmail(data.email));
            contact_details_username.insertAdjacentHTML('beforeend', getContactDetailsUsername(data.username));
            contact_details_lastName.insertAdjacentHTML('beforeend', getContactDetailsLastName(data.lastName));
            contact_details_firstName.insertAdjacentHTML('beforeend', getContactDetailsFirstName(data.firstName));
            contact_details_birthday.insertAdjacentHTML('beforeend', getContactDetailsBirthday(data.birthday))

        })
}

function getContactDetailsEmail(data) {
    return `
    ${data}
    `
}

function getContactDetailsUsername(data) {
    return `
    ${data}
    `
}

function getContactDetailsLastName(data) {
    return `
    ${data}
    `
}

function getContactDetailsFirstName(data) {
    return `
    ${data}
    `
}

function getContactDetailsBirthday(data) {
    return `
    ${data}
    `
}

function usernameEdit() {
    let usernameEdit = document.getElementById('username');
    let usernameEditDiv = document.getElementById('div-username');
    usernameEdit.classList.add('iQgDDK');
    usernameEditDiv.classList.add('my-div-bg');
    usernameEdit.setAttribute("contentEditable", true);
}

function emailEdit() {
    let emailEdit = document.getElementById('email');
    let emailEditDiv = document.getElementById('div-email');
    emailEdit.classList.add('iQgDDK');
    emailEditDiv.classList.add('my-div-bg');
    emailEdit.setAttribute("contentEditable", true);
}

function putProfile() {

    let username = document.getElementById('username').innerText;
    let email = document.getElementById('email').innerText;

    user.username = username;
    user.email = email;

    fetch("http://localhost:8080/api/user", {
        method: "PUT",
        body: JSON.stringify(user),
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then((res) => res.json())

}



function deleteUser() {

    let id = document.getElementById('delete_user_id').getAttribute('userId');

    fetch("http://localhost:8080/api/user/delete/" + id, {
        method: "DELETE",
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then((data) => console.log(data))

}
