
function initTabs() {
    const buttons = document.querySelectorAll('.tab-btn');
    const panels = document.querySelectorAll('.tab-panel');

    buttons.forEach(button => {
        button.addEventListener('click', () => {
            const targetTab = button.dataset.tab;


            buttons.forEach(b => b.classList.remove('active'));
            button.classList.add('active');


            panels.forEach(p => p.classList.add('hidden'));
            document.getElementById(`tab-${targetTab}`).classList.remove('hidden');


            if (targetTab === 'tasks') {
                initTasksTab();
            } else if (targetTab === 'categories') {
                initCategoriesTab();
            }
        });
    });
}

/**
 * Инициализация переключателя темы.
 * Сохраняем выбор в localStorage, чтобы при перезагрузке тема сохранялась.
 */
function initTheme() {
    const htmlEl = document.documentElement;
    const toggleBtn = document.getElementById('theme-toggle');

    // Применяем сохранённую тему или системную по умолчанию
    const savedTheme = localStorage.getItem('theme');
    const systemPrefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
    const shouldBeDark = savedTheme === 'dark' || (savedTheme === null && systemPrefersDark);

    if (shouldBeDark) {
        htmlEl.classList.add('dark');
    }


    toggleBtn.addEventListener('click', () => {
        htmlEl.classList.toggle('dark');
        const isDark = htmlEl.classList.contains('dark');
        localStorage.setItem('theme', isDark ? 'dark' : 'light');
    });
}


document.addEventListener('DOMContentLoaded', () => {
    initTheme();
    initTabs();


    initTasksTab();
});