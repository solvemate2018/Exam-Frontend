import shared from "/pages/shared/shared.js"
export default () => {
    const content = document.querySelector(".content");

    return fetch("./pages/statistics/statistics.html")
        .then((response) => response.text())
        .then((loginHtml) => {
            content.innerHTML = loginHtml;
            shared();
            generateContent();
        });
};

function generateContent(){
    fetch(`${window.apiUrl}/api/vote/results`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + JSON.parse(localStorage.getItem("user")).accessToken,
        },
    })
        .then((response) => response.json())
        .then((data) => {
            data.parties.forEach(party => {
                generatePartyStatistics(party, data.totalNumberOfVotes);
            });
            data.candidates.sort((a,b) => (a.numberOfVotes > b.numberOfVotes) ? -1 : ((b.numberOfVotes > a.numberOfVotes) ? 1 : 0)).slice(0, 8).forEach(candidate => {
                generateCandidateStatistic(candidate, data.totalNumberOfVotes);
            });
            const totalVotes = document.querySelector(".h1");
            totalVotes.innerText += " " + data.totalNumberOfVotes;
        })
        .catch((error) => {
            console.log(error);
        });
}

function generatePartyStatistics(party, numberOfVotes){
    const partyStatistics = document.querySelector(".party-statistics");
    const percentageOfVotes = ((party.numberOfVotes / numberOfVotes) * 100).toFixed(2);

    const partyTitle = document.createElement("p");
    partyTitle.classList.add("h4");
    partyTitle.innerText = party.name + " - Votes: " + party.numberOfVotes;

    partyStatistics.appendChild(partyTitle);

    const progress = document.createElement("div");
    progress.classList.add("progress");

    partyStatistics.appendChild(progress);

    const progressbar = document.createElement("div");
    progressbar.classList.add("progress-bar", "bg-success");
    progressbar.setAttribute("role", "progressbar");
    progressbar.setAttribute("style", "width: " + percentageOfVotes + "%");
    progressbar.setAttribute("aria-valuenow", percentageOfVotes);
    progressbar.setAttribute("aria-valuemin", 0);
    progressbar.setAttribute("aria-valuemax", 100);
    progressbar.innerText = percentageOfVotes;

    progress.appendChild(progressbar);

    partyStatistics.appendChild(document.createElement("br"));
}

function generateCandidateStatistic(candidate, numberOfVotes){
    const candidateStatistics = document.querySelector(".candidate-statistics");
    const percentageOfVotes = ((candidate.numberOfVotes / numberOfVotes) * 100).toFixed(2);

    const candidateTitle = document.createElement("p");
    candidateTitle.classList.add("h4");
    candidateTitle.innerText = candidate.fullName + " " + candidate.partyName + " - Votes: " + candidate.numberOfVotes;

    candidateStatistics.appendChild(candidateTitle);

    const progress = document.createElement("div");
    progress.classList.add("progress");

    candidateStatistics.appendChild(progress);

    const progressbar = document.createElement("div");
    progressbar.classList.add("progress-bar", "bg-success");
    progressbar.setAttribute("role", "progressbar");
    progressbar.setAttribute("style", "width: " + percentageOfVotes + "%");
    progressbar.setAttribute("aria-valuenow", percentageOfVotes);
    progressbar.setAttribute("aria-valuemin", 0);
    progressbar.setAttribute("aria-valuemax", 100);
    progressbar.innerText = percentageOfVotes;

    progress.appendChild(progressbar);

    candidateStatistics.appendChild(document.createElement("br"));
}