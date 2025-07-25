 // Smooth animations on load
        document.addEventListener('DOMContentLoaded', function() {
            const statsNumbers = document.querySelectorAll('.stats-number');
            statsNumbers.forEach(stat => {
                const finalValue = stat.textContent;
                const isPercentage = finalValue.includes('%');
                const numericValue = parseInt(finalValue.replace(/[^\d]/g, ''));
                let currentValue = 0;
                const increment = numericValue / 50;
                const timer = setInterval(() => {
                    currentValue += increment;
                    if (currentValue >= numericValue) {
                        currentValue = numericValue;
                        clearInterval(timer);
                    }
                    stat.textContent = Math.floor(currentValue) + (isPercentage ? '%' : '');
                }, 30);
            });
            const cards = document.querySelectorAll('.content-card, .job-card, .stats-card');
            cards.forEach(card => {
                card.addEventListener('mouseenter', function() {
                    this.style.transform = 'translateY(-8px)';
                });
                card.addEventListener('mouseleave', function() {
                    this.style.transform = 'translateY(0)';
                });
            });
            const buttons = document.querySelectorAll('.btn');
            buttons.forEach(btn => {
                btn.addEventListener('click', function() {
                    if (!this.classList.contains('loading')) {
                        this.classList.add('loading');
                        setTimeout(() => {
                            this.classList.remove('loading');
                        }, 2000);
                    }
                });
            });
        });
        document.querySelectorAll('a[href^="#"]').forEach(anchor => {
            anchor.addEventListener('click', function (e) {
                e.preventDefault();
                const target = document.querySelector(this.getAttribute('href'));
                if (target) {
                    target.scrollIntoView({
                        behavior: 'smooth',
                        block: 'start'
                    });
                }
            });
        });