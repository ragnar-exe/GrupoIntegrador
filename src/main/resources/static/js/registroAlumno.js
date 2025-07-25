document.addEventListener("DOMContentLoaded", function () {
  // Form validation
  function validateForm() {
    const form = document.getElementById("studentRegistrationForm");
    const inputs = form.querySelectorAll("input[required], select[required]");
    let isValid = true;

    inputs.forEach((input) => {
      if (!input.value.trim()) {
        input.classList.add("is-invalid");
        isValid = false;
      } else {
        input.classList.remove("is-invalid");
        input.classList.add("is-valid");
      }
    });

    // Validar DNI
    const dni = document.querySelector('input[th\\:field="*{dni}"]');
    if (dni?.value && dni.value.length !== 8) {
      dni.classList.add("is-invalid");
      isValid = false;
    }

    const password = document.querySelector(
      'input[th\\:field="*{contrasena}"]'
    );
    const confirmPassword = document.querySelector(
      'input[th\\:field="*{confirmarContrasena}"]'
    );

    if (password?.value.length < 8) {
      password.classList.add("is-invalid");
      isValid = false;
    }

    if (password?.value !== confirmPassword?.value) {
      confirmPassword.classList.add("is-invalid");
      isValid = false;
    }

    validarEdad();
    if (
      document
        .getElementById("fechaNacimiento")
        .classList.contains("is-invalid")
    ) {
      isValid = false;
    }

    return isValid;
  }

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

  document
    .getElementById("studentRegistrationForm")
    .addEventListener("submit", async (e) => {
      e.preventDefault();

      if (!validateForm()) return;

      const submitBtn = document.getElementById("submitBtn");
      const spinner = submitBtn.querySelector(".loading-spinner");

      submitBtn.disabled = true;
      spinner.style.display = "inline-block";
      submitBtn.innerHTML =
        '<span class="loading-spinner me-2"></span>Creando cuenta...';

      try {
        e.target.submit();
      } catch (error) {
        alert("Error al crear la cuenta. Por favor intenta nuevamente.");
      } finally {
        submitBtn.disabled = false;
        spinner.style.display = "none";
        submitBtn.innerHTML =
          '<i class="bi bi-check-circle me-2"></i>Crear Mi Cuenta';
      }
    });

  document.querySelectorAll("input, select").forEach((input) => {
    input.addEventListener("blur", () => {
      if (input.hasAttribute("required") && !input.value.trim()) {
        input.classList.add("is-invalid");
      } else {
        input.classList.remove("is-invalid");
        input.classList.add("is-valid");
      }
    });

    input.addEventListener("input", () => {
      if (input.classList.contains("is-invalid") && input.value.trim()) {
        input.classList.remove("is-invalid");
        input.classList.add("is-valid");
      }
    });
  });

  const password = document.querySelector('input[th\\:field="*{contrasena}"]');
  const confirmPassword = document.querySelector(
    'input[th\\:field="*{confirmarContrasena}"]'
  );

  confirmPassword?.addEventListener("input", () => {
    if (password?.value !== confirmPassword?.value) {
      confirmPassword.classList.add("is-invalid");
    } else {
      confirmPassword.classList.remove("is-invalid");
      confirmPassword.classList.add("is-valid");
    }
  });

  const elements = document.querySelectorAll(
    ".form-control, .form-select, .gender-option"
  );
  elements.forEach((el, index) => {
    setTimeout(() => {
      el.style.animation = `fadeIn 0.6s ease-out ${index * 0.1}s both`;
    }, 100);
  });
});
