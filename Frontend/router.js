import renderMain from "./pages/main/main.js";
import renderLogin from "./pages/login/login.js";
import renderVote from "./pages/vote/vote.js";  
import renderCandidates from "./pages/candidate/index/candidate.js";  
import renderDeleteCandidate from "./pages/candidate/delete/delete.js";
import renderEditCandidate from "./pages/candidate/edit/edit.js";  
import renderCreateCandidate from "./pages/candidate/create/create.js";
import renderParties from "./pages/party/index/party.js";
import renderCreateParty from "./pages/party/create/create.js"
import renderStatistics from "./pages/statistics/statistics.js";  

export default function () {
  window.router = new Navigo("/", { hash: true });

  router
    .on({
      "/": () => {
        renderMain().then(router.updatePageLinks);
      },
      login: () => {
        renderLogin();
      },
      vote: () => {
        renderVote();
      },
      "/vote/:sorting": ({data}) => {
        renderVote(data.sorting);
      },
      candidates: () => {
        renderCandidates();
      },
      "/candidates/:sorting": ({data}) => {
        renderCandidates(data.sorting);
      },
      "/candidate/delete/:candidateId": ({data}) => {
        renderDeleteCandidate(data.candidateId);
      },
      "/candidate/edit/:candidateId": ({data}) => {
        renderEditCandidate(data.candidateId);
      },
      "/candidate/create/": () => {
        renderCreateCandidate();
      },
      parties: () => {
        renderParties();
      },
      "/party/create/": () => {
        renderCreateParty();
      },
      statistics: () => {
        renderStatistics();
      }
    })
    .resolve();
}