// Переключение вкладок
function showTab(tabName) {
    const tabContents = document.querySelectorAll('.tab-content');
    tabContents.forEach(content => content.style.display = 'none');
  
    const tabs = document.querySelectorAll('.tab');
    tabs.forEach(tab => tab.classList.remove('active'));
  
    document.getElementById(tabName).style.display = 'flex';
    document.querySelector(`button[onclick="showTab('${tabName}')"]`).classList.add('active');
}

// Карусель (объединенный код)
document.addEventListener('DOMContentLoaded', () => {
    const carousels = document.querySelectorAll('.carousel');
    carousels.forEach(carousel => {
        const leftBtn = carousel.querySelector('.carousel-btn.left');
        const rightBtn = carousel.querySelector('.carousel-btn.right');
        const track = carousel.querySelector('.carousel-track');
        const cards = track.querySelectorAll('.event-card');
        
        let index = 0;
        const cardWidth = cards[0].offsetWidth + 10; // ширина карточки + отступ
        const visibleCards = 3; // количество видимых карточек
        const maxIndex = cards.length - visibleCards;

        // Функция обновления позиции карусели
        const updateCarousel = () => {
            track.style.transform = `translateX(${-index * cardWidth}px)`;
        };

        // Обработчик кнопки "назад"
        leftBtn.onclick = () => {
            if (index > 0) {
                index--;
                updateCarousel();
            }
        };

        // Обработчик кнопки "вперед"
        rightBtn.onclick = () => {
            if (index < maxIndex) {
                index++;
                updateCarousel();
            }
        };
    });
});
