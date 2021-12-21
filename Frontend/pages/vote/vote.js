import shared from"/pages/shared/shared.js"

export default (sorting) => {
    const content = document.querySelector(".content");

    return fetch("./pages/vote/vote.html")
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
                data.sort((a,b) => (a.fullName > b.fullName) ? 1 : ((b.fullName > a.fullName) ? -1 : 0)).forEach(candidate => {
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

    const fullName = document.createElement("td");
    const partyName = document.createElement("td");
    const voteButton = document.createElement("td");

    row.appendChild(fullName);
    row.appendChild(partyName);
    row.appendChild(voteButton);

    fullName.innerText = candidate.fullName;
    partyName.innerText = candidate.partyName;

    const link = document.createElement("a");
    link.classList.add("nav-link");
    const button = document.createElement("span");
    button.classList.add("btn", "btn-primary");
    button.innerText = "Vote";

    voteButton.appendChild(link);
    link.appendChild(button);

    link.addEventListener("click", (event) => vote(event, candidate))
}

function vote(event, candidate){
    let cpr = prompt("Please enter your CPR!", "CPR...");

    if(cpr.length == 10){
        fetch(`${window.apiUrl}/api/vote/${candidate.id}`, {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: `${cpr}`
            })
            .then((response) => response.text())
            .then((data) => {
                window.location.href = "/#/vote";
                window.alert(data);
            }).catch((error) => {
              window.alert(error);
            });
    }
    else{
        window.alert("Please input valid cpr!");
    }
}