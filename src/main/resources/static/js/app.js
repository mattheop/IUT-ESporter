const defaultSwal = {
    confirmButtonText: "Confirmer",
    cancelButtonText: 'Annuler',
    confirmButtonColor: '#1e5262',
    cancelButtonColor: '#de4242',
}

$(function () {
    const token = $("meta[name='_csrf']").attr("content")
    const header = $("meta[name='_csrf_header']").attr("content")
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token)
    })
})


$('.form-need-confirm').on("submit", (e) => {
    e.preventDefault()

    const confirmText = e.currentTarget.getAttribute("data-confirm-message")

    $(document).ready(() => {
        Swal.fire({
            ...defaultSwal,
            type: 'question',
            title: "Êtes-vous sûrs ?",
            text: confirmText,
            showCancelButton: true
        }).then((result) => {
            if (result.value) e.currentTarget.submit()
        })
    })
})

$(".poule-toggle").click((e) => {
    const target = e.currentTarget.getAttribute("data-target")

    $(".poule-toggle").removeClass("btn-primary")
    $(".rencontre-poule").removeClass("active")

    $(e.currentTarget).addClass("btn-primary")
    $(`#${target}`).addClass("active")
})

$(".rencontre-line.arbitre-mode").click((e) => {
    const equipe1Name = e.currentTarget.getAttribute("data-equipe1")
    const equipe2Name = e.currentTarget.getAttribute("data-equipe2")
    const rencontreId = e.currentTarget.getAttribute("data-rencontre-id")

    const scoreForm = Swal.fire({
        ...defaultSwal,
        type: 'warning',
        title: "Modification rencontre",
        text: "Veuillez saisir le score de la rencontre ci-dessous",
        html:
            '<div style="display: flex; flex-direction: column;">' +
            `<input style="width: 100%; max-width: 100%" type="number" min="0" id="score1" class="swal2-input" placeholder="Score ${equipe1Name}">` +
            `<input style="width: 100%; max-width: 100%" type="number" min="0" id="score2" class="swal2-input" placeholder="Score ${equipe2Name}">` +
            '</div>',
        showCancelButton: true,
        preConfirm: () => {
            return {
                'equipe1': document.getElementById('score1').value,
                'equipe2': document.getElementById('score2').value
            }
        }
    }).then((result) => {
        $.ajax({
            url: '/arbitre/score',
            type: 'PUT',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify({rencontreId, 'equipe1': result.value.equipe1, 'equipe2': result.value.equipe2}),
            statusCode: {
                200: (response) => {
                    Swal.fire({
                        ...defaultSwal,
                        type: 'success',
                        title: "Modification rencontre",
                        text: "Le score a bien été mis à jour",
                        showCancelButton: false,
                    })

                    $(e.currentTarget).find(".rencontre-line--score p:nth-child(1)").text(result.value.equipe1)
                    $(e.currentTarget).find(".rencontre-line--score p:nth-child(3)").text(result.value.equipe2)
                },
                500: (response) => {
                    Swal.fire({
                        ...defaultSwal,
                        type: 'error',
                        title: "Modification rencontre",
                        text: "Une erreur est survenue veuillez réessayer ultérieurement ou contacter un administrateur.",
                        showCancelButton: false,
                    })
                }
            },
            success: function (data) {
            }
        })
    })
})
