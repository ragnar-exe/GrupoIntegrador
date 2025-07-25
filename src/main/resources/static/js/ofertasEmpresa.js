document.addEventListener("DOMContentLoaded", function () {
    const modalEditar = document.getElementById("editarOferta");

    modalEditar.addEventListener("show.bs.modal", function (event) {
        const button = event.relatedTarget;

        // Obtiene los valores desde los data-attributes del botón
        const id = button.getAttribute("data-id");
        const titulo = button.getAttribute("data-titulo");
        const descripcion = button.getAttribute("data-descripcion");
        const requisitos = button.getAttribute("data-requisitos");
        const vacantes = button.getAttribute("data-vacantes");
        const fecha = button.getAttribute("data-fecha");

        // Asigna los valores a los campos del modal
        document.getElementById("editId").value = id;
        document.getElementById("editTitulo").value = titulo;
        document.getElementById("editDescripcion").value = descripcion;
        document.getElementById("editRequisitos").value = requisitos;
        document.getElementById("editVacantes").value = vacantes;
        document.getElementById("editFechaLimite").value = fecha;
    });
});
