
async function initTasksTab() {
    try {
        const page = await api.get('/tasks?page=0&size=5');
        console.log('Tasks loaded:', page);
    } catch (error) {
        showApiError(error);
    }
}