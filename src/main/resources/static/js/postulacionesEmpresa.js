// Filter functionality
document.querySelectorAll(".filter-tab").forEach((tab) => {
  tab.addEventListener("click", function () {
    // Remove active class from all tabs
    document
      .querySelectorAll(".filter-tab")
      .forEach((t) => t.classList.remove("active"));
    // Add active class to clicked tab
    this.classList.add("active");

    const status = this.dataset.status;
    const cards = document.querySelectorAll(".candidate-card");
    const emptyState = document.getElementById("emptyState");
    let visibleCards = 0;

    cards.forEach((card) => {
      if (status === "todos" || card.dataset.status === status) {
        card.style.display = "block";
        visibleCards++;
      } else {
        card.style.display = "none";
      }
    });

    // Show/hide empty state
    if (visibleCards === 0) {
      emptyState.style.display = "block";
    } else {
      emptyState.style.display = "none";
    }
  });
});

// Sort functionality
document.querySelector(".sort-select").addEventListener("change", function () {
  const sortBy = this.value;
  const grid = document.getElementById("candidatesGrid");
  const cards = Array.from(grid.querySelectorAll(".candidate-card"));

  // Add loading state
  grid.classList.add("loading");

  setTimeout(() => {
    cards.sort((a, b) => {
      switch (sortBy) {
        case "nombre":
          return a
            .querySelector("h3")
            .textContent.localeCompare(b.querySelector("h3").textContent);
        case "carrera":
          return a
            .querySelector(".candidate-role")
            .textContent.localeCompare(
              b.querySelector(".candidate-role").textContent
            );
        case "estado":
          return a.dataset.status.localeCompare(b.dataset.status);
        default:
          return 0;
      }
    });

    // Re-append sorted cards
    cards.forEach((card) => grid.appendChild(card));

    // Remove loading state
    grid.classList.remove("loading");
  }, 800);
});

// Button actions
document.querySelectorAll(".btn-accept").forEach((btn) => {
  btn.addEventListener("click", function () {
    if (!this.disabled) {
      const card = this.closest(".candidate-card");
      const badge = card.querySelector(".status-badge");

      // Update status
      card.dataset.status = "aceptado";
      badge.className = "status-badge status-aceptado";
      badge.textContent = "Aceptado";

      // Disable button
      this.disabled = true;
      this.style.opacity = "0.5";
      this.innerHTML = '<i class="bi bi-check-circle"></i> Aceptado';

      // Show success message
      alert("Candidato aceptado exitosamente");
    }
  });
});

document.querySelectorAll(".btn-reject").forEach((btn) => {
  btn.addEventListener("click", function () {
    if (confirm("¿Estás seguro de que quieres rechazar este candidato?")) {
      const card = this.closest(".candidate-card");
      const badge = card.querySelector(".status-badge");

      // Update status
      card.dataset.status = "rechazado";
      badge.className = "status-badge status-rechazado";
      badge.textContent = "Rechazado";

      // Update accept button
      const acceptBtn = card.querySelector(".btn-accept");
      acceptBtn.disabled = true;
      acceptBtn.style.opacity = "0.5";

      // Show success message
      alert("Candidato rechazado");
    }
  });
});

// Smooth animations on load
document.addEventListener("DOMContentLoaded", function () {
  const cards = document.querySelectorAll(".candidate-card");
  cards.forEach((card, index) => {
    card.style.opacity = "0";
    card.style.transform = "translateY(20px)";

    setTimeout(() => {
      card.style.transition = "all 0.6s ease";
      card.style.opacity = "1";
      card.style.transform = "translateY(0)";
    }, index * 100);
  });
});
