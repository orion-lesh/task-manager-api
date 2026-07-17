
function showNotification(message, type = 'info') {
    const el = document.getElementById('notification');
    const styles = {
        success: 'bg-green-100 dark:bg-green-900 text-green-800 dark:text-green-200 border border-green-300 dark:border-green-700',
        error:   'bg-red-100 dark:bg-red-900 text-red-800 dark:text-red-200 border border-red-300 dark:border-red-700',
        info:    'bg-blue-100 dark:bg-blue-900 text-blue-800 dark:text-blue-200 border border-blue-300 dark:border-blue-700'
    };

    el.className = `mb-4 p-4 rounded-lg fade-in ${styles[type]}`;
    el.textContent = message;
    el.classList.remove('hidden');


    clearTimeout(el._timer);
    el._timer = setTimeout(() => {
        el.classList.add('hidden');
    }, 4000);
}


function showApiError(error) {
    if (error instanceof ApiError && error.fieldErrors) {
        const details = error.fieldErrors
            .map(fe => `${fe.field}: ${fe.message}`)
            .join('; ');
        showNotification(`${error.message} — ${details}`, 'error');
    } else {
        showNotification(error.message || 'Unknown error', 'error');
    }
    console.error(error);
}


function formatDateTime(isoString) {
    if (!isoString) return '—';
    const date = new Date(isoString);
    return date.toLocaleString(undefined, {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}


function formatDate(isoDate) {
    if (!isoDate) return '—';
    const date = new Date(isoDate);
    return date.toLocaleDateString(undefined, {
        year: 'numeric',
        month: 'short',
        day: 'numeric'
    });
}


function escapeHtml(text) {
    if (text == null) return '';
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}