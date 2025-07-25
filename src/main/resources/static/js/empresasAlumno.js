document.querySelectorAll(".btn-follow").forEach((btn) => {
  btn.addEventListener("click", function () {
    if (this.classList.contains("following")) {
      this.classList.remove("following");
      this.innerHTML = '<i class="bi bi-plus me-1"></i>Seguir';
    } else {
      this.classList.add("following");
      this.innerHTML = '<i class="bi bi-check me-1"></i>Siguiendo';
    }
  });
});

// Funcionalidad de filtros
document.querySelectorAll(".filter-btn").forEach((btn) => {
  btn.addEventListener("click", function () {
    // Remover clase active de todos los botones
    document
      .querySelectorAll(".filter-btn")
      .forEach((b) => b.classList.remove("active"));
    // Agregar clase active al botón clickeado
    this.classList.add("active");

    // Aquí puedes agregar la lógica de filtrado
    console.log("Filtrar por:", this.textContent);
  });
});

// Funcionalidad de búsqueda
const searchInput = document.querySelector(".search-input");
searchInput.addEventListener("input", function () {
  const searchTerm = this.value.toLowerCase();
  console.log("Buscar:", searchTerm);
  // Aquí puedes agregar la lógica de búsqueda
});

// Animación de entrada escalonada
const observerOptions = {
  threshold: 0.1,
  rootMargin: "0px 0px -50px 0px",
};

const observer = new IntersectionObserver((entries) => {
  entries.forEach((entry, index) => {
    if (entry.isIntersecting) {
      setTimeout(() => {
        entry.target.style.opacity = "1";
        entry.target.style.transform = "translateY(0)";
      }, index * 100);
    }
  });
}, observerOptions);

// Aplicar animación a las cards
document.querySelectorAll(".stagger-animation").forEach((card, index) => {
  card.style.opacity = "0";
  card.style.transform = "translateY(30px)";
  card.style.transition = "all 0.6s ease-out";
  observer.observe(card);
});

// Contador animado para estadísticas
function animateCounter(element, target, duration = 2000) {
  let start = 0;
  const increment = target / (duration / 16);

  const timer = setInterval(() => {
    start += increment;
    if (start >= target) {
      element.textContent = target;
      clearInterval(timer);
    } else {
      element.textContent = Math.floor(start);
    }
  }, 16);
}

// Animar contadores cuando sean visibles
const statsObserver = new IntersectionObserver((entries) => {
  entries.forEach((entry) => {
    if (entry.isIntersecting) {
      const number = entry.target.querySelector(".stat-number");
      const target = parseInt(number.textContent);
      animateCounter(number, target);
      statsObserver.unobserve(entry.target);
    }
  });
});

document.querySelectorAll(".stat-item").forEach((item) => {
  statsObserver.observe(item);
});
