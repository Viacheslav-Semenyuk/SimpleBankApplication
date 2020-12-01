let user = {
}

function getCard() {
    const fetchPromise = fetch("http://localhost:8080/api/user/profile");
    let card = document.getElementById("card");
    let transfer_form = document.getElementById("fromCard");
    let add_card = document.getElementById("user_id");
    card.innerHTML = '';
    fetchPromise
        .then((res) => res.json())
        .then((data) => {
            user = data;

            add_card.setAttribute('cardId', data.id);

            for (let cardItem of data.card) {
                let listCard = document.createRange()
                    .createContextualFragment(getCardContent(cardItem));
                card.appendChild(listCard)

                let listCardId = document.createRange()
                    .createContextualFragment(getCardToCard(cardItem));
                transfer_form.appendChild(listCardId)
            }
        })
}

function getCardToCard(data) {

    return `
            <option  value="${data.id}">${data.id}</option>
    `
}

function getCardContent(data) {
    return `
    <div class="div-card">
   <div id="${data.id}" class="div-card-2"   onclick="generateArchive(${data.id})">
   <div class="name">Card</div>
    <div class="balance" id="balance">Balance: ${data.balance}<small>$</small></div>
    <div class="number" id="number">Number card: ${data.id}</div> 
   </div>
    <div class="btn-card">
    <button type="button" class="btn btn-card dropdown-toggle dropdown-toggle-split"
     data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    <span class="sr-only">Toggle Dropdown</span>
  </button>
  <div class="dropdown-menu" style="padding: unset; margin: 10%;">
    <a class="my-dropdown-item my-title" onclick="deleteCard(${data.id})" href="/view/user/transfer.html">Delete card</a>
  </div>
    </div>
    <ul class="list-group" id="list${data.id}">
    
    </ul>
    </div>
    <hr/>
    
    `
}

function getArchive(data) {
    return `
     <li id="listitem" class="list-group-item list-group-item-dark">
               ${data.createdAt}: ${data.history}</li>
    `
}

function generateArchive(data) {
    let archive = document.getElementById('list' + data);
    let id = document.getElementById(data).id;
    let list_child = archive.getElementsByClassName("list-group-item");
    if (list_child.length === 0) {
        fetch("http://localhost:8080/api/card/history/" + id, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then((res) => res.json())
            .then((data) => {
                if (data.length > 0) {
                    for (let element of data) {
                        let listArchive = document.createRange()
                            .createContextualFragment(getArchive(element))
                        archive.appendChild(listArchive)
                    }
                } else {
                    let listArchive = document.createRange()
                        .createContextualFragment(`
                <li id="listitem" class="list-group-item list-group-item-dark">
               History is empty</li>`);
                    archive.appendChild(listArchive)

                }
            })
    } else {
        while (list_child.length !== 0) {
            document.getElementById('listitem').remove()
        }
    }
}

function replenish() {
    let id = document.getElementById('fromCard').value;
    let replenish = document.getElementById('replenish').value;

    fetch("http://localhost:8080/api/card", {
        method: "PUT",
        body: JSON.stringify({
            id: id,
            balance: replenish
        }),
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then((res) => res.json())
        .then((data) => console.log(data))
}

function transfer() {
    let fromCard = document.getElementById('fromCard').value;
    let toCard = document.getElementById('toCard').value;
    let amount = document.getElementById('amount').value;
    fetch("http://localhost:8080/api/card/transfer", {
        method: "POST",
        body: JSON.stringify({
            fromCard: fromCard,
            toCard: toCard,
            amount: amount
        }),
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then((res) => res.json())
        .then((data) => console.log(data))
}

function deleteCard(data) {

    let id = document.getElementById(data).id;

    fetch("http://localhost:8080/api/card/" + id, {
        method: "DELETE",
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then((data) => console.log(data))

}

function addCard() {

    fetch("http://localhost:8080/api/card/created", {
        method: "POST",
        body: JSON.stringify(
            user
        ),
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then((data) => console.log(data));
    getCard()
}
