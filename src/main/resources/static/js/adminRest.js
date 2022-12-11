
// Show All User
$(async function() {
    await showAllUsers();
});
const table = $('#getAllUserTable');
async function showAllUsers() {
    table.empty()
    fetch("http://localhost:8080/rs/admin")
        .then(res => res.json())
        .then(data => {
            data.forEach(user => {
                let tableWithUsers = `$(
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.username}</td>
                            <td>${user.fullname}</td>
                            <td>${user.age}</td>                                                 
                            <td>${user.email}</td>
                            <td>${user.roles.map(role => " " + role.role.substring(5))}</td>
                            <td>
                                <button type="button" class="btn btn-info" data-toggle="modal" id="buttonEdit"
                                data-action="edit" data-id="${user.id}" data-target="#edit">Edit</button>
                            </td>
                            <td>
                                <button type="button" class="btn btn-danger" data-toggle="modal" id="buttonDelete"
                                data-action="delete" data-id="${user.id}" data-target="#delete">Delete</button>
                            </td>
                        </tr>)`;
                table.append(tableWithUsers);
            })
        })
}
// Show Active User

$(async function() {
    await thisUser();
});
async function thisUser() {
    fetch("http://localhost:8080/rs/admin/authUser")
        .then(res => res.json())
        .then(data => {
            // авторизированный пользователь
            $('#headerUsername').append(data.username);
            let roles = data.roles.map(role => " " + role.role.substring(5));
            $('#headerRoles').append(roles);

            let user = `$(
            <tr>
                <td>${data.id}</td>
                <td>${data.username}</td>
                <td>${data.fullname}</td>
                <td>${data.age}</td>
                <td>${data.email}</td>
                <td>${roles}</td>)
            </tr>`;
            $('#userPanelBody').append(user);
        })
}


// Add New User

$(async function () {
    await newUser();
});

async function newUser() {
    await fetch("http://localhost:8080/rs/admin/roles")
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                let elementRole = document.createElement("option");
                elementRole.text = role.role.substring(5);
                elementRole.value = role.id;
                $('#newUserRoles')[0].appendChild(elementRole);
            })
        })

    const form = document.forms["formNewUser"];

    form.addEventListener('submit', addNewUser)

    async function addNewUser(e) {
        e.preventDefault();
        let userRoles = [];
        for (let i = 0; i < form.roles.options.length; i++) {
            if (form.roles.options[i].selected) userRoles.push({
                id: form.roles.options[i].value,
                role: "ROLE_" + form.roles.options[i].text
            })
        }
        const response = await fetch("http://localhost:8080/rs/admin", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: form.username.value,
                fullname: form.fullname.value,
                age: form.age.value,
                email: form.email.value,
                password: form.password.value,
                roles: userRoles
            })
        })
        if(response.ok){
            form.reset();
            showAllUsers();
            $('#allUsersTable').click();
        } else {
            let error = await response.json();
            console.log(error.info);
        }
    }

}

// Edit User - Json

$(async function() {
    editUser();

});
function editUser() {
    const editForm = document.forms["formEditUser"];
    editForm.addEventListener("submit", async ev => {
        ev.preventDefault();
        let editUserRoles = [];
        for (let i = 0; i < editForm.roles.options.length; i++) {
            if (editForm.roles.options[i].selected) editUserRoles.push({
                id: editForm.roles.options[i].value,
                role: "ROLE_" + editForm.roles.options[i].text
            })
        }

        const response = await fetch("http://localhost:8080/rs/admin/" + editForm.id.value, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: editForm.id.value,
                username: editForm.username.value,
                fullname: editForm.fullname.value,
                age: editForm.age.value,
                email: editForm.email.value,
                password: editForm.password.value,
                roles: editUserRoles
            })
        })
        if (response.ok){
            $('#editFormCloseButton').click();
            showAllUsers();
        } else {
            let error = await response.json();
            console.log(error.info);
        }
    })
}

// Show modal For editing

$('#edit').on('show.bs.modal', ev => {
    let button = $(ev.relatedTarget);
    let id = button.data('id');
    showEditModal(id);
})

async function showEditModal(id) {
    $('#rolesEditUser').empty();
    let user = await getUser(id);
    let form = document.forms["formEditUser"];
    form.id.value = user.id;
    form.username.value = user.username;
    form.fullname.value = user.fullname;
    form.age.value = user.age;
    form.email.value = user.email;
    form.password.value = user.password;



    await fetch("http://localhost:8080/rs/admin/roles")
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                let selectedRole = false;
                for (let i = 0; i < user.roles.length; i++) {
                    if (user.roles[i].role === role.role) {
                        selectedRole = true;
                        break;
                    }
                }
                let elementRole = document.createElement("option");
                elementRole.text = role.role.substring(5);
                elementRole.value = role.id;
                if (selectedRole) elementRole.selected = true;
                $('#rolesEditUser')[0].appendChild(elementRole);
            })
        })
}

// Json Delete User

$(async function() {

    removeUser();
});
function removeUser(){
    const deleteForm = document.forms["formDeleteUser"];
    deleteForm.addEventListener("submit", ev => {
        ev.preventDefault();
        fetch("http://localhost:8080/rs/admin/" + deleteForm.id.value, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(() => {
                $('#deleteFormCloseButton').click();
                showAllUsers();
            })
    })
}

// Show modal for Delete

$('#delete').on('show.bs.modal', ev => {
    let button = $(ev.relatedTarget);
    let id = button.data('id');
    showDeleteModal(id);
})

async function showDeleteModal(id) {
    let user = await getUser(id);
    let form = document.forms["formDeleteUser"];
    form.id.value = user.id;
    form.username.value = user.username;
    form.fullname.value = user.fullname;
    form.age.value = user.age;
    form.email.value = user.email;

    $('#rolesDeleteUser').empty();

    await fetch("http://localhost:8080/rs/admin/roles")
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                let selectedRole = false;
                for (let i = 0; i < user.roles.length; i++) {
                    if (user.roles[i].role === role.role) {
                        selectedRole = true;
                        break;
                    }
                }
                let elementRole = document.createElement("option");
                elementRole.text = role.role.substring(5);
                elementRole.value = role.id;
                if (selectedRole) elementRole.selected = true;
                $('#rolesDeleteUser')[0].appendChild(elementRole);
            })
        });
}
async function getUser(id) {
    let url = "http://localhost:8080/rs/admin/edit/" + id;
    let response = await fetch(url);
    return await response.json();
}