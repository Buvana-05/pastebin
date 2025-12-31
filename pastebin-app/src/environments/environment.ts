export const environment = {
    production: true,
    get apiUrl() {
        if (typeof window !== 'undefined' && window.location.hostname === 'localhost') {
            return 'http://localhost:8080/api/pastes';
        }
        return 'https://pastebin-backend-kwjl.onrender.com/api/pastes';
    }
};
