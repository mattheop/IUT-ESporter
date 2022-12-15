const defaultSwal = {
    confirmButtonText: "Confirmer",
    cancelButtonText: 'Annuler',
    confirmButtonColor: '#1e5262',
    cancelButtonColor: '#de4242',
}

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

