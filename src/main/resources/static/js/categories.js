
async function initCategoriesTab() {

    try {
        const categories = await api.get('/categories');
        console.log('Categories loaded:', categories);
    } catch (error) {
        showApiError(error);
    }
}