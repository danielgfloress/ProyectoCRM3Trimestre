const menuToggle = document.getElementById('menuToggle');
const sidebar = document.getElementById('sidebar');
const main = document.getElementById('main');
const header = document.getElementById('header');
const overlay = document.getElementById('overlay');

menuToggle.addEventListener('click', () => {
    if (window.innerWidth <= 768) {
        sidebar.classList.toggle('sidebar--mobile-active');
        overlay.classList.toggle('overlay--active');
        return;
    }
    sidebar.classList.toggle('sidebar--collapsed');
    main.classList.toggle('main--expanded');
    header.classList.toggle('header--expanded');
});

overlay.addEventListener('click', () => {
    sidebar.classList.remove('sidebar--mobile-active');
    overlay.classList.remove('overlay--active');
});

window.addEventListener('resize', () => {
    if (window.innerWidth > 768) {
        sidebar.classList.remove('sidebar--mobile-active');
        overlay.classList.remove('overlay--active');
    }
});