document.addEventListener("DOMContentLoaded", function () {
  // Form validation and submission
  function validateForm() {
    const form = document.getElementById("companyRegistrationForm");
    const inputs = form.querySelectorAll("input[required], textarea[required]");
    let isValid = true;

    inputs.forEach((input) => {
      if (!input.value.trim()) {
        input.classList.add("is-invalid");
        input.classList.remove("is-valid");
        isValid = false;
      } else {
        input.classList.remove("is-invalid");
        input.classList.add("is-valid");
      }
    });

    // Special validations
    const ruc = document.querySelector('input[name="ruc"]');
    if (ruc.value && ruc.value.length !== 11) {
      ruc.classList.add("is-invalid");
      ruc.classList.remove("is-valid");
      isValid = false;
    }

    const password = document.querySelector('input[name="contrasena"]');
    const confirmPassword = document.querySelector(
      'input[name="confirmarContrasena"]'
    );

    if (password.value.length < 8) {
      password.classList.add("is-invalid");
      password.classList.remove("is-valid");
      isValid = false;
    }

    if (password.value !== confirmPassword.value) {
      confirmPassword.classList.add("is-invalid");
      confirmPassword.classList.remove("is-valid");
      isValid = false;
    }

    return isValid;
  }

  // Form submission
  document
    .getElementById("companyRegistrationForm")
    .addEventListener("submit", async (e) => {
      e.preventDefault();

      if (!validateForm()) {
        return;
      }

      const submitBtn = document.getElementById("submitBtn");
      const spinner = submitBtn.querySelector(".loading-spinner");

      // Show loading state
      submitBtn.disabled = true;
      spinner.style.display = "inline-block";
      submitBtn.innerHTML =
        '<span class="loading-spinner me-2"></span>Creando cuenta...';

      // Simulate API call
      try {
        await new Promise((resolve) => setTimeout(resolve, 2000));

        // Success
        alert(
          "¡Registro exitoso! Bienvenido a EmpleaU. Ya puedes comenzar a publicar ofertas laborales."
        );

        // Reset form or redirect
        window.location.href = "/login-empresa.html";
      } catch (error) {
        alert("Error al crear la cuenta. Por favor intenta nuevamente.");
      } finally {
        // Reset button
        submitBtn.disabled = false;
        spinner.style.display = "none";
        submitBtn.innerHTML =
          '<i class="bi bi-check-circle me-2"></i>Crear Cuenta Empresarial';
      }
    });

  // Real-time validation
  document.querySelectorAll("input, textarea").forEach((input) => {
    input.addEventListener("blur", () => {
      if (input.hasAttribute("required") && !input.value.trim()) {
        input.classList.add("is-invalid");
        input.classList.remove("is-valid");
      } else if (input.value.trim()) {
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

  // RUC validation
  document
    .querySelector('input[name="ruc_emp"]')
    .addEventListener("input", (e) => {
      e.target.value = e.target.value.replace(/\D/g, "");
    });

  // Password confirmation validation
  const password = document.querySelector('input[name="contrasena"]');
  const confirmPassword = document.querySelector(
    'input[name="confirmarContrasena"]'
  );

  confirmPassword.addEventListener("input", () => {
    if (password.value !== confirmPassword.value) {
      confirmPassword.classList.add("is-invalid");
      confirmPassword.classList.remove("is-valid");
    } else {
      confirmPassword.classList.remove("is-invalid");
      confirmPassword.classList.add("is-valid");
    }
  });

  // Initialize animations
  document.addEventListener("DOMContentLoaded", () => {
    // Trigger form field animations
    const formFields = document.querySelectorAll(".form-field");
    formFields.forEach((field, index) => {
      setTimeout(() => {
        field.style.animationDelay = `${index * 0.1}s`;
      }, 100);
    });
  });
});
