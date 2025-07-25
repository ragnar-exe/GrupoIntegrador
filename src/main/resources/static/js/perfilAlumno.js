// CV upload functionality
document.addEventListener("DOMContentLoaded", function () {
  const cvUpload = document.getElementById("cvUpload");
  const cvInput = document.getElementById("cvInput");
  const cvPreview = document.getElementById("cvPreview");
  const cvFileName = document.getElementById("cvFileName");

  // Verificar que los elementos existen antes de agregar event listeners
  if (cvUpload && cvInput && cvPreview && cvFileName) {
    // Click to upload
    cvUpload.addEventListener("click", () => cvInput.click());

    // Drag and drop functionality
    cvUpload.addEventListener("dragover", (e) => {
      e.preventDefault();
      cvUpload.classList.add("dragover");
    });

    cvUpload.addEventListener("dragleave", () => {
      cvUpload.classList.remove("dragover");
    });

    cvUpload.addEventListener("drop", (e) => {
      e.preventDefault();
      cvUpload.classList.remove("dragover");
      const files = e.dataTransfer.files;
      if (files.length > 0) {
        handleCvUpload(files[0]);
      }
    });

    // File input change
    cvInput.addEventListener("change", (e) => {
      if (e.target.files.length > 0) {
        handleCvUpload(e.target.files[0]);
      }
    });

    // Handle file upload
    function handleCvUpload(file) {
      // Validate file type (PDF o DOC/DOCX)
      const allowedTypes = [
        "application/pdf",
        "application/msword",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
      ];
      if (!allowedTypes.includes(file.type)) {
        showAlert("Solo se permiten archivos PDF o Word (DOC/DOCX).", "error");
        return;
      }

      // Validate file size (5MB max)
      const maxSize = 5 * 1024 * 1024; // 5MB
      if (file.size > maxSize) {
        showAlert("El archivo es muy grande. Máximo 5MB.", "error");
        return;
      }

      // Show success message
      cvFileName.textContent = file.name;
      cvPreview.classList.remove("d-none");

      // Add fade in animation
      cvPreview.style.opacity = "0";
      setTimeout(() => {
        cvPreview.style.transition = "opacity 0.5s ease";
        cvPreview.style.opacity = "1";
      }, 100);

      // Hide upload area after successful upload
      setTimeout(() => {
        cvUpload.style.opacity = "0.7";
        cvUpload.style.pointerEvents = "none";
      }, 1000);
    }
  }

  // Smooth scroll for internal links
  document.querySelectorAll('a[href^="#"]').forEach((anchor) => {
    anchor.addEventListener("click", function (e) {
      e.preventDefault();
      const targetId = this.getAttribute("href");
      const target = targetId ? document.querySelector(targetId) : null;

      if (target) {
        target.scrollIntoView({
          behavior: "smooth",
          block: "start",
        });
      }
    });
  });

  // Add loading state to buttons
  document.querySelectorAll(".btn-primary-red").forEach((btn) => {
    btn.addEventListener("click", function (e) {
      if (this.textContent.includes("Editar")) {
        const originalText = this.innerHTML;
        this.innerHTML =
          '<span class="loading-spinner me-2"></span>Cargando...';
        this.disabled = true;

        // Restaurar el botón después de 2 segundos o cuando la operación termine
        setTimeout(() => {
          this.innerHTML = originalText;
          this.disabled = false;
        }, 2000);
      }
    });
  });

  // Animate elements on scroll
  const observerOptions = {
    threshold: 0.1,
    rootMargin: "0px 0px -50px 0px",
  };

  const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting) {
        entry.target.classList.add("visible");
        entry.target.style.opacity = "1";
        entry.target.style.transform = "translateY(0)";
        // Opcional: dejar de observar después de que se muestre
        observer.unobserve(entry.target);
      }
    });
  }, observerOptions);

  // Observe all info items
  document.querySelectorAll(".info-item").forEach((item) => {
    item.style.opacity = "0";
    item.style.transform = "translateY(20px)";
    item.style.transition = "all 0.6s ease";
    observer.observe(item);
  });

  // Función para mostrar alertas personalizadas
  function showAlert(message, type = "success") {
    // Aquí puedes implementar un sistema de notificaciones más elegante
    // que un simple alert(), como un toast o modal
    alert(message); // Temporal - reemplazar con mejor implementación
  }

  // Validar edad al cambiar la fecha de nacimiento
  function validarEdad() {
    const fechaInput = document.getElementById("fechaNacimiento");
    const fechaError = document.getElementById("fechaError");
    const submitBtn = document.getElementById("submitBtn");

    if (!fechaInput?.value) return;

    const fechaNacimiento = new Date(fechaInput.value);
    const hoy = new Date();

    let edad = hoy.getFullYear() - fechaNacimiento.getFullYear();
    const mes = hoy.getMonth() - fechaNacimiento.getMonth();

    if (mes < 0 || (mes === 0 && hoy.getDate() < fechaNacimiento.getDate())) {
      edad--;
    }

    if (edad < 18) {
      fechaInput.classList.add("is-invalid");
      fechaError.style.display = "block";
      submitBtn.disabled = true;
    } else {
      fechaInput.classList.remove("is-invalid");
      fechaInput.classList.add("is-valid");
      fechaError.style.display = "none";
      submitBtn.disabled = false;
    }
  }
});






