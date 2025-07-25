// Filter functionality
function filterByStatus(status) {
  // Update active button
  document.querySelectorAll(".filter-btn").forEach((btn) => {
    btn.classList.remove("active");
  });
  document.querySelector(`[data-status="${status}"]`).classList.add("active");

  // Filter applications
  const applications = document.querySelectorAll(".application-card");
  let visibleCount = 0;

  applications.forEach((app) => {
    if (status === "all" || app.dataset.status === status) {
      app.style.display = "block";
      visibleCount++;
    } else {
      app.style.display = "none";
    }
  });

  // Show/hide empty state
  const emptyState = document.getElementById("emptyState");
  const applicationsList = document.getElementById("applicationsList");

  if (visibleCount === 0) {
    emptyState.classList.remove("d-none");
    applicationsList.style.display = "none";
  } else {
    emptyState.classList.add("d-none");
    applicationsList.style.display = "block";
  }
}

// Initialize page
document.addEventListener("DOMContentLoaded", function () {
  // Add hover effects to cards
  document.querySelectorAll(".application-card").forEach((card) => {
    card.addEventListener("mouseenter", function () {
      this.style.transform = "translateY(-5px)";
    });

    card.addEventListener("mouseleave", function () {
      this.style.transform = "translateY(0)";
    });
  });

  // Add click effects to filter buttons
  document.querySelectorAll(".filter-btn").forEach((btn) => {
    btn.addEventListener("click", function () {
      this.style.transform = "scale(0.95)";
      setTimeout(() => {
        this.style.transform = "scale(1)";
      }, 150);
    });
  });
});
