import shared from "/pages/shared/shared.js"
export default () => {
    const content = document.querySelector(".content");

    return fetch("./pages/party/index/party.html")
        .then((response) => response.text())
        .then((loginHtml) => {
            content.innerHTML = loginHtml;
            shared();
            generateContent();
        });
};

function generateContent() {
    fetch(`${window.apiUrl}/api/party`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + JSON.parse(localStorage.getItem("user")).accessToken,
        },
    })
        .then((response) => response.json())
        .then((data) => {
            data.forEach(party => {
                generateRow(party);
            });
        })
        .catch((error) => {
            console.log(error);
        });
}

function generateRow(party) {
    const body = document.querySelector("tbody");
    const row = document.createElement("tr");

    body.appendChild(row);

    const id = document.createElement("th");
    const name = document.createElement("td");

    row.appendChild(id);
    row.appendChild(name);

    id.innerText = party.id;
    name.innerText = party.name;
}