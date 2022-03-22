import shared from"/pages/shared/shared.js"

export default (sorting) => {
    const content = document.querySelector(".content");

    return fetch("./pages/candidate/index/candidate.html")
        .then((response) => response.text())
        .then((loginHtml) => {
            content.innerHTML = loginHtml;
            shared();
            generateContent(sorting);
        });
};

function generateContent(sorting) {
    fetch(`${window.apiUrl}/api/candidate`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
    })
        .then((response) => response.json())
        .then((data) => {
            if(sorting === "fullName"){
                data.sort((a,b) => (a.fullName > b.fullName) ? 1 : ((b.fullName > a.fullName) ? -1 : 0))
                .forEach(candidate => {
                    generateRow(candidate);
                });
            }
            else if(sorting === "partyName"){
                data.sort((a,b) => (a.partyName > b.partyName) ? 1 : ((b.partyName > a.partyName) ? -1 : 0)).forEach(candidate => {
                    generateRow(candidate);
                });
            }
            else{
                data.forEach(candidate => {
                    generateRow(candidate);
                });
            }
        })
        .catch((error) => {
            console.log(error);
        });
}

function generateRow(candidate) {
    const body = document.querySelector("tbody");
    const row = document.createElement("tr");

    body.appendChild(row);

    const id = document.createElement("th");
    const fullName = document.createElement("td");
    const partyName = document.createElement("td");
    const editButton = document.createElement("td");
    const deleteButton = document.createElement("td");

    row.appendChild(id);
    row.appendChild(fullName);
    row.appendChild(partyName);
    row.appendChild(editButton);
    row.appendChild(deleteButton);

    id.innerText = candidate.id;

    fullName.innerText = candidate.fullName;
    partyName.innerText = candidate.partyName;

    const editLink = document.createElement("a");
    editLink.classList.add("nav-link");
    editLink.href = "/#/candidate/edit/" + candidate.id;
    const eButton = document.createElement("span");
    eButton.classList.add("btn", "btn-primary");
    eButton.innerText = "Edit";


    editButton.appendChild(editLink);
    editLink.appendChild(eButton);

    const deleteLink = document.createElement("a");
    deleteLink.classList.add("nav-link");
    deleteLink.href = "/#/candidate/delete/" + candidate.id;
    const dButton = document.createElement("span");
    dButton.classList.add("btn", "btn-danger");
    dButton.innerText = "Delete";

    deleteButton.appendChild(deleteLink);
    deleteLink.appendChild(dButton);
}