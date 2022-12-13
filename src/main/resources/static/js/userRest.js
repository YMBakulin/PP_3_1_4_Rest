// Show Active User

$(async function() {
    await thisUser();
});
async function thisUser() {
    fetch("http://localhost:8080/rs/user/authUser")
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